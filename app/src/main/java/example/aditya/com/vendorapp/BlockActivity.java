package example.aditya.com.vendorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static example.aditya.com.vendorapp.URLs.PASSWORD;
import static example.aditya.com.vendorapp.URLs.URL_BLOCK_PRODUCT;
import static example.aditya.com.vendorapp.URLs.URL_LOGIN;
import static example.aditya.com.vendorapp.URLs.URL_STALL_PRODUCTS;
import static example.aditya.com.vendorapp.URLs.USERNAME;
import static example.aditya.com.vendorapp.URLs.WALLET_TOKEN;
import static example.aditya.com.vendorapp.URLs.stallID;

public class BlockActivity extends AppCompatActivity {
    static RecyclerView AvailableRecyclerView;
    static BlockAvailableAdapter AvailableAdapter;
    static RecyclerView NotAvailableRecyclerView;
    static BlockAdapter NotAvailableAdapter;
    static ArrayList<MenuItems> availbaleList;
    static ArrayList<MenuItems> notAvailbaleList;
    static ProgressDialog progressDialog;
    static Context ctx;
    public static Handler UIHandler;
    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        availbaleList= new ArrayList<>();
        notAvailbaleList= new ArrayList<>();

        AvailableRecyclerView = findViewById(R.id.avb_block_list);
        AvailableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NotAvailableRecyclerView = findViewById(R.id.navb_block_list);
        NotAvailableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(getSupportActionBar()!=null)  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AvailableAdapter = new BlockAvailableAdapter(this,availbaleList);
        AvailableRecyclerView.setAdapter(AvailableAdapter);

        NotAvailableAdapter = new BlockAdapter(this,notAvailbaleList);
        NotAvailableRecyclerView.setAdapter(NotAvailableAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                AvailableRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        AvailableRecyclerView.addItemDecoration(mDividerItemDecoration);

        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
                NotAvailableRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        NotAvailableRecyclerView.addItemDecoration(mDividerItemDecoration2);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        ctx = getApplicationContext();
        getMenuInitiator();
      //  parseJSON(d);
}

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static void getMenuInitiator() {
progressDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_LOGIN);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("username", USERNAME));
                        nameValuePairs.add(new BasicNameValuePair("password", PASSWORD));

                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        // httppost.setHeader("Authorization","JWT "+token);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        Log.e("Error1",e.toString());
                    } catch (IOException e) {
                        Log.e("Error2",e.toString());
                    }

                    try{
                        JSONObject mainObject = new JSONObject(downloadedString);
                        if(mainObject.get("token")!=null){
                            String tok = String.valueOf(mainObject.get("token"));
                            getMenu(tok);

                        }
                    }catch (Exception e){

                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    public static void getMenu(final String tok) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_STALL_PRODUCTS+stallID+"/");

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("token", tok));
                         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        httppost.setHeader("Authorization","JWT "+tok);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();
                        parseJSON(downloadedString);
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);
                    //progressDialog.hide();
                   // finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    static void parseJSON(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray("products");
            availbaleList.clear();
            notAvailbaleList.clear();

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonobject = array.getJSONObject(i);
                String name = jsonobject.getString("name");
                boolean isAvailable = jsonobject.getBoolean("is_available");
                int id = jsonobject.getInt("id");
                MenuItems item = new MenuItems(name,isAvailable,id);
                if(isAvailable) availbaleList.add(item);
                else notAvailbaleList.add(item);

               // adapter.notifyDataSetChanged();
                Log.d("stock", item.toString());
            }
            BlockActivity.runOnUI(new Runnable() {
                @Override
                public void run() {

                    AvailableAdapter = new BlockAvailableAdapter(ctx,availbaleList);
                    AvailableRecyclerView.setAdapter(AvailableAdapter);

                    NotAvailableAdapter = new BlockAdapter(ctx,notAvailbaleList);
                    NotAvailableRecyclerView.setAdapter(NotAvailableAdapter);

                    if(progressDialog.isShowing()) progressDialog.dismiss();
                }
            });




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void toggleInitiator(final int id) {
if(progressDialog!=null) progressDialog.show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_LOGIN);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("username", USERNAME));
                        nameValuePairs.add(new BasicNameValuePair("password", PASSWORD));

                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        // httppost.setHeader("Authorization","JWT "+token);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();

                    } catch (ClientProtocolException e) {
                        Log.e("Error1",e.toString());
                    } catch (IOException e) {
                        Log.e("Error2",e.toString());
                    }

                    try{
                        JSONObject mainObject = new JSONObject(downloadedString);
                        if(mainObject.get("token")!=null){
                            String tok = String.valueOf(mainObject.get("token"));
                            toggle(tok,id);

                        }
                    }catch (Exception e){

                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    public static void toggle(final String tok, final int id) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_BLOCK_PRODUCT);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("token", tok));
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("p_id", String.valueOf(id)));

                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        httppost.setHeader("Authorization","JWT "+tok);
                        HttpResponse response = httpclient.execute(httppost);
                        InputStream in = response.getEntity().getContent();
                        StringBuilder stringbuilder = new StringBuilder();
                        BufferedReader bfrd = new BufferedReader(new InputStreamReader(in), 1024);
                        String line;
                        while ((line = bfrd.readLine()) != null)
                            stringbuilder.append(line);
                        downloadedString = stringbuilder.toString();
                        getMenuInitiator();

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


}
