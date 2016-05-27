package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoDeudasSS extends Asiento {
	
	public PagoDeudasSS(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

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