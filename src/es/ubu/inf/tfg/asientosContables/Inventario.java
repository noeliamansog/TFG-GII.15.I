package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

public class Inventario extends Asiento {
	
	public Inventario(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = " Se hace inventario de mercaderías. Se estima un valor de coste "
				+ "de "+inputs[0]+ "€. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 300. Mercaderías; 610. Variación de existencias de mercaderías.\n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		double saldoMercaderias = dameCuenta(300).getSaldo(fecha);
		dameCuenta(300).añadirHaber(new Anotacion(fecha, "Mercaderías", saldoMercaderias, damePrioridad(300)));	
		dameCuenta(300).añadirDebe(new Anotacion(fecha, "Mercaderias", inputs[0], damePrioridad(300)));
		
		if(inputs[0]<saldoMercaderias){
			dameCuenta(610).añadirDebe(new Anotacion(fecha, "Variación mercaderias", saldoMercaderias-inputs[0], damePrioridad(610)));
		}else{
			dameCuenta(610).añadirHaber(new Anotacion(fecha, "Variación mercaderias", inputs[0]-saldoMercaderias, damePrioridad(610)));
		}
	}
}