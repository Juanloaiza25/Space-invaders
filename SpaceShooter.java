import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class SpaceShooter {

    static Nave nave; 
    static List<NaveEnemiga> navesEnemigas = new ArrayList<>();
    static List<NaveEnemiga> navesAEliminar = new ArrayList<>();
    static List<Disparo> disparosAEliminar = new ArrayList<>();
    static List<Disparo> disparos = new ArrayList<>();  // <-- Declaración de disparos
    static NaveEnemiga naveEnemiga = new NaveEnemiga(0, 0, 30, 30, true);
    static int direccion = 1;
    static boolean gameOver = false;

    public static void main(String[] args) {

        nave = new Nave(370, 520, 30, 30, 5, disparos);
        
        int anchoPanel = 800;
        int altoPanel = 600;
        int anchoNaveEnemiga = naveEnemiga.getAncho();
        int altoNaveEnemiga = naveEnemiga.getAlto();
        int espacioEntreNaves = 15;
        int numeroDeNaves = (anchoPanel - espacioEntreNaves) / (anchoNaveEnemiga + espacioEntreNaves);
        int numeroDeFilas = 5;

        Sound MusicaFondo = new Sound("./Assets/spaceinvaders1.wav");
        Sound SoundGameOver = new Sound("./Assets/gameover.wav");
        Sound SoundGameWon = new Sound("./Assets/winsound.wav");
        MusicaFondo.play();

        JFrame ventana = new JFrame("Space Shooter");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(anchoPanel, altoPanel);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setIconImage(new ImageIcon("./Assets/spaceinvaders_128_icon.png").getImage());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, anchoPanel, altoPanel);
                nave.dibujar(g);
                
                // Dibujar y mover disparos
                List<Disparo> disparosAEliminar = new ArrayList<>();
                for (Disparo disparo : disparos) {
                    disparo.mover();
                    disparo.dibujar(g);
                    if (disparo.fueraDePantalla(altoPanel)) {
                        disparosAEliminar.add(disparo);
                    }
                }
                disparos.removeAll(disparosAEliminar);  // Elimina los disparos fuera de pantalla

                // Dibujar las naves enemigas
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.dibujar(g);
                }
            }
        };

        ventana.add(panel);
        ventana.setVisible(true);

        // Inicializar naves enemigas
        for (int i = 1; i < numeroDeNaves / 2; i++) {
            int x = i * (anchoNaveEnemiga + espacioEntreNaves);
            for (int j = 1; j < numeroDeFilas; j++) {
                int y = j * (altoNaveEnemiga + espacioEntreNaves);
                navesEnemigas.add(new NaveEnemiga(x, y, anchoNaveEnemiga, altoNaveEnemiga, true));
            }
        }
        // Verificar colisiones entre disparos y naves enemigas (todavía sin implementar)



        // Agregar KeyListener para mover la nave y disparar
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                nave.keyPressed(e);
                panel.repaint();
            }
        });

            
        Timer timer = new Timer(50, e -> {

            
            
            boolean cambiarDireccion = false;

            // Mover las naves enemigas
            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                naveEnemiga.setX(naveEnemiga.getX() + direccion * 5);
                if (naveEnemiga.getX() <= 0 || naveEnemiga.getX() + anchoNaveEnemiga >= anchoPanel - 30) {
                    cambiarDireccion = true;
                }
            }
            
            // Cambiar la dirección de las naves si alcanzan los bordes
            if (cambiarDireccion) {
                direccion *= -1;
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.setY(naveEnemiga.getY() + altoNaveEnemiga + espacioEntreNaves);
                }
            }

            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                for (Disparo disparo : disparos) {
                    if (naveEnemiga.colision(disparo)) {
                        navesAEliminar.add(naveEnemiga);  // Marcar la nave para eliminación
                        disparosAEliminar.add(disparo);   // Marcar el disparo para eliminación
                    }
                }
            }
            navesEnemigas.removeAll(navesAEliminar);
            disparos.removeAll(disparosAEliminar);

            if (navesEnemigas.isEmpty()) {
                gameOver = true;
                if (gameOver) {
                    MusicaFondo.stop();
                    SoundGameWon.play();
                    JOptionPane.showMessageDialog(ventana, "¡Has ganado!", "¡Has ganado!", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }

            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                if (naveEnemiga.getY() + naveEnemiga.getAlto() >= nave.getY()) {
                    gameOver = true;
                    break;
                }
            }

            
            if (gameOver) {
                MusicaFondo.stop();
                SoundGameOver.play();
                JOptionPane.showMessageDialog(ventana, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                
            }
            
            panel.repaint();
        });
        
        timer.start();
    }
}

    
