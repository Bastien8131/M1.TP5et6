package utcapitole.miage.tp5et6.db;

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

            stmt.execute(utcapitole.miage.tp5et6.model.gestionconf.Conferences.CREATE_TABLE_SQL);
            stmt.execute(utcapitole.miage.tp5et6.model.gestionconf.Participants.CREATE_TABLE_SQL);
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS INSCRIPTIONS (" +
                            "codCongres INTEGER, " +
                            "codParticipant INTEGER, " +
                            "dateInscription TEXT, " +
                            "PRIMARY KEY (codCongres, codParticipant), " +
                            "FOREIGN KEY (codCongres) REFERENCES CONFERENCES(CodCongres), " +
                            "FOREIGN KEY (codParticipant) REFERENCES PARTICIPANTS(codParticipant)" +
                            ")"
            );

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

            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (1, 'Etudiant')");
            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (2, 'Universitaire')");
            stmt.execute("INSERT INTO STATUS (codStatus, nomStatus) VALUES (3, 'Entreprise')");

            stmt.execute(
                    "INSERT INTO ACTIVITES (codAct, nomAct, prixAct) " +
                            "VALUES (1, 'Visite guidée de la ville', 120)"
            );


            stmt.close();
            conn.close();
            System.out.println("Values inserted into STATUS table successfully.");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des tables : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
