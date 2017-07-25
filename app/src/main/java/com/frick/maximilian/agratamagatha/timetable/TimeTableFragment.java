package com.frick.maximilian.agratamagatha.timetable;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frick.maximilian.agratamagatha.NotificationPublisher;
import com.frick.maximilian.agratamagatha.R;
import com.frick.maximilian.agratamagatha.TopLevelActivity;
import com.frick.maximilian.agratamagatha.timetable.models.Day;
import com.frick.maximilian.agratamagatha.timetable.models.Gig;
import com.frick.maximilian.agratamagatha.timetable.models.Location;
import com.frick.maximilian.agratamagatha.timetable.models.TimeTableType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Notification.DEFAULT_ALL;

public class TimeTableFragment extends Fragment implements OnAlarmSetListener {
   private static final String ASSETS_TIMETABLE_JSON = "timetable.json";
   TimeTableAdapter adapter;
   @BindView (R.id.recyclerview)
   RecyclerView recyclerView;
   @BindView (R.id.toolbar)
   Toolbar toolbar;
   private List<Day> days;

   public static Fragment newInstance() {
      return new TimeTableFragment();
   }

   @Override
   public void onAlarmClicked(Gig gig, boolean isActive) {
      Toast.makeText(this.getContext(), gig.getBand() + " wurde als Erinnerung hinzugefügt!", Toast.LENGTH_SHORT)
            .show();
      scheduleNotification(getNotification(gig.getBand(), gig.getTime()), 5000);
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
         @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.timetable_fragment, container, false);
      ButterKnife.bind(this, view);
      initToolbar();
      return view;
   }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      loadDays();
      adapter = new TimeTableAdapter(generateItems(days), this);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(adapter);
   }

   private List<TimeTableType> generateItems(List<Day> days) {
      List<TimeTableType> items = new ArrayList<>();
      for (Day day : days) {
         items.add(day);
         for (Location location : day.getLocations()) {
            items.add(location);
            for (Gig gig : location.getGigs()) {
               items.add(gig);
            }
         }
      }
      return items;
   }

   private Notification getNotification(String title, String message) {
      Notification.Builder builder = new Notification.Builder(getActivity());
      builder.setContentTitle(title);
      builder.setContentText(message);
      builder.setDefaults(DEFAULT_ALL);
      builder.setAutoCancel(true);
      builder.setContentIntent(PendingIntent.getActivity(getActivity(), 0,
            new Intent(getActivity(), TopLevelActivity.class), PendingIntent.FLAG_ONE_SHOT));
      builder.setSmallIcon(R.drawable.ic_lineup);
      return builder.build();
   }

   private void initToolbar() {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      toolbar.setTitle(getString(R.string.title_timetable));
   }

   private void loadDays() {
      Gson gson = new Gson();
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(new InputStreamReader(this.getActivity()
               .getAssets()
               .open(ASSETS_TIMETABLE_JSON)));
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
      Type type = new TypeToken<List<Day>>() {
      }.getType();
      if (reader != null) {
         days = gson.fromJson(reader, type);
      }
   }

   private void scheduleNotification(Notification notification, int delay) {
      Intent notificationIntent = new Intent(getActivity(), NotificationPublisher.class);
      notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,
            (int) System.currentTimeMillis());
      notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
      PendingIntent pendingIntent =
            PendingIntent.getBroadcast(getActivity(), (int) System.currentTimeMillis(),
                  notificationIntent, PendingIntent.FLAG_ONE_SHOT);
      long futureInMillis = SystemClock.elapsedRealtime() + delay;
      AlarmManager alarmManager =
            (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
      alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
   }
}
