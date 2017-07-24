package com.frick.maximilian.agratamagatha;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartFragment extends Fragment {
   @BindView (R.id.countdown_days)
   TextView days;
   @BindView (R.id.countdown_hours)
   TextView hours;
   @BindView (R.id.countdown_minutes)
   TextView minutes;
   @OnClick(R.id.btn_more_bands)
   void onMoreClicked() {
      ((TopLevelActivity)getActivity()).handleNavigationItemClicked(R.id.navigation_lineup);
   }

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
      Calendar dateOfFestival = Calendar.getInstance();
      dateOfFestival.setTimeInMillis(1501102800000L);
      Calendar now = Calendar.getInstance();
      long difference = dateOfFestival.getTimeInMillis() - now.getTimeInMillis();
      timeLeft = Calendar.getInstance();
      new CountDownTimer(difference, 5000) {

         @Override
         public void onFinish() {

         }

         @Override
         public void onTick(long millisUntilFinished) {
            timeLeft.setTimeInMillis(millisUntilFinished);
            days.setText(String.valueOf(timeLeft.get(Calendar.DAY_OF_MONTH)));
            hours.setText(String.valueOf(timeLeft.get(Calendar.HOUR_OF_DAY)));
            minutes.setText(String.valueOf(timeLeft.get(Calendar.MINUTE)));
         }
      }.start();
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
}
