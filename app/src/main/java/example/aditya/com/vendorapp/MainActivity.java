package example.aditya.com.vendorapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    public static ArrayList<Order> orderArrayList;
    private String insertData;
    int num = 1;
    String VendorID="1";
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        fab = findViewById(R.id.fab_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view_recycler_view);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        adapter = new RecyclerViewAdapter(this,orderArrayList);
        mRecyclerView.setAdapter(adapter);
        orderArrayList = new ArrayList<>();
      //  adapter.setItems(orderArrayList);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    initData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private void initData() {
        orderArrayList = new ArrayList<>();
        orderArrayList.clear();
        insertData = "0";
        mDatabase.child("stall").child(VendorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderArrayList.clear();

                        Log.e("Numbers of orders: " ,String.valueOf(dataSnapshot.getChildrenCount()));
                for(DataSnapshot orders : dataSnapshot.getChildren()) {

                    String orderID = String.valueOf(orders.child("id").getValue());
                    String unique_code = String.valueOf(orders.child("unique_code").getValue());

                    ArrayList<Item> itemArrayList = new ArrayList<>();

                    for(DataSnapshot order_item : orders.child("sales").getChildren()) {

                        String name = (String) order_item.child("product").child("name").getValue();
                        long quantity = (Long) order_item.child("quantity").getValue();
                        itemArrayList.add(new Item(name, quantity));
                    }

                    orderArrayList.add(new Order(itemArrayList,orderID,unique_code));
                    Log.e("order List refreshed:",String.valueOf(orderArrayList.get(0).getItemArrayList().get(0)));
                    adapter.notifyDataSetChanged();
                }
              //  adapter.notifyDataSetChanged();
                adapter.setItems(orderArrayList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
     }

 /*   private void initView() {
        fab = findViewById(R.id.fab_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view_recycler_view);

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);


      //  adapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setItems(orderArrayList);
       // Toast.makeText(this, String.valueOf(data.size()), Toast.LENGTH_LONG).show();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                adapter.addItem(data.size(),String.valueOf(num));
                num++;
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
*/









}
