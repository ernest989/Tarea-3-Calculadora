import java.lang.Math;
/**
 * @author Ernesto Medina
 * clase NodoTan para el arbol de análisis sintáctico
 */

public class NodoTan extends NodoOperador {

	/**
	 * @param izq
	 * @param der
	 */
	public NodoTan(CompositeEA izq, CompositeEA der){
		super(izq,der);
		precedence=2;
	}

	/**
	 * @return la tangente del real ingresado, dicho real representa radianes
	 */
	@Override
	public double evalua() {
		if( izq != null ){
			throw new ArithmeticException("Si intentaste multiplicar por la tangente recuerda usar * entre operandos");
		}
		return Math.tan(der.evalua());
	}

}//fin de class