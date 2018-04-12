package at.htl.organicer.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phili on 05.10.2017.
 */

public class Event implements Serializable {
    public int id;
    public String name;
    public Date date;
    public String subject;
    public int dislike = 0;
    public int upvotes = 0;
    public ArrayList<String> userDislikes = new ArrayList();
    public ArrayList<String> userUpvotes = new ArrayList();
    public boolean confirmed = false;

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

    public void addDislike(String uid){
        if(!userDislikes.contains(uid)) {
            userDislikes.add(dislike,uid);
            dislike++;
        }
    }
    public void addUpvote(String uid){
        if(!userUpvotes.contains(uid)) {
            userUpvotes.add(upvotes,uid);
            upvotes++;
        }
    }
    public ArrayList<String> getUserDislikes(){
        return userDislikes;
    }
    public void setUserDislikes(ArrayList<String> userDislikes){
        this.userDislikes = userDislikes;
    }
    public ArrayList<String> getUserUpvotes() {
        return userUpvotes;
    }

    public void setUserUpvotes(ArrayList<String> userUpvotes) {
        this.userUpvotes = userUpvotes;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("date", date);
        result.put("subject", subject);
        result.put("userDislikes", userDislikes);
        result.put("userUpvotes", userUpvotes);
        result.put("confirmed", confirmed);

        return result;
    }
    @Override
    public String toString() {
        return "Id: " + id + "\n" + "Name: " + "Description: " + subject + "\n" + "Date: " + date.toString();
    }
}
