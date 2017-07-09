package com.tincio.capstoneproject.presentation.widget;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static com.tincio.capstoneproject.data.provider.SoccerFieldContract.BASE_CONTENT_URI;
import static com.tincio.capstoneproject.data.provider.SoccerFieldContract.PATH_FIELD;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PichangaWidgetService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    public static final String ACTION_RECIPE = "com.tincio.capstoneproject.presentation.widget.action.FIELD";

    public PichangaWidgetService() {
        super("PichangaWidgetService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionRecipe(Context context) {
        Intent intent = new Intent(context, PichangaWidgetService.class);
        intent.setAction(ACTION_RECIPE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RECIPE.equals(action)) {
                handleActionRecipe();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRecipe() {
        // TODO: Handle action Foo
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FIELD).build();
        ContentValues contentValues = new ContentValues();
        try{
            getContentResolver().update(
                    RECIPE_URI,
                    contentValues,
                    null,
                    null);
        }catch (Exception e){
            System.out.println("esception "+e.getMessage());
        }

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
}
