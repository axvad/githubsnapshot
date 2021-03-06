package com.samtools.githubsnapshot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samtools.githubsnapshot.dbview.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final VisitsListCR visitsListCR;
    private final GHUsersListCR usersListCR;


    public ApiController(VisitsListCR visitsListCR, GHUsersListCR usersListCR) {
        this.visitsListCR = visitsListCR;
        this.usersListCR = usersListCR;

    }

    @GetMapping("/visits")
    public Iterable<Visit> getVisits(){
        return visitsListCR.findAll();
    }

    @PostMapping("/adduser")
    public Boolean addUser(@RequestBody String jsonUser) {

        parseUserDataFromJson(jsonUser);

        return false;
    }

    private void parseUserDataFromJson(String jsonString) {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GHUser ghUser = new GHUser();

        try {
            JsonNode jtree = mapper.readTree(jsonString);

            ghUser.setLogin(jtree.findPath("userlogin").asText());

            List<GHRepo> repos = new ArrayList<>();

            for (JsonNode repo_node : jtree.findPath("repo_nodes")) {

                GHRepo rep = new GHRepo();

                rep.setUser(ghUser);

                rep.setDescription(repo_node.findPath("repo_name").asText());
                rep.setCount_branches(repo_node.findPath("branch_count").asInt());
                rep.setStars(repo_node.findPath("stars").asInt());

                List<GHCommit> dtCommits = new ArrayList<>();

                for (JsonNode commit : repo_node.findPath("commit_nodes")) {

                    if (commit.findPath("authoredByCommitter").asBoolean()) {

                        GHCommit cm = new GHCommit(rep);

                        String strDiteTime = commit.findPath("commit_date").asText();

                        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

                        LocalDateTime parsed = LocalDateTime.parse(strDiteTime, formatter);

                        cm.setAuthor(commit.findPath("commit_author").asText());
                        cm.setDatetime(parsed);

                        dtCommits.add(cm);
                    }
                }

                rep.setTimesOfCommit(dtCommits);

                repos.add(rep);
            }

            ghUser.setRepos(repos);

            usersListCR.save(ghUser);

            System.out.println(ghUser.getShortText());

            return;


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return;
    }
}