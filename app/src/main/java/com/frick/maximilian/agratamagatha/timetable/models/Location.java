package com.frick.maximilian.agratamagatha.timetable.models;

import java.util.List;

public class Location implements TimeTableType{
   private String name;
   private List<Gig> gigs;

   public String getName() {
      return name;
   }

   public List<Gig> getGigs() {
      return gigs;
   }

   @Override
   public int getViewType() {
      return TimeTableType.LOCATION_TYPE;
   }
}
