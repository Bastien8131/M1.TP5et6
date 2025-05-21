package utcapitole.miage.tp5et6.model.gestionconf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Activites {
    private Integer codAct;
    private String nomAct;
    private Double prixAct;

    public Activites(Integer codAct, String nomAct, Double prixAct) {
        this.codAct = codAct;
        this.nomAct = nomAct;
        this.prixAct = prixAct;
    }

    public Integer getCodAct() {
        return codAct;
    }

    public void setCodAct(Integer codAct) {
        this.codAct = codAct;
    }

    public String getNomAct() {
        return nomAct;
    }

    public void setNomAct(String nomAct) {
        this.nomAct = nomAct;
    }

    public Double getPrixAct() {
        return prixAct;
    }

    public void setPrixAct(Double prixAct) {
        this.prixAct = prixAct;
    }

    public static List<Activites> findAll(String url){
        List<Activites> list = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ACTIVITES");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Activites(
                    rs.getInt("codAct"),
                    rs.getString("nomAct"),
                    rs.getDouble("prixAct")
                ));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
