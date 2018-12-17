/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;
/**
 *
 * @author toni
 */
public abstract class Casilla {
    private int numeroCasilla;
    private int coste;
    
    public Casilla(int numCasilla, int coste){
        this.numeroCasilla = numCasilla;
        this.coste = coste;
    }

    int getNumeroCasilla() {
        return numeroCasilla;
    }

    int getCoste() {
        return coste;
    }
    
    public void setCoste(int coste){
        this.coste = coste;
    }

    protected abstract TipoCasilla getTipo();

    protected abstract TituloPropiedad getTitulo();
    
    protected abstract boolean soyEdificable();
    
    @Override
    public String toString() {
        return "Casilla{" + 
                " numeroCasilla = " + numeroCasilla +
                ", coste = " + coste;      
    }
    
}
