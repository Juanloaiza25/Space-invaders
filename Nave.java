import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Nave extends KeyAdapter {
    private int x, y, ancho, alto, velocidad;
    private boolean disparando;

    public Nave(int x, int y, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.disparando = false;
    }

    // Métodos


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

    public boolean isDisparando() {
        return disparando;
    }

    
    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);
        int[] xPoints = {this.x, this.x + this.ancho / 2 , this.x + this.ancho}; // 
        int[] yPoints = {this.y + this.alto , this.y, this.y + this.alto};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    //Metodo para mover a la izquierda, este comprueba las colisiones de la ventana, si x es menor a 0, se establece en 0
    
    public void moverIzquierda() {
        this.x -= this.velocidad;
        if (this.x < 0) {
            this.x = 0;
        }
    }
    
    //Metodo para mover a la derecha, este comprueba las colisiones de la ventana, si x es mayor a 750, se establece en 750
    
    public void moverDerecha() {
        this.x += this.velocidad;
        if (this.x > 750) {
            this.x = 750;
        }
    }
    
    @Override // Método de la clase KeyAdapter
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            moverIzquierda(); // Mover la nave a la izquierda usando la flecha izquierda o la tecla 'A'
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            moverDerecha(); // Mover la nave a la derecha usando la flecha derecha o la tecla 'D'
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            disparando = true;
        }
    }
}





 


    
