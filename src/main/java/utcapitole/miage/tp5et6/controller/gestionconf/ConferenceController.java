package utcapitole.miage.tp5et6.controller.gestionconf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utcapitole.miage.tp5et6.db.DBConfig;
import utcapitole.miage.tp5et6.model.gestionconf.Activites;
import utcapitole.miage.tp5et6.model.gestionconf.Conferences;
import utcapitole.miage.tp5et6.model.gestionconf.Thematiques;

import java.util.List;

@Controller
@RequestMapping("/gestionconf/conferences")
public class ConferenceController {
    private final DBConfig dbConfig;

    public ConferenceController() {
        this.dbConfig = new DBConfig();
    }

    @GetMapping("/form")
    public String showConferenceForm(Model model) {
        try {
            model.addAttribute("thematiques", Thematiques.findAll(dbConfig.getDburl()));
            model.addAttribute("activites", Activites.findAll(dbConfig.getDburl()));
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
            e.printStackTrace();
        }

        return "gestionconf/conferences/insert/insert";
    }

    @PostMapping("/insert")
    public String addConference(
            @RequestParam String titreCongres,
            @RequestParam Integer numEditionCongres,
            @RequestParam String dtDebutCongres,
            @RequestParam String dtFinCongres,
            @RequestParam String urlSiteWebCongres,
            @RequestParam List<Integer> activites,
            @RequestParam List<Integer> thematiques,
            Model model
    ) {
        try {
            Conferences conference = new Conferences(
                    64L, // REF ;)
                    titreCongres,
                    numEditionCongres,
                    dtDebutCongres,
                    dtFinCongres,
                    urlSiteWebCongres,
                    thematiques,
                    activites
            );

            int result = conference.insert(dbConfig.getDburl());

            if (result > 0) {
                model.addAttribute("msgTitre", "Conférence créée avec succès !");
                model.addAttribute("msgStatut", "ok");
            } else {
                model.addAttribute("msgTitre", "Erreur lors de la création de la conférence");
                model.addAttribute("msgStatut", "erreur");
            }

            return "gestionconf/message/message";
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la conférence : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("msgTitre", "Erreur lors de l'ajout de la conférence");
            model.addAttribute("msgStatut", "erreur");
            model.addAttribute("msgContenu", "Une erreur est survenue : " + e.getMessage());
            return "gestionconf/message/message";
        }
    }

    @GetMapping("/list")
    public String listConferences(Model model) {
        model.addAttribute("conferences", Conferences.findAll(dbConfig.getDburl()));
        return "gestionconf/conferences/list/list";
    }
}