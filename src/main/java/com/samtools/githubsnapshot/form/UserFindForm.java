package com.samtools.githubsnapshot.form;

public class UserFindForm {
    private String username;

    public UserFindForm() {
    }

    public UserFindForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    @Override
    public String toString() {
        return "UserFindForm{" +
                "username='" + username + '\'' +
                '}'+super.toString();
    }
}
