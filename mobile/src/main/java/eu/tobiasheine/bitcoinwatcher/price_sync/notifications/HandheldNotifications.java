package eu.tobiasheine.bitcoinwatcher.price_sync.notifications;

import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import eu.tobiasheine.bitcoinwatcher.R;
import eu.tobiasheine.bitcoinwatcher.api.dto.BitcoinPriceDTO;
import eu.tobiasheine.bitcoinwatcher.widget.BitcoinWatcherWidgetProvider;

public class HandheldNotifications {

    private final NotificationCompat.Builder builder;
    private final NotificationManager notificationManager;

    private final Context context;

    public HandheldNotifications(Context context) {
        this.builder = new NotificationCompat.Builder(context);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
    }

    public void notifyAboutNewPrice(final BitcoinPriceDTO bitcoinPriceDTO) {
        builder.setContentTitle("Bitcoin price update")
                .setContentText("New Price: " + bitcoinPriceDTO.getBpi().getEur().rate)
                .setSmallIcon(R.drawable.ic_launcher)
                .setVibrate(new long[] {0, 1000, 50, 2000});

        notificationManager.notify(1, builder.build());
    }

    public void notifyWidget() {
        Intent intent = new Intent(context, BitcoinWatcherWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, BitcoinWatcherWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(intent);
    }
}
