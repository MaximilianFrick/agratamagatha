package com.frick.maximilian.agratamagatha;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

public class MyCustomTabsBuilder {
   public static CustomTabsIntent createCustomTabBuilder(Activity activity) {
      CustomTabsIntent intent;
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      prepareMenuItems(builder, activity);
      prepareToolbar(builder, activity);
      builder.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
      builder.setExitAnimations(activity, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right);
      intent = builder.build();
      addReferrer(intent, activity);
      return intent;
   }

   private static void prepareMenuItems(CustomTabsIntent.Builder builder, Activity activity) {
      builder.addDefaultShareMenuItem();
      Intent menuIntent = new Intent();
      menuIntent.setClass(activity.getApplicationContext(), activity.getClass());
      Bundle menuBundle = ActivityOptions.makeCustomAnimation(activity, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
            .toBundle();
      PendingIntent pendingIntent =
            PendingIntent.getActivity(activity.getApplicationContext(), 0, menuIntent, 0, menuBundle);
      builder.addMenuItem("menu", pendingIntent);
   }

   private static void prepareToolbar(CustomTabsIntent.Builder builder, Activity activity) {
      builder.enableUrlBarHiding();
      builder.setShowTitle(true);
      builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
      builder.setCloseButtonIcon(
            BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_arrow_back));
   }

   private static void addReferrer(CustomTabsIntent intent, Activity activity) {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
         return;
      }
      intent.intent.putExtra(Intent.EXTRA_REFERRER,
            Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));
   }
}
