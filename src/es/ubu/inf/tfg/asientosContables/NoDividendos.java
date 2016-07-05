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
 * Clase NoDividendos que implementa los calculos necesarios en las cuentas contables
 *  para un supuesto contable sin dividendos.
 * 
 * @author Noelia Manso García
 */
public class NoDividendos extends Asiento {
	
	/**
	 * Gestiona las cuentas contables en el caso de que no se ejecute el asiento Dividendos
	 * @param f fecha del año en la que se realiza el supuesto contable sin Dividendos
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public NoDividendos(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
	
		double saldo12 = dameCuenta(12).getSaldo(fecha);

		dameCuenta(12).añadirDebe(new Anotacion(fecha, "Paso a reservas",saldo12, damePrioridad(12)));
		dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+(fecha.get(Calendar.YEAR)-1), saldo12, damePrioridad(112)));
	}
}