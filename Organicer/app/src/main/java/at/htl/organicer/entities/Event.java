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
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //endregion

    public Event(){

    }

    public Event(int id, String name, Date date, String description){
        setId(id);
        setName(name);
        setDate(date);
        setDescription(description);
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" + "Name: " + "Description: " + description + "\n" + "Date: " + date.toString();
    }
}
