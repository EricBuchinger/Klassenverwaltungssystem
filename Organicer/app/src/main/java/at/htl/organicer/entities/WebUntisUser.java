package at.htl.organicer.entities;

/**
 * Created by eric on 18.12.17.
 */

public class WebUntisUser {
    private String username;
    private String password;

    public WebUntisUser() {
    }

    public WebUntisUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
