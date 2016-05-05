package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class VentaMercaderias extends Asiento {

	public VentaMercaderias(Calendar f, int[] i) {
		fecha =f;
		inputs=i;

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		enunciado= formateador.format(fecha.getTime())+" La empresa efectúa una venta de mercaderías por importe de " +inputs[0]+ "€ más un " +inputs[1]+ "% de IVA. " 
				 + "Se acuerda que el cliente pague en " +inputs[2]+ " días. El cliente paga al contado. \n"
				 + "CUENTAS PGC: 430. Clientes; 700. Venta de mercaderías; 477. H.P. IVA Repercutido. \n";  
		
		System.out.println(enunciado);

		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes mercaderías", inputs[0]));
		dameCuenta(700).añadirHaber(new Anotacion(fecha, "Ventas mercaderías", inputs[0]));
		dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA mercaderias", (inputs[1])));
			
		
		//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[2]);
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+ " Los clientes saldan su deuda de mercaderías con la empresa.\n"
				+ "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		
		System.out.println(enunciado2);

		dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Bancos mercaderias", inputs[0]));
		dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Clientes mercaderias", inputs[0]));
	}
}
