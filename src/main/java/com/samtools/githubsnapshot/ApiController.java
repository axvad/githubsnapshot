package com.samtools.githubsnapshot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    final VisitsRepository visitsRepository;

    public ApiController(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    @GetMapping("/visits")
    public Iterable<Visit> getVisits(){
        return visitsRepository.findAll();
    }

    @GetMapping("/user")
    public GitHUBUser getUser(){
        //TODO заменить заглушку
        return new GitHUBUser(33, "GitBORIS");
    }
}