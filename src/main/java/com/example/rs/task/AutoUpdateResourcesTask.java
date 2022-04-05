package com.example.rs.task;

import com.example.rs.entity.Resource;
import com.example.rs.service.inf.ResourceService;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author hzh
 */

public class AutoUpdateResourcesTask {

    public static final String sessionId = "0cb324a0621011c5cf45fc0ce118619d";
    public static final String cloudFlareId = "ndjMiWArLcBzuQXWbEm0Za62OW4D0o2HP3PbCYAYbvc-1646703350-0-150";
    public static final String host = "https://www.spigotmc.org";
    public static final String xfUserId = "1395701%2Cfc508bc3e20fb7a489de292d104664c5acc895e3";
    public static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36 Edg/99.0.1150.30";
    public static final String accept = "text/javascript, text/html, application/xml, text/xml, */*";
    public static final String currPath = System.getProperty("user.dir");
    @Autowired
    ResourceService resourceService;
    /**
     * 设置HttpClient代理8888端口并永远跟踪重定向响应头。
     */
    HttpClient httpClient = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 8888))).
            followRedirects(HttpClient.Redirect.ALWAYS).
            build();
    @Scheduled(fixedRate = 10000)
    public void updateResources() {
        try {
            //查出目前所有记录在案的资源信息。
            List<Resource> resourceList = resourceService.getAllResources();
            //逐一遍历处理这些资源更新。
            for (Resource resource : resourceList) {
                HttpRequest httpRequest = getBasicHttpRequest(resource.getUrl()+ "/history")
                        .header("Referer", resource.getUrl())
                        .header("User-Agent", userAgent)
                        .header("Accept", accept)
                        //使用 Gzip 压缩响应体 ， 减少网络带宽传输时间。
                        .header("Accept-Encoding", "gzip")
                        .build();
                HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
                GZIPInputStream gzipInputStream = new GZIPInputStream(httpResponse.body());
                byte[] bytes = gzipInputStream.readAllBytes();
                String body = new String(bytes, StandardCharsets.UTF_8);
                Document document = Jsoup.parse(body);
                Elements elements = document.getElementById("content").getElementsByClass("innerContent").get(0).getElementsByTag("tr");
                //删除第 1 个 tr元素，这种元素是标头展示，没有实际数据意义参考价值。
                elements.remove(0);
                ResourceInfo resourceInfo = findVersionOfList(elements, resource.getVersionNumber());
                //index值大于0说明存在资源更新.
                if (resourceInfo != null) {
                    int index = resourceInfo.getIndex();
                    String downloadUrl = elements.get(--index).getElementsByClass("download").get(0).getElementsByTag("a").get(0).attr("href");
                    HttpRequest httpRequestDownload = getBasicHttpRequest(host + "/" + downloadUrl)
                            .header("Referer", resource.getUrl())
                            .header("User-Agent", userAgent)
                            .header("Accept", accept)
                            .build();
                    HttpResponse<InputStream> httpResponseDownload = httpClient.send(httpRequestDownload, HttpResponse.BodyHandlers.ofInputStream());
                    String fileName = resource.getName() + "-" + resourceInfo.getVersionNumber() + ".jar";
                    FileOutputStream fileInputStream = new FileOutputStream(fileName);
                    fileInputStream.write(httpResponseDownload.body().readAllBytes());
                    fileInputStream.close();
                    resource.setVersionNumber(resourceInfo.versionNumber);
                    resource.setFilePath(currPath + "\\" + fileName);
                    resourceService.updateResource(resource);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取一个带身份认证的 HttpRequest 请求对象(已添加身份认证Cookie)
     */
    private static HttpRequest.Builder getBasicHttpRequest(String url) throws Exception{
        try {
            return  HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .header("Cookie", "cf_clearance=" + cloudFlareId +
                            ";xf_session=" + sessionId +
                            ";xf_user=" + xfUserId
                    );
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("构建基本网络请求时遭遇错误!");
        }
    }

    private static ResourceInfo findVersionOfList(Elements elements, Integer versionNumber) throws Exception{
        for (int i = 0; i < elements.size(); i++) {
            Element temp = elements.get(i).getElementsByClass("download").get(0);
            String str = temp.getElementsByTag("a").attr("href");
            if (!str.contains("version")) {
                throw new Exception("无法找到version关键字！");
            }
            String number = str.substring(str.indexOf("=") + 1);
            if (Integer.valueOf(number).equals(versionNumber) && i > 0) {
                return new ResourceInfo(i, Integer.valueOf(elements.get(--i).getElementsByClass("download").get(0).getElementsByTag("a").attr("href").substring(str.indexOf("=") + 1)));
            }
        }
        return null;
    }

    @Data
    static class ResourceInfo {
        Integer index;
        Integer versionNumber;

        public ResourceInfo(Integer index, Integer versionNumber) {
            this.index = index;
            this.versionNumber = versionNumber;
        }
    }
}
