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
public class Sorpresa {
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    public Sorpresa(String t, int v, TipoSorpresa type){
        texto = t;
        tipo = type;
        valor = v;
    }
    
    String getTexto(){
        return texto;
    }
    
    TipoSorpresa getTipo(){
        return tipo;
    }
    
    int getValor(){
        return valor;
    }
    
    @Override
    public String toString(){
        return "Sorpresa {" + "Texto = " + texto +", valor = " +
                Integer.toString(valor) + ", tipo = " + tipo + "}";
         
    }
}
