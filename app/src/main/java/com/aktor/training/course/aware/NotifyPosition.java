package com.aktor.training.course.aware;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.aktor.training.course.R;
import com.aktor.training.course.gui.MainGuiActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by aktor on 22/09/16.
 */

public class NotifyPosition extends IntentService {
    private static final int GEO_NOTIFICATION = 1;
    private static final String TAG = "NotifyPosition";
    public NotifyPosition() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if (event.hasError()){
            int errorCode = event.getErrorCode();
            Log.d(TAG, "onHandleIntent: ");
            return;
        }

        List<Geofence> triggeringGeofences = event.getTriggeringGeofences();
        for (Geofence f: triggeringGeofences){
            String requestId = f.getRequestId();
            Location loc = event.getTriggeringLocation();
            Log.d(TAG, "onHandleIntent: "+requestId+" location: "+loc);


            /*
              ID, ID_GEOFENCE, latitudine, longitudine,raggio,active, .......
             */
            sendNotification();

        }
    }

    private void sendNotification(){
        NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this, MainGuiActivity.class);
        PendingIntent i = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent implicit = new Intent(Intent.ACTION_VIEW);
//        implicit.setDataAndType(Uri.parse("file:///a/"),"application/pdf");

        builder.setContentTitle("Geofence triggered")
                .setContentText("MESSAGGIO ESTESO")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setAutoCancel(true)
                .setContentIntent(i);

        manager.notify(GEO_NOTIFICATION,builder.build());

        /**
         * Se voglio cancellarla
         */
        //manager.cancel(GEO_NOTIFICATION);
        /**
         * Se ne mando un'altra con lo stesso id == aggiorno quella corrente
         */
    }
}
