package es.ubu.inf.tfg.otrasCosas;

import java.io.FileOutputStream;
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
	static ArrayList<Cuenta> activoNoCorrienteMaterial = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> activoNoCorrienteIntangible = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> activoNoCorrienteFinanciero = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> activoCorrienteExistencias = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> activoCorrienteRealizable = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> activoCorrienteDisponible = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> patrimonioNetoCapital = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> patrimonioNetoReservas = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> patrimonioNetoSubvenciones = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> pasivoNoCorriente = new ArrayList<Cuenta>();
	static ArrayList<Cuenta> pasivoCorriente = new ArrayList<Cuenta>();	
	private int año;
	private Calendar fecha;
	static double valorActivoNoCorrienteMaterial;
	static double valorActivoNoCorrienteIntangible;
	static double valorActivoNoCorrienteFinanciero;
	static double valorActivoCorrienteExistencias;
	static double valorActivoCorrienteRealizable;
	static double valorActivoCorrienteDisponible;
	static double valorPatrimonioNetoCapital;
	static double valorPatrimonioNetoReservas;
	static double valorPatrimonioNetoSubvenciones;
	static double valorPasivoNoCorriente;
	static double valorPasivoCorriente;
	
	public Balance(int año){
		this.año=año;
		fecha = Calendar.getInstance();
		fecha.set(año,11,31);
		valorActivoNoCorrienteMaterial = 0;
		valorActivoNoCorrienteIntangible = 0;
		valorActivoNoCorrienteFinanciero = 0;
		valorActivoCorrienteExistencias = 0;
		valorActivoCorrienteRealizable = 0;
		valorActivoCorrienteDisponible = 0;
		valorPatrimonioNetoCapital = 0;
		valorPatrimonioNetoReservas = 0;
		valorPatrimonioNetoSubvenciones = 0;
		valorPasivoNoCorriente = 0;
		valorPasivoCorriente = 0;
		
		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);

		  //Activo No Corriente Material
		  if(cuenta.prioridad>=1 && cuenta.prioridad<=10){
			  activoNoCorrienteMaterial.add(cuenta);
			  valorActivoNoCorrienteMaterial += cuenta.getSaldo(fecha);
		  }
		  
		  //Activo No Corriente Intangible
		  if(cuenta.prioridad>=11 && cuenta.prioridad<=20){
			  activoNoCorrienteIntangible.add(cuenta);
			  valorActivoNoCorrienteIntangible += cuenta.getSaldo(fecha);
		  }
		  
		  //Activo No Corriente Financiero
		  if(cuenta.prioridad>=21 && cuenta.prioridad<=30){
			  activoNoCorrienteFinanciero.add(cuenta);
			  valorActivoNoCorrienteFinanciero += cuenta.getSaldo(fecha);
		  }
		  
		  //Activo Corriente Existencias
		  if(cuenta.prioridad>=31 && cuenta.prioridad<=40){
			  activoCorrienteExistencias.add(cuenta);
			  valorActivoCorrienteExistencias += cuenta.getSaldo(fecha);
		  }
		  
		  //Activo Corriente Realizable
		  if(cuenta.prioridad>=41 && cuenta.prioridad<=50){
			  activoCorrienteRealizable.add(cuenta);
			  valorActivoCorrienteRealizable += cuenta.getSaldo(fecha);
		  }
		  //Activo Corriente Disponible
		  if(cuenta.prioridad>=51 && cuenta.prioridad<=60){
			  activoCorrienteDisponible.add(cuenta);
			  valorActivoCorrienteDisponible += cuenta.getSaldo(fecha);
		  }
		  
		  //Patrimonio Neto Capital
		  if(cuenta.prioridad<=-1 && cuenta.prioridad>=-5){
			  patrimonioNetoCapital.add(cuenta);
			  valorPatrimonioNetoCapital += cuenta.getSaldo(fecha);
		  }
		  
		  //Patrimonio Neto Reservas
		  if(cuenta.prioridad<=-6 && cuenta.prioridad>=-15){
			  patrimonioNetoReservas.add(cuenta);
			  valorPatrimonioNetoReservas += cuenta.getSaldo(fecha);
		  }
		  
		  //Patrimonio Neto Subvenciones
		  if(cuenta.prioridad<=-16 && cuenta.prioridad>=-20){
			  patrimonioNetoSubvenciones.add(cuenta);
			  valorPatrimonioNetoSubvenciones += cuenta.getSaldo(fecha);
		  }
		  
		  //Pasivo No Corriente
		  if(cuenta.prioridad<=-21 && cuenta.prioridad>=-30){
			  pasivoNoCorriente.add(cuenta);
			  valorPasivoNoCorriente += cuenta.getSaldo(fecha);
		  }
		  
		  //Pasivo Corriente
		  if(cuenta.prioridad<=-31 && cuenta.prioridad>=-60){ 
			  pasivoCorriente.add(cuenta);
			  valorPasivoCorriente += cuenta.getSaldo(fecha);
		  }
		  
		}
	}
	
	public void imprimeBalance(){
		
		Collections.sort(activoNoCorrienteMaterial);
		Collections.sort(activoNoCorrienteIntangible);
		Collections.sort(activoNoCorrienteFinanciero);
		Collections.sort(activoCorrienteExistencias);
		Collections.sort(activoCorrienteRealizable);
		Collections.sort(activoCorrienteDisponible);
		Collections.sort(patrimonioNetoCapital);
		Collections.sort(patrimonioNetoReservas);
		Collections.sort(patrimonioNetoSubvenciones);
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
            
            PdfPCell celda =new PdfPCell (new Paragraph("BALANCE ABREVIADO \n del año "+año, FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (12.0f);
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            
            balance.addCell(celda);
            
            //ACTIVO
            PdfPTable activo = new PdfPTable(2);
    
            PdfPCell celdaActivo =new PdfPCell (new Paragraph("ACTIVO", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaActivo.setColspan(2);
            celdaActivo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaActivo.setPadding (10.0f);
            celdaActivo.setBackgroundColor(BaseColor.GRAY);
            activo.addCell(celdaActivo);
            
            //Activo No Corriente
            PdfPCell celdaActivoNoCorriente =new PdfPCell (new Paragraph("ACTIVO NO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
            celdaActivoNoCorriente.setPadding (10.0f);
            celdaActivoNoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoNoCorriente);
            activo.addCell((Math.round(valorActivoNoCorrienteMaterial + valorActivoNoCorrienteIntangible + valorActivoNoCorrienteFinanciero)*100)/100+"€");        

            for(int i=0; i<activoNoCorrienteMaterial.size(); i++){
        	   activo.addCell(activoNoCorrienteMaterial.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteMaterial.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            for(int i=0; i<activoNoCorrienteIntangible.size(); i++){
        	   activo.addCell(activoNoCorrienteIntangible.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteIntangible.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            for(int i=0; i<activoNoCorrienteFinanciero.size(); i++){
        	   activo.addCell(activoNoCorrienteFinanciero.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteFinanciero.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
           
            //Activo Corriente
            PdfPCell celdaActivoCorriente =new PdfPCell (new Paragraph("ACTIVO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
            celdaActivoCorriente.setPadding (10.0f);
            celdaActivoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoCorriente);
            activo.addCell((Math.round(valorActivoCorrienteExistencias + valorActivoCorrienteRealizable + valorActivoCorrienteDisponible)*100)/100+"€");
           
            for(int i=0; i<activoCorrienteExistencias.size(); i++){
            	activo.addCell(activoCorrienteExistencias.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteExistencias.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            for(int i=0; i<activoCorrienteRealizable.size(); i++){
            	activo.addCell(activoCorrienteRealizable.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteRealizable.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            for(int i=0; i<activoCorrienteDisponible.size(); i++){
            	activo.addCell(activoCorrienteDisponible.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteDisponible.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
          
            //CELDA TOTAL ACTIVO
            double totalActivo = (valorActivoNoCorrienteMaterial + valorActivoNoCorrienteIntangible + valorActivoNoCorrienteFinanciero) + (valorActivoCorrienteExistencias + valorActivoCorrienteRealizable + valorActivoCorrienteDisponible);
            PdfPCell resulActivo = new PdfPCell (new Paragraph("TOTAL ACTIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	resulActivo.setPadding (10.0f);
           	resulActivo.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell cantidadActivo = new PdfPCell (new Paragraph(Math.round(totalActivo)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	cantidadActivo.setColspan(2);
           	cantidadActivo.setPadding (10.0f);
           	cantidadActivo.setBackgroundColor(BaseColor.YELLOW);
           	activo.addCell(resulActivo);
          	activo.addCell(cantidadActivo);

          	
          	//PASIVO
          	PdfPTable pasivo = new PdfPTable(2);
  
          	PdfPCell celdaPasivo =new PdfPCell (new Paragraph("PATRIMONIO NETO + PASIVO", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivo.setColspan(2);
          	celdaPasivo.setHorizontalAlignment(Element.ALIGN_CENTER);
          	celdaPasivo.setPadding (10.0f);
          	celdaPasivo.setBackgroundColor(BaseColor.GRAY);
          	pasivo.addCell(celdaPasivo);
          
          	//Patrimonio Neto
          	PdfPCell celdaPN =new PdfPCell (new Paragraph("PATRIMONIO NETO", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPN.setPadding (10.0f);
          	celdaPN.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPN);
          	pasivo.addCell((Math.round(valorPatrimonioNetoCapital + valorPatrimonioNetoReservas + valorPatrimonioNetoSubvenciones)*100)/100+"€");     

          	for(int i=0; i<patrimonioNetoCapital.size(); i++){
          		pasivo.addCell(patrimonioNetoCapital.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoCapital.get(i).getSaldo(fecha))*100/100+"€");
          	}  
            pasivo.addCell(" ");
            pasivo.addCell(" ");
            
          	for(int i=0; i<patrimonioNetoReservas.size(); i++){
          		pasivo.addCell(patrimonioNetoReservas.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoReservas.get(i).getSaldo(fecha))*100/100+"€");
          	}  
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
            
          	for(int i=0; i<patrimonioNetoSubvenciones.size(); i++){
          		pasivo.addCell(patrimonioNetoSubvenciones.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoSubvenciones.get(i).getSaldo(fecha))*100/100+"€");
          	}  
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
          
          	//Pasivo No Corriente
          	PdfPCell celdaPasivoNoCorriente =new PdfPCell (new Paragraph("PASIVO NO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoNoCorriente.setPadding (10.0f);
          	celdaPasivoNoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoNoCorriente);
          	pasivo.addCell(valorPasivoNoCorriente+"€");        

          	for(int i=0; i<pasivoNoCorriente.size(); i++){
          		pasivo.addCell(pasivoNoCorriente.get(i).nombre);
          		pasivo.addCell(Math.round(pasivoNoCorriente.get(i).getSaldo(fecha))*100/100+"€");
          	} 
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
         
          	//Pasivo Corriente
          	PdfPCell celdaPasivoCorriente =new PdfPCell (new Paragraph("PASIVO CORRIENTE", FontFactory.getFont("arial",9,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoCorriente.setPadding (10.0f);
          	celdaPasivoCorriente.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoCorriente);
          	pasivo.addCell((Math.round(valorPasivoCorriente)*100)/100+"€"); 
        
          	for(int i=0; i<pasivoCorriente.size(); i++){
          		pasivo.addCell(pasivoCorriente.get(i).nombre);
          		pasivo.addCell(Math.round(pasivoCorriente.get(i).getSaldo(fecha))*100/100+"€");
          	}
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
          	
          	//CELDA TOTAL PASIVO
          	double totalPasivo = (valorPatrimonioNetoCapital + valorPatrimonioNetoReservas + valorPatrimonioNetoSubvenciones) + valorPasivoNoCorriente + valorPasivoCorriente;
          	PdfPCell resulPasivo = new PdfPCell (new Paragraph("TOTAL PATRIMONIO NETO Y PASIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	resulPasivo.setPadding (10.0f);
          	resulPasivo.setBackgroundColor(BaseColor.YELLOW);
          	PdfPCell cantidadPasivo = new PdfPCell (new Paragraph(Math.round(totalPasivo)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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
