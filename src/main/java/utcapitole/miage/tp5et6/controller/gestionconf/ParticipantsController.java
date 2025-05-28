package utcapitole.miage.tp5et6.controller.gestionconf;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import utcapitole.miage.tp5et6.db.DBConfig;
import utcapitole.miage.tp5et6.model.gestionconf.Participants;
import utcapitole.miage.tp5et6.model.gestionconf.Status;

@Controller
@RequestMapping("/gestionconf/participants")
public class ParticipantsController {
    private final DBConfig dbConfig;

    public ParticipantsController() {
        this.dbConfig = new DBConfig();
    }

    @GetMapping("/form/insert")
    public String showFormInsert(Model model) {
        model.addAttribute("statuts", Status.findAll(dbConfig.getDburl()));
        return "gestionconf/participants/insert/insert";
    }

    @GetMapping("/form/update/{codParticipant}")
    public String showFormUpdate(@PathVariable Integer codParticipant, Model model) {
        model.addAttribute("statuts", Status.findAll(dbConfig.getDburl()));
        model.addAttribute("fetchParticipant", Participants.findById(dbConfig.getDburl(), codParticipant));
        return "gestionconf/participants/update/update";
    }

    @PostMapping("/insert")
    public String addParticipants(@ModelAttribute Participants participant, Model model) {
        int rs = participant.insert(dbConfig.getDburl());

        if (rs == 0) {
            model.addAttribute("msgTitre", "Compte créé avec succès !");
            model.addAttribute("msgStatut", "ok");
        }
        else if (rs == 1){
            model.addAttribute("msgTitre", "Erreur lors de la création du compte");
            model.addAttribute("msgStatut", "erreur");
        }
        else if (rs == 2) {
            model.addAttribute("msgTitre", "Cet email de participant existe déjà");
            model.addAttribute("msgStatut", "erreur");
        }

        return "gestionconf/message/message";
    }

    @PostMapping("/update")
    public String updateParticipants(@ModelAttribute Participants participant, Model model) {
        int rs = participant.update(dbConfig.getDburl());

        if (rs == 0) {
            model.addAttribute("msgTitre", "Compte mis à jour avec succès !");
            model.addAttribute("msgStatut", "ok");
            model.addAttribute("participant", participant);
        }
        else if (rs == 1) {
            model.addAttribute("msgTitre", "Erreur lors de la mise à jour du compte");
            model.addAttribute("msgStatut", "erreur");
            model.addAttribute("participant", participant);
        }

        return "gestionconf/message/message";
    }

    @GetMapping("/list")
    public String listParticipants(Model model) {
        model.addAttribute("participants", Participants.findAll(dbConfig.getDburl()));
        return "gestionconf/participants/list/list";
    }

    @PostMapping("/connect")
    public String connectParticipants(HttpSession session, @RequestParam String email, @RequestParam String password, Model model) {
        Participants participant = Participants.authenticate(dbConfig.getDburl(), email, password);

        if (participant != null) {
            System.out.println("Ce compte existe");
            session.setAttribute("utilisateur", participant);
            return "redirect:/gestionconf";
        } else {
            System.out.println("Ce compte n'existe pas");
            model.addAttribute("message", "Ce compte n'existe pas");
            return "redirect:/gestionconf";
        }
    }

    @GetMapping("/deconnect")
    public String deconnectParticipants(HttpSession session, Model model) {
        System.out.println("Tentative de déconnexion");
        session.removeAttribute("utilisateur");
        return "redirect:/gestionconf";
    }
}