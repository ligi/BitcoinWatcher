package eu.tobiasheine.bitcoinwatcher.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import eu.tobiasheine.bitcoinwatcher.R;
import eu.tobiasheine.bitcoinwatcher.settings.Settings;
import eu.tobiasheine.bitcoinwatcher.dao.storage.Storage;
import eu.tobiasheine.bitcoinwatcher.widget.ui.BitcoinWatcherViewModelFactory;
import eu.tobiasheine.bitcoinwatcher.widget.ui.BitcoinWatcherWidgetViewModel;

public class BitcoinWatcherWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final Settings settings = new Settings(context);
        final Storage storage = new Storage(context);

        final BitcoinWatcherWidgetViewModel viewModel = BitcoinWatcherViewModelFactory.create(context, settings, storage.getStoredPrice());

        final String currentPrice = viewModel.getCurrentPrice();
        final String updatedAt = viewModel.getUpdatedAt();

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bitcoin_watcher_appwidget);

        views.setTextViewText(R.id.tvCurrentPrice, currentPrice);
        views.setTextViewText(R.id.tvUpdatedAt, updatedAt);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
