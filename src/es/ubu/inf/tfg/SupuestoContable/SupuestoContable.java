
import java.text.SimpleDateFormat;
import java.util.Date;


public class SupuestoContable {
	public static void main(String args[]) {
		
		int impuestoSociedad = 12;
		int IVA = 12;
		
		Date fecha = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		//APORTACIÓN INICIAL
		int [] inputsAportacion = {2, 1000};
		AportacionInicial aportacion = new AportacionInicial("lalala", fecha, inputsAportacion, null);
		
		/*PRESTAMO
		int [] inputsPrestamo = {100, 1, 1, 5, 12, 1};
		Prestamo prestamo = new Prestamo("lalala", fecha, inputsPrestamo, null);*/
		
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		int [] inputsMaterialNoAmortizable = {1, 100000, 50000, 3};
		CompraMatrialNoAmortizable materialNoAmortizable = new CompraMatrialNoAmortizable("lalala", fecha, inputsMaterialNoAmortizable, null);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		int [] inputsMaterialAmortizable = {1, 10000, 80, 5};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable("lalala", fecha, inputsMaterialAmortizable, null);
		
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		int [] inputsIntangibleNoAmortizable = null;
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable("lalala", fecha, inputsIntangibleNoAmortizable, null);
		
		//COMPRA_INTANGIBLE_AMORTIZABLE
		int [] inputsIntangibleAmortizable = {3000, 80, 3, 3000, 80, 3};
		CompraIntangibleAmortizable intangibleAmortizable = new CompraIntangibleAmortizable("lalala", fecha, inputsIntangibleAmortizable, null);
		
		
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
				 + "acción emitida el " +formateador.format(fecha)+ ". ¿Cuál sería el valor de mercado de las acciones? \n";
		
		System.out.println(enunciadoEjercicio);
		
	}

}
