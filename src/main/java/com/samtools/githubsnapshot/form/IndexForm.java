package com.samtools.githubsnapshot.form;

import com.samtools.githubsnapshot.dbview.GHRepo;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class IndexForm {

    private String searchedUsername;

    private String username;

    private List<GHRepo> repoList;

    private List<Integer> timestamp;

    private Integer median_hour;

    private String selectedRepo;

    public void clear(){
        this.searchedUsername = null;
        this.username = null;
        this.repoList = null;
        this.timestamp = null;
        this.median_hour = null;
        this.selectedRepo = null;
    }

    public String getSearchedUsername() {
        return searchedUsername;
    }

    public void setSearchedUsername(String searchedUsername) {
        this.searchedUsername = searchedUsername;
    }

    public List<Integer> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Integer> timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMedian_hour() {
        return median_hour;
    }

    public void setMedian_hour(Integer median_hour) {
        this.median_hour = median_hour;
    }

    public String getSelectedRepo() {
        return selectedRepo;
    }

    public void setSelectedRepo(String selectedRepo) {
        this.selectedRepo = selectedRepo;
    }

    public Integer getMedianHour() {
        return median_hour;
    }

    public void setMedianHour(Integer median) {
        this.median_hour = median;
    }

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
