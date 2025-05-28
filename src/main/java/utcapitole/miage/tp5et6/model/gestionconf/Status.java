package utcapitole.miage.tp5et6.model.gestionconf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Status {
    private Integer codStatus;
    private String nomStatus;

    public Status(Integer codStatus, String nomStatus) {
        this.codStatus = codStatus;
        this.nomStatus = nomStatus;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
    }

    public String getNomStatus() {
        return nomStatus;
    }

    public void setNomStatus(String nomStatus) {
        this.nomStatus = nomStatus;
    }

    public static List<Status> findAll(String url){
        List<Status> list = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM STATUS");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Status(
                        rs.getInt("codStatus"),
                        rs.getString("nomStatus")
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