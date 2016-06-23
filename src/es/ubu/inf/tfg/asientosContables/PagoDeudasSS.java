package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase PagoDeudasSS que implementa el asiento contable de pago de las deudas a la seguridad social.
 * 
 * @author Noelia Manso García
 */
public class PagoDeudasSS extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado cuando se paga las deudas a la seguridad social.
	 * @param f fecha en la que se pagan las deudas a la seguridad social.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public PagoDeudasSS(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;

		String enunciado1 = " Se paga la deuda con la Seguridad Social.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 476. Organismos de la S.S acreedores. \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		double saldo = dameCuenta(476).getSaldo(fecha);
		dameCuenta(476).añadirDebe(new Anotacion(fecha, "Organismos de la S.S acreedores", saldo, damePrioridad(476)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos S.S", saldo, damePrioridad(572)));
	}
}