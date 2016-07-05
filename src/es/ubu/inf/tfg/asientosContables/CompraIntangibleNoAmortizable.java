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
 * Clase CompraIntangibleNoAmortizable que implementa el asiento contable de una compra intangible no amortizable.
 *
 * @author Noelia Manso García
 */
public class CompraIntangibleNoAmortizable extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una compra intangible no amortizable.
	 * @param f fecha en la que se realiza la compra
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public CompraIntangibleNoAmortizable(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
	
		String enunciado1 = " La empresa compra a una Administración Pública el derecho de explotación de un terreno "
		+ "por valor de " + inputs[0] + "€. Se paga al contado. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 202. Concesiones administrativas; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		}
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(202).añadirDebe(new Anotacion(fecha, "Concesiones administrativas", inputs[0], damePrioridad(202)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Concesiones administrativas", inputs[0], damePrioridad(572)));
		
	}
}
