package utcapitole.miage.tp5et6;

import utcapitole.miage.tp5et6.db.DB;
import utcapitole.miage.tp5et6.db.DBConfig;
import utcapitole.miage.tp5et6.model.gestionconf.Conferences;
import utcapitole.miage.tp5et6.model.gestionconf.Participants;

import java.util.List;

public class InitDB {

    public static void main(String[] args) {
        DBConfig dbconfig = new DBConfig();
        Participants p = new Participants(
                1L,
                "Dupont",
                "Jean",
                "Université de Toulouse",
                31000,
                "1 rue de la République",
                "Toulouse",
                "France",
                "dupont.jean@gmail.com",
                "2023-10-01",
                1,
                "password123"
        );

        Conferences c = new Conferences(
                1L,
                "Conférence sur la gestion de la base de données",
                1,
                "2023-10-01",
                "2023-10-03",
                "www.conference.com",
                List.of(1, 2, 3),
                List.of(1, 2)
        );

        // Test connection
        if (DB.testConnect(dbconfig.getDburl())) {
            System.out.println("Connection to the database established successfully.");
        } else {
            System.out.println("Failed to connect to the database.");
        }

        // Create database
        if (DB.createDatabase(dbconfig.getDburl(), dbconfig.getDbpath())) {
            System.out.println("Database created successfully.");
        } else {
            System.out.println("Failed to create the database.");
        }

        // Create tables
        if (DB.createTables(dbconfig.getDburl())) {
            System.out.println("Tables created successfully.");
        } else {
            System.out.println("Failed to create tables.");
        }

        DB.initTables(dbconfig.getDburl());

        p.insertDB(dbconfig.getDburl());
        c.insertDB(dbconfig.getDburl());

        DB.displayTables(dbconfig.getDburl());


    }
}
