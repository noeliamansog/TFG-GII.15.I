package es.ubu.inf.tfg.solucion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase Calculos que implementa el resultado de la rentabilidad económica y financiera,
 * el fondo de maniobra y la liquidez y endeudamiento de la empresa.
 * 
 * @author Noelia Manso García
 */
public class Calculos extends Asiento{
	/**
	 * Año del que se quieren realizar los calculos.
	 */
	public int ano;
	/**
	 * Rentabilidad económica.
	 */
	private static double rentEconomica;
	/**
	 * Rentabilidad financiera.
	 */
	private static double rentFinanciera;
	/**
	 * Fondo de maniobra.
	 */
	private static double fondoManiobra;
	/**
	 * Ratio de liquidez.
	 */
	private static double ratioLiquidez;
	/**
	 * Ratio de tesorería.
	 */
	private static double ratioTesoreria;
	/**
	 * Ratio de endeudamiento.
	 */
	private static double ratioEndeudamiento;
	/**
	 * Ratio de autonomia.
	 */
	private static double ratioAutonomia;
	/**
	 * Ratio de calidad.
	 */
	private static double ratioCalidad;
	/**
	 * Fecha con la que calculamos el saldo de las cuentas.
	 */
	private static Calendar fecha;
	
	
	/**
	 * Constructor de la clase Calculos en el que se generan todos los cálculos en el año pasado como parámetro.
	 * @param ano Año en el que se desean realizar los calculos.
	 */
	public Calculos(int ano){
		this.ano=ano;
		rentEconomica = 0;
		rentFinanciera = 0;
		fondoManiobra = 0;
		ratioLiquidez = 0;
		ratioTesoreria = 0;
		ratioEndeudamiento = 0;
		ratioAutonomia = 0;
		ratioCalidad = 0;
		
	
		fecha = Calendar.getInstance();
		fecha.set(ano,11,31);	
		
		//RENTABILIDAD ECONÓMICA Y RENTABILIDAD FINANCIERA
		//1ºAño
		if(ano == Main.anoInicial){
			//RENTABILIDAD ECONÓMICA:
			//RentEconomica = (ExcedenteActividad + 769.IngrFinancieros) / (TotalActivo - 129.Resultado)					
			rentEconomica = ((CuentaResultados.excedenteActividad + CuentaResultados.ingresosF) / (Balance.totalActivo - dameCuenta(12).getSaldo(fecha)))*100;
			
			//RENTABILIDAD FINANCIERA:
			//RentFinanciera = ResultadoTotal / (PN - 129.Resultado)
			rentFinanciera = (CuentaResultados.resultadoTotal / (Balance.valorPatrimonioNeto - dameCuenta(12).getSaldo(fecha)))*100;
					
					
		//Resto años
		}else{
			//RENTABILIDAD ECONÓMICA:
			//RentEconomica = (ExcedenteActividad + 769.IngrFinancieros) / TotalActivo del año anterior
			rentEconomica = (CuentaResultados.excedenteActividad + CuentaResultados.ingresosF) / Main.activoAnoAnterior;
					
			//RENTABILIDAD FINANCIERA:
			//RentFinanciera = ResultadoTotal / PN del año anterior
			rentFinanciera = CuentaResultados.resultadoTotal / Main.pnAnoAnterior;
		}		
					
		//FONDO DE MANIOBRA:
		//FM = ActivoCorriente - PasivoCorriente
		fondoManiobra = Balance.valorActivoCorriente - Balance.valorPasivoCorriente;
		
		//LIQUIDEZ DE LA EMPRESA MEDIANTE RATIOS:
		//Ratio de liquidez general = ActivoCorriente/PasivoCorriente
		//Ratio de tesorería o prueba del ácido = (ActivoCorriente-Mercaderias) / PasivoCorriente
		ratioLiquidez = Balance.valorActivoCorriente / Balance.valorPasivoCorriente;
		ratioTesoreria = (Balance.valorActivoCorriente - dameCuenta(300).getSaldo(fecha)) / Balance.valorPasivoCorriente;
		
		//ENDEUDAMIENTO DE LA EMPRESA MEDIANTE RATIOS:
		//Ratio de endeudamiento general = (PasivoNoCorriente + PasivoCorriente) / TotalPNyPasivo
		//Ratio de autonomía financiera = PN / TotalPNyPasivo
		//Ratio de la calidad de la deuda = PasivoCorriente / (PasivoNoCorriente + PasivoCorriente)
		ratioEndeudamiento = (Balance.valorPasivoNoCorriente + Balance.valorPasivoCorriente) / Balance.totalPNyPasivo;
		ratioAutonomia = Balance.valorPatrimonioNeto / Balance.totalPNyPasivo;
		ratioCalidad = Balance.valorPasivoCorriente / (Balance.valorPasivoNoCorriente + Balance.valorPasivoCorriente);	
		
	}
	
