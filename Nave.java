import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Nave extends KeyAdapter {
    private int x, y, ancho, alto, velocidad;
    private boolean disparando;
    private List<Disparo> disparos;  // Referencia a la lista de disparos para añadir nuevos disparos

    Sound DisparoSound = new Sound("./Assets/shoot.wav");

    // Constructor
    public Nave(int x, int y, int ancho, int alto, int velocidad, List<Disparo> disparos) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.disparos = disparos;  // Se pasa la referencia a la lista de disparos
        this.disparando = false;
    }

    // Métodos

    // Método para dibujar la nave
    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);  // Color de la nave
        int[] xPoints = {this.x, this.x + this.ancho / 2, this.x + this.ancho};  // Coordenadas del triángulo (nave)
        int[] yPoints = {this.y + this.alto, this.y, this.y + this.alto};  // Coordenadas del triángulo (nave)
        g.fillPolygon(xPoints, yPoints, 3);  // Dibuja un triángulo para representar la nave
    }

    // Método para mover la nave a la izquierda
    public void moverIzquierda() {
        this.x -= this.velocidad;
        if (this.x < 0) {
            this.x = 0;  // Evita que la nave salga del límite izquierdo de la pantalla
        }
    }

    // Método para mover la nave a la derecha
    public void moverDerecha(int anchoPantalla) {
        this.x += this.velocidad;
        if (this.x + this.ancho > anchoPantalla) {
            this.x = anchoPantalla - this.ancho;  // Evita que la nave salga del límite derecho
        }
    }

    // Método para disparar
    public void disparar() {
        if (!disparando) {
            // Crear un nuevo disparo y agregarlo a la lista
            disparos.add(new Disparo(this.x + this.ancho / 2 - 5, this.y - 10, 10, 20, 10));
            disparando = true;

             Sound disparoSound = new Sound("./Assets/disparo.wav");
            disparoSound.setVolume(SpaceShooter.volumen / 100.0); // Usa el volumen global
            disparoSound.play();

            DisparoSound.restart();
        }
        else {
            // Permitir disparar de nuevo si ya no hay disparos en la pantalla
            boolean hayDisparos = false;
            for (Disparo disparo : disparos) {
                if (disparo.getY() >= 0) {
                    hayDisparos = true;
                    break;
                }
            }
            if (!hayDisparos) {
                disparando = false;
            }
        }
    }

    // Método para procesar la entrada del teclado
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            moverIzquierda();  // Mover la nave a la izquierda
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            moverDerecha(800);  // Mover la nave a la derecha, con 800 como ancho del panel
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            disparar();  // Disparar un proyectil
        }
    }


    // Getters (opcional, según lo que necesites)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}