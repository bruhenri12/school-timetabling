package org.acme.schooltimetabling.domain;

/*  
 * Credits: This file is built oriented with the TimeFold "Hello World" Quick Start Guide
 */

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Timeslot {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public Timeslot() {
    }

    public Timeslot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
        }

        public int getDayPart() {
            int hour = startTime.getHour();
            if (hour >= 0 && hour < 6) {
                return 0; // After midnight
            } else if (hour >= 6 && hour < 12) {
                return 1; // Morning
            } else if (hour >= 12 && hour < 18) {
                return 2; // Afternoon
            } else {
                return 3; // Evening
            }
        }

        @Override
        public String toString() {
        return dayOfWeek + " " + startTime;
    }

}