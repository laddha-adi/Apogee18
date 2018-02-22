package example.aditya.com.vendorapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static example.aditya.com.vendorapp.MainFragment.orderArrayList;
import static example.aditya.com.vendorapp.URLs.PASSWORD;
import static example.aditya.com.vendorapp.URLs.*;
import static example.aditya.com.vendorapp.URLs.URL_LOGIN;
import static example.aditya.com.vendorapp.URLs.URL_ORDER_COMPLETE;
import static example.aditya.com.vendorapp.URLs.USERNAME;
import static example.aditya.com.vendorapp.URLs.WALLET_TOKEN;

public class DetailsActivity extends AppCompatActivity {

    TextView order_unique_id;
    RecyclerView list;
    int position;
    Button cancel_order;
    Button order_ready;
    Button order_complete;
    String stall_ID;
    Order order;
    ProgressDialog progressDialog;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        order_unique_id = (TextView) findViewById(R.id.order_id);
        status = (TextView) findViewById(R.id.order_status);

        list = (RecyclerView) findViewById(R.id.list);

        cancel_order = (Button) findViewById(R.id.cancel_btn);
        order_ready = (Button) findViewById(R.id.ready_btn);
        order_complete = (Button) findViewById(R.id.comp_btn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request");



        if(getSupportActionBar()!=null)  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     position = getIntent().getIntExtra("order_num",-1);
    // order = getOrder(position);
     order = orderArrayList.get(position);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                list.getContext(),
                DividerItemDecoration.VERTICAL
        );
        list.addItemDecoration(mDividerItemDecoration);

      DetailsListAdapter detailsListAdapter = new DetailsListAdapter(this, order.getItemArrayList());
      list.setLayoutManager(new LinearLayoutManager(this));
      list.setAdapter(detailsListAdapter);
        UpdateUI();
      cancel_order.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
try {
    new AlertDialog.Builder(DetailsActivity.this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to Cancel this order?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    //Toast.makeText(DetailsActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                    progressDialog.show();
                    cancelButtonClicked();
                }
            })
            .setNegativeButton("No", null).show();
}
catch(Exception e){}
          }
      });

        order_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new AlertDialog.Builder(DetailsActivity.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to Complete this order?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Toast.makeText(DetailsActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                                    progressDialog.show();
                                    realcompleteButtonClicked();
                                }
                            })
                            .setNegativeButton("No", null).show();
                }
                catch(Exception e){}
            }
        });


        order_ready.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
try{
              new AlertDialog.Builder(DetailsActivity.this)
                      .setTitle("Confirmation")
                      .setMessage("Are you sure the order is ready?")
                      .setIcon(android.R.drawable.ic_dialog_alert)
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                          public void onClick(DialogInterface dialog, int whichButton) {
                              //Toast.makeText(DetailsActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                              progressDialog.show();
                              completeButtonClicked();
                          }
                      })
                      .setNegativeButton("No", null).show();
          }
catch(Exception e){}

          }
      });

    }
   void UpdateUI(){
        order_unique_id.setText(order.getOrderId());
      if(order.isReady()) {
          status.setText("Ready");
          status.setTextColor(ContextCompat.getColor(this, R.color.blue));
          order_ready.setEnabled(false);
          cancel_order.setEnabled(false);

          order_complete.setVisibility(View.VISIBLE);
          order_complete.setEnabled(true);
      }
      else{
          status.setText("Pending");
          status.setTextColor(ContextCompat.getColor(this, R.color.yellow));
          order_ready.setEnabled(true);
          cancel_order.setEnabled(true);
          order_complete.setVisibility(View.INVISIBLE);
          order_complete.setEnabled(false);

      }
    }


    private void completeButtonClicked() {
        orderCompleteInitiator(order.getID(),URL_ORDER_COMPLETE);
    }

    private void cancelButtonClicked() {
        orderCompleteInitiator(order.getID(),URL_CANCEL_ORDER);

    }
    private void realcompleteButtonClicked() {
        realorderCompleteInitiator(order.getID(),URL_ORDER_COMPLETE);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
/*
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
                        Log.e("Error1",e.toString());
                    } catch (IOException e) {
                        Log.e("Error2",e.toString());
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void cancelOrder(final String stallNo , final String order_id) {

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


    }*/


    public void orderCompleteInitiator(final String order_id, final String url) {

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
                            completeOrder(tok, order_id, url);

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

    public void completeOrder(final String tok, final String order_id, final String url) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("token", tok));
                        nameValuePairs.add(new BasicNameValuePair("sg_id", order_id));
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

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);
                    //progressDialog.hide();
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    public void realorderCompleteInitiator(final String order_id, final String url) {

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
                            realcompleteOrder(tok, order_id, url);

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

    public void realcompleteOrder(final String tok, final String order_id, final String url) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URLCcmp);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
                        nameValuePairs.add(new BasicNameValuePair("token", tok));
                        nameValuePairs.add(new BasicNameValuePair("sg_id", order_id));
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

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                    }
                    System.out.println("downloadedString:in login:::" + downloadedString);
                    //progressDialog.hide();
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }


}
