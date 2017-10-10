package com.samtools.githubsnapshot;

import com.samtools.githubsnapshot.dbview.*;
import com.samtools.githubsnapshot.form.IndexForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("indexForm")
public class IndexController {

    private final VisitsListCR visitsListCR;
    private final GHUsersListCR usersListCR;
    private final GHReposListCR reposListCR;

    @Autowired
    private Analyzer analyzer;

    @Value("${view.visits_log_size}")
    private int visitsLogSize;

    public IndexController(VisitsListCR visitsListCR, GHUsersListCR usersListCR, GHReposListCR reposListCR) {

        this.visitsListCR = visitsListCR;
        this.usersListCR = usersListCR;
        this.reposListCR = reposListCR;
    }

    /**
     * Append statistic data log
     */
    private void appendVisits() {

        Visit visit = new Visit();

        visit.description = String.format("event at %s", LocalDateTime.now());

        if (visitsListCR.count() > visitsLogSize - 1)
            visitsListCR.deleteAll();

        visitsListCR.save(visit);
    }

    @GetMapping("/")
    public ModelAndView index(ModelMap model) {

        if (!model.containsAttribute("indexForm")) {

            IndexForm indexForm = new IndexForm();

            model.addAttribute("indexForm", indexForm);
        }

        appendVisits();

        return new ModelAndView("index");
    }

    @PostMapping("/")
    public String findUser(@ModelAttribute("indexForm") IndexForm indexForm,
                           HttpServletRequest request) {

        String finduser = indexForm.getSearchedUsername();

        indexForm.clear();

        System.out.printf("Your select: %s\n", finduser);

        String cmd_find = request.getRequestURL().append("finduser/").append(finduser).toString();
        String cmd_add = request.getRequestURL().append("api/adduser").toString();

        System.out.println("Find command: " + cmd_find);
        System.out.println("Add command: " + cmd_add);

        String result = new RestTemplate()
                .getForObject(cmd_find, String.class);

        Boolean success = new RestTemplate()
                .postForObject(cmd_add, result, Boolean.class);

        GHUser user = usersListCR.findOne(usersListCR.count());

        if (user == null)
            return "index";

        indexForm.setUsername(user.getName());
        indexForm.setRepoList(user.getRepos());

        appendVisits();

        return "index";
    }

    @GetMapping("/repo/{id}")
    public String repo_detail(@PathVariable("id") Long id,
                              @ModelAttribute("indexForm") IndexForm indexForm) {

        GHRepo rp = reposListCR.findOne(id);

        List<LocalDateTime> dtList = rp.getTimesOfCommit().stream()
                .map(GHCommit::getDatetime).collect(Collectors.toList());

        List<Integer> dest = analyzer.timeDistribution(dtList);

        Integer median = null;
        if (dest != null) {
            median = analyzer.median(dest);
        }

        indexForm.setSelectedRepo(rp.getDescription());
        indexForm.setMedianHour(median);
        indexForm.setTimestamp(dest);

        return "index";
    }


}
