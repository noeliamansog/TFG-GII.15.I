package es.ubu.inf.tfg.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.ubu.inf.tfg.asientosContables.*;
import es.ubu.inf.tfg.asientosContables.sinIVA.PagoDeudasHaciendaSinIVA;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.PagoDeudasHaciendaSinRetenciones;
import es.ubu.inf.tfg.doc.*;
import es.ubu.inf.tfg.tablas.Balance;
import es.ubu.inf.tfg.tablas.CuentaResultados;
import es.ubu.inf.tfg.tablas.Tesoreria;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.AportacionPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraMercaderiasPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraPIPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraSWPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.DividendosPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.IntangibleNoAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.InteresPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.InventarioPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.MaterialAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.MaterialNoAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.NuevoSocioPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.PrestamoPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.SueldoEmpleadosPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.SueldoIngenieroPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.VentaMercaderiasPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.VentaProyectoPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.CompraMercaderiasSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.VentaMercaderiasSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.VentaProyectoSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.DividendosSinRetPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.InteresSinRetPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.SueldoEmpleadosSinRetPanel;

public class Main {
	/*public static double impuestoSociedades = 30;
	public static double IVA = 10;
	public static double numeroSocios = 2;
	public static double numAcciones = numEmpleados;
	public static boolean enunciadoConCuentas = true;
	public static boolean conIVA = true;
	public static boolean conRetenciones = true;
	public static String direccionRuta = "/Users/noelia/Desktop";
	public static int anoInicio = 2016;
	public static int anoFin = anoInicio;*/
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static double IVA;
	public static double impuestoSociedades;
	public static int anoInicial;
	public static int anoFinal = anoInicial;
	public static int anoLimite;
	public static double numeroSocios;
	
	public static boolean conIVA = true;
	public static boolean conRetenciones = true;
	public static boolean enunciadoConCuentas = true;
	
	public static String direccionRuta = "";
	
	public JFrame frmSupuesto;
	
	public JFrame ventanaInicial;
	public JLabel textoVentanaInicial;
	public JLabel ivaText;
	public JComboBox<String> desplegable1;
	public JLabel introduceIva;
	public JTextField cuadroIntroduceIva;
	public JLabel retencionText;
	public JComboBox<String> desplegable2;
	public JLabel introduceImpuestoSoc;
	public JTextField cuadroIntroduceImpuestoSoc;
	public JLabel enunciadoCuentasText;
	public JComboBox<String> desplegable3;
	public JTextField cuadroIntroduceAno;
	public JButton botonVentanaInicial;
	public boolean comprobarIVA = true;
	
	public JMenuBar menuBar;
	public JMenuItem menuNuevo;
	public JMenu menuArchivo;
	public JMenu menuAyuda;
	public JMenuItem menuAcercaDe;
	public JMenuItem menuWeb;
	
	public JPanel controlPanelIzq;
	public JPanel panelBox;
	public JButton añadirButtonMas;
	public JComboBox<String> añadirBox;
	public Component añadirDerechoStrut;
	public Component añadirIzquierdoStrut;
	public static JPanel contenedorPanel;
	public JPanel panelAsientos;
	public JScrollPane contenedorScroll1;
	
	private JPanel controlPanelDrch;
	private JPanel panelBotonGenerar;
	private JButton añadirButtonGenerar;
	public static JTextPane panelEnunciado;
	public static String textoEnunciado = "";
	public JScrollPane contenedorScroll2;
	
	public JFrame ventanaFinal;
	public JLabel textoVentanaFinal;
	public JComboBox<String> desplegableCambioAnoLimite;
	public JLabel introduceAnoLimite;
	public JTextField cuadroIntroduceAnoLimite;
	public JButton botonVentanaFinal;
	public boolean comprobarDesplegable = false;
	public boolean comprobarAnoFinal = false;
	
	public static AportacionPanel panelAportacion = null;
	public static boolean aportacionEjecutada = false;
	public static Calendar fechaAportacion;
	public static boolean ejecucionAlgunAsiento = false;
	
	public List<AsientoPanel<?>> panelesAsiento = new ArrayList<>();
	
