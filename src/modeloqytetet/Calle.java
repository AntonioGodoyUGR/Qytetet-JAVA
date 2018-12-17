/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author toni
 */
public class Calle extends Casilla {
    private TituloPropiedad titulo; 
    
    public Calle(int numCasilla, TituloPropiedad titulo){
        super(numCasilla, titulo.getPrecioCompra());
        setTitulo(titulo);
    }

    public TituloPropiedad asignarPropietario(Jugador jugador){
        titulo.setPropietario(jugador);
        return titulo;
    }
    
    public int pagarAlquiler(){
        int costeAlquiler = titulo.pagarAlquiler();
        return costeAlquiler;
    }
    
    @Override
    protected TipoCasilla getTipo() {
        return TipoCasilla.CALLE;
    }

    @Override
    protected TituloPropiedad getTitulo() {
        return titulo;
    }
    
    protected void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }
    
    @Override
    protected boolean soyEdificable(){
        return true;
    }
    
    public boolean tengoPropietario(){
        return titulo.tengoPropietario();
    }
    
    public boolean propietarioEncarcelado(){
        return titulo.propietarioEncarcelado();
    }
    
    @Override
    public String toString(){
        return "Calle{" + 
                "numeroCasilla = " + super.getNumeroCasilla() +
                ", coste = " + super.getCoste() +
                ", titulo = " + titulo + "}" + "\n";
    }
}
