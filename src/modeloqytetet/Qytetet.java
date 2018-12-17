/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 *
 * @author toni
 */
public class Qytetet {
    private static final Qytetet instance = new Qytetet();
    private Qytetet(){
        
    }
    
    public static Qytetet getInstance(){
        return instance;
    }
    
    public static int MAX_JUGADORES = 4;
    static int NUM_SORPRESAS = 10;
    public static int NUM_CASILLAS = 20;
    static int PRECIO_LIBERTAD = 200;
    static int SALDO_SALIDA = 1000;
    
    Dado dado = Dado.getInstance();
    private ArrayList<Sorpresa> mazo = new ArrayList<>();
    private Tablero tablero;
    private Sorpresa cartaActual;
    private Jugador jugadorActual;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private EstadoJuego estado;
    
    public Tablero getTablero() {
        return tablero;
    }
    
    private void inicializarTablero(){
        tablero = new Tablero();
    }
    
    ArrayList<Sorpresa> getMazo(){
        return mazo;
    }
    
    private void inicializarCartasSorpresa(){
        mazo.add(new Sorpresa ("Convertirme 1", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("Convertirme 2", 5000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("¡Vas a la carcel!", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Vas a casa de Miguelon", 2, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Vuelve a casa de Pepe", 12, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Te deben dinero tus amigos morosos!", 100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Le debes dinero a tu casero", -100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Gracias por visitar a tu abuelita, toma un regalito", 50, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Has pisado mi jardin, págame!", -50, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Has ganado la apuesta a tus amigos!", 10, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Has perdido la apuesta con tus amuigos! ", -10, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("¡Sales de la cárcel!", 0, TipoSorpresa.SALIRCARCEL));

        Collections.shuffle(mazo);
    }
    
    private void inicializarJugadores(ArrayList<String> nombres){
        for(String nom:nombres){
            jugadores.add(new Jugador(nom));
        }
    }
    
    public void inicializarJuego(ArrayList<String> nombres){
        inicializarJugadores(nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    private void setCartaACtual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }
    
    public Sorpresa getCartaActual(){
        return cartaActual;
    }
    
    Dado getDado(){
       return dado;
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
    void actuarSiEnCasillaEdificable(){
        boolean deboPagar=jugadorActual.deboPagarAlquiler();
        if(deboPagar){
            jugadorActual.pagarAlquiler();
        }
        Calle calle = (Calle)obtenerCasillaJugadorActual();
        boolean tengoPropietario = calle.tengoPropietario();
        if(tengoPropietario){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }else{
            setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
        }
    }
    
    void actuarSiEnCasillaNoEdificable(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual = obtenerCasillaJugadorActual();
        
        if(casillaActual.getTipo()==TipoCasilla.IMPUESTO){
            jugadorActual.pagarImpuesto();
        }else{
            if(casillaActual.getTipo()==TipoCasilla.JUEZ){
                encarcelarJugador();
            }else if (casillaActual.getTipo()==TipoCasilla.SORPRESA){
                cartaActual=mazo.remove(0);
                setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
            }
        }
        
    }
    
    public void aplicarSorpresa(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL){
            jugadorActual.setCartaLibertad(cartaActual);
        }else{
            mazo.add(cartaActual);
            Collections.shuffle(mazo);
        }
        if(cartaActual.getTipo()==TipoSorpresa.PAGARCOBRAR){
            jugadorActual.modificarSaldo(cartaActual.getValor());
            if(jugadorActual.getSaldo()<0){
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        }else if(cartaActual.getTipo()==TipoSorpresa.IRACASILLA){
            int valor = cartaActual.getValor();
            boolean casillaCarcel=tablero.esCasillaCarcel(valor);
            if(casillaCarcel){
                encarcelarJugador();
            }else{
                mover(valor);
            }
        }else if(cartaActual.getTipo()==TipoSorpresa.PORCASAHOTEL){
            int cantidad = cartaActual.getValor();
            int numeroTotal = jugadorActual.cuantasCasasHotelesTengo();
            jugadorActual.modificarSaldo(cantidad*numeroTotal);
            if(jugadorActual.getSaldo()<0){
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        }else if(cartaActual.getTipo()==TipoSorpresa.PORJUGADOR){
            for(Jugador j : jugadores){
                if(j != jugadorActual){
                    j.modificarSaldo(cartaActual.getValor());
                
                    if(j.getSaldo()<0){
                        setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                    }
                    jugadorActual.modificarSaldo(-cartaActual.getValor());
                    if(jugadorActual.getSaldo()<0){
                        setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                    }
                }
            }
        }else if(cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
            Especulador especulador = jugadorActual.convertirme(cartaActual.getValor());
            jugadores.set(jugadores.indexOf(jugadorActual), especulador);
            jugadorActual = especulador;
        }    
    }
    
    public boolean cancelarHipoteca(int numeroCasilla){
        Casilla c = tablero.obtenerCasillaNumero(numeroCasilla);
        boolean cancelada = false;
        
        if(c.soyEdificable()){
            if(c.getTitulo().getPropietario() == jugadorActual){
                if(c.getTitulo().isHipotecada()){
                    cancelada = jugadorActual.cancelarHipoteca(c.getTitulo());
                }
            }
        }
        
        return cancelada;
    }
    
    public boolean comprarTituloPropiedad(){
        boolean comprado = jugadorActual.comprarTituloPropiedad();
        
        if (comprado == true){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return comprado;
    }
    
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada = false;
        
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada = jugadorActual.edificarCasa(titulo);
        
        if (edificada == true){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return edificada;
    }
    
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada = false;
        
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada = jugadorActual.edificarHotel(titulo);
        
        if (edificada == true){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return edificada;
    }
    
    private void encarcelarJugador(){
        if(!jugadorActual.deboIrACarcel()){
            Casilla casillaCarcel = tablero.getCarcel();
            
            jugadorActual.irACarcel(casillaCarcel);
            
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
            
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    
    public int getValorDado(){
        return dado.getValor();
    }
    
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla=tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        if(metodo == MetodoSalirCarcel.TIRANDODADO){
            int resultado = tirarDado();
            if(resultado >= 5){
                jugadorActual.setEncarcelado(false);
            }
        }
        else{
            if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
                jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            }
        }
        
        boolean libre = jugadorActual.getEncarcelado();
        
        if(libre){
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
        
        return libre;
    }
    
    public void jugar(){
        int desplazamiento = tirarDado();
        System.out.println("Lanza el dado y obtiene un " + desplazamiento );
        Casilla c = tablero.obtenerCasillaFinal(jugadorActual.getCasillaActual(),desplazamiento);
        mover(c.getNumeroCasilla());
    }
    
    void mover(int numCasillaDestino){
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        Casilla casillaFinal = tablero.obtenerCasillaNumero(numCasillaDestino);
        jugadorActual.setCasillaActual(casillaFinal);
        
        if(numCasillaDestino < casillaInicial.getNumeroCasilla()){
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        }
        
        if(casillaFinal.soyEdificable()){
            
            actuarSiEnCasillaEdificable();
        }else{
            
            actuarSiEnCasillaNoEdificable();
        }
    }
    
    public Casilla obtenerCasillaJugadorActual(){
        return jugadorActual.getCasillaActual();
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero(){
        return tablero.getCasillas();
    }
    
    
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<Integer> numeroCasillas = new ArrayList<Integer>();
       
        for(TituloPropiedad t: jugadorActual.getPropiedades()){
            for(Casilla c: tablero.getCasillas()){
                if (c.getTitulo() == t){
                    numeroCasillas.add(c.getNumeroCasilla());
                }
            }
        }
        
        return numeroCasillas;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<Integer> numeroCasillas = new ArrayList<Integer>();
       
        for(TituloPropiedad t: jugadorActual.obtenerPropiedades(estadoHipoteca)){
            for(Casilla c: tablero.getCasillas()){
                if (c.getTitulo() == t){
                    numeroCasillas.add(c.getNumeroCasilla());
                }
            }
        }
        
        return numeroCasillas;
    }    
    
    
    public void obtenerRanking(){
        Collections.sort(jugadores);
    }
    
    public int obtenerSaldoJugadorActual(){
        return jugadorActual.getSaldo();
    }
    
    private void salidaJugadores(){
        setEstadoJuego(EstadoJuego.JA_PREPARADO);
        for(Jugador j: jugadores){
            j.setCasillaActual(tablero.getCasillas().get(0));
        }
        
        Random aleatorio = new Random();
        
        int indice_jugador_actual = aleatorio.nextInt(jugadores.size()-1);
        
        jugadorActual = jugadores.get(indice_jugador_actual);
    }

    public void siguienteJugador(){
        int indice_siguiente_jugador = jugadores.indexOf(jugadorActual);
        jugadorActual = jugadores.get((indice_siguiente_jugador+1)%jugadores.size());
        
        setEstadoJuego(EstadoJuego.JA_PREPARADO);
        
        if(jugadorActual.getEncarcelado() == true){
            setEstadoJuego(EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD);
        }
    }
    
    public void setEstadoJuego(EstadoJuego estadoJuego){
        estado = estadoJuego;
    }
    
    public EstadoJuego getEstadoJuego(){
        return estado;
    }
    
    int tirarDado(){
        return dado.tirar();
    }
    
    public boolean venderPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        boolean vendida = jugadorActual.venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return vendida;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
