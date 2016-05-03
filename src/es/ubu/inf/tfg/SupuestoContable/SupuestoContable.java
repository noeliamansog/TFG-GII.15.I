import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SupuestoContable {
	
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
		int [] inputsIntereses = {300};
		Interes intereses = new Interes(fecha, inputsIntereses);
		
		//NUEVOS_SOCIOS
		
		//CIERRE
		int [] inputsCierre = {20};
		Cierre cierre = new Cierre (fecha, inputsCierre);
		
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
