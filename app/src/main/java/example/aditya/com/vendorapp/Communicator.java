package example.aditya.com.vendorapp;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static example.aditya.com.vendorapp.URLs.URL_CANCEL_ORDER;
import static example.aditya.com.vendorapp.URLs.URL_LOGIN;
import static example.aditya.com.vendorapp.URLs.URL_ORDER_COMPLETE;
import static example.aditya.com.vendorapp.URLs.URL_TOKEN_REFRESH;

/**
 * Created by aditya on 2/9/2018.
 */

public class Communicator {

    public static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InR1c2hhciIsIm9yaWdfaWF0IjoxNTE4MTcxNDc3LCJ1c2VyX2lkIjoxLCJlbWFpbCI6IiIsImV4cCI6MTUxODE3NzQ3N30.kTIlwg6yJYHtS62yPW2HGzPQb665fhcuth3PGp9Nmos";
    public static String WALLET_TOKEN = "4b6e39873f40492aadee397b03316b62";

    public static void Login(final String userID, final String password) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_LOGIN);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("username", userID));
                        nameValuePairs.add(new BasicNameValuePair("password", password));

                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        httppost.setHeader("Authorization","JWT "+token);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void updateToken() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_TOKEN_REFRESH);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("token", token));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        //httppost.setHeader("Authorization","JWT "+token);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void completeOrder(final String order_id) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_ORDER_COMPLETE);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("token", token));
                        nameValuePairs.add(new BasicNameValuePair("sg_id", order_id));

                        httppost.setHeader("Authorization","JWT "+token);

                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void cancelOrder(final String order_id) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_CANCEL_ORDER);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("token", token));
                        nameValuePairs.add(new BasicNameValuePair("sg_id", order_id));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        httppost.setHeader("Authorization","JWT "+token);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

  /*  public static String postData(String order_id) {

        String downloadedString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://172.17.38.252:8000/shop/complete_order/");

        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
            nameValuePairs.add(new BasicNameValuePair("token", token));
            nameValuePairs.add(new BasicNameValuePair("sg_id", order_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httppost.setHeader("Authorization", "JWT " + token);

            HttpResponse response = httpclient.execute(httppost);

            InputStream in = response.getEntity().getContent();
            StringBuilder stringbuilder = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
            String line;
            while ((line = bfrd.readLine()) != null)
                stringbuilder.append(line);
            downloadedString = stringbuilder.toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        System.out.println("downloadedString:in login:::" + downloadedString);
        return downloadedString;
    }*/
}