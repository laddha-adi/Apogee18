package example.aditya.com.vendorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static example.aditya.com.vendorapp.Communicator.WALLET_TOKEN;
import static example.aditya.com.vendorapp.URLs.PASSWORD;
import static example.aditya.com.vendorapp.URLs.URL_LOGIN;
import static example.aditya.com.vendorapp.URLs.URL_STALL_ID;
import static example.aditya.com.vendorapp.URLs.USERNAME;
import static example.aditya.com.vendorapp.URLs.stallID;
import static example.aditya.com.vendorapp.URLs.stallName;


public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static ProgressDialog pd;
    private Thread th;
    private boolean flag = false;
    public static String email;
    public static String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("username","");
        String password = sharedPref.getString("password","");
       // boolean f = getIntent().getBooleanExtra("flag",true);
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Logging In...");
        pd.dismiss();
        if(!(username.equals(""))&&(!password.equals("")) ){
            pd.show();
            Log.e("SharedPref","data present");
            mEmailView.setText(username);
            mPasswordView.setText(password);
         //  if(f) startPosting(username,password);
        }
        else{
            Log.e("SharedPref","data not present");

        }

//
        if(pd.isShowing()){pd.dismiss();}
        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEmailView.getText().toString();
                pass = mPasswordView.getText().toString();
                    startPosting(mEmailView.getText().toString(),mPasswordView.getText().toString());

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    void startPosting(final String username, final String password){

                pd.show();
                Login(username,password);

}

    public void Login(final String userID, final String password) {

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
                      //  Toast.makeText(LoginActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    try{
                        JSONObject mainObject = new JSONObject(downloadedString);
                        if(mainObject.get("token")!=null){
                            PASSWORD = password;
                            USERNAME = userID;
                            Log.e("TAG","User Successfully LoggedIn");
                            getStallID(String.valueOf(mainObject.get("token")));
                        }
                        else{
                         //   Toast.makeText(LoginActivity.this,downloadedString , Toast.LENGTH_SHORT).show();
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

    public void getStallID(final String token) {

         th = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String downloadedString = null;

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(URL_STALL_ID);

                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                        nameValuePairs.add(new BasicNameValuePair("token", token));
                        nameValuePairs.add(new BasicNameValuePair("WALLET_TOKEN", WALLET_TOKEN));
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
                    try{
                        JSONObject mainObject = new JSONObject(downloadedString);
                        if(mainObject.get("id")!=null){
                            stallID = String.valueOf(mainObject.get("id"));
                            stallName = String.valueOf(mainObject.get("name"));
                           Log.e("TAG","Stall ID Successfully fetched");
                            g= true;

                            pd.dismiss();
                            moveToMain();
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid Credentials. Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){

                    }
                    System.out.println("downloadedString:in getStallToken:::" + downloadedString);



                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(g) moveToMain();
            }
        });

        th.start();
    }
    boolean g=false;

    public void moveToMain(){
        pd.dismiss();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", mEmailView.getText().toString());
        editor.putString("password", mPasswordView.getText().toString());
        editor.apply();

        Intent intent = new Intent(getBaseContext(),HomeActivity.class);
        startActivity(intent);
       // finish();
    }
}

