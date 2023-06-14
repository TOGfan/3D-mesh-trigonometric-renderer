
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

public class Board extends JPanel implements ActionListener {
                private final int B_WIDTH = 300;
            private final int B_HEIGHT = 300;
    public Camera camera;
    private ArrayList<Mesh> meshes;
    private final int DELAY = 10;
    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseMotionListener(new MouseAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
              setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
                meshes = new ArrayList<Mesh>();
                meshes.add(new Mesh("cube.txt"));
                camera = new Camera(B_HEIGHT,B_WIDTH,114,null);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);

        Toolkit.getDefaultToolkit().sync();
    }

                private Graphics2D g2dSetup(Graphics g){

                Graphics2D g2d = (Graphics2D) g;

                RenderingHints rh
                        = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHints(rh);
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.gray);
                return g2d;
            }
            private void draw(Graphics g) {
                camera.g2d=g2dSetup(g);
                for(Mesh a:meshes){
                    a.draw(camera);
                }
                Toolkit.getDefaultToolkit().sync();
            }
    @Override
    public void actionPerformed(ActionEvent e) {

        update();


        repaint();
    }

    private void update() {
        camera.simulate();
    }


    private class TAdapter extends KeyAdapter {


        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                camera.speedX = 0;
            }

            if (key == KeyEvent.VK_RIGHT) {
                camera.speedX = 0;
            }

            if (key == KeyEvent.VK_UP) {
                camera.speedZ = 0;
            }

            if (key == KeyEvent.VK_DOWN) {
                camera.speedZ = 0;
            }
            if (key == KeyEvent.VK_SPACE) {
                camera.speedY = 0;
            }

            if (key == KeyEvent.VK_CONTROL) {
                camera.speedY = 0;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                camera.speedX = -2;
            }

            if (key == KeyEvent.VK_RIGHT) {
                camera.speedX = 2;
            }

            if (key == KeyEvent.VK_UP) {
                camera.speedZ = 2;
            }

            if (key == KeyEvent.VK_DOWN) {
                camera.speedZ = -2;
            }
            if (key == KeyEvent.VK_SPACE) {
                camera.speedY = -2;
            }

            if (key == KeyEvent.VK_CONTROL) {
                camera.speedY = 2;
            }
        }

    }
    private class MouseAdapter extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e){
            int x=(e.getX()-B_WIDTH/2)*360/B_WIDTH;
            int y=(e.getY()-B_HEIGHT/2)*360/B_HEIGHT;
            camera.angleX=x+90;
            camera.angleY=y+90;
            //System.out.println(camera.angleX);
        }
    }
}
