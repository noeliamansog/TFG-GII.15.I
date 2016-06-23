package es.ubu.inf.tfg.solucion;

import java.io.File;
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

import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Cuenta;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase Balance que implementa la solución del balance del supuesto contable.
 * 
 * @author Noelia Manso García
 */
public class Balance extends Asiento{
	/**
	 * Año del que se desea generar el balance.
	 */
	private int ano;
	/**
	 * Fecha con la que calculamos el saldo de las cuentas.
	 */
	private Calendar fecha;
	
	/**
	 * Cuentas que pertenecen al activo no corriente material.
	 */
	private static ArrayList<Cuenta> activoNoCorrienteMaterial;
	/**
	 * Cuentas que pertenecen al activo no corriente intangible.
	 */
	private static ArrayList<Cuenta> activoNoCorrienteIntangible;
	/**
	 * Cuentas que pertenecen al activo no corriente financiero.
	 */
	private static ArrayList<Cuenta> activoNoCorrienteFinanciero;
	/**
	 * Cuentas que pertenecen al activo corriente existencias.
	 */
	private static ArrayList<Cuenta> activoCorrienteExistencias;
	/**
	 * Cuentas que pertenecen al activo corriente realizable.
	 */
	private static ArrayList<Cuenta> activoCorrienteRealizable;
	/**
	 * Cuentas que pertenecen al activo corriente disponible.
	 */
	private static ArrayList<Cuenta> activoCorrienteDisponible;
	/**
	 * Cuentas que pertenecen al patrimonio neto capital.
	 */
	private static ArrayList<Cuenta> patrimonioNetoCapital;
	/**
	 * Cuentas que pertenecen al patrimonio neto reservas.
	 */
	private static ArrayList<Cuenta> patrimonioNetoReservas;
	/**
	 * Cuentas que pertenecen al patrimonio neto subvenciones.
	 */
	private static ArrayList<Cuenta> patrimonioNetoSubvenciones;
	/**
	 * Cuentas que pertenecen al pasivo no corriente.
	 */
	private static ArrayList<Cuenta> pasivoNoCorriente;
	/**
	 * Cuentas que pertenecen al pasivo corriente.
	 */
	private static ArrayList<Cuenta> pasivoCorriente;

	
	/**
	 * Valor del activo no corriente material.
	 */
	private static double valorActivoNoCorrienteMaterial;
	/**
	 * Valor del activo no corriente intangible.
	 */
	private static double valorActivoNoCorrienteIntangible;
	/**
	 * Valor del activo no corriente financiero.
	 */
	private static double valorActivoNoCorrienteFinanciero;
	/**
	 * Valor del valor activo corriente existencias.
	 */
	private static double valorActivoCorrienteExistencias;
	/**
	 * Valor del activo corriente realizable.
	 */
	private static double valorActivoCorrienteRealizable;
	/**
	 * Valor del valor activo corriente disponible.
	 */
	private static double valorActivoCorrienteDisponible;
	/**
	 * Valor del valor activo corriente.
	 */
	public static double valorActivoCorriente;
	/**
	 * Valor del total activo.
	 */
	public static double totalActivo;
	/**
	 * Valor del patrimonio neto capital.
	 */
	private static double valorPatrimonioNetoCapital;
	/**
	 * Valor del patrimonio neto reservas.
	 */
	private static double valorPatrimonioNetoReservas;
	/**
	 * Valor del patrimonio neto subvenciones.
	 */
	private static double valorPatrimonioNetoSubvenciones;
	/**
	 * Valor del patrimonio neto.
	 */
	public static double valorPatrimonioNeto;
	/**
	 * Valor del pasivo no corriente.
	 */
	public static double valorPasivoNoCorriente;
	/**
	 * Valor del pasivo corriente.
	 */
	public static double valorPasivoCorriente;
	/**
	 * Valor del patrimonio neto y pasivo.
	 */
	public static double totalPNyPasivo;

	
	/**
	 * Contructor de la clase Balance en el que se genera el balance del supuesto contable 
	 * en el año pasado como parámetro.
	 * @param ano Año del que se desea generar el balance.
	 */
	public Balance(int ano){
		this.ano=ano;
		fecha = Calendar.getInstance();
		fecha.set(ano,11,31);
		
		activoNoCorrienteMaterial = new ArrayList<Cuenta>();
		activoNoCorrienteIntangible = new ArrayList<Cuenta>();
		activoNoCorrienteFinanciero = new ArrayList<Cuenta>();
		activoCorrienteExistencias = new ArrayList<Cuenta>();
		activoCorrienteRealizable = new ArrayList<Cuenta>();
		activoCorrienteDisponible = new ArrayList<Cuenta>();
		patrimonioNetoCapital = new ArrayList<Cuenta>();
		patrimonioNetoReservas = new ArrayList<Cuenta>();
		patrimonioNetoSubvenciones = new ArrayList<Cuenta>();
		pasivoNoCorriente = new ArrayList<Cuenta>();
		pasivoCorriente = new ArrayList<Cuenta>();	
		
		valorActivoNoCorrienteMaterial = 0;
		valorActivoNoCorrienteIntangible = 0;
		valorActivoNoCorrienteFinanciero = 0;
		valorActivoCorrienteExistencias = 0;
		valorActivoCorrienteRealizable = 0;
		valorActivoCorrienteDisponible = 0;
		valorActivoCorriente = 0;
		totalActivo = 0;
		valorPatrimonioNetoCapital = 0;
		valorPatrimonioNetoReservas = 0;
		valorPatrimonioNetoSubvenciones = 0;
		valorPatrimonioNeto = 0;
		valorPasivoNoCorriente = 0;
		valorPasivoCorriente = 0;
		totalPNyPasivo = 0;
	
		
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
	
	/**
	 * Función que devuelve el valor del total del activo.
	 * @return Valor total del activo.
	 */
	public double getActivo(){
		return totalActivo;
	}
	/**
	 * Funcion que devuelve el valor del patrimonio neto.
	 * @return Valor del patrimonio neto.
	 */
	public double getPN(){
		return valorPatrimonioNeto;
	}
	
	/**
	 * Función que imprime el balance en un documento PDF.
	 */
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
			File directorio = new File(Main.direccionRuta+"/"+ano);
			if(!directorio.exists()){
				directorio.mkdir();
			}	
			PdfWriter.getInstance(documento, new FileOutputStream(directorio+"/Balance"+ano+".pdf"));
           
            documento.open();
            documento.addTitle("Balance"); 
            documento.addAuthor("Noelia Manso García"); 
            
            
            
            //BALANCE
            PdfPTable balance = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("BALANCE ABREVIADO \n del año "+ano, FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
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
            
            //ACTIVO NO CORRIENTE
            PdfPCell celdaActivoNoCorriente1 =new PdfPCell (new Paragraph("ACTIVO NO CORRIENTE", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaActivoNoCorriente1.setPadding (10.0f);
            celdaActivoNoCorriente1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell celdaActivoNoCorriente2 =new PdfPCell (new Paragraph((Math.round(valorActivoNoCorrienteMaterial + valorActivoNoCorrienteIntangible + valorActivoNoCorrienteFinanciero)*100)/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaActivoNoCorriente2.setPadding (10.0f);
            celdaActivoNoCorriente2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoNoCorriente1);
            activo.addCell(celdaActivoNoCorriente2);      

            //Activo no corriente material
            PdfPCell celdaActivoNoCorrienteMaterial1 = new PdfPCell (new Paragraph("Activo no corriente material", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteMaterial1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoNoCorrienteMaterial2 = new PdfPCell (new Paragraph((Math.round(valorActivoNoCorrienteMaterial)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteMaterial2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoNoCorrienteMaterial1);
            activo.addCell(celdaActivoNoCorrienteMaterial2);       

            for(int i=0; i<activoNoCorrienteMaterial.size(); i++){
        	   activo.addCell(activoNoCorrienteMaterial.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteMaterial.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            //Activo no corriente intangible
            PdfPCell celdaActivoNoCorrienteIntangible1 = new PdfPCell (new Paragraph("Activo no corriente intangible", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteIntangible1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoNoCorrienteIntangible2 = new PdfPCell (new Paragraph((Math.round(valorActivoNoCorrienteIntangible)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteIntangible2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoNoCorrienteIntangible1);
            activo.addCell(celdaActivoNoCorrienteIntangible2);       

            for(int i=0; i<activoNoCorrienteIntangible.size(); i++){
        	   activo.addCell(activoNoCorrienteIntangible.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteIntangible.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            //Activo no corriente financiero
            PdfPCell celdaActivoNoCorrienteFinanciero1 = new PdfPCell (new Paragraph("Activo no corriente financiero", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteFinanciero1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoNoCorrienteFinanciero2 = new PdfPCell (new Paragraph((Math.round(valorActivoNoCorrienteFinanciero)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoNoCorrienteFinanciero2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoNoCorrienteFinanciero1);
            activo.addCell(celdaActivoNoCorrienteFinanciero2);      

            for(int i=0; i<activoNoCorrienteFinanciero.size(); i++){
        	   activo.addCell(activoNoCorrienteFinanciero.get(i).nombre);
       		   activo.addCell(Math.round(activoNoCorrienteFinanciero.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
           
            //ACTIVO CORRIENTE
            valorActivoCorriente = valorActivoCorrienteExistencias + valorActivoCorrienteRealizable + valorActivoCorrienteDisponible;
            PdfPCell celdaActivoCorriente1 =new PdfPCell (new Paragraph("ACTIVO CORRIENTE", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaActivoCorriente1.setPadding (10.0f);
            celdaActivoCorriente1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell celdaActivoCorriente2 =new PdfPCell (new Paragraph((Math.round(valorActivoCorriente)*100)/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            celdaActivoCorriente2.setPadding (10.0f);
            celdaActivoCorriente2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            activo.addCell(celdaActivoCorriente1);
            activo.addCell(celdaActivoCorriente2);
            
            
            //Activo corriente existencias
            PdfPCell celdaActivoCorrienteExistencias1 =new PdfPCell (new Paragraph("Activo corriente existencias", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteExistencias1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoCorrienteExistencias2 =new PdfPCell (new Paragraph((Math.round(valorActivoCorrienteExistencias)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteExistencias2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoCorrienteExistencias1);
            activo.addCell(celdaActivoCorrienteExistencias2);
     
            for(int i=0; i<activoCorrienteExistencias.size(); i++){
            	activo.addCell(activoCorrienteExistencias.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteExistencias.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            //Activo corriente realizable
            PdfPCell celdaActivoCorrienteRealizable1 =new PdfPCell (new Paragraph("Activo corriente realizable", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteRealizable1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoCorrienteRealizable2 =new PdfPCell (new Paragraph((Math.round(valorActivoCorrienteRealizable)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteRealizable2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoCorrienteRealizable1);
            activo.addCell(celdaActivoCorrienteRealizable2);
     
            for(int i=0; i<activoCorrienteRealizable.size(); i++){
            	activo.addCell(activoCorrienteRealizable.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteRealizable.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
            //Activo corriente disponible
            PdfPCell celdaActivoCorrienteDisponible1 =new PdfPCell (new Paragraph("Activo corriente disponible", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteDisponible1.setBackgroundColor(BaseColor.PINK);
            PdfPCell celdaActivoCorrienteDisponible2 =new PdfPCell (new Paragraph((Math.round(valorActivoCorrienteDisponible)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
            celdaActivoCorrienteDisponible2.setBackgroundColor(BaseColor.PINK);
            activo.addCell(celdaActivoCorrienteDisponible1);
            activo.addCell(celdaActivoCorrienteDisponible2);
     
            for(int i=0; i<activoCorrienteDisponible.size(); i++){
            	activo.addCell(activoCorrienteDisponible.get(i).nombre);
            	activo.addCell(Math.round(activoCorrienteDisponible.get(i).getSaldo(fecha))*100/100+"€");
            } 
            activo.addCell(" ");
            activo.addCell(" ");
            
          
            //CELDA TOTAL ACTIVO
            totalActivo = (valorActivoNoCorrienteMaterial + valorActivoNoCorrienteIntangible + valorActivoNoCorrienteFinanciero) + (valorActivoCorrienteExistencias + valorActivoCorrienteRealizable + valorActivoCorrienteDisponible);
            PdfPCell resulActivo = new PdfPCell (new Paragraph("TOTAL ACTIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	resulActivo.setPadding (10.0f);
           	resulActivo.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell cantidadActivo = new PdfPCell (new Paragraph(Math.round(totalActivo)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	cantidadActivo.setColspan(2);
           	cantidadActivo.setPadding (10.0f);
           	cantidadActivo.setBackgroundColor(BaseColor.YELLOW);
           	activo.addCell(resulActivo);
          	activo.addCell(cantidadActivo);

          	
          	//PATRIMONIO NETO + PASIVO
          	PdfPTable pasivo = new PdfPTable(2);
          	PdfPCell celdaPasivo =new PdfPCell (new Paragraph("PATRIMONIO NETO + PASIVO", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivo.setColspan(2);
          	celdaPasivo.setHorizontalAlignment(Element.ALIGN_CENTER);
          	celdaPasivo.setPadding (10.0f);
          	celdaPasivo.setBackgroundColor(BaseColor.GRAY);
          	pasivo.addCell(celdaPasivo);
          
          	//PATRIMONIO NETO
          	valorPatrimonioNeto = valorPatrimonioNetoCapital + valorPatrimonioNetoReservas + valorPatrimonioNetoSubvenciones;
          	PdfPCell celdaPN1 =new PdfPCell (new Paragraph("PATRIMONIO NETO", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPN1.setPadding (10.0f);
          	celdaPN1.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	PdfPCell celdaPN2 =new PdfPCell (new Paragraph((Math.round(valorPatrimonioNeto)*100)/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPN2.setPadding (10.0f);
          	celdaPN2.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPN1);
          	pasivo.addCell(celdaPN2);
          	
          	//Patrimonio neto capital
          	PdfPCell celdaPNCapital1 =new PdfPCell (new Paragraph("Patrimonio neto capital", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNCapital1.setBackgroundColor(BaseColor.PINK);
          	PdfPCell celdaPNCapital2 =new PdfPCell (new Paragraph((Math.round(valorPatrimonioNetoCapital)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNCapital2.setBackgroundColor(BaseColor.PINK);
          	pasivo.addCell(celdaPNCapital1);
          	pasivo.addCell(celdaPNCapital2);     

          	for(int i=0; i<patrimonioNetoCapital.size(); i++){
          		pasivo.addCell(patrimonioNetoCapital.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoCapital.get(i).getSaldo(fecha))*100/100+"€");
          	}  
            pasivo.addCell(" ");
            pasivo.addCell(" ");
            
          	//Patrimonio neto reservas
          	PdfPCell celdaPNReservas1 =new PdfPCell (new Paragraph("Patrimonio neto reservas", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNReservas1.setBackgroundColor(BaseColor.PINK);
          	PdfPCell celdaPNReservas2 =new PdfPCell (new Paragraph((Math.round(valorPatrimonioNetoReservas)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNReservas2.setBackgroundColor(BaseColor.PINK);
          	pasivo.addCell(celdaPNReservas1);
          	pasivo.addCell(celdaPNReservas2);  

          	for(int i=0; i<patrimonioNetoReservas.size(); i++){
          		pasivo.addCell(patrimonioNetoReservas.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoReservas.get(i).getSaldo(fecha))*100/100+"€");
          	}  
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
            
          	//Patrimonio neto subvenciones
          	PdfPCell celdaPNSubvenciones1 =new PdfPCell (new Paragraph("Patrimonio neto subvenciones", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNSubvenciones1.setBackgroundColor(BaseColor.PINK);
          	PdfPCell celdaPNSubvenciones2 =new PdfPCell (new Paragraph((Math.round(valorPatrimonioNetoSubvenciones)*100)/100+"€", FontFactory.getFont("arial",9,Font.BOLDITALIC, BaseColor.BLACK)));
          	celdaPNSubvenciones2.setBackgroundColor(BaseColor.PINK);
          	pasivo.addCell(celdaPNSubvenciones1);
          	pasivo.addCell(celdaPNSubvenciones2);  

          	for(int i=0; i<patrimonioNetoSubvenciones.size(); i++){
          		pasivo.addCell(patrimonioNetoSubvenciones.get(i).nombre);
          		pasivo.addCell(Math.round(patrimonioNetoSubvenciones.get(i).getSaldo(fecha))*100/100+"€");
          	}  
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
          
          	//PASIVO NO CORRIENTE
          	PdfPCell celdaPasivoNoCorriente1 =new PdfPCell (new Paragraph("PASIVO NO CORRIENTE", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoNoCorriente1.setPadding (10.0f);
          	celdaPasivoNoCorriente1.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	PdfPCell celdaPasivoNoCorriente2 =new PdfPCell (new Paragraph((Math.round(valorPasivoNoCorriente)*100)/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoNoCorriente2.setPadding (10.0f);
          	celdaPasivoNoCorriente2.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoNoCorriente1);
          	pasivo.addCell(celdaPasivoNoCorriente2);       

          	for(int i=0; i<pasivoNoCorriente.size(); i++){
          		pasivo.addCell(pasivoNoCorriente.get(i).nombre);
          		pasivo.addCell(Math.round(pasivoNoCorriente.get(i).getSaldo(fecha))*100/100+"€");
          	} 
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
         
          	//PASIVO CORRIENTE
          	PdfPCell celdaPasivoCorriente1 =new PdfPCell (new Paragraph("PASIVO CORRIENTE", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoCorriente1.setPadding (10.0f);
          	celdaPasivoCorriente1.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	PdfPCell celdaPasivoCorriente2 =new PdfPCell (new Paragraph((Math.round(valorPasivoCorriente)*100)/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	celdaPasivoCorriente2.setPadding (10.0f);
          	celdaPasivoCorriente2.setBackgroundColor(BaseColor.LIGHT_GRAY);
          	pasivo.addCell(celdaPasivoCorriente1);
          	pasivo.addCell(celdaPasivoCorriente2);
        
          	for(int i=0; i<pasivoCorriente.size(); i++){
          		pasivo.addCell(pasivoCorriente.get(i).nombre);
          		pasivo.addCell(Math.round(pasivoCorriente.get(i).getSaldo(fecha))*100/100+"€");
          	}
          	pasivo.addCell(" ");
          	pasivo.addCell(" ");
          	
          	//CELDA TOTAL PASIVO
          	totalPNyPasivo = (valorPatrimonioNetoCapital + valorPatrimonioNetoReservas + valorPatrimonioNetoSubvenciones) + valorPasivoNoCorriente + valorPasivoCorriente;
          	PdfPCell resulPasivo = new PdfPCell (new Paragraph("TOTAL PATRIMONIO NETO Y PASIVO:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
          	resulPasivo.setPadding (10.0f);
          	resulPasivo.setBackgroundColor(BaseColor.YELLOW);
          	PdfPCell cantidadPasivo = new PdfPCell (new Paragraph(Math.round(totalPNyPasivo)*100/100+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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
