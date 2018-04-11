package at.htl.organicer.entities;

import java.time.LocalDate;

/**
 * Created by phili on 11.04.2018.
 */

public class TimeTable extends BasicEntity {

    private LocalDate date;
    private String startTime;
    private String endTime;
    private int klasseId;
    private String teacher;
    private String subject;
    private int subjectId;
    private int lessonNumber;
    private String activityType;

    //region Getter & Setter
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getKlasseId() {
        return klasseId;
    }

    public void setKlasseId(int klasseId) {
        this.klasseId = klasseId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    //endregion

    public TimeTable(){

    }
}
