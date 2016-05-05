package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class CompraMaterialAmortizable extends Asiento{
	
	public CompraMaterialAmortizable(Calendar f, int[] i) {
		fecha =f;
		inputs=i;
		String compra = null;
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		switch (inputs[0]){
			case 0: compra = "maquinaria"; break;
			case 1: compra = "mobiliario de oficina"; break;
			case 2:	compra = "equipos para procesos de información"; break;
			case 3: compra = "un elemento de transporte"; break;
		}
		
		enunciado = formateador.format(fecha.getTime())+" La empresa compra " +compra+ " por valor de " +inputs[1]+ "€. "
				 + "El importe de la compra se abonará a los " +inputs[2]+ " días. La compra "
				 + "se amortiza linealmente en " +inputs[3]+ " años.\n";
		
		switch (inputs[0]){
			case 0: enunciado = enunciado + "CUENTAS PGC: 213. Maquinaria; 400. Proveedores.\n";
					dameCuenta(213).añadirDebe(new Anotacion(fecha, "Maquinaria", inputs[1]));
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Maquinaria", inputs[1]));	
					break;
			case 1: enunciado = enunciado + "CUENTAS PGC: 216. Mobiliario; 400. Proveedores.\n"; 
					dameCuenta(216).añadirDebe(new Anotacion(fecha, "Mobiliario", inputs[1])); 
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Mobiliario", inputs[1]));	
					break;
			case 2: enunciado = enunciado + "CUENTAS PGC: 217. Equipos para procesos de información; 400. Proveedores.\n";
					dameCuenta(217).añadirDebe(new Anotacion(fecha, "Equipos para procesos de información", inputs[1])); 
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Equipos para procesos de información", inputs[1]));
					break;
			case 3: enunciado = enunciado + "CUENTAS PGC: 218. Elementos de transporte; 400. Proveedores.\n";
					dameCuenta(218).añadirDebe(new Anotacion(fecha, "Elementos de transporte", inputs[1]));
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Elementos de transporte", inputs[1]));
					break;
		}
		
		System.out.println(enunciado);
		
		
		//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[2]);
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+" Se salda la deuda con los proveedores de " +compra+ ".\n"
				+ "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		
		System.out.println(enunciado2);
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores", inputs[1]));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos proveedores", inputs[1]));
		

		
		//SE AÑADE LA AMORTIZACIÓN AL FINAL DEL AÑO:
		Calendar fechaAmortizacion = (Calendar)fecha.clone();
		fechaAmortizacion.set(fechaAmortizacion.get(Calendar.YEAR), 11, 31);
		
		String enunciado3 = formateador.format(fechaAmortizacion.getTime())+" Se añade la amortización de "+compra+" al final del año.\n"
				+"CUENTAS PGC: 281. Amortización acumulada del inmovilizado material; 681. Amortización del inmovilizado material.\n";
		
		System.out.println(enunciado3);
		
		for (int j=0; j<=inputs[3]; j++){
			dameCuenta(681).añadirDebe(new Anotacion(fechaAmortizacion, "Amortización inmovilizado", (inputs[1]/inputs[3])));
			dameCuenta(281).añadirHaber(new Anotacion(fechaAmortizacion, "Amortización acumulada del inmovilizado material", (inputs[1]/inputs[3])));		
			fechaAmortizacion.add(Calendar.YEAR, +1);
		}	
	}
}
