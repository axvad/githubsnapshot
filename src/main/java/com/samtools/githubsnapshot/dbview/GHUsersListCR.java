package com.samtools.githubsnapshot.dbview;

import com.samtools.githubsnapshot.dbview.GHUser;
import org.springframework.data.repository.CrudRepository;


public interface GHUsersListCR extends CrudRepository<GHUser, Long>{}
