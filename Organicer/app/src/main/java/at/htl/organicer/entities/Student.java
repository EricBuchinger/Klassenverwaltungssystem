package at.htl.organicer.entities;

/**
 * Created by phili on 29.11.2017.
 */

public class Student extends BasicEntity {
    private String firstName;
    private String lastName;
    private String apiKey;
    private String gender;


    public Student() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
