package utcapitole.miage.tp5et6.model.gestionconf;

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
    private StatutParticipants statut;
    private String password;

    public Participants(Long codParticipant, String nomPart, String prenomPart, String organismePart, Integer cpPart, String adrPart, String villePart, String paysPart, String emailPart, String dtInscription, StatutParticipants statut, String password) {
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

    public StatutParticipants getStatut() { return statut; }

    public void setStatut(StatutParticipants statut) { this.statut = statut; }

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
        "statut TEXT, " +
        "password TEXT" +
        ")";
}


