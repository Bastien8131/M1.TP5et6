package utcapitole.miage.tp5et6.controller.gestionconf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utcapitole.miage.tp5et6.db.DBConfig;
import utcapitole.miage.tp5et6.model.gestionconf.Conferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());

            // Récupération des thématiques
            List<Map<String, Object>> thematiques = new ArrayList<>();
            PreparedStatement stmtTh = conn.prepareStatement("SELECT * FROM THEMATIQUES");
            ResultSet rsTh = stmtTh.executeQuery();

            while (rsTh.next()) {
                Map<String, Object> thematique = new HashMap<>();
                thematique.put("codTh", rsTh.getInt("codTh"));
                thematique.put("nomTh", rsTh.getString("nomTh"));
                thematiques.add(thematique);
            }

            rsTh.close();
            stmtTh.close();

            // Récupération des activités
            List<Map<String, Object>> activites = new ArrayList<>();
            PreparedStatement stmtAct = conn.prepareStatement("SELECT * FROM ACTIVITES");
            ResultSet rsAct = stmtAct.executeQuery();

            while (rsAct.next()) {
                Map<String, Object> activite = new HashMap<>();
                activite.put("codAct", rsAct.getInt("codAct"));
                activite.put("nomAct", rsAct.getString("nomAct"));
                activite.put("prixAct", rsAct.getDouble("prixAct"));
                activites.add(activite);
            }

            rsAct.close();
            stmtAct.close();
            conn.close();

            model.addAttribute("thematiques", thematiques);
            model.addAttribute("activites", activites);

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
            @RequestParam(required = false) List<Integer> activites,
            @RequestParam(required = false) List<Integer> thematiques,
            Model model
    ) {
        if (activites == null) {
            activites = new ArrayList<>();
        }
        if (thematiques == null) {
            thematiques = new ArrayList<>();
        }

        try {
            // Obtenir le prochain ID
            Long nextId;
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(CodCongres) FROM CONFERENCES");
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getObject(1) != null) {
                nextId = rs.getLong(1) + 1;
            } else {
                nextId = 1L;
            }
            rs.close();
            stmt.close();
            conn.close();

            Conferences conference = new Conferences(
                    nextId,
                    titreCongres,
                    numEditionCongres,
                    dtDebutCongres,
                    dtFinCongres,
                    urlSiteWebCongres,
                    thematiques,
                    activites
            );

            int result = conference.insertDB(dbConfig.getDburl());

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
        List<Conferences> conferences = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(dbConfig.getDburl());
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CONFERENCES");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long codCongres = rs.getLong("CodCongres");

                // Récupérer les thématiques associées
                List<Integer> thematiques = new ArrayList<>();
                PreparedStatement stmtTh = conn.prepareStatement(
                        "SELECT codTh FROM CONF_THEMATIQUES WHERE codCongres = ?"
                );
                stmtTh.setLong(1, codCongres);
                ResultSet rsTh = stmtTh.executeQuery();
                while (rsTh.next()) {
                    thematiques.add(rsTh.getInt("codTh"));
                }
                rsTh.close();
                stmtTh.close();

                // Récupérer les activités associées
                List<Integer> activites = new ArrayList<>();
                PreparedStatement stmtAct = conn.prepareStatement(
                        "SELECT codAct FROM CONF_ACTIVITES WHERE codCongres = ?"
                );
                stmtAct.setLong(1, codCongres);
                ResultSet rsAct = stmtAct.executeQuery();
                while (rsAct.next()) {
                    activites.add(rsAct.getInt("codAct"));
                }
                rsAct.close();
                stmtAct.close();

                Conferences conference = new Conferences(
                        codCongres,
                        rs.getString("titreCongres"),
                        rs.getInt("numEditionCongres"),
                        rs.getString("dtDebutCongres"),
                        rs.getString("dtFinCongres"),
                        rs.getString("urlSiteWebCongres"),
                        thematiques,
                        activites
                );
                conferences.add(conference);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des conférences : " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("conferences", conferences);
        return "gestionconf/conferences/list/list";
    }
}