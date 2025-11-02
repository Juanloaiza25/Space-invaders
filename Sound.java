import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {

    private Clip clip;
    private static double volumenGlobal = 0.5; // Volumen global (0.0 a 1.0)

    // Constructor que carga el archivo de sonido
    public Sound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            aplicarVolumen(); // Aplicar volumen al crear el clip
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Método para reproducir el sonido
    public void play() {
        if (clip != null) {
            aplicarVolumen(); // Asegurar que tiene el volumen actual
            clip.setFramePosition(0); // Reiniciar posición
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
            aplicarVolumen(); // Asegurar volumen actual
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // Método privado para aplicar el volumen al clip
    private void aplicarVolumen() {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                double value = volumenGlobal <= 0.01 ? 0.01 : volumenGlobal;
                float dB = (float) (Math.log10(value) * 20.0);
                // Limitar el valor dB al rango permitido
                dB = Math.max(gainControl.getMinimum(), Math.min(dB, gainControl.getMaximum()));
                gainControl.setValue(dB);
            } catch (Exception e) {
                // Silenciar errores si el control no está disponible
            }
        }
    }

    // Método estático para cambiar el volumen global (afecta a todos los sonidos)
    public static void setVolumenGlobal(double volumen) {
        volumenGlobal = Math.max(0.01, Math.min(1.0, volumen));
    }

    // Método de instancia para compatibilidad (actualiza el volumen de este clip)
    public void setVolume(double value) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                double vol = value <= 0.01 ? 0.01 : value;
                float dB = (float) (Math.log10(vol) * 20.0);
                dB = Math.max(gainControl.getMinimum(), Math.min(dB, gainControl.getMaximum()));
                gainControl.setValue(dB);
            } catch (Exception e) {
                // Silenciar errores
            }
        }
    }

    // Método para obtener el volumen global actual
    public static double getVolumenGlobal() {
        return volumenGlobal;
    }

    // Método para liberar recursos
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}