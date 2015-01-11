package eu.tobiasheine.bitcoinwatcher;

import android.app.Application;

import eu.tobiasheine.bitcoinwatcher.price_sync.Synchronization;
import eu.tobiasheine.bitcoinwatcher.settings.Settings;

public class BitcoinWatcherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Synchronization synchronization = new Synchronization(this);
        final Settings settings = new Settings(this);

        synchronization.syncNow();
        synchronization.syncPeriodic(settings.getSyncIntervalInMinutes());
    }
}
