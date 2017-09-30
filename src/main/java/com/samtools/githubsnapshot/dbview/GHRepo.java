package com.samtools.githubsnapshot.dbview;

import javax.persistence.*;
import java.util.List;

/**
 * Database class: Repositories, join to tables:
 *  GHUser as ManyToOne by user
 *  GHCommit as OneToMany
 */
@Entity
@Table(name="REPOSITS")
public class GHRepo {

    @Id
    @GeneratedValue
    @Column(name="repo_ID")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_ID")
    private GHUser user;

    public String description;

    public Integer stars;

    public Integer count_branches;

    @OneToMany(mappedBy = "repo",cascade = CascadeType.ALL)
    private List<GHCommit> timesOfCommit;

    public long getId() {
        return id;
    }

    public GHUser getUser() {
        return user;
    }

    public void setUser(GHUser user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCount_branches() {
        return count_branches;
    }

    public void setCount_branches(int count_branches) {
        this.count_branches = count_branches;
    }

    public List<GHCommit> getTimesOfCommit() {
        return timesOfCommit;
    }

    public void setTimesOfCommit(List<GHCommit> timesOfCommit) {
        this.timesOfCommit = timesOfCommit;
    }
}
