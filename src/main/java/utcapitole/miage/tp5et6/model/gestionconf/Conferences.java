package utcapitole.miage.tp5et6.model.gestionconf;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Conferences {
    private Long CodCongres;
    private String titreCongres;
    private Integer numEditionCongres;
    private String dtDebutCongres;
    private String dtFinCongres;
    private String urlSiteWebCongres;
    private List<Integer> thematiques;
    private List<Integer> activites;
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS CONFERENCES (" +
                    "CodCongres INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titreCongres TEXT NOT NULL, " +
                    "numEditionCongres INTEGER, " +
                    "dtDebutCongres TEXT, " +
                    "dtFinCongres TEXT, " +
                    "urlSiteWebCongres TEXT, " +
                    ")";

    // Premier constructeur
    public Conferences(Long codCongres, String titreCongres, Integer numEditionCongres,
                       String dtDebutCongres, String dtFinCongres, String urlSiteWebCongres,
                       List<Integer> thematiques, List<Integer> activites) {
        CodCongres = codCongres;
        this.titreCongres = titreCongres;
        this.numEditionCongres = numEditionCongres;
        this.dtDebutCongres = dtDebutCongres;
        this.dtFinCongres = dtFinCongres;
        this.urlSiteWebCongres = urlSiteWebCongres;
        this.thematiques = thematiques;
        this.activites = activites;
    }

    // Second constructeur (qui prend des chaînes de caractères)
    public Conferences(Long codCongres, String titreCongres, Integer numEditionCongres,
                       String dtDebutCongres, String dtFinCongres, String urlSiteWebCongres,
                       String thematiques, String activites) {
        CodCongres = codCongres;
        this.titreCongres = titreCongres;
        this.numEditionCongres = numEditionCongres;
        this.dtDebutCongres = dtDebutCongres;
        this.dtFinCongres = dtFinCongres;
        this.urlSiteWebCongres = urlSiteWebCongres;
        this.thematiques = stringToIntegerList(thematiques);
        this.activites = stringToIntegerList(activites);
    }

    public Long getCodCongres() {
        return CodCongres;
    }

    public void setCodCongres(Long codCongres) {
        CodCongres = codCongres;
    }

    public String getTitreCongres() {
        return titreCongres;
    }

    public void setTitreCongres(String titreCongres) {
        this.titreCongres = titreCongres;
    }

    public Integer getNumEditionCongres() {
        return numEditionCongres;
    }

    public void setNumEditionCongres(Integer numEditionCongres) {
        this.numEditionCongres = numEditionCongres;
    }

    public String getDtDebutCongres() {
        return dtDebutCongres;
    }

    public void setDtDebutCongres(String dtDebutCongres) {
        this.dtDebutCongres = dtDebutCongres;
    }

    public String getDtFinCongres() {
        return dtFinCongres;
    }

    public void setDtFinCongres(String dtFinCongres) {
        this.dtFinCongres = dtFinCongres;
    }

    public String getUrlSiteWebCongres() {
        return urlSiteWebCongres;
    }

    public void setUrlSiteWebCongres(String urlSiteWebCongres) {
        this.urlSiteWebCongres = urlSiteWebCongres;
    }

    public List<Integer> getThematiques() {
        return thematiques;
    }

    public void setThematiques(List<Integer> thematiques) {
        this.thematiques = thematiques;
    }

    public List<Integer> getActivites() {
        return activites;
    }

    public void setActivites(List<Integer> activites) {
        this.activites = activites;
    }

    private List<String> stringToList(String str) {
        String[] strArr = str.split(",");
        System.out.println(Arrays.toString(strArr));
        return Arrays.asList(strArr);
    }

    private List<Integer> stringToIntegerList(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] strArr = str.split(",");
        List<Integer> intList = new ArrayList<>();
        for (String s : strArr) {
            try {
                intList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Impossible de convertir '" + s + "' en entier. Ignorer.");
            }
        }
        return intList;
    }

    public int insertDB(String url){
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url);
//            java.sql.Statement stmt = conn.createStatement();

            PreparedStatement stmtC = conn.prepareStatement(
                    "INSERT INTO CONFERENCES (" +
                            "CodCongres," +
                            "titreCongres," +
                            "numEditionCongres," +
                            "dtDebutCongres," +
                            "dtFinCongres," +
                            "urlSiteWebCongres) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );

            stmtC.setLong(1, this.CodCongres);
            stmtC.setString(2, this.titreCongres);
            stmtC.setInt(3, this.numEditionCongres);
            stmtC.setString(4, this.dtDebutCongres);
            stmtC.setString(5, this.dtFinCongres);
            stmtC.setString(6, this.urlSiteWebCongres);


            stmtC.executeUpdate();
            stmtC.close();

            PreparedStatement stmtC_A = conn.prepareStatement(
                    "INSERT INTO CONF_ACTIVITES (codCongres, codAct) " +
                            "VALUES (?, ?)"
            );

            PreparedStatement stmtC_T = conn.prepareStatement(
                    "INSERT INTO CONF_THEMATIQUES (codCongres, codTh) " +
                            "VALUES (?, ?)"
            );

            for(Integer a : activites){
                stmtC_A.setLong(1, this.CodCongres);
                stmtC_A.setInt(2, a);
                stmtC_A.executeUpdate();
            }

            for(Integer th : thematiques){
                stmtC_T.setLong(1, this.CodCongres);
                stmtC_T.setInt(2, th);
                stmtC_T.executeUpdate();
            }

            stmtC_A.close();
            stmtC_T.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'insertion dans la table PARTICIPANTS : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
