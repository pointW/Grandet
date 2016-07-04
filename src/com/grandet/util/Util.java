package com.grandet.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by outen on 16/7/3.
 */
public class Util {
    private static final String prefix = "http://123.206.33.237:8000";
//    private final String prefix = "http://127.0.0.1:8000";
    public static String searchProduct(String keyword) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(prefix+"/search/"+keyword);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST either fully consume the response content  or abort request
        // execution by calling CloseableHttpResponse#close().
        //建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
        //为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）

        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            entity1.writeTo(stream);
            EntityUtils.consume(entity1);
            response.close();
        } catch (Exception e) {

        }
        return stream.toString();
    }

    public static String searchProduct(String keyword, int page) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(prefix+"/search/"+keyword+"/"+page);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            entity1.writeTo(stream);
            EntityUtils.consume(entity1);
            response.close();
        } catch (Exception e) {

        }
        return stream.toString();
    }

    public static String getProductById(long id){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(prefix+"/attribute/"+id);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            entity1.writeTo(stream);
            EntityUtils.consume(entity1);
            response.close();
        }
        catch (Exception e) {
        }
        return stream.toString();
    }

    public static List<Integer> jsonToIntList(String jsonString){
        jsonString = org.apache.commons.lang.StringUtils.strip(jsonString, "[]");
        if (jsonString.equals("")){
            return null;
        }
        String[] strings = jsonString.split(",");
        List<Integer> result = new ArrayList<Integer>();
        for (String s : strings){
            s = org.apache.commons.lang.StringUtils.strip(s, "\""+" ");
            result.add(Integer.parseInt(s));
            System.out.println(s);
        }
        return result;
    }

    public static List<Long> jsonToLongList(String jsonString){
        jsonString = org.apache.commons.lang.StringUtils.strip(jsonString, "[]");
        if (jsonString.equals("")){
            return null;
        }
        String[] strings = jsonString.split(",");
        List<Long> result = new ArrayList<Long>();
        for (String s : strings){
            s = org.apache.commons.lang.StringUtils.strip(s, "\""+" ");
            result.add(Long.parseLong(s));
            System.out.println(s);
        }
        return result;
    }

    public static Map<String, Object> jsonToMap(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map map = mapper.readValue(jsonString, Map.class);
            System.out.println(map.toString());
            return map;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

