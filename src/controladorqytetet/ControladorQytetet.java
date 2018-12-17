/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;

import java.util.ArrayList;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.Qytetet;
import modeloqytetet.EstadoJuego;

/**
 *
 * @author toni
 */
public class ControladorQytetet {
    private ArrayList<String> nombreJugadores;
    private Qytetet modelo = Qytetet.getInstance();
    
    private static final ControladorQytetet instance = new ControladorQytetet();
    private ControladorQytetet(){
        nombreJugadores = null;
    }
    public static ControladorQytetet getInstance(){
        return instance;
    }
    

    
    public void setNombreJugadores(ArrayList<String> nombreJugadores){
        this.nombreJugadores = nombreJugadores;
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList<Integer> operacionesValidas = new ArrayList<Integer>();
        EstadoJuego estado = modelo.getEstadoJuego();
        
        operacionesValidas.clear();
        
        if(modelo.getJugadores().isEmpty()){
            operacionesValidas.add(OpcionMenu.INICIARJUEGO.ordinal());
        }
        else{
            switch(estado){
                case JA_CONSORPRESA:
                    operacionesValidas.add(OpcionMenu.APLICARSORPRESA.ordinal());
                    break;
                case ALGUNJUGADORENBANCARROTA:
                    operacionesValidas.add(OpcionMenu.OBTENERRANKING.ordinal());
                    break;
                case JA_PUEDECOMPRAROGESTIONAR:
                    operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
                    operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    operacionesValidas.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                    break;
                case JA_PUEDEGESTIONAR:
                    operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
                    operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    break;
                case JA_PREPARADO:
                    operacionesValidas.add(OpcionMenu.JUGAR.ordinal());
                    break;
                case JA_ENCARCELADO:
                    operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
                    break;
                case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                    operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                    operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                    break;
            }
        }
        
        operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
        operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
        operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
        operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
        
        return operacionesValidas;
    }
    
    public boolean necesitaElegirCasilla(int opcionMenu){
        OpcionMenu opcion = OpcionMenu.values()[opcionMenu];
        
        if(opcion == OpcionMenu.HIPOTECARPROPIEDAD || opcion == OpcionMenu.CANCELARHIPOTECA ||
           opcion == OpcionMenu.EDIFICARCASA || opcion == OpcionMenu.EDIFICARHOTEL ||
           opcion == OpcionMenu.VENDERPROPIEDAD ){
            return true;
        }
        else {
            return false;
        }
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu){
        ArrayList<Integer> listaCasillas = new ArrayList<Integer>(); 
        OpcionMenu opcion = OpcionMenu.values()[opcionMenu];
        
        if(opcion == OpcionMenu.EDIFICARCASA || 
           opcion == OpcionMenu.EDIFICARHOTEL ||
           opcion == OpcionMenu.VENDERPROPIEDAD ){
            
            listaCasillas = modelo.obtenerPropiedadesJugador();
        }
        else {
            switch(opcion){
                case HIPOTECARPROPIEDAD:
                    listaCasillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                    break;
                case CANCELARHIPOTECA:
                    listaCasillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
                    break;
            }
        }
        return listaCasillas;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida){
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        String informacion = "";
        switch(opcion){
            case INICIARJUEGO:
                informacion += "¡Comenzamos a jugar!" + "\n";
                modelo.inicializarJuego(nombreJugadores);
                informacion += "Juego inicializado..." + "\n";
                break;
            case APLICARSORPRESA:
                informacion += "Carta Sorpresa: " + "\n" + modelo.getCartaActual().toString();
                modelo.aplicarSorpresa();
                break;
            case JUGAR:
                modelo.jugar();
                informacion += modelo.getJugadorActual().getCasillaActual();
                break;
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                break;
            case INTENTARSALIRCARCELTIRANDODADO:
                modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
                break;
            case COMPRARTITULOPROPIEDAD:
                modelo.comprarTituloPropiedad();
                break;
            case HIPOTECARPROPIEDAD:
                modelo.hipotecarPropiedad(casillaElegida);
                break;
            case CANCELARHIPOTECA:
                modelo.cancelarHipoteca(casillaElegida);
                break;
            case EDIFICARCASA:
                modelo.edificarCasa(casillaElegida);
                break;
            case EDIFICARHOTEL:
                modelo.edificarHotel(casillaElegida);
                break;
            case VENDERPROPIEDAD:
                modelo.venderPropiedad(casillaElegida);
                break;
            case PASARTURNO:
                modelo.siguienteJugador();
                System.out.println("Turno para " + modelo.getJugadorActual().getNombre());
                break;
            case OBTENERRANKING:
                modelo.obtenerRanking();
                break;
            case TERMINARJUEGO:
                informacion += "JUEGO TERMINADO!";
                break;
            case MOSTRARJUGADORACTUAL:
                System.out.println(modelo.getJugadorActual().toString());
                break;
            case MOSTRARJUGADORES:
                System.out.println(modelo.getJugadores().toString());
                break;
            case MOSTRARTABLERO:
                System.out.println(modelo.getTablero().toString());
                break;           
        }
        return informacion;
    }
}
