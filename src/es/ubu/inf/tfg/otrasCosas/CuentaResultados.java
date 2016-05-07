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
	static double valorGastos;
	static double valorIngresos;
	
	public CuentaResultados(Calendar fecha){
		this.fecha = fecha;
		valorGastos=0;
		valorIngresos=0;
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);

		  if(cuenta.prioridad==0){
			  //Gastos
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  if (cuenta.debe.get(i).fecha.before(fecha)){
						  gastos.add(cuenta.debe.get(i));
						  valorGastos += cuenta.debe.get(i).cantidad;
					  }
				  }
			  }
			  //Ingresos
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  if (cuenta.haber.get(i).fecha.before(fecha)){
						  ingresos.add(cuenta.haber.get(i));
						  valorIngresos += cuenta.haber.get(i).cantidad;
					  }
				  }
			  }
		  }
		}
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
            
            PdfPCell celda =new PdfPCell (new Paragraph("CUENTA RESULTADOS hasta "+formateador.format(fecha.getTime()), FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
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
        	   ingresosGastosActividad.addCell(gastos.get(i).cantidad+"€");
            } 
            
            //INGRESOS
            PdfPCell celdaIngresos = new PdfPCell (new Paragraph("INGRESOS DE LA ACTIVIDAD", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaIngresos.setColspan(3);
            celdaIngresos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIngresos.setPadding (10.0f);
            celdaIngresos.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosActividad.addCell(celdaIngresos);
            
            for(int i=0; i<gastos.size(); i++){
        	   Calendar fechaGastos= gastos.get(i).fecha;
        	   ingresosGastosActividad.addCell(formateador.format(fechaGastos.getTime()));
        	   ingresosGastosActividad.addCell(gastos.get(i).nombre);
        	   ingresosGastosActividad.addCell(gastos.get(i).cantidad+"€");
            } 
            
            //CELDA TOTAL INGRESOS Y GASTOS DE LA ACTIVIDAD
            PdfPCell resulActividad = new PdfPCell (new Paragraph("EXCEDENTE DE LA ACTIVIDAD:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulActividad.setColspan(2);
            resulActividad.setPadding (10.0f);
            resulActividad.setBackgroundColor(BaseColor.CYAN);
           	PdfPCell excedenteAct = new PdfPCell (new Paragraph(valorGastos-valorIngresos+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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
            
            for(int i=0; i<1; i++){
        	   ingresosGastosFinancieros.addCell("Fecha");
        	   ingresosGastosFinancieros.addCell("Nombre");
        	   ingresosGastosFinancieros.addCell("Cantidad");
            } 
            
            //INGRESOS
            PdfPCell celdaIngresosF = new PdfPCell (new Paragraph("INGRESOS FINANCIEROS", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaIngresosF.setColspan(3);
            celdaIngresosF.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaIngresosF.setPadding (10.0f);
            celdaIngresosF.setBackgroundColor(BaseColor.LIGHT_GRAY);
            ingresosGastosFinancieros.addCell(celdaIngresosF);
            
            for(int i=0; i<1; i++){
        	   ingresosGastosFinancieros.addCell("Fecha");
        	   ingresosGastosFinancieros.addCell("Nombre");
        	   ingresosGastosFinancieros.addCell("Cantidad");
            } 
            
            //CELDA TOTAL INGRESOS Y GASTOS DE LA ACTIVIDAD
            PdfPCell resulFinanciero = new PdfPCell (new Paragraph("EXCEDENTE DE LAS OPERACIONES FINANCIERAS:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulFinanciero.setColspan(2);
            resulFinanciero.setPadding (10.0f);
            resulFinanciero.setBackgroundColor(BaseColor.CYAN);
           	PdfPCell excedenteFinan = new PdfPCell (new Paragraph("Calcular excedente:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	excedenteFinan.setColspan(2);
           	excedenteFinan.setPadding (10.0f);
           	excedenteFinan.setBackgroundColor(BaseColor.CYAN);
           	ingresosGastosFinancieros.addCell(resulFinanciero);
           	ingresosGastosFinancieros.addCell(excedenteFinan);
           	
           	
            //RESULTADO TOTAL
           	PdfPTable resultado = new PdfPTable(3);
            PdfPCell total = new PdfPCell (new Paragraph("RESULTADO TOTAL:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            total.setColspan(2);
            total.setPadding (10.0f);
            total.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell calculoTotal = new PdfPCell (new Paragraph("Calculo total:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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

