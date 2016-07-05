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
 * Clase CompraMercaderias que implementa el asiento contable de una compra de mercaderias.
 * 
 * @author Noelia Manso García
 */
public class CompraMercaderias extends Asiento{

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una compra de mercaderías.
	 * @param f fecha en la que se realiza la compra
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public CompraMercaderias(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = null;
		
		//CON IVA
		if(Main.conIVA){
			if((int)inputs[1]>0){
				enunciado1= " La empresa compra mercaderías por un importe de " +inputs[0]+ "€ más "
						+ "un " +Main.IVA+ "% de IVA. Se acuerda que el pago se realice en " +(int)inputs[1]+ " días. \n";
			}
			if((int)inputs[1]==0){
				enunciado1= " La empresa compra mercaderías por un importe de " +inputs[0]+ "€ más "
					 + "un " +Main.IVA+ "% de IVA. Se paga al contado. \n";
			}
			if (enunciadoCuentas){
				enunciado1 = enunciado1 +"CUENTAS PGC: 400. Proveedores; 600. Compra de mercaderías; 472. H.P. IVA Soportado.\n";
			}

			enunciados.add(new Enunciado(fecha, enunciado1));
		
			dameCuenta(600).añadirDebe(new Anotacion(fecha, "Compra mercaderias", inputs[0], damePrioridad(600)));
			dameCuenta(472).añadirDebe(new Anotacion(fecha, "H.P IVA Soportado", (Main.IVA*inputs[0])/100, damePrioridad(472)));
			dameCuenta(400).añadirHaber(new Anotacion(fecha, "Proveedores mercaderias", inputs[0]*(1+Main.IVA/100),damePrioridad(400)));
				

			if((int)inputs[1]>0){	
				//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES:
				Calendar fechaDeudas = (Calendar)fecha.clone();
				fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
		
				String enunciado2 = " Se salda la deuda con los vendedores de las mercaderías.\n";
				if (enunciadoCuentas){
					enunciado2 = enunciado2 + "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
				}
		
				enunciados.add(new Enunciado(fechaDeudas, enunciado2));
			
				dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores mercaderias", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(400)));
				dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Proveedores mercaderias", inputs[0]+(inputs[0]*(Main.IVA/100)), damePrioridad(572)));
			}
			
		//SIN IVA
		}else{
			if((int)inputs[1]>0){
				enunciado1= " La empresa compra mercaderías por un importe de " +inputs[0]+ "€. "
					 + "Se acuerda que el pago se realice en " +(int)inputs[1]+ " días. \n";
			}
			if((int)inputs[1]==0){
				enunciado1= " La empresa compra mercaderías por un importe de " +inputs[0]+ "€.  "
						 + "Se paga al contado. \n";
			}
			if (enunciadoCuentas){
				enunciado1 = enunciado1 +"CUENTAS PGC: 400. Proveedores; 600. Compra de mercaderías.\n";
			}
			
			enunciados.add(new Enunciado(fecha, enunciado1));
			
			dameCuenta(600).añadirDebe(new Anotacion(fecha, "Compra mercaderias", inputs[0], damePrioridad(600)));
			dameCuenta(400).añadirHaber(new Anotacion(fecha, "Proveedores mercaderias", inputs[0], damePrioridad(400)));
					
			if((int)inputs[1]>0){		
				//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES:
				Calendar fechaDeudas = (Calendar)fecha.clone();
				fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
			
				String enunciado2 = " Se salda la deuda con los vendedores de las mercaderías.\n";
				if (enunciadoCuentas){
					enunciado2 = enunciado2 + "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
				}
			
				enunciados.add(new Enunciado(fechaDeudas, enunciado2));

				dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores mercaderias", inputs[0], damePrioridad(400)));
				dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Proveedores mercaderias", inputs[0], damePrioridad(572)));
			}
		}
	}
}
