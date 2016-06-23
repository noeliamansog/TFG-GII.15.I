package es.ubu.inf.tfg.asientosContables.sinIVA;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase PagoDeudasHaciendaSinIVA que implementa el asiento contable de pago de las deudas a hacienda sin IVA.
 * 
 * @author Noelia Manso García
 */
public class PagoDeudasHaciendaSinIVA extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado al realizar el pago de las duedas de hacienda sin IVA.
	 * @param f fecha en la que se realiza el pago.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public PagoDeudasHaciendaSinIVA(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;

		String enunciado1 = " Se saldan todas las deudas con Hacienda.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 473.H.P retenciones y pagos a cuenta; 572. Bancos e instituciones de crédito c/c vista, euros;"
					+"4751. H.P acreedor por retenciones practicas; 4752. H.P acreedora por impuesto sobre sociedades \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));	
				
		double saldo473 = dameCuenta(473).getSaldo(fecha);
		dameCuenta(473).añadirHaber(new Anotacion(fecha, "H.P retenciones y pagos a cuenta", saldo473, damePrioridad(473)));
		
		double saldo4751 = dameCuenta(4751).getSaldo(fecha);
		dameCuenta(4751).añadirDebe(new Anotacion(fecha, "H.P acreedor por retenciones practicas", saldo4751, damePrioridad(4751)));
		
		double saldo4752 = dameCuenta(4752).getSaldo(fecha);
		dameCuenta(4752).añadirDebe(new Anotacion(fecha, "H.P acreedora por impuesto sobre sociedades", saldo4752, damePrioridad(4752)));
	
		//Lo que debo a hacienda
		double saldo = (saldo4751 + saldo4752) - saldo473;
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo, damePrioridad(572)));
		
	}
}