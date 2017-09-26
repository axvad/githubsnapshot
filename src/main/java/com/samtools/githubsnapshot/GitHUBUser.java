package com.samtools.githubsnapshot;

public class GitHUBUser {

    private int id;

    private String name;

    public GitHUBUser(String name) {
        this.name = name;
        this.id = -1;
    }

    public GitHUBUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "GitHUBUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitHUBUser that = (GitHUBUser) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }


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
}
