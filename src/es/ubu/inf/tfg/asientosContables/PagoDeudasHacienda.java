package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoDeudasHacienda extends Asiento {
	
	public PagoDeudasHacienda(Calendar f, int [] i) {
		fecha =f;
		inputs=i;
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se paga la deuda con Hacienda.\n"
    			+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 4751. H.P acreedor por retenciones practicas.\n";
		
		System.out.println(enunciado);
		
		double saldo = dameCuenta(4751).getSaldo(fecha);

		dameCuenta(4751).añadirDebe(new Anotacion(fecha, "H.P acreedor por retenciones practicas", saldo));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo));	
	}
}