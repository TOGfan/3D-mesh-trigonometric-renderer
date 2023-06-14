import java.awt.*;

import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

public class Camera {
    public int x;
    public int y;
    public int z;
    public int angleX;
    public int angleY;
   // public int angleZ;
    public int speedX;
    public int speedY;
    public int speedZ;
    public int screenZ;
    public int height;
    public int width;
    public Graphics2D g2d;
    public Camera(int height, int width, int fov, Graphics2D g2d){
        x=width/2;
        y=height/2;
        z=0;
        angleX=90;
        angleY=90;
       // angleZ=0;
        screenZ= (int) (width/(2*tan(toRadians(fov/4))));
        this.height=height;
        this.width=width;
        this.g2d=g2d;
    }
    public void simulate(){
        x+=speedX;
        y+=speedY;
        z+=speedZ;
    }
}
