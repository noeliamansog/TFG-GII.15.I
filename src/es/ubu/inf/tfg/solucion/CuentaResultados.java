package es.ubu.inf.tfg.solucion;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Cuenta;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase CuentaResultados que implementa la solución de la cuenta de perdidas y ganancias del supuesto contable.
 * 
 * @author Noelia Manso García
 */
public class CuentaResultados extends Asiento{
	/**
	 * Año del que se desea generar el balance.
	 */
	private int ano;
	
	/**
	 * Lista de anotaciones de gastos.
	 */
	private static ArrayList<Anotacion> gastos;
	/**
	 * Lista de anotaciones de ingresos.
	 */
	private static ArrayList<Anotacion> ingresos;
	/**
	 * Lista de anotaciones de gastos financieros.
	 */
	private static ArrayList<Anotacion> gastosFinancieros;
	/**
	 * Lista de anotaciones de ingresos financieros.
	 */
	private static ArrayList<Anotacion> ingresosFinancieros;

	
	/**
	 * Valor de todos los gastos.
	 */
	private static double valorGastos;
	/**
	 * Valor de todos los ingresos.
	 */
	private static double valorIngresos;
	/**
	 * Valor de todos los gastos financieros.
	 */
	private static double valorGastosFinancieros;
	/**
	 * Valor de todos los ingresos financieros.
	 */
	private static double valorIngresosFinancieros;
	/**
	 * Valor del excedente de actividad.
	 */
	public static double excedenteActividad;

	/**
	 * Valor del excedente.
	 */
	private static double excedente;
	/**
	 * Valor de los impuestos.
	 */
	private static double impuestos;
	/**
	 * valor del resultado total. 
	 */
	public static double resultadoTotal;
	/**
	 * Valor de los ingresos financieros de la cuenta 769.
	 */
	public static double ingresosF;
	
	
	/**
	 * Contructor de la clase CuentaResultados en el que se genera la cuenta de perdidas y ganancias
	 *  del supuesto contable  en el año pasado como parámetro.
	 * @param ano Año del que se desea generar la cuenta de resultados.
	 */
	public CuentaResultados(int ano){
		this.ano=ano;
		
		gastos = new ArrayList<Anotacion>();
		ingresos = new ArrayList<Anotacion>();
		gastosFinancieros = new ArrayList<Anotacion>();
		ingresosFinancieros = new ArrayList<Anotacion>();
		
		valorGastos=0;
		valorIngresos=0;
		valorGastosFinancieros = 0;
		valorIngresosFinancieros = 0;
		excedenteActividad = 0;
		excedente=0;
		impuestos=0;
		resultadoTotal=0;
		ingresosF=0;
		
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);
  
