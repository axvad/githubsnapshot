package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.dbview.GHUser;
import com.samtools.githubsnapshot.dbview.GHUsersListCR;
import com.samtools.githubsnapshot.dbview.Visit;
import com.samtools.githubsnapshot.dbview.VisitsRepository;
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

    /*@GetMapping("/")
    public ModelAndView index(){
        Map<String,String> model = new HashMap<>();
        model.put("name", "Alexander");

        Visit visit = new Visit();
        visit.description = String.format("visited at %s (%s)", LocalDateTime.now(),this.gh_name);
        visitsRepository.save(visit);

        return new ModelAndView("index",model);
    }*/

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

        visitsRepository.save(visit);

        return "finduser";
    }

    @GetMapping("/finduser")
    public String finduser(Model model){

        return createIndex(model);
    }

    @PostMapping("/finduser")
    public String findUser(Model model, @ModelAttribute UserFindForm userFindForm) {//@RequestBody UserFindForm userFindForm) {

        this.gh_name = userFindForm.getUsername();

        GHUser user = new GHUser();
        user.name = this.gh_name;
        usersListCR.save(user);

        return createIndex(model);
    }


    /*
    @PostMapping("/finduser")
    public ModelAndView findUser(@ModelAttribute UserFindForm userFindForm){//@RequestBody UserFindForm userFindForm) {
        if (userFindForm != null){
            this.gh_name = userFindForm.toString();
            //this.gh_name = userFindForm.getUserName();
        }
        else{
            this.gh_name = "can't find model";
        }

        //todo проверка после поиска пользователя
        //model.addAttribute("errorMessage", errorMessage);
        //return "addPerson";


        Map<String,String> mod = new HashMap<>();
        mod.put("name", this.gh_name);

        Visit visit = new Visit();
        visit.description = String.format("visited at %s (%s)", LocalDateTime.now(),this.gh_name);
        visitsRepository.save(visit);

        return new ModelAndView("profile",mod);
    }*/

    final VisitsRepository visitsRepository;
    final GHUsersListCR usersListCR;

    public IndexController(VisitsRepository visitsRepository, GHUsersListCR usersRepository){
        this.visitsRepository = visitsRepository;
        this.usersListCR = usersRepository;
    }
}
