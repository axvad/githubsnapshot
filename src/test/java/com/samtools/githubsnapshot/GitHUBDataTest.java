package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.modeldata.GitHUBData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GitHUBDataTest {
    private GitHUBData gitData;

    @Before
    public void setUp() throws Exception {
        gitData = new GitHUBData();
    }

    @After
    public void tearDown() throws Exception {
        gitData = null;
    }

    @Test
    public void connect() throws Exception {
        assertEquals(false, gitData.connect());
    }

    @Test
    public void searchData() throws Exception {

    }

    @Test
    public void getData() throws Exception {
    }

}