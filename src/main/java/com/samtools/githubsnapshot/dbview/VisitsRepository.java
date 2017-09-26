package com.samtools.githubsnapshot.dbview;

import com.samtools.githubsnapshot.dbview.Visit;
import org.springframework.data.repository.CrudRepository;


public interface VisitsRepository extends CrudRepository<Visit, Long> {
}