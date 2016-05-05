package es.ubu.inf.tfg.asientosContables;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;

public class NuevoSocio extends Asiento {

	public NuevoSocio(Calendar f, int[] i) {
		fecha =f;
		inputs=i;

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se incorpora un nuevo socio a la empresa, el cual aporta " +inputs[0]+ "€ en efectivo "
				 + "(los cuales se ingresan en cuenta corriente) a cambio de una acción de nueva emisión. \n"
				 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 110 Prima de misión o asunción. "
				 + "100. Capital social \n";
		
		System.out.println(enunciado);
		
		double valorNominal = dameCuenta(100).getSaldo(fecha);
		
		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Aportacion nuevo socio", inputs[0]));
		dameCuenta(100).añadirHaber(new Anotacion(fecha, "Valor nominal nuevo socio", valorNominal));	
		dameCuenta(110).añadirHaber(new Anotacion(fecha, "Prima nuevo socio", inputs[0]-valorNominal));
	}
}
