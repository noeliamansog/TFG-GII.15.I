package es.ubu.inf.tfg.otrasCosas;

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

public class CuentaResultados extends Asiento{
	static ArrayList<Anotacion> gastos = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> ingresos = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> gastosFinancieros = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> ingresosFinancieros = new ArrayList<Anotacion>();
	static double valorGastos;
	static double valorIngresos;
	static double valorGastosFinancieros;
	static double valorIngresosFinancieros;
	static double excedente;
	static double impuestos;
	static double resultadoTotal;
	private Calendar fechaDesde;
	private Calendar fechaHasta;
	
	
	public CuentaResultados(Calendar fechaEnunciado, Calendar fechaDesde, Calendar fechaHasta, double impuestoSociedad){
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		valorGastos=0;
		valorIngresos=0;
		valorGastosFinancieros = 0;
		valorIngresosFinancieros = 0;
		excedente=0;
		impuestos=0;
		resultadoTotal=0;
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);

		  if(cuenta.prioridad==0){
			  //Gastos
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  if (cuenta.debe.get(i).fecha.before(fechaHasta) && cuenta.debe.get(i).fecha.after(fechaDesde)){
						  if (cuenta.codigo==769 || cuenta.codigo==662){
							  gastosFinancieros.add(cuenta.debe.get(i));
							  valorGastosFinancieros += cuenta.debe.get(i).cantidad;
						  }else{
							  gastos.add(cuenta.debe.get(i));
							  valorGastos += cuenta.debe.get(i).cantidad;
						  }
					  }
				  }
			  }
			  //Ingresos
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  if (cuenta.haber.get(i).fecha.before(fechaHasta) && cuenta.haber.get(i).fecha.after(fechaDesde)){
						  if (cuenta.codigo==769 || cuenta.codigo==662){
							  ingresosFinancieros.add(cuenta.haber.get(i));
							  valorIngresosFinancieros += cuenta.haber.get(i).cantidad;
						  }else{
							  ingresos.add(cuenta.haber.get(i));
							  valorIngresos += cuenta.haber.get(i).cantidad;
						  }
					  }
				  }
			  }
		  }
		}
		//Si el expediente <0
		excedente = (valorIngresos - valorGastos)+(valorIngresosFinancieros-valorGastosFinancieros);
	    if(excedente>0){
	    	impuestos =(excedente*impuestoSociedad)/100;
	    }
	       	
	    if (impuestos > 0){
	       	dameCuenta(630).añadirDebe(new Anotacion(fechaHasta, "Impuestos", impuestos, damePrioridad(630)));
	       	Anotacion anotacion = new Anotacion(fechaHasta, "Impuestos", impuestos, damePrioridad(4752));
	       	dameCuenta(630).añadirDebe(anotacion);
	       	int indice = dameCuenta(630).debe.indexOf(anotacion);
	       	gastos.add(dameCuenta(630).debe.get(indice));
	       	
	       	dameCuenta(630).añadirHaber(new Anotacion(fechaHasta, "Impuestos", impuestos, damePrioridad(630)));
	       	anotacion = new Anotacion(fechaHasta, "Impuestos", impuestos, damePrioridad(4752));
	       	dameCuenta(630).añadirHaber(anotacion);
	       	indice = dameCuenta(630).haber.indexOf(anotacion);
	       	ingresos.add(dameCuenta(630).haber.get(indice));
	       	
	       	       	
	        anotacion = new Anotacion(fechaEnunciado, "Impuestos", impuestos, damePrioridad(4752));
	       	dameCuenta(4752).añadirHaber(anotacion);
	       	indice = dameCuenta(4752).haber.indexOf(anotacion);
	       	ingresos.add(dameCuenta(4752).haber.get(indice));	
	   }
	    
	    resultadoTotal = excedente-impuestos;
	    dameCuenta(129).añadirHaber(new Anotacion (fechaDesde, "Resultado ejercicio", resultadoTotal, damePrioridad(129)));
		 
	}
	
	public void imprimeCuentaResultados(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");	
		
		Collections.sort(gastos);
		Collections.sort(ingresos);
		
		Document documento = new Document();
		
		try{			
            PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/CuentaResultados.pdf"));
            
            documento.open();
            documento.addTitle("Cuenta de Resultados"); 
            documento.addAuthor("Noelia Manso García"); 
                  
            
            //CUENTA RESULTADOS
            PdfPTable cuentaResultados = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("CUENTA RESULTADOS  \n desde "+formateador.format(fechaDesde.getTime())+" hasta "+formateador.format(fechaHasta.getTime()), FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
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
            PdfPCell resulActividad = new PdfPCell (new Paragraph("EXCEDENTE DE LA ACTIVIDAD:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulActividad.setColspan(2);
            resulActividad.setPadding (10.0f);
            resulActividad.setBackgroundColor(BaseColor.CYAN);
           	PdfPCell excedenteAct = new PdfPCell (new Paragraph(Math.round(valorIngresos - valorGastos)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	excedenteAct.setColspan(2);
           	excedenteAct.setPadding (10.0f);
           	excedenteAct.setBackgroundColor(BaseColor.CYAN);
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
            resulFinanciero.setBackgroundColor(BaseColor.CYAN);
           	PdfPCell excedenteFinan = new PdfPCell (new Paragraph(Math.round(valorIngresosFinancieros-valorGastosFinancieros)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	excedenteFinan.setColspan(2);
           	excedenteFinan.setPadding (10.0f);
           	excedenteFinan.setBackgroundColor(BaseColor.CYAN);
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
               
            // Agregamos la tabla al documento     
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