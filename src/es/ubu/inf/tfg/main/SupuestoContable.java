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
import es.ubu.inf.tfg.asientosContables.sinIVA.CompraMercaderiasSinIVA;
import es.ubu.inf.tfg.asientosContables.sinIVA.PagoDeudasHaciendaSinIVA;
import es.ubu.inf.tfg.asientosContables.sinIVA.VentaMercaderiasSinIVA;
import es.ubu.inf.tfg.asientosContables.sinIVA.VentaProyectoSinIVA;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.DividendosSinRetenciones;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.InteresSinRetenciones;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.PagoDeudasHaciendaSinRetenciones;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.SueldosEmpleadosSinRetenciones;
import es.ubu.inf.tfg.doc.*;
import es.ubu.inf.tfg.tablasResultados.Balance;
import es.ubu.inf.tfg.tablasResultados.CuentaResultados;
import es.ubu.inf.tfg.tablasResultados.Tesoreria;

public class SupuestoContable {
	public static double impuestoSociedad = 30;
	public static double IVA = 10;
	public static double numEmpleados = 2;
	public static double numAcciones = numEmpleados;
	public static boolean enunciadoCuentas;
	public static boolean conIVA;
	public static boolean conRetenciones;
	
	public static void main(String args[]) {

		ArrayList<ArrayList<Enunciado>> todosEnunciados = new ArrayList<ArrayList<Enunciado>>();	
		ArrayList<Calendar> todasFechas = new ArrayList<Calendar>();
		
		Dividendos dividendos = null;
		Dividendos dividendos2 = null;
		// ... para todos los años... 
		DividendosSinRetenciones dividendosSinReteneciones = null;
		DividendosSinRetenciones dividendosSinReteneciones2 = null;
		
		//Opcion de poner enunciados con las cuentas PGC
		enunciadoCuentas = false;
		
		//Opcion del supuesto contable con o sin IVA
		conIVA = true;
		
		//Opcion del supuesto contable con o sin Retenciones
		conRetenciones = true;

		//Años para las tablas:
		int anoInicio;
		int anoFin = 2018;
		
		

		//////////////////////////
		////	 1º AÑO 	/////	
		////////////////////////
		
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
		CompraSWAmortizable softwareAmortizable = new CompraSWAmortizable(fechaCompraSW, inputsSoftwareAmortizable, enunciadoCuentas);
		todosEnunciados.add(softwareAmortizable.enunciados);
		todasFechas.add(fechaCompraSW);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		Calendar fechaCompraPI = Calendar.getInstance();
		fechaCompraPI.add(Calendar.DAY_OF_YEAR, +6);
		double [] inputsPropiedadIndustrialAmortizable = {1500, 90, 3};
		CompraPIAmortizable propiedadIndustrialAmortizable = new CompraPIAmortizable(fechaCompraPI, inputsPropiedadIndustrialAmortizable, enunciadoCuentas);
		todosEnunciados.add(propiedadIndustrialAmortizable.enunciados);
		todasFechas.add(fechaCompraPI);
		
		//COMPRA_MERCADERIAS
		Calendar fechaCompraMercaderias = Calendar.getInstance();
		fechaCompraMercaderias.add(Calendar.DAY_OF_YEAR, +7);
		if(conIVA){
			//Con IVA
			double [] inputsCompraMercaderias = {20000, IVA, 60};
			CompraMercaderias compraMercaderias = new CompraMercaderias(fechaCompraMercaderias, inputsCompraMercaderias, enunciadoCuentas);
			todosEnunciados.add(compraMercaderias.enunciados);
			todasFechas.add(fechaCompraMercaderias);
		}else{
			//Sin IVA
			double [] inputsCompraMercaderiasSinIVA = {20000, 60};
			CompraMercaderiasSinIVA compraMercaderiasSinIVA = new CompraMercaderiasSinIVA(fechaCompraMercaderias, inputsCompraMercaderiasSinIVA, enunciadoCuentas);
			todosEnunciados.add(compraMercaderiasSinIVA.enunciados);
			todasFechas.add(fechaCompraMercaderias);
		}	
		
		//VENTA_MERCADERIAS
		Calendar fechaVentaMercaderias = Calendar.getInstance();
		fechaVentaMercaderias.add(Calendar.DAY_OF_YEAR, +8);
		if(conIVA){
			//Con IVA
			double [] inputsVentaMercaderias = {30000, IVA, 30};
			VentaMercaderias ventaMercaderias = new VentaMercaderias(fechaVentaMercaderias, inputsVentaMercaderias, enunciadoCuentas);
			todosEnunciados.add(ventaMercaderias.enunciados);
			todasFechas.add(fechaVentaMercaderias);
		}else{
			//Sin IVA
			double [] inputsVentaMercaderiasSinIVA = {30000, 30};
			VentaMercaderiasSinIVA ventaMercaderiasSinIVA = new VentaMercaderiasSinIVA(fechaVentaMercaderias, inputsVentaMercaderiasSinIVA, enunciadoCuentas);
			todosEnunciados.add(ventaMercaderiasSinIVA.enunciados);
			todasFechas.add(fechaVentaMercaderias);
		}
		
		//VENTA_PROYECTO
		Calendar fechaVentaProy = Calendar.getInstance();
		fechaVentaProy.add(Calendar.DAY_OF_YEAR, +9);
		if(conIVA){
			//Con IVA
			double [] inputsVentaProyecto = {200000, IVA, 30};
			VentaProyecto ventaProyecto = new VentaProyecto(fechaVentaProy, inputsVentaProyecto, enunciadoCuentas);
			todosEnunciados.add(ventaProyecto.enunciados);
			todasFechas.add(fechaVentaProy);
		}else{
			//Sin IVA	
			double [] inputsVentaProyectoSinIVA = {200000, 30};
			VentaProyectoSinIVA ventaProyectoSinIVA = new VentaProyectoSinIVA(fechaVentaProy, inputsVentaProyectoSinIVA, enunciadoCuentas);
			todosEnunciados.add(ventaProyectoSinIVA.enunciados);
			todasFechas.add(fechaVentaProy);
		}	
		
		//SUELDOS_EMPLEADOS
		Calendar fechaSueldoEmp = Calendar.getInstance();
		fechaSueldoEmp.add(Calendar.DAY_OF_YEAR, +10);
		if(conRetenciones){
			double [] inputsSueldoEmpleado = {numEmpleados, 10000, 3350, 10, 5};
			SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fechaSueldoEmp, inputsSueldoEmpleado, enunciadoCuentas);
			todosEnunciados.add(sueldoEmpleado.enunciados);
			todasFechas.add(fechaSueldoEmp);
		}else{
			double [] inputsSueldoEmpleadoSinR = {numEmpleados, 10000, 3350};
			SueldosEmpleadosSinRetenciones sueldoEmpleadoSinRetenciones = new SueldosEmpleadosSinRetenciones(fechaSueldoEmp, inputsSueldoEmpleadoSinR, enunciadoCuentas);
			todosEnunciados.add(sueldoEmpleadoSinRetenciones.enunciados);
			todasFechas.add(fechaSueldoEmp);
		}
		
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
		if(conRetenciones){
			double [] inputsIntereses = {300, 80};
			Interes intereses = new Interes(fechaIntereses, inputsIntereses, enunciadoCuentas);
			todosEnunciados.add(intereses.enunciados);
			todasFechas.add(fechaIntereses);
		}else{
			double [] inputsInteresesSinR = {300};
			InteresSinRetenciones interesesSinRetenciones = new InteresSinRetenciones(fechaIntereses, inputsInteresesSinR, enunciadoCuentas);
			todosEnunciados.add(interesesSinRetenciones.enunciados);
			todasFechas.add(fechaIntereses);
		}
		
