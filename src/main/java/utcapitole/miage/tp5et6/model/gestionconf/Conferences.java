package utcapitole.miage.tp5et6.model.gestionconf;

import java.util.Arrays;
import java.util.List;

public class Conferences {
    private Long CodCongres;
    private String titreCongres;
    private Integer numEditionCongres;
    private String dtDebutCongres;
    private String dtFinCongres;
    private String urlSiteWebCongres;
    private List<String> thematiques;
    private List<String> activites;

    public Conferences(Long codCongres, String titreCongres, Integer numEditionCongres, String dtDebutCongres, String dtFinCongres, String urlSiteWebCongres, List<String> thematiques, List<String> activites) {
        CodCongres = codCongres;
        this.titreCongres = titreCongres;
        this.numEditionCongres = numEditionCongres;
        this.dtDebutCongres = dtDebutCongres;
        this.dtFinCongres = dtFinCongres;
        this.urlSiteWebCongres = urlSiteWebCongres;
        this.thematiques = thematiques;
        this.activites = activites;
    }

    public Conferences(Long codCongres, String titreCongres, Integer numEditionCongres, String dtDebutCongres, String dtFinCongres, String urlSiteWebCongres, String thematiques, String activites) {
        CodCongres = codCongres;
        this.titreCongres = titreCongres;
        this.numEditionCongres = numEditionCongres;
        this.dtDebutCongres = dtDebutCongres;
        this.dtFinCongres = dtFinCongres;
        this.urlSiteWebCongres = urlSiteWebCongres;
        this.thematiques = stringToList(thematiques);
        this.activites = stringToList(activites);
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

    public List<String> getThematiques() {
        return thematiques;
    }

    public void setThematiques(List<String> thematiques) {
        this.thematiques = thematiques;
    }

    public List<String> getActivites() {
        return activites;
    }

    public void setActivites(List<String> activites) {
        this.activites = activites;
    }

    private List<String> stringToList(String str) {
        String[] strArr = str.split(",");
        System.out.println(Arrays.toString(strArr));
        return Arrays.asList(strArr);
    }
}
