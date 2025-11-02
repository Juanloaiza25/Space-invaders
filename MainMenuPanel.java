import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class MainMenuPanel extends JPanel {
    
    private JButton jugarBtn;
    private JButton configBtn;
    private JButton salirBtn;
    private Image backgroundImage;
    
    public MainMenuPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
        
        // Intentar cargar imagen de fondo (opcional)
        try {
            backgroundImage = new ImageIcon("./Assets/space_background.png").getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }
        
        // Crear botones con estilo personalizado
        jugarBtn = crearBotonEstilizado("JUGAR", 300, 250);
        configBtn = crearBotonEstilizado("CONFIGURACIÓN", 300, 330);
        salirBtn = crearBotonEstilizado("SALIR", 300, 410);
        
        add(jugarBtn);
        add(configBtn);
        add(salirBtn);
    }
    
    private JButton crearBotonEstilizado(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 200, 50);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(50, 50, 150));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 70, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 50, 150));
            }
        });
        
        return btn;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Degradado de fondo espacial
            GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 30), 
                                                  0, getHeight(), new Color(30, 10, 50));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Estrellas
            g2d.setColor(Color.WHITE);
            for (int i = 0; i < 100; i++) {
                int x = (int)(Math.random() * getWidth());
                int y = (int)(Math.random() * getHeight());
                int size = (int)(Math.random() * 3) + 1;
                g2d.fillOval(x, y, size, size);
            }
        }
        
        // Título del juego
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        String titulo = "SPACE SHOOTER";
        FontMetrics fm = g2d.getFontMetrics();
        int tituloX = (getWidth() - fm.stringWidth(titulo)) / 2;
        
        // Sombra del título
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.drawString(titulo, tituloX + 3, 123);
        
        // Título con degradado
        GradientPaint titleGradient = new GradientPaint(
            tituloX, 100, new Color(100, 200, 255),
            tituloX, 150, new Color(200, 100, 255)
        );
        g2d.setPaint(titleGradient);
        g2d.drawString(titulo, tituloX, 120);
        
        // Subtítulo
        g2d.setFont(new Font("Arial", Font.ITALIC, 16));
        g2d.setColor(new Color(200, 200, 200));
        String subtitulo = "Defiende la galaxia";
        int subtituloX = (getWidth() - g2d.getFontMetrics().stringWidth(subtitulo)) / 2;
        g2d.drawString(subtitulo, subtituloX, 160);
        
        // Versión
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString("v1.0", 10, getHeight() - 10);
    }
    
    // Getters para los botones
    public JButton getJugarBtn() { return jugarBtn; }
    public JButton getConfigBtn() { return configBtn; }
    public JButton getSalirBtn() { return salirBtn; }
}