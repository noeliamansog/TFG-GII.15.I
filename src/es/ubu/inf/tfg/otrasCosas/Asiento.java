package es.ubu.inf.tfg.otrasCosas;
import java.util.Calendar;
import java.util.HashMap;

public class Asiento {
	protected String enunciado;
	protected Calendar fecha;
	protected int[] inputs;
	static HashMap<Integer, Cuenta> todasCuentas = new HashMap<Integer, Cuenta>();
	static HashMap<Integer, String> todosNombresCuentas = new HashMap<Integer, String>();
	static HashMap<Integer, Integer> prioridades = new HashMap<Integer, Integer>();

	public Asiento() {
		inicializarTodosNombresCuentas();
		inicializarPrioridades();		
	}

	public HashMap<Integer, Cuenta> getTodasCuentas() {
		return this.todasCuentas;
	}
	public void setTodasCuentas(HashMap<Integer, Cuenta> todasCuentas) {
		this.todasCuentas = todasCuentas;
	}

	public HashMap<Integer, String> getTodosNombresCuentas() {
		return todosNombresCuentas;
	}
	public void setTodosNombresCuentas(
			HashMap<Integer, String> todosNombresCuentas) {
		this.todosNombresCuentas = todosNombresCuentas;
	}

	public HashMap<Integer, Integer> getPrioridades() {
		return prioridades;
	}
	public void setPrioridades(HashMap<Integer, Integer> prioridades) {
		this.prioridades = prioridades;
	}

	public Cuenta dameCuenta(int codigo) {
		Cuenta cuenta;

		if (getTodasCuentas().containsKey(codigo)) {
			cuenta = getTodasCuentas().get(codigo);
		} else {
			cuenta = new Cuenta(codigo, getTodosNombresCuentas().get(codigo));
			getTodasCuentas().put(codigo, cuenta);
			setTodasCuentas(getTodasCuentas());
		}
		return cuenta;
	}

	public void inicializarTodosNombresCuentas() {
		// INICIALIZMAOS TODAS LAS CUENTAS CON SU CODIGO Y SU NOMBRE
		todosNombresCuentas
				.put(17,
						"Deudas a largo plazo por préstamos recibidos y otros conceptos.");

		todosNombresCuentas.put(100, "Capital social");
		// todosNombresCuentas.put(129, "Resultado del ejercicio");
		todosNombresCuentas
				.put(173,
						"Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE)");

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
		todosNombresCuentas.put(280,
				"Amortización acumulada del inmovilizado intangible");
		todosNombresCuentas.put(281,
				"Amortización acumulada del inmovilizado material");

		todosNombresCuentas.put(400, "Proveedores");
		todosNombresCuentas.put(430, "Clientes");
		todosNombresCuentas.put(472, "H.P. IVA Soportado");
		todosNombresCuentas.put(476, "Organismos de la S.S acreedores");
		todosNombresCuentas.put(473,
				"Hacienda Pública, retenciones y pagos a cuenta");
		todosNombresCuentas.put(477, "H.P. IVA Repercutido");

		todosNombresCuentas.put(523,
				"Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE)");
		todosNombresCuentas.put(572,
				"Bancos e instituciones de crédito c/c vista, euros");

		todosNombresCuentas.put(600, "Compra de mercaderías");
		todosNombresCuentas.put(640, "Sueldos y salario");
		todosNombresCuentas.put(642, "S.S a cargo de la empresa");
		todosNombresCuentas.put(662, "Intereses de deudas");
		todosNombresCuentas
				.put(680, "Amortización del inmovilizado intangible");
		todosNombresCuentas.put(681, "Amortización del inmovilizado material");

		todosNombresCuentas.put(700, "Venta de mercaderías");
		todosNombresCuentas.put(705, "Prestaciones de servicios");
		todosNombresCuentas.put(730,
				"Trabajos realizados para el inmovilizado intangible");
		todosNombresCuentas.put(769, "Otros ingressos financieros");

		todosNombresCuentas.put(4700, "H.P deudor por IVA");
		todosNombresCuentas.put(4750, "H.P Acreedor por IVA");
		todosNombresCuentas.put(4751,
				"H.P Acreedor por rentenciones practicadas");
	}
	
	public void inicializarPrioridades() {
		// INICIALIZAMOS PRIORIDADES CON EL CODIGO DE LA CUENTA Y SUS
		// PRIORIDADES
		prioridades.put(17, -21);

		prioridades.put(100, -1);
		// prioridades.put(129, value);
		prioridades.put(173, -23);

		prioridades.put(200, 13);
		prioridades.put(202, 12);
		prioridades.put(203, 11);
		prioridades.put(206, 14);
		prioridades.put(210, 1);
		prioridades.put(211, 2);
		prioridades.put(213, 4);
		prioridades.put(216, 6);
		prioridades.put(217, 8);
		prioridades.put(218, 5);
		prioridades.put(280, 20);
		prioridades.put(281, 10);

		prioridades.put(400, -40);
		prioridades.put(430, 42);
		prioridades.put(472, 0);
		prioridades.put(473, 48);
		prioridades.put(476, -50);
		prioridades.put(477, 0);

		prioridades.put(523, -35);
		prioridades.put(572, 59);

		prioridades.put(600, 0);
		prioridades.put(640, 0);
		prioridades.put(642, 0);
		prioridades.put(662, 0);
		prioridades.put(680, 0);
		prioridades.put(681, 0);

		prioridades.put(700, 0);
		prioridades.put(705, 0);
		prioridades.put(730, 0);
		prioridades.put(769, 0);

		prioridades.put(4751, -51);
		prioridades.put(4700, 45);
		prioridades.put(4750, -52);
	}
}
