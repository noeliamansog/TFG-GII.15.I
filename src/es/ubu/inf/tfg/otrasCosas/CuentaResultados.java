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
	ArrayList<Anotacion> gastos = new ArrayList<Anotacion>();
	ArrayList<Anotacion> ingresos = new ArrayList<Anotacion>();
	
	public CuentaResultados(){
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);

		  if(cuenta.prioridad==0){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  gastos.add(cuenta.debe.get(i));
				  }
			  }
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  ingresos.add(cuenta.haber.get(i));
					  
				  }
			  }
		  }
		}
	}
	public void imprimeCuentaResultados(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		/* IMPRIME POR PANTALLA 
		 
		System.out.println("\n\n CUENTA DE RESULTADOS: \n");
		System.out.println("GASTOS:");
		if (!(gastos.isEmpty())){
			for(int i=0; i<gastos.size(); i++){
				Calendar fecha= gastos.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ gastos.get(i).nombre +" \t\t\t\t"+ gastos.get(i).cantidad);
			}
		}
		System.out.println("\n");
		System.out.println("INGRESOS:");
		if (!(ingresos.isEmpty())){
			for(int i=0; i<ingresos.size(); i++){
				Calendar fecha= ingresos.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ ingresos.get(i).nombre +" \t\t\t\t"+ ingresos.get(i).cantidad);
		  }
		}
		*/
		
		
		
		/*GENERA PDF */
		Collections.sort(gastos);
		Collections.sort(ingresos);
		
		int tamaño = 0;
		if(gastos.size()>=ingresos.size()){
			tamaño=gastos.size();
		}else{
			tamaño=ingresos.size();
		}
		
		Document documento = new Document();
		
		try{			
            PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/CuentaResultados.pdf"));
            
            documento.open();
            documento.addTitle("Cuenta de Resultados"); 
            documento.addAuthor("Noelia Manso García"); 
            
            PdfPTable table = new PdfPTable(6);
            
            PdfPCell celda =new PdfPCell (new Paragraph("Cuenta de resultados", FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            celda.setColspan(6);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (12.0f);
            celda.setBackgroundColor(BaseColor.GRAY);
            
            table.addCell(celda);
            
            PdfPCell celda2 =new PdfPCell (new Paragraph("Gastos", FontFactory.getFont("arial",13,Font.BOLD, BaseColor.BLACK)));
            celda2.setColspan(3);
            celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (10.0f);
            celda2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda2);
            
            PdfPCell celda3 =new PdfPCell (new Paragraph("Ingresos", FontFactory.getFont("arial",13,Font.BOLD, BaseColor.BLACK)));
            celda3.setColspan(3);
            celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (10.0f);
            celda3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda3);
            
            
           int contador=0;
           int contGastos=0;
           int contIngresos=0;
           for(int i=0; i<tamaño; i++){
        	   //GASTOS
        	   if(gastos.size()>contGastos){
        		   Calendar fechaGastos= gastos.get(contador).fecha;
        		   table.addCell(formateador.format(fechaGastos.getTime()));
        		   table.addCell(gastos.get(contador).nombre);
        		   table.addCell(gastos.get(contador).cantidad+"€");
        		   
 
        		   contGastos++;
        	   }else{
        		   table.addCell(" ");
        		   table.addCell(" ");
        		   table.addCell(" "); 
        	   }

        	   //INGRESOS
        	   if(ingresos.size()>contIngresos){
        		   Calendar fechaIngresos= ingresos.get(contador).fecha;
        		   table.addCell(formateador.format(fechaIngresos.getTime()));
        		   table.addCell(ingresos.get(contador).nombre);
        		   table.addCell(ingresos.get(contador).cantidad+"€");
        		   contIngresos++;
        	   }else{
        		   table.addCell(" ");
        		   table.addCell(" ");
        		   table.addCell(" "); 
        	   }
        	  contador++;
        	   
           }
               
            // Agregamos la tabla al documento            
            documento.add(table);
            
            documento.close();
            
        }catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
        }
	}
}

