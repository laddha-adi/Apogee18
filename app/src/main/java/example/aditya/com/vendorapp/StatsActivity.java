package example.aditya.com.vendorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static example.aditya.com.vendorapp.URLs.stallID;

public class StatsActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    String VendorID = stallID;
    ArrayList<StatsItem> orderArrayList  = new ArrayList<>();
    ArrayList<StatsItem> newArrayList  = new ArrayList<>();
    ArrayList<Long> idList = new ArrayList<>();
    RecyclerView statsList;
    StatsAdapter adapter;
    TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statsList = findViewById(R.id.stats_list);
        orderArrayList = new ArrayList<>();
        final StatsAdapter adapter = new StatsAdapter(this, newArrayList);
        statsList.setAdapter(adapter);
        statsList.setLayoutManager(new LinearLayoutManager(this));
        total = findViewById(R.id.total);
        mDatabase  = FirebaseDatabase.getInstance().getReference().child("stall");
                //.child(VendorID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderArrayList.clear();
                newArrayList.clear();
                idList.clear();
                try {
                    for(DataSnapshot vendors : dataSnapshot.getChildren()) {

                        for (DataSnapshot orders : vendors.getChildren()) {
                            boolean isReady = (boolean) orders.child("order_ready").getValue();
                            boolean isComplete = (boolean) orders.child("order_complete").getValue();
                            boolean isCancelled = (boolean) orders.child("cancelled").getValue();


                            if ((boolean) orders.child("order_complete").getValue() == true)

                            {
                                for (DataSnapshot item : orders.child("sales").getChildren()) {

                                    String name = (String) item.child("product").child("name").getValue();
                                    long id = (Long) item.child("product").child("id").getValue();
                                    long quantity = (Long) item.child("quantity").getValue();
                                    long price = (Long) item.child("product").child("price").getValue();
                                    orderArrayList.add(new StatsItem(name, quantity, price, id));
                                    adapter.notifyDataSetChanged();

                                }
                            }

                        }
                   }

                    for(int i=0;i<orderArrayList.size();i++){
                       if(idList.contains(orderArrayList.get(i).getId())){
                          int index =  idList.indexOf(orderArrayList.get(i).getId());
                            newArrayList.get(index).setQuantity(newArrayList.get(index).getQuantity()+orderArrayList.get(i).getQuantity());
                       }
                       else{
                           idList.add(orderArrayList.get(i).getId());
                           newArrayList.add(orderArrayList.get(i));
                       }
                    }
                    adapter.notifyDataSetChanged();

                    calculateTotal();
                    Log.e("All Items", String.valueOf(orderArrayList));
                } catch (Exception e) {
                     e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("databaseError", databaseError.getMessage());
            }
        });




    }

    private void calculateTotal() {
        long total_int = 0;
       for(int i=0;i<newArrayList.size();i++){
           total_int = total_int + (newArrayList.get(i).getPrice()*newArrayList.get(i).getQuantity());
       }
       total.setText(String.valueOf(total_int));
    }
}