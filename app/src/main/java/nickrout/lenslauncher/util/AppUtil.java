package nickrout.lenslauncher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nickrout.lenslauncher.model.App;

/**
 * Created by nickrout on 2016/04/02.
 */
public class AppUtil {

    // Get all available apps for launcher
    public static ArrayList<App> getApps(PackageManager packageManager) {
        ArrayList<App> apps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
        for(ResolveInfo resolveInfo : availableActivities){
            App app = new App();
            app.setLabel(resolveInfo.loadLabel(packageManager));
            app.setName(resolveInfo.activityInfo.packageName);
            apps.add(app);
        }
        Collections.sort(apps, new Comparator<App>() {
            @Override
            public int compare(App appOne, App appTwo) {
                return appOne.getLabel().toString().compareToIgnoreCase(appTwo.getLabel().toString());
            }
        });
        return apps;
    }

    // Launch apps, for launcher :-P
    public static void launchApp(Context context, PackageManager packageManager, String appName) {
        Intent appIntent = packageManager.getLaunchIntentForPackage(appName);
        context.startActivity(appIntent);
    }

    public static App getApp(PackageManager packageManager, String packageName) {
        App app = new App();
        app.setName(packageName);
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Resources resources = packageManager.getResourcesForApplication(applicationInfo);
            app.setLabel(applicationInfo.loadLabel(packageManager));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return app;
    }
}
