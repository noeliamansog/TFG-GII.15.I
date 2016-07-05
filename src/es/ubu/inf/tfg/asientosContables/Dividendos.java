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
import es.ubu.inf.tfg.main.Main;

/** 
 * Clase Dividendos que implementa el asiento contable de dividendos.
 * 
 * @author Noelia Manso García
 */
public class Dividendos extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado al realizar dividendos.
	 * @param f fecha en la que se realizan los dividendos.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public Dividendos(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = null;
		
		//CON RETENCIONES
		if(Main.conRetenciones){
			enunciado1 = " Se decide repartir dividendos por valor del " +inputs[0]+ "% del resultado del "
				+ "ejercicio anterior (sobre los cuales se practica una retención del " +inputs[1]+ "%). El resto se lleva a Reserva Legal.\n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 129. Resultados del ejercicio;"
									+ "112. Reserva legal; 4751. H.P acreedor por retenciones practicadas; 12. Resultados pendientes de aplicación.\n";
			}
		
			enunciados.add(new Enunciado(fecha, enunciado1));
		
			double saldo12 = dameCuenta(12).getSaldo(fecha);
		
			dameCuenta(12).añadirDebe(new Anotacion(fecha, "Reparto de dividendos",saldo12, damePrioridad(12)));
		
			Calendar fechaAñoAnterior = (Calendar)fecha.clone();
			fechaAñoAnterior.add(Calendar.YEAR, -1);
		
			dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+fechaAñoAnterior.get(Calendar.YEAR), ((100-inputs[0])*saldo12)/100, damePrioridad(112)));
			dameCuenta(4751).añadirHaber(new Anotacion(fecha, "Retenciones practicadas por reparto de dividendos", ((inputs[0]/100)*saldo12)*inputs[1]/100, damePrioridad(4751)));	
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Reparto dividendos", ((inputs[0]/100)*saldo12)*(100-inputs[1])/100, damePrioridad(572)));
		
		//SIN RETENCIONES
		}else{
			enunciado1 = " Se decide repartir dividendos por valor del " +inputs[0]+ "% del resultado del "
					+ "ejercicio anterior \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 129. Resultados del ejercicio;"
										+ "12. Resultados pendientes de aplicación.\n";
			}
			
			enunciados.add(new Enunciado(fecha, enunciado1));
			
			double saldo12 = dameCuenta(12).getSaldo(fecha);
			dameCuenta(12).añadirDebe(new Anotacion(fecha, "Reparto de dividendos",saldo12, damePrioridad(12)));
			
			Calendar fechaAñoAnterior = (Calendar)fecha.clone();
			fechaAñoAnterior.add(Calendar.YEAR, -1);
			
			dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+fechaAñoAnterior.get(Calendar.YEAR), ((100-inputs[0])*saldo12)/100, damePrioridad(112)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Reparto dividendos", (inputs[0]/100)*saldo12, damePrioridad(572)));
		}
	}
}