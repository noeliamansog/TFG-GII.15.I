package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase PagoDeudasHacienda que implementa el asiento contable de pago de las deudas a hacienda.
 *
 * @author Noelia Manso García
 */
public class PagoDeudasHacienda extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado cuando se paga las deudas a hacienda.
	 * @param f fecha en la que se pagan las deudas a hacienda.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public PagoDeudasHacienda(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;

		String enunciado1 = " Se saldan todas las deudas con Hacienda.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 473.H.P retenciones y pagos a cuenta; 572. Bancos e instituciones de crédito c/c vista, euros;"
					+" 4700. H.P Deudor por IVA; 4750. H.P Acreedor por IVA; 4751. H.P acreedor por retenciones practicas;"
					+ "4752. H.P acreedora por impuesto sobre sociedades \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));	
				
		double saldo473 = dameCuenta(473).getSaldo(fecha);
		dameCuenta(473).añadirHaber(new Anotacion(fecha, "H.P retenciones y pagos a cuenta", saldo473, damePrioridad(473)));
		
		double saldo4700 = dameCuenta(4700).getSaldo(fecha);
		dameCuenta(4700).añadirHaber(new Anotacion(fecha, "H.P Deudor por IVA", saldo4700, damePrioridad(4700)));
		
		double saldo4750 = dameCuenta(4750).getSaldo(fecha);
		dameCuenta(4750).añadirDebe(new Anotacion(fecha, "H.P Acreedor por IVA", saldo4750, damePrioridad(4750)));
		
		double saldo4751 = dameCuenta(4751).getSaldo(fecha);
		dameCuenta(4751).añadirDebe(new Anotacion(fecha, "H.P acreedor por retenciones practicas", saldo4751, damePrioridad(4751)));
		
		double saldo4752 = dameCuenta(4752).getSaldo(fecha);
		dameCuenta(4752).añadirDebe(new Anotacion(fecha, "H.P acreedora por impuesto sobre sociedades", saldo4752, damePrioridad(4752)));
	
		//Lo que debo a hacienda
		double saldo = (saldo4750 + saldo4751 + saldo4752) - (saldo473 + saldo4700);
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo, damePrioridad(572)));
		
	}
}