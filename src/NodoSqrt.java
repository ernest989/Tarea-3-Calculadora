import java.lang.Math;
/**
 * @author Ernesto Medina
 * clase NodoSqrt para el arbol de análisis sintáctico
 */

public class NodoSqrt extends NodoOperador {

	/**
	 * @param izq
	 * @param der
	 */
	public NodoSqrt(CompositeEA izq, CompositeEA der){
		super(izq,der);
		precedence=2;
	}

	/**
	 * @return la raiz positiva del real ingresado
	 */
	@Override
	public double evalua() {
		if(der.evalua()<0){
			throw new ArithmeticException("No se calcular la raiz de números negativos ");
		}
		if(izq != null){
			throw new ArithmeticException("Si intentaste multiplicar por la raiz recuerda usar * entre operandos");
		}
		return Math.sqrt(der.evalua());
	}

}//fin de class