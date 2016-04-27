import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SupuestoContable {
	
	HashMap<Integer, Cuenta> todasCuentas = new HashMap<Integer, Cuenta>();
	HashMap<Integer, String> todosNombresCuentas= new HashMap<Integer, String>();
	HashMap<Integer, Integer> prioridades = new HashMap<Integer, Integer>();
	
	public SupuestoContable(){
		//INICIALIZMAOS TODAS LAS CUENTAS CON SU CODIGO Y SU NOMBRE
		todosNombresCuentas.put(17, "Deudas a largo plazo por préstamos recibidos y otros conceptos.");
		
		todosNombresCuentas.put(100, "Capital social");
		//todosNombresCuentas.put(129, "Resultado del ejercicio");
		todosNombresCuentas.put(173, "Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE)");
		
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
		todosNombresCuentas.put(280, "Amortización acumulada del inmovilizado intangible");
		todosNombresCuentas.put(281, "Amortización acumulada del inmovilizado material");
		
		todosNombresCuentas.put(400, "Proveedores");
		todosNombresCuentas.put(430, "Clientes");
		todosNombresCuentas.put(472, "H.P. IVA Soportado");
		todosNombresCuentas.put(476, "Organismos de la S.S acreedores");
		todosNombresCuentas.put(473, "Hacienda Pública, retenciones y pagos a cuenta");
		todosNombresCuentas.put(477, "H.P. IVA Repercutido");
		
		todosNombresCuentas.put(523, "Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE)");
		todosNombresCuentas.put(572, "Bancos e instituciones de crédito c/c vista, euros");
		
		todosNombresCuentas.put(600, "Compra de mercaderías");
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
		todosNombresCuentas.put(4751, "H.P Acreedor por rentenciones practicadas");

		
		
		//INICIALIZAMOS PRIORIDADES CON EL CODIGO DE LA CUENTA Y SUS PRIORIDADES
		prioridades.put(17, -21);
		
		prioridades.put(100, -1);
		//prioridades.put(129, value);
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
		prioridades.put(769,0);
		
		prioridades.put(4751, -51);
		prioridades.put(4700, 45);
		prioridades.put(4750, -52);
		

	}
	
	public static void main(String args[]) {
		int impuestoSociedad = 12;
		int IVA = 12;
		
		Calendar fecha = Calendar.getInstance();
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		//APORTACIÓN INICIAL
		int [] inputsAportacion = {2, 1000};
		AportacionInicial aportacion = new AportacionInicial(fecha, inputsAportacion);
		
		//PRESTAMO
		int [] inputsPrestamo = {100, 1, 1, 5, 12, 1};
		Prestamo prestamo = new Prestamo(fecha, inputsPrestamo);
		
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		int [] inputsMaterialNoAmortizable = {1, 100000, 50000, 3};
		CompraMaterialNoAmortizable materialNoAmortizable = new CompraMaterialNoAmortizable(fecha, inputsMaterialNoAmortizable);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		int [] inputsMaterialAmortizable = {1, 10000, 80, 5};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable(fecha, inputsMaterialAmortizable);
	
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		int [] inputsIntangibleNoAmortizable = {1000};
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fecha, inputsIntangibleNoAmortizable);
		//COMPRA_SOFTWARE_AMORTIZABLE
		int [] inputsSoftwareAmortizable = {3000, 80, 3,};
		CompraSoftwareAmortizable softwareAmortizable = new CompraSoftwareAmortizable(fecha, inputsSoftwareAmortizable);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		int [] inputsPropiedadIndustrialAmortizable = {3000, 80, 3,};
		CompraPropiedadIndustrialaAmortizable propiedadIndustrialaAmortizable = new CompraPropiedadIndustrialaAmortizable(fecha, inputsPropiedadIndustrialAmortizable);
		
		//COMPRA_MERCADERIAS
		int [] inputsCompraMercaderias = {30000, 10};
		CompraMercaderias compraMercaderias = new CompraMercaderias(fecha, inputsCompraMercaderias);
		
		//VENTA_MERCADERIAS
		int [] inputsVentaMercaderias = {40000, 10};
		VentaMercaderias ventaMercaderias = new VentaMercaderias(fecha, inputsVentaMercaderias);
		
		//VENTA_PROYECTO
		int [] inputsVentaProyecto = {20000, 10};
		VentaProyecto ventaProyecto = new VentaProyecto(fecha, inputsVentaProyecto);
		
		//SUELDOS_EMPLEADOS
		int [] inputsSueldoEmpleado = {2, 1500, 500, 10, 5};
		SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fecha, inputsSueldoEmpleado);
		
		//SUELDO_INGENIERO
		int [] inputsSueldoIngeniero = {3000, 30};
		SueldoIngeniero sueldoIngeniero = new SueldoIngeniero(fecha, inputsSueldoIngeniero);
		
		//INTERESES
		
		//NUEVOS_SOCIOS
		
		//CIERRE
		
		String enunciadoEjercicio = "\n\nSe pide: \n"
				 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
				 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
				 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. El impuesto "
				 + "de sociedades es el " +impuestoSociedad+ "% del beneficio y el IVA es " +IVA+ "%.\n"
				 + "  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
				 + "que se cumple la ecuación que las liga. \n"
				 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.\n"
				 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.\n"
				 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n"
				 + "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
				 + "acción emitida el " +formateador.format(fecha.getTime())+ ". ¿Cuál sería el valor de mercado de las acciones? \n";
		
		System.out.println(enunciadoEjercicio);
		
	}

}
