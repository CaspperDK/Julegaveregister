public class Gave {
    private int gavelisteID;
    private String gave;
    private String gaveModtager;
    private String gaveGiver;
    private String gavePris;
    private String bought;

    Gave() {

    }

    Gave(String gave, String gaveModtager, String gaveGiver, String gavePris, String bought) {
        this.gave = gave;
        this.gaveModtager = gaveModtager;
        this.gaveGiver = gaveGiver;
        this.gavePris = gavePris;
        this.bought = bought;
    }

    public int getGavelisteID() {
        return gavelisteID;
    }

    public void setGavelisteID(int gavelisteID) {
        this.gavelisteID = gavelisteID;
    }

    public String getGave() {
        return gave;
    }

    public void setGave(String gave) {
        this.gave = gave;
    }

    public String getGaveModtager() {
        return gaveModtager;
    }

    public void setGaveModtager(String gaveModtager) {
        this.gaveModtager = gaveModtager;
    }

    public String getGaveGiver() {
        return gaveGiver;
    }

    public void setGaveGiver(String gaveGiver) {
        this.gaveGiver = gaveGiver;
    }

    public String getGavePris() {
        return gavePris;
    }

    public void setGavePris(String gavePris) {
        this.gavePris = gavePris;
    }

    public String getBought() {
        return bought;
    }
    public void setBought(String bought) {
        this.bought = bought;
    }

    @Override
    public String toString() {
        return "Gave: " + gave +
                ", Modtager: " + gaveModtager +
                ", Giver: " + gaveGiver +
                ", Pris: " + gavePris +
                ", Købt: " + bought +
                ", ID: " + gavelisteID;
    }
}
