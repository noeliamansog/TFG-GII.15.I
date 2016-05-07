package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class SueldosEmpleados extends Asiento {

	public SueldosEmpleados(Calendar f, int[] i) {
		fecha =f;
		inputs=i;
	
		String enunciado1 = " La empresa paga a cada uno de sus " +inputs[0]+ " empleados " +inputs[1]+ "€ de sueldo bruto al año. "
			 + "Por cada uno de sus empleados la empresa cotiza " +inputs[2]+ "€ a la Seguridad Social (cuota patronal), "
			 + "que pagará al año que viene. Se retiene el " +inputs[3]+ "% del salario bruto por I.R.P.F. y "
			 + "el " +inputs[4]+ "% del salario bruto en concepto de Seguridad Social a cargo del trabajador (cuota obrera). \n"
			 + "CUENTAS PGC: 640. Sueldo y salario; 642.S.S. a cargo de la empresa; 476.Organismos de la S.S acreedores; "
			 + " 4751.H.P acreedor por retenciones practicadas; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
	
		enunciados.add(new Enunciado(fecha, enunciado1));
	
	dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos empleados", (inputs[0]*inputs[1]), damePrioridad(640)));
	dameCuenta(642).añadirDebe(new Anotacion(fecha, "S.S. a cargo de la empresa", (inputs[0]*inputs[2]), damePrioridad(642)));
	dameCuenta(476).añadirHaber(new Anotacion(fecha, "Organismos de ls S.S acreedores", ((inputs[4]/((inputs[0]*inputs[1]))))*100, damePrioridad(476)));
	dameCuenta(4751).añadirHaber(new Anotacion(fecha, "H.P acreedor por retenciones practicadas", ((inputs[3]/((inputs[0]*inputs[1]))))*100, damePrioridad(4751)));
	dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos empleados", (inputs[0]*inputs[1]), damePrioridad(572)));
	
	}
}
