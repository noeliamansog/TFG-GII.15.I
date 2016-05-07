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
	static ArrayList<Anotacion> activoNoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> activoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> patrimonioNeto = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pasivoNoCorriente = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pasivoCorriente = new ArrayList<Anotacion>();	
	
	static double valorActivoNoCorriente;
	static double valorActivoCorriente;
	static double valorPatrimonioNeto;
	static double valorPasivoNoCorriente;
	static double valorPasivoCorriente;
	
	public Balance(Calendar fecha){
		this.fecha = fecha;
		valorActivoNoCorriente = 0;
		valorActivoCorriente = 0;
		valorPatrimonioNeto = 0;
		valorPasivoNoCorriente = 0;
		valorPasivoCorriente = 0;
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);
		  
		  //Activo No Corriente
		  if(cuenta.prioridad>=1 && cuenta.prioridad<=30){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  if (cuenta.debe.get(i).fecha.before(fecha)){
						  activoNoCorriente.add(cuenta.debe.get(i));
						  valorActivoNoCorriente += cuenta.debe.get(i).cantidad;
					  }
				  }
			  }
		  }
		  
		  //Activo Corriente
		  if(cuenta.prioridad>=31 && cuenta.prioridad<=60){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  if (cuenta.debe.get(i).fecha.before(fecha)){
						  activoCorriente.add(cuenta.debe.get(i));
						  valorActivoCorriente += cuenta.debe.get(i).cantidad;
					  }
				  }
			  }
		  }
		  
		  //Patrimonio Neto
		  if(cuenta.prioridad<=-1 && cuenta.prioridad>=-20){
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  if (cuenta.haber.get(i).fecha.before(fecha)){
						  patrimonioNeto.add(cuenta.haber.get(i));
						  valorPatrimonioNeto += cuenta.haber.get(i).cantidad;
					  }
				  }
			  }
		  }
		  
		  //Pasivo No Corriente
		  if(cuenta.prioridad<=-21 && cuenta.prioridad>=-30){
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  if (cuenta.haber.get(i).fecha.before(fecha)){
						  pasivoNoCorriente.add(cuenta.haber.get(i));
						  valorPasivoNoCorriente += cuenta.haber.get(i).cantidad;
					  }
				  }
			  }
		  }
		  
		  //Pasivo Corriente
		  if(cuenta.prioridad<=-31 && cuenta.prioridad>=-60){
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  if (cuenta.haber.get(i).fecha.before(fecha)){
						  pasivoCorriente.add(cuenta.haber.get(i));
						  valorPasivoCorriente += cuenta.haber.get(i).cantidad;
					  }
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
				
		Document documento = new Document();
		
		try{			
            PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Balance.pdf"));
            
            documento.open();
            documento.addTitle("Balance"); 
            documento.addAuthor("Noelia Manso García"); 
            
            //BALANCE
            PdfPTable balance = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("BALANCE ABREVIADO hasta "+formateador.format(fecha.getTime()), FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
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
            activo.addCell(valorActivoNoCorriente+"€");         

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
            activo.addCell(valorActivoCorriente+"€");
           
            for(int i=0; i<activoCorriente.size(); i++){
            	Calendar fechaGastos= activoCorriente.get(i).fecha;
            	activo.addCell(formateador.format(fechaGastos.getTime()));
            	activo.addCell(activoCorriente.get(i).nombre);
            	activo.addCell(activoCorriente.get(i).cantidad+"€");
            } 
          
            //CELDA TOTAL ACTIVO
            double totalActivo = valorActivoNoCorriente+valorActivoCorriente;
            PdfPCell resulActivo = new PdfPCell (new Paragraph("TOTAL ACTIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            resulActivo.setColspan(2);
           	resulActivo.setPadding (10.0f);
           	resulActivo.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell cantidadActivo = new PdfPCell (new Paragraph(totalActivo+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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
          	pasivo.addCell(valorPatrimonioNeto+"€");     

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
          	pasivo.addCell(valorPasivoNoCorriente+"€");        

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
          	pasivo.addCell(valorPasivoCorriente+"€");
        
          	for(int i=0; i<pasivoCorriente.size(); i++){
          		Calendar fechaGastos= pasivoCorriente.get(i).fecha;
          		pasivo.addCell(formateador.format(fechaGastos.getTime()));
          		pasivo.addCell(pasivoCorriente.get(i).nombre);
          		pasivo.addCell(pasivoCorriente.get(i).cantidad+"€");
          	}      
          	double totalPasivo = valorPatrimonioNeto + valorPasivoNoCorriente + valorPasivoCorriente;
          	//CELDA TOTAL PASIVO
          	PdfPCell resulPasivo = new PdfPCell (new Paragraph("TOTAL PASIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	resulPasivo.setColspan(2);
          	resulPasivo.setPadding (10.0f);
          	resulPasivo.setBackgroundColor(BaseColor.YELLOW);
          	PdfPCell cantidadPasivo = new PdfPCell (new Paragraph(totalPasivo+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	cantidadPasivo.setColspan(2);
          	cantidadPasivo.setPadding (10.0f);
          	cantidadPasivo.setBackgroundColor(BaseColor.YELLOW);
          	pasivo.addCell(resulPasivo);
          	pasivo.addCell(cantidadPasivo);
          
            // Agregamos las tablas al documento   
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
