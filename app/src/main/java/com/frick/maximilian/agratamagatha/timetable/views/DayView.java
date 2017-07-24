package com.frick.maximilian.agratamagatha.timetable.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.ModelProp;
import com.airbnb.epoxy.ModelView;
import com.frick.maximilian.agratamagatha.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@ModelView
public class DayView extends LinearLayout {
   @BindView (R.id.day_name)
   TextView textView;

   public DayView(Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
      init();
   }

   @ModelProp (options = ModelProp.Option.GenerateStringOverloads)
   public void setText(CharSequence text) {
      this.textView.setText(text);
   }

   private void init() {
      setOrientation(VERTICAL);
      inflate(getContext(), R.layout.view_day, this);
      ButterKnife.bind(this);
   }
}