		//NUEVOS_SOCIOS
		Calendar fechaNuevosSoc = Calendar.getInstance();
		fechaNuevosSoc.add(Calendar.DAY_OF_YEAR, +13);
		double [] inputsNuevoSocio = {60000, numAcciones};
		NuevoSocio nuevoSocio = new NuevoSocio(fechaNuevosSoc, inputsNuevoSocio, enunciadoCuentas);
		todosEnunciados.add(nuevoSocio.enunciados);
		todasFechas.add(fechaNuevosSoc);
		
		//INVENTARIO
		Calendar fechaInventario = Calendar.getInstance();
		fechaInventario.set(anoFin-2,11,31);
		double [] inputsInventario = {2000};
		Inventario inventario = new Inventario (fechaInventario, inputsInventario, enunciadoCuentas);
		todosEnunciados.add(inventario.enunciados);
		todasFechas.add(fechaInventario);
			
		//IVA
		if (conIVA){
			Calendar fechaIVA = Calendar.getInstance();
			fechaIVA.set(2016,11,31);
			IVA iva = new IVA (fechaIVA, null, enunciadoCuentas);
			todosEnunciados.add(iva.enunciados);
			todasFechas.add(fechaIVA);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados = new CuentaResultados (2016, impuestoSociedad);
		cuentaResultados.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria = new Tesoreria (2016);
		tesoreria.imprimeTesoreria();
		
		//BALANCE
		Balance balance = new Balance(2016);
		balance.imprimeBalance();
		
		
		
		//////////////////////////
		////	 2º AÑO 	/////	
		////////////////////////	
		
		//PAGO DEUDAS HACIENDA
		Calendar fechaDeudasHP = Calendar.getInstance();
		fechaDeudasHP.add(Calendar.YEAR, +1);
		if(conIVA && conRetenciones){
			PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fechaDeudasHP, null, enunciadoCuentas);
			todosEnunciados.add(deudasHacienda.enunciados);
			todasFechas.add(fechaDeudasHP);
		}if(conIVA==false && conRetenciones){
			PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA = new PagoDeudasHaciendaSinIVA(fechaDeudasHP, null, enunciadoCuentas);
			todosEnunciados.add(deudasHaciendaSinIVA.enunciados);
			todasFechas.add(fechaDeudasHP);	
		}if(conIVA && conRetenciones==false){
			PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP, null, enunciadoCuentas);
			todosEnunciados.add(deudasHaciendaSinRetenciones.enunciados);
			todasFechas.add(fechaDeudasHP);	
		}
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		if(conRetenciones){
			Calendar fechaDeudasSS = Calendar.getInstance();
			fechaDeudasSS.add(Calendar.YEAR, +1);
			PagoDeudasSS deudasSS = new PagoDeudasSS(fechaDeudasSS, null, enunciadoCuentas);
			todosEnunciados.add(deudasSS.enunciados); 
			todasFechas.add(fechaDeudasSS);
		}
		
		//DIVIDENDOS
		Calendar fechaDividendos = Calendar.getInstance();
		fechaDividendos.add(Calendar.YEAR, +1);
		if(conRetenciones){
			double [] inputsDividendos = {50, 20};
			dividendos = new Dividendos (fechaDividendos, inputsDividendos, enunciadoCuentas);
			todosEnunciados.add(dividendos.enunciados);
			todasFechas.add(fechaDividendos);
		}else{
			double [] inputsDividendosSinR = {50};
			dividendosSinReteneciones = new DividendosSinRetenciones (fechaDividendos, inputsDividendosSinR, enunciadoCuentas);
			todosEnunciados.add(dividendosSinReteneciones.enunciados);
			todasFechas.add(fechaDividendos);
		}
		
		//NO_DIVIDENDOS
		if(dividendos == null && dividendosSinReteneciones==null){
			NoDividendos noDividendos = new NoDividendos (Calendar.getInstance(), null, false);
		}
		
		//IVA
		if (conIVA){
			Calendar fechaIVA2 = Calendar.getInstance();
			fechaIVA2.set(2017,11,31);
			IVA iva2 = new IVA (fechaIVA2, null, enunciadoCuentas);
			todosEnunciados.add(iva2.enunciados);
			todasFechas.add(fechaIVA2);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados2 = new CuentaResultados (2017, impuestoSociedad);
		cuentaResultados2.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria2 = new Tesoreria (2017);
		tesoreria2.imprimeTesoreria();
		
		//BALANCE
		Balance balance2 = new Balance(2017);
		balance2.imprimeBalance();
			
		//////////////////////////
		////	 3º AÑO 	/////	
		////////////////////////	
		
		//PAGO DEUDAS HACIENDA
		Calendar fechaDeudasHP2 = Calendar.getInstance();
		fechaDeudasHP2.add(Calendar.YEAR, +2);
		if(conIVA && conRetenciones){
			PagoDeudasHacienda deudasHacienda2 = new PagoDeudasHacienda(fechaDeudasHP2, null, enunciadoCuentas);
			todosEnunciados.add(deudasHacienda2.enunciados);
			todasFechas.add(fechaDeudasHP2);
		}if(conIVA==false && conRetenciones){
			PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA2 = new PagoDeudasHaciendaSinIVA(fechaDeudasHP2, null, enunciadoCuentas);
			todosEnunciados.add(deudasHaciendaSinIVA2.enunciados);
			todasFechas.add(fechaDeudasHP2);	
		}if(conIVA && conRetenciones==false){
			PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones2 = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP2, null, enunciadoCuentas);
			todosEnunciados.add(deudasHaciendaSinRetenciones2.enunciados);
			todasFechas.add(fechaDeudasHP2);	
		}
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		if(conRetenciones){
			Calendar fechaDeudasSS2 = Calendar.getInstance();
			fechaDeudasSS2.add(Calendar.YEAR, +2);
			PagoDeudasSS deudasSS2 = new PagoDeudasSS(fechaDeudasSS2, null, enunciadoCuentas);
			todosEnunciados.add(deudasSS2.enunciados); 
			todasFechas.add(fechaDeudasSS2);
		}
		
		//DIVIDENDOS
		Calendar fechaDividendos2 = Calendar.getInstance();
		fechaDividendos2.add(Calendar.YEAR, +2);
		if(conRetenciones){
			double [] inputsDividendos2 = {50, 20};
			dividendos2 = new Dividendos (fechaDividendos2, inputsDividendos2, enunciadoCuentas);
			todosEnunciados.add(dividendos2.enunciados);
			todasFechas.add(fechaDividendos2);
		}else{
			double [] inputsDividendosSinR2 = {50};
			dividendosSinReteneciones2 = new DividendosSinRetenciones (fechaDividendos2, inputsDividendosSinR2, enunciadoCuentas);
			todosEnunciados.add(dividendosSinReteneciones2.enunciados);
			todasFechas.add(fechaDividendos2);
		}
		
		//NO_DIVIDENDOS
		if(dividendos2 == null && dividendosSinReteneciones2==null){
			NoDividendos noDividendos2 = new NoDividendos (Calendar.getInstance(), null, false);
		}
		
		//IVA
		if (conIVA){
			Calendar fechaIVA3 = Calendar.getInstance();
			fechaIVA3.set(2018,11,31);
			IVA iva2 = new IVA (fechaIVA3, null, enunciadoCuentas);
			todosEnunciados.add(iva2.enunciados);
			todasFechas.add(fechaIVA3);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados3 = new CuentaResultados (2018, impuestoSociedad);
		cuentaResultados3.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria3 = new Tesoreria (2018);
		tesoreria3.imprimeTesoreria();
		
		//BALANCE
		Balance balance3 = new Balance(2018);
		balance3.imprimeBalance();
		
		
		//Calculo el año de la fecha mas vieja
		anoInicio=2999;
		for (int i=0; i<todasFechas.size(); i++){
			if (todasFechas.get(i).get(Calendar.YEAR) < anoInicio){
				anoInicio=todasFechas.get(i).get(Calendar.YEAR);
			}
		}

		int ano = anoInicio;
		for(int i=0; i<=(anoFin-anoInicio); i++){
			
			
			
			ano = ano + 1;
		}	
		
		
		
		//ENUNCIADO
		imprimeEnunciados(todosEnunciados, anoInicio, anoFin);
	
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
			
			int anoTemp1=anoInicio;
			int anoTemp2=anoInicio;
			//int anoTemp2=0;
			Calendar fechaCierre = Calendar.getInstance();
			 
			for(int i=0; i<todosEnunciadosOrdenados.size(); i++){
				//Cierre
				if(todosEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)!=anoTemp2 && anoTemp2<=anoFin){
					fechaCierre.set(anoTemp2,11,31);
					if(enunciadoCuentas){
						if(conRetenciones){
							documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedad+"% del beneficio).\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
						}else{
							documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
						}
					}else{
						if(conRetenciones){
							documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedad+"% del beneficio).\n\n"));
						}else{
							documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\n\n"));
						}
					}
					anoTemp2=todosEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR);
					
				}
				//Nuevo año
				if(todosEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==anoTemp1 && anoTemp1<=anoFin){
					documento.add(new Paragraph("Se tiene la siguiente información vinculada con la empresa correspondiente al año "+anoTemp1+":\n\n", FontFactory.getFont("arial", 12, Font.BOLDITALIC, BaseColor.BLACK)));
					anoTemp1++;
				}
				
				//Imprime asientos
				if(todosEnunciadosOrdenados.get(i).fecha.before(fechaLimite)){
					fech= todosEnunciadosOrdenados.get(i).getFecha();
					enun = todosEnunciadosOrdenados.get(i).getEnunciado();
					documento.add(new Paragraph(formateador.format(fech.getTime())+ " " + enun));
					documento.add(new Paragraph("\n"));	
				}
			}
			
			//Imprime enunciado
			Paragraph parrafo = new Paragraph("\n\nSe pide: \n"
					 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
					 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
					 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. ");
			if(conRetenciones && conIVA){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedad+ "% del beneficio y el IVA es " +IVA+ "%.");
			}if(conRetenciones==false && conIVA){
				parrafo.add("El IVA es " +IVA+ "%.\n");	 
			}
			if(conRetenciones && conIVA==false){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedad+ "% del beneficio.");				 
			}
 			parrafo.add("\n  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
					 + "que se cumple la ecuación que las liga. \n"
					 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.\n"
					 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.\n"
					 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n"
					 + "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
					 + "acción emitida el " +formateador.format(fechaLimite.getTime())
					 + ". ¿Cuál sería el valor de mercado de las acciones? \n");
			documento.add(parrafo);
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}

}
