package utcapitole.miage.tp5et6.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

    public static boolean testConnect(String url){
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            return false;
        }
    }

    public static boolean createDatabase(String url, String path_to_db) {
        try {
            java.io.File dbFile = new java.io.File(path_to_db);
            if (!dbFile.exists()) {
                java.io.File parentDir = dbFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdir();
                }
                dbFile.createNewFile();
            }
            return testConnect(url);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de la base de données : " + e.getMessage());
            return false;
        }
    }

    public static boolean createTables(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();

            stmt.execute("DROP TABLE IF EXISTS CONF_THEMATIQUES");
            stmt.execute("DROP TABLE IF EXISTS CONF_ACTIVITES");
            stmt.execute("DROP TABLE IF EXISTS INSCRIPTIONS");
            stmt.execute("DROP TABLE IF EXISTS PARTICIPANTS");
            stmt.execute("DROP TABLE IF EXISTS CONFERENCES");
            stmt.execute("DROP TABLE IF EXISTS STATUS");
            stmt.execute("DROP TABLE IF EXISTS ACTIVITES");
            stmt.execute("DROP TABLE IF EXISTS THEMATIQUES");
            stmt.execute("DROP TABLE IF EXISTS CONF_STATUS");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS CONFERENCES (" +
                            "CodCongres INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "titreCongres TEXT NOT NULL, " +
                            "numEditionCongres INTEGER, " +
                            "dtDebutCongres TEXT, " +
                            "dtFinCongres TEXT, " +
                            "urlSiteWebCongres TEXT" +
                            ")"
            );
            stmt.execute(utcapitole.miage.tp5et6.model.gestionconf.Participants.CREATE_TABLE_SQL);

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS STATUS (" +
                            "codStatus INTEGER PRIMARY KEY, " +
                            "nomStatus TEXT NOT NULL " +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS ACTIVITES (" +
                            "codAct INTEGER PRIMARY KEY, " +
                            "nomAct TEXT NOT NULL, " +
                            "prixAct REAL NOT NULL " +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS THEMATIQUES (" +
                            "codTh INTEGER PRIMARY KEY, " +
                            "nomTh TEXT NOT NULL " +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS CONF_THEMATIQUES (" +
                            "codCongres INTEGER, " +
                            "codTh INTEGER, " +
                            "PRIMARY KEY (codCongres, codTh), " +
                            "FOREIGN KEY (codCongres) REFERENCES CONFERENCES(CodCongres), " +
                            "FOREIGN KEY (codTh) REFERENCES THEMATIQUES(codTh)" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS CONF_ACTIVITES (" +
                            "codCongres INTEGER, " +
                            "codAct INTEGER, " +
                            "PRIMARY KEY (codCongres, codAct), " +
                            "FOREIGN KEY (codCongres) REFERENCES CONFERENCES(CodCongres), " +
                            "FOREIGN KEY (codAct) REFERENCES ACTIVITES(codAct)" +
                            ")"
            );

            stmt.close();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void initTables(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();

            stmt.execute("DELETE FROM STATUS");
            stmt.execute("DELETE FROM ACTIVITES");
            stmt.execute("DELETE FROM THEMATIQUES");

            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (1, 'Etudiant')");
            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (2, 'Universitaire')");
            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (3, 'Entreprise')");

            stmt.execute(
                    "INSERT INTO ACTIVITES (codAct, nomAct, prixAct) " +
                            "VALUES (1, 'Visite guidée de la ville', 50)"
            );
            stmt.execute(
                    "INSERT INTO ACTIVITES (codAct, nomAct, prixAct) " +
                            "VALUES (2, 'repas de gala', 120)"
            );
            stmt.execute(
                    "INSERT INTO ACTIVITES (codAct, nomAct, prixAct) " +
                            "VALUES (3, 'repas rencontre', 70)"
            );

            PreparedStatement stmtTh = conn.prepareStatement(
                    "INSERT INTO THEMATIQUES (codTh, nomTh) VALUES (?, ?)"
            );

            String[] thList = {
                    "Comptabilité-Contrôle",
                    "Ressources Humaines",
                    "Marketing","Finance",
                    "Gestion des SI",
                    "Gestion de projets informatiques",
                    "Business Intelligence",
                    "Informatique Décisionnelle",
                    "Veille Stratégique"
            };

            for (int i = 0; i < thList.length; i++) {
                stmtTh.setInt(1, i);
                stmtTh.setString(2, thList[i]);
                stmtTh.executeUpdate();
            }

            stmt.close();
            conn.close();
            System.out.println("Values inserted into STATUS table successfully.");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des tables : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void displayTables(String url){
        try{
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();

            String[] queries = {
                    "SELECT * FROM ACTIVITES",
                    "SELECT * FROM THEMATIQUES",
                    "SELECT * FROM PARTICIPANTS",
                    "SELECT * FROM CONFERENCES",
                    "SELECT * FROM STATUS"
            };

            for (String query : queries) {
                ResultSet rs = stmt.executeQuery(query);
                int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println("\n");
                rs.close();
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des tables : " + e.getMessage());
            e.printStackTrace();
        }
    }
}