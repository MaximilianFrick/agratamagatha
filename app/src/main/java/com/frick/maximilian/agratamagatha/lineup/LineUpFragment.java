package com.frick.maximilian.agratamagatha.lineup;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.frick.maximilian.agratamagatha.ImpressumActivity;
import com.frick.maximilian.agratamagatha.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineUpFragment extends Fragment implements BandClickedListener {

   public static final String ASSETS_BANDS_JSON = "bands.json";
   @BindView (R.id.recyclerview)
   RecyclerView recyclerView;
   @BindView (R.id.toolbar)
   Toolbar toolbar;
   private List<Band> bands;

   public static Fragment newInstance() {
      return new LineUpFragment();
   }

   @Override
   public void onBandClicked(Band band) {
      // TODO: start Webview and load band
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      prepareMenuItems(builder);
      prepareToolbar(builder);
      builder.setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
      builder.setExitAnimations(getActivity(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right);
      CustomTabsIntent intent = builder.build();
      addReferrer(intent);
      intent.launchUrl(getActivity(), Uri.parse(band.getLink()));
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.impressum, menu);
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
         @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.lineup_fragment, container, false);
      ButterKnife.bind(this, view);
      initToolbar();
      return view;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.action_impressum) {
         startActivity(new Intent(getActivity(), ImpressumActivity.class));
      }
      return true;
   }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      loadBands();
      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
      recyclerView.setLayoutManager(layoutManager);
      LineUpListAdapter lineUpListAdapter = new LineUpListAdapter(bands, this);
      recyclerView.setAdapter(lineUpListAdapter);
   }

   private void addReferrer(CustomTabsIntent intent) {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
         return;
      }
      intent.intent.putExtra(Intent.EXTRA_REFERRER,
            Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getActivity().getPackageName()));
   }

   private void initToolbar() {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      toolbar.setTitle(getString(R.string.title_lineup));
   }

   private void loadBands() {
      Gson gson = new Gson();
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(new InputStreamReader(this.getActivity()
               .getAssets()
               .open(ASSETS_BANDS_JSON)));
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
      Type type = new TypeToken<List<Band>>() {
      }.getType();
      if (reader != null) {
         bands = gson.fromJson(reader, type);
      }
   }

   private void prepareMenuItems(CustomTabsIntent.Builder builder) {
      builder.addDefaultShareMenuItem();
      Intent menuIntent = new Intent();
      menuIntent.setClass(getActivity().getApplicationContext(), this.getClass());
      Bundle menuBundle =
            ActivityOptions.makeCustomAnimation(getActivity(), android.R.anim.slide_in_left,
                  android.R.anim.slide_out_right)
                  .toBundle();
      PendingIntent pendingIntent =
            PendingIntent.getActivity(getActivity().getApplicationContext(), 0, menuIntent, 0,
                  menuBundle);
   }

   private void prepareToolbar(CustomTabsIntent.Builder builder) {
      builder.enableUrlBarHiding();
      builder.setShowTitle(true);
      builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
      builder.setCloseButtonIcon(
            BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back));
   }
}
