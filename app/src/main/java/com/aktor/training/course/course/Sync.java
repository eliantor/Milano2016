package com.aktor.training.course.course;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by aktor on 22/09/16.
 */

public class Sync extends Service {


    private static class Synchronizer extends AbstractThreadedSyncAdapter{


        public Synchronizer(Context context) {
            super(context, true,false);
        }

        @Override
        public void onPerformSync(Account account,
                                  Bundle extras,
                                  String authority,
                                  ContentProviderClient provider,
                                  SyncResult syncResult) {

            syncResult.fullSyncRequested=true;


        }
    }

    private Synchronizer mSynch;

    @Override
    public void onCreate() {
        super.onCreate();
        mSynch = new Synchronizer(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSynch.getSyncAdapterBinder();
    }
}
