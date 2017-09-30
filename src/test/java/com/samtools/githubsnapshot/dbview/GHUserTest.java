package com.samtools.githubsnapshot.dbview;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GHUserTest {

    private final GHUser user = new GHUser();

    @Before
    public void InitData() {
        List<GHRepo> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            GHRepo repo = new GHRepo();
            repo.setUser(user);
            repo.setStars((int)(Math.random()*1000));
            repo.setDescription("Repo"+i);
            list.add(repo);
        }

        user.setRepos(list);
    }

    @Test
    public void getRepos() throws Exception {
        List<GHRepo> list = user.getRepos();
        assertEquals(true, list.get(0).getStars()>list.get(list.size()-1).getStars());
    }

}