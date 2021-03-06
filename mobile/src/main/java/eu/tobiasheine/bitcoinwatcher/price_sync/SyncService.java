package eu.tobiasheine.bitcoinwatcher.price_sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class SyncService extends Service{

    private static final Object syncServiceLock = new Object();

    private SyncAdapter syncAdapter;
    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (syncServiceLock) {
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(Wearable.API)
                        .build();

                googleApiClient.connect();
            }

            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true, googleApiClient);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }
}
