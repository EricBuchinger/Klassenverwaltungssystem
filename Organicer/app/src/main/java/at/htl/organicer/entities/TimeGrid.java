package at.htl.organicer.entities;

import java.util.LinkedList;

/**
 * Created by phili on 29.11.2017.
 */

public class TimeGrid {
    private Weekday weekday;
    private LinkedList<TimeUnit> timeUnits;


    //region Getters and Setters
    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public LinkedList<TimeUnit> getTimeUnits() {
        return timeUnits;
    }

    public void setTimeUnits(LinkedList<TimeUnit> timeUnits) {
        this.timeUnits = timeUnits;
    }
    //endregion

    public TimeGrid(){ timeUnits = new LinkedList<>();}

    @Override
    public String toString() {
        return "TimeGrid{" +
                "weekday=" + weekday +
                ", timeUnits=" + timeUnits +
                '}';
    }
}
