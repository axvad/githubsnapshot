package com.samtools.githubsnapshot.dbview;

import org.springframework.data.repository.CrudRepository;

public interface GHCommitListCR extends CrudRepository<GHCommit, Long> {
}
