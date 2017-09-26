package com.samtools.githubsnapshot.modeldata;

public class GitRepository {

    private long id;
    private String description;
    private int star;
    private int count_commits;

    public GitRepository(long id, String description, int star, int count_commits) {
        this.id = id;
        this.description = description;
        this.star = star;
        this.count_commits = count_commits;
    }

    @Override
    public String toString() {
        return "GitRepository{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", star=" + star +
                ", count_commits=" + count_commits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitRepository that = (GitRepository) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getCount_commits() {
        return count_commits;
    }

    public void setCount_commits(int count_commits) {
        this.count_commits = count_commits;
    }


}
