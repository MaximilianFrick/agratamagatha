package com.frick.maximilian.agratamagatha.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frick.maximilian.agratamagatha.R;
import com.frick.maximilian.agratamagatha.timetable.models.Day;
import com.frick.maximilian.agratamagatha.timetable.models.Gig;
import com.frick.maximilian.agratamagatha.timetable.models.Location;
import com.frick.maximilian.agratamagatha.timetable.models.TimeTableType;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
   public class DayViewHolder extends ViewHolder {
      @BindView (R.id.day_name)
      TextView day;

      DayViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      @Override
      void updateView(int position) {
         day.setText(((Day) items.get(position)).getDay());
      }
   }

   public class GigViewHolder extends ViewHolder {
      @BindView (R.id.gig_alarm)
      ImageView alarm;
      @BindView (R.id.gig_band)
      TextView band;
      @BindView (R.id.gig_time)
      TextView time;

      GigViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      @Override
      void updateView(int position) {
         final Gig gig = ((Gig) items.get(position));
         final boolean isSelected = gig.isNotify();
         time.setText(gig.getTime());
         band.setText(gig.getBand());
         alarm.setSelected(isSelected);
         alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alarm.setSelected(!isSelected);
               alarmClickListener.onAlarmClicked(gig, alarm.isSelected());
            }
         });
      }
   }

   public class LocationViewHolder extends ViewHolder {
      @BindView (R.id.location_name)
      TextView locationName;

      LocationViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }

      @Override
      void updateView(int position) {
         locationName.setText(((Location) items.get(position)).getName());
      }
   }

   abstract class ViewHolder extends RecyclerView.ViewHolder {
      ViewHolder(View itemView) {
         super(itemView);
      }

      abstract void updateView(int position);
   }

   private OnAlarmSetListener alarmClickListener;
   private List<TimeTableType> items = Collections.emptyList();

   TimeTableAdapter(List<TimeTableType> items, OnAlarmSetListener alarmClickListener) {
      this.items = items;
      this.alarmClickListener = alarmClickListener;
   }

   @Override
   public int getItemCount() {
      return items.size();
   }

   @Override
   public int getItemViewType(int position) {
      return items.get(position)
            .getViewType();
   }

   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {
      holder.updateView(position);
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      switch (viewType) {
         case TimeTableType.DAY_TYPE:
            return new DayViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.view_day, parent, false));
         case TimeTableType.LOCATION_TYPE:
            return new LocationViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.view_location, parent, false));
         case TimeTableType.GIG_TYPE:
            return new GigViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.view_timetable_entry, parent, false));
         default:
            break;
      }
      return null;
   }
}