	/**
	 * Función que imprime los calculos en un documento PDF.
	 */
	public void imprimeCalculos(){
	
		Document documento = new Document();
		
		try{	
			File directorio = new File(Main.direccionRuta+"/"+ano);
			if(!directorio.exists()){
				directorio.mkdir();
			}	
			PdfWriter.getInstance(documento, new FileOutputStream(directorio+"/Calculos "+ano+".pdf"));
		
            documento.open();
            documento.addTitle("Calculos"); 
            documento.addAuthor("Noelia Manso García"); 
            
            //OTROS CALCULOS
            
            documento.add(new Paragraph("CALCULOS DEL AÑO "+ano+"\n \n" , FontFactory.getFont("arial",22,Font.BOLD, BaseColor.BLACK)));
            
            //Balance, Cuenta perdidas y ganancias y Tesorería:
            documento.add(new Paragraph("a) El balance de situación, la cuenta de pérdidas y ganancias y el estado de flujos de tesorería se encuentran cada uno de ellos en documentos separados junto a este. \n"));
            documento.add(new Paragraph("\n"));
            
            //Rentabilidad económica y financiera:
            documento.add(new Paragraph("b) Rentabilidad económica: "+ rentEconomica+"%"));
            documento.add(new Paragraph("    Rentabilidad financiera: "+ rentFinanciera +"%\n"));
            documento.add(new Paragraph("\n"));
            
            //Fondo de maniobra
            documento.add(new Paragraph("c) Fondo de maniobra: "+ fondoManiobra+"\n"));
            documento.add(new Paragraph("\n"));
            
            //Liquidez de la empresa mediante ratios
            documento.add(new Paragraph("d) Liquidez de la empresa mediante ratios: "));
            documento.add(new Paragraph("    Ratio de liquidez general: "+ratioLiquidez));
            documento.add(new Paragraph("    Ratio de tesoreria o prueba del ácido: "+ratioTesoreria+"\n"));
            documento.add(new Paragraph("\n"));
            
            //Endeudamiento de la empresa mediante ratio
            documento.add(new Paragraph("e) Endeudamiento de la empresa mediante ratios: "));
            documento.add(new Paragraph("    Ratio de endeudamiento general: "+ratioEndeudamiento));
            documento.add(new Paragraph("    Ratio de autonomía financiera: "+ratioAutonomia));
            documento.add(new Paragraph("    Ratio de la calidad de la deuda: "+ratioCalidad+"\n"));   
            documento.add(new Paragraph("\n"));
            
            documento.add(new Paragraph("Otros datos de interes: \n", FontFactory.getFont("arial",12,Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph("Valor nominal de las acciones: "+Main.valorNominal+"\n"));
            documento.add(new Paragraph("Valor contable de las acciones: "+Main.valorContable+"\n"));
            

            documento.close();
            
            
        }catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
        }
	}
	
}
