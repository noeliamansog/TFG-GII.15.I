package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class VentaProyecto extends Asiento {

	public VentaProyecto(Calendar f, int[] i) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = " La empresa entrega un proyecto a un cliente, por el cual factura " +inputs[0]+ "€ "
				+ "más un " +inputs[1]+ "% de IVA. Se acuerda que el cliente pague en " +inputs[2]+ " días. El cliente paga al contado. \n"
				+ "CUENTAS PGC: 430. Clientes; 705. Prestaciones de servicios; 477. H.P. IVA Repercutido.\n";  
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes proyecto", inputs[0]));
		dameCuenta(705).añadirHaber(new Anotacion(fecha, "Prestaciones de servicios", inputs[0]));
		dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA proyecto", (inputs[1])));
			
		
		//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[2]);
		
		String enunciado2 = " Los clientes saldan su deuda del proyecto con la empresa.\n"
				+ "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		
		enunciados.add(new Enunciado(fechaDeudas, enunciado2));

		dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Bancos proyecto", inputs[0]));
		dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Clientes proyecto", inputs[0]));	
	}
}
