package es.ubu.inf.tfg.solucion;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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
 * Clase Tesoreria que implementa la solución de la tesorería del supuesto contable.
 * 
 * @author Noelia Manso García
 */
public class Tesoreria extends Asiento{
	/**
	 * Año del que se desea generar el balance.
	 */
	private int ano;
	
	/**
	 * Lista de anotaciones que pertenecen a cobros.
	 */
	private static ArrayList<Anotacion> cobros;
	/**
	 * Lista de anotaciones que pertenecen a pagos.
	 */
	private static ArrayList<Anotacion> pagos;
	
	/**
	 * Valor de todos los cobros.
	 */
	private static double valorCobros;
	/**
	 * Valor de todos los pagos.
	 */
	private static double valorPagos;
	/**
	 * Saldo total de la tesorería.
	 */
	private static double saldoTesoreria;
	/**
	 * Saldo inicial.
	 */
	private static double saldoInicial;

	
	/**
	 * Contructor de la clase Tesoreria en el que se genera la tesorería del supuesto contable 
	 * en el año pasado como parámetro.
	 * @param ano Año del que se desea generar la tesorería.
	 */
	public Tesoreria(int ano){
		this.ano=ano;

		cobros = new ArrayList<Anotacion>();
		pagos = new ArrayList<Anotacion>();
		
		valorCobros=0;
		valorPagos=0;
		
		Calendar fechaAñoAnterior = Calendar.getInstance();
		fechaAñoAnterior.set(ano-1, 11, 31);
		saldoInicial = dameCuenta(572).getSaldo(fechaAñoAnterior);
		valorCobros=saldoInicial;
		
		Cuenta cuenta = dameCuenta(572);
		  
		//Cobros
		if (!(cuenta.debe.isEmpty())){
			for(int i=0; i<cuenta.debe.size(); i++){
				if (cuenta.debe.get(i).fecha.get(Calendar.YEAR)==ano){
				//if (cuenta.debe.get(i).fecha.before(fechaHasta) && cuenta.debe.get(i).fecha.after(fechaDesde)){
					cobros.add(cuenta.debe.get(i));
					valorCobros += cuenta.debe.get(i).cantidad;
				}
			 }
		}
		//Pagos
		if (!(cuenta.haber.isEmpty())){
			for(int i=0; i<cuenta.haber.size(); i++){
				if (cuenta.haber.get(i).fecha.get(Calendar.YEAR)==ano){
				//if (cuenta.haber.get(i).fecha.before(fechaHasta) && cuenta.haber.get(i).fecha.after(fechaDesde)){
					pagos.add(cuenta.haber.get(i));
					valorPagos += cuenta.haber.get(i).cantidad;
				}
			}
		}
		saldoTesoreria = valorCobros-valorPagos;
	}
	
	/**
	 * Función que imprime la tesorería en un documento PDF.
	 */
	public void imprimeTesoreria(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		Collections.sort(cobros);
		Collections.sort(pagos);
	
		Document documento = new Document();
		
		try{		
			File directorio = new File(Main.direccionRuta+"/"+ano);
			if(!directorio.exists()){
				directorio.mkdir();
			}	
			PdfWriter.getInstance(documento, new FileOutputStream(directorio+"/Tesorería"+ano+".pdf"));
			 
            documento.open();
            documento.addTitle("Tesorería"); 
            documento.addAuthor("Noelia Manso García"); 
            
            //TESORERIA
            PdfPTable tesoreria = new PdfPTable(1);
            
            PdfPCell celda =new PdfPCell (new Paragraph("TESORERÍA  \n del año "+ano+"" , FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
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
            
            Calendar fech = Calendar.getInstance();
            fech.set(ano, 0, 1);
            //tablaCobros.addCell(formateador.format(fechaDesde.getTime()));
            tablaCobros.addCell(formateador.format(fech.getTime()));
            tablaCobros.addCell("Saldo inicial");
            tablaCobros.addCell(Math.round(saldoInicial)*100/100+"€");
            for(int i=0; i<cobros.size(); i++){
         	   Calendar fechaCobros= cobros.get(i).fecha;
         	   tablaCobros.addCell(formateador.format(fechaCobros.getTime()));
         	   tablaCobros.addCell(cobros.get(i).nombre);
         	   tablaCobros.addCell(Math.round(cobros.get(i).cantidad)*100/100+"€");
             }
            
            
            //Pagos
            PdfPTable tablaPagos = new PdfPTable(3);
            
            PdfPCell celdaPagos =new PdfPCell (new Paragraph("PAGOS", FontFactory.getFont("arial",14,Font.BOLD, BaseColor.BLACK)));
            celdaPagos.setColspan(3);
            celdaPagos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaPagos.setPadding (10.0f);
            celdaPagos.setBackgroundColor(BaseColor.GRAY);
            tablaCobros.addCell(celdaPagos);
            
            for(int i=0; i<pagos.size(); i++){
         	   Calendar fechaPagos= pagos.get(i).fecha;
         	   tablaPagos.addCell(formateador.format(fechaPagos.getTime()));
         	   tablaPagos.addCell(pagos.get(i).nombre);
         	   tablaPagos.addCell(Math.round(pagos.get(i).cantidad)*100/100+"€");
             } 
            
            
            //SALDO TOTAL
           	PdfPTable saldoTotal = new PdfPTable(3);
            PdfPCell total = new PdfPCell (new Paragraph("SALDO TOTAL TESORERÍA:", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
            total.setColspan(2);
            total.setPadding (10.0f);
            total.setBackgroundColor(BaseColor.YELLOW);
           	PdfPCell calculoTotal = new PdfPCell (new Paragraph(Math.round(saldoTesoreria)*100/100 +"€", FontFactory.getFont("arial",10,Font.BOLD, BaseColor.BLACK)));
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
