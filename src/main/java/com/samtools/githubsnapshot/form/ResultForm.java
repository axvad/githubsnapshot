package com.samtools.githubsnapshot.form;

import com.samtools.githubsnapshot.dbview.GHRepo;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ResultForm {

    private String username;

    private List<GHRepo> repoList;

    private Map<Integer, Integer> timestamp;

    private LocalTime median;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GHRepo> getRepoList() {
        return repoList;
    }

    public void setRepoList(List<GHRepo> repoList) {
        this.repoList = repoList;
    }
}
