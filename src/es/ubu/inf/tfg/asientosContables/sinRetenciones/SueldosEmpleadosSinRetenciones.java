package es.ubu.inf.tfg.asientosContables.sinRetenciones;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class SueldosEmpleadosSinRetenciones extends Asiento {

	public SueldosEmpleadosSinRetenciones(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
	}
	
	public void generar(Calendar f, double[] inputs){
		String enunciado1 = " La empresa paga a cada uno de sus " +(int)inputs[0]+ " empleados " +inputs[1]+ "€ de sueldo bruto al año. "
				 + "Por cada uno de sus empleados la empresa cotiza " +inputs[2]+ "€ a la Seguridad Social (cuota patronal), "
				 + "que pagará al año que viene. \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 640. Sueldo y salario; 642.S.S. a cargo de la empresa; "
										+ "572. Bancos e instituciones de crédito c/c vista, euros.\n";
			}
			
			enunciados.add(new Enunciado(fecha, enunciado1));
		
			dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos y salarios", inputs[0]*inputs[1], damePrioridad(640)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Sueldos y salarios", inputs[0]*inputs[1], damePrioridad(572)));
		
			dameCuenta(642).añadirDebe(new Anotacion(fecha, "S.S. a cargo de la empresa", (inputs[0]*inputs[2]), damePrioridad(642)));
			dameCuenta(476).añadirHaber(new Anotacion(fecha, "Organismos de la S.S. acreedores", (inputs[0]*inputs[2]), damePrioridad(476)));
	}
}
