package com.samtools.githubsnapshot.dbview;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Database class: Commits, join to table Repositories ("class GHRepo)
 */
@Entity
@Table(name="COMMITS")
public class GHCommit {
    @Id
    @GeneratedValue
    @Column(name="commit_ID")
    private Long id;

    private String author;

    private LocalDateTime datetime;

    @ManyToOne(optional = false)
    @JoinColumn(name="repo_ID")
    private GHRepo repo;

    public GHCommit(GHRepo repo) {
        this.repo = repo;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public GHRepo getRepo() {
        return repo;
    }

    public void setRepo(GHRepo repo) {
        this.repo = repo;
    }
}
