package com.lewis.asynchttpclient;

import com.ning.http.client.*;
import com.ning.http.client.uri.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zmh46712
 * @version Id: Demo, v 0.1 2017/4/28 15:38 zmh46712 Exp $
 */
public class Demo {

    public static void main(String[] args) {
        AsyncHttpClient asyncHttpClient = createAsyncHttpClient();
        RequestBuilder builder = new RequestBuilder("GET");
        builder.setUrl("http://localhost:8088/mvc/hello/say");
        builder.setRequestTimeout(10000);

        asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandler<Object>() {
            @Override
            public Object onCompleted(Response response) throws Exception {
                int statusCode = response.getStatusCode();
                String responseBody = response.getResponseBody();
                System.out.println(statusCode);
                System.out.println(responseBody);
                return null;
            }
        });

    }

    public static Request createRequest(){
        RequestBuilder builder = new RequestBuilder("POST");
        setRequestHeader(builder);
        builder.setUrl("http://10.100.44.46:7151/search/search");
        builder.setBody("{\"arrCityCode\":\"HKG\",\"cabinClass\":[\"F\",\"C\",\"Y\",\"S\"],\"depCityCode\":\"PEK\",\"depDate\":\"20170701\",\"engineCodes\":[],\"memberId\":\"123456\",\"productCodes\":[\"dda\",\"WXZXCP\",\"GW\",\"qweq\"],\"reqPassenger\":[{\"passengerCnt\":1,\"passengerType\":\"ADT\"}],\"resourceId\":\"TCPL\",\"retDate\":\"\",\"traceId\":\"WX2017042816092448VJHU09L2VADHVNDJN4IENYJ7AJJF\",\"tripType\":\"OW\"}");
        builder.setRequestTimeout(10000);
        return builder.build();
    }

   //网关层header
    private static void setRequestHeader(RequestBuilder builder) {
        builder.setHeader("SName", "qa.dsf.flight.intl.urgateway.search");
        builder.setHeader("SPort", "7151");
        String localIP = "10.1.36.15";
        if (localIP == null) {
            localIP = "127.0.0.1";
        }
        builder.setHeader("CToken", localIP);
        builder.setHeader("CName", "dsf.flight.int.searchcore");
        builder.setHeader("CIP", localIP);
        builder.setHeader("CVersion", "1.0");
        builder.setHeader("SVersion", "1.0");
        builder.setHeader("PLevel", "5");
        builder.setHeader("RTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        builder.setHeader("Content-Type", "application/json;charset=utf-8");
        builder.setHeader("APIName", "search");
        builder.setHeader("Connection", "Keep-Alive");
    }

    //搜索核心header
    private static void setRequestHeaderOfSearchCore(RequestBuilder builder) {
        builder.setHeader("SName", "qa.dsf.flight.int.searchcore");
        builder.setHeader("SPort", "8082");
        String localIP = "10.1.36.15";
        if (localIP == null) {
            localIP = "127.0.0.1";
        }
        builder.setHeader("CToken", localIP);
        builder.setHeader("CName", "dsf.flight.int.searchcore");
        builder.setHeader("CIP", localIP);
        builder.setHeader("CVersion", "1.2");
        builder.setHeader("SVersion", "1.2");
        builder.setHeader("PLevel", "3");
        builder.setHeader("RTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        builder.setHeader("Content-Type", "application/json;charset=utf-8");
        builder.setHeader("APIName", "search");
        builder.setHeader("Connection", "Keep-Alive");
    }

    //


    public static AsyncHttpClient createAsyncHttpClient() {
        AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
                .setAllowPoolingConnections(true)
                .setPooledConnectionIdleTimeout(3000)
            .setMaxConnectionsPerHost(300).build();
        return new AsyncHttpClient(config);
    }
}
