package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.form.UserFindForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/finduser")
    public String finduser(Model model){
        //model.addAllAttributes("find_user_form", new UserFindForm());
        //model.addAttribute("name", new String("Alexander"));
        model.addAttribute("user_find_form", new UserFindForm());

        Visit visit = new Visit();
        visit.description = String.format("visited at %s (%s)", LocalDateTime.now(),this.gh_name);
        visitsRepository.save(visit);

        return "finduser";
    }


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
    }

    final VisitsRepository visitsRepository;

    public IndexController(VisitsRepository visitsRepository){
        this.visitsRepository = visitsRepository;
    }
}