	public static void main(String args[]) {
		/*
		ArrayList<ArrayList<Enunciado>> todosEnunciados = new ArrayList<ArrayList<Enunciado>>();	
		ArrayList<Calendar> todasFechas = new ArrayList<Calendar>();
		
		Dividendos dividendos = null;
		Dividendos dividendos2 = null;
		// ... para todos los años... 
		DividendosSinRetenciones dividendosSinReteneciones = null;
		DividendosSinRetenciones dividendosSinReteneciones2 = null;

		

		//////////////////////////
		////	 1º AÑO 	/////	
		////////////////////////
		
		//APORTACIÓN INICIAL
		Calendar fechaAportacion = Calendar.getInstance();
		double [] inputsAportacion = {numeroSocios, 50000};
		AportacionInicial aportacion = new AportacionInicial(fechaAportacion, inputsAportacion, enunciadoConCuentas);
		todosEnunciados.add(aportacion.enunciados);
		todasFechas.add(fechaAportacion);
		
		//PRESTAMO		
		Calendar fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.add(Calendar.DAY_OF_YEAR, +1);
		double [] inputsPrestamo = {60000, 0, 1, 10, 5};
		Prestamo prestamo = new Prestamo(fechaPrestamo, inputsPrestamo, enunciadoConCuentas);
		todosEnunciados.add(prestamo.enunciados);
		todasFechas.add(fechaPrestamo);
	
		//COMPRA_MATERIAL_NO_AMORTIZABLE
		Calendar fechaCompraMatNoAmort = Calendar.getInstance();
		fechaCompraMatNoAmort.add(Calendar.DAY_OF_YEAR, +2);
		double [] inputsMaterialNoAmortizable = {0, 120000, 60000, 2};
		CompraMaterialNoAmortizable materialNoAmortizable = new CompraMaterialNoAmortizable(fechaCompraMatNoAmort, inputsMaterialNoAmortizable, enunciadoConCuentas);
		todosEnunciados.add(materialNoAmortizable.enunciados);
		todasFechas.add(fechaCompraMatNoAmort);
		
		//COMPRA_MATERIAL_AMORTIZABLE
		Calendar fechaCompraMatAmort = Calendar.getInstance();
		fechaCompraMatAmort.add(Calendar.DAY_OF_YEAR, +3);
		double [] inputsMaterialAmortizable = {1, 8000, 30, 4};
		CompraMaterialAmortizable materialAmortizable = new CompraMaterialAmortizable(fechaCompraMatAmort, inputsMaterialAmortizable, enunciadoConCuentas);
		todosEnunciados.add(materialAmortizable.enunciados);
		todasFechas.add(fechaCompraMatAmort);
		
		//COMPRA_INTANGIBLE_NO_AMORTIZABLE
		Calendar fechaCompraInNoAmort = Calendar.getInstance();
		fechaCompraInNoAmort.add(Calendar.DAY_OF_YEAR, +4);
		double [] inputsIntangibleNoAmortizable = {1000};
		CompraIntangibleNoAmortizable intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fechaCompraInNoAmort, inputsIntangibleNoAmortizable, enunciadoConCuentas);
		todosEnunciados.add(intangibleNoAmortizable.enunciados);
		todasFechas.add(fechaCompraInNoAmort);
		
		//COMPRA_SOFTWARE_AMORTIZABLE
		Calendar fechaCompraSW = Calendar.getInstance();
		fechaCompraSW.add(Calendar.DAY_OF_YEAR, +5);
		double [] inputsSoftwareAmortizable = {3000, 60, 3};
		CompraSoftwareAmortizable softwareAmortizable = new CompraSoftwareAmortizable(fechaCompraSW, inputsSoftwareAmortizable, enunciadoConCuentas);
		todosEnunciados.add(softwareAmortizable.enunciados);
		todasFechas.add(fechaCompraSW);
		
		//COMPRA_PROPIEDAD_INDUSTRIAL_AMORTIZABLE
		Calendar fechaCompraPI = Calendar.getInstance();
		fechaCompraPI.add(Calendar.DAY_OF_YEAR, +6);
		double [] inputsPropiedadIndustrialAmortizable = {1500, 90, 3};
		CompraPropiedadIndustrialaAmortizable propiedadIndustrialAmortizable = new CompraPropiedadIndustrialaAmortizable(fechaCompraPI, inputsPropiedadIndustrialAmortizable, enunciadoConCuentas);
		todosEnunciados.add(propiedadIndustrialAmortizable.enunciados);
		todasFechas.add(fechaCompraPI);
		
		//COMPRA_MERCADERIAS
		Calendar fechaCompraMercaderias = Calendar.getInstance();
		fechaCompraMercaderias.add(Calendar.DAY_OF_YEAR, +7);
		if(conIVA){
			//Con IVA
			double [] inputsCompraMercaderias = {20000, IVA, 60};
			CompraMercaderias compraMercaderias = new CompraMercaderias(fechaCompraMercaderias, inputsCompraMercaderias, enunciadoConCuentas);
			todosEnunciados.add(compraMercaderias.enunciados);
			todasFechas.add(fechaCompraMercaderias);
		}else{
			//Sin IVA
			double [] inputsCompraMercaderiasSinIVA = {20000, 60};
			CompraMercaderiasSinIVA compraMercaderiasSinIVA = new CompraMercaderiasSinIVA(fechaCompraMercaderias, inputsCompraMercaderiasSinIVA, enunciadoConCuentas);
			todosEnunciados.add(compraMercaderiasSinIVA.enunciados);
			todasFechas.add(fechaCompraMercaderias);
		}	
		
		//VENTA_MERCADERIAS
		Calendar fechaVentaMercaderias = Calendar.getInstance();
		fechaVentaMercaderias.add(Calendar.DAY_OF_YEAR, +8);
		if(conIVA){
			//Con IVA
			double [] inputsVentaMercaderias = {30000, IVA, 30};
			VentaMercaderias ventaMercaderias = new VentaMercaderias(fechaVentaMercaderias, inputsVentaMercaderias, enunciadoConCuentas);
			todosEnunciados.add(ventaMercaderias.enunciados);
			todasFechas.add(fechaVentaMercaderias);
		}else{
			//Sin IVA
			double [] inputsVentaMercaderiasSinIVA = {30000, 30};
			VentaMercaderiasSinIVA ventaMercaderiasSinIVA = new VentaMercaderiasSinIVA(fechaVentaMercaderias, inputsVentaMercaderiasSinIVA, enunciadoConCuentas);
			todosEnunciados.add(ventaMercaderiasSinIVA.enunciados);
			todasFechas.add(fechaVentaMercaderias);
		}
		
		//VENTA_PROYECTO
		Calendar fechaVentaProy = Calendar.getInstance();
		fechaVentaProy.add(Calendar.DAY_OF_YEAR, +9);
		if(conIVA){
			//Con IVA
			double [] inputsVentaProyecto = {200000, IVA, 30};
			VentaProyecto ventaProyecto = new VentaProyecto(fechaVentaProy, inputsVentaProyecto, enunciadoConCuentas);
			todosEnunciados.add(ventaProyecto.enunciados);
			todasFechas.add(fechaVentaProy);
		}else{
			//Sin IVA	
			double [] inputsVentaProyectoSinIVA = {200000, 30};
			VentaProyectoSinIVA ventaProyectoSinIVA = new VentaProyectoSinIVA(fechaVentaProy, inputsVentaProyectoSinIVA, enunciadoConCuentas);
			todosEnunciados.add(ventaProyectoSinIVA.enunciados);
			todasFechas.add(fechaVentaProy);
		}	
		
		//SUELDOS_EMPLEADOS
		Calendar fechaSueldoEmp = Calendar.getInstance();
		fechaSueldoEmp.add(Calendar.DAY_OF_YEAR, +10);
		if(conRetenciones){
			double [] inputsSueldoEmpleado = {numeroSocios, 10000, 3350, 10, 5};
			SueldosEmpleados sueldoEmpleado = new SueldosEmpleados(fechaSueldoEmp, inputsSueldoEmpleado, enunciadoConCuentas);
			todosEnunciados.add(sueldoEmpleado.enunciados);
			todasFechas.add(fechaSueldoEmp);
		}else{
			double [] inputsSueldoEmpleadoSinR = {numeroSocios, 10000, 3350};
			SueldosEmpleadosSinRetenciones sueldoEmpleadoSinRetenciones = new SueldosEmpleadosSinRetenciones(fechaSueldoEmp, inputsSueldoEmpleadoSinR, enunciadoConCuentas);
			todosEnunciados.add(sueldoEmpleadoSinRetenciones.enunciados);
			todasFechas.add(fechaSueldoEmp);
		}
		
		//SUELDO_INGENIERO
		Calendar fechaSueldoIng = Calendar.getInstance();
		fechaSueldoIng.add(Calendar.DAY_OF_YEAR, +11);
		double [] inputsSueldoIngeniero = {30000, 30};
		SueldoIngeniero sueldoIngeniero = new SueldoIngeniero(fechaSueldoIng, inputsSueldoIngeniero, enunciadoConCuentas);
		todosEnunciados.add(sueldoIngeniero.enunciados);
		todasFechas.add(fechaSueldoIng);
		
		//INTERESES
		Calendar fechaIntereses = Calendar.getInstance();
		fechaIntereses.add(Calendar.DAY_OF_YEAR, +12);
		if(conRetenciones){
			double [] inputsIntereses = {300, 80};
			Interes intereses = new Interes(fechaIntereses, inputsIntereses, enunciadoConCuentas);
			todosEnunciados.add(intereses.enunciados);
			todasFechas.add(fechaIntereses);
		}else{
			double [] inputsInteresesSinR = {300};
			InteresSinRetenciones interesesSinRetenciones = new InteresSinRetenciones(fechaIntereses, inputsInteresesSinR, enunciadoConCuentas);
			todosEnunciados.add(interesesSinRetenciones.enunciados);
			todasFechas.add(fechaIntereses);
		}
		
		//NUEVOS_SOCIOS
		Calendar fechaNuevosSoc = Calendar.getInstance();
		fechaNuevosSoc.add(Calendar.DAY_OF_YEAR, +13);
		double [] inputsNuevoSocio = {60000, numeroSocios};
		NuevoSocio nuevoSocio = new NuevoSocio(fechaNuevosSoc, inputsNuevoSocio, enunciadoConCuentas);
		todosEnunciados.add(nuevoSocio.enunciados);
		todasFechas.add(fechaNuevosSoc);
		
		//INVENTARIO
		Calendar fechaInventario = Calendar.getInstance();
		fechaInventario.set(anoInicial,11,31);
		double [] inputsInventario = {2000};
		Inventario inventario = new Inventario (fechaInventario, inputsInventario, enunciadoConCuentas);
		todosEnunciados.add(inventario.enunciados);
		todasFechas.add(fechaInventario);
			
		//IVA
		if (conIVA){
			Calendar fechaIVA = Calendar.getInstance();
			fechaIVA.set(2016,11,31);
			IVA iva = new IVA (fechaIVA, null, enunciadoConCuentas);
			todosEnunciados.add(iva.enunciados);
			todasFechas.add(fechaIVA);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados = new CuentaResultados (2016);
		cuentaResultados.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria = new Tesoreria (2016);
		tesoreria.imprimeTesoreria();
		
		//BALANCE
		Balance balance = new Balance(2016);
		balance.imprimeBalance();
		
		
		
		//////////////////////////
		////	 2º AÑO 	/////	
		////////////////////////	
		
		//PAGO DEUDAS HACIENDA
		Calendar fechaDeudasHP = Calendar.getInstance();
		fechaDeudasHP.add(Calendar.YEAR, +1);
		if(conIVA && conRetenciones){
			PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fechaDeudasHP, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHacienda.enunciados);
			todasFechas.add(fechaDeudasHP);
		}if(conIVA==false && conRetenciones){
			PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA = new PagoDeudasHaciendaSinIVA(fechaDeudasHP, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHaciendaSinIVA.enunciados);
			todasFechas.add(fechaDeudasHP);	
		}if(conIVA && conRetenciones==false){
			PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHaciendaSinRetenciones.enunciados);
			todasFechas.add(fechaDeudasHP);	
		}
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		if(conRetenciones){
			Calendar fechaDeudasSS = Calendar.getInstance();
			fechaDeudasSS.add(Calendar.YEAR, +1);
			PagoDeudasSS deudasSS = new PagoDeudasSS(fechaDeudasSS, null, enunciadoConCuentas);
			todosEnunciados.add(deudasSS.enunciados); 
			todasFechas.add(fechaDeudasSS);
		}
		
		//DIVIDENDOS
		Calendar fechaDividendos = Calendar.getInstance();
		fechaDividendos.add(Calendar.YEAR, +1);
		if(conRetenciones){
			double [] inputsDividendos = {50, 20};
			dividendos = new Dividendos (fechaDividendos, inputsDividendos, enunciadoConCuentas);
			todosEnunciados.add(dividendos.enunciados);
			todasFechas.add(fechaDividendos);
		}else{
			double [] inputsDividendosSinR = {50};
			dividendosSinReteneciones = new DividendosSinRetenciones (fechaDividendos, inputsDividendosSinR, enunciadoConCuentas);
			todosEnunciados.add(dividendosSinReteneciones.enunciados);
			todasFechas.add(fechaDividendos);
		}
		
		//NO_DIVIDENDOS
		if(dividendos == null && dividendosSinReteneciones==null){
			new NoDividendos (Calendar.getInstance(), null, false);
		}
		
		//IVA
		if (conIVA){
			Calendar fechaIVA2 = Calendar.getInstance();
			fechaIVA2.set(2017,11,31);
			IVA iva2 = new IVA (fechaIVA2, null, enunciadoConCuentas);
			todosEnunciados.add(iva2.enunciados);
			todasFechas.add(fechaIVA2);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados2 = new CuentaResultados (2017);
		cuentaResultados2.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria2 = new Tesoreria (2017);
		tesoreria2.imprimeTesoreria();
		
		//BALANCE
		Balance balance2 = new Balance(2017);
		balance2.imprimeBalance();
			
		//////////////////////////
		////	 3º AÑO 	/////	
		////////////////////////	
		
		//PAGO DEUDAS HACIENDA
		Calendar fechaDeudasHP2 = Calendar.getInstance();
		fechaDeudasHP2.add(Calendar.YEAR, +2);
		if(conIVA && conRetenciones){
			PagoDeudasHacienda deudasHacienda2 = new PagoDeudasHacienda(fechaDeudasHP2, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHacienda2.enunciados);
			todasFechas.add(fechaDeudasHP2);
		}if(conIVA==false && conRetenciones){
			PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA2 = new PagoDeudasHaciendaSinIVA(fechaDeudasHP2, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHaciendaSinIVA2.enunciados);
			todasFechas.add(fechaDeudasHP2);	
		}if(conIVA && conRetenciones==false){
			PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones2 = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP2, null, enunciadoConCuentas);
			todosEnunciados.add(deudasHaciendaSinRetenciones2.enunciados);
			todasFechas.add(fechaDeudasHP2);	
		}
		 
		//PAGO DEUDAS SEGURIDAD SOCIAL
		if(conRetenciones){
			Calendar fechaDeudasSS2 = Calendar.getInstance();
			fechaDeudasSS2.add(Calendar.YEAR, +2);
			PagoDeudasSS deudasSS2 = new PagoDeudasSS(fechaDeudasSS2, null, enunciadoConCuentas);
			todosEnunciados.add(deudasSS2.enunciados); 
			todasFechas.add(fechaDeudasSS2);
		}
		
		//DIVIDENDOS
		Calendar fechaDividendos2 = Calendar.getInstance();
		fechaDividendos2.add(Calendar.YEAR, +2);
		if(conRetenciones){
			double [] inputsDividendos2 = {50, 20};
			dividendos2 = new Dividendos (fechaDividendos2, inputsDividendos2, enunciadoConCuentas);
			todosEnunciados.add(dividendos2.enunciados);
			todasFechas.add(fechaDividendos2);
		}else{
			double [] inputsDividendosSinR2 = {50};
			dividendosSinReteneciones2 = new DividendosSinRetenciones (fechaDividendos2, inputsDividendosSinR2, enunciadoConCuentas);
			todosEnunciados.add(dividendosSinReteneciones2.enunciados);
			todasFechas.add(fechaDividendos2);
		}
		
		//NO_DIVIDENDOS
		if(dividendos2 == null && dividendosSinReteneciones2==null){
			new NoDividendos (Calendar.getInstance(), null, false);
		}
		
		//IVA
		if (conIVA){
			Calendar fechaIVA3 = Calendar.getInstance();
			fechaIVA3.set(2018,11,31);
			IVA iva2 = new IVA (fechaIVA3, null, enunciadoConCuentas);
			todosEnunciados.add(iva2.enunciados);
			todasFechas.add(fechaIVA3);
		}
		
		//CUENTA DE PERDIDAS Y GANANCIAS
		CuentaResultados cuentaResultados3 = new CuentaResultados (2018);
		cuentaResultados3.imprimeCuentaResultados();
		
		//TESORERIA
		Tesoreria tesoreria3 = new Tesoreria (2018);
		tesoreria3.imprimeTesoreria();
		
		//BALANCE
		Balance balance3 = new Balance(2018);
		balance3.imprimeBalance();
		
		
			
		//ENUNCIADO
		imprimeEnunciados(todosEnunciados);
		
		*/
		log.info("Aplicación iniciada");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					log.error("Error al iniciar la aplicación", e);
				}
			}
		});
	}
	
	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.error("Error estableciendo el look and feel", e);
		}
		
		initVentanaInicial();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		initPanel();
		initMenuBar();
		initPanelIzq();
		initPanelDrch();
	}

	public void initVentanaInicial(){
		this.ventanaInicial = new JFrame();   
        
        this.ventanaInicial.setTitle("Configuaración antes de empezar");
        this.ventanaInicial.setSize(400, 430);
        this.ventanaInicial.setLocationRelativeTo(null);
        ventanaInicial.setLayout(null); 
    
        this.textoVentanaInicial = new JLabel();
        this.textoVentanaInicial.setText("<html><p align=center><font face=arial, serif size=4><b><u> ¿COMO DESEA REALIZAR EL SUPUESTO CONTABLE?</u></b></html>");
        this.textoVentanaInicial.setBounds(10, 7, 400, 50);
        this.ventanaInicial.add(this.textoVentanaInicial);		
        
        //IVA
        this.ivaText = new JLabel();
        this.ivaText.setText("¿Con IVA?");
        this.ivaText.setBounds(10, 60, 65, 50);
        this.ventanaInicial.add(this.ivaText);
        
        this.desplegable1 = new JComboBox<>();
		this.desplegable1.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable1.setBounds(85, 70, 50, 30);
        this.ventanaInicial.add(this.desplegable1);
        this.desplegable1.addItemListener(new DesplegableItemListener()); 
        
        this.introduceIva = new JLabel();
        this.introduceIva.setText("Introduce el % de IVA: ");
        this.introduceIva.setBounds(170, 60, 200, 50);
        this.ventanaInicial.add(this.introduceIva);
        
        this.cuadroIntroduceIva = new JTextField(4);
        this.cuadroIntroduceIva.setBounds(315, 75, 30, 20);
        this.ventanaInicial.add(this.cuadroIntroduceIva);
       
        //Retenciones
        this.retencionText = new JLabel();
        this.retencionText.setText("¿Con Retenciones?");
        this.retencionText.setBounds(10, 110, 150, 50);
        this.ventanaInicial.add(this.retencionText);
        
        this.desplegable2 = new JComboBox<>();
		this.desplegable2.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable2.setBounds(135, 120, 50, 30);
        this.ventanaInicial.add(this.desplegable2);
        this.desplegable2.addItemListener(new DesplegableItemListener()); 
        
        
        //Enunciado con cuentas
        this.enunciadoCuentasText = new JLabel();
        this.enunciadoCuentasText.setText("<html>¿Deseas que en el enunciado del supuesto aparezcan las cuentas del PGC?</html>");
        this.enunciadoCuentasText.setBounds(10, 160, 380, 50);
        this.ventanaInicial.add(this.enunciadoCuentasText);
        
        this.desplegable3 = new JComboBox<>();
		this.desplegable3.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable3.setBounds(20, 210, 50, 30);
        this.ventanaInicial.add(this.desplegable3);
        this.desplegable3.addItemListener(new DesplegableItemListener()); 
        
        //Impuesto Sociedad
        this.introduceImpuestoSoc = new JLabel();
        this.introduceImpuestoSoc.setText("Introduce el % del impuesto de sociedad: ");
        this.introduceImpuestoSoc.setBounds(10, 250, 300, 50);
        this.ventanaInicial.add(this.introduceImpuestoSoc);
        
        this.cuadroIntroduceImpuestoSoc = new JTextField(4);
        this.cuadroIntroduceImpuestoSoc.setBounds(275, 265, 30, 20);
        this.ventanaInicial.add(this.cuadroIntroduceImpuestoSoc);
        
        
        //Año inicio
        this.enunciadoCuentasText = new JLabel();
        this.enunciadoCuentasText.setText("<html>Introduce el año inicial:</html>");
        this.enunciadoCuentasText.setBounds(10, 300, 390, 50);
        this.ventanaInicial.add(this.enunciadoCuentasText);
        
        this.cuadroIntroduceAno =  new JTextField(4);
        this.cuadroIntroduceAno.setBounds(170, 315, 50, 20);
        this.ventanaInicial.add(this.cuadroIntroduceAno);
        
        //Boton aceptar
        this.botonVentanaInicial = new JButton();
        this.botonVentanaInicial.setText("Aceptar"); 
        this.botonVentanaInicial.setBounds(230, 350, 150, 30);
        this.ventanaInicial.add(this.botonVentanaInicial);
        this.botonVentanaInicial.addActionListener (new BotonAceptarActionListener()); 
        
        this.ventanaInicial.setVisible(true);		
	}
	
	public void initPanel(){
		this.frmSupuesto = new JFrame();
		this.frmSupuesto.setTitle("Supuesto contable");
		this.frmSupuesto.setBounds(100, 100, 1150, 900);
		this.frmSupuesto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSupuesto.setVisible(true);
	}
		
	public void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.frmSupuesto.getContentPane().add(this.menuBar, BorderLayout.NORTH);

		this.menuArchivo = new JMenu("Archivo");
		this.menuBar.add(this.menuArchivo);

		this.menuAyuda = new JMenu("Ayuda");
		this.menuBar.add(this.menuAyuda);

		this.menuWeb = new JMenuItem("Página web");
		this.menuWeb.addActionListener(new MenuWebActionListener());
		this.menuAyuda.add(this.menuWeb);

		this.menuAcercaDe = new JMenuItem("Acerca de");
		this.menuAcercaDe.addActionListener(new MenuAcercaDeActionListener());
		this.menuAyuda.add(this.menuAcercaDe);

		this.menuNuevo = new JMenuItem("Documento en blanco");
		this.menuNuevo.addActionListener(new MenuNuevoActionListener());
		this.menuArchivo.add(this.menuNuevo);
	}

	public void initPanelIzq() { 
		//Panel control izquierda
		this.controlPanelIzq = new JPanel();
		this.controlPanelIzq.setBorder(null);
		this.frmSupuesto.getContentPane().add(this.controlPanelIzq, BorderLayout.WEST);
		this.controlPanelIzq.setLayout(new BorderLayout(0, 0));

		this.contenedorScroll1 = new JScrollPane();
		this.contenedorScroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.controlPanelIzq.add(this.contenedorScroll1, BorderLayout.CENTER);

		//Panel de la izquierda para los asientos
		this.panelAsientos = new JPanel();
		this.panelAsientos.setBorder(null);
		this.contenedorScroll1.add(this.panelAsientos);
		this.contenedorScroll1.setViewportView(this.panelAsientos);

		//Panel de la izquierda
		contenedorPanel = new JPanel();
		contenedorPanel.setBorder(null);
		contenedorPanel.setLayout(new BoxLayout(contenedorPanel, BoxLayout.Y_AXIS));
		this.panelAsientos.add(contenedorPanel);
		
		//Panel para el despliegue
		this.panelBox = new JPanel();
		this.controlPanelIzq.add(this.panelBox, BorderLayout.SOUTH);

		this.añadirButtonMas = new JButton("+");
		this.añadirButtonMas.addActionListener(new AddButtonActionListener());

		this.añadirIzquierdoStrut = Box.createHorizontalStrut(110);
		this.panelBox.add(this.añadirIzquierdoStrut);
		this.panelBox.add(this.añadirButtonMas);

		this.añadirBox = new JComboBox<>();
		this.añadirBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Aportación inicial", "Préstamo", 
				"Compra material no amortizable", "Compra material amortizable", "Compra intangible no amortizable", 
				"Compra software amortizable", "Compra propiedad industrial amortizable", "Compra mercaderias", 
				"Venta mercaderías", "Venta proyecto", "Sueldos y salarios", "Sueldo ingeniero", "Intereses", "Nuevo socio", 
				"Inventario", "Dividendos" }));
		
		this.panelBox.add(this.añadirBox);

		this.añadirDerechoStrut = Box.createHorizontalStrut(110);
		this.panelBox.add(this.añadirDerechoStrut);	
	}
	
	private void initPanelDrch() {
		//Panel control derecha
		this.controlPanelDrch = new JPanel();
		this.controlPanelDrch.setBorder(null);
		this.frmSupuesto.getContentPane().add(this.controlPanelDrch,BorderLayout.CENTER);
		this.controlPanelDrch.setLayout(new BorderLayout(0, 0));
		
		
		this.contenedorScroll2 = new JScrollPane();
		this.contenedorScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.controlPanelDrch.add(this.contenedorScroll2, BorderLayout.CENTER);
		
		//Panel derecha texto
		panelEnunciado = new JTextPane();
		panelEnunciado.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
		
		panelEnunciado.setEditable(false);
		
		panelEnunciado.setMargin(new Insets (5,5,5,5));
	
		panelEnunciado.setContentType("text/html");
		textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciado supuesto contable<br><br></b></p>";
		panelEnunciado.setText(textoEnunciado);
		
		this.contenedorScroll2.add(panelEnunciado);
		this.contenedorScroll2.setViewportView(panelEnunciado);
		
		
		//Panel para botón generar
		this.panelBotonGenerar = new JPanel();
		this.controlPanelDrch.add(this.panelBotonGenerar, BorderLayout.SOUTH);

		this.añadirButtonGenerar = new JButton("Generar solución");
		this.panelBotonGenerar.add(this.añadirButtonGenerar);
		this.añadirButtonGenerar.addActionListener(new BotonGenerarSolucionActionListener());

	}
	
	private void initVentanaFinal() {
		
		this.ventanaFinal = new JFrame();   
        
        this.ventanaFinal.setTitle("Configuaración de la generación de tablas");
        this.ventanaFinal.setSize(450, 250);
        this.ventanaFinal.setLocationRelativeTo(null);
        ventanaFinal.setLayout(null); 
    
        this.textoVentanaFinal = new JLabel();
        if(anoInicial == anoFinal){
        	this.textoVentanaFinal.setText("<html><b>Se generará en el año "+anoInicial+" las tablas correspondientes "
        			+ "al Balance, Cuenta de perdidas y ganancias y Tesorería de este año.</b></html>");
        	this.textoVentanaFinal.setBounds(10, 5, 430, 100);
            this.ventanaFinal.add(this.textoVentanaFinal);
        	
        }else{
        	this.textoVentanaFinal.setText("<html><b>Se generará, por cada año (desde "+anoInicial+" hasta "+ anoFinal +")"
        			+ " las tablas correspondientes al Balance, Cuenta de perdidas y ganancias y Tesorería. <br> "
        			+ "¿Desea modificar el año limite para la generación de estas tablas?</b></html>");
        	this.textoVentanaFinal.setBounds(10, 5, 430, 100);
            this.ventanaFinal.add(this.textoVentanaFinal);
        
            this.desplegableCambioAnoLimite = new JComboBox<>();
            this.desplegableCambioAnoLimite.setModel(new DefaultComboBoxModel<String>(new String[] {"No", "Si"}));
            this.desplegableCambioAnoLimite.setBounds(20, 95, 50, 30);
            this.ventanaFinal.add(this.desplegableCambioAnoLimite);
            this.desplegableCambioAnoLimite.addItemListener(new DesplegableItemListener()); 
        
            this.introduceAnoLimite = new JLabel();
        	this.introduceAnoLimite.setText("Introduce hasta que año desea generen las tablas: ");
        	this.introduceAnoLimite.setBounds(20, 120, 400, 50);
        	this.ventanaFinal.add(this.introduceAnoLimite);
        	this.introduceAnoLimite.setVisible(false);
	
        	this.cuadroIntroduceAnoLimite = new JTextField(4);
        	this.cuadroIntroduceAnoLimite.setBounds(20, 160, 40, 25);
        	this.ventanaFinal.add(this.cuadroIntroduceAnoLimite);
        	this.cuadroIntroduceAnoLimite.setVisible(false);
        	
        	comprobarDesplegable = true;
        }
       
        
        //Boton aceptar
        this.botonVentanaFinal = new JButton();
        this.botonVentanaFinal.setText("Aceptar"); 
        this.botonVentanaFinal.setBounds(230, 190, 170, 30);
        this.ventanaFinal.add(this.botonVentanaFinal);
        this.botonVentanaFinal.addActionListener (new BotonAceptarActionListener()); 
        
        this.ventanaFinal.setVisible(true);		
		
	}

	
	public void añadeAsiento(String asiento){
		switch (asiento){
			
			case "AportaciónInicial":
							if (panelAportacion==null){
									panelAportacion = new AportacionPanel();
									contenedorPanel.add(panelAportacion);
									panelesAsiento.add(panelAportacion);
									contenedorPanel.revalidate();
							}else{
								JOptionPane.showMessageDialog(null, "Solo puede haber una aportación inicial en el ejercicio");
							}
							break;
			case "Prestamo":  	PrestamoPanel panelPrestamo = new PrestamoPanel();
								contenedorPanel.add(panelPrestamo);
								panelesAsiento.add(panelPrestamo);
								contenedorPanel.revalidate();
								break;
			case "CompraMaterialNoAmortizable":  	MaterialNoAmortizablePanel panelMatNoAmort = new MaterialNoAmortizablePanel();
													contenedorPanel.add(panelMatNoAmort);
													panelesAsiento.add(panelMatNoAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraMaterialAmortizable":   	MaterialAmortizablePanel panelMatAmort = new MaterialAmortizablePanel();								
													contenedorPanel.add(panelMatAmort);
													panelesAsiento.add(panelMatAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraIntangibleNoAmortizable": 	IntangibleNoAmortizablePanel panelIntNoAmort = new IntangibleNoAmortizablePanel();
													contenedorPanel.add(panelIntNoAmort);
													panelesAsiento.add(panelIntNoAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraSWAmortizable": CompraSWPanel panelCompraSW = new CompraSWPanel();
										contenedorPanel.add(panelCompraSW);
										panelesAsiento.add(panelCompraSW);
										contenedorPanel.revalidate();
										break;
			case "CompraPIAmortizable": CompraPIPanel panelCompraPI = new CompraPIPanel();
										contenedorPanel.add(panelCompraPI);
										panelesAsiento.add(panelCompraPI);
										contenedorPanel.revalidate();
										break;
			case "CompraMercaderias": 	
								if(conIVA){
									CompraMercaderiasPanel panelCompraMerc = new CompraMercaderiasPanel();
									contenedorPanel.add(panelCompraMerc);
									panelesAsiento.add(panelCompraMerc);
									contenedorPanel.revalidate();
								}else{
									CompraMercaderiasSinIVAPanel panelCompraMercSinIVA = new CompraMercaderiasSinIVAPanel();
									contenedorPanel.add(panelCompraMercSinIVA);
									panelesAsiento.add(panelCompraMercSinIVA);
									contenedorPanel.revalidate();			
								}
								break;
			case "VentaMercaderias":  	
								if(conIVA){
									VentaMercaderiasPanel panelVentaMerc = new VentaMercaderiasPanel();
									contenedorPanel.add(panelVentaMerc);
									panelesAsiento.add(panelVentaMerc);
									contenedorPanel.revalidate();
								}else{
									VentaMercaderiasSinIVAPanel panelVentaMercSinIVA = new VentaMercaderiasSinIVAPanel();
									contenedorPanel.add(panelVentaMercSinIVA);
									panelesAsiento.add(panelVentaMercSinIVA);
									contenedorPanel.revalidate();
								}
								break;
			case "VentaProyecto":  	
								if(conIVA){
									VentaProyectoPanel panelVentaProy = new VentaProyectoPanel();
									contenedorPanel.add(panelVentaProy);
									panelesAsiento.add(panelVentaProy);
									contenedorPanel.revalidate();
								}else{
									VentaProyectoSinIVAPanel panelVentaProySinIVA = new VentaProyectoSinIVAPanel();
									contenedorPanel.add(panelVentaProySinIVA);
									panelesAsiento.add(panelVentaProySinIVA);
									contenedorPanel.revalidate();
								}
								break;
			case "SueldosYSalarios":
							if(panelAportacion==null || aportacionEjecutada==false){
								JOptionPane.showMessageDialog(null, "Para añadir un los sueldos y salarios antes tiene que haber una aportación inicial ejecutada");
							}else{
								if(conRetenciones){
									SueldoEmpleadosPanel panelSueldoEmpl = new SueldoEmpleadosPanel();
									contenedorPanel.add(panelSueldoEmpl);
									panelesAsiento.add(panelSueldoEmpl);
									contenedorPanel.revalidate();
								}else{
									SueldoEmpleadosSinRetPanel panelSueldoEmplSinRet = new SueldoEmpleadosSinRetPanel();
									contenedorPanel.add(panelSueldoEmplSinRet);
									panelesAsiento.add(panelSueldoEmplSinRet);
									contenedorPanel.revalidate();
								}
							}
							break;
			case "SueldoIgeniero": 	SueldoIngenieroPanel panelSueldoIng = new SueldoIngenieroPanel();
									contenedorPanel.add(panelSueldoIng);
									panelesAsiento.add(panelSueldoIng);
									contenedorPanel.revalidate();
									break;
			case "Interes": 
						if (conRetenciones){
							InteresPanel panelInteres = new InteresPanel();
							contenedorPanel.add(panelInteres);
							panelesAsiento.add(panelInteres);
							contenedorPanel.revalidate();
						}else{
							InteresSinRetPanel panelInteresSinRet = new InteresSinRetPanel();
							contenedorPanel.add(panelInteresSinRet);
							panelesAsiento.add(panelInteresSinRet);
							contenedorPanel.revalidate();
						}
						break;
			case "NuevoSocio": 	
							if(panelAportacion==null || aportacionEjecutada==false){
								JOptionPane.showMessageDialog(null, "Para añadir un nuevo socio antes tiene que haber una aportació inicial ejecutada");
							}else{
								NuevoSocioPanel panelNuevoSoc = new NuevoSocioPanel();
								contenedorPanel.add(panelNuevoSoc);
								panelesAsiento.add(panelNuevoSoc);
								contenedorPanel.revalidate();
							}
								break;
			case "Inventario": 	InventarioPanel panelInventario = new InventarioPanel();
								contenedorPanel.add(panelInventario);
								panelesAsiento.add(panelInventario);
								contenedorPanel.revalidate();
								break;
			case "Dividendos": 
							if (conRetenciones){
								DividendosPanel panelDividendos = new DividendosPanel();
								contenedorPanel.add(panelDividendos);
								panelesAsiento.add(panelDividendos);
								contenedorPanel.revalidate();
							}else{
								DividendosSinRetPanel panelDividendosSinRet = new DividendosSinRetPanel();
								contenedorPanel.add(panelDividendosSinRet);
								panelesAsiento.add(panelDividendosSinRet);
								contenedorPanel.revalidate();	
							}
							break;
		}
	}
	
	
	private class DesplegableItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == desplegable1){
				if (desplegable1.getSelectedItem().equals("Si")) {
					log.info("Configurando asiento contable con IVA");
					conIVA=true;
					introduceIva.setVisible(true);
					cuadroIntroduceIva.setVisible(true);
					comprobarIVA=true;	
				}if (desplegable1.getSelectedItem().equals("No")){
					log.info("Configurando asiento contable sin IVA");
					conIVA=false;
					introduceIva.setVisible(false);
					cuadroIntroduceIva.setVisible(false);
					comprobarIVA=false;
				}	
			}
			if (e.getSource() == desplegable2){
				if (desplegable2.getSelectedItem().equals("Si")) {
					log.info("Configurando asiento contable con Retenciones");
					conRetenciones=true;
				}if (desplegable2.getSelectedItem().equals("No")){
					log.info("Configurando asiento contable sin Retenciones");
					conRetenciones=false;
				}	
			}
			if (e.getSource() == desplegable3){
				if (desplegable3.getSelectedItem().equals("Si")) {
					log.info("Configurando enunciado del supuesto contable con cuentas del PGC");
					enunciadoConCuentas=true;
				}if (desplegable3.getSelectedItem().equals("No")){
						log.info("Configurando enunciado del supuesto contable sin cuentas del PGC");
						enunciadoConCuentas=false;
				}	
			}
			if(comprobarDesplegable){
				if(e.getSource() == desplegableCambioAnoLimite){
					if (desplegableCambioAnoLimite.getSelectedItem().equals("No")) {
						log.info("Configurando año limite para la generación de las tablas");
						introduceAnoLimite.setVisible(false);
						cuadroIntroduceAnoLimite.setVisible(false);
						comprobarAnoFinal=false;
					}if (desplegableCambioAnoLimite.getSelectedItem().equals("Si")){
						log.info("Configurando año limite para la generación de las tablas");
						introduceAnoLimite.setVisible(true);
						cuadroIntroduceAnoLimite.setVisible(true);
						comprobarAnoFinal=true;
					}
				}
			}
		}
	}
	
	public class BotonAceptarActionListener implements ActionListener {
		public void actionPerformed (ActionEvent event){
			boolean ok = true;
			//BOTÓN ACEPTAR VENATANA INICIAL
			if (event.getSource()==botonVentanaInicial){	
				//IVA
				if(comprobarIVA){
					String iva = cuadroIntroduceIva.getText();
					if("".equals(iva)){
						JOptionPane.showMessageDialog(null, "Introduce el % del IVA correctamente");
						ok = false;
					}else{
						try{
							IVA = Double.parseDouble(iva);
							if(IVA<0 || IVA>100){
								JOptionPane.showMessageDialog(null, "El % del IVA debe estar entre 0 y 100");
								ok=false;
							}
						}catch (Exception e){
							JOptionPane.showMessageDialog(null, "Introduce el % del IVA correctamente");
							ok = false;
						}
					}
				}
				
				//Impuesto de sociedades
				String ret = cuadroIntroduceImpuestoSoc.getText();
				if("".equals(ret)){
					JOptionPane.showMessageDialog(null, "Introduce el % del impuesto de sociedades correctamente");
					ok = false;
				}else{
					try{
						impuestoSociedades = Double.parseDouble(ret);
						if(impuestoSociedades<0 || impuestoSociedades>100){
							JOptionPane.showMessageDialog(null, "El % del impuesto de sociedades debe estar entre 0 y 100");
							ok=false;
						}
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, "Introduce el % del impuesto de sociedades correctamente");
						ok = false;
					}
				}
				
				
				//Año inicial
				String ano = cuadroIntroduceAno.getText();
				if("".equals(ano) || ano.length()!=4 ){
					JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
					ok = false;
				}else{
					try{
						anoInicial = Integer.parseInt(ano);
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
						ok =false;
					}
				}	
				if(ok){
					initialize();
					ventanaInicial.dispose();
				}
			}
			//BOTÓN ACEPTAR VENTANA FINAL
			if (event.getSource()==botonVentanaFinal){
				if(comprobarAnoFinal){
					String a = cuadroIntroduceAnoLimite.getText();
					if("".equals(a) || a.length()!=4 ){
						JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
						ok=false;
					}else{
						try{
							anoLimite = Integer.parseInt(a);
							if(anoLimite<anoInicial || anoLimite>anoFinal){
								JOptionPane.showMessageDialog(null, "El año limite debe estar entre el año inicial y el año final");
								ok=false;
							}
						}catch (Exception e){
							JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
							ok=false;
						}
					}	
				}
				if(ok){
					ventanaFinal.dispose();
					JFileChooser fileChooser= new JFileChooser(); 
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					try{ 
						if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){ 
							direccionRuta = fileChooser.getSelectedFile().getAbsolutePath();
							generarSupuestoContable();
							JOptionPane.showMessageDialog(null, "La solución se ha generado con exito");
							frmSupuesto.dispose();
							System.exit(0);
						}else{
							frmSupuesto.setVisible(true);;
						}
						
					}catch (Exception ex){ 
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al generar el supuesto contable");
						ex.printStackTrace(); 
					}
					
					
				}
				
			}
	
		}
	}
	
	public class AddButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (añadirBox.getSelectedItem().equals("Aportación inicial")) {
				log.info("Añadiendo asiento aportación inicial");
				añadeAsiento("AportaciónInicial");
			} 			
			if (añadirBox.getSelectedItem().equals("Préstamo")) {
				log.info("Añadiendo asiento préstamo");
				añadeAsiento("Prestamo");
			}
			if (añadirBox.getSelectedItem().equals("Compra material no amortizable")) {
				log.info("Añadiendo asiento compra material no amortizable");
				añadeAsiento("CompraMaterialNoAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra material amortizable")) {
				log.info("Añadiendo asiento compra material amortizable");
				añadeAsiento("CompraMaterialAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra intangible no amortizable")) {
				log.info("Añadiendo asiento compra intangible no amortizable");
				añadeAsiento("CompraIntangibleNoAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra software amortizable")) {
				log.info("Añadiendo asiento compra software amortizable");
				añadeAsiento("CompraSWAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra propiedad industrial amortizable")) {
				log.info("Añadiendo asiento compra propiedad industrial amortizable");
				añadeAsiento("CompraPIAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra mercaderias")) {
				log.info("Añadiendo asiento compra mercaderias");
				añadeAsiento("CompraMercaderias");
			}
			if (añadirBox.getSelectedItem().equals("Venta mercaderías")) {
				log.info("Añadiendo asiento venta mercaderías");
				añadeAsiento("VentaMercaderias");
			}
			if (añadirBox.getSelectedItem().equals("Venta proyecto")) {
				log.info("Añadiendo asiento venta proyecto");
				añadeAsiento("VentaProyecto");
			}
			if (añadirBox.getSelectedItem().equals("Sueldos y salarios")) {
				log.info("Añadiendo asiento sueldo empleado");
				añadeAsiento("SueldosYSalarios");
			}
			if (añadirBox.getSelectedItem().equals("Sueldo ingeniero")) {
				log.info("Añadiendo asiento sueldo ingeniero");
				añadeAsiento("SueldoIgeniero");
			}
			if (añadirBox.getSelectedItem().equals("Intereses")) {
				log.info("Añadiendo asiento intereses");
				añadeAsiento("Interes");
			}
			if (añadirBox.getSelectedItem().equals("Nuevo socio")) {
				log.info("Añadiendo asiento nuevos socios");
				añadeAsiento("NuevoSocio");
			}
			if (añadirBox.getSelectedItem().equals("Inventario")) {
				log.info("Añadiendo asiento inventario");
				añadeAsiento("Inventario");
			}
			if (añadirBox.getSelectedItem().equals("Dividendos")) {
				log.info("Añadiendo asiento dividendos");
				añadeAsiento("Dividendos");
			}
		}
	}
	
	private class MenuNuevoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			log.info("Generando un documento nuevo.");
			contenedorPanel.removeAll();
			panelesAsiento.clear();
			contenedorPanel.revalidate();
			
			textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciados de los asientos contable<br><br></b></p>";
			panelEnunciado.setText(textoEnunciado);
			Main.aportacionEjecutada = false;
			Main.panelAportacion = null;
			AsientoPanel.listaEnunciados =  new ArrayList<ArrayList<Enunciado>>();
			ejecucionAlgunAsiento = false;
		}
	}

	private class MenuWebActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			log.info("Mostrando página web.");
			try {
				Desktop.getDesktop().browse(
						new URI("http://robertoia.github.com/PLQuiz"));
			} catch (IOException | URISyntaxException e) {
				log.error("Error abriendo página web de la aplicación", e);
			}
		}
	}

	private class MenuAcercaDeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			log.info("Mostrando acerca de.");
			JOptionPane.showMessageDialog(frmSupuesto, "Supuesto contable\n"
					+ "TFG del Grado en Ingeniería Informática\n"
					+ "Escuela Politécnica Superior, Universidad de Burgos\n"
					+ "Presentado en Julio de 2016\n\n"
					+ "Autor: Noelia Manso García\n"
					+ "Tutor: Luis R. Izquierdo", "Acerca de",
					JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public class BotonGenerarSolucionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(ejecucionAlgunAsiento==false){
				JOptionPane.showMessageDialog(null, "No has introducido ningún asiento");
			}else{
				frmSupuesto.setVisible(false);
				initVentanaFinal();
			}
		}
	}
	
	public void generarSupuestoContable(){
		int anoFin = 0;
		boolean hayDividendo = false;
		boolean entra1 = true;
		boolean entra2 = true;
		boolean entra3 = true;
		boolean entra4 = true;
		boolean entra5 = true;
		CuentaResultados cuentaResultados;
		Tesoreria tesoreria;
		Balance balance;
		
		if(anoLimite==0){
			//Calculo el ultimo año
			for(int i=0; i<AsientoPanel.listaEnunciadosOrdenados.size(); i++){
				if(AsientoPanel.listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)>anoFin){
					anoFin = AsientoPanel.listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR);
				}	
			}
		}else{
			anoFin=anoLimite;
		}
		System.out.println("Año inicio: "+anoInicial);
		System.out.println("Año fin: "+anoFin);
		System.out.println("Año limite: "+anoLimite);
		for(int anoActual=anoInicial; anoActual<=anoFin; anoActual++){
			entra1= true;
			entra2=true;
			entra3=true;
			entra4=true;
			entra5=true;
			for(int i=0; i<AsientoPanel.listaEnunciadosOrdenados.size(); i++){
				//TODOS LOS AÑOS MENOS EL PRIMERO
				if (anoActual!=anoInicial){
					
					//Pago deudas hacienda
					Calendar fechaDeudasHP = Calendar.getInstance();
					fechaDeudasHP.set(anoActual,0,1);
					if(conIVA && conRetenciones && entra1==true){
						PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fechaDeudasHP, null, enunciadoConCuentas);
						AsientoPanel.añadeEnunciado(deudasHacienda.enunciados);
						entra1=false;
					}if(conIVA==false && conRetenciones && entra2==true){
						PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA = new PagoDeudasHaciendaSinIVA(fechaDeudasHP, null, enunciadoConCuentas);
						AsientoPanel.añadeEnunciado(deudasHaciendaSinIVA.enunciados);
						entra2=false;
					}if(conIVA && conRetenciones==false && entra3==true){
						PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP, null, enunciadoConCuentas);
						AsientoPanel.añadeEnunciado(deudasHaciendaSinRetenciones.enunciados);
						entra3=false;
					}
					 
					//Pago deudas segurada social
					if(conRetenciones && entra4==true){
						Calendar fechaDeudasSS = Calendar.getInstance();
						fechaDeudasSS.set(anoActual,0,1);
						PagoDeudasSS deudasSS = new PagoDeudasSS(fechaDeudasSS, null, enunciadoConCuentas);
						AsientoPanel.añadeEnunciado(deudasSS.enunciados);
						entra4=false;
					}
					//Si no existe dividendos
					for(int j=0; j<AsientoPanel.anoDividendo.size(); j++){
						if(AsientoPanel.anoDividendo.get(j) == anoActual){
							hayDividendo=true;
						}
					}
					if(hayDividendo==false){
						Calendar fechaNoDividendos = Calendar.getInstance();
						fechaNoDividendos.set(anoActual,0,1);
						//NoDividendos noDividendos2 = new NoDividendos (fechaNoDividendos, null, false);
						new NoDividendos (fechaNoDividendos, null, false);
					}else{
						hayDividendo=false;
					}
				
				}
				
				//SIEMPRE
				//Iva
				if (conIVA && entra5==true){
					Calendar fechaIVA = Calendar.getInstance();
					fechaIVA.set(anoActual,11,31);
					IVA iva = new IVA (fechaIVA, null, enunciadoConCuentas);
					AsientoPanel.añadeEnunciado(iva.enunciados);
					entra5=false;
				}
				//Cuenta perdidas y ganancias
				cuentaResultados = new CuentaResultados (anoActual);
				cuentaResultados.imprimeCuentaResultados();
				
				//Tesorería
				tesoreria = new Tesoreria (anoActual);
				tesoreria.imprimeTesoreria();
				
				//Balance
				balance = new Balance(anoActual);
				balance.imprimeBalance();
			}
		}
		
		//IMPRIME ENUNCIADO
		imprimeEnunciado(AsientoPanel.listaEnunciadosOrdenados, anoFin);	
		
	}
	public void imprimeEnunciado (ArrayList<Enunciado>todosEnunciadosOrdenados, int anoFin){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar fech;
		String enun;
		
		Document documento = new Document();
		
		try{	
			PdfWriter.getInstance(documento, new FileOutputStream(direccionRuta+"/Enunciado.pdf"));
			
			documento.open();
			
			Paragraph par = new Paragraph("ENUNCIADO SUPUESTO CONTABLE:  \n \n", FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK)); 
			documento.add(par);
			

			Calendar fechaCierre = Calendar.getInstance();
			 
			for(int anoActual=anoInicial; anoActual<=anoFin; anoActual++){
				//Nuevo año
				documento.add(new Paragraph("Se tiene la siguiente información vinculada con la empresa correspondiente al año "+anoActual+":\n\n", FontFactory.getFont("arial", 12, Font.BOLDITALIC, BaseColor.BLACK)));
				
				//Asientos
				for(int i=0; i<todosEnunciadosOrdenados.size(); i++){
					if(todosEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==anoActual){
						fech= todosEnunciadosOrdenados.get(i).getFecha();
						enun = todosEnunciadosOrdenados.get(i).getEnunciado();
						documento.add(new Paragraph(formateador.format(fech.getTime())+ " " + enun));
						documento.add(new Paragraph("\n"));	
					}
				}
				
				//Cierre
				fechaCierre.set(anoActual,11,31);
				if(enunciadoConCuentas){
					if(conRetenciones){
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedades+"% del beneficio).\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
					}else{
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
					}
				}else{
					if(conRetenciones){
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedades+"% del beneficio).\n\n"));
					}else{
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\n\n"));
					}
				}
			}
			
			//Imprime enunciado
			Paragraph parrafo = new Paragraph("\n\nSe pide: \n"
					 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
					 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
					 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. ");
			if(conRetenciones && conIVA){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedades+ "% del beneficio y el IVA es " +IVA+ "%.");
			}if(conRetenciones==false && conIVA){
				parrafo.add("El IVA es " +IVA+ "%.\n");	 
			}
			if(conRetenciones && conIVA==false){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedades+ "% del beneficio.");				 
			}
 			parrafo.add("\n  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
					 + "que se cumple la ecuación que las liga. \n"
					 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.\n"
					 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.\n"
					 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n"
					 + "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
					 + "acción emitida el " +formateador.format(fechaCierre.getTime())
					 + ". ¿Cuál sería el valor de mercado de las acciones? \n");
			documento.add(parrafo);
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}
	
	/*
	public static void imprimeEnunciados (ArrayList<ArrayList<Enunciado>>listaEnunciados){
		ArrayList<Enunciado> listaEnunciadosOrdenados = new ArrayList<Enunciado>();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		Calendar fech;
		String enun;
		
		//Ordenamos cada enunciado de cada asiento por fecha
		for(int i=0; i<listaEnunciados.size(); i++){
			for(int j=0; j<listaEnunciados.get(i).size(); j++){
				listaEnunciadosOrdenados.add(listaEnunciados.get(i).get(j));	
				if(listaEnunciados.get(i).get(j).fecha.get(Calendar.YEAR)>anoFin){
					anoFin = listaEnunciados.get(i).get(j).fecha.get(Calendar.YEAR);
				}
			}
		}
		Collections.sort(listaEnunciadosOrdenados);
		
		Document documento = new Document();
		
		try{	
			PdfWriter.getInstance(documento, new FileOutputStream(direccionRuta+"/Enunciado.pdf"));
			
			documento.open();
			
			Paragraph par = new Paragraph("ENUNCIADO SUPUESTO CONTABLE:  \n \n", FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK)); 
			documento.add(par);
			

			Calendar fechaCierre = Calendar.getInstance();
			for(int anoActual=anoInicial; anoActual<=anoFin; anoActual++){
				//Inicio año
				documento.add(new Paragraph("Se tiene la siguiente información vinculada con la empresa correspondiente al año "+anoActual+":\n\n", FontFactory.getFont("arial", 12, Font.BOLDITALIC, BaseColor.BLACK)));
				
				//Imprime asientos
				for(int i=0; i<listaEnunciadosOrdenados.size(); i++){
					if(listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==anoActual){
						fech= listaEnunciadosOrdenados.get(i).getFecha();
						enun = listaEnunciadosOrdenados.get(i).getEnunciado();
						documento.add(new Paragraph(formateador.format(fech.getTime())+ " " + enun));
						documento.add(new Paragraph("\n"));	
					}
				}
				
				//Cierre
				fechaCierre.set(anoActual,11,31);
				if(enunciadoCuentas){
					if(conRetenciones){
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedades+"% del beneficio).\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
					}else{
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n\n"));
					}
				}else{
					if(conRetenciones){
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+impuestoSociedades+"% del beneficio).\n\n"));
					}else{
						documento.add(new Paragraph(formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\n\n"));
					}
				}
			}
					
			
			//Imprime enunciado
			Paragraph parrafo = new Paragraph("\n\nSe pide: \n"
					 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
					 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
					 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. ");
			if(conRetenciones && conIVA){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedades+ "% del beneficio y el IVA es " +IVA+ "%.");
			}if(conRetenciones==false && conIVA){
				parrafo.add("El IVA es " +IVA+ "%.\n");	 
			}
			if(conRetenciones && conIVA==false){
				parrafo.add("El impuesto de sociedades es el " +impuestoSociedades+ "% del beneficio.");				 
			}
 			parrafo.add("\n  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
					 + "que se cumple la ecuación que las liga. \n"
					 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.\n"
					 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.\n"
					 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n"
					 + "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
					 + "acción emitida el " +formateador.format(fechaCierre.getTime())
					 + ". ¿Cuál sería el valor de mercado de las acciones? \n");
			documento.add(parrafo);
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}*/

}
