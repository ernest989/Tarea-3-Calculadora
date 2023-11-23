import java.lang.Math;
/**
 * @author Ernesto Medina
 * clase NodoCos para el arbol de análisis sintáctico
 */

public class NodoCos extends NodoOperador {

	/**
	 * @param izq
	 * @param der
	 */
	public NodoCos(CompositeEA izq, CompositeEA der){
		super(izq,der);
		precedence=2;
	}

	/**
	 * @return el Coseno del real ingresado, dicho real represemta radianes
	 */
	@Override
	public double evalua(){
		if( izq != null ){
			throw new ArithmeticException("Si intentaste multiplicar por el coseno recuerda usar * entre operandos");
		}
		return Math.cos(der.evalua());
	}

}//fin de class