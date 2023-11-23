import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Clase diseñada para realizar la compilación del lenguaje de la calculadora.
 * Esta clase provee de un método que hace el análisis léxico y un método que
 * devuelve el árbol de análisis sintáctico.
 *
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 */
public class Compilador {

    /**
     * Método que hace el análisis léxico del lenguaje de la calculadora.
     *
     * @param cadena La expresión aritmética a evaluar.
     * @return Un conjunto de tokens encapsulados en un objeto de la clase
     * {@link StringTokenizer}
     */
    //recibe una cadena con espacios, se los elimina y separa por tokens cada que se encuentra 
    //uno de los simbolos del segundo parámetro, incluye a los mismos simbolos como tokens
    //añadí s de seno, c de cos, t de tan y r de raiz
    public StringTokenizer analisisLexico(String cadena) {
        cadena = cadena.replace(" ", "");
        StringTokenizer tokenizer = new StringTokenizer(cadena, "()\\+\\*\\-\\/\\s\\c\\t\\r", true);
        return tokenizer;
    }

    /**
     * Método que devuelve un árbol de análisis sintáctico
     *
     * @param tokenizer El objeto con el conjunto de tokens a analizar.
     * @return Un nodo de la clase {@link CompositeEA} que es la raíz del árbol
     * de análisis sintáctico.
     * @throws ErrorDeSintaxisException En caso de que la expresión esté mal formada
     */
    public CompositeEA arbolDeAnalisisSintactico(StringTokenizer tokenizer) throws ErrorDeSintaxisException {
        if (tokenizer == null) {
            return null;
        }

        //2 pilas, de operadores y de salida
        Stack<NodoOperador> operadores = new Stack<>();
        Stack<CompositeEA> salida = new Stack<>();

        boolean anteriorEsOperador = true;
        //ciclo que decide que se hará con cada token
        while (tokenizer.hasMoreTokens()) {
            CompositeEA n;
            NodoOperador no;
            String actual = tokenizer.nextToken();
            
            //entra al caso del paréntesis derecho obv
            if (actual.equals(")")) {
                casoParentesisDerecho(operadores, salida);
                anteriorEsOperador = true;
            } else {
            //intenta cambiar el token intermedio (idealmente un numero en forma de String)
            //a tipo double para ser añadido a la pila de la salida
                try {
                    n = new NodoValor(Double.parseDouble(actual));
                    salida.push(n);
                    anteriorEsOperador = false;
                } catch (NumberFormatException nfe) {
                    no = NodoOperador.factoryMethodOperadorNuevo(actual, anteriorEsOperador);
                    //si es operador de parentesis lo añade a la pila de operadores
                    if (no instanceof NodoParentesis) {
                        anteriorEsOperador = true;
                        operadores.push(no);
                    //si no es operador de parentesis y si no ha dado la excepcion del contructor se llama al método
                    //caso operador con parametos la pila de operadores, la pila de salida y el reciente nodo op
                    } else {
                        casoOperador(operadores, salida, no);
                        anteriorEsOperador = true;
                    }
                }
            }
        }
        //termina con los operadores
        while (!operadores.empty()) {
            NodoOperador top = operadores.pop();
            if (top instanceof NodoParentesis) {
                throw new ErrorDeSintaxisException("Error en el balanceo de parentesis");
            } else {
                popIntoOutput(top, salida);
            }
        }
        return salida.pop();
    }

    //si es un operador se llama a este método
    private void casoOperador(Stack<NodoOperador> operadores,
            Stack<CompositeEA> salida, NodoOperador no) throws ErrorDeSintaxisException {
        //ciclo mientras haya operadores que revisa que este en orden la precedencia, y lo mete a operadores
        while (!operadores.empty()) {
            NodoOperador top = operadores.pop();
            if ((top.getPrecedence() <= no.getPrecedence() || (top instanceof NodoParentesis))
                    && top.getPrecedence() != 3) {
                operadores.push(top);
                break;
            //realiza lo que se tiene hasta ahora para asignar la nueva precedencia
            } else {
                popIntoOutput(top, salida);
            }
        }
        operadores.push(no);
    }


    private void popIntoOutput(NodoOperador op, Stack<CompositeEA> salida) throws ErrorDeSintaxisException {
        //intenta asignar el valor que le corresponde a la operacion con la que se llamó el método
        try {
            CompositeEA der = salida.pop();
            op.setDer(der);
            //estaba un 3, ie consideraba las precedencias 2,1 y 0 pero la presedencia 2 es exclusiva de operadores 
            //unarios que no deben poseer hijo izquierdo
            if (op.getPrecedence() < 2) {
                CompositeEA izq = salida.pop();
                op.setIzq(izq);
            }
            salida.push(op);
            
        } catch (EmptyStackException ese) {
            throw new ErrorDeSintaxisException("Falta de operando");
        }
    }

    //método que determina que hacer en el análisis sintáctico si se encuentra un ")"
    private void casoParentesisDerecho(Stack<NodoOperador> operadores,
            Stack<CompositeEA> salida) throws ErrorDeSintaxisException {
        //ciclo que cierra cuando la pila de operadores recibida esté vacía
        while (!operadores.empty()) {
            //toma el último operador de la pila de operadores
            NodoOperador top = operadores.pop();
            //si no es un parentesis llama a popIntoOutput con el el ultimo nodo de la pila y con la pila de salida
            if (!(top instanceof NodoParentesis)) {
                popIntoOutput(top, salida);
            } else {
                return;
            }
        }
        throw new ErrorDeSintaxisException("Error en el balanceo de parentesis");

    }

}
