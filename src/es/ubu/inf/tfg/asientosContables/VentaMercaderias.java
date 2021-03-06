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
 * Clase VentaMercaderias que implementa el asiento contable de una venta de mercaderías.
 *
 * @author Noelia Manso García
 */
public class VentaMercaderias extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una venta de mercaderías.
	 * @param f fecha en la que se realiza la venta.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public VentaMercaderias(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = null;
		
		//CON IVA
		if (Main.conIVA){
			enunciado1 = " La empresa efectúa una venta de mercaderías por importe de " +inputs[0]+ "€ más un " +Main.IVA+ "% de IVA. ";
			if((int)inputs[1]>0){
				enunciado1 = enunciado1 + "Se acuerda que el cliente pague en " +(int)inputs[1]+ " días. \n";
				dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes mercaderías", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(430)));
			}
			if((int)inputs[1]==0){
				enunciado1= enunciado1 + "Se cobra al contado. \n";
				dameCuenta(572).añadirDebe(new Anotacion(fecha, "Venta de mercaderias", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(572)));
			}
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 430. Clientes; 700. Venta de mercaderías; 477. H.P. IVA Repercutido. \n";  
			}
		
			enunciados.add(new Enunciado(fecha, enunciado1));

			dameCuenta(700).añadirHaber(new Anotacion(fecha, "Ventas mercaderías", inputs[0], damePrioridad(700)));
			dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA mercaderias", (inputs[0]*Main.IVA)/100, damePrioridad(477)));
			
			if((int)inputs[1]>0){	
				//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
				Calendar fechaDeudas = (Calendar)fecha.clone();
				fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
		
				String enunciado2 = " Los clientes saldan su deuda de mercaderías con la empresa.\n";
				if (enunciadoCuentas){
				enunciado2 = enunciado2 + "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
				}
		
				enunciados.add(new Enunciado(fechaDeudas, enunciado2));

				dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Clientes por venta de mercaderias", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(572)));
				dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Venta de mercaderias", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(430)));
			}
			
		//SIN IVA	
		}else{
			enunciado1 = " La empresa efectúa una venta de mercaderías por importe de " +inputs[0]+ "€. ";
			if((int)inputs[1]>0){
				enunciado1 = enunciado1 + "Se acuerda que el cliente pague en " +(int)inputs[1]+ " días. \n";
				dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes mercaderías", inputs[0], damePrioridad(430)));
			}
			if((int)inputs[1]==0){
				enunciado1 = enunciado1 + "Se cobra al contado. \n";
				dameCuenta(572).añadirDebe(new Anotacion(fecha, "Venta de mercaderias", inputs[0], damePrioridad(572)));
			}
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 430. Clientes; 700. Venta de mercaderías. \n";  
			}
					
			enunciados.add(new Enunciado(fecha, enunciado1));

			dameCuenta(700).añadirHaber(new Anotacion(fecha, "Ventas mercaderías", inputs[0], damePrioridad(700)));
				
			if((int)inputs[1]>=0){
				//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
				Calendar fechaDeudas = (Calendar)fecha.clone();
				fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
			
				String enunciado2 = " Los clientes saldan su deuda de mercaderías con la empresa.\n";
				if (enunciadoCuentas){
					enunciado2 = enunciado2 + "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
				}
			
				enunciados.add(new Enunciado(fechaDeudas, enunciado2));

				dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Clientes por venta de mercaderias", inputs[0], damePrioridad(572)));
				dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Venta de mercaderias", inputs[0], damePrioridad(430)));
			}
		}
	}
}
