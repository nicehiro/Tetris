import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by hiro on 16-8-19.
 */
public class Block extends JPanel implements KeyListener {

    int blockType;
    int turnRotate;

    int x;
    int y;

    int map[][] = new int[12][22];

    Timer timer;
    Test test;

    public Block() {
        init_map();
        new_block();
        //timer = new Timer(1000, new TimeListener());
        //timer.start();
        test = new Test();
        test.start();
    }


    public void init_map() {

        for (int i=0; i<12; i++) {
            for (int j=0; j<22; j++) {
                map[i][j] = 0;
            }
        }

        for (int i=0; i<22; i++) {
            map[0][i] = 1;
            map[11][i] = 1;
        }

        for (int i=0; i<12; i++) {
            map[i][21] = 1;
        }
    }

    public void new_block() {
        blockType = (int)(Math.random()*1000) % 7;
        turnRotate = (int)(Math.random()*1000) % 4;

        x = 4;
        y = 0;

        if (gameover(x, y)) {
            init_map();
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }

    }

    public int shape[][][] = new int[][][] {
        //方形
            {{2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0},
                    {2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0},
                    {2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0},
                    {2,2,0,0,2,2,0,0,0,0,0,0,0,0,0,0}},
            //直形
            {{2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0},
                    {2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0},
                    {2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0},
                    {2,0,0,0,2,0,0,0,2,0,0,0,2,0,0,0}},
            //土形
            {{0,2,0,0,2,2,2,0,0,0,0,0,0,0,0,0},
                    {2,0,0,0,2,2,0,0,2,0,0,0,0,0,0,0},
                    {2,2,2,0,0,2,0,0,0,0,0,0,0,0,0,0},
                    {0,2,0,0,2,2,0,0,0,2,0,0,0,0,0,0}},
            //右向L
            {{2,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0},
                    {2,2,0,0,2,0,0,0,2,0,0,0,0,0,0,0},
                    {2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0},
                    {0,2,0,0,0,2,0,0,2,2,0,0,0,0,0,0}},
            //左向L
            {{0,0,2,0,2,2,2,0,0,0,0,0,0,0,0,0},
                    {2,0,0,0,2,0,0,0,2,2,0,0,0,0,0,0},
                    {2,2,2,0,2,0,0,0,0,0,0,0,0,0,0,0},
                    {2,2,0,0,0,2,0,0,0,2,0,0,0,0,0,0}},
            //右扭S
            {{2,0,0,0,2,2,0,0,0,2,0,0,0,0,0,0},
                    {0,2,2,0,2,2,0,0,0,0,0,0,0,0,0,0},
                    {2,0,0,0,2,2,0,0,0,2,0,0,0,0,0,0},
                    {0,2,2,0,2,2,0,0,0,0,0,0,0,0,0,0}},
            //左扭S
            {{0,2,0,0,2,2,0,0,2,0,0,0,0,0,0,0},
                    {2,2,0,0,0,2,2,0,0,0,0,0,0,0,0,0},
                    {0,2,0,0,2,2,0,0,2,0,0,0,0,0,0,0},
                    {2,2,0,0,0,2,2,0,0,0,0,0,0,0,0,0}}
    };



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i=0; i<16; i++) {
            if (shape[blockType][turnRotate][i] == 2)
                g.fillRect((x+i % 4+1)*10, (i/4+y)*10, 10, 10);
        }

        for (int i=0; i<12; i++) {
            for (int j=0; j<22; j++) {
                if (map[i][j] == 1)
                    g.drawRect(i*10, j*10, 10, 10);
                else if (map[i][j] == 2)
                    g.fillRect(i*10, j*10, 10, 10);
            }
        }


    }

    public boolean gameover(int x, int y) {
        if (collision(x, y, blockType, turnRotate)) {
            return true;
        }
        return false;
    }

    public boolean collision(int x, int y, int blockType, int turnRotate) {
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (((shape[blockType][turnRotate][i*4+j] == 2) && (map[x+j+1][y+i] == 1)) ||
                        ((shape[blockType][turnRotate][i*4+j] == 2) && (map[x+j+1][y+i] == 2)))
                    return true;
            }
        }
        return false;
    }

    public void delLine() {

        int time = 0;

        for (int i=0; i<22; i++) {
            for (int j=0; j<12; j++) {
                if (map[j][i] == 2) {
                    time ++;

                    if (time == 10) {

                        for (int a=1; a<11; a++) {
                            for (int b=i; b>0; b--) {
                                map[a][b] = map[a][b-1];
                            }
                        }
                    }

                }

            }
            time = 0;
        }
    }

    public void add_map(int x, int y, int blockType, int turnRotate) {

        for (int i=0; i<16; i++) {
            if (shape[blockType][turnRotate][i] == 2) {
                if (map[x+i%4+1][y+i/4] == 0) {
                    map[x+i%4+1][y+i/4] = 2;
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                down();
                break;
            case KeyEvent.VK_LEFT:
                left();
                break;
            case KeyEvent.VK_RIGHT:
                right();
                break;
            case KeyEvent.VK_UP:
                up();
                break;

        }
    }

    private void up() {

        int temp = (turnRotate + 1) % 4;
        if (!collision(x, y, blockType, temp))
            turnRotate = temp;

        repaint();
    }

    private void right() {
        if (!collision(x+1, y, blockType, turnRotate)) {
            x ++;
        }
        repaint();
    }

    private void left() {
        if (!collision(x-1, y, blockType, turnRotate)) {
            x --;
        }
        repaint();
    }

    private void down() {
        if (!collision(x, y+1, blockType, turnRotate)) {
            y ++;
            delLine();
        }
        repaint();
    }

    class TimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();

            if (!collision(x, y+1, blockType, turnRotate)) {
                y ++;
                delLine();
            } else {
                add_map(x, y, blockType, turnRotate);
                delLine();
                new_block();
            }

        }
    }

    class Test extends Thread {
        public void run() {
            while (true) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();

                if (!collision(x, y+1, blockType, turnRotate)) {
                    y ++;
                    delLine();
                } else {
                    add_map(x, y, blockType, turnRotate);
                    delLine();
                    new_block();
                }
            }

        }
    }

}
