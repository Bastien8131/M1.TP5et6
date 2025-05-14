package utcapitole.miage.tp5et6.controller.gestionconf;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import utcapitole.miage.tp5et6.model.gestionconf.Participants;
import utcapitole.miage.tp5et6.model.gestionconf.StatutParticipants;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestionconf/participants")
public class ParticipantsController {
    List<Participants> participants = new ArrayList<>();

    public ParticipantsController() {
        //TEST
        Participants testParticipant = new Participants(
                1L,
                "Test",
                "User",
                "TestOrg",
                12345,
                "123 Test St",
                "TestCity",
                "TestCountry",
                "test.user@example.com",
                "2023-10-01",
                1,
                "password"
        );
        participants.add(testParticipant);
    }

    @PostMapping("/insert")
    public String addParticipants(@ModelAttribute Participants participant, Model model) {
        System.out.println(participant);

        for (Participants p : participants) {
            if(p.getEmailPart().equalsIgnoreCase(participant.getEmailPart())) {
                System.out.println("l'email de ce participant exist déjà");
                model.addAttribute("msgTitre", "C'est email de ce participant exist déjà");
                model.addAttribute("msgStatut", "erreur");
                model.addAttribute("participant", participant);
                return "gestionconf/message/message";
            }
        }

        participants.add(participant);
        model.addAttribute("msgTitre", "Compte créé avec succès !");
        model.addAttribute("msgStatut", "ok");
        model.addAttribute("participant", participant);
        return "gestionconf/message/message";
    }

    @GetMapping("/list")
    public String listParticipants(Model model) {
        model.addAttribute("participants", participants);
        return "gestionconf/participants/list/list";
    }

    @PostMapping("/connect")
    public String connectParticipants(HttpSession session, @RequestParam String email, @RequestParam String password, Model model) {
//        System.out.println(session.getAttribute("utilisateur"));

        for (Participants p : participants) {
            if(p.getEmailPart().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                System.out.println("Ce compte existe");
                model.addAttribute("message", "Ce compte existe");
                session.setAttribute("utilisateur", p);
                return "redirect:/gestionconf";
            }
        }

        System.out.println("Ce compte n'existe pas");
        model.addAttribute("message", "Ce compte n'existe pas");

        return "redirect:/gestionconf";
    }

    @GetMapping("/deconnect")
    public String deconnectParticipants(HttpSession session, Model model) {
        System.out.println("Tentative de connexion deconnect");
        session.removeAttribute("utilisateur");
        return "redirect:/gestionconf";
    }
}
