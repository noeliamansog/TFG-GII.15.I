package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoDeudasSS extends Asiento {
	
	public PagoDeudasSS(Calendar f, int [] i) {
		fecha =f;
		inputs=i;
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se paga la deuda con la Seguridad Social.\n"
    			+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 476. Organismos de la S.S acreedores. \n";
		
		System.out.println(enunciado);
		
		double saldo = dameCuenta(476).getSaldo(fecha);

		dameCuenta(476).añadirDebe(new Anotacion(fecha, "Organismos de la S.S acreedores", saldo));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos S.S", saldo));
	}
}