public class Hive {
    private int resources;

    private final int x;

    private final int y;

    private final int width;

    private final int height;
    public Hive(int xx, int yy, int w, int h){
        this.x = xx;
        this.y = yy;
        this.height = h;
        this.width = w;
    }

    public int getResources(){
        return this.resources;
    }

    public void withdrawResources(){
        this.resources = 0;
    }

    public void addResources(){
        this.resources++;
    }

    public int getMX() {
        return (int)(x + (this.width / 2));
    }

    public int getMY() {
        return (int)(y + (this.height / 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
