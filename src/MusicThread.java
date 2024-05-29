import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicThread extends Thread {

    private String filePath = null;
    public static String sendCardsMusicPath = "music/sendCards.wav";
    public static String moveCardsMusicPath = "music/moveCards.wav";

    public MusicThread(String filePath_) {
        filePath = filePath_;
    }

    @Override
    public void run() {
        try {
            File f = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
