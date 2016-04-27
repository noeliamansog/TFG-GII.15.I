import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CompraMaterialAmortizable extends Asiento{
	
	public CompraMaterialAmortizable(Calendar fecha, int[] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" La empresa compra " +inputs[0]+ " por valor de " +inputs[1]+ "€. "
				 + "El importe de la compra se abonará a los " +inputs[2]+ " días. La compra "
				 + "(" +inputs[0]+ ") se amortiza linealmente en " +inputs[3]+ " años.\n"
				 + "CUENTAS PGC: 213. Maquinaria; 216. Mobiliario; 217. Equipos para procesos "
				 + "de información; 218. Elementos de transporte; 400. Proveedores.\n";
		
		System.out.println(enunciado);
		
		switch (inputs[0]){
		case 0: dameCuenta(213).añadirDebe(new Anotacion(fecha, "Maquinaria", inputs[1]));
				dameCuenta(400).añadirHaber(new Anotacion(fecha, "Maquinaria", inputs[1]));	
				break;
		case 1:dameCuenta(216).añadirDebe(new Anotacion(fecha, "Mobiliario", inputs[1])); 
				dameCuenta(400).añadirHaber(new Anotacion(fecha, "Mobiliario", inputs[1]));	
				break;
		case 2: dameCuenta(217).añadirDebe(new Anotacion(fecha, "Equipos para procesos de información", inputs[1])); 
				dameCuenta(400).añadirHaber(new Anotacion(fecha, "Equipos para procesos de información", inputs[1]));
				break;
		case 3: dameCuenta(218).añadirDebe(new Anotacion(fecha, "Elementos de transporte", inputs[1]));
				dameCuenta(400).añadirHaber(new Anotacion(fecha, "Elementos de transporte", inputs[1]));
				break;
		}
		
		//DESPUES DE "Y" DIAS (Se salda la deuda con los proveedores)
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[2]);
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores", inputs[1]));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos proveedores", inputs[1]));
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+" Se salda la deuda con los proveedores de " +inputs[0]+ ".\n"
				+ "CUENTAS PGC: 400. Proveedores. 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		System.out.println(enunciado2);
		
		//AL FINAL DEL AÑO:
		Calendar fechaAmortizacion = (Calendar)fecha.clone();
		fechaAmortizacion.set(fechaAmortizacion.get(Calendar.YEAR), 11, 31);
				
		dameCuenta(681).añadirDebe(new Anotacion(fechaAmortizacion, "Amortización inmovilizado", (inputs[1]/inputs[3])));
		dameCuenta(281).añadirHaber(new Anotacion(fechaAmortizacion, "Amortización acumulada del inmovilizado material", (inputs[1]/inputs[3])));
		
		String enunciado3 = formateador.format(fechaAmortizacion.getTime())+" Se añade la amortización al final del año.\n"
				+"CUENTAS PGC: 281. Amortización acumulada del inmovilizado material. 681. Amortización del inmovilizado material.\n";
		System.out.println(enunciado3);
	}

}
