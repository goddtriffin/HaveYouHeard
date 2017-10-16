package net.magnusfrater.haveyouheard;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by magnusfrater on 10/16/17.
 */

public class Utils {

    public static boolean isAppInstalled (Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
