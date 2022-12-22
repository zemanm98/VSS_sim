import java.util.ArrayList;
import java.util.List;

public class Flower {
    private final int x;
    private final int y;
    private int food;
    private boolean isAlive;

    public Flower(int in_x, int in_y, int in_f){
        this.x = in_x;
        this.y = in_y;
        this.food = in_f;
        this.isAlive = true;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFood() {
        return food;
    }

    public void drainFood(){
        this.food--;
        if(this.food <= 0){
            this.isAlive = false;
        }
    }

    public boolean canHaveChild(List<Flower> flowers){
        int count = 0;
        for(int i = 0; i < flowers.size(); i++){
            if(Math.pow((flowers.get(i).getX() - this.x), 2) < 400.0 && Math.pow((flowers.get(i).getY() - this.y), 2) < 400.0){
                count++;
            }
        }
        if(count > 4){
            return false;
        }
        return true;
    }
    public boolean isAlive(){
        return this.isAlive;
    }
}
