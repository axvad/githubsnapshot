package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.dbview.*;
import com.samtools.githubsnapshot.form.ResultForm;
import com.samtools.githubsnapshot.form.UserFindForm;
import com.samtools.githubsnapshot.graphql.GQLClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.jws.WebParam;
import java.time.LocalDateTime;

@Controller
public class IndexController {
    //testing inner field
    private String gh_name;

    @Autowired
    ApiController api;

    @Value("${graphql.server}")
    private String rootGQL;

    @Value("${graphql.query}")
    private String queryGQL;

    @Value("${graphql.key}")
    private String keyGQL;

    @Value("${view.visits_log_size}")
    private int visitsLogSize;

    private String createIndex(Model model){

        GHUser user = usersListCR.findOne(usersListCR.count());

        UserFindForm uiform = new UserFindForm();

        model.addAttribute("user_find_form", uiform);

        appendResult(model, user);

        appendVisits(model, user);

        return "index";
    }

    /**
     * Create data for result form
     * @param model of Thymeleaf page page for adding data
     * @param user finded user
     * @return modifided model
     */
    private void appendResult(Model model, GHUser user){

        if (user == null)
            return;

        ResultForm res_form = new ResultForm();

        res_form.setUsername(user.getName());
        res_form.setRepoList(user.getRepos());

        model.addAttribute("result_form", res_form);

        return;
    }

    /**
     * append detiled data about repo
     */
    private void appendDetailOfRepo(Model model, GHUser user)
    {
        if (user == null)
            return;


        return;
    }

    /**
     * Append statistic data log
     * @param model for append data on start (no used now)
     * @param user for append info about last searching
     * @return updated model data
     */
    private void appendVisits(Model model, GHUser user){
        Visit visit = new Visit();
        if (user != null){
            visit.description = String.format("last search at %s for user %s", LocalDateTime.now(),user.getShortText());
        }
        else{
            visit.description = String.format("visited at %s", LocalDateTime.now());
        }

        visitsListCR.save(visit);

        if (visitsListCR.count()>visitsLogSize)
            visitsListCR.deleteAll();

        return;
    }

    @GetMapping("/")
    public String index(Model model){

        return createIndex(model);
    }

    /*@GetMapping("/repo/{id}")
    public String repo_detail(Model model, @PathVariable("id") Long id){

        return createIndex(model);
    }*/

    @PostMapping("/")
    public String findUser(Model model, @ModelAttribute UserFindForm userFindForm) {

        String finduser = userFindForm.getFindusername();

        System.out.printf("Your select: %s\n", finduser);

        //todo create service
        GQLClient client = new GQLClient();
        client.setRoot(this.rootGQL);
        client.setToken(this.keyGQL);
        //client.setFileQuery("/home/sam/Programming/Git/githubsnapshot/src/main/java/com/samtools/githubsnapshot/graphql/gitsnap.gql");
        client.setQuery(this.queryGQL);

        String result = client.getUserData(finduser);

        api.parseUserDataFromJson(result);

        return createIndex(model);
    }

    final VisitsListCR visitsListCR;
    final GHUsersListCR usersListCR;
    final GHReposListCR reposListCR;

    public IndexController(VisitsListCR visitsListCR, GHUsersListCR usersListCR, GHReposListCR reposListCR){
        this.visitsListCR = visitsListCR;
        this.usersListCR = usersListCR;
        this.reposListCR = reposListCR;
    }
}
