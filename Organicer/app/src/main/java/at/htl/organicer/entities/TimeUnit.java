package at.htl.organicer.entities;

import java.time.LocalDateTime;

/**
 * Created by phili on 29.11.2017.
 */

public class TimeUnit {
    private String startTime;
    private String endTime;
    private String name;

    public TimeUnit(){}


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if(startTime.length()==3) {
            this.startTime = "0" + startTime.charAt(0) + ":" + startTime.substring(1);
        }
        else
            this.startTime = startTime.substring(0,2) + ":" + startTime.substring(2);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if(endTime.length()==3) {
            this.endTime = "0" + endTime.charAt(0) + ":" + endTime.substring(1);
        }
        else
            this.endTime = endTime.substring(0,2) + ":" + endTime.substring(2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TimeUnit{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
