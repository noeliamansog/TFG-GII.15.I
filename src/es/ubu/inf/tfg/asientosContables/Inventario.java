package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase Inventario que implementa el asiento contable de un inventario.
 *
 * @author Noelia Manso García
 */
public class Inventario extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado al realizar el inventario.
	 * @param f fecha en la que se realiza el inventario
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
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