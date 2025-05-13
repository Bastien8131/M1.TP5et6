package utcapitole.miage.tp5et6.db;

public class DBConfig {
    private String dbpath;
    private String dburl;

    public DBConfig() {
        this.dbpath = System.getProperty("user.dir") + "\\db\\conferences.db";
        this.dburl = "jdbc:sqlite:" + this.dbpath;
    }

    public DBConfig(String dbpath, String dburl) {
        this.dbpath = dbpath;
        this.dburl = dburl;
    }

    public String getDbpath() {
        return dbpath;
    }

    public void setDbpath(String dbpath) {
        this.dbpath = dbpath;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }
}
