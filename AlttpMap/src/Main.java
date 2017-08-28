
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {

    //Declares images
    private Image im = new Image("images\\Eg-map.png");
    private Image link = new Image("images\\link.png");
    private Image lw = new Image("images\\lw.png");
    private Image dw = new Image("images\\dw.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Creates instance of framework used to talk between LUA script running in emulator, and this application
        DataManager dm = DataManager.getInstance();

        final double w = im.getWidth();
        final double h = im.getHeight();
        Pane layout = new Pane();
        Canvas canvas = new Canvas(w,h);
        Canvas linkCanvas = new Canvas(w,h);

        VBox values = new VBox();
        VBox labels = new VBox();
        //Sets size of Canvas
        canvas.setHeight(420);
        canvas.setWidth(400);

        //Compiles JavaFX componants and launches window

        layout.getChildren().addAll(canvas, linkCanvas);
        primaryStage.setMaxWidth(400);
        primaryStage.setWidth(400);
        primaryStage.setMaxHeight(420);
        primaryStage.setTitle("ALTTP EG/OW Map");
        primaryStage.setScene(new Scene(layout));
        primaryStage.show();

        //Main Loop, checks every frame to get current address values and maps them to the map
        //Then Draws the map and link's position on the map.

        new AnimationTimer() {
            int byteToCheck = -127;
            @Override
            public void handle(long now) {
                int owmap = dm.getOwmap();
                int dmap = dm.getDmap();
                int xpos = dm.getXpos();
                int ypos = dm.getYpos();
                int trow = dm.getTrow();
                int tcol = dm.getTcol();
                int wState = dm.getTileset();


                //Switch that tells the application to draw the overworld, underworld(EG) or the Dark World
                switch(wState) {
                    case 1:
                        redrawOW(canvas, wState, trow,tcol,xpos,ypos,lw);
                        drawLinkOW(linkCanvas, xpos, ypos, tcol, trow);
                        break;
                    case 3:
                        redrawOW(canvas, wState, trow,tcol,xpos,ypos, dw);
                        drawLinkOW(linkCanvas, xpos, ypos, tcol, trow);
                        break;
                    default:reDraw(canvas, dmap);
                        drawLink(linkCanvas, xpos, ypos, tcol, trow);
                        //System.out.println(dmap);
                        byteToCheck = dmap;
                        //System.out.println("UPDATING MAP");
                        break;
                }
            }
        }.start();

    }

    private void drawLink(Canvas canvas, int xpos, int ypos, int tcol, int trow){

        //Determine's Link's X and Y position and draws on the map.
        int sx = 0;
        int sy = 0;
        int sw = 399;
        int sh = 399;
        int dy = 128+(((trow%2)*64)+(ypos/2)/2);
        int dx = 128+(((tcol%2)*64)+(xpos/2)/2);
        int dw = 32;
        int dh = 32;

        canvas.getGraphicsContext2D().drawImage(link,sx,sy,sw,sh,dx,dy,dw,dh);
    }
    private void drawLinkOW(Canvas canvas, int xpos, int ypos, int tcol, int trow){
        //Secondary equation to draw link while on the overworld
        int sx = 0;
        int sy = 0;
        int sw = 399;
        int sh = 399;
        int dy = 192;
        int dx = 192;
        int dw = 32;
        int dh = 32;

        canvas.getGraphicsContext2D().drawImage(link,sx,sy,sw,sh,dx,dy,dw,dh);
    }



    private void reDraw(Canvas canvas, int index){
        //Draws the map
        int newDex = newByte(index);
        canvas.getGraphicsContext2D().clearRect(0,0,384,384);


        for (int i = 0; i < 3; i++){
            int xmod = 0;
            int ymod = 0;
            if (newDex % 16 == 15 && (newDex+(i-1)) %16==0 ) {
                ymod = 1;
            } else if (newDex % 16 == 0 && (newDex+(i-1)) %16 == 15){
                ymod = -1;
            }
            //Draws cells in a 3x3 square around link, pulling from sections of the image supplied
            canvas.getGraphicsContext2D().drawImage(im, (((i-1)+newDex)%16)*257,(((newDex/16)-1)+ymod)*257, 256,256,i*128,0,128,128);
            canvas.getGraphicsContext2D().drawImage(im, (((i-1)+newDex)%16)*257,(((newDex/16)+0)+ymod)*257, 256,256,i*128,128,128,128);
            canvas.getGraphicsContext2D().drawImage(im, (((i-1)+newDex)%16)*257,(((newDex/16)+1)+ymod)*257, 256,256,i*128,256,128,128);
        }
    }

    //Checks the signed value and converts to an unsigned value.
    public int newByte(int b){
        int newB = b;
        if (b < 0) {
            newB = b + 256;
        }
        return newB;
    }
    //Code was used when program read from a file that the LUA Script wrote too. No Longer used
    /*
    public ArrayList<Integer> newReader(String file){
        ArrayList<Integer> list = new ArrayList<Integer>();
        Byte retB = 0;
        

        try {
            for (String line: Files.readAllLines(Paths.get("src\\Sample\\"+ file))){
                list.add(Integer.valueOf(line));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
    */
    //Draws the overworld
    private void redrawOW(Canvas canvas, int worldState, int trow,int tcol,int xpos, int ypos, Image world){
        int ymod = 0;

        canvas.getGraphicsContext2D().clearRect(0,0,384,384);

        for (int i = 0; i < 3; i++) {
            canvas.getGraphicsContext2D().drawImage(world, ((((i - 1) + tcol)) * 128)+(xpos/2)-64-(tcol*2), ((((trow) - 1)) * 128)+(ypos/2)-64-(trow*2), 128, 128, i * 128, 0, 128, 128);
            canvas.getGraphicsContext2D().drawImage(world, ((((i - 1) + tcol)) * 128)+(xpos/2)-64-(tcol*2), ((((trow))) * 128)+(ypos/2)-64-(trow*2), 128, 128, i * 128, 128, 128, 128);
            canvas.getGraphicsContext2D().drawImage(world, ((((i - 1) + tcol)) * 128)+(xpos/2)-64-(tcol*2), ((((trow) + 1)) * 128)+(ypos/2)-64-(trow*2), 128, 128, i * 128, 256, 128, 128);        }
    }
}


