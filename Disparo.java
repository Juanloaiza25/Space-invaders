import java.awt.Color;
import java.awt.Graphics;

public class Disparo {
    
    // Atributos
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private int velocidad;
    
    // Constructor
    public Disparo(int x, int y, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
    }
    
    // Métodos
    
    // Método para dibujar el disparo
    public void dibujar(Graphics g) {
        g.setColor(Color.YELLOW);  // Color del disparo
        g.fillRect(x, y, ancho, alto);  // Dibuja un rectángulo relleno con el color amarillo
    }
    
    // Método para mover el disparo hacia arriba
    public void mover() {
        this.y -= this.velocidad;  // El disparo se mueve hacia arriba en el eje Y
    }
    
    // Método para verificar si el disparo salió de la pantalla
    public boolean fueraDePantalla(int alturaPantalla) {
        return this.y + this.alto < 0;  // Si el disparo está fuera del límite superior de la pantalla
    }
    
    // Getters para obtener la posición del disparo (puede ser útil para detectar colisiones)
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