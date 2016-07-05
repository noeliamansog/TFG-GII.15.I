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
 * Clase NuevoSocio que implementa el asiento contable de la incorporación de un nuevo socio.
 * 
 * @author Noelia Manso García
 */
public class NuevoSocio extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al incorporarse un nuevo socio.
	 * @param f fecha en la que se incorpora un nuevo socio.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public NuevoSocio(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		double valorNominal = dameCuenta(100).getSaldo(fecha)/inputs[1];
		
		if (inputs[0]<valorNominal){
			inputs[0]=valorNominal;
		}
		String enunciado1 = " Se incorpora un nuevo socio a la empresa, el cual aporta " +inputs[0]+ "€ en efectivo "
				 + "(los cuales se ingresan en cuenta corriente) a cambio de una acción de nueva emisión. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 110 Prima de misión o asunción. "
									+ "100. Capital social \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Aportacion nuevo socio", inputs[0], damePrioridad(572)));
		dameCuenta(100).añadirHaber(new Anotacion(fecha, "Valor nominal nuevo socio", valorNominal, damePrioridad(100)));	
		dameCuenta(110).añadirHaber(new Anotacion(fecha, "Prima nuevo socio", inputs[0]-valorNominal, damePrioridad(110)));
	}
}
