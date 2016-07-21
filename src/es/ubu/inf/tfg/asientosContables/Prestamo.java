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
 * Clase Prestamo que implementa el asiento contable de un préstamo.
 *
 * @author Noelia Manso García
 */
public class Prestamo extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar un prestamo.
	 * @param f fecha en la que se realiza el prestamo.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public Prestamo(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		String tipoPrestamo=null;
		String mensualAnual=null;
		String mesAno=null;
		double cuotaAmortizacion = 0;
		double cuotaPago = 0;
		double tipoInteres = 0;
		double deudaViva = inputs[0];
		double numPagos = 0;
	

		if(inputs[1]==0){
			tipoPrestamo="amortización";
		}else{
			tipoPrestamo="pago";
		}

		if(inputs[2]==0){
			mensualAnual="mensual";
			mesAno = "mes";
		}else{
			mensualAnual="anual";
			mesAno ="año";
		}
		
		String enunciado1 = " La empresa obtiene un préstamo por importe de " +inputs[0]+ "€, que se ingresa en la cuenta "
    			+ "corriente de la que es titular la empresa. El préstamo se devolverá en cuotas de " +tipoPrestamo+" "
    			+mensualAnual+ " constantes, en " +(int)inputs[3]+ " años, a un tipo de interés fijo del " +inputs[4]+ "% nominal. "
    			+ "El primer pago se realizará al cabo de un "+mesAno+" desde la concesión del préstamo. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 572. Bancos e instituciones de crédito c/c vista, euros; "
									+ "662. Intereses de deudas. \n";
		}
		

		enunciados.add(new Enunciado(fecha, enunciado1));
		
		//CUANDO ME DAN EL PRESTAMO:
		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Prestamo", (inputs[0]), damePrioridad(572)));		
		dameCuenta(170).añadirHaber(new Anotacion(fecha, "Prestamo", (inputs[0]), damePrioridad(170)));
		
		
		Calendar fech = (Calendar)fecha.clone();
		fech.set(Calendar.DAY_OF_YEAR, -0);

		
		//PAGO DE UNA CUOTA:
		//Cuotas de amortización
		 if(inputs[1]==0){
			 //Mensual
			 if(inputs[2]==0){
				 tipoInteres = (inputs[4]/100)/12;
				 numPagos = inputs[3]*12;
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 			 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaMesSiguiente = (Calendar)fech.clone();
					 fechaMesSiguiente.add(Calendar.MONTH, +j);
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion+deudaViva*tipoInteres, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 
					 String enunciado2 = " Se paga la "+j+"ª cuota del préstamo.\n";
						if (enunciadoCuentas){
							enunciado2 = enunciado2 + "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 662.Intereses de deudas; "
									+ "572. Bancos e instituciones de crédito c/c vista, euros. \n";
						}			
					enunciados.add(new Enunciado(fechaMesSiguiente, enunciado2));		 
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4]/100;
				 numPagos = inputs[3];
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fech.clone();
					 fechaAñoSiguiente.add(Calendar.YEAR, +j);
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion+deudaViva*tipoInteres, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 
					 String enunciado2 = " Se paga la "+j+"ª cuota del préstamo.\n";
						if (enunciadoCuentas){
							enunciado2 = enunciado2 + "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 662.Intereses de deudas; "
									+ "572. Bancos e instituciones de crédito c/c vista, euros. \n";
						}			
					enunciados.add(new Enunciado(fechaAñoSiguiente, enunciado2));		 
				 }
			 }

			 
		 //Cuotas de pago
		 }else{ 
			 //Mensual
			 if(inputs[2]==0){
				 tipoInteres = (inputs[4]/100)/12;
				 numPagos = inputs[3]*12;
				 
				 cuotaPago = inputs[0]*(tipoInteres*(Math.pow(1+tipoInteres, numPagos))/(Math.pow(1+tipoInteres, numPagos)-1));
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaMesSiguiente = (Calendar)fech.clone();
					 fechaMesSiguiente.add(Calendar.MONTH, +j);
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaPago, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 
					 String enunciado2 = " Se paga la "+j+"ª cuota del préstamo.\n";
						if (enunciadoCuentas){
							enunciado2 = enunciado2 + "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 662.Intereses de deudas; "
									+ "572. Bancos e instituciones de crédito c/c vista, euros. \n";
						}			
					enunciados.add(new Enunciado(fechaMesSiguiente, enunciado2));	 
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4]/100;
				 numPagos = inputs[3];
				 
				 cuotaPago = inputs[0]*(tipoInteres*(Math.pow(1+tipoInteres, numPagos))/(Math.pow(1+tipoInteres, numPagos)-1));
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fech.clone();
					 fechaAñoSiguiente.add(Calendar.YEAR, +j);
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaPago, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;		
					 
					 String enunciado2 = " Se paga la "+j+"ª cuota del préstamo.\n";
						if (enunciadoCuentas){
							enunciado2 = enunciado2 + "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 662.Intereses de deudas; "
									+ "572. Bancos e instituciones de crédito c/c vista, euros. \n";
						}			
					enunciados.add(new Enunciado(fechaAñoSiguiente, enunciado2));
				 }
			 }
		 }
	}
}



