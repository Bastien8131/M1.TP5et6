package utcapitole.miage.tp5et6.model.gestionconf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Thematiques {
    private Integer codTh;
    private String nomTh;

    public Thematiques(Integer codTh, String nomTh) {
        this.codTh = codTh;
        this.nomTh = nomTh;
    }

    public Integer getCodTh() {
        return codTh;
    }

    public void setCodTh(Integer codTh) {
        this.codTh = codTh;
    }

    public String getNomTh() {
        return nomTh;
    }

    public void setNomTh(String nomTh) {
        this.nomTh = nomTh;
    }

    public static List<Thematiques> findAll(String url){
        List<Thematiques> list = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM THEMATIQUES");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Thematiques(
                    rs.getInt("codTh"),
                    rs.getString("nomTh")
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
