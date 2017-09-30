package com.samtools.githubsnapshot.form;

public class UserFindForm {

    private String findusername;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public UserFindForm() {
    }

    public UserFindForm(String username) {
        this.findusername = username;
    }

    public String getFindusername() {
        return findusername;
    }

    public void setFindusername(String userName) {
        this.findusername = userName;
    }

    @Override
    public String toString() {
        return "UserFindForm{" +
                "findusername='" + findusername + '\'' +
                '}'+super.toString();
    }
}
