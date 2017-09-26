package com.samtools.githubsnapshot.dbview;

import org.springframework.data.repository.CrudRepository;

public interface GHReposListCR extends CrudRepository<GHRepo, Long> {
}