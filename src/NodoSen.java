import java.lang.Math;
/**
 * @author Ernesto Medina
 * clase NodoSen para el arbol de análisis sintáctico
 */

public class NodoSen extends NodoOperador {

	/**
	 * @param izq
	 * @param der
	 */
	public NodoSen(CompositeEA izq, CompositeEA der){
		super(izq,der);
		precedence=2;
	}

	/**
	 * @return el Seno del real ingresado, dicho real represemta radianes
	 */
	@Override
	public double evalua(){
		if( izq != null ){
			throw new ArithmeticException("Si intentaste multiplicar por el seno recuerda usar * entre operandos");
		}
		return Math.sin(der.evalua());
	}

}//fin de class