import java.awt.Graphics;
import java.awt.Color;


/*
 Diseñaremos las naves enemigas que aparecerán en la parte superior de la pantalla. Estas naves se destruirán al ser alcanzadas por un disparo del jugador.
 */
public class NaveEnemiga {

    // Atributos
    int x;
    private int y;
    private int ancho;
    private int alto;
    private boolean visible;

    // Constructor

    public NaveEnemiga(int x, int y, int ancho, int alto, boolean visible) {
        this.setX(x);
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.visible = visible;
    }

    // Métodos

    public int getX() {
        return x;
        
    }

    public void setX(int x) {
        this.x = x;
        
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        
    }
    
    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
    
    public void dibujar(Graphics g) {
        if (visible) {
            g.setColor(Color.RED);
            int[] xPoints = {this.getX() + this.ancho, this.getX() + this.ancho / 2, this.getX()};
            int[] yPoints = {this.y, this.y + this.alto, this.y};
            g.fillPolygon(xPoints, yPoints, 3);
        }
        else {
            g.setColor(Color.BLACK);
            g.fillRect(this.getX(), this.y, this.ancho, this.alto);
        }

    }

    public boolean colision(Disparo disparo) {
        return false;
    }

    
}
