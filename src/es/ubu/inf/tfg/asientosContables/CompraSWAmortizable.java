package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;

public class CompraSWAmortizable extends Asiento{
	
	public CompraSWAmortizable(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
				
		String enunciado1 = " La empresa compra una aplicación informática por valor de " +inputs[0]+ "€. "
				 + "El importe de la compra se abonará a los " +(int)inputs[1]+ " días. El software se amortiza linealmente "
				 + "en " +(int)inputs[2]+ " años.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 206. Aplicaciones informáticas; 400. Proveedores \n";
		}
	
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(206).añadirDebe(new Anotacion(fecha, "Software", inputs[0], damePrioridad(206)));
		dameCuenta(400).añadirHaber(new Anotacion(fecha, "Proveedores Software", inputs[0], damePrioridad(400)));
		
		
		//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES:
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
		
		String enunciado2 = " Se salda la deuda con los proveedores de software.\n";
		if (enunciadoCuentas){
			enunciado2 = enunciado2 + "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		}
		
		enunciados.add(new Enunciado(fechaDeudas, enunciado2));
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores Software", inputs[0], damePrioridad(400)));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Software", inputs[0], damePrioridad(572)));
		

		
		//SE AÑADE LA AMORTIZACIÓN AL FINAL DEL AÑO:
		String enunciado3;
		
		for (int j=0; j<inputs[2]; j++){
			Calendar fechaAmortizacion = (Calendar)fecha.clone();
			fechaAmortizacion.set(fechaAmortizacion.get(Calendar.YEAR), 11, 31);
			fechaAmortizacion.add(Calendar.YEAR, +j);
			enunciado3 = " Se añade la amortización del software al final del año.\n";
			if (enunciadoCuentas){
				enunciado3 = enunciado3 +"CUENTAS PGC: 280. Amortización acumulada del inmovilizado intangible; 680. Amortización del inmovilizado intangible.\n";
			}
			
			enunciados.add(new Enunciado(fechaAmortizacion, enunciado3));
			
			dameCuenta(680).añadirDebe(new Anotacion(fechaAmortizacion, "Amortización Software", (inputs[0]/inputs[2]), damePrioridad(680)));
			dameCuenta(280).añadirHaber(new Anotacion(fechaAmortizacion, "Amortización acumulada Software", (inputs[0]/inputs[2]), damePrioridad(280)));

		}		
	}
}
