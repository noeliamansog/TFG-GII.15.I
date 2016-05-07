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

public class Tesoreria extends Asiento{
	static ArrayList<Anotacion> cobros = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pagos = new ArrayList<Anotacion>();
	
	public Tesoreria(){

		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);
		  
		  if (!(cuenta.debe.isEmpty())){
			  for(int i=0; i<cuenta.debe.size(); i++){
				  cobros.add(cuenta.debe.get(i));
			  }
		  }
		  if (!(cuenta.haber.isEmpty())){
			  for(int i=0; i<cuenta.haber.size(); i++){
				  pagos.add(cuenta.haber.get(i));			  
			  }
		  }
		}	
	}
	
	public void imprimeTesoreria(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		/* IMPRIME POR PANTALLA 
		System.out.println("\n\n TESORERÍA: \n");
		System.out.println("COBROS:");
		if (!(cobros.isEmpty())){
			for(int i=0; i<cobros.size(); i++){
				Calendar fecha= cobros.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ cobros.get(i).nombre +" \t\t\t\t"+ cobros.get(i).cantidad);
			}
		}
		System.out.println("\n");
		System.out.println("PAGOS:");
		if (!(pagos.isEmpty())){
			for(int i=0; i<pagos.size(); i++){
				Calendar fecha= pagos.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ pagos.get(i).nombre +" \t\t\t\t"+ pagos.get(i).cantidad);
		  }
		}
		*/
	
	/*GENERA PDF */
	Collections.sort(cobros);
	Collections.sort(pagos);
	
	int tamaño = 0;
	if(cobros.size()>=pagos.size()){
		tamaño=cobros.size();
	}else{
		tamaño=pagos.size();
	}
	
	Document documento = new Document();
	
	try{			
        PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Tesorería.pdf"));
        
        documento.open();
        documento.addTitle("Tesorería"); 
        documento.addAuthor("Noelia Manso García"); 
        
        
        //TESORERÍA
        PdfPTable tesoreria = new PdfPTable(1);
        
        PdfPCell celda =new PdfPCell (new Paragraph("TESORERÍA", FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding (12.0f);
        celda.setBackgroundColor(BaseColor.DARK_GRAY);
        
        tesoreria.addCell(celda);
        
        //COBROS Y PAGOS
        PdfPTable cobrosPagos = new PdfPTable(6);
        
        PdfPCell celda2 =new PdfPCell (new Paragraph("COBROS", FontFactory.getFont("arial",12,Font.BOLD, BaseColor.BLACK)));
        celda2.setColspan(3);
        celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding (10.0f);
        celda2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cobrosPagos.addCell(celda2);
        
        PdfPCell celda3 =new PdfPCell (new Paragraph("PAGOS", FontFactory.getFont("arial",12,Font.BOLD, BaseColor.BLACK)));
        celda3.setColspan(3);
        celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding (10.0f);
        celda3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cobrosPagos.addCell(celda3);
        
        int contador=0;
        int contCobros=0;
        int contPagos=0;
        for(int i=0; i<tamaño; i++){
    	   //GASTOS
    	   if(cobros.size()>contCobros){
    		   Calendar fechaCobros= cobros.get(contador).fecha;
    		   cobrosPagos.addCell(formateador.format(fechaCobros.getTime()));
    		   cobrosPagos.addCell(cobros.get(contador).nombre);
    		   cobrosPagos.addCell(cobros.get(contador).cantidad+"€");
    		   

    		   contCobros++;
    	   }else{
    		   cobrosPagos.addCell(" ");
    		   cobrosPagos.addCell(" ");
    		   cobrosPagos.addCell(" "); 
    	   }

    	   //INGRESOS
    	   if(pagos.size()>contPagos){
    		   Calendar fechaPagos= pagos.get(contador).fecha;
    		   cobrosPagos.addCell(formateador.format(fechaPagos.getTime()));
    		   cobrosPagos.addCell(pagos.get(contador).nombre);
    		   cobrosPagos.addCell(pagos.get(contador).cantidad+"€");
    		   contPagos++;
    	   }else{
    		   cobrosPagos.addCell(" ");
    		   cobrosPagos.addCell(" ");
    		   cobrosPagos.addCell(" "); 
    	   }
    	  contador++;
    	   
       }
       //CELDA RESULTADO TOTAL
      	PdfPTable tablaResultado = new PdfPTable(3);
      	PdfPCell total = new PdfPCell (new Paragraph("SALDO TOTAL TESORERÍA:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
      	total.setColspan(2);
      	total.setPadding (10.0f);
      	total.setBackgroundColor(BaseColor.YELLOW);
      	PdfPCell calculoTotal = new PdfPCell (new Paragraph("Calcular saldo", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
      	calculoTotal.setPadding (10.0f);
      	calculoTotal.setBackgroundColor(BaseColor.YELLOW);
      	tablaResultado.addCell(total);
      	tablaResultado.addCell(calculoTotal);
           
        // Agregamos la tabla al documento    
      	documento.add(tesoreria);
        documento.add(cobrosPagos);
        documento.add(tablaResultado);
        
        documento.close();
        
    }catch(Exception e){
        System.err.println("Ocurrio un error al crear el archivo");
        System.exit(-1);
    }
}

}
