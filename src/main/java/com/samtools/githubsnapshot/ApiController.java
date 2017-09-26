package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.dbview.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    final VisitsRepository visitsRepository;
    final GHUsersListCR usersListCR;
    final GHReposListCR reposListCR;

    public ApiController(VisitsRepository visitsRepository, GHUsersListCR usersListCR, GHReposListCR reposListCR) {
        this.visitsRepository = visitsRepository;
        this.usersListCR = usersListCR;
        this.reposListCR = reposListCR;
    }

    @GetMapping("/visits")
    public Iterable<Visit> getVisits(){
        return visitsRepository.findAll();
    }

    @GetMapping("/user")
    public GHUser getLastUser(){
        return usersListCR.findOne(usersListCR.count()); //if (usersListCR.count()==0)
    }

    @GetMapping("/repos")
    public Iterable<GHRepo> getRepos(long userId){
        return reposListCR.findAll();
    }
}