package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

public class NoDividendos extends Asiento {
	
	public NoDividendos(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
	
		double saldo12 = dameCuenta(12).getSaldo(fecha);

		dameCuenta(12).añadirDebe(new Anotacion(fecha, "Paso a reservas",saldo12, damePrioridad(12)));
		dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+(fecha.get(Calendar.YEAR)-1), saldo12, damePrioridad(112)));
	}
}