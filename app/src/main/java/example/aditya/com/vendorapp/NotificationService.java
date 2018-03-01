package example.aditya.com.vendorapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setListeners();

    }


    void setListeners(){
        try {
            String vendorId = getApplicationContext().getSharedPreferences("default", MODE_PRIVATE).getString("id", "");



            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("stall").child(vendorId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        setChildListeners(s.getRef());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    void setChildListeners(final DatabaseReference ref){

        ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    boolean isComplete = (boolean)(dataSnapshot.child("order_complete").getValue());
                    boolean cancelled =(boolean)(dataSnapshot.child("cancelled").getValue());
                    boolean order_ready = (boolean)(dataSnapshot.child("order_ready").getValue());

                    if ( ((!isComplete) && (!cancelled) && (!order_ready) )){
                        String id = (String)dataSnapshot.child("orderid").getValue();
                        String ids = getApplicationContext().getSharedPreferences("default", MODE_PRIVATE).getString("ids", "");
                        List<String> list = new ArrayList<String>(Arrays.asList(ids.split(" , ")));
                        if (!list.contains(id)) {
                            createNotification("New Order available");
                            Log.e("NOTI. id", id);
                            if(list.size() != 0) { ids = ids + "," + id;}
                            else{
                                ids = id;
                            }
                            SharedPreferences sh = getApplicationContext().getSharedPreferences("default", MODE_PRIVATE);
                            sh.edit().putString("id", ids).apply();
                        }
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
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("New Order available")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setSound(notificationSound)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(0,n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        // Log.e("ID",id);
        return Service.START_STICKY;
    }
}

