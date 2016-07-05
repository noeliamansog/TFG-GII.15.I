/* GSC
 * GSC es una aplicación que permite la creación de supuestos contables 
 * personalizados y los resuelve de forma automática.
 * Copyright (C) 2016 Noelia Manso & Luis R. Izquierdo
 *
 * This file is part of GSC.
 *
 * GSC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSC.  If not, see <http://www.gnu.org/licenses/>.
 */

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