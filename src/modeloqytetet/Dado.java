/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.Random;
/**
 *
 * @author toni
 */
public class Dado {
    private static final Dado instance = new Dado();
    private Dado(){}//Constructor privado para que de otra clase no se pueda crear otra instancia de dado.
    public static Dado getInstance(){
        return instance;
    }
    private int valor;
    
    int tirar(){
        Random aleatorio = new Random();
        
        valor = 1 + aleatorio.nextInt(6);
        
        return valor;        
    }
    
    public int getValor(){
        return valor;
    }
}
