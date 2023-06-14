import java.awt.*;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Mesh {

    private class Vertex{
        int x;
        int y;
        int z;
        public Vertex(){
            x=0;
            y=0;
            z=0;
        }
        public Vertex(int x,int y, int z){
            this.x=x;
            this.y=y;
            this.z=z;
        }
        public Vertex(String[] input){

            this.x=Integer.valueOf(input[0]);
            this.y=Integer.valueOf(input[1]);
            this.z=Integer.valueOf(input[2]);
        }
        private int get2DX(Camera camera){
            //return (x+camera.x+camera.x-(camera.width/2)*(camera.screenZ)/(z-camera.z))+camera.width/2;
            //return (camera.width/2)+camera.screenZ*(x-camera.x)/(z-camera.z);
            return (camera.width/2)+(int)(
                    tan(toRadians(camera.angleX)-
                            atan(
                                (double)(z-camera.z)
                                        /
                                (double)(x-camera.x)
                            )
                    )*(double)(camera.screenZ));
        }
        private int get2DY(Camera camera){
            //return (y+camera.y+camera.y-(camera.height/2)*(camera.screenZ)/(z-camera.z))+camera.height/2;
            //return (camera.width/2)+camera.screenZ*(y-camera.y)/(z-camera.z);
            return (camera.height/2)+(int)(
                    tan(toRadians(camera.angleY)-
                            atan(
                                    (double)(z-camera.z)
                                            /
                                    (double)(y-camera.y)
                            )
                    )*(double)(camera.screenZ));
        }
    }
    private class Edge{
        Vertex a;
        Vertex b;
        public Edge(){
            a=null;
            b=null;
        }
        public Edge(Vertex a, Vertex b){
            this.a=a;
            this.b=b;
        }
        public void drawEdge(Camera camera){
            Line2D l = new Line2D.Double(a.get2DX(camera), a.get2DY(camera), b.get2DX(camera), b.get2DY(camera));
            camera.g2d.draw(l);
        }
    }
    private ArrayList<Vertex> verteces;
    private ArrayList<Edge> edges;
    public Mesh(){
        verteces = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
    }
    public Mesh(String fileName){
        verteces = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        loadFromFile(fileName);
    }
    public void draw(Camera camera){
        for(Edge e:edges){
            e.drawEdge(camera);
        }
    }
    public void loadFromFile(String fileName){
        FileReader fr = null;
        String line = "";
        boolean readingEdges=false;
        try {
            fr = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
        }

        BufferedReader bfr = new BufferedReader(fr);

        try {
            while((line = bfr.readLine()) != null){
                if(!readingEdges){
                    if(line.length()==0){
                        readingEdges=true;
                        continue;
                    }
                    verteces.add(new Vertex(line.split(" ")));
                }else{
                    String[] temp=line.split(" ");
                    edges.add(new Edge(verteces.get(Integer.valueOf(temp[0])-1),verteces.get(Integer.valueOf(temp[1])-1)));
                }
            }
        } catch (IOException e) {
            System.out.println("read error");
        }

        try {
            fr.close();
        } catch (IOException e) {
            System.out.println("closing error");
        }
    }
}

