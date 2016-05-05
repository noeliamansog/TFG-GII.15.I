package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class Inventario extends Asiento {
	
	public Inventario(Calendar f, int [] i) {
		fecha =f;
		inputs=i;
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se hace inventario de mercaderías. Se estima un valor de coste "
				+ "de "+inputs[0]+ "€. \n"
    			+ "CUENTAS PGC: 300. Mercaderías; 610. Variación de existencias de mercaderías.\n";
		
		System.out.println(enunciado);

		double saldoMercaderias = dameCuenta(300).getSaldo(fecha);

		dameCuenta(610).añadirDebe(new Anotacion(fecha, "Variación mercaderias", saldoMercaderias));
		dameCuenta(300).añadirHaber(new Anotacion(fecha, "Mercaderías", saldoMercaderias));
	
		dameCuenta(610).añadirHaber(new Anotacion(fecha, "Variación mercaderias", inputs[0]));
		dameCuenta(300).añadirDebe(new Anotacion(fecha, "Mercaderias", inputs[0]));				
	}
}