package es.ubu.inf.tfg.main;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.ubu.inf.tfg.asientosContables.*;
import es.ubu.inf.tfg.otrasCosas.*;

public class SupuestoContable {
	public static double impuestoSociedad = 30;
	public static double IVA = 10;
	public static double numEmpleados = 2;
	public static double numAcciones = numEmpleados;
	
	public static void main(String args[]) {

		ArrayList<ArrayList<Enunciado>> todosEnunciados = new ArrayList<ArrayList<Enunciado>>();	
		ArrayList<Calendar> todasFechas = new ArrayList<Calendar>();
		
		//Opcion de poner enunciados con las cuentas PGC
		boolean enunciadoCuentas = false;
		
		//Año para las tablas
		int annoFin = 2018;
		int anoInicio;
		int ano;

		
		//APORTACIÓN INICIAL
		Calendar fechaAportacion = Calendar.getInstance();
		double [] inputsAportacion = {numEmpleados, 50000};
		AportacionInicial aportacion = new AportacionInicial(fechaAportacion, inputsAportacion, enunciadoCuentas);
		todosEnunciados.add(aportacion.enunciados);
		todasFechas.add(fechaAportacion);
		
		//PRESTAMO		
		Calendar fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.add(Calendar.DAY_OF_YEAR, +1);
		double [] inputsPrestamo = {60000, 0, 1, 10, 5};
		Prestamo prestamo = new Prestamo(fechaPrestamo, inputsPrestamo, enunciadoCuentas);
		todosEnunciados.add(prestamo.enunciados);
		todasFechas.add(fechaPrestamo);
	
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		Calendar fechaCompraMatNoAmort = Calendar.getInstance();
		fechaCompraMatNoAmort.add(Calendar.DAY_OF_YEAR, +2);
		double [] inputsMaterialNoAmortizable = {0, 120000, 60000, 2};
		CompraMaterialNoAmortizable materialNoAmortizable = new CompraMaterialNoAmortizable(fechaCompraMatNoAmort, inputsMaterialNoAmortizable, enunciadoCuentas);
		todosEnunciados.add(materialNoAmortizable.enunciados);
		todasFechas.add(fechaCompraMatNoAmort);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		Calendar fechaCompraMatAmort = Calendar.getInstance();
		fechaCompraMatAmort.add(Calendar.DAY_OF_YEAR, +3);
		double [] inputsMaterialAmortizable = {1, 8000, 30, 4};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable(fechaCompraMatAmort, inputsMaterialAmortizable, enunciadoCuentas);
		todosEnunciados.add(materialAmortizable.enunciados);
		todasFechas.add(fechaCompraMatAmort);
		
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		Calendar fechaCompraInNoAmort = Calendar.getInstance();
		fechaCompraInNoAmort.add(Calendar.DAY_OF_YEAR, +4);
		double [] inputsIntangibleNoAmortizable = {1000};
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fechaCompraInNoAmort, inputsIntangibleNoAmortizable, enunciadoCuentas);
		todosEnunciados.add(intangibleNoAmortizable.enunciados);
		todasFechas.add(fechaCompraInNoAmort);
		
		//COMPRA_SOFTWARE_AMORTIZABLE
		Calendar fechaCompraSW = Calendar.getInstance();
		fechaCompraSW.add(Calendar.DAY_OF_YEAR, +5);
		double [] inputsSoftwareAmortizable = {3000, 60, 3};
		CompraSoftwareAmortizable softwareAmortizable = new CompraSoftwareAmortizable(fechaCompraSW, inputsSoftwareAmortizable, enunciadoCuentas);
		todosEnunciados.add(softwareAmortizable.enunciados);
		todasFechas.add(fechaCompraSW);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		Calendar fechaCompraPI = Calendar.getInstance();
		fechaCompraPI.add(Calendar.DAY_OF_YEAR, +6);
		double [] inputsPropiedadIndustrialAmortizable = {1500, 90, 3};
		CompraPropiedadIndustrialaAmortizable propiedadIndustrialAmortizable = new CompraPropiedadIndustrialaAmortizable(fechaCompraPI, inputsPropiedadIndustrialAmortizable, enunciadoCuentas);
		todosEnunciados.add(propiedadIndustrialAmortizable.enunciados);
		todasFechas.add(fechaCompraPI);
		
		//COMPRA_MERCADERIAS
		Calendar fechaCompraMercaderias = Calendar.getInstance();
		fechaCompraMercaderias.add(Calendar.DAY_OF_YEAR, +7);
		double [] inputsCompraMercaderias = {20000, IVA, 60};
		CompraMercaderias compraMercaderias = new CompraMercaderias(fechaCompraMercaderias, inputsCompraMercaderias, enunciadoCuentas);
		todosEnunciados.add(compraMercaderias.enunciados);
		todasFechas.add(fechaCompraMercaderias);
		
		//VENTA_MERCADERIAS
		Calendar fechaVentaMercaderias = Calendar.getInstance();
		fechaVentaMercaderias.add(Calendar.DAY_OF_YEAR, +8);
		double [] inputsVentaMercaderias = {30000, IVA, 30};
		VentaMercaderias ventaMercaderias = new VentaMercaderias(fechaVentaMercaderias, inputsVentaMercaderias, enunciadoCuentas);
		todosEnunciados.add(ventaMercaderias.enunciados);
		todasFechas.add(fechaVentaMercaderias);
		
		//VENTA_PROYECTO
		Calendar fechaVentaProy = Calendar.getInstance();
		fechaVentaProy.add(Calendar.DAY_OF_YEAR, +9);
		double [] inputsVentaProyecto = {200000, IVA, 30};
		VentaProyecto ventaProyecto = new VentaProyecto(fechaVentaProy, inputsVentaProyecto, enunciadoCuentas);
		todosEnunciados.add(ventaProyecto.enunciados);
		todasFechas.add(fechaVentaProy);
		
		//SUELDOS_EMPLEADOS
		Calendar fechaSueldoEmp = Calendar.getInstance();
		fechaSueldoEmp.add(Calendar.DAY_OF_YEAR, +10);
		double [] inputsSueldoEmpleado = {numEmpleados, 10000, 3350, 10, 5};
		SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fechaSueldoEmp, inputsSueldoEmpleado, enunciadoCuentas);
		todosEnunciados.add(sueldoEmpleado.enunciados);
		todasFechas.add(fechaSueldoEmp);
		
		//SUELDO_INGENIERO
		Calendar fechaSueldoIng = Calendar.getInstance();
		fechaSueldoIng.add(Calendar.DAY_OF_YEAR, +11);
		double [] inputsSueldoIngeniero = {30000, 30};
		SueldoIngeniero sueldoIngeniero = new SueldoIngeniero(fechaSueldoIng, inputsSueldoIngeniero, enunciadoCuentas);
		todosEnunciados.add(sueldoIngeniero.enunciados);
		todasFechas.add(fechaSueldoIng);
		
		//INTERESES
		Calendar fechaIntereses = Calendar.getInstance();
		fechaIntereses.add(Calendar.DAY_OF_YEAR, +12);
		double [] inputsIntereses = {300, 80};
		Interes intereses = new Interes(fechaIntereses, inputsIntereses, enunciadoCuentas);
		todosEnunciados.add(intereses.enunciados);
		todasFechas.add(fechaIntereses);
		
		//NUEVOS_SOCIOS
		Calendar fechaNuevosSoc = Calendar.getInstance();
		fechaNuevosSoc.add(Calendar.DAY_OF_YEAR, +13);
		double [] inputsNuevoSocio = {60000, numAcciones};
		NuevoSocio nuevoSocio = new NuevoSocio(fechaNuevosSoc, inputsNuevoSocio, enunciadoCuentas);
		todosEnunciados.add(nuevoSocio.enunciados);
		todasFechas.add(fechaNuevosSoc);
		
		//INVENTARIO
		Calendar fechaInventario = Calendar.getInstance();
		fechaInventario.set(annoFin-2,11,31);
		double [] inputsInventario = {2000};
		Inventario inventario = new Inventario (fechaInventario, inputsInventario, enunciadoCuentas);
		todosEnunciados.add(inventario.enunciados);
		todasFechas.add(fechaInventario);
			
		//CIERRRE
		Calendar fechaCierre = Calendar.getInstance();
		fechaCierre.set(2016,11,31);
		double [] inputsCierre = {impuestoSociedad};
		Cierre cierre = new Cierre (fechaCierre, inputsCierre, enunciadoCuentas);
		todosEnunciados.add(cierre.enunciados);
		todasFechas.add(fechaCierre);
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados = new CuentaResultados (2016, impuestoSociedad);
		cuentaResultados.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria = new Tesoreria (2016);
		tesoreria.imprimeTesoreria();
		
		//BALANCE
		Balance balance = new Balance(2016);
		balance.imprimeBalance();
		
		//PAGO DEUDAS HACIENDA
		Calendar fechaDeudasHP = Calendar.getInstance();
		fechaDeudasHP.add(Calendar.YEAR, +1);
		PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fechaDeudasHP, null, enunciadoCuentas);
		todosEnunciados.add(deudasHacienda.enunciados);
		todasFechas.add(fechaDeudasHP);
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		Calendar fechaDeudasSS = Calendar.getInstance();
		fechaDeudasSS.add(Calendar.YEAR, +1);
		PagoDeudasSS deudasSS = new PagoDeudasSS(fechaDeudasSS, null, enunciadoCuentas);
		todosEnunciados.add(deudasSS.enunciados); 
		todasFechas.add(fechaDeudasSS);
		
		//DIVIDENDOS
		Calendar fechaDividendos = Calendar.getInstance();
		fechaDividendos.add(Calendar.YEAR, +1);
		double [] inputsDividendos = {50, 20};
		Dividendos dividendos = new Dividendos (fechaDividendos, inputsDividendos, enunciadoCuentas);
		todosEnunciados.add(dividendos.enunciados);
		todasFechas.add(fechaDividendos);
		
		//CIERRRE
		Calendar fechaCierre2 = Calendar.getInstance();
		fechaCierre2.set(2017,11,31);
		double [] inputsCierre2 = {impuestoSociedad};
		Cierre cierre2 = new Cierre (fechaCierre2, inputsCierre2, enunciadoCuentas);
		todosEnunciados.add(cierre2.enunciados);
		todasFechas.add(fechaCierre2);
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados2 = new CuentaResultados (2017, impuestoSociedad);
		cuentaResultados2.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria2 = new Tesoreria (2017);
		tesoreria2.imprimeTesoreria();
		
		//BALANCE
		Balance balance2 = new Balance(2017);
		balance2.imprimeBalance();
			
		//SI NO HAY DIVIDENDOS
		
		//Calculo el año de la fecha mas vieja
		anoInicio=2999;
		for (int i=0; i<todasFechas.size(); i++){
			if (todasFechas.get(i).get(Calendar.YEAR) < anoInicio){
				anoInicio=todasFechas.get(i).get(Calendar.YEAR);
			}
		}

		ano = anoInicio;
		for(int i=0; i<=(annoFin-anoInicio); i++){	
			/*CIERRRE
			Calendar fechaCierre = Calendar.getInstance();
			fechaCierre.set(ano,11,31);
			double [] inputsCierre = {impuestoSociedad};
			Cierre cierre = new Cierre (fechaCierre, inputsCierre, enunciadoCuentas);
			todosEnunciados.add(cierre.enunciados);
			todasFechas.add(fechaCierre);*/
			
			/*//CUENTA DE PERDIDAS Y GANANCIAS
			CuentaResultados cuentaResultados = new CuentaResultados (ano, impuestoSociedad);
			cuentaResultados.imprimeCuentaResultados();
			
			//TESORERIA
			Tesoreria tesoreria = new Tesoreria (ano);
			tesoreria.imprimeTesoreria();
			
			//BALANCE
			Balance balance = new Balance(ano);
			balance.imprimeBalance();
			
			ano = ano + 1;*/
		}	
		
		
		
		//ENUNCIADO
		imprimeEnunciados(todosEnunciados, anoInicio, annoFin);
	
	}
	
	
	public static void imprimeEnunciados (ArrayList<ArrayList<Enunciado>>todosEnunciados, int anoInicio, int anoFin){
		Calendar fechaLimite = Calendar.getInstance();
		fechaLimite.set(anoFin,11,31);
		ArrayList<Enunciado> todosEnunciadosOrdenados = new ArrayList<Enunciado>();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		Calendar fech;
		String enun;
		
		//Ordenamos cada enunciado de cada asiento por fecha
		for(int i=0; i<todosEnunciados.size(); i++){
			for(int j=0; j<todosEnunciados.get(i).size(); j++){
				todosEnunciadosOrdenados.add(todosEnunciados.get(i).get(j));				
			}
		}
		Collections.sort(todosEnunciadosOrdenados);
		
		Document documento = new Document();
		
		try{	
			PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Enunciado.pdf"));
			
			documento.open();
			
			Paragraph par = new Paragraph("ENUNCIADO SUPUESTO CONTABLE:  \n \n", FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK)); 
			documento.add(par);
			
			int annoTemp=anoInicio;
			for(int i=0; i<todosEnunciadosOrdenados.size(); i++){
				if(todosEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==annoTemp && annoTemp<=anoFin){
					Paragraph p = new Paragraph("Se tiene la siguiente información vinculada con la empresa correspondiente al año "+annoTemp+":\n\n", FontFactory.getFont("arial", 12, Font.BOLDITALIC, BaseColor.BLACK)); 
					documento.add(p);
					annoTemp++;
				}
				if(todosEnunciadosOrdenados.get(i).fecha.before(fechaLimite)){
					fech= todosEnunciadosOrdenados.get(i).getFecha();
					enun = todosEnunciadosOrdenados.get(i).getEnunciado();
					documento.add(new Paragraph(formateador.format(fech.getTime())+ " " + enun));
					documento.add(new Paragraph("\n"));	
				}
			}
			
			documento.add(new Paragraph("\n\nSe pide: \n"
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
				 + "acción emitida el " +formateador.format(fechaLimite.getTime())
				 + ". ¿Cuál sería el valor de mercado de las acciones? \n"));
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}

}
