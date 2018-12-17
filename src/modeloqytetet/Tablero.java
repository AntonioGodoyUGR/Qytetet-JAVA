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
public class Tablero {
    private ArrayList<Casilla> casillas;
    private Casilla carcel;
    
    public Tablero(){
      inicializar();
    }

    private void inicializar(){
        this.casillas = new ArrayList<>();
        this.carcel = new OtraCasilla(10, TipoCasilla.CARCEL,0);
        
        this.casillas.add(new OtraCasilla(0,TipoCasilla.SALIDA, 0));
        this.casillas.add(new Calle(1, new TituloPropiedad("Calle Uno",500,50,10,250,30)));
        this.casillas.add(new Calle(2, new TituloPropiedad("Calle Dos",550,50,10,300,30)));
        this.casillas.add(new OtraCasilla(3,TipoCasilla.SORPRESA, 100));
        this.casillas.add(new Calle(4, new TituloPropiedad("Calle Tres",600,60,10,350,30)));
        this.casillas.add(new Calle(5, new TituloPropiedad("Calle Cuatro",650,60,10,400,30)));
        this.casillas.add(new OtraCasilla(6,TipoCasilla.JUEZ, 100));
        this.casillas.add(new Calle(7, new TituloPropiedad("Calle Cinco",650,70,10,450,30)));
        this.casillas.add(new Calle(8, new TituloPropiedad("Calle Seis",700,70,10,500,30)));
        this.casillas.add(new OtraCasilla(9,TipoCasilla.SORPRESA, 100)); 
        this.casillas.add(carcel);
        this.casillas.add(new OtraCasilla(11,TipoCasilla.PARKING, 100));
        this.casillas.add(new Calle(12, new TituloPropiedad("Calle Siete",750,75,10,550,30)));        
        this.casillas.add(new Calle(13, new TituloPropiedad("Calle Ocho",800,75,10,600,30)));
        this.casillas.add(new OtraCasilla(14,TipoCasilla.SORPRESA, 100));
        this.casillas.add(new Calle(15, new TituloPropiedad("Calle Nueve",850,80,10,650,30)));        
        this.casillas.add(new Calle(16, new TituloPropiedad("Calle Diez",900,90,10,700,30)));
        this.casillas.add(new OtraCasilla(17,TipoCasilla.IMPUESTO, 100));
        this.casillas.add(new Calle(18, new TituloPropiedad("Calle Once",950,95,10,725,30)));        
        this.casillas.add(new Calle(19, new TituloPropiedad("Calle Doce",1000,100,10,750,30)));
        
        
        
    }
    
    ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    Casilla getCarcel() {
        return carcel;
    }
    
    boolean esCasillaCarcel(int numeroCasilla){
        return numeroCasilla == carcel.getNumeroCasilla();
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        return casillas.get((casilla.getNumeroCasilla()+desplazamiento)%19);
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla){
        if((numeroCasilla > 0 && numeroCasilla < 20)){
            return casillas.get(numeroCasilla);
        }
        else return null;
    }
    
    @Override
    public String toString() {
        return "Tablero{" + casillas + "}" + "\n";
    }

}
