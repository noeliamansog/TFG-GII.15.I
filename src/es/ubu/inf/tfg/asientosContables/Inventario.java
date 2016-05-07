package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class Inventario extends Asiento {
	
	public Inventario(Calendar f, int [] i) {
		fecha =f;
		inputs=i;

		String enunciado1 = " Se hace inventario de mercaderías. Se estima un valor de coste "
				+ "de "+inputs[0]+ "€. \n"
    			+ "CUENTAS PGC: 300. Mercaderías; 610. Variación de existencias de mercaderías.\n";
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		double saldoMercaderias = dameCuenta(300).getSaldo(fecha);

		dameCuenta(610).añadirDebe(new Anotacion(fecha, "Variación mercaderias", saldoMercaderias, damePrioridad(610)));
		dameCuenta(300).añadirHaber(new Anotacion(fecha, "Mercaderías", saldoMercaderias, damePrioridad(300)));
	
		dameCuenta(610).añadirHaber(new Anotacion(fecha, "Variación mercaderias", inputs[0], damePrioridad(610)));
		dameCuenta(300).añadirDebe(new Anotacion(fecha, "Mercaderias", inputs[0], damePrioridad(300)));				
	}
}