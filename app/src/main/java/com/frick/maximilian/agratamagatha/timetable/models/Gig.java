package com.frick.maximilian.agratamagatha.timetable.models;

public class Gig implements TimeTableType {
   private String band;
   private boolean notify;
   private long timestamp;
   private String time;

   public String getBand() {
      return band;
   }

   public long getStartTime() {
      return timestamp;
   }

   public String getTime() {
      return time;
   }

   @Override
   public int getViewType() {
      return TimeTableType.GIG_TYPE;
   }

   public boolean isNotify() {
      return notify;
   }

   public void setNotify(boolean notify) {
      this.notify = notify;
   }
}
