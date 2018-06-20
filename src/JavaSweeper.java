import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame {
    private Game game;
    private JLabel jLabel;
    private JPanel panel;
    private final int COLS = 10;
    private final int ROWS = 10;
    private final int BOMBS = 15;
    private final int IMAGE_SIZE = 50;


    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        jLabel = new JLabel("WELCOME!",SwingConstants.CENTER);
        add(jLabel, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage(
                            (Image) game.getBox(coord).image,
                            coord.getX() * IMAGE_SIZE,
                            coord.getY() * IMAGE_SIZE,
                            this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coord);
                    panel.repaint();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                    panel.repaint();
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                    panel.repaint();
                }
                jLabel.setText(getMessage());

            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().getX() * IMAGE_SIZE,
                Ranges.getSize().getY() * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getGameState()) {
            case PLAYED:
                return "Think twice!";
            case BOMBED:
                return "You lose!";
            case WINNER:
                return "Congratulations";
            default:
                return "Welcome";
        }

    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaSweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String fileName = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }
}
