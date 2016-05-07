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

public class Balance extends Asiento{
	static ArrayList<Anotacion> activo = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pnPasivo = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> activoNoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> activoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> patrimonioNeto = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pasivoNoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pasivoCorriente = new ArrayList<Anotacion>();
	
	
	public Balance(){
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);
		  
		  //Activo No Corriente
		  if(cuenta.prioridad>=1 && cuenta.prioridad<=30){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  activoNoCorriente.add(cuenta.debe.get(i));
				  }
			  }
			  /*if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  activoNoCorriente.add(cuenta.haber.get(i));
				  }
			  }*/
		  }
		  
		  //Activo Corriente
		  if(cuenta.prioridad>=31 && cuenta.prioridad<=60){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  activoCorriente.add(cuenta.debe.get(i));
				  }
			  }
			 /* if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  activoCorriente.add(cuenta.haber.get(i));
				  }
			  }*/
		  }
		  
		  //Patrimonio Neto
		  if(cuenta.prioridad<=-1 && cuenta.prioridad>=-20){
			  /*if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  patrimonioNeto.add(cuenta.debe.get(i));
				  }
			  }*/
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  patrimonioNeto.add(cuenta.haber.get(i));
				  }
			  }
		  }
		  
		  //Pasivo No Corriente
		  if(cuenta.prioridad<=-21 && cuenta.prioridad>=-30){
			  /*if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  pasivoNoCorriente.add(cuenta.debe.get(i));
				  }
			  }*/
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  pasivoNoCorriente.add(cuenta.haber.get(i));
				  }
			  }
		  }
		  
		  //Pasivo Corriente
		  if(cuenta.prioridad<=-31 && cuenta.prioridad>=-60){
			  /*if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  pasivoCorriente.add(cuenta.debe.get(i));
				  }
			  }*/
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  pasivoCorriente.add(cuenta.haber.get(i));
				  }
			  }
		  }
		  
		}
	}
	
	public void imprimeBalance(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		Collections.sort(activoNoCorriente);
		Collections.sort(activoCorriente);
		Collections.sort(patrimonioNeto);
		Collections.sort(pasivoNoCorriente);
		Collections.sort(pasivoCorriente);
		
		/* IMPRIME POR PANTALLA 
		System.out.println("\n\n BALANCE: \n");
		System.out.println("\n _______ACTIVO:______ \n");
		System.out.println("\n Activo No Corriente:");
		if (!(activoNoCorriente.isEmpty())){
			for(int i=0; i<activoNoCorriente.size(); i++){
				Calendar fecha= activoNoCorriente.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ activoNoCorriente.get(i).nombre +" \t\t\t\t"+ activoNoCorriente.get(i).cantidad);
			}
		}
		System.out.println("\n Activo Corriente:");
		if (!(activoCorriente.isEmpty())){
			for(int i=0; i<activoCorriente.size(); i++){
				Calendar fecha= activoCorriente.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ activoCorriente.get(i).nombre +" \t\t\t\t"+ activoCorriente.get(i).cantidad);
			}
		}
		System.out.println("\n");
		System.out.println("\n ____________PATRIMONIO NETO Y PASIVO: __________\n");
		System.out.println("\n Patrimonio neto:");
		if (!(patrimonioNeto.isEmpty())){
			for(int i=0; i<patrimonioNeto.size(); i++){
				Calendar fecha= patrimonioNeto.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ patrimonioNeto.get(i).nombre +" \t\t\t\t"+ patrimonioNeto.get(i).cantidad);
			}
		}
		
		System.out.println("\n Pasivo No Corriente:");
		if (!(pasivoNoCorriente.isEmpty())){
			for(int i=0; i<pasivoNoCorriente.size(); i++){
				Calendar fecha= pasivoNoCorriente.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ pasivoNoCorriente.get(i).nombre +" \t\t\t\t"+ pasivoNoCorriente.get(i).cantidad);
			}
		}
		
		System.out.println("\n Pasivo Corriente:");
		if (!(pasivoCorriente.isEmpty())){
			for(int i=0; i<pasivoCorriente.size(); i++){
				Calendar fecha= pasivoCorriente.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ pasivoCorriente.get(i).nombre +" \t\t\t\t"+ pasivoCorriente.get(i).cantidad);
			}
		}*/
		
		
		
		
		
		
		
		
		/* GENERA PDF */		
		Document documento = new Document();
		
		try{			
            PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Balance.pdf"));
            
            documento.open();
            documento.addTitle("Balance"); 
            documento.addAuthor("Noelia Manso García"); 
            
            //BALANCE
            PdfPTable balance = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("BALANCE ABREVIADO", FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (12.0f);
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            
            balance.addCell(celda);
            
            //ACTIVO
            PdfPTable activo = new PdfPTable(3);
    
            PdfPCell celdaActivo =new PdfPCell (new Paragraph("ACTIVO", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaActivo.setColspan(3);
            celdaActivo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaActivo.setPadding (10.0f);
            celdaActivo.setBackgroundColor(BaseColor.GRAY);
            activo.addCell(celdaActivo);
            
            //Activo No Corriente
            PdfPCell celdaActivoNoCorriente =new PdfPCell (new Paragraph("ACTIVO NO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
            celdaActivoNoCorriente.setColspan(2);
            celdaActivoNoCorriente.setPadding (10.0f);
            celdaActivoNoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoNoCorriente);
            activo.addCell("Suma A.NoCorriente");         

            for(int i=0; i<activoNoCorriente.size(); i++){
        	   Calendar fechaGastos= activoNoCorriente.get(i).fecha;
        	   activo.addCell(formateador.format(fechaGastos.getTime()));
        	   activo.addCell(activoNoCorriente.get(i).nombre);
       		   activo.addCell(activoNoCorriente.get(i).cantidad+"€");
            } 
           
            //Activo Corriente
            PdfPCell celdaActivoCorriente =new PdfPCell (new Paragraph("ACTIVO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
            celdaActivoCorriente.setColspan(2);
            celdaActivoCorriente.setPadding (10.0f);
            celdaActivoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoCorriente);
            activo.addCell("Suma A.Corriente");
           
            for(int i=0; i<activoCorriente.size(); i++){
            	Calendar fechaGastos= activoCorriente.get(i).fecha;
            	activo.addCell(formateador.format(fechaGastos.getTime()));
            	activo.addCell(activoCorriente.get(i).nombre);
            	activo.addCell(activoCorriente.get(i).cantidad+"€");
            } 
          
            //CELDA TOTAL ACTIVO
            PdfPCell resulActivo = new PdfPCell (new Paragraph("TOTAL ACTIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulActivo.setColspan(2);
           	resulActivo.setPadding (10.0f);
           	resulActivo.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell cantidadActivo = new PdfPCell (new Paragraph("Suma ACTIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	cantidadActivo.setColspan(2);
           	cantidadActivo.setPadding (10.0f);
           	cantidadActivo.setBackgroundColor(BaseColor.YELLOW);
           	activo.addCell(resulActivo);
          	activo.addCell(cantidadActivo);
 

          	
          	//PASIVO
          	PdfPTable pasivo = new PdfPTable(3);
  
          	PdfPCell celdaPasivo =new PdfPCell (new Paragraph("PASIVO", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivo.setColspan(3);
          	celdaPasivo.setHorizontalAlignment(Element.ALIGN_CENTER);
          	celdaPasivo.setPadding (10.0f);
          	celdaPasivo.setBackgroundColor(BaseColor.GRAY);
          	pasivo.addCell(celdaPasivo);
          
          	//Patrimonio Neto
          	PdfPCell celdaPN =new PdfPCell (new Paragraph("PATRIMONIO NETO", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPN.setColspan(2);
          	celdaPN.setPadding (10.0f);
          	celdaPN.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPN);
          	pasivo.addCell("Suma Patrimonio Neto");     

          	for(int i=0; i<patrimonioNeto.size(); i++){
          		Calendar fechaGastos= patrimonioNeto.get(i).fecha;
          		pasivo.addCell(formateador.format(fechaGastos.getTime()));
          		pasivo.addCell(patrimonioNeto.get(i).nombre);
          		pasivo.addCell(patrimonioNeto.get(i).cantidad+"€");
          	} 
          
          	//Pasivo No Corriente
          	PdfPCell celdaPasivoNoCorriente =new PdfPCell (new Paragraph("PASIVO NO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoNoCorriente.setColspan(2);
          	celdaPasivoNoCorriente.setPadding (10.0f);
          	celdaPasivoNoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoNoCorriente);
          	pasivo.addCell("Suma P.NoCorriente");        

          	for(int i=0; i<pasivoNoCorriente.size(); i++){
          		Calendar fechaGastos= pasivoNoCorriente.get(i).fecha;
          		pasivo.addCell(formateador.format(fechaGastos.getTime()));
          		pasivo.addCell(pasivoNoCorriente.get(i).nombre);
          		pasivo.addCell(pasivoNoCorriente.get(i).cantidad+"€");
          	} 
         
          	//Pasivo Corriente
          	PdfPCell celdaPasivoCorriente =new PdfPCell (new Paragraph("PASIVO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoCorriente.setColspan(2);
          	celdaPasivoCorriente.setPadding (10.0f);
          	celdaPasivoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoCorriente);
          	pasivo.addCell("Suma P.Corriente");
        
          	for(int i=0; i<pasivoCorriente.size(); i++){
          		Calendar fechaGastos= pasivoCorriente.get(i).fecha;
          		pasivo.addCell(formateador.format(fechaGastos.getTime()));
          		pasivo.addCell(pasivoCorriente.get(i).nombre);
          		pasivo.addCell(pasivoCorriente.get(i).cantidad+"€");
          	}      
        
          	//CELDA TOTAL PASIVO
          	PdfPCell resulPasivo = new PdfPCell (new Paragraph("TOTAL PASIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	resulPasivo.setColspan(2);
          	resulPasivo.setPadding (10.0f);
          	resulPasivo.setBackgroundColor(BaseColor.YELLOW);
          	PdfPCell cantidadPasivo = new PdfPCell (new Paragraph("Suma PASIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	cantidadPasivo.setColspan(2);
          	cantidadPasivo.setPadding (10.0f);
          	cantidadPasivo.setBackgroundColor(BaseColor.YELLOW);
          	pasivo.addCell(resulPasivo);
          	pasivo.addCell(cantidadPasivo);
          
            // Agregamos la tabla al documento   
           	documento.add(balance);
            documento.add(activo);
            documento.add(pasivo);
            
            documento.close();
            
        }catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
        }
	}

}
