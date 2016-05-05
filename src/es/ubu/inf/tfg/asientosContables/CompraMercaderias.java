package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class CompraMercaderias extends Asiento{

	public CompraMercaderias(Calendar f, int[] i) {
		fecha =f;
		inputs=i;

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		enunciado= formateador.format(fecha.getTime())+" La empresa compra mercaderías por un importe de " +inputs[0]+ "€ más "
				 + "un " +inputs[1]+ "% de IVA. Se acuerda que el pago se realice en " +inputs[2]+ " días. Se paga al contado. \n"
				 + "CUENTAS PGC: 400. Proveedores; 600. Compra de mercaderías; 472. H.P. IVA Soportado.\n";
		
		System.out.println(enunciado);
		
		dameCuenta(600).añadirDebe(new Anotacion(fecha, "Compra mercaderias", inputs[0]));
		dameCuenta(472).añadirDebe(new Anotacion(fecha, "H.P IVA Soportado", inputs[1]));
		dameCuenta(400).añadirHaber(new Anotacion(fecha, "Proveedores mercaderias", (inputs[0])));
				
				
		//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES:
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, +inputs[2]);
		
		String enunciado2 = formateador.format(fechaDeudas.getTime())+" Se salda la deuda con los vendedores de las mercaderías.\n"
				+ "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		
		System.out.println(enunciado2);

		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Proveedores mercaderias", inputs[0]));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Bancos mercaderias", inputs[0]));
		
	}
}
