import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class SpaceShooter {

    static Nave nave = new Nave(370, 520, 30, 30, 5); // Se inicializa el objeto nave en la posición (370, 520)                                                                         // ancho y alto de 30 y una velocidad de 5
    static List<NaveEnemiga> navesEnemigas = new ArrayList<>();
    static NaveEnemiga naveEnemiga = new NaveEnemiga(0, 0, 30, 30, true);
    static int direccion = 1;

    public static void main(String[] args) {
        int anchoPanel = 800;
        int altoPanel = 600;
        int anchoNaveEnemiga = naveEnemiga.getAncho();
        int altoNaveEnemiga = naveEnemiga.getAlto();
        int espacioEntreNaves = 15;
        int numeroDeNaves = (anchoPanel - espacioEntreNaves) / (anchoNaveEnemiga + espacioEntreNaves);
        int numeroDeFilas = 5;
        boolean gameOver = false;
        
        JFrame ventana = new JFrame("Space Shooter");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(anchoPanel, altoPanel);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        



        
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, anchoPanel, altoPanel);
                nave.dibujar(g);
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.dibujar(g);
                }
                
            }
        };
        ventana.add(panel);
        ventana.setVisible(true);
        
        for (int i = 1; i < numeroDeNaves / 2; i++) {
            int x = i * (anchoNaveEnemiga + espacioEntreNaves);
            for (int j = 1; j < numeroDeFilas; j++) {
                int y = j * (altoNaveEnemiga + espacioEntreNaves);
                navesEnemigas.add(new NaveEnemiga(x, y, anchoNaveEnemiga, altoNaveEnemiga, true));
            }
        }
        // Agregar KeyListener para mover la nave
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                nave.keyPressed(e);
                panel.repaint();
            }
        });

        Timer timer = new Timer(50, e -> {
            boolean cambiarDireccion = false;
            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                naveEnemiga.setX(naveEnemiga.getX() + direccion * 5);
                if (naveEnemiga.getX() <= 0 || naveEnemiga.getX() + anchoNaveEnemiga >= anchoPanel - 30) {
                    cambiarDireccion = true;
                }
            }
            if (cambiarDireccion) {
                direccion *= -1; // Cambiar la dirección
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.setY(naveEnemiga.getY() + altoNaveEnemiga + espacioEntreNaves);
                }
            }
            panel.repaint();
        });
        timer.start();
    }
}