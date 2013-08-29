package io.catalyze.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by mvolkhart on 8/21/13.
 */
public class CatalyzeRegistry {

    public String getAcccessKeyFromManifest(Context context) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String accessKey = ai.metaData.getString("io.catalyze.android.sdk.v1.ACCESS_KEY");
        return accessKey;
    }
}
