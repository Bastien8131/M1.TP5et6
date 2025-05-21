package utcapitole.miage.tp5et6.model.gestionconf;

import java.sql.*;

public class Participants {
    private Long codParticipant;
    private String nomPart;
    private String prenomPart;
    private String organismePart;
    private Integer cpPart;
    private String adrPart;
    private String villePart;
    private String paysPart;
    private String emailPart;
    private String dtInscription;
    private Integer statut;
    private String password;
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS PARTICIPANTS (" +
                    "codParticipant INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nomPart TEXT NOT NULL, " +
                    "prenomPart TEXT, " +
                    "organismePart TEXT, " +
                    "cpPart INTEGER, " +
                    "adrPart TEXT, " +
                    "villePart TEXT, " +
                    "paysPart TEXT, " +
                    "emailPart TEXT, " +
                    "dtInscription TEXT, " +
                    "statut INTEGER, " +
                    "password TEXT" +
                    ")";

    public Participants(Long codParticipant, String nomPart, String prenomPart, String organismePart, Integer cpPart, String adrPart, String villePart, String paysPart, String emailPart, String dtInscription, Integer statut, String password) {
        this.codParticipant = codParticipant;
        this.nomPart = nomPart;
        this.prenomPart = prenomPart;
        this.organismePart = organismePart;
        this.cpPart = cpPart;
        this.adrPart = adrPart;
        this.villePart = villePart;
        this.paysPart = paysPart;
        this.emailPart = emailPart;
        this.dtInscription = dtInscription;
        this.statut = statut;
        this.password = password;
    }

    public Long getCodParticipant() {
        return codParticipant;
    }

    public void setCodParticipant(Long codParticipant) {
        this.codParticipant = codParticipant;
    }

    public String getNomPart() {
        return nomPart;
    }

    public void setNomPart(String nomPart) {
        this.nomPart = nomPart;
    }

    public String getPrenomPart() {
        return prenomPart;
    }

    public void setPrenomPart(String prenomPart) {
        this.prenomPart = prenomPart;
    }

    public String getOrganismePart() {
        return organismePart;
    }

    public void setOrganismePart(String organismePart) {
        this.organismePart = organismePart;
    }

    public Integer getCpPart() {
        return cpPart;
    }

    public void setCpPart(Integer cpPart) {
        this.cpPart = cpPart;
    }

    public String getAdrPart() {
        return adrPart;
    }

    public void setAdrPart(String adrPart) {
        this.adrPart = adrPart;
    }

    public String getVillePart() {
        return villePart;
    }

    public void setVillePart(String villePart) {
        this.villePart = villePart;
    }

    public String getPaysPart() {
        return paysPart;
    }

    public void setPaysPart(String paysPart) {
        this.paysPart = paysPart;
    }

    public String getEmailPart() {
        return emailPart;
    }

    public void setEmailPart(String emailPart) {
        this.emailPart = emailPart;
    }

    public String getDtInscription() {
        return dtInscription;
    }

    public void setDtInscription(String dtInscription) {
        this.dtInscription = dtInscription;
    }

    public Integer getStatut() { return statut; }

    public void setStatut(Integer statut) { this.statut = statut; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Participants{" +
                "codParticipant=" + codParticipant +
                ", nomPart='" + nomPart + '\'' +
                ", prenomPart='" + prenomPart + '\'' +
                ", organismePart='" + organismePart + '\'' +
                ", cpPart=" + cpPart +
                ", adrPart='" + adrPart + '\'' +
                ", villePart='" + villePart + '\'' +
                ", paysPart='" + paysPart + '\'' +
                ", emailPart='" + emailPart + '\'' +
                ", dtInscription='" + dtInscription + '\'' +
                ", statut='" + statut + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int insert(String url){
        if (this.checkEmail(url) == 1) return 2;

        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();

            PreparedStatement stmtP = conn.prepareStatement(
                    "INSERT INTO PARTICIPANTS (" +
                            "nomPart," +
                            "prenomPart," +
                            "organismePart," +
                            "cpPart," +
                            "adrPart," +
                            "villePart," +
                            "paysPart," +
                            "emailPart," +
                            "dtInscription," +
                            "statut," +
                            "password) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            stmtP.setString(1, this.nomPart);
            stmtP.setString(2, this.prenomPart);
            stmtP.setString(3, this.organismePart);
            stmtP.setInt(4, this.cpPart);
            stmtP.setString(5, this.adrPart);
            stmtP.setString(6, this.villePart);
            stmtP.setString(7, this.paysPart);
            stmtP.setString(8, this.emailPart);
            stmtP.setString(9, this.dtInscription);
            stmtP.setInt(10, this.statut);
            stmtP.setString(11, this.password);

            stmtP.executeUpdate();
            stmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'insertion dans la table PARTICIPANTS : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int update(String url){
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
            java.sql.Statement stmt = conn.createStatement();

            PreparedStatement stmtP = conn.prepareStatement(
                    "UPDATE PARTICIPANTS SET " +
                            "nomPart = ?," +
                            "prenomPart = ?," +
                            "organismePart = ?," +
                            "cpPart = ?," +
                            "adrPart = ?," +
                            "villePart = ?," +
                            "paysPart = ?," +
                            "emailPart = ?," +
                            "dtInscription = ?," +
                            "statut = ?," +
                            "password = ?" +
                            "WHERE codParticipant = ?"
            );

            stmtP.setString(1, this.nomPart);
            stmtP.setString(2, this.prenomPart);
            stmtP.setString(3, this.organismePart);
            stmtP.setInt(4, this.cpPart);
            stmtP.setString(5, this.adrPart);
            stmtP.setString(6, this.villePart);
            stmtP.setString(7, this.paysPart);
            stmtP.setString(8, this.emailPart);
            stmtP.setString(9, this.dtInscription);
            stmtP.setInt(10, this.statut);
            stmtP.setString(11, this.password);
            stmtP.setString(12, this.codParticipant.toString() );

            stmtP.executeUpdate();
            stmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'update dans la table PARTICIPANTS : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int checkEmail(String url){
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PARTICIPANTS WHERE emailPart = ?"
            );
            checkStmt.setString(1, this.getEmailPart());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();
            conn.close();

            return count > 0 ? 1 : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


