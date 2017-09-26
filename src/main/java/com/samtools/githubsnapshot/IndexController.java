package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.dbview.*;
import com.samtools.githubsnapshot.form.UserFindForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Controller
public class IndexController {
    //testing inner field
    private String gh_name;

    private String createIndex(Model model){
        model.addAttribute("user_find_form", new UserFindForm((this.gh_name != null) ? this.gh_name:""));

        GHUser user = usersListCR.findOne(usersListCR.count());

        Visit visit = new Visit();
        if (user != null){
            visit.description = String.format("last search at %s for user %s", LocalDateTime.now(),this.gh_name);
        }
        else{
            visit.description = String.format("visited at %s", LocalDateTime.now());
        }

        visitsListCR.save(visit);

        return "index";
    }

    @GetMapping("/")
    public String index(Model model){

        return createIndex(model);
    }

    @PostMapping("/")
    public String findUser(Model model, @ModelAttribute UserFindForm userFindForm) {

        this.gh_name = userFindForm.getUsername();

        GHUser user = new GHUser();//(this.reposListCR);
        user.name = this.gh_name;
        //user.fillRepos();
        usersListCR.save(user);

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
