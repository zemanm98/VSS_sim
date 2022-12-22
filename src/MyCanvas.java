import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MyCanvas extends JPanel
{
    private int width = 0;
    private int height = 0;
    public static List<Bee> Beelist = new ArrayList<Bee>();

    public static List<Flower> Flowerlist = new ArrayList<Flower>();

    public static List<Hive> Hivelist = new ArrayList<Hive>();

    private final int SIZE = 10;

    private final Random rnd = new Random();
    // class constructor
    public MyCanvas(int wi, int he) {
        this.width = wi;
        this.height = he;
        this.setBackground (Color.GRAY);
        InitMap();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }
    public void InitMap(){
        for(int j = 0; j < 6; j++){
            int xx = rnd.nextInt(790);
            xx -= xx%5;
            int yy = rnd.nextInt(790);
            yy -= yy%5;
            Hive h = new Hive(xx, yy, 30, 30);
            Hivelist.add(h);
            for(int i = 1; i < 60; i++){
                int l = rnd.nextInt(8) + 1;
                Bee w = new Bee(l,5,this.width, this.height, SIZE, h.getMX(), h.getMY(), h);
                Beelist.add(w);
            }
        }

        for(int i = 1; i < 420; i++){
            int x = rnd.nextInt(790);
            x -= x%5;
            int y = rnd.nextInt(790);
            y -= y%5;
            int l = rnd.nextInt(8) + 1;
            Flower w = new Flower(x,y, 1);
            Flowerlist.add(w);
        }

    }

    // paint() method to draw inside the canvas
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int j = 0; j < Beelist.size(); j++) {
            Beelist.get(j).step();
            g.setColor(Color.yellow);
            g.fillOval(Beelist.get(j).getX(), Beelist.get(j).getY(), SIZE, SIZE);
            if(!Beelist.get(j).getRetToHive()){
                for (int i = 0; i < Flowerlist.size(); i++) {
                    if(Math.pow(Beelist.get(j).getX() - Flowerlist.get(i).getX(), 2) <= 25.0 &&
                            Math.pow(Beelist.get(j).getY() - Flowerlist.get(i).getY(), 2) <= 25.0){
                        Beelist.get(j).setX(Flowerlist.get(i).getX());
                        Beelist.get(j).setY(Flowerlist.get(i).getY());
                    }
                    if(Beelist.get(j).getX() == Flowerlist.get(i).getX() && Beelist.get(j).getY() == Flowerlist.get(i).getY()){
                        if(!Beelist.get(j).sitsOnFlower()){
                            Beelist.get(j).setOnFlower();
                            Flowerlist.get(i).drainFood();
                        }
                    }
                }
            }
            if(!Beelist.get(j).isAlive()){
                Beelist.remove(Beelist.get(j));
            }
        }
        for (int i = 0; i < Flowerlist.size(); i++) {
            if(Flowerlist.get(i).isAlive()){
                int child = rnd.nextInt(500);
                if(child < 1){
                    if(Flowerlist.get(i).canHaveChild(Flowerlist)){
                        int rx = rnd.nextInt(40) - 20;
                        int ry = rnd.nextInt(40) - 20;
                        int x = Flowerlist.get(i).getX() + rx;
                        int y = Flowerlist.get(i).getY() + ry;
                        if(x < 0){
                            x = 0;
                        }
                        if(x > (this.width - SIZE)){
                            x = this.width - SIZE;
                        }
                        if(y < 0){
                            y = 0;
                        }
                        if(y > (this.height - SIZE)){
                            y = this.height - SIZE;
                        }
                        Flowerlist.add(new Flower(x, y, 20));
                    }
                }
                g.setColor(Color.green);
                g.fillOval(Flowerlist.get(i).getX(), Flowerlist.get(i).getY(), SIZE, SIZE);
            }
            else{
                Flowerlist.remove(Flowerlist.get(i));
            }

        }
        for(int i = 0; i < Hivelist.size(); i++){
            if(Hivelist.get(i).getResources() > 10){
                Hivelist.get(i).withdrawResources();
                int l = rnd.nextInt(8) + 1;
                Beelist.add(new Bee(l,5,this.width, this.height, SIZE, Hivelist.get(i).getMX(), Hivelist.get(i).getMY(), Hivelist.get(i)));
            }
            g.setColor(Color.red);
            g.fillRect(Hivelist.get(i).getX(), Hivelist.get(i).getY(),Hivelist.get(i).getWidth(), Hivelist.get(i).getHeight());
        }

        // adding specifications
    }

    public int getBees(){
        return Beelist.size();
    }
}
