package com.samtools.githubsnapshot.dbview;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GHUser {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
}
