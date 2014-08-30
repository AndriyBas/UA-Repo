package ua.com.oyster.hotpotato;

import android.graphics.Bitmap;

/**
 * Created by linkbe on 8/30/14.
 */
public class Potato {

    private int x;
    private int y;
    private Bitmap image;


    private int vx = 1;
    private int vy = 1;

    public Potato(int x, int y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        image = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void move() {
        this.x += vx;
        this.y += vy;
    }

    public void changeVelocity(int newVx, int newVy) {
        vx = newVx;
        vy = newVy;
    }

    public int getVy() {
        return vy;
    }

    public int getVx() {
        return vx;
    }
}
