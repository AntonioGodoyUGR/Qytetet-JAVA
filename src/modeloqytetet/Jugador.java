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
public class Jugador implements Comparable<Jugador>{
    private boolean encarcelado = false;
    private String nombre;
    private int saldo = 7500;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.propiedades = new ArrayList<TituloPropiedad>();
    }
    
    private ArrayList<TituloPropiedad> propiedades;
    private Casilla casillaActual;
    private Sorpresa cartaLibertad;
    
    protected Especulador convertirme(int fianza){
        Especulador especulador = new Especulador(this, fianza);
        return especulador;
    }
    
    protected boolean deboIrACarcel(){
        return tengoCartaLibertad();
    }
    
    protected Jugador(Jugador jug){
        if(this != jug){
           this.propiedades = jug.getPropiedades();
           this.nombre = jug.getNombre();
           this.encarcelado = jug.getEncarcelado();
           this.saldo = jug.getSaldo();
           this.cartaLibertad = jug.cartaLibertad;
           this.casillaActual = jug.getCasillaActual();
        }
    }
       
    @Override
    public int compareTo(Jugador otroJugador){
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - obtenerCapital();
    }
    
    boolean cancelarHipoteca(TituloPropiedad titulo){
        boolean cancelada = false;
        if(saldo > titulo.calcularCosteCancelar()){
            System.out.println("Hipoteca cancelada con exito");
            modificarSaldo(-titulo.calcularCosteCancelar());
            titulo.cancelarHipoteca();
            cancelada = true;
        }
        
        return cancelada;
    }
    
    boolean comprarTituloPropiedad(){
        int costeCompra = casillaActual.getCoste();
        boolean comprado = false;
        
        if(costeCompra < saldo){
            System.out.println("Propiedad comprada con exito");
            comprado = true;
            TituloPropiedad titulo = ((Calle)casillaActual).asignarPropietario(this);
            propiedades.add(titulo);
            modificarSaldo(-costeCompra);
        }
        return comprado;        
    }
    
    int cuantasCasasHotelesTengo(){
        int num_casas_hoteles = 0;
        for(TituloPropiedad t:propiedades){
          num_casas_hoteles += t.getNumCasas() + t.getNumHoteles();
        }
        return num_casas_hoteles;
    }
    
    boolean deboPagarAlquiler(){
        if(!esDeMiPropiedad(casillaActual.getTitulo())){
            if(((Calle)casillaActual).tengoPropietario()){
                if(!((Calle)casillaActual).propietarioEncarcelado()){
                    if(!casillaActual.getTitulo().isHipotecada()){
                        return true;
                    }
                }
            }     
        }
        return false;
    }
    
    Sorpresa devolverCartaLibertad(){
        Sorpresa carta = cartaLibertad;
        cartaLibertad = null;
        return carta;
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean puedoEdificar = false;
        int numCasas = titulo.getNumCasas();
        int costeEdificarCasa = titulo.getPrecioEdificar();
        boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
        
        if (numCasas < 4 && tengoSaldo){
            puedoEdificar = true;
        }
        return puedoEdificar;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puedoEdificar = false;
        int numCasas = titulo.getNumCasas();
        int numHoteles = titulo.getNumHoteles();
        int costeEdificarHotel = titulo.getPrecioEdificar();
        boolean tengoSaldo = tengoSaldo(costeEdificarHotel);
        
        if (numCasas >=4 && numHoteles < 4 && tengoSaldo){
            puedoEdificar = true;            
        }
        return puedoEdificar;
    }
    
    
    boolean edificarCasa(TituloPropiedad titulo){
        boolean edificada = false;
        int costeEdificarCasa = titulo.getPrecioEdificar();
        
        if(puedoEdificarCasa(titulo)){
            titulo.edificarCasa();
            modificarSaldo(-costeEdificarCasa);
            edificada = true;
        }
       
        return edificada;
    }
    
    boolean edificarHotel(TituloPropiedad titulo){
        boolean edificada = false;
        int costeEdificarHotel = titulo.getPrecioEdificar();

        if(puedoEdificarHotel(titulo)){
            titulo.edificarHotel();
            modificarSaldo(-costeEdificarHotel);
            edificada = true;
        }
        
        return edificada;
    }
    
    private void eliminarDeMisPropiedades(TituloPropiedad titulo){
        propiedades.remove(titulo);
        titulo.setPropietario(null);
    }
    
    private boolean esDeMiPropiedad(TituloPropiedad titulo){
        boolean esDeMiPropiedad = false;
        
        for(TituloPropiedad t: propiedades){
            if(t == titulo){
                esDeMiPropiedad = true;
            }
        }
        
        return esDeMiPropiedad;
    }
    
    Sorpresa getCartaLibertad(){
        return cartaLibertad;
    }
    
    public Casilla getCasillaActual(){
        return casillaActual;
    }
    
    boolean getEncarcelado(){
       return encarcelado;
    }
    
    void setEncarcelado(boolean enc){
        encarcelado = enc;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    public int getSaldo(){
        return saldo;
    }
    
    boolean hipotecarPropiedad(TituloPropiedad titulo){
        System.out.println("Propiedad hipotecada con exito");
        int costeHipoteca = titulo.hipotecar();
        modificarSaldo(-costeHipoteca);
        return true;
    }
    
    void irACarcel(Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    int modificarSaldo(int cantidad){
        saldo = saldo + cantidad;
        return saldo;
    }
    
    int obtenerCapital(){
        int capital = saldo;
        
        for(TituloPropiedad t: propiedades){
            capital += t.getPrecioCompra() + ((t.getNumCasas()+t.getNumHoteles())*t.getPrecioEdificar());
        }
        
        return capital;
    }
    
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada){
        ArrayList<TituloPropiedad> propiedades_hipotecadas = new ArrayList<TituloPropiedad>();
        
        for(TituloPropiedad t: propiedades){
            if(t.isHipotecada() == hipotecada){
                propiedades_hipotecadas.add(t);
            }
        }
        
        return propiedades_hipotecadas;
    }
    
    void pagarAlquiler(){
        int costeAlquiler = ((Calle)casillaActual).pagarAlquiler();
        
        modificarSaldo(-costeAlquiler);
    }
    
    protected void pagarImpuesto(){
        saldo = saldo - casillaActual.getCoste();             
    }
    
    void pagarLibertad(int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        
        if(tengoSaldo){
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
    }
    
    
    void setCartaLibertad(Sorpresa carta){
        cartaLibertad = carta;
    }
    
    void setCasillaActual(Casilla casilla){
        casillaActual = casilla;
    }

    
    boolean tengoCartaLibertad(){
        if(cartaLibertad != null){
            return true;
        }
        else 
            return false;
    }
    
    protected boolean tengoSaldo(int cantidad){
        if (saldo > cantidad){
            return true;
        }
        else return false;
    }
    
    boolean venderPropiedad(Casilla casilla){
        TituloPropiedad titulo = casilla.getTitulo();
        
        eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        modificarSaldo(precioVenta);
        ((Calle)casillaActual).setTitulo(null);
        System.out.println("Propiedad vendida con exito");
        return true;       
    }

    @Override
    public String toString() {
        return "Jugador{ \n" + "   Encarcelado=" + encarcelado +"\n"
                           + "   Nombre=" + nombre +"\n"
                           + "   Saldo=" + saldo +"\n"
                           + "   Capital=" + obtenerCapital() +"\n"
                           + "   Propiedades=" + propiedades +"\n"
                           + "   CasillaActual=" + casillaActual +"\n"
                           + "   CartaLibertad=" + cartaLibertad + "} \n";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
