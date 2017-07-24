package com.frick.maximilian.agratamagatha.lineup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.frick.maximilian.agratamagatha.BaseActivity;
import com.frick.maximilian.agratamagatha.R;

public class BandWebViewActivity extends BaseActivity {
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.band_web_view_activity);
      WebView webView = (WebView) findViewById(R.id.webview);
   }
}
