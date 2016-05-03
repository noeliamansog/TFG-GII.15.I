import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Prestamo extends Asiento {

	public Prestamo(Calendar fecha, int [] inputs) {
	
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" La empresa obtiene un préstamo por importe de " +inputs[0]+ "€, que se ingresa en la cuenta "
    			+ "corriente de la que es titular la empresa. El préstamo se devolverá en cuotas de " +inputs[1]+" "
    			+inputs[2]+ "constantes, en " +inputs[3]+ " años, a un tipo de interés fijo del " +inputs[4]+ "%."
    			+ "El primer pago se realizará al cabo de un "+inputs[5]+" desde la concesión del prestamo. \n"
    			+ "CUENTAS PGC: 17. Deudas a largo plazo por préstamos recibidos y otros conceptos; 572. Bancos "
    			+ "e instituciones de crédito c/c vista, euros; 662. Intereses de deudas \n";
		
		System.out.println(enunciado);
		
		
		//CUANDO ME DAN EL PRESTAMO:
		dameCuenta(527).añadirDebe(new Anotacion(fecha, "Prestamo", (inputs[0])));
				
		dameCuenta(17).añadirHaber(new Anotacion(fecha, "Prestamo", (inputs[0])));
		/*
		//PAGO DE UNA CUOTA:
		 if(inputs[1]==0){ //cuotas de amortización
		 	//inputs[0] --> importe
		 	//inputs[2] --> anual o mensual
		 	//inputs[3] --> años
		 	//inputs[4] --> interes
		  
		 }else{ //cuotas de pago
		 	dameCuenta(17).añadirDebe(new Anotacion(fecha, "Cuota prestamo", (inputs[0])));
		 	dameCuenta(662).añadirDebe(new Anotacion(fecha, "Interés del prestamo", (inputs[4])));
		 	
		 	dameCuenta(17).añadirHaber(new Anotacion(fecha, "Prestamo", (inputs[0]/inputs[3])));
		 
		 }
		 */
	}
}
