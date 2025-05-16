package utcapitole.miage.tp5et6.controller.gestionconf;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/form")
    public String showForm(Model model) {
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

    @PostMapping("/insert")
    public String addParticipants(@ModelAttribute Participants participant, Model model) {
        System.out.println(participant);

        try {
            // Vérification de l'unicité de l'email
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PARTICIPANTS WHERE emailPart = ?"
            );
            checkStmt.setString(1, participant.getEmailPart());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (count > 0) {
                System.out.println("L'email de ce participant existe déjà");
                model.addAttribute("msgTitre", "Cet email de participant existe déjà");
                model.addAttribute("msgStatut", "erreur");
                model.addAttribute("participant", participant);
                conn.close();
                return "gestionconf/message/message";
            }

            // Insertion du participant dans la base de données
            int result = participant.insertDB(dbConfig.getDburl());
            conn.close();

            if (result > 0) {
                model.addAttribute("msgTitre", "Compte créé avec succès !");
                model.addAttribute("msgStatut", "ok");
                model.addAttribute("participant", participant);
            } else {
                model.addAttribute("msgTitre", "Erreur lors de la création du compte");
                model.addAttribute("msgStatut", "erreur");
                model.addAttribute("participant", participant);
            }

            return "gestionconf/message/message";
        } catch (Exception e) {
            System.out.println("Erreur lors de l'insertion dans la table PARTICIPANTS : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("msgTitre", "Erreur lors de la création du compte");
            model.addAttribute("msgStatut", "erreur");
            model.addAttribute("msgContenu", "Une erreur est survenue : " + e.getMessage());
            return "gestionconf/message/message";
        }
    }

    @GetMapping("/list")
    public String listParticipants(Model model) {
        List<Participants> participants = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PARTICIPANTS");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
                participants.add(participant);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des participants : " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("participants", participants);
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