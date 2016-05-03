package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class CompraPropiedadIndustrialaAmortizable extends Asiento{
	
	public CompraPropiedadIndustrialaAmortizable(Calendar fecha, int[] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
				
		enunciado = formateador.format(fecha.getTime())+"La empresa adquiere el derecho a usar un logotipo, por "
				+ " lo cual paga " +inputs[0]+ "€. El importe de la compra se abonará a los " +inputs[1]+ " días. "
				+ " La propiedad industrial se amortiza linealmente en " +inputs[2]+ " años.\n"
				+ "CUENTAS PGC: 203. Propiedad industrial; 400. Proveedores \n";
	
		System.out.println(enunciado);
		
		dameCuenta(203).añadirDebe(new Anotacion(fecha, "Propiedad industrial", inputs[0]));
		dameCuenta(400).añadirHaber(new Anotacion(fecha, "Proveedores Propiedad industrial", inputs[0]));
		
		
		// DESPUES DE "Y" DIAS (Se salda la deuda con los proveedores):
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[1]);
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores Propiedad industrial", inputs[0]));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos Propiedad industrial", inputs[0]));
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+" Se salda la deuda con los proveedores de software.\n"
				+ "CUENTAS PGC: 400. Proveedores. 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		System.out.println(enunciado2);
		
		
		//AL FINAL DEL AÑO:
		Calendar fechaAmortizacion = (Calendar)fecha.clone();
		fechaAmortizacion.set(fechaAmortizacion.get(Calendar.YEAR), 11, 31);
						
		dameCuenta(680).añadirDebe(new Anotacion(fechaAmortizacion, "Amortización Propiedad industrial", (inputs[0]/inputs[2])));
		dameCuenta(280).añadirHaber(new Anotacion(fechaAmortizacion, "Amortización acumulada Propiedad industrial", (inputs[0]/inputs[2])));
				
		String enunciado3 = formateador.format(fechaAmortizacion.getTime())+" Se añade la amortización al final del año.\n"
				+"CUENTAS PGC: 280. Amortización acumulada del inmovilizado intangible. 680. Amortización del inmovilizado intangible.\n";
		System.out.println(enunciado3);
		
	}
	
	
}
