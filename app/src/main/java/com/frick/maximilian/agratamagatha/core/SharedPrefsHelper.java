package com.frick.maximilian.agratamagatha.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.frick.maximilian.agratamagatha.timetable.models.Day;
import com.frick.maximilian.agratamagatha.timetable.models.Gig;
import com.frick.maximilian.agratamagatha.timetable.models.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsHelper {
   private static final String DAYS = "DAYS";
   private static final String PREFS_TAG = "prefs_tag";

   public static List<Day> getDaysFromSharedPreferences(Activity activity) {
      Gson gson = new Gson();
      SharedPreferences sharedPref = activity.getApplicationContext()
            .getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
      String json = sharedPref.getString(DAYS, null);
      Type type = new TypeToken<ArrayList<Day>>() {
      }.getType();
      ArrayList<Day> arrayList = gson.fromJson(json, type);
      if (arrayList == null) {
         arrayList = new ArrayList<>();
      }
      return arrayList;
   }

   public static void setDaysFromSharedPreferences(List<Day> days, Activity activity) {
      Gson gson = new Gson();
      SharedPreferences sharedPref = activity.getApplicationContext()
            .getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putString(DAYS, gson.toJson(days));
      editor.apply();
   }

   public static void updateDaysPreferences(Gig gig, Activity activity) {
      List<Day> days = getDaysFromSharedPreferences(activity);
      for (Day day : days) {
         for (Location location : day.getLocations()) {
            for (Gig currentGig : location.getGigs()) {
               if (currentGig.getBand()
                     .equalsIgnoreCase(gig.getBand())) {
                  currentGig.setNotify(true);
               }
            }
         }
      }
      setDaysFromSharedPreferences(days, activity);
   }
}
