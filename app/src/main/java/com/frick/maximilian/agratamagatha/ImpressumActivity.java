package com.frick.maximilian.agratamagatha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.frick.maximilian.agratamagatha.core.BaseActivity;

import butterknife.ButterKnife;

public class ImpressumActivity extends BaseActivity {
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         finish();
      }
      return true;
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.impressum_activity);
      Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      toolbar.setTitle(getString(R.string.impressum));

      WebView webView = ButterKnife.findById(this, R.id.webview);
      webView.getSettings().setJavaScriptEnabled(true);
      webView.loadUrl("file:///android_asset/impressum.html");
   }
}
