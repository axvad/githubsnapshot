package com.samtools.githubsnapshot.dbview;

import javax.persistence.*;
import java.util.Set;

@Entity
public class GHUser {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name="USERNAME")
    public String name;

    @OneToMany
    @Column(name = "REPOS")
    Set<GHRepo> repos;

    /*final GHReposListCR repositories;

    public Iterable<GHRepo> getRepos(){
        return repositories.findAll();
    }

    public GHUser(GHReposListCR repositories){
        this.repositories = repositories;
    }

    //todo test. delete for release
    public void fillRepos(){
        for (int i=0; i < 3; i++){
            GHRepo rp = new GHRepo();
            rp.count_branches = (int) (Math.random()*10);
            rp.star = (int) (Math.random()*50);
            rp.user_id = this.id;
            rp.description = "Repository "+i;

            repositories.save(rp);
        }
    }*/
}
