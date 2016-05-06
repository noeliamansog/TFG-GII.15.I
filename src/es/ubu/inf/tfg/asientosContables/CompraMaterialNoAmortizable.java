package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class CompraMaterialNoAmortizable extends Asiento {

	public CompraMaterialNoAmortizable(Calendar f, int[] i) {
		fecha =f;
		inputs=i;
		String compra=null;
		
		
		if (inputs[0]==0){
			compra = "un solar";
		}else{
			compra = "un local donde instalar la oficina";
		}
		
		String enunciado1 = " La empresa compra " +compra+ " por importe de " +inputs[1]+ "€. "
     			+ "Este activo no es amortizable. Se abonan " +inputs[2]+ "€ mediante transferencia y "
     			+ "quedan " +(inputs[1]-inputs[2])+ "€ pendiente de pago. Se acuerda que la deuda se pagará "
     			+ "en " +inputs[3]+ " meses. \n";
			
		if (inputs[0]==1){
			enunciado1 = enunciado1 + "CUENTAS PGC: 210. Terrenos y bienes naturales; 572. Bancos e instituciones de crédito c/c vista, euros;";							
			dameCuenta(210).añadirDebe(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[1]));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[1]));
		}else{
			enunciado1 = enunciado1 + "CUENTAS PGC: 211. Construcciones; 572. Bancos e instituciones de crédito c/c vista, euros;";
			dameCuenta(211).añadirDebe(new Anotacion(fecha, "Construcciones", inputs[1]));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Construcciones", inputs[1]));
		}
		
		if (inputs[3]<12){
			enunciado1 = enunciado1 + " 523. Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE). \n";
			dameCuenta(523).añadirHaber(new Anotacion(fecha, "Proveedores", (inputs[1]-inputs[2])));
		}else{
			enunciado1 = enunciado1 + " 173. Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE). \n";
			dameCuenta(173).añadirHaber(new Anotacion(fecha, "Proveedores", (inputs[1]-inputs[2])));
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
			
		//SE SALDAN LAS DEUDAS CON LOS VENDEDORES "Y" DIAS DESPUES
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.MONTH, +inputs[3]);
		
		String enunciado2 = " Se salda la deuda con los vendedores de " +compra+ ".\n"
				+ "CUENTAS PGC: 400.Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros.\n";

		enunciados.add(new Enunciado(fechaDeudas, enunciado2));
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores", (inputs[1]-inputs[2])));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos proveedores", (inputs[1]-inputs[2])));
					
	}

}