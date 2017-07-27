package com.frick.maximilian.agratamagatha;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartFragment extends Fragment {
   public static final long END_DATE = 1501452000000L;
   @BindView (R.id.countdown)
   LinearLayout countDownView;
   @BindView (R.id.countdown_days)
   TextView days;
   @BindView (R.id.countdown_hours)
   TextView hours;
   @BindView (R.id.live_view)
   TextView liveView;
   @BindView (R.id.countdown_minutes)
   TextView minutes;
   private Calendar timeLeft;

   public static Fragment newInstance() {
      return new StartFragment();
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActivity().getWindow()
            .getDecorView()
            .setSystemUiVisibility(
                  View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
         @Nullable Bundle savedInstanceState) {
      getActivity().getWindow()
            .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      View view = inflater.inflate(R.layout.start_fragment, container, false);
      ButterKnife.bind(this, view);
      return view;
   }

   @Override
   public void onResume() {
      super.onResume();
   }

   @Override
   public void onStop() {
      super.onStop();
   }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      handleCountdown();
   }

   private void handleCountdown() {
      Calendar dateOfFestival = Calendar.getInstance();
      dateOfFestival.setTimeInMillis(1501192800000L);
      Calendar now = Calendar.getInstance();
      long difference = dateOfFestival.getTimeInMillis() - System.currentTimeMillis();
      timeLeft = Calendar.getInstance();
      new CountDownTimer(difference, 5000) {

         @Override
         public void onFinish() {
            countDownView.setVisibility(View.GONE);
            liveView.setVisibility(View.VISIBLE);
            checkIfFestivalIsOver();
         }

         @Override
         public void onTick(long millisUntilFinished) {
            timeLeft.setTimeInMillis(millisUntilFinished);
            long diff = millisUntilFinished / 1000;
            int secss = (int) (diff % 60);
            diff = diff / 60;

            int minss = (int) (diff % 60);
            diff = diff / 60;

            int hourss = (int) (diff % 24);
            int dayss = (int) (diff / 24);
            days.setText(String.valueOf(dayss));
            hours.setText(String.valueOf(hourss));
            minutes.setText(String.valueOf(minss));
         }
      }.start();
   }

   private void checkIfFestivalIsOver() {
      if (System.currentTimeMillis() > END_DATE) {
         liveView.setText("VORBEI");
      }
   }

   @OnClick (R.id.headliner_5)
   void onHeadlinerFiveClicked() {
      String url = "http://agratamagatha.de/2017/artist/sparkling/";
      startBandInfo(url);
   }

   @OnClick (R.id.headliner_4)
   void onHeadlinerFourClicked() {
      String url = "http://agratamagatha.de/2017/artist/die-meute/";
      startBandInfo(url);
   }

   @OnClick (R.id.headliner_1)
   void onHeadlinerOneClicked() {
      String url = "http://agratamagatha.de/2017/artist/dorian-concept/";
      startBandInfo(url);
   }

   @OnClick (R.id.headliner_6)
   void onHeadlinerSixClicked() {
      String url = "http://agratamagatha.de/2017/artist/sepalot/";
      startBandInfo(url);
   }

   @OnClick (R.id.headliner_3)
   void onHeadlinerThreeClicked() {
      String url = "http://agratamagatha.de/2017/artist/isolation-berlin/";
      startBandInfo(url);
   }

   @OnClick (R.id.headliner_2)
   void onHeadlinerTwoClicked() {
      String url = "http://agratamagatha.de/2017/artist/lady-chann/";
      startBandInfo(url);
   }

   @OnClick (R.id.btn_more_bands)
   void onMoreClicked() {
      ((TopLevelActivity) getActivity()).handleNavigationItemClicked(R.id.navigation_lineup);
   }

   private void startBandInfo(String url) {
      CustomTabsIntent intent = MyCustomTabsBuilder.createCustomTabBuilder(getActivity());
      intent.launchUrl(getContext(), Uri.parse(url));
   }
}
