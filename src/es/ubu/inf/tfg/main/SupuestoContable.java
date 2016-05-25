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
	public static Calendar fecha = Calendar.getInstance();
	
	public static void main(String args[]) {

		ArrayList<ArrayList<Enunciado>> todosEnunciados = new ArrayList<ArrayList<Enunciado>>();	
		
		boolean enunciadoCuentas = true;
		
		//APORTACIÓN INICIAL
		double [] inputsAportacion = {numEmpleados, 50000};
		AportacionInicial aportacion = new AportacionInicial(fecha, inputsAportacion, enunciadoCuentas);
		todosEnunciados.add(aportacion.enunciados);
		
		//PRESTAMO		
		Calendar fechaSig1 = (Calendar)fecha.clone();
		fechaSig1.add(Calendar.DAY_OF_YEAR, +1);
		
		double [] inputsPrestamo = {60000, 0, 1, 10, 5};
		Prestamo prestamo = new Prestamo(fechaSig1, inputsPrestamo, enunciadoCuentas);
		todosEnunciados.add(prestamo.enunciados);
	
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		Calendar fechaSig2 = (Calendar)fecha.clone();
		fechaSig2.add(Calendar.DAY_OF_YEAR, +2);
		double [] inputsMaterialNoAmortizable = {0, 120000, 60000, 2};
		CompraMaterialNoAmortizable materialNoAmortizable = new CompraMaterialNoAmortizable(fechaSig2, inputsMaterialNoAmortizable, enunciadoCuentas);
		todosEnunciados.add(materialNoAmortizable.enunciados);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		Calendar fechaSig3 = (Calendar)fecha.clone();
		fechaSig3.add(Calendar.DAY_OF_YEAR, +3);
		double [] inputsMaterialAmortizable = {1, 8000, 30, 4};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable(fechaSig3, inputsMaterialAmortizable, enunciadoCuentas);
		todosEnunciados.add(materialAmortizable.enunciados);
		
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		Calendar fechaSig4 = (Calendar)fecha.clone();
		fechaSig4.add(Calendar.DAY_OF_YEAR, +4);
		double [] inputsIntangibleNoAmortizable = {1000};
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fechaSig4, inputsIntangibleNoAmortizable, enunciadoCuentas);
		todosEnunciados.add(intangibleNoAmortizable.enunciados);
		
		//COMPRA_SOFTWARE_AMORTIZABLE
		Calendar fechaSig5 = (Calendar)fecha.clone();
		fechaSig5.add(Calendar.DAY_OF_YEAR, +5);
		double [] inputsSoftwareAmortizable = {3000, 60, 3};
		CompraSoftwareAmortizable softwareAmortizable = new CompraSoftwareAmortizable(fechaSig5, inputsSoftwareAmortizable, enunciadoCuentas);
		todosEnunciados.add(softwareAmortizable.enunciados);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		Calendar fechaSig6 = (Calendar)fecha.clone();
		fechaSig6.add(Calendar.DAY_OF_YEAR, +6);
		double [] inputsPropiedadIndustrialAmortizable = {1500, 90, 3};
		CompraPropiedadIndustrialaAmortizable propiedadIndustrialAmortizable = new CompraPropiedadIndustrialaAmortizable(fechaSig6, inputsPropiedadIndustrialAmortizable, enunciadoCuentas);
		todosEnunciados.add(propiedadIndustrialAmortizable.enunciados);
		
		//COMPRA_MERCADERIAS
		Calendar fechaSig7 = (Calendar)fecha.clone();
		fechaSig7.add(Calendar.DAY_OF_YEAR, +7);
		double [] inputsCompraMercaderias = {20000, IVA, 60};
		CompraMercaderias compraMercaderias = new CompraMercaderias(fechaSig7, inputsCompraMercaderias, enunciadoCuentas);
		todosEnunciados.add(compraMercaderias.enunciados);
		
		//VENTA_MERCADERIAS
		Calendar fechaSig8 = (Calendar)fecha.clone();
		fechaSig8.add(Calendar.DAY_OF_YEAR, +8);
		double [] inputsVentaMercaderias = {30000, IVA, 30};
		VentaMercaderias ventaMercaderias = new VentaMercaderias(fechaSig8, inputsVentaMercaderias, enunciadoCuentas);
		todosEnunciados.add(ventaMercaderias.enunciados);
		
		//VENTA_PROYECTO
		Calendar fechaSig9 = (Calendar)fecha.clone();
		fechaSig9.add(Calendar.DAY_OF_YEAR, +9);
		double [] inputsVentaProyecto = {200000, IVA, 30};
		VentaProyecto ventaProyecto = new VentaProyecto(fechaSig9, inputsVentaProyecto, enunciadoCuentas);
		todosEnunciados.add(ventaProyecto.enunciados);
		
		//SUELDOS_EMPLEADOS
		Calendar fechaSig10 = (Calendar)fecha.clone();
		fechaSig10.add(Calendar.DAY_OF_YEAR, +10);
		double [] inputsSueldoEmpleado = {numEmpleados, 10000, 3350, 10, 5};
		SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fechaSig10, inputsSueldoEmpleado, enunciadoCuentas);
		todosEnunciados.add(sueldoEmpleado.enunciados);
		
		//SUELDO_INGENIERO
		Calendar fechaSig11 = (Calendar)fecha.clone();
		fechaSig11.add(Calendar.DAY_OF_YEAR, +11);
		double [] inputsSueldoIngeniero = {30000, 30};
		SueldoIngeniero sueldoIngeniero = new SueldoIngeniero(fechaSig11, inputsSueldoIngeniero, enunciadoCuentas);
		todosEnunciados.add(sueldoIngeniero.enunciados);
		
		//INTERESES
		Calendar fechaSig12 = (Calendar)fecha.clone();
		fechaSig12.add(Calendar.DAY_OF_YEAR, +12);
		double [] inputsIntereses = {300, 80};
		Interes intereses = new Interes(fechaSig12, inputsIntereses, enunciadoCuentas);
		todosEnunciados.add(intereses.enunciados);
		
		//NUEVOS_SOCIOS
		Calendar fechaSig13 = (Calendar)fecha.clone();
		fechaSig13.add(Calendar.DAY_OF_YEAR, +13);
		double [] inputsNuevoSocio = {60000, numAcciones};
		NuevoSocio nuevoSocio = new NuevoSocio(fechaSig13, inputsNuevoSocio, enunciadoCuentas);
		todosEnunciados.add(nuevoSocio.enunciados);
		

		/*
		//PAGO DEUDAS HACIENDA
		 PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fecha, null, enunciadoCuentas);
		 todosEnunciados.add(deudasHacienda.enunciados);
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		 PagoDeudasSS deudasSS = new PagoDeudasSS(fecha, null, enunciadoCuentas);
		 todosEnunciados.add(deudasSS.enunciados);
		 
		
		//DIVIDENDOS
		double [] inputsDividendos = {20, 15};
		Dividendos dividendos = new Dividendos (fecha, inputsDividendos, enunciadoCuentas);
		todosEnunciados.add(dividendos.enunciados);

		
		//INVENTARIO
		double [] inputsInventario = {2000};
		Inventario inventario = new Inventario (fecha, inputsInventario, enunciadoCuentas);
		todosEnunciados.add(inventario.enunciados);

		*/
		
			
		//Rango de fechas
		Calendar fechaDesde =  Calendar.getInstance();
		//fechaDesde.set(2016,11,31);
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.set(2018,11,31);
		
		//ENUNCIADO
		ArrayList<Enunciado> todosEnunciadosOrdenados = ordenaEnunciadosPorFecha(todosEnunciados);
		imprimeEnunciados(todosEnunciadosOrdenados, fechaHasta);
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados = new CuentaResultados (fecha, fechaDesde, fechaHasta, impuestoSociedad);
		cuentaResultados.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria = new Tesoreria (fechaDesde, fechaHasta);
		tesoreria.imprimeTesoreria();
		
		//BALANCE
		Balance balance = new Balance(fechaHasta);
		balance.imprimeBalance();
		
		
		//CIERRE
		Calendar fechaSig14 = (Calendar)fecha.clone();
		fechaSig14.add(Calendar.DAY_OF_YEAR, +14);
		double [] inputsCierre = {impuestoSociedad};
		Cierre cierre = new Cierre (fechaSig14, inputsCierre, enunciadoCuentas);
		todosEnunciados.add(cierre.enunciados);
			
	}
	
	public static ArrayList<Enunciado> ordenaEnunciadosPorFecha(ArrayList<ArrayList<Enunciado>>todosEnunciados){
		ArrayList<Enunciado> ordenada = new ArrayList<Enunciado>();
		for(int i=0; i<todosEnunciados.size(); i++){
			for(int j=0; j<todosEnunciados.get(i).size(); j++){
				ordenada.add(todosEnunciados.get(i).get(j));				
			}
		}
		Collections.sort(ordenada);
		return ordenada;
	}
	
	public static void imprimeEnunciados (ArrayList<Enunciado> todosEnunciadosOrdenados, Calendar fechaLimite){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		Calendar fech;
		String enun;
		
		Document documento = new Document();
		
		try{	
			PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Enunciado.pdf"));
			
			documento.open();
			
			Paragraph par = new Paragraph("ENUNCIADO SUPUESTO CONTABLE:  \n \n", FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.GRAY)); 
			documento.add(par);
			
			for(int i=0; i<todosEnunciadosOrdenados.size(); i++){
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
				 + "acción emitida el " +formateador.format(fecha.getTime())
				 + ". ¿Cuál sería el valor de mercado de las acciones? \n"));
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}

}
