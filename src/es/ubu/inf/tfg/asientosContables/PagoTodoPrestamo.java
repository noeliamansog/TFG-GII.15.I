package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoTodoPrestamo extends Asiento {
	
	public PagoTodoPrestamo(Calendar f, int [] i) {
		fecha =f;
		inputs=i;
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se paga la totalidad del préstamo anticipadamente. \n"
    			+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros;"
    			+ "170. Deudas a largo plazo con entidades de crédito. \n";
		
		System.out.println(enunciado);
		
		//Intereses???
		double saldo = dameCuenta(170).getSaldo(fecha);
		
		dameCuenta(170).añadirDebe(new Anotacion(fecha, "Prestamo", saldo));
		dameCuenta(527).añadirHaber(new Anotacion(fecha, "Prestamo", saldo));		
	}
}