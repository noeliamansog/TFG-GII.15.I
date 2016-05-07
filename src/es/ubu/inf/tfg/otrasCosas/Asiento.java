package es.ubu.inf.tfg.otrasCosas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Asiento {
	
	public ArrayList<Enunciado> enunciados = new ArrayList<Enunciado>();
	public Calendar fecha;
	public int[] inputs;
	protected static int numSocios;
	protected static int numAcciones;
	protected static HashMap<Integer, Cuenta> cuentas = new HashMap<Integer, Cuenta>();
	protected static HashMap<Integer, Cuenta> todasCuentas = new HashMap<Integer, Cuenta>();
	
	public Asiento() {
		inicializarTodasCuentas();
	}

	public Cuenta dameCuenta(int codigo) {
		Cuenta cuenta;
		
		if (cuentas.containsKey(codigo)) {
			cuenta = cuentas.get(codigo);
		} else {
			cuenta = new Cuenta(codigo, todasCuentas.get(codigo).nombre, todasCuentas.get(codigo).prioridad);
			cuentas.put(codigo, cuenta);
		}
		return cuenta;
	}

	public void inicializarTodasCuentas() {
		// INICIALIZMAOS TODAS LAS CUENTAS CON SU CODIGO, SU NOMBRE Y SU PRIORIDAD
		todasCuentas.put(12,new Cuenta(12, "Resultados pendientes de aplicación.", -11));

		todasCuentas.put(100, new Cuenta (100, "Capital social", -1));
		todasCuentas.put(110, new Cuenta (110, "Prima de misión o sunción", -7));
		todasCuentas.put(112, new Cuenta (112, "Reserva legar", -6));
		todasCuentas.put(129, new Cuenta (129, "Resultado del ejercicio", -10));
		todasCuentas.put(170, new Cuenta (170, "Deudas a largo plazo con entidades de crédito", -21));
		todasCuentas.put(173, new Cuenta (173, "Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE)", -23));

		todasCuentas.put(200, new Cuenta (200, "Gasto en investigación", 13));
		todasCuentas.put(202, new Cuenta (202, "Concesiones administrativas", 12));
		todasCuentas.put(203, new Cuenta (203, "Propiedad industrial", 11));
		todasCuentas.put(206, new Cuenta (206, "Aplicaciones informáticas", 14));
		todasCuentas.put(210, new Cuenta (210, "Terrenos y bienes naturales", 1));
		todasCuentas.put(211, new Cuenta (211, "Construcciones", 2));
		todasCuentas.put(213, new Cuenta (213, "Maquinaria", 4));
		todasCuentas.put(216, new Cuenta (216, "Mobiliario", 6));
		todasCuentas.put(217, new Cuenta (217, "Equipos para procesos de información", 8));
		todasCuentas.put(218, new Cuenta (218, "Elementos de transporte", 5));
		todasCuentas.put(280, new Cuenta (280, "Amortización acumulada del inmovilizado intangible", 20));
		todasCuentas.put(281, new Cuenta (281, "Amortización acumulada del inmovilizado material", 10));
		
		todasCuentas.put(300, new Cuenta (300, "Mercaderías", 35));

		todasCuentas.put(400, new Cuenta (400, "Proveedores", -40));
		todasCuentas.put(430, new Cuenta (430, "Clientes", 42));
		todasCuentas.put(472, new Cuenta (472, "H.P. IVA Soportado", 0));
		todasCuentas.put(473, new Cuenta (472, "Hacienda Pública, retenciones y pagos a cuenta.", 48));
		todasCuentas.put(476, new Cuenta (476, "Organismos de la S.S acreedores", -50));
		todasCuentas.put(477, new Cuenta (477, "H.P. IVA Repercutido", 0));

		todasCuentas.put(523, new Cuenta (523, "Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE)", -35));
		todasCuentas.put(572, new Cuenta (572, "Bancos e instituciones de crédito c/c vista, euros", 59));

		todasCuentas.put(600, new Cuenta (600, "Compra de mercaderías", 0));
		todasCuentas.put(610, new Cuenta (610, "Variación de existencias de mercaderías", 0));
		todasCuentas.put(640, new Cuenta (640, "Sueldos y salario", 0));
		todasCuentas.put(642, new Cuenta (642, "S.S a cargo de la empresa", 0));
		todasCuentas.put(662, new Cuenta (662, "Intereses de deudas", 0));
		todasCuentas.put(680, new Cuenta (680, "Amortización del inmovilizado intangible", 0));
		todasCuentas.put(681, new Cuenta (681, "Amortización del inmovilizado material", 0));

		todasCuentas.put(700, new Cuenta (700, "Venta de mercaderías", 0));
		todasCuentas.put(705, new Cuenta (705, "Prestaciones de servicios", 0));
		todasCuentas.put(730, new Cuenta (730, "Trabajos realizados para el inmovilizado intangible", 0));
		todasCuentas.put(769, new Cuenta (769, "Otros ingressos financieros", 0));

		todasCuentas.put(4700, new Cuenta (4700, "H.P deudor por IVA", 45));
		todasCuentas.put(4750, new Cuenta (4750, "H.P Acreedor por IVA", -51));
		todasCuentas.put(4751,new Cuenta (4751, "H.P Acreedor por rentenciones practicadas", -52));
	}
}