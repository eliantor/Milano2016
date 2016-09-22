package com.aktor.training.course.course;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

/**
 * Created by aktor on 22/09/16.
 */

public class SyncClient {

    private void sampleSynchRequests(Context context){

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,true);

        ContentResolver.setSyncAutomatically(
                AuthenticatorFake.FAKE,GeoNotesProvider.AUTHORITY,true
        );

        ContentResolver.addPeriodicSync(AuthenticatorFake.FAKE,GeoNotesProvider.AUTHORITY,
                Bundle.EMPTY, TimeUnit.DAYS.toMillis(1));

        ContentResolver.requestSync(null,null,Bundle.EMPTY);
    }
}
