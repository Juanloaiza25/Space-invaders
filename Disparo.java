import java.awt.*;
import java.awt.event.*;


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

    
    public void dibujar() {
        System.out.println("Dibujando disparo en (" + x + ", " + y + ")");
    }
    
    public boolean colision(NaveEnemiga naveEnemiga) {
        return false;
    }
}
