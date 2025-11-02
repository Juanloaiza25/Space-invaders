import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SpaceShooter {

    static Nave nave; 
    static List<NaveEnemiga> navesEnemigas = new ArrayList<>();
    static List<NaveEnemiga> navesAEliminar = new ArrayList<>();
    static List<Disparo> disparosAEliminar = new ArrayList<>();
    static List<Disparo> disparos = new ArrayList<>();
    static NaveEnemiga naveEnemiga = new NaveEnemiga(0, 0, 30, 30, true);
    static int direccion = 1;
    static boolean gameOver = false;
    static int volumen = 50;
    static int dificultad = 1;
    static boolean paused = false;
    static Timer timer;
    static JFrame ventana;
    static CardLayout cardLayout;
    static JPanel mainPanel;
    static JPanel juegoPanel;
    static MainMenuPanel menuPanel;
    static JPanel configPanel;
    static JPanel pausaPanel;
    static JPanel gameOverPanel;

    public static void main(String[] args) {

        nave = new Nave(370, 520, 30, 30, 5, disparos);
        
        int anchoPanel = 800;
        int altoPanel = 600;
        int anchoNaveEnemiga = naveEnemiga.getAncho();
        int altoNaveEnemiga = naveEnemiga.getAlto();
        int espacioEntreNaves = 30;
        int numeroDeNaves = Math.min(10, (anchoPanel - espacioEntreNaves) / (anchoNaveEnemiga + espacioEntreNaves));
        int numeroDeFilas = 3;

        Sound MusicaFondo = new Sound("./Assets/spaceinvaders1.wav");
        Sound SoundGameOver = new Sound("./Assets/gameover.wav");
        Sound SoundGameWon = new Sound("./Assets/winsound.wav");

        // --- Menú Principal ---
        menuPanel = new MainMenuPanel();

        // --- Configuración ---
        configPanel = new JPanel();
        configPanel.setLayout(new GridBagLayout());
        JLabel volumenLabel = new JLabel("Volumen: " + volumen);
        JSlider volumenSlider = new JSlider(0, 100, volumen);
        JLabel dificultadLabel = new JLabel("Dificultad: " + dificultad);
        JSlider dificultadSlider = new JSlider(1, 3, dificultad);
        dificultadSlider.setMajorTickSpacing(1);
        dificultadSlider.setPaintTicks(true);
        dificultadSlider.setPaintLabels(true);
        JButton volverBtn = new JButton("Volver");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(volumenLabel, gbc);
        gbc.gridy++;
        configPanel.add(volumenSlider, gbc);
        gbc.gridy++;
        configPanel.add(dificultadLabel, gbc);
        gbc.gridy++;
        configPanel.add(dificultadSlider, gbc);
        gbc.gridy++;
        configPanel.add(volverBtn, gbc);

        // --- Pausa ---
        pausaPanel = new JPanel();
        pausaPanel.setLayout(new GridBagLayout());
        JButton continuarBtn = new JButton("Continuar");
        JButton pausaConfigBtn = new JButton("Configuración");
        JButton pausaSalirBtn = new JButton("Salir");
        gbc.gridy = 0;
        pausaPanel.add(continuarBtn, gbc);
        gbc.gridy++;
        pausaPanel.add(pausaConfigBtn, gbc);
        gbc.gridy++;
        pausaPanel.add(pausaSalirBtn, gbc);

        // --- Game Over ---
        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridBagLayout());
        JLabel gameOverLabel = new JLabel("Game Over");
        JButton reiniciarBtn = new JButton("Reiniciar");
        JButton gameOverSalirBtn = new JButton("Salir");
        gbc.gridy = 0;
        gameOverPanel.add(gameOverLabel, gbc);
        gbc.gridy++;
        gameOverPanel.add(reiniciarBtn, gbc);
        gbc.gridy++;
        gameOverPanel.add(gameOverSalirBtn, gbc);

        // --- Juego ---
        juegoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, anchoPanel, altoPanel);
                nave.dibujar(g);
                List<Disparo> disparosAEliminar = new ArrayList<>();
                for (Disparo disparo : disparos) {
                    disparo.mover();
                    disparo.dibujar(g);
                    if (disparo.fueraDePantalla(altoPanel)) {
                        disparosAEliminar.add(disparo);
                    }
                }
                disparos.removeAll(disparosAEliminar);
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.dibujar(g);
                }
            }
        };
        juegoPanel.setFocusable(true);

        juegoPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!paused) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        paused = true;
                        cardLayout.show(mainPanel, "Pausa");
                    } else {
                        nave.keyPressed(e);
                        juegoPanel.repaint();
                    }
                }
            }
        });

        // --- CardLayout para cambiar entre pantallas ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(juegoPanel, "Juego");
        mainPanel.add(configPanel, "Config");
        mainPanel.add(pausaPanel, "Pausa");
        mainPanel.add(gameOverPanel, "GameOver");

        ventana = new JFrame("Space Shooter");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(anchoPanel, altoPanel);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setIconImage(new ImageIcon("./Assets/spaceinvaders_128_icon.png").getImage());
        ventana.setContentPane(mainPanel);
        ventana.setVisible(true);

        // --- Timer del juego ---
        timer = new Timer(50, e -> {
            if (paused) return;
            boolean cambiarDireccion = false;
            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                naveEnemiga.setX(naveEnemiga.getX() + direccion * (2 * dificultad));
                if (naveEnemiga.getX() <= 0 || naveEnemiga.getX() + anchoNaveEnemiga >= anchoPanel - 30) {
                    cambiarDireccion = true;
                }
            }
            if (cambiarDireccion) {
                direccion *= -1;
                for (NaveEnemiga naveEnemiga : navesEnemigas) {
                    naveEnemiga.setY(naveEnemiga.getY() + altoNaveEnemiga + espacioEntreNaves);
                }
            }
            for (NaveEnemiga naveEnemiga : navesEnemigas) {
                for (Disparo disparo : disparos) {
                    if (naveEnemiga.colision(disparo)) {
                        navesAEliminar.add(naveEnemiga);
                        disparosAEliminar.add(disparo);
                    }
                }
            }
            navesEnemigas.removeAll(navesAEliminar);
            disparos.removeAll(disparosAEliminar);

            if (navesEnemigas.isEmpty()) {
                gameOver = true;
                MusicaFondo.stop();
                SoundGameWon.play();
                JOptionPane.showMessageDialog(ventana, "¡Has ganado!", "¡Has ganado!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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
                cardLayout.show(mainPanel, "GameOver");
                paused = true;
                gameOver = false;
            }
            juegoPanel.repaint();
        });

        // --- Listeners de botones del MENÚ ---
        menuPanel.getJugarBtn().addActionListener(e -> {
            reiniciarJuego(numeroDeNaves, numeroDeFilas, anchoNaveEnemiga, altoNaveEnemiga, espacioEntreNaves);
            MusicaFondo.play();
            cardLayout.show(mainPanel, "Juego");
            paused = false;
            juegoPanel.requestFocusInWindow();
            timer.start();
        });
        menuPanel.getConfigBtn().addActionListener(e -> cardLayout.show(mainPanel, "Config"));
        menuPanel.getSalirBtn().addActionListener(e -> System.exit(0));

        // --- Listeners de CONFIGURACIÓN ---
        volverBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        volumenSlider.addChangeListener(e -> {
            volumen = volumenSlider.getValue();
            volumenLabel.setText("Volumen: " + volumen);
            double volumenNormalizado = volumen / 100.0;
            Sound.setVolumenGlobal(volumenNormalizado); // Cambiar volumen GLOBAL
            MusicaFondo.setVolume(volumenNormalizado); // Actualizar música actual
        });
        dificultadSlider.addChangeListener(e -> {
            dificultad = dificultadSlider.getValue();
            dificultadLabel.setText("Dificultad: " + dificultad);
        });

        // --- Listeners de PAUSA ---
        continuarBtn.addActionListener(e -> {
            paused = false;
            cardLayout.show(mainPanel, "Juego");
            juegoPanel.requestFocusInWindow();
        });
        pausaConfigBtn.addActionListener(e -> cardLayout.show(mainPanel, "Config"));
        pausaSalirBtn.addActionListener(e -> System.exit(0));

        // --- Listeners de GAME OVER ---
        reiniciarBtn.addActionListener(e -> {
            reiniciarJuego(numeroDeNaves, numeroDeFilas, anchoNaveEnemiga, altoNaveEnemiga, espacioEntreNaves);
            MusicaFondo.play();
            cardLayout.show(mainPanel, "Juego");
            paused = false;
            juegoPanel.requestFocusInWindow();
            timer.start();
        });
        gameOverSalirBtn.addActionListener(e -> System.exit(0));

        // --- KeyListener para juego y pausa ---
        ventana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (cardLayout != null && mainPanel.isAncestorOf(juegoPanel) && !paused) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        paused = true;
                        cardLayout.show(mainPanel, "Pausa");
                    } else {
                        nave.keyPressed(e);
                        juegoPanel.repaint();
                    }
                }
            }
        });

        // Mostrar menú principal al inicio
        cardLayout.show(mainPanel, "Menu");
    }

    // Método para reiniciar el juego
    static void reiniciarJuego(int numeroDeNaves, int numeroDeFilas, int anchoNaveEnemiga, int altoNaveEnemiga, int espacioEntreNaves) {
        nave = new Nave(370, 520, 30, 30, 5, disparos);
        navesEnemigas.clear();
        disparos.clear();
        navesAEliminar.clear();
        disparosAEliminar.clear();
        direccion = 1;
        for (int i = 0; i < numeroDeNaves; i++) {
            int x = i * (anchoNaveEnemiga + espacioEntreNaves);
            for (int j = 0; j < numeroDeFilas; j++) {
                int y = j * (altoNaveEnemiga + espacioEntreNaves);
                navesEnemigas.add(new NaveEnemiga(x, y, anchoNaveEnemiga, altoNaveEnemiga, true));
            }
        }
    }
}