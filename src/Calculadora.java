import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;


/**
 *
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 */
public class Calculadora {
    static Compilador comp;
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ErrorDeSintaxisException {
        boolean salir=false;
        while(!salir){
            try{

                JOptionPane.showMessageDialog(null, "**********CALCULADORA***********");
                String input = JOptionPane.showInputDialog("Ingresa la expresión a calcular (s para seno, r para raiz, t para tangente, c para coseno seguido del valor a calcular, para operaciones binarias básicas, solo x-y por ejemplo) Para salir del programa solo escribe 'exit'  ");    
                
                if(input.equals("exit")){
                    salir=true;
            
                }else{
                    comp= new Compilador();
                    StringTokenizer lexemas = comp.analisisLexico(input);
                    CompositeEA nodo = comp.arbolDeAnalisisSintactico(lexemas);
                    JOptionPane.showMessageDialog(null, nodo + " = "+nodo.evalua());          
                    

                }  
            }
            catch(ErrorDeSintaxisException s){
                JOptionPane.showMessageDialog(null, "Tu expresión no está bien escrita, por favor solo usa los simbolos + - * / s c t r, y si incluiste parentesis recuerda cerrarlos");
            }
            catch(ArithmeticException a){
                JOptionPane.showMessageDialog(null, "Tu operación esta indefinida");
            }
            catch(NullPointerException n){
                JOptionPane.showMessageDialog(null, "Ingresa exit para salir");
            }catch(Exception x){
                JOptionPane.showMessageDialog(null, "Hubo un error, lo intentamos de nuevo? o ingresa exit para salir");
            }


        }
        
    }
}