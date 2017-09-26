package com.samtools.githubsnapshot.dbview;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GHRepo {

    @Id
    @GeneratedValue

    public long id;

    public long user_id;

    public String description;

    public int star;

    public int count_branches;

}
