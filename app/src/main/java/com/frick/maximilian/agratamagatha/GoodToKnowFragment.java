package com.frick.maximilian.agratamagatha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GoodToKnowFragment extends Fragment {
   public static Fragment newInstance() {
      return new GoodToKnowFragment();
   }
   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
         @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.good_to_know_fragment, container, false);
   }
}
