package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class Prestamo extends Asiento {

	public Prestamo(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		nombre = "prestamo";	
	}
	
	public void generar(Calendar f, double[] inputs){
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
		
		
		//PAGO DE UNA CUOTA:
		//Cuotas de amortización
		 if(inputs[1]==0){
			 //Mensual
			 if(inputs[2]==0){
				 tipoInteres = (inputs[4]/100)/12;
				 numPagos = inputs[3]*12;
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 			 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaMesSiguiente = (Calendar)fecha.clone();
					 fechaMesSiguiente.add(Calendar.MONTH, +j);
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion+deudaViva*tipoInteres, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4]/100;
				 numPagos = inputs[3];
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fecha.clone();
					 fechaAñoSiguiente.add(Calendar.YEAR, +j);
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion+deudaViva*tipoInteres, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
		
					 
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
					 Calendar fechaMesSiguiente = (Calendar)fecha.clone();
					 fechaMesSiguiente.add(Calendar.MONTH, +j);
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, j+ "ª cuota prestamo", cuotaPago, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4]/100;
				 numPagos = inputs[3];
				 
				 cuotaPago = inputs[0]*(tipoInteres*(Math.pow(1+tipoInteres, numPagos))/(Math.pow(1+tipoInteres, numPagos)-1));
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fecha.clone();
					 fechaAñoSiguiente.add(Calendar.YEAR, +j);
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaAmortizacion, damePrioridad(170)));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo de la " +j+ "ª cuota", deudaViva*tipoInteres, damePrioridad(662)));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, j+ "ª cuota prestamo", cuotaPago, damePrioridad(572)));
					 deudaViva = deudaViva - cuotaAmortizacion;					 
				 }
			 }
		 }
		
	}
}



