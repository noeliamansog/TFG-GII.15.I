package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class VentaProyecto extends Asiento {

	public VentaProyecto(Calendar fecha, int[] inputs) {

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		enunciado= formateador.format(fecha.getTime())+"La empresa entrega un proyecto a un cliente, por el cual factura " +inputs[0]+ "€ "
				+ "más un " +inputs[1]+ "% de IVA. [Se acuerda que el cliente pague en 30 días, el cliente paga al contado]. \n"
				+ "CUENTAS PGC: 430. Clientes. 705. Prestaciones de servicios. 572. Bancos e instituciones de crédito c/c vista, euros. "
				+ "477. H.P. IVA Repercutido.\n";  
		
		System.out.println(enunciado);
		
		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes proyecto", inputs[0]));
		
		dameCuenta(705).añadirHaber(new Anotacion(fecha, "Prestaciones de servicios", inputs[0]));
		
		dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA proyecto", (inputs[1])));
			
		
		//DESPUES DE 30 DIAS (Se salda la deuda con la empresa)
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.MONTH, 30);

		dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Bancos proyecto", inputs[0]));
		dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Clientes proyecto", inputs[0]));
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+ "Los clientes saldan su deuda con la empresa.\n"
				+ "CUENTAS PGC: 430. Clientes. 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		System.out.println(enunciado2);
		
	}

}
