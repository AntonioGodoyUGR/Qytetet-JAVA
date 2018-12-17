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
public class OtraCasilla extends Casilla{
    private TipoCasilla tipo;
    
    public OtraCasilla(int numCasilla, TipoCasilla tipo, int coste){
        super(numCasilla, coste);
        this.tipo = tipo;        
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return tipo;
    }
    
    @Override
    protected boolean soyEdificable(){
        return false;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
       return null; 
    }
    
    
}
