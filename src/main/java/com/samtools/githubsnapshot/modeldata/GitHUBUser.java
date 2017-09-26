package com.samtools.githubsnapshot.modeldata;

import java.util.ArrayList;
import java.util.List;


public class GitHUBUser {

    private int id;

    private String name;

    private List<GitRepository> repositories;


    public GitHUBUser(String name) {
        this.name = name;
        this.id = -1;
    }

    public GitHUBUser(int id, String name,List<GitRepository> repos) {
        this.id = id;
        this.name = name;
        this.repositories = new ArrayList<>(repos);
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

    public List<GitRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GitRepository> repositories) {
        this.repositories = repositories;
    }

    //todo delete from release
    private static int idcounter =0;

    //it's stub for testing.
    static GitHUBUser createTestUser(String name){
        idcounter++;

        ArrayList<GitRepository> rps = new ArrayList<>();

        for (int i=0;i<6;i++ )
            rps.add(new GitRepository(40+i,"Repos"+(i+1),(int)(Math.random()*10),(int)(Math.random()*100)));

        return new GitHUBUser(idcounter, name, rps);
    }
}
