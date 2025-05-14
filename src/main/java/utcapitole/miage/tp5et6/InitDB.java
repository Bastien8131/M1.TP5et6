package utcapitole.miage.tp5et6;

import utcapitole.miage.tp5et6.db.DB;
import utcapitole.miage.tp5et6.db.DBConfig;

public class InitDB {

    public static void main(String[] args) {
        DBConfig dbconfig = new DBConfig();

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

        DB.displayTables(dbconfig.getDburl());


    }
}
