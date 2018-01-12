package at.htl.organicer.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by phili on 05.10.2017.
 */

public class Event implements Serializable {
    private int id;
    private String name;
    private Date date;
    private String subject;

    //region Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    //endregion

    public Event(){

    }

    public Event(int id, String name, Date date, String subject){
        setId(id);
        setName(name);
        setDate(date);
        setSubject(subject);
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" + "Name: " + "Description: " + subject + "\n" + "Date: " + date.toString();
    }
}
