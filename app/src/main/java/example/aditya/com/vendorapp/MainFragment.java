package example.aditya.com.vendorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static example.aditya.com.vendorapp.LoginActivity.pd;
import static example.aditya.com.vendorapp.URLs.stallID;


public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    public static ArrayList<Order> orderArrayList;
    private String insertData;
    int num = 1;
    String VendorID = stallID;
    DatabaseReference mDatabase;
    ImageView no_order;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_completed, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        mRecyclerView = rootView.findViewById(R.id.recycler_view_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        no_order = (ImageView) rootView.findViewById(R.id.imageView);

        adapter = new RecyclerViewAdapter(getContext(),orderArrayList);
        mRecyclerView.setAdapter(adapter);
        orderArrayList = new ArrayList<>();

        //pd.setMessage("Fetching Orders...");
        //  pd.show();

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
        return rootView;
    }

    private void initData() {
        orderArrayList = new ArrayList<>();
        orderArrayList.clear();
        insertData = "0";
        mDatabase.child("stall").child(VendorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              //  pd.show();
                orderArrayList.clear();

                Log.e("Numbers of orders: " ,String.valueOf(dataSnapshot.getChildrenCount()));
                for(DataSnapshot orders : dataSnapshot.getChildren()) {
                    try{
                        boolean isReady = (boolean)orders.child("order_ready").getValue();
                        boolean isComplete = (boolean)orders.child("order_complete").getValue();
                        boolean isCancelled = (boolean)orders.child("cancelled").getValue();

                        if((boolean)orders.child("cancelled").getValue()== false && (boolean)orders.child("order_complete").getValue()==false)
                        {            String orderID = String.valueOf(orders.child("id").getValue());
                            String unique_code = String.valueOf(orders.child("unique_code").getValue());
                            String ordId = String.valueOf(orders.child("orderid").getValue());

                            ArrayList<Item> itemArrayList = new ArrayList<>();

                            for(DataSnapshot order_item : orders.child("sales").getChildren()) {

                                String name = (String) order_item.child("product").child("name").getValue();
                                long quantity = (Long) order_item.child("quantity").getValue();
                                long price =0;
                                try {
                                     price = (Long) order_item.child("product").child("price").getValue();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }Log.e("fetched item",name+" "+quantity+" "+price);
                                itemArrayList.add(new Item(name,quantity,price));
                            }
                            orderArrayList.add(new Order(itemArrayList,orderID,unique_code,isReady,isComplete,ordId,isCancelled));
                            Log.e("order List refreshed:",String.valueOf(orderArrayList.get(0).getItemArrayList().get(0)));
                            adapter.notifyDataSetChanged();
                        }

                    }
                    catch(Exception e){

                    }}
                adapter.setItems(orderArrayList);
                UpdateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }

    void UpdateUI(){
        if(orderArrayList.isEmpty()){
            no_order.setVisibility(View.VISIBLE);
        }
        else{
            no_order.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
