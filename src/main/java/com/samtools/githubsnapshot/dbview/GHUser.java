package com.samtools.githubsnapshot.dbview;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="USER")
public class GHUser {

    @Id
    @GeneratedValue
    @Column(name="user_ID")
    public Long id;

    public String name;

    public String login;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    public List<GHRepo> repos;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        if (this.name != null)
            return new StringBuilder(this.login).append(" (").append(this.name).append(")").toString();
        else
            return this.login;
    }

    public String getShortText(){
        StringBuilder result = new StringBuilder();

        result = result.append(this.getLogin());

        result = (this.name != null) ? result.append(" (").append(this.name).append(")") : result;
        result = (repos != null) ? result.append(", repositories: ").append(repos.size()) : result;

        return result.toString();
    }

    /**
     * Sorted by repository stars from top to down
     * @return
     */
    public List<GHRepo> getRepos() {
        this.repos.sort( Comparator.comparing(it->-it.getStars()));
        return this.repos;
    }

    public void setRepos(List<GHRepo> repos) {
        this.repos = repos;
    }

}
