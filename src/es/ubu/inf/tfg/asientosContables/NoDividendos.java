package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase NoDividendos que implementa los calculos necesarios en las cuentas contables
 *  para un supuesto contable sin dividendos.
 * 
 * @author Noelia Manso García
 */
public class NoDividendos extends Asiento {
	
	/**
	 * Gestiona las cuentas contables en el caso de que no se ejecute el asiento Dividendos
	 * @param f fecha del año en la que se realiza el supuesto contable sin Dividendos
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public NoDividendos(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
	
		double saldo12 = dameCuenta(12).getSaldo(fecha);

		dameCuenta(12).añadirDebe(new Anotacion(fecha, "Paso a reservas",saldo12, damePrioridad(12)));
		dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+(fecha.get(Calendar.YEAR)-1), saldo12, damePrioridad(112)));
	}
}