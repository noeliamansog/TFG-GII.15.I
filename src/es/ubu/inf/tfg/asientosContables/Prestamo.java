package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class Prestamo extends Asiento {

	public Prestamo(Calendar f, int [] i) {
		fecha =f;
		inputs=i;
		String tipoPrestamo=null;
		String mensualAnual=null;
		String mesAno=null;
		double cuotaAmortizacion = 0;
		double cuotaPago = 0;
		double tipoInteres = 0;
		double deudaViva = inputs[0];
		int numPagos = 0;
	

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
    			+mensualAnual+ " constantes, en " +inputs[3]+ " años, a un tipo de interés fijo del " +inputs[4]+ "% nominal. "
    			+ "El primer pago se realizará al cabo de un "+mesAno+" desde la concesión del préstamo. \n"
    			+ "CUENTAS PGC: 170. Deudas a largo plazo con entidades de crédito; 572. Bancos e instituciones de crédito c/c vista, euros; "
    			+ "662. Intereses de deudas. \n";
		

		enunciados.add(new Enunciado(fecha, enunciado1));
		
		//CUANDO ME DAN EL PRESTAMO:
		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Prestamo", (inputs[0])));		
		dameCuenta(170).añadirHaber(new Anotacion(fecha, "Prestamo", (inputs[0])));
		
		
		//PAGO DE UNA CUOTA:
		//Cuotas de amortización
		 if(inputs[1]==0){
			 //Mensual
			 if(inputs[2]==0){
				 tipoInteres = Math.pow(1 + inputs[4], (1/12)-1);
				 numPagos = inputs[3]*12;
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 			 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaMesSiguiente = (Calendar)fecha.clone();
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo, cuota numero:"+j, deudaViva*tipoInteres));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion+deudaViva*tipoInteres));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 fechaMesSiguiente.add(Calendar.MONTH, +j-1);
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4];
				 numPagos = inputs[3];
				 
				 cuotaAmortizacion = inputs[0]/numPagos;
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fecha.clone();
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo, cuota numero:"+j, deudaViva*tipoInteres));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion+deudaViva*tipoInteres));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 fechaAñoSiguiente.add(Calendar.YEAR, +j-1);
				 }
			 }

			 
		 //Cuotas de pago
		 }else{ 
			 //Mensual
			 if(inputs[2]==0){
				 tipoInteres = Math.pow(1 + inputs[4], (1/12)-1);
				 numPagos = inputs[3]*12;
				 
				 cuotaPago = (Math.pow(inputs[0]*(1+tipoInteres), numPagos)*tipoInteres)/(Math.pow(1+tipoInteres, numPagos)-1);
				 
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaMesSiguiente = (Calendar)fecha.clone();
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaMesSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaMesSiguiente, "Intereses préstamo, cuota numero:"+j, deudaViva*tipoInteres));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaMesSiguiente, "Cuota prestamo, numero:"+j, cuotaPago));
					 deudaViva = deudaViva - cuotaAmortizacion;
					 fechaMesSiguiente.add(Calendar.MONTH, +j-1);
				 }
			 //Anual
			 }else{
				 tipoInteres =inputs[4];
				 numPagos = inputs[3];
				 
				 cuotaPago = (Math.pow(inputs[0]*(1+tipoInteres), numPagos)*tipoInteres)/(Math.pow(1+tipoInteres, numPagos)-1);
				 
				 //Calendar fechaAñoSiguiente = (Calendar)fecha.clone();
				 for(int j=1; j<=numPagos; j++){
					 Calendar fechaAñoSiguiente = (Calendar)fecha.clone();
					 cuotaAmortizacion =  cuotaPago-deudaViva*tipoInteres;
					 dameCuenta(170).añadirDebe(new Anotacion(fechaAñoSiguiente, "Cuota prestamo, numero:"+j, cuotaAmortizacion));
					 dameCuenta(662).añadirDebe(new Anotacion(fechaAñoSiguiente, "Intereses préstamo, cuota numero:"+j, deudaViva*tipoInteres));
					 dameCuenta(572).añadirHaber(new Anotacion(fechaAñoSiguiente, "Cuota prestamo, numero:"+j, cuotaPago));
					 deudaViva = deudaViva - cuotaAmortizacion;
	
					 fechaAñoSiguiente.add(Calendar.YEAR, +j-1);
				 }
			 }
		 }
	}
}
