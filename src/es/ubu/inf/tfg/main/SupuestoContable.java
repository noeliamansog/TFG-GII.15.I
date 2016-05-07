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
	public static int impuestoSociedad = 12;
	public static int IVA = 10;
	public static int numEmpleados = 2;
	public static Calendar fecha = Calendar.getInstance();
	
	public static void main(String args[]) {

		ArrayList<ArrayList<Enunciado>> todosEnunciados = new ArrayList<ArrayList<Enunciado>>();
			

		//APORTACIÓN INICIAL
		int [] inputsAportacion = {numEmpleados, 50000};
		AportacionInicial aportacion = new AportacionInicial(fecha, inputsAportacion);
		todosEnunciados.add(aportacion.enunciados);
		
		//PRESTAMO
		int [] inputsPrestamo = {60000, 1, 1, 10, 5};
		Prestamo prestamo = new Prestamo(fecha, inputsPrestamo);
		todosEnunciados.add(prestamo.enunciados);
	
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		int [] inputsMaterialNoAmortizable = {0, 120000, 60000, 2};
		CompraMaterialNoAmortizable materialNoAmortizable = new CompraMaterialNoAmortizable(fecha, inputsMaterialNoAmortizable);
		todosEnunciados.add(materialNoAmortizable.enunciados);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		int [] inputsMaterialAmortizable = {1, 8000, 30, 4};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable(fecha, inputsMaterialAmortizable);
		todosEnunciados.add(materialAmortizable.enunciados);
		
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		int [] inputsIntangibleNoAmortizable = {1000};
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fecha, inputsIntangibleNoAmortizable);
		todosEnunciados.add(intangibleNoAmortizable.enunciados);
		
		//COMPRA_SOFTWARE_AMORTIZABLE
		int [] inputsSoftwareAmortizable = {3000, 60, 3};
		CompraSoftwareAmortizable softwareAmortizable = new CompraSoftwareAmortizable(fecha, inputsSoftwareAmortizable);
		todosEnunciados.add(softwareAmortizable.enunciados);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		int [] inputsPropiedadIndustrialAmortizable = {1500, 90, 3};
		CompraPropiedadIndustrialaAmortizable propiedadIndustrialAmortizable = new CompraPropiedadIndustrialaAmortizable(fecha, inputsPropiedadIndustrialAmortizable);
		todosEnunciados.add(propiedadIndustrialAmortizable.enunciados);
		
		//COMPRA_MERCADERIAS
		int [] inputsCompraMercaderias = {20000, IVA, 60};
		CompraMercaderias compraMercaderias = new CompraMercaderias(fecha, inputsCompraMercaderias);
		todosEnunciados.add(compraMercaderias.enunciados);
		
		//VENTA_MERCADERIAS
		int [] inputsVentaMercaderias = {30000, IVA, 30};
		VentaMercaderias ventaMercaderias = new VentaMercaderias(fecha, inputsVentaMercaderias);
		todosEnunciados.add(ventaMercaderias.enunciados);
		
		//VENTA_PROYECTO
		int [] inputsVentaProyecto = {20000, IVA, 30};
		VentaProyecto ventaProyecto = new VentaProyecto(fecha, inputsVentaProyecto);
		todosEnunciados.add(ventaProyecto.enunciados);
		
		//SUELDOS_EMPLEADOS
		int [] inputsSueldoEmpleado = {numEmpleados, 10000, 3350, 10, 5};
		SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fecha, inputsSueldoEmpleado);
		todosEnunciados.add(sueldoEmpleado.enunciados);
		
		//SUELDO_INGENIERO
		int [] inputsSueldoIngeniero = {30000, 30};
		SueldoIngeniero sueldoIngeniero = new SueldoIngeniero(fecha, inputsSueldoIngeniero);
		todosEnunciados.add(sueldoIngeniero.enunciados);
		
		//INTERESES
		int [] inputsIntereses = {300};
		Interes intereses = new Interes(fecha, inputsIntereses);
		todosEnunciados.add(intereses.enunciados);
		
		//NUEVOS_SOCIOS
		int [] inputsNuevoSocio = {10000};
		NuevoSocio nuevoSocio = new NuevoSocio(fecha, inputsNuevoSocio);
		todosEnunciados.add(nuevoSocio.enunciados);
		
		//CIERRE
		int [] inputsCierre = {impuestoSociedad};
		Cierre cierre = new Cierre (fecha, inputsCierre);
		todosEnunciados.add(cierre.enunciados);
		
		/*
		//PAGO DEUDAS HACIENDA
		 PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fecha, null);
		 todosEnunciados.add(deudasHacienda.enunciados);
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		 PagoDeudasSS deudasSS = new PagoDeudasSS(fecha, null);
		 todosEnunciados.add(deudasSS.enunciados);
		 
		//PAGO TODALIDAD PRESTAMO
		 PagoTodoPrestamo pagoTodoPrestamo = new PagoTodoPrestamo(fecha, null);
		 todosEnunciados.add(pagoTodoPrestamo.enunciados);
		
		//DIVIDENDOS
		int [] inputsDividendos = {20, 15};
		Dividendos dividendos = new Dividendos (fecha, inputsDividendos);
		todosEnunciados.add(dividendos.enunciados);

		
		//INVENTARIO
		int [] inputsInventario = {2000};
		Inventario inventario = new Inventario (fecha, inputsInventario);
		todosEnunciados.add(inventario.enunciados);

		*/
		
		//Meto todos los enunciados en una lista y la ordeno
		ArrayList<Enunciado> todosEnunciadosOrdenados = ordenaEnunciadosPorFecha(todosEnunciados);
		imprimeEnunciados(todosEnunciadosOrdenados);
		
		
		CuentaResultados cuentaResultados = new CuentaResultados ();
		cuentaResultados.imprimeCuentaResultados();
		
		Balance balance = new Balance();
		balance.imprimeBalance();
		
		Tesoreria tesoreria = new Tesoreria ();
		tesoreria.imprimeTesoreria();
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
	
	public static void imprimeEnunciados (ArrayList<Enunciado> todosEnunciadosOrdenados){
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
				fech= todosEnunciadosOrdenados.get(i).getFecha();
				enun = todosEnunciadosOrdenados.get(i).getEnunciado();
				documento.add(new Paragraph(formateador.format(fech.getTime())+ " " + enun));
				documento.add(new Paragraph("\n"));	
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
