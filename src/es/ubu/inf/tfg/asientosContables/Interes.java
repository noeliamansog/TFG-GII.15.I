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
import es.ubu.inf.tfg.main.Main;

/** 
 * Clase Interes que implementa el asiento contable de intereses.
 *
 * @author Noelia Manso García
 */
public class Interes extends Asiento{

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar intereses en el supuesto contable.
	 * @param f fecha en la que se realizan los intereses.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public Interes(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		String enunciado1 = null;
		//CON RETENCIONES
		if(Main.conRetenciones){
			enunciado1 = " La empresa obtiene un ingreso de " +inputs[0]+ "€ por intereses devengados en la cuenta corriente "
				+ "durante este año, de los cuales cobra el "+inputs[1]+"% (el resto lo retienen). \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 473. Hacienda Pública, retenciones y pagos a cuenta;"
									+ "769. Otros ingresos financieros.\n";
			}
		
			enunciados.add(new Enunciado(fecha, enunciado1));

			dameCuenta(572).añadirDebe(new Anotacion(fecha, "Intereses devengados", inputs[0]*(inputs[1]/100), damePrioridad(572)));
			dameCuenta(473).añadirDebe(new Anotacion(fecha, "Hacienda Pública", inputs[0]*(1- inputs[1]/100), damePrioridad(473)));
			dameCuenta(769).añadirHaber(new Anotacion(fecha, "Otros ingresos financieros", inputs[0], damePrioridad(769)));
		
		//SIN RETENCIONES
		}else{
			enunciado1 = " La empresa obtiene un ingreso de " +inputs[0]+ "€ por intereses devengados en la cuenta corriente "
					+ "durante este año. \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 769. Otros ingresos financieros.\n";
			}
			
			enunciados.add(new Enunciado(fecha, enunciado1));

			dameCuenta(572).añadirDebe(new Anotacion(fecha, "Intereses devengados", inputs[0], damePrioridad(572)));
			dameCuenta(769).añadirHaber(new Anotacion(fecha, "Otros ingresos financieros", inputs[0], damePrioridad(769)));
		
		}
	}
}
