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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private int hipotecaBase;
    private float factorRevalorizacion;
    private int precioEdificar;
    private int numHoteles, numCasas;
    private Jugador propietario;
    
    public TituloPropiedad(String nom, int precioCom,
                           int alquilerB, float factor,
                           int precioEdif, int hipotecaBase){
        this.nombre = nom;
        this.precioCompra = precioCom;
        this.alquilerBase = alquilerB;
        this.precioEdificar = precioEdif;
        this.hipotecaBase = hipotecaBase;
        this.factorRevalorizacion = factor;
        this.hipotecada = false;
        this.numCasas = 0;
        this.numHoteles = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isHipotecada() {
        return hipotecada;
    }

    public int getHipotecaBase(){
        return hipotecaBase;
    }
    
    public int getPrecioCompra() {
        return precioCompra;
    }

    public int getAlquilerBase() {
        return alquilerBase;
    }

    public float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    public int getPrecioEdificar() {
        return precioEdificar;
    }

    public int getNumHoteles() {
        return numHoteles;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    
    int calcularCosteCancelar(){
        int coste = calcularCosteHipotecar();
        coste = (int) (1.10*coste);
        return coste;
    }
    
    int calcularCosteHipotecar(){
        return (int) (hipotecaBase + numCasas*0.5*hipotecaBase + numHoteles*hipotecaBase);
    }
    
    int calcularImporteAlquiler(){
        int costeAlquiler = alquilerBase + (int)(numCasas*0.5 + numHoteles*2);
        return costeAlquiler;
    }
    
    int calcularPrecioVenta(){
       int precioVenta = precioCompra + (int) ((numCasas + numHoteles)*precioEdificar*factorRevalorizacion ) ;
       return precioVenta;
    }
    
    void cancelarHipoteca(){
        System.out.println("Hipoteca cancelada");
        this.hipotecada = false;
    }
    
    void edificarCasa(){
        System.out.println("Has edificado una casa!");
        numCasas = numCasas + 1;
    }
    
    void edificarHotel(){
        System.out.println("Has edificado un hotel!");
        numCasas = numCasas - 4;
        numHoteles = numHoteles + 1;
    }

    
    Jugador getPropietario(){
        return propietario;
    }
    void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }
    
    
    int hipotecar(){
        setHipotecada(true);
        
        return calcularCosteHipotecar();
    }
    
    int pagarAlquiler(){
        int costeAlquiler = calcularImporteAlquiler();
        
        propietario.modificarSaldo(costeAlquiler);
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        if(propietario.getEncarcelado() == true){
            return true;
        }
        else return false;
    }
    
   
    boolean tengoPropietario(){
        if(propietario != null){
            return true;
        }
        else
            return false;
    }
    
      

    @Override
    public String toString() {
        return "TituloPropiedad{" + 
                "nombre = " + nombre + 
                ", hipotecada = " + hipotecada + 
                ", precioCompra = " + precioCompra + 
                ", alquilerBase = " + alquilerBase + 
                ", factorRevalorizacion = " + factorRevalorizacion + 
                ", precioEdificar = " + precioEdificar + 
                ", numHoteles = " + numHoteles + 
                ", numCasas = " + numCasas + "}" + "\n";
    }

    
    
}
