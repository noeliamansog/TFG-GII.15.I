package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoTodoPrestamo extends Asiento {
	
	public PagoTodoPrestamo(Calendar f, double [] i) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = " Se paga la totalidad del préstamo anticipadamente. \n"
    			+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros;"
    			+ "170. Deudas a largo plazo con entidades de crédito. \n";
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		//Intereses???
		double saldo = dameCuenta(170).getSaldo(fecha);
		
		dameCuenta(170).añadirDebe(new Anotacion(fecha, "Prestamo", saldo, damePrioridad(170)));
		dameCuenta(527).añadirHaber(new Anotacion(fecha, "Prestamo", saldo, damePrioridad(572)));		
	}
}