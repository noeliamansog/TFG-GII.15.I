package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;

/** 
 * Clase CompraIntangibleNoAmortizable que implementa el asiento contable de una compra intangible no amortizable.
 *
 * @author Noelia Manso García
 */
public class CompraIntangibleNoAmortizable extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una compra intangible no amortizable.
	 * @param f fecha en la que se realiza la compra
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public CompraIntangibleNoAmortizable(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
	
		String enunciado1 = " La empresa compra a una Administración Pública el derecho de explotación de un terreno "
		+ "por valor de " + inputs[0] + "€. Se paga al contado. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 202. Concesiones administrativas; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		}
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(202).añadirDebe(new Anotacion(fecha, "Concesiones administrativas", inputs[0], damePrioridad(202)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Concesiones administrativas", inputs[0], damePrioridad(572)));
		
	}
}
