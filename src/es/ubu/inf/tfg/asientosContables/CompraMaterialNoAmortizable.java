package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class CompraMaterialNoAmortizable extends Asiento {
	
	public CompraMaterialNoAmortizable(Calendar fecha, int[] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		enunciado = formateador.format(fecha.getTime())+" La empresa compra " +inputs[0]+ " por importe de " +inputs[1]+ "€. "
     			+ "Este activo no es amortizable. Se abonan " +inputs[2]+ "€ mediante transferencia y "
     			+ "quedan " +(inputs[1]-inputs[2])+ "€ pendiente de pago. Se acuerda que la deuda se pagará "
     			+ "en " +inputs[3]+ " meses. \n"
     			+ "CUENTAS PGC: 210. Terrenos y bienes naturales; 211. Construcciones; 173. Proveedores de inmovilizado "
     			+ " a largo plazo (PASIVO NO CORRIENTE); 523. Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE); "
     			+ "572. Bancos e instituciones de crédito c/c vista, euros. \n";
		
		System.out.println(enunciado);
		
		if (inputs[0]==1){
			dameCuenta(210).añadirDebe(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[1]));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[1]));
		}else{
			dameCuenta(211).añadirDebe(new Anotacion(fecha, "Construcciones", inputs[1]));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Construcciones", inputs[1]));
		}
		
		
		if (inputs[3]<12){
			dameCuenta(523).añadirHaber(new Anotacion(fecha, "Proveedores", (inputs[1]-inputs[2])));
		}else{
			dameCuenta(173).añadirHaber(new Anotacion(fecha, "Proveedores", (inputs[1]-inputs[2])));
		}
			
		
		//DESPUES DE "Y" DIAS (Se salda la deuda con los vendedores)
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.MONTH, +inputs[3]);

		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores", (inputs[1]-inputs[2])));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos proveedores", (inputs[1]-inputs[2])));
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+" Se salda la deuda con los vendedores.\n"
				+ "CUENTAS PGC: Si es en menos de un año (W<12), cuenta 523 (ver abajo). Si es más de un año (W>12), cuenta 173.\n";
		System.out.println(enunciado2);
					
	}

}