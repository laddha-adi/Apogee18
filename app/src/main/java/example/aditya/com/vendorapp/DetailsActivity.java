package example.aditya.com.vendorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static example.aditya.com.vendorapp.MainActivity.orderArrayList;

public class DetailsActivity extends AppCompatActivity {

    TextView order_unique_id;
    RecyclerView list;
    int position;
    Button cancel_order;
    Button order_ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        order_unique_id = (TextView) findViewById(R.id.order_id);
        list = (RecyclerView) findViewById(R.id.list);

        cancel_order = (Button) findViewById(R.id.cancel_btn);
        order_ready = (Button) findViewById(R.id.ready_btn);


        if(getSupportActionBar()!=null)  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     position = getIntent().getIntExtra("order_num",-1);
     Order order = orderArrayList.get(position);
     order_unique_id.setText(order.getUniqueId());

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                list.getContext(),
                DividerItemDecoration.VERTICAL
        );
        list.addItemDecoration(mDividerItemDecoration);

      DetailsListAdapter detailsListAdapter = new DetailsListAdapter(this, order.getItemArrayList());
      list.setLayoutManager(new LinearLayoutManager(this));
      list.setAdapter(detailsListAdapter);

      cancel_order.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              cancelButtonClicked();
          }
      });

      order_ready.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              readyButtonClicked();
          }
      });

    }

    private void readyButtonClicked() {
       // startPosting();
    }

    private void cancelButtonClicked() {
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
