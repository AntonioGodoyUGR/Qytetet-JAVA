/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;

import controladorqytetet.ControladorQytetet;
import java.util.ArrayList;
import controladorqytetet.OpcionMenu;
import java.util.Scanner;


/**
 *
 * @author toni
 */
public class VistaTextualQytetet {
    private static ControladorQytetet controlador = ControladorQytetet.getInstance();
    private final Scanner in = new Scanner (System.in);
    
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos){
        String lectura;
        boolean valido = false;
        
        do{
            System.out.println("Introduzca un valor válido:");
            lectura = in.nextLine();
            
            for(String i: valoresCorrectos){
                if(Integer.parseInt(lectura) == Integer.parseInt(i)){
                    valido = true;
                }
            }
        }while(!valido);
        
        return lectura;
    }
    
    public int elegirOperacion(){
        OpcionMenu opcion;
        ArrayList<Integer> lista = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> listaAString = new ArrayList<String>();
        
        for(Integer i: lista){
            listaAString.add(i.toString());
            opcion = OpcionMenu.values()[i];
        
            System.out.println(opcion.ordinal() + " " + opcion.toString());
        }
        
        
        return Integer.parseInt(leerValorCorrecto(listaAString));
    }
    
    public int elegirCasilla(int opcionMenu){
        ArrayList<Integer> lista = controlador.obtenerCasillasValidas(opcionMenu);
        if(!lista.isEmpty()){
            System.out.println(lista.toString());
            String lectura;
            boolean valido = false;
            
            do{
                System.out.println("Introduzca la casilla a elegir: ");
                lectura = in.nextLine();
                
                for(int i: lista){
                    if(Integer.parseInt(lectura) == i){
                        valido = true;
                    }
                }
                
            }while(!valido);
            
            return Integer.parseInt(lectura);
        }
        else{
            return -1;
        }
        
    }
    
    private ArrayList<String> obtenerNombreJugadores(){
        String lectura;
        ArrayList<String> nombres = new ArrayList<>();
        System.out.println("Introduzca numero: \n");
        lectura = in.nextLine();  //lectura de teclado

        for (int i = 1; i <= Integer.parseInt(lectura); i++) { //solicitud del nombre de cada jugador
          System.out.println("Introduzca nombre: \n");
          nombres.add(in.nextLine());
        }
        
        return nombres;
    }
    
    public static void main(String args[]){
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        
        do{
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = controlador.necesitaElegirCasilla(operacionElegida);
            if(necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida);
                
            if(!necesitaElegirCasilla || casillaElegida >= 0)
                System.out.println(controlador.realizarOperacion(operacionElegida,casillaElegida));
        }while(1==1);
          
    }
}