		  if(cuenta.prioridad==0){
			 //GASTOS
			 if (tieneAnotaciones(cuenta, ano, true)){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  if (cuenta.debe.get(i).fecha.get(Calendar.YEAR)==ano){
						  if (cuenta.codigo==769 || cuenta.codigo==662){
							  gastosFinancieros.add(cuenta.debe.get(i));
							  valorGastosFinancieros += cuenta.debe.get(i).cantidad;
						  }else{
							  gastos.add(cuenta.debe.get(i));
							  valorGastos += cuenta.debe.get(i).cantidad;
						  }	
						  //Cierro la cuenta
						  dameCuenta(cuenta.codigo).añadirHaber(cuenta.debe.get(i));
					  }
				  }
				 
			 }
			 //INGRESOS
			 else{
				 if (tieneAnotaciones(cuenta, ano, false)){
					 for(int i=0; i<cuenta.haber.size(); i++){
						 if (cuenta.haber.get(i).fecha.get(Calendar.YEAR)==ano){
							 if (cuenta.codigo==769 || cuenta.codigo==662){
								 ingresosFinancieros.add(cuenta.haber.get(i));
								 valorIngresosFinancieros += cuenta.haber.get(i).cantidad;
								 if(cuenta.codigo==769){
									 ingresosF += cuenta.haber.get(i).cantidad;
								 }
							 }else{
								 ingresos.add(cuenta.haber.get(i));
								 valorIngresos += cuenta.haber.get(i).cantidad;
							 }			  
							 //Cierro la cuenta
							 dameCuenta(cuenta.codigo).añadirDebe(cuenta.haber.get(i));
						 }
					 }
				 }
			 }
		  }
		}
		
		Calendar fech = Calendar.getInstance();
		fech.set(ano,11,31);
		
		//Calculo expediente y los impuestos
		excedente = (valorIngresos - valorGastos)+(valorIngresosFinancieros-valorGastosFinancieros);
	    if(excedente>0){
	    	impuestos =(excedente*Main.impuestoSociedades)/100;
	    }
	       	
	    if (impuestos > 0){
	       	dameCuenta(630).añadirDebe(new Anotacion(fech, "Impuestos", impuestos, damePrioridad(630)));       	
	       	dameCuenta(630).añadirHaber(new Anotacion(fech, "Impuestos", impuestos, damePrioridad(630)));
	       	dameCuenta(4752).añadirHaber(new Anotacion(fech, "Impuestos", impuestos, damePrioridad(4752)));
	   }
	    
	    resultadoTotal = excedente-impuestos;
	    
	    dameCuenta(129).añadirHaber(new Anotacion (fech, "Resultado ejercicio", resultadoTotal, damePrioridad(129)));
	    dameCuenta(129).añadirDebe(new Anotacion (fech, "Resultado ejercicio", resultadoTotal, damePrioridad(129)));
	    dameCuenta(12).añadirHaber(new Anotacion (fech, "Resultado ejercicio", resultadoTotal, damePrioridad(129)));
	}
	
	/**
	 * Función que comprueba si una cuenta, en un año tiene anotaciones en la lista
	 *  de debe o haber según el parámetro pasado.
	 *  
	 * @param cuenta Cuenta de la que queremos saber si tiene anotaciones.
	 * @param anno Año en el que queremos comprobar si hay anotaciones.
	 * @param debe Boolean que nos indica si debemos comprobar en la lista debe o en la lista haber.
	 * @return Devuelve true si hay alguna anotación en esa cuenta, ese año y en esa lista. Devuelve false en 
	 * 		   caso contrario.
	 */
	public boolean tieneAnotaciones(Cuenta cuenta, int anno, boolean debe){
		boolean tieneCuentas=false;	
		if (debe){
			if (!(cuenta.debe.isEmpty())){
				for(int i=0; i<cuenta.debe.size(); i++){
					if (cuenta.debe.get(i).fecha.get(Calendar.YEAR)==anno){
						tieneCuentas=true;
					}
				}
			}
		}else{
			if (!(cuenta.haber.isEmpty())){
				for(int i=0; i<cuenta.haber.size(); i++){
					if (cuenta.haber.get(i).fecha.get(Calendar.YEAR)==anno){
						tieneCuentas=true;
					}
				}
			}
		}
		
		return tieneCuentas;
	}
	
	/**
	 * Función que imprime la cuenta de perdidas y ganancias en un documento PDF.
	 */
	public void imprimeCuentaResultados(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");	
		
		Collections.sort(gastos);
		Collections.sort(ingresos);
		
		Document documento = new Document();
		
		try{	
			File directorio = new File(Main.direccionRuta+"/"+ano);
			if(!directorio.exists()){
				directorio.mkdir();
			}	
			PdfWriter.getInstance(documento, new FileOutputStream(directorio+"/CuentaResultados"+ano+".pdf"));
			
            documento.open();
            documento.addTitle("Cuenta de Resultados"); 
            documento.addAuthor("Noelia Manso García"); 
                  
            
            //CUENTA RESULTADOS
            PdfPTable cuentaResultados = new PdfPTable(1);
			PdfPCell celda =new PdfPCell (new Paragraph("CUENTA RESULTADOS \n del año "+ano+"", FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (12.0f);
            celda.setBackgroundColor(BaseColor.DARK_GRAY);  
            cuentaResultados.addCell(celda);
            
            //INGRESOS Y GASTOS DE LA ACTIVIDAD
            PdfPTable ingresosGastosActividad = new PdfPTable(3);
    
            PdfPCell celdaIG =new PdfPCell (new Paragraph("INGRESOS Y GASTOS DE LA ACTIVIDAD", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaIG.setColspan(3);
            celdaIG.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIG.setPadding (10.0f);
            celdaIG.setBackgroundColor(BaseColor.GRAY);
            ingresosGastosActividad.addCell(celdaIG);
            
            //GASTOS
            PdfPCell celdaGastos = new PdfPCell (new Paragraph("GASTOS DE LA ACTIVIDAD", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaGastos.setColspan(3);
            celdaGastos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaGastos.setPadding (10.0f);
            celdaGastos.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosActividad.addCell(celdaGastos);
            
            for(int i=0; i<gastos.size(); i++){
        	   Calendar fechaGastos= gastos.get(i).fecha;
        	   ingresosGastosActividad.addCell(formateador.format(fechaGastos.getTime()));
        	   ingresosGastosActividad.addCell(gastos.get(i).nombre);
        	   ingresosGastosActividad.addCell(Math.round(gastos.get(i).cantidad)*100/100+"€");
            }
            
            //INGRESOS
            PdfPCell celdaIngresos = new PdfPCell (new Paragraph("INGRESOS DE LA ACTIVIDAD", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaIngresos.setColspan(3);
            celdaIngresos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIngresos.setPadding (10.0f);
            celdaIngresos.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosActividad.addCell(celdaIngresos);
            
            for(int i=0; i<ingresos.size(); i++){
        	   Calendar fechaIngresos = ingresos.get(i).fecha;
        	   ingresosGastosActividad.addCell(formateador.format(fechaIngresos.getTime()));
        	   ingresosGastosActividad.addCell(ingresos.get(i).nombre);
        	   ingresosGastosActividad.addCell(Math.round(ingresos.get(i).cantidad)*100/100+"€");
            } 
            
            //TOTAL INGRESOS Y GASTOS DE LA ACTIVIDAD
            excedenteActividad = valorIngresos - valorGastos;
            PdfPCell resulActividad = new PdfPCell (new Paragraph("EXCEDENTE DE LA ACTIVIDAD:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulActividad.setColspan(2);
            resulActividad.setPadding (10.0f);
            resulActividad.setBackgroundColor(BaseColor.PINK);
           	PdfPCell excedenteAct = new PdfPCell (new Paragraph(Math.round(excedenteActividad)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	excedenteAct.setColspan(2);
           	excedenteAct.setPadding (10.0f);
           	excedenteAct.setBackgroundColor(BaseColor.PINK);
           	ingresosGastosActividad.addCell(resulActividad);
           	ingresosGastosActividad.addCell(excedenteAct);
                    
            
            
           	//INGRESOS Y GASTOS FINANCIEROS
            PdfPTable ingresosGastosFinancieros = new PdfPTable(3);
    
            PdfPCell celdaIGF =new PdfPCell (new Paragraph("INGRESOS Y GASTOS FINANCIEROS", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaIGF.setColspan(3);
            celdaIGF.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIGF.setPadding (10.0f);
            celdaIGF.setBackgroundColor(BaseColor.GRAY);
            ingresosGastosFinancieros.addCell(celdaIGF);
            
            //GASTOS
            PdfPCell celdaGastosF = new PdfPCell (new Paragraph("GASTOS FINANCIEROS", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaGastosF.setColspan(3);
            celdaGastosF.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaGastosF.setPadding (10.0f);
            celdaGastosF.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosFinancieros.addCell(celdaGastosF);
            
            for(int i=0; i<gastosFinancieros.size(); i++){
        	   Calendar fechaGastosFinancieros= gastosFinancieros.get(i).fecha;
        	   ingresosGastosFinancieros.addCell(formateador.format(fechaGastosFinancieros.getTime()));
        	   ingresosGastosFinancieros.addCell(gastosFinancieros.get(i).nombre);
        	   ingresosGastosFinancieros.addCell(Math.round(gastosFinancieros.get(i).cantidad)*100/100+"€");
            } 
            
            //INGRESOS
            PdfPCell celdaIngresosF = new PdfPCell (new Paragraph("INGRESOS FINANCIEROS", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaIngresosF.setColspan(3);
            celdaIngresosF.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIngresosF.setPadding (10.0f);
            celdaIngresosF.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosFinancieros.addCell(celdaIngresosF);
            
            for(int i=0; i<ingresosFinancieros.size(); i++){
        	   Calendar fechaIngresosFinancieros= ingresosFinancieros.get(i).fecha;
        	   ingresosGastosFinancieros.addCell(formateador.format(fechaIngresosFinancieros.getTime()));
        	   ingresosGastosFinancieros.addCell(ingresosFinancieros.get(i).nombre);
        	   ingresosGastosFinancieros.addCell(Math.round(ingresosFinancieros.get(i).cantidad)*100/100+"€");
            } 
            
            //TOTAL INGRESOS Y GASTOS DE LA ACTIVIDAD
            PdfPCell resulFinanciero = new PdfPCell (new Paragraph("EXCEDENTE DE LAS OPERACIONES FINANCIERAS:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulFinanciero.setColspan(2);
            resulFinanciero.setPadding (10.0f);
            resulFinanciero.setBackgroundColor(BaseColor.PINK);
           	PdfPCell excedenteFinan = new PdfPCell (new Paragraph(Math.round(valorIngresosFinancieros-valorGastosFinancieros)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	excedenteFinan.setColspan(2);
           	excedenteFinan.setPadding (10.0f);
           	excedenteFinan.setBackgroundColor(BaseColor.PINK);
           	ingresosGastosFinancieros.addCell(resulFinanciero);
           	ingresosGastosFinancieros.addCell(excedenteFinan);
           	
           	
           	//EXCEDENTE ANTES DE IMPUESTOS
           	PdfPTable resultado = new PdfPTable(3);      	
            PdfPCell excedenteImpuestos = new PdfPCell (new Paragraph("EXCEDENTE ANTES DE IMPUESTOS:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            excedenteImpuestos.setColspan(2);
            excedenteImpuestos.setPadding (10.0f);
            excedenteImpuestos.setBackgroundColor(BaseColor.GRAY);
           	PdfPCell calculoExcedente = new PdfPCell (new Paragraph(Math.round(excedente)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	calculoExcedente.setColspan(2);
           	calculoExcedente.setPadding (10.0f);
           	calculoExcedente.setBackgroundColor(BaseColor.LIGHT_GRAY);
           	resultado.addCell(excedenteImpuestos);
           	resultado.addCell(calculoExcedente);
           	
           	
           	//IMPUESTOS SOBRE BENEFICIOS 
            PdfPCell impuestosBeneficios = new PdfPCell (new Paragraph("IMPUESTOS SOBRE BENEFICIOS:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            impuestosBeneficios.setColspan(2);
            impuestosBeneficios.setPadding (10.0f);
            impuestosBeneficios.setBackgroundColor(BaseColor.GRAY);
           	PdfPCell calculoImpuestos = new PdfPCell (new Paragraph(Math.round(impuestos)*100/100 +"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	calculoImpuestos.setColspan(2);
           	calculoImpuestos.setPadding (10.0f);
           	calculoImpuestos.setBackgroundColor(BaseColor.LIGHT_GRAY);
           	resultado.addCell(impuestosBeneficios);
           	resultado.addCell(calculoImpuestos);       	
           	
           	
            //RESULTADO TOTAL
            PdfPCell total = new PdfPCell (new Paragraph("RESULTADO TOTAL:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            total.setColspan(2);
            total.setPadding (10.0f);
            total.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell calculoTotal = new PdfPCell (new Paragraph(Math.round(resultadoTotal)*100/100 + "€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	calculoTotal.setPadding (10.0f);
           	calculoTotal.setBackgroundColor(BaseColor.YELLOW);
           	resultado.addCell(total);
           	resultado.addCell(calculoTotal);
               
            // Agregamos las tablas al documento     
           	documento.add(cuentaResultados);
            documento.add(ingresosGastosActividad);
            documento.add(ingresosGastosFinancieros);
            documento.add(resultado);
            
            documento.close();
            
        }catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
        }
	}
}