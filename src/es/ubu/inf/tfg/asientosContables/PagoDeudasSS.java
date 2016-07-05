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
 * Clase PagoDeudasSS que implementa el asiento contable de pago de las deudas a la seguridad social.
 * 
 * @author Noelia Manso García
 */
public class PagoDeudasSS extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado cuando se paga las deudas a la seguridad social.
	 * @param f fecha en la que se pagan las deudas a la seguridad social.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public PagoDeudasSS(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;

		Calendar fech = (Calendar) f.clone();
		fech.set(Calendar.DAY_OF_YEAR, 1);
		fech.set(Calendar.MONTH, 6);
		
		String enunciado1 = " Se paga la deuda con la Seguridad Social.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 476. Organismos de la S.S acreedores. \n";
		}
		
		enunciados.add(new Enunciado(fech, enunciado1));
		
		double saldo476 = dameCuenta(476).getSaldo(fech);
		if(saldo476>0){
			dameCuenta(476).añadirDebe(new Anotacion(fech, "Organismos de la S.S acreedores", saldo476, damePrioridad(476)));
			dameCuenta(572).añadirHaber(new Anotacion(fech, "Organismos de la S.S acreedores", saldo476, damePrioridad(572)));
		}
	}
}