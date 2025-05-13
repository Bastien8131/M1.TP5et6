package utcapitole.miage.tp5et6.controller.gestionconf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utcapitole.miage.tp5et6.model.gestionconf.Conferences;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestionconf/conferences")
public class ConferenceController {
    List<Conferences> conferences = new ArrayList<>();

    @PostMapping("/insert")
    public String addConference(
            @RequestParam String titreCongres,
            @RequestParam Integer numEditionCongres,
            @RequestParam String dtDebutCongres,
            @RequestParam String dtFinCongres,
            @RequestParam String urlSiteWebCongres,
            @RequestParam String activites,
            @RequestParam String thematiques
    ) {
        conferences.add(new Conferences(
                (long) conferences.size() + 1,
                titreCongres,
                numEditionCongres,
                dtDebutCongres,
                dtFinCongres,
                urlSiteWebCongres,
                activites,
                thematiques
        ));
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listConferences(Model model) {
        model.addAttribute("conferences", conferences);
        return "gestionconf/conferences/list/list";
    }


}
