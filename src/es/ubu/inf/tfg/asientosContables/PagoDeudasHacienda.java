package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoDeudasHacienda extends Asiento {
	
	public PagoDeudasHacienda(Calendar f, double [] i) {
		fecha =f;
		inputs=i;

		String enunciado1 = " Se paga la deuda con Hacienda.\n"
    			+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 4751. H.P acreedor por retenciones practicas.\n";
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		double saldo = dameCuenta(4751).getSaldo(fecha);

		dameCuenta(4751).añadirDebe(new Anotacion(fecha, "H.P acreedor por retenciones practicas", saldo, damePrioridad(4751)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo, damePrioridad(572)));	
	}
}