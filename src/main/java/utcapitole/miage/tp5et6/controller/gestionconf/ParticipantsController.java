package utcapitole.miage.tp5et6.controller.gestionconf;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import utcapitole.miage.tp5et6.db.DB;
import utcapitole.miage.tp5et6.db.DBConfig;
import utcapitole.miage.tp5et6.model.gestionconf.Participants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/gestionconf/participants")
public class ParticipantsController {
    private final DBConfig dbConfig;

    public ParticipantsController() {
        this.dbConfig = new DBConfig();
    }

    @GetMapping("/form/insert")
    public String showFormInsert(Model model) {
        // Récupération des statuts depuis la base de données
        List<Map<String, Object>> statuts = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM STATUS");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> statut = new HashMap<>();
                statut.put("codStatus", rs.getInt("codStatus"));
                statut.put("nomStatus", rs.getString("nomStatus"));
                statuts.add(statut);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des statuts : " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("statuts", statuts);
        return "gestionconf/participants/insert/insert";
    }

    @GetMapping("/form/update/{codParticipant}")
    public String showFormUpdate(@PathVariable Integer codParticipant, Model model) {
        List<Map<String, Object>> statuts = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM STATUS");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> statut = new HashMap<>();
                statut.put("codStatus", rs.getInt("codStatus"));
                statut.put("nomStatus", rs.getString("nomStatus"));
                statuts.add(statut);
            }



            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des statuts : " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("statuts", statuts);
        model.addAttribute(
                "fetchParticipant",
                DB.getPartFromDB(dbConfig.getDburl(), codParticipant)
        );

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
        try {
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM PARTICIPANTS WHERE emailPart = ? AND password = ?"
            );
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Participants participant = new Participants(
                        rs.getLong("codParticipant"),
                        rs.getString("nomPart"),
                        rs.getString("prenomPart"),
                        rs.getString("organismePart"),
                        rs.getInt("cpPart"),
                        rs.getString("adrPart"),
                        rs.getString("villePart"),
                        rs.getString("paysPart"),
                        rs.getString("emailPart"),
                        rs.getString("dtInscription"),
                        rs.getInt("statut"),
                        rs.getString("password")
                );

                System.out.println("Ce compte existe");
                model.addAttribute("message", "Ce compte existe");
                session.setAttribute("utilisateur", participant);

                rs.close();
                stmt.close();
                conn.close();
                return "redirect:/gestionconf";
            }

            rs.close();
            stmt.close();
            conn.close();

            System.out.println("Ce compte n'existe pas");
            model.addAttribute("message", "Ce compte n'existe pas");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("message", "Erreur lors de la connexion : " + e.getMessage());
        }

        return "redirect:/gestionconf";
    }

    @GetMapping("/deconnect")
    public String deconnectParticipants(HttpSession session, Model model) {
        System.out.println("Tentative de déconnexion");
        session.removeAttribute("utilisateur");
        return "redirect:/gestionconf";
    }
}