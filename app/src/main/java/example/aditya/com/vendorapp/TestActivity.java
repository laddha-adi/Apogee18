package example.aditya.com.vendorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static example.aditya.com.vendorapp.Communicator.Login;
import static example.aditya.com.vendorapp.Communicator.cancelOrder;
import static example.aditya.com.vendorapp.Communicator.completeOrder;
import static example.aditya.com.vendorapp.Communicator.updateToken;


public class TestActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button1 = (Button) findViewById(R.id.btn_1);
        button2 = (Button) findViewById(R.id.btn_2);
        button3 = (Button) findViewById(R.id.btn_3);
        button4 = (Button) findViewById(R.id.btn_4);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToken();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String order_id = "8";
           completeOrder(order_id);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String order_id = "8";
                cancelOrder(order_id);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = "tushar";
                String Password = "qwertyuiop";
                Login(userId,Password);
            }
        });
    }
}
