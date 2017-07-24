package com.frick.maximilian.agratamagatha.timetable;

import com.frick.maximilian.agratamagatha.timetable.models.Gig;

public interface OnAlarmSetListener {
   void onAlarmClicked(Gig gig, boolean isActive);
}
