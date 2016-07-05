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

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;
                                                               
/** 
 * Clase SueldoIngeniero que implementa el asiento contable del pago del sueldo de un ingeniero.
 *
 * @author Noelia Manso García
 */
public class SueldoIngeniero extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar el pago del sueldo del ingeniero.
	 * @param f fecha en la que se paga al ingeniero.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public SueldoIngeniero(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
	
		String enunciado1 = " Se contrata a un ingeniero informático cuyo sueldo bruto anual es " +inputs[0]+ "€. El informático dedica "
			 + "el " +inputs[1]+ "% de su tiempo a desarrollar un proyecto de investigación con altas probabilidades "
			 + "de generar ganancias en un futuro. Contabilizar primero el gasto, y luego la activación de la parte correspondiente. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 640. Sueldo y salario; 572. Bancos e instituciones de crédito c/c vista, euros. "
									+ "200.Gastos en investigación; 730 Trabajos realizados para el inmovilizado intangible.\n";
		}
	
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos Ingeniero informático", inputs[0], damePrioridad(640)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Ingeniero informático", inputs[0], damePrioridad(572)));
		dameCuenta(200).añadirDebe(new Anotacion(fecha, "Gasto en investigacion", (inputs[1]*inputs[0])/100, damePrioridad(200)));
		dameCuenta(730).añadirHaber(new Anotacion(fecha, "Trabajos realizados para el inmovilizado intangible", (inputs[1]*inputs[0])/100, damePrioridad(730)));
	
	}
}
