import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SnakeGame {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        SnakeGame x = new SnakeGame();
        
        new GameFrame();

    }
}
//
//
//import javax.swing.JFrame;
//import java.awt.Color;
//
//import javax.swing.JFrame;
//
//public class SnakeGame {
//
//	public static void main(String[] args) {
//		
//		JFrame obj = new JFrame();
//		GamePlay gameplay= new GamePlay();
//		
//		obj.setBounds(10,10,905,700);
//		obj.setBackground(Color.DARK_GRAY);
//		obj.setResizable(false);
//		obj.setVisible(true);
//		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		obj.add(gameplay);
//	}
//
//}