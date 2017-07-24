package com.frick.maximilian.agratamagatha;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.frick.maximilian.agratamagatha.lineup.LineUpFragment;
import com.frick.maximilian.agratamagatha.timetable.TimeTableFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopLevelActivity extends BaseActivity
      implements BottomNavigationView.OnNavigationItemSelectedListener {

   @BindView (R.id.navigation)
   BottomNavigationView navigationView;

   public void handleNavigationItemClicked(int itemId) {
      navigationView.setSelectedItemId(itemId);
   }

   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      Fragment selectedFragment = null;
      switch (item.getItemId()) {
         case R.id.navigation_start:
            selectedFragment = StartFragment.newInstance();
            break;
         case R.id.navigation_lineup:
            selectedFragment = LineUpFragment.newInstance();
            break;
         case R.id.navigation_timetable:
            selectedFragment = TimeTableFragment.newInstance();
            break;
         case R.id.navigation_good_to_know:
            selectedFragment = GoodToKnowFragment.newInstance();
            break;
      }
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragmentContainer, selectedFragment);
      transaction.commit();
      return true;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_activity);
      ButterKnife.bind(this);
      navigationView.setOnNavigationItemSelectedListener(this);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragmentContainer, StartFragment.newInstance());
      transaction.commit();
   }
}
