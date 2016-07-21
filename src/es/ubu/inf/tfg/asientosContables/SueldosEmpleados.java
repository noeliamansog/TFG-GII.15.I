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
 * Clase SueldosEmpleados que implementa el asiento contable del pago del sueldo de los empleados.
 * 
 * @author Noelia Manso García
 */
public class SueldosEmpleados extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar el pago del sueldo de los empleados.
	 * @param f fecha en la que se paga a los empleados.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public SueldosEmpleados(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
	
		String enunciado1 = null;
		
		//Calcular la diferencia hasta fin de año
		Calendar fechaFinAno = (Calendar)fecha.clone();
		fechaFinAno.set(fecha.get(Calendar.YEAR), 11, 31);	
		double diferencia = (( fechaFinAno.getTimeInMillis() - fecha.getTimeInMillis())/(1000*60*60*24)+1);
				
		double aPagar = (diferencia/365)*inputs[1];
		
		//CON RETENCIONES
		if(Main.conRetenciones){
			enunciado1 = " La empresa paga a cada uno de sus " +(int)inputs[0]+ " empleados " +inputs[1]+ "€ de sueldo bruto al año. "
					+ "Por cada uno de sus empleados la empresa cotiza el " +inputs[2]+ "% a la Seguridad Social (cuota patronal), "
					+ "que pagará al año que viene. Se retiene el " +inputs[3]+ "% del salario bruto por I.R.P.F. y "
					+ "el " +inputs[4]+ "% del salario bruto en concepto de Seguridad Social a cargo del trabajador (cuota obrera). \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 640. Sueldo y salario; 642.S.S. a cargo de la empresa; 476.Organismos de la S.S. acreedores; "
									+ " 4751.H.P acreedor por retenciones practicadas; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
			}
		
			enunciados.add(new Enunciado(fecha, enunciado1));
	
			dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos y salarios", (inputs[0]*aPagar), damePrioridad(640)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Sueldos y salarios", (inputs[0]*aPagar)-(((inputs[4]/100)*(inputs[0]*aPagar))+((inputs[3]/100)*((inputs[0]*aPagar)))), damePrioridad(572)));
	
			dameCuenta(642).añadirDebe(new Anotacion(fecha, "S.S. a cargo de la empresa", ((inputs[2]/100)*((inputs[0]*aPagar))), damePrioridad(642)));
			dameCuenta(476).añadirHaber(new Anotacion(fecha, "Organismos de la S.S. acreedores", ((inputs[2]/100)*((inputs[0]*aPagar))), damePrioridad(476)));
	
			dameCuenta(476).añadirHaber(new Anotacion(fecha, "Organismos de la S.S. acreedores", ((inputs[4]/100)*(inputs[0]*aPagar)), damePrioridad(476)));
			dameCuenta(4751).añadirHaber(new Anotacion(fecha, "H.P acreedor por retenciones practicadas", ((inputs[3]/100)*((inputs[0]*aPagar))), damePrioridad(4751)));

		//SIN RETENCIONES
		}else{
			enunciado1 = " La empresa paga a cada uno de sus " +(int)inputs[0]+ " empleados " +inputs[1]+ "€ de sueldo bruto al año. "
					 + "Por cada uno de sus empleados la empresa cotiza el " +inputs[2]+ "% a la Seguridad Social (cuota patronal), "
					 + "que pagará al año que viene. \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 640. Sueldo y salario; 642.S.S. a cargo de la empresa; "
										+ "572. Bancos e instituciones de crédito c/c vista, euros.\n";
			}
				
			enunciados.add(new Enunciado(fecha, enunciado1));
			
			dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos y salarios", inputs[0]*aPagar, damePrioridad(640)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Sueldos y salarios", inputs[0]*aPagar, damePrioridad(572)));
			
			dameCuenta(642).añadirDebe(new Anotacion(fecha, "S.S. a cargo de la empresa", ((inputs[2]/100)*((inputs[0]*aPagar))), damePrioridad(642)));
			dameCuenta(476).añadirHaber(new Anotacion(fecha, "Organismos de la S.S. acreedores", ((inputs[2]/100)*((inputs[0]*aPagar))), damePrioridad(476)));	
		}
	}
}
