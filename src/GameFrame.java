
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;



public class GameFrame extends JFrame {

    GamePanel panel = new GamePanel();  
    
    public GameFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
              
        this.add(panel);
        
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        
        this.setLocationRelativeTo(null);
        
        
    }

}
