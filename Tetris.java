import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hiro on 16-8-19.
 */

public class Tetris extends JFrame {

    public Tetris() {

        Block block = new Block();
        this.addKeyListener(block);
        this.add(block);

        JMenu menu = new JMenu("Menu");
        JMenuBar menuBar = new JMenuBar();
        JMenu help = new JMenu("Help");
        help.add(new JMenuItem("About"));
        menu.add(new JMenuItem("Start"));
        JMenuItem parse = new JMenuItem("Parse");
        menu.add(parse);
        menu.add(new JMenuItem("Stop"));
        menuBar.add(menu);
        menuBar.add(help);

        this.setJMenuBar(menuBar);
        this.setTitle("Tetris");
        this.setBounds(300, 300, 120, 275);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //未起作用？why
        parse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();

                if (cmd.equals("Start")) {
                    block.test.notify();
                } else if (cmd.equals("Parse")) {
                    try {
                        block.test.wait();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else if (cmd.equals("Stop")) {
                    block.test.stop();
                    block.init_map();
                    block.new_block();
                }
            }
        });

    }

    public static void main(String[] args) {

        Tetris tetris = new Tetris();

    }



}
