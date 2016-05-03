package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;


public class VentaMercaderias extends Asiento {


	public VentaMercaderias(Calendar fecha, int[] inputs) {

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		enunciado= formateador.format(fecha.getTime())+"La empresa efectúa una venta de mercaderías por importe de " +inputs[0]+ "€ más un " +inputs[1]+ "% de IVA. " 
				 + "[Se acuerda que el cliente pague en 30 días, el cliente paga al contado]. \n"
				 + "CUENTAS PGC: 430. Clientes. 700. Venta de mercaderías. 572. Bancos e instituciones de "
				 + "crédito c/c vista, euros. 477. H.P. IVA Repercutido. \n";  
		
		System.out.println(enunciado);

		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes mercaderías", inputs[0]));
		
		dameCuenta(700).añadirHaber(new Anotacion(fecha, "Ventas mercaderías", inputs[0]));
		
		dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA mercaderias", (inputs[1])));
			
		
		//DESPUES DE 30 DIAS (Se salda la deuda con la empresa)
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.MONTH, 30);

		dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Bancos mercaderias", inputs[0]));
		dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Clientes mercaderias", inputs[0]));
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+ "Los clientes saldan su deuda con la empresa.\n"
				+ "CUENTAS PGC: 430. Clientes. 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		System.out.println(enunciado2);
		
	}

}
