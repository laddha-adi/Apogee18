package example.aditya.com.vendorapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static example.aditya.com.vendorapp.URLs.stallID;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setListeners();
    }

    void setListeners(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("stall").child(stallID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void setChildListeners(final DatabaseReference ref, final String id){


        ref.child(id).child("stallgroup").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    boolean cancelled = Boolean.valueOf(dataSnapshot.child("cancelled").getValue().toString());
                    boolean order_ready = Boolean.valueOf(dataSnapshot.child("order_ready").getValue().toString());
                    if (cancelled ){
                        createNotification("Your recent order got cancelled");
                    }else if (order_ready){
                        createNotification("Your recent order is now ready");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void createNotification(String message){
        Intent intent = new Intent(this, LoginActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Log.e("Notification","Called");
        Notification n  = new Notification.Builder(this)
                .setContentTitle("BITS Emergency")
                .setContentText(message)
//                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
   //             .addAction(R.drawable.pay_icon, "View", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(0,n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_STICKY;
    }
}

