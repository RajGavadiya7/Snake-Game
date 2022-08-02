
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.sound.sampled.*;
import javax.swing.Timer;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    Clip clip;
    AudioInputStream audioInputStream;

    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 150;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyparts = 5;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        bodyparts = 5;
        direction = 'R';
        running = false;
        Sound();
        startGame();

    }

    public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        new GamePanel();
        

        SnakeGame s = new SnakeGame();
        new GameFrame();
    }

    public void startGame() {

        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        
   

    }

    public void Sound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStream = AudioSystem.getAudioInputStream(new File("so.wav").getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }


    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {

        if (running) {

//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);
//            }_
// Score printing 
            g.setColor(Color.blue);
            g.setFont(new Font("PLAIN", Font.BOLD, 30));
            g.drawString("Score : " + applesEaten, 5, 30);
//  draw apple             
            Toolkit t = Toolkit.getDefaultToolkit();
            Image img = t.getImage("img.jpg");
            g.drawImage(img, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);

            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                } else {
                    g.setColor(Color.CYAN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }

            }
        } else {

            gameOver(g);

        }

    }

    public void eat() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File file = new File("eat.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    public void end() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File file = new File("end.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void newApple() {

        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void move() {

        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {

            bodyparts++;
            newApple();
            applesEaten++;

            try {
                eat();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void checkCollision() {

        // this cheks if head collides with body;
        for (int i = bodyparts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] > SCREEN_WIDTH) {
            running = false;

        }
        if (y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("PLAIN", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.blue);
        g.setFont(new Font("PLAIN", Font.ITALIC, 20));
        g.drawString("Score : " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score :     ")) / 2, SCREEN_HEIGHT / 2 + UNIT_SIZE * 2);

        clip.stop();

       try {
                end();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollision();

        }
        repaint();

    }

    //--------------------------------------------------------------------------
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

                case KeyEvent.VK_SPACE:
                    if (!running) {
                        running = true;
                    }
                {
                    try {
                        restart(); 
                        break;
                    
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }
    }
}
