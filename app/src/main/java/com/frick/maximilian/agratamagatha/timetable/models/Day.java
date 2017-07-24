package com.frick.maximilian.agratamagatha.timetable.models;

import java.util.List;

public class Day implements TimeTableType {
   private String day;
   private List<Location> locations;

   public String getDay() {
      return day;
   }

   public List<Location> getLocations() {
      return locations;
   }

   @Override
   public int getViewType() {
      return TimeTableType.DAY_TYPE;
   }
}
