package at.htl.organicer.entities;

/**
 * Created by phili on 29.11.2017.
 */

public class Subject extends BasicEntity {
    private String longName;
    private String alternateName;
    private boolean active;

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Subject(){}

    @Override
    public String toString() {
        return "Subject{" +
                "longName='" + longName + '\'' +
                ", alternateName='" + alternateName + '\'' +
                ", active=" + active +
                '}';
    }
}
