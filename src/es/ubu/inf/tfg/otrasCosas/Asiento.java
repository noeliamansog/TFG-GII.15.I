package es.ubu.inf.tfg.otrasCosas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Asiento {
	public ArrayList<Enunciado> enunciados = new ArrayList<Enunciado>();
	protected Calendar fecha;
	protected int[] inputs;
	protected static int numSocios;
	protected static int numAcciones;
	protected static HashMap<Integer, Cuenta> todasCuentas = new HashMap<Integer, Cuenta>();
	protected static HashMap<Integer, String> todosNombresCuentas = new HashMap<Integer, String>();
	

	public Asiento() {
		inicializarTodosNombresCuentas();
	
	}

	public Cuenta dameCuenta(int codigo) {
		Cuenta cuenta;
		
		if (todasCuentas.containsKey(codigo)) {
			cuenta = todasCuentas.get(codigo);
		} else {
			cuenta = new Cuenta(codigo, todosNombresCuentas.get(codigo));
			todasCuentas.put(codigo, cuenta);
		}
		return cuenta;
	}

	public void inicializarTodosNombresCuentas() {
		// INICIALIZMAOS TODAS LAS CUENTAS CON SU CODIGO Y SU NOMBRE
		todosNombresCuentas.put(12,"Resultados pendientes de aplicación.");

		todosNombresCuentas.put(100, "Capital social");
		todosNombresCuentas.put(110, "Prima de misión o sunción");
		todosNombresCuentas.put(112, "Reserva legar");
		todosNombresCuentas.put(129, "Resultado del ejercicio");
		todosNombresCuentas.put(170, "Deudas a largo plazo con entidades de crédito");
		todosNombresCuentas.put(173,"Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE)");

		todosNombresCuentas.put(200, "Gasto en investigación");
		todosNombresCuentas.put(202, "Concesiones administrativas");
		todosNombresCuentas.put(203, "Propiedad industrial");
		todosNombresCuentas.put(206, "Aplicaciones informáticas");
		todosNombresCuentas.put(210, "Terrenos y bienes naturales");
		todosNombresCuentas.put(211, "Construcciones");
		todosNombresCuentas.put(213, "Maquinaria");
		todosNombresCuentas.put(216, "Mobiliario");
		todosNombresCuentas.put(217, "Equipos para procesos de información");
		todosNombresCuentas.put(218, "Elementos de transporte");
		todosNombresCuentas.put(280,"Amortización acumulada del inmovilizado intangible");
		todosNombresCuentas.put(281,"Amortización acumulada del inmovilizado material");
		
		todosNombresCuentas.put(300, "Mercaderías");

		todosNombresCuentas.put(400, "Proveedores");
		todosNombresCuentas.put(430, "Clientes");
		todosNombresCuentas.put(472, "H.P. IVA Soportado");
		todosNombresCuentas.put(473, "Hacienda Pública, retenciones y pagos a cuenta.");
		todosNombresCuentas.put(476, "Organismos de la S.S acreedores");
		todosNombresCuentas.put(477, "H.P. IVA Repercutido");

		todosNombresCuentas.put(523,"Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE)");
		todosNombresCuentas.put(572,"Bancos e instituciones de crédito c/c vista, euros");

		todosNombresCuentas.put(600, "Compra de mercaderías");
		todosNombresCuentas.put(610, "Variación de existencias de mercaderías");
		todosNombresCuentas.put(640, "Sueldos y salario");
		todosNombresCuentas.put(642, "S.S a cargo de la empresa");
		todosNombresCuentas.put(662, "Intereses de deudas");
		todosNombresCuentas.put(680, "Amortización del inmovilizado intangible");
		todosNombresCuentas.put(681, "Amortización del inmovilizado material");

		todosNombresCuentas.put(700, "Venta de mercaderías");
		todosNombresCuentas.put(705, "Prestaciones de servicios");
		todosNombresCuentas.put(730, "Trabajos realizados para el inmovilizado intangible");
		todosNombresCuentas.put(769, "Otros ingressos financieros");

		todosNombresCuentas.put(4700, "H.P deudor por IVA");
		todosNombresCuentas.put(4750, "H.P Acreedor por IVA");
		todosNombresCuentas.put(4751,"H.P Acreedor por rentenciones practicadas");
	}
	
	
}
