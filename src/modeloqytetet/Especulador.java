/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author Toni
 */
public class Especulador extends Jugador {
    private int fianza;
    
    Especulador(Jugador jugador, int fianza){
        super(jugador);
        this.fianza = fianza;
    }
    
    @Override
    protected void pagarImpuesto(){
        modificarSaldo(-getCasillaActual().getCoste());
    }
    
    @Override
    protected Especulador convertirme(int fianza){
        this.fianza = fianza;
        return this;
    }
    
    @Override
    protected boolean deboIrACarcel(){
        if(super.tengoCartaLibertad() && !pagarFianza()){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean pagarFianza(){
        boolean puedePagar = false;
        
        puedePagar = tengoSaldo(fianza);
        modificarSaldo(-fianza);
        
        return puedePagar;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean puedoEdificar = false;
        int numCasas = titulo.getNumCasas();
        int costeEdificarCasa = titulo.getPrecioEdificar();
        boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
        
        if (numCasas < 8 && tengoSaldo){
            puedoEdificar = false;
        }
        return puedoEdificar;
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puedoEdificar = false;
        int numCasas = titulo.getNumCasas();
        int numHoteles = titulo.getNumHoteles();
        int costeEdificarHotel = titulo.getPrecioEdificar();
        boolean tengoSaldo = tengoSaldo(costeEdificarHotel);
        
        if (numHoteles < 8 && tengoSaldo){
            puedoEdificar = true;            
        }
        return puedoEdificar;
    }
    
    @Override
    public String toString() {
        return super.toString() + "   Fianza= " + fianza + "\n";
    }
    
}
