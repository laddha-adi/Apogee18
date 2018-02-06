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
    private ArrayList<Order> orderArrayList;
    private String insertData;
    int num = 1;
    String VendorID="vendorIdXYZABC";
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        initData();
    }

    private void initData() {
        orderArrayList = new ArrayList<>();
        orderArrayList.clear();
        insertData = "0";
        mDatabase.child("Orders").child(VendorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot orders : dataSnapshot.getChildren()) {
                    String id = new String();
                    ArrayList<Item> itemArrayList = new ArrayList<>();


                            for (DataSnapshot orderItem : orders.getChildren()) {
                                Log.e("yo man",String.valueOf(orderItem.getKey()));

                                if(orderItem.getKey().equals("ID")){
                                    id = (String) orderItem.getValue();
                                    Log.e("ID",id+"");
                                }
                                else{
                                String name = (String) orderItem.child("name").getValue();
                                Log.e("name",name+"error");
                                //int quantity = (int)(long)orderItem.child("quan").getValue();
                                int quantity = 1;
                                itemArrayList.add(new Item(name, quantity));
                                Log.e("item",(new Item(name,quantity)).toString());
                            }
                            }

                        orderArrayList.add(new Order(itemArrayList,id));
                }
                initView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
     }

    private void initView() {
        fab = findViewById(R.id.fab_recycler_view);
        mRecyclerView = findViewById(R.id.recycler_view_recycler_view);

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);


        adapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
        //data.clear();
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
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


    }





}
