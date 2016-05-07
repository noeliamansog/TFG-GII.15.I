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
	static double valorCobros;
	static double valorPagos;
	
	public Tesoreria(Calendar fecha){
		this.fecha = fecha;
		valorCobros=0;
		valorPagos=0;

		Iterator<Integer> it = cuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = cuentas.get(key);
		  
		  //Cobros
		  if (!(cuenta.haber.isEmpty())){
			  for(int i=0; i<cuenta.haber.size(); i++){
				  if (cuenta.haber.get(i).fecha.before(fecha)){
					  cobros.add(cuenta.haber.get(i));
					  valorCobros += cuenta.haber.get(i).cantidad;
				  }
			  }
		  }
		  //Gastos
		  if (!(cuenta.debe.isEmpty())){
			  for(int i=0; i<cuenta.debe.size(); i++){
				  if (cuenta.debe.get(i).fecha.before(fecha)){
					  pagos.add(cuenta.debe.get(i));
					  valorPagos += cuenta.debe.get(i).cantidad;
				  }
			  }
		  }
		}	
	}
	
	public void imprimeTesoreria(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		Collections.sort(cobros);
		Collections.sort(pagos);
	
		Document documento = new Document();
		
		try{			
            PdfWriter.getInstance(documento, new FileOutputStream("/Users/noelia/Desktop/Tesorería.pdf"));
            
            documento.open();
            documento.addTitle("Tesorería"); 
            documento.addAuthor("Noelia Manso García"); 
            
            //TESORERIA
            PdfPTable tesoreria = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("TESORERÍA hasta "+formateador.format(fecha.getTime()), FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding (12.0f);
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            
            tesoreria.addCell(celda);
            
            //COBROS
            PdfPTable tablaCobros = new PdfPTable(3);
    
            PdfPCell celdaCobros =new PdfPCell (new Paragraph("COBROS", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaCobros.setColspan(3);
            celdaCobros.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaCobros.setPadding (10.0f);
            celdaCobros.setBackgroundColor(BaseColor.GRAY);
            tablaCobros.addCell(celdaCobros);
            
            for(int i=0; i<cobros.size(); i++){
         	   Calendar fechaCobros= cobros.get(i).fecha;
         	   tablaCobros.addCell(formateador.format(fechaCobros.getTime()));
         	   tablaCobros.addCell(cobros.get(i).nombre);
         	   tablaCobros.addCell(cobros.get(i).cantidad+"€");
             } 
            
            
            //Pagos
            PdfPTable tablaPagos = new PdfPTable(3);
            
            PdfPCell celdaPagos =new PdfPCell (new Paragraph("GASTOS", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaPagos.setColspan(3);
            celdaPagos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaPagos.setPadding (10.0f);
            celdaPagos.setBackgroundColor(BaseColor.GRAY);
            tablaCobros.addCell(celdaPagos);
            
            for(int i=0; i<pagos.size(); i++){
         	   Calendar fechaPagos= pagos.get(i).fecha;
         	   tablaPagos.addCell(formateador.format(fechaPagos.getTime()));
         	   tablaPagos.addCell(pagos.get(i).nombre);
         	   tablaPagos.addCell(pagos.get(i).cantidad+"€");
             } 
            
            
            //SALDO TOTAL
           	PdfPTable saldoTotal = new PdfPTable(3);
            PdfPCell total = new PdfPCell (new Paragraph("SALDO TOTAL TESORERÍA:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            total.setColspan(2);
            total.setPadding (10.0f);
            total.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell calculoTotal = new PdfPCell (new Paragraph(valorCobros-valorPagos+"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
           	calculoTotal.setPadding (10.0f);
           	calculoTotal.setBackgroundColor(BaseColor.YELLOW);
           	saldoTotal.addCell(total);
           	saldoTotal.addCell(calculoTotal);
         
            // Agregamos las tablas al documento  
            documento.add(tesoreria);
           	documento.add(tablaCobros);
            documento.add(tablaPagos);
            documento.add(saldoTotal);
            
            documento.close();
            
        }catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
        }
	}
}
