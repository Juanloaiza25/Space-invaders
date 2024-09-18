import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class SpaceShooter {

    static Nave nave = new Nave(370, 520, 30, 30, 5);
    static List<NaveEnemiga> navesEnemigas = new ArrayList<>();  
    static NaveEnemiga naveEnemiga = new NaveEnemiga(0, 0, 30, 30, true);
    static int direccion = 1; // 1 para mover a la derecha, -1 para mover a la izquierda
    static boolean gameOver = false; // Variable para controlar el estado del juego
    static Timer timer;

    public static void main(String[] args) {
        int anchoPanel = 800;
        int altoPanel = 600;
        int anchoNaveEnemiga = naveEnemiga.getAncho();
        int altoNaveEnemiga = naveEnemiga.getAlto();
        int espacioEntreNaves = 15;
        int numeroDeNaves = (anchoPanel - espacioEntreNaves) / (anchoNaveEnemiga + espacioEntreNaves);
        int numeroDeFilas = 5;

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

        // Crear botón de inicio
        JButton botonInicio = new JButton("Iniciar Juego");
        botonInicio.setBounds(anchoPanel / 2 - 75, altoPanel / 2 - 25, 150, 50);
        panel.setLayout(null);
        panel.add(botonInicio);

        // Crear botón de reinicio
        JButton botonReiniciar = new JButton("Volver a empezar");
        botonReiniciar.setBounds(anchoPanel / 2 - 75, altoPanel / 2 - 25, 150, 50);
        botonReiniciar.setVisible(false);
        panel.add(botonReiniciar);

        // Inicializar naves enemigas
        inicializarNavesEnemigas(numeroDeNaves, numeroDeFilas, anchoNaveEnemiga, altoNaveEnemiga, espacioEntreNaves);

        // Agregar ActionListener al botón de inicio
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonInicio.setVisible(false); // Ocultar el botón de inicio
                gameOver = false;
                iniciarJuego(panel, anchoPanel, altoNaveEnemiga, espacioEntreNaves, botonReiniciar);
            }
        });

        // Agregar ActionListener al botón de reinicio
        botonReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonReiniciar.setVisible(false); // Ocultar el botón de reinicio
                gameOver = false;
                nave = new Nave(370, 520, 30, 30, 5);
                navesEnemigas.clear();
                inicializarNavesEnemigas(numeroDeNaves, numeroDeFilas, anchoNaveEnemiga, altoNaveEnemiga, espacioEntreNaves);
                iniciarJuego(panel, anchoPanel, altoNaveEnemiga, espacioEntreNaves, botonReiniciar);
            }
        });

        ventana.setVisible(true);

        // Agregar KeyListener para mover la nave
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                nave.keyPressed(e);
                panel.repaint();
            }
        });
    }

    private static void inicializarNavesEnemigas(int numeroDeNaves, int numeroDeFilas, int anchoNaveEnemiga, int altoNaveEnemiga, int espacioEntreNaves) {
        for (int i = 1; i < numeroDeNaves / 2; i++) {
            int x = i * (anchoNaveEnemiga + espacioEntreNaves);
            for (int j = 1; j < numeroDeFilas; j++) {
                int y = j * (altoNaveEnemiga + espacioEntreNaves);
                navesEnemigas.add(new NaveEnemiga(x, y, anchoNaveEnemiga, altoNaveEnemiga, true));
            }
        }
    }

    private static void iniciarJuego(JPanel panel, int anchoPanel, int altoNaveEnemiga, int espacioEntreNaves, JButton botonReiniciar) {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    boolean cambiarDireccion = false;
                    for (NaveEnemiga naveEnemiga : navesEnemigas) {
                        naveEnemiga.setX(naveEnemiga.getX() + direccion * 5);
                        if (naveEnemiga.getX() <= 0 || naveEnemiga.getX() + naveEnemiga.getAncho() >= anchoPanel - 30) {
                            cambiarDireccion = true;
                        }
                    }
                    if (cambiarDireccion) {
                        direccion *= -1; // Cambiar la dirección
                        for (NaveEnemiga naveEnemiga : navesEnemigas) {
                            naveEnemiga.setY(naveEnemiga.getY() + altoNaveEnemiga + espacioEntreNaves);
                            if (naveEnemiga.getY() >= nave.getY()) {
                                gameOver = true;
                                botonReiniciar.setVisible(true); // Mostrar el botón de reinicio
                                timer.stop(); // Detener el juego
                                break;
                            }
                        }
                    }
                    panel.repaint();
                }
            }
        });
        timer.start();
    }
}