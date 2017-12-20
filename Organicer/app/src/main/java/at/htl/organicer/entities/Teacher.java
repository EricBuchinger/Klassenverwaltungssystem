package at.htl.organicer.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by phili on 29.11.2017.
 */

public class Teacher extends BasicEntity {

    private String firstName;
    private String lastName;
    private String title;
    private boolean active;
    private List<Integer> dids;

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", title='" + title + '\'' +
                ", active=" + active +
                ", dids=" + dids +
                '}';
    }

    //region Getter and Setter

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Integer> getDids() {
        return dids;
    }

    public void setDids(List<Integer> dids) {
        this.dids = dids;
    }

    //endregion

    public Teacher(){
        dids = new LinkedList<>();
    }


}
