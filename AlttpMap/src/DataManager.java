import java.util.ArrayList;

//Generates the Singleton that works with the AddressManager framework

public class DataManager extends AddressManager {
    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        setOwmap(0);
        setDmap(0);
        setXpos(0);
        setYpos(0);
        setTrow(0);
        setTcol(0);
        setTileset(0);
    }

    //Declare Variables
    private int xpos;
    private int ypos;
    private int trow;
    private int tcol;
    private int dmap;
    private int owmap;
    private int tileset;

    //Getters and Setters for the addresses
    public int getTileset() {
        return tileset;
    }

    public void setTileset(int tileset) {
        this.tileset = tileset;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getTrow() {
        return trow;
    }

    public void setTrow(int trow) {
        this.trow = trow;
    }

    public int getTcol() {
        return tcol;
    }

    public void setTcol(int tcol) {
        this.tcol = tcol;
    }

    public int getDmap() {
        return dmap;
    }

    public void setDmap(int dmap) {
        this.dmap = dmap;
    }

    public int getOwmap() {
        return owmap;
    }

    public void setOwmap(int owmap) {
        this.owmap = owmap;
    }


    //Code was once a thread.
    @Override
    public void run(ArrayList<String> arrayList) {
        for (String s:arrayList) {
            String[] l = s.split("=");
            switch(l[0]){
                case "xpos": setXpos(Integer.valueOf(l[1]));break;
                case "ypos": setYpos(Integer.valueOf(l[1]));break;
                case "trow": setTrow(Integer.valueOf(l[1]));break;
                case "tcol": setTcol(Integer.valueOf(l[1]));break;
                case "dmap": setDmap(Integer.valueOf(l[1]));break;
                case "owmap": setOwmap(Integer.valueOf(l[1]));break;
                case "tileset": setTileset(Integer.valueOf(l[1]));break;
            }
        }

    }
}
