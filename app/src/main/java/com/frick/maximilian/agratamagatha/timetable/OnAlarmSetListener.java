package com.frick.maximilian.agratamagatha.timetable;

import com.frick.maximilian.agratamagatha.timetable.models.Gig;

public interface OnAlarmSetListener {
   void alarmAlreadySet();

   void onAlarmClicked(Gig gig);
}
