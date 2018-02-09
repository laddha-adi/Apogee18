package example.aditya.com.vendorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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



public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);*/

        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmailView.getText().toString().equals("admin")&& mPasswordView.getText().toString().equals("password")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    startPosting(mEmailView.getText().toString(),mPasswordView.getText().toString());
                   // Toast.makeText(LoginActivity.this, "Incorrect UserId/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
void startPosting(final String username, final String password){
    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                postData(username,password);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    thread.start();
}
    public void postData(String username, String password) {
        String downloadedString= null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("");

        try {
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);

            InputStream in = response.getEntity().getContent();
            StringBuilder stringbuilder = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(in),1024);
            String line;
            while((line = bfrd.readLine()) != null)
                stringbuilder.append(line);

            downloadedString = stringbuilder.toString();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        System.out.println("downloadedString:in login:::"+downloadedString);
    }


}

