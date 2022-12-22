
import java.util.Random;
public class Bee {
    private final int step;

    private final int width;

    private final int height;
    private int x;
    private int y;

    private int start_x;

    private int start_y;
    private int direction;

    private final int size;

    private int life;

    private int onFlower;

    private boolean retToHive = false;

    private int justReturned = 0;

    private boolean restInHive;

    private Random rnd = new Random();

    private Hive hive;

    public Bee(int ini_d, int st, int w, int h, int siz, int s_x, int s_y, Hive hiv){
        this.x = s_x;
        this.y = s_y;
        this.direction = ini_d;
        this.step = st;
        this.width = w;
        this.height = h;
        this.size = siz;
        this.onFlower = 0;
        this.start_x = s_x;
        this.start_y = s_y;
        this.hive = hiv;
        this.life = 1000;
        this.restInHive = false;
    }

    public void step() {
        int luck = rnd.nextInt(100);
        this.life--;
        if (!isOnFlower()) {
            if (!this.retToHive) {
                if(this.restInHive){
                    this.justReturned--;
                    if(this.justReturned == 0){
                        this.restInHive = false;
                    }
                }
                else{
                    int direction = 0;
                    int[] n_pos = new int[3];
                    if (luck < 35) {
                        int d = rnd.nextInt(4) + 1;
                        switch (d) {
                            case 1 -> n_pos = makeMove(this.x + this.step, this.y, this.direction);
                            case 2 -> n_pos = makeMove(this.x - this.step, this.y, this.direction);
                            case 3 -> n_pos = makeMove(this.x, this.y + this.step, this.direction);
                            case 4 -> n_pos = makeMove(this.x, this.y - this.step, this.direction);
                        }
                        direction = n_pos[2];
                    } else {
                        n_pos = makeMove(this.x, this.y, this.direction);
                        direction = n_pos[2];
                    }
                    if (luck < 4) {
                        direction = rnd.nextInt(7) + 1;
                    }
                    this.x = n_pos[0];
                    this.y = n_pos[1];
                    this.direction = direction;
                }
            }
            else {
                int[] xy = returnToHive(this.x, this.y, this.start_x, this.start_y);
                if (xy[0] < 0) {
                    this.retToHive = false;
                    this.restInHive = true;
                    this.justReturned = 15;
                    this.hive.addResources();
                    this.x = this.hive.getMX();
                    this.y = this.hive.getY();
                } else {
                    this.x = xy[0];
                    this.y = xy[1];
                }
            }
        }
    }


    private int[] makeMove(int x, int y, int dir){

        switch(dir){
            case 1:
                if(y > 0){
                    y -= this.step;
                }
                else{
                    dir = 5;
                }
                break;
            case 2:
                if(x < (this.width - this.size) && y > 0){
                    x += this.step;
                    y -= this.step;
                }
                else if(x < (this.width - this.size) && y <= 0){
                    x += this.step;
                    dir = 4;
                }
                else if(x >= (this.width - this.size) && y > 0){
                    y -= this.step;
                    dir = 8;
                }
                else{
                    dir = 6;
                }
                break;
            case 3:
                if(x < (this.width - this.size)){
                    x += this.step;
                }
                else{
                    dir = 7;
                }
                break;
            case 4:
                if(x < (this.width - this.size) && this.y < (this.height - this.size)){
                    x += this.step;
                    y += this.step;
                }
                else if(x >= (this.width - this.size) && y < (this.height - this.size)){
                    y += this.step;
                    dir = 6;
                }
                else if(x < (this.width - this.size) && y >= (this.height - this.size)){
                    x += this.step;
                    dir = 2;
                }
                else{
                    dir = 8;
                }
                break;
            case 5:
                if(y < (this.height - this.size)){
                    y += this.step;
                }
                else{
                    dir = 1;
                }
                break;
            case 6:
                if(x > 0 && y < (this.height - this.size)){
                    y += this.step;
                    x -= this.step;
                }
                else if(x <= 0 && y < (this.height - this.size)){
                    y += this.step;
                    dir = 4;
                }
                else if(x > 0 && y >= (this.height - this.size)) {
                    x -= this.step;
                    dir = 8;
                }
                else{
                    dir = 2;
                }
                break;
            case 7:
                if(x > 0){
                    x -= this.step;
                }
                else{
                    dir = 3;
                }
                break;
            case 8:
                if(x > 0 && y > 0){
                    y -= this.step;
                    x -= this.step;
                }
                else if(x <= 0 && y > 0){
                    y -= this.step;
                    dir = 2;
                }
                else if(x > 0 && y <= 0){
                    x -= this.step;
                    dir = 6;
                }
                else{
                    dir = 4;
                }
                break;
        }
        if(x < 0){
            x = 0;
        }
        else if(x > (this.width - this.size)){
            x = (this.width - this.size);
        }

        if(y < 0){
            y = 0;
        }
        else if(y > (this.height - this.size)){
            y = (this.height - this.size);
        }
        return new int[]{x, y, dir};
    }

    public int[] returnToHive(int x, int y, int s_x, int s_y){
        if(Math.pow((x - s_x), 2) < 225.0 && Math.pow((y - s_y), 2) < 225.0){
            return new int[]{-1, -1};
        }
        if(x == s_x){
            if(y > s_y){
                y -= this.step;
            }
            else if(y < s_y){
                y += this.step;
            }
        }
        else if(y == s_y){
            if(x > s_x){
                x -= this.step;
            }
            else if(x < s_x){
                x += this.step;
            }
        }
        else{
            double slope = (double) (s_y - y) / (s_x - x);
            double b = s_y - (slope * s_x);
            int n_x = 0;
            if(x > s_x){
                n_x = x - this.step;
            }
            else{
                n_x = x + this.step;
            }
            int n_y = (int) (((int) (slope * n_x)) + b);
            float angle = (float) Math.atan2(n_y - y, n_x - x);
            x = (int) (x + (6 * Math.cos(angle)));
            y = (int) (y + (6 * Math.sin(angle)));
        }
        return new int[]{x,y};
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int xx) {
        this.x = xx;
    }

    public void setY(int yy) {
        this.y = yy;
    }

    public void setOnFlower() {
        this.onFlower = 10;
    }

    public boolean isOnFlower(){
        if(this.onFlower > 0){
            this.onFlower--;
            if(this.onFlower == 0){
                this.retToHive = true;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean sitsOnFlower(){
        if(this.onFlower > 0){
            return true;
        }
        return false;
    }

    public boolean getRetToHive(){
        return this.retToHive;
    }

    public boolean isAlive(){
        if(this.life > 0){
            return true;
        }
        return false;
    }
}
