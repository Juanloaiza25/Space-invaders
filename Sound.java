import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Sound {

    private Clip clip;

    // Constructor que carga el archivo de sonido
    public Sound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Método para reproducir el sonido
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    // Método para detener el sonido
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Método para reiniciar el sonido (para efectos cortos como disparos)
    public void restart() {
        if (clip != null) {
            clip.setFramePosition(0); // Reinicia desde el inicio
            clip.start();
        }
    }
}