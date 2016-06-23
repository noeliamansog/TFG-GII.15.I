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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.ubu.inf.tfg.asientosContables.*;
import es.ubu.inf.tfg.asientosContables.sinIVA.PagoDeudasHaciendaSinIVA;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.DividendosSinRetenciones;
import es.ubu.inf.tfg.asientosContables.sinRetenciones.PagoDeudasHaciendaSinRetenciones;
import es.ubu.inf.tfg.doc.*;
import es.ubu.inf.tfg.solucion.Balance;
import es.ubu.inf.tfg.solucion.Calculos;
import es.ubu.inf.tfg.solucion.CuentaResultados;
import es.ubu.inf.tfg.solucion.Tesoreria;
import es.ubu.inf.tfg.ui.panelesAsientos.AportacionPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraMercaderiasPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraPIPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraSWPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.DividendosPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraIntangibleNoAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.InteresPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.InventarioPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraMaterialAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.CompraMaterialNoAmortizablePanel;
import es.ubu.inf.tfg.ui.panelesAsientos.NuevoSocioPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.PrestamoPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.SueldosEmpleadosPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.SueldoIngenieroPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.VentaMercaderiasPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.VentaProyectoPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.CompraMercaderiasSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.VentaMercaderiasSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinIVA.VentaProyectoSinIVAPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.DividendosSinRetPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.InteresesSinRetPanel;
import es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones.SueldosEmpleadosSinRetPanel;

/**
 * Clase Main, clase principal desde donde se ejecuta la aplicación.
 * 
 * @author Noelia Manso García
 */
public class Main {
	
	/**
	 * % del IVA.
	 */
	public static double IVA;
	/**
	 * % del impuesto de sociedades.
	 */
	public static double impuestoSociedades;
	/**
	 * Año inical del supuesto contable.
	 */
	public static int anoInicial;
	/**
	 * Último año que afecta al supuesto contable.
	 */
	public static int anoFinal = anoInicial;
	/**
	 * Año limite. Hasta que año desea el usuario generar la solución del supuesto contable.
	 */
	public static int anoLimite;
	/**
	 * Número de socios en el supuesto contable.
	 */
	public static double numeroSocios;
	/**
	 * Número de acciones en el supuesto contable.
	 */
	public static double numeroAcciones;
	
	
	/**
	 * Booleano que indica si el usuario desea el supuesto contable con o sin IVA.
	 */
	public static boolean conIVA = true;
	/**
	 * Booleano que indica si el usuario desea el supuesto contable con o sin retenciones.
	 */
	public static boolean conRetenciones = true;
	/**
	 * Booleano que indica si el usuario desea que en el enunciado del supuesto contable 
	 * aparezca los nombres de las cuentas PGC usadas en cada asiento contable.
	 */
	public static boolean enunciadoConCuentas = true;
	
	/**
	 * Dirección donde guardar en enunciado y la solución del supuesto contable.
	 */
	public static String direccionRuta = "";
	
	
	/**
	 * Panel del asiento contable de aportación inicial.
	 */
	public static AportacionPanel panelAportacion = null;
	/**
	 * Booleano que indica si el asiento contable de aportación inicial se ha ejecutado.
	 */
	public static boolean aportacionEjecutada = false;
	/**
	 * Fecha en la que se ejecuta el asiento contable de aportación inicial.
	 */
	public static Calendar fechaAportacion;
	/**
	 * Booleano que indica si se ha ejecutado algún asiento contable.
	 */
	public static boolean ejecucionAlgunAsiento = false;

	
	/**
	 * Lista de los paneles de los asientos contables.
	 */
	public List<AsientoPanel> panelesAsiento = new ArrayList<>();
	
	/**
	 * Valor nominal de las acciones del supuesto contable.
	 */
	public static double valorNominal;
	/**
	 * Valor contable de las acciones del supuesto contable.
	 */
	public static double valorContable;
	
	/**
	 * Valor del activo del balance del año anterior.
	 */
	public static double activoAnoAnterior = 0;
	/**
	 * Valor del patrimonio neto del balance del año anterior.
	 */
	public static double pnAnoAnterior = 0;
	

	/**
	 * Datos que se generan al ejecutar el asiento contable dividendos.
	 */
	public static HashMap<Calendar, double []> datosDividendos = new HashMap<Calendar, double []>();
	/**
	 * Datos que se generan al ejecutar el asiento contable dividendosSinRetenciones.
	 */
	public static HashMap<Calendar, double []> datosDividendosSinRet = new HashMap<Calendar, double []>();
	
	//INTERFAZ
	public JFrame ventanaPrincipal;
	
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

	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {}
			}
		});
	}
	
	/**
	 * Constructor de la clase Main en el que se comienza la ejecución del programa.
	 */
	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
		}
		
		initVentanaInicial();
	}

	/**
	 * Función que crea la ventana inicial de la interfaz de la aplicación.
	 */
	public void initVentanaInicial(){
		this.ventanaInicial = new JFrame();   
        
        this.ventanaInicial.setTitle("Configuaración antes de empezar");
        this.ventanaInicial.setSize(440, 470);
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
        this.enunciadoCuentasText.setText("<html>¿Deseas que en el enunciado del supuesto aparezcan las <br> cuentas del PGC?</html>");
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
        
        this.ventanaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ventanaInicial.setVisible(true);		
	}
	
	/**
	 * Función que crea la ventana principal de la interfaz de la aplicación.
	 */
	public void initVentanaPrincipal(){
		this.ventanaPrincipal= new JFrame();
		this.ventanaPrincipal.setTitle("Supuesto contable");
		this.ventanaPrincipal.setBounds(100, 100, 1150, 900);
		this.ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaPrincipal.setVisible(true);
	}
		
	/**
	 * Función que crea la barra de herramientas en la ventana principal de la interfaz.
	 */
	public void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.ventanaPrincipal.getContentPane().add(this.menuBar, BorderLayout.NORTH);

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

	/**
	 * Función que crea el panel de los asientos contables en la ventana principal de la interfaz.
	 */
	public void initPanelIzq() { 
		//Panel control izquierda
		this.controlPanelIzq = new JPanel();
		this.controlPanelIzq.setBorder(null);
		this.ventanaPrincipal.getContentPane().add(this.controlPanelIzq, BorderLayout.WEST);
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
	
	/**
	 * Función que crea el panel del enunciado en la ventana principal de la interfaz.
	 */
	private void initPanelDrch() {
		//Panel control derecha
		this.controlPanelDrch = new JPanel();
		this.controlPanelDrch.setBorder(null);
		this.ventanaPrincipal.getContentPane().add(this.controlPanelDrch,BorderLayout.CENTER);
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
		textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciado "
				+ "supuesto contable<br><br></b></p>";
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
	
	/**
	 * Función que crea la ventana final de la interfaz de la aplicación.
	 */
	private void initVentanaFinal() {	
		this.ventanaFinal = new JFrame();   
        
        this.ventanaFinal.setTitle("Configuaración de la generación de tablas");
        this.ventanaFinal.setSize(470, 280);
        this.ventanaFinal.setLocationRelativeTo(null);
        ventanaFinal.setLayout(null); 
    
        this.textoVentanaFinal = new JLabel();
        if(anoInicial == anoFinal){
        	this.textoVentanaFinal.setText("<html><b>Se generará en el año "+anoInicial+" las tablas correspondientes "
        			+ "al Balance,<br> Cuenta de perdidas y ganancias y Tesorería de este año.</b></html>");
        	this.textoVentanaFinal.setBounds(10, 0, 430, 100);
            this.ventanaFinal.add(this.textoVentanaFinal);
        	
        }else{
        	this.textoVentanaFinal.setText("<html><b>Se generará, por cada año (desde "+anoInicial+" hasta "+ anoFinal +" incluidos)"
        			+ " las<br> tablas correspondientes al Balance, Cuenta de perdidas y ganancias<br> y Tesorería. <br> "
        			+ "¿Desea modificar hasta que año generar estas tablas?</b></html>");
        	this.textoVentanaFinal.setBounds(10, 0, 430, 100);
            this.ventanaFinal.add(this.textoVentanaFinal);
        
            this.desplegableCambioAnoLimite = new JComboBox<>();
            this.desplegableCambioAnoLimite.setModel(new DefaultComboBoxModel<String>(new String[] {"No", "Si"}));
            this.desplegableCambioAnoLimite.setBounds(20, 95, 50, 30);
            this.ventanaFinal.add(this.desplegableCambioAnoLimite);
            this.desplegableCambioAnoLimite.addItemListener(new DesplegableItemListener()); 
        
            this.introduceAnoLimite = new JLabel();
        	this.introduceAnoLimite.setText("Introduce el año de limite deseado: ");
        	this.introduceAnoLimite.setBounds(20, 120, 400, 50);
        	this.ventanaFinal.add(this.introduceAnoLimite);
        	this.introduceAnoLimite.setVisible(false);
	
        	this.cuadroIntroduceAnoLimite = new JTextField(4);
        	this.cuadroIntroduceAnoLimite.setBounds(250, 130, 40, 25);
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
        
        this.ventanaFinal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ventanaFinal.setVisible(true);			
	}

	
	/**
	 * Función que añade un asiento al panel de asientos contables de la interfaz.
	 * @param asiento Asiento que deseamos añadir al panel.
	 */
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
			case "CompraMaterialNoAmortizable":  	CompraMaterialNoAmortizablePanel panelMatNoAmort = new CompraMaterialNoAmortizablePanel();
													contenedorPanel.add(panelMatNoAmort);
													panelesAsiento.add(panelMatNoAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraMaterialAmortizable":   	CompraMaterialAmortizablePanel panelMatAmort = new CompraMaterialAmortizablePanel();								
													contenedorPanel.add(panelMatAmort);
													panelesAsiento.add(panelMatAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraIntangibleNoAmortizable": 	CompraIntangibleNoAmortizablePanel panelIntNoAmort = new CompraIntangibleNoAmortizablePanel();
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
									SueldosEmpleadosPanel panelSueldoEmpl = new SueldosEmpleadosPanel();
									contenedorPanel.add(panelSueldoEmpl);
									panelesAsiento.add(panelSueldoEmpl);
									contenedorPanel.revalidate();
								}else{
									SueldosEmpleadosSinRetPanel panelSueldoEmplSinRet = new SueldosEmpleadosSinRetPanel();
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
							InteresesSinRetPanel panelInteresSinRet = new InteresesSinRetPanel();
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
	
	
	/**
	 * Clase DesplegableItemListener para controlar los desplegables de la interfaz.
	 * 
	 * @author Noelia Manso García
	 */
	private class DesplegableItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == desplegable1){
				if (desplegable1.getSelectedItem().equals("Si")) {
					conIVA=true;
					introduceIva.setVisible(true);
					cuadroIntroduceIva.setVisible(true);
					comprobarIVA=true;	
				}if (desplegable1.getSelectedItem().equals("No")){
					conIVA=false;
					introduceIva.setVisible(false);
					cuadroIntroduceIva.setVisible(false);
					comprobarIVA=false;
				}	
			}
			if (e.getSource() == desplegable2){
				if (desplegable2.getSelectedItem().equals("Si")) {
					conRetenciones=true;
				}if (desplegable2.getSelectedItem().equals("No")){
					conRetenciones=false;
				}	
			}
			if (e.getSource() == desplegable3){
				if (desplegable3.getSelectedItem().equals("Si")) {
					enunciadoConCuentas=true;
				}if (desplegable3.getSelectedItem().equals("No")){
						enunciadoConCuentas=false;
				}	
			}
			if(comprobarDesplegable){
				if(e.getSource() == desplegableCambioAnoLimite){
					if (desplegableCambioAnoLimite.getSelectedItem().equals("No")) {
						introduceAnoLimite.setVisible(false);
						cuadroIntroduceAnoLimite.setVisible(false);
						comprobarAnoFinal=false;
					}if (desplegableCambioAnoLimite.getSelectedItem().equals("Si")){
						introduceAnoLimite.setVisible(true);
						cuadroIntroduceAnoLimite.setVisible(true);
						comprobarAnoFinal=true;
					}
				}
			}
		}
	}
	
	/**
	 * Clase BotonAceptarActionListener para controlar los datos introducidos por el usuario en la interfaz.
	 *
	 * @author Noelia Manso García
	 */
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
					initVentanaPrincipal();
					initMenuBar();
					initPanelIzq();
					initPanelDrch();
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
							JOptionPane.showMessageDialog(null, "La solución se ha generado con éxito");
							ventanaPrincipal.dispose();
							System.exit(0);
						}else{
							ventanaPrincipal.setVisible(true);
							comprobarAnoFinal = false;
						}
						
					}catch (Exception ex){ 
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al generar el supuesto contable");
						ex.printStackTrace(); 
					}		
				}
			}
		}
	}
	
	/**
	 * Clase AddButtonActionListener para controlar el tipo de asiento contable que desea añadir 
	 * el usuario en la interfaz.
	 * 
	 * @author Noelia Manso García
	 */
	public class AddButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (añadirBox.getSelectedItem().equals("Aportación inicial")) {
				añadeAsiento("AportaciónInicial");
			} 			
			if (añadirBox.getSelectedItem().equals("Préstamo")) {
				añadeAsiento("Prestamo");
			}
			if (añadirBox.getSelectedItem().equals("Compra material no amortizable")) {
				añadeAsiento("CompraMaterialNoAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra material amortizable")) {
				añadeAsiento("CompraMaterialAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra intangible no amortizable")) {
				añadeAsiento("CompraIntangibleNoAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra software amortizable")) {
				añadeAsiento("CompraSWAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra propiedad industrial amortizable")) {
				añadeAsiento("CompraPIAmortizable");
			}
			if (añadirBox.getSelectedItem().equals("Compra mercaderias")) {
				añadeAsiento("CompraMercaderias");
			}
			if (añadirBox.getSelectedItem().equals("Venta mercaderías")) {
				añadeAsiento("VentaMercaderias");
			}
			if (añadirBox.getSelectedItem().equals("Venta proyecto")) {
				añadeAsiento("VentaProyecto");
			}
			if (añadirBox.getSelectedItem().equals("Sueldos y salarios")) {
				añadeAsiento("SueldosYSalarios");
			}
			if (añadirBox.getSelectedItem().equals("Sueldo ingeniero")) {
				añadeAsiento("SueldoIgeniero");
			}
			if (añadirBox.getSelectedItem().equals("Intereses")) {
				añadeAsiento("Interes");
			}
			if (añadirBox.getSelectedItem().equals("Nuevo socio")) {
				añadeAsiento("NuevoSocio");
			}
			if (añadirBox.getSelectedItem().equals("Inventario")) {
				añadeAsiento("Inventario");
			}
			if (añadirBox.getSelectedItem().equals("Dividendos")) {
				añadeAsiento("Dividendos");
			}
		}
	}
	
	/**
	 * Clase MenuNuevoActionListener para controlar la generación de un nuevo documento en blanco.
	 * 
	 * @author Noelia Manso García
	 */
	private class MenuNuevoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			contenedorPanel.removeAll();
			panelesAsiento.clear();
			contenedorPanel.revalidate();
			
			textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciados de los asientos contable<br><br></b></p>";
			panelEnunciado.setText(textoEnunciado);
			AsientoPanel.listaEnunciados =  new ArrayList<ArrayList<Enunciado>>();
			
			ejecucionAlgunAsiento = false;
			Main.aportacionEjecutada = false;
			Main.panelAportacion = null;	
			
			Main.anoFinal = Main.anoInicial;
			Main.anoLimite = 0;
			AsientoPanel.anoFin=0;
			
			comprobarDesplegable = false;
		}
	}

	/**
	 * Clase MenuWebActionListener para controlar el botón de la interfaz que redirigé a la pagina web.
	 * 
	 * @author Noelia Manso García.
	 */
	private class MenuWebActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Desktop.getDesktop().browse(
						new URI("http://robertoia.github.com/PLQuiz"));
			} catch (IOException | URISyntaxException e) {
			}
		}
	}

	/**
	 * Clase MenuAcercaDeActionListenerpara controlar el botón acerca de de la interfaz.
	 * 
	 * @author Noelia Manso García
	 */
	private class MenuAcercaDeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Supuesto contable\n"
					+ "TFG del Grado en Ingeniería Informática\n"
					+ "Escuela Politécnica Superior, Universidad de Burgos\n"
					+ "Presentado en Julio de 2016\n\n"
					+ "Autor: Noelia Manso García\n"
					+ "Tutor: Luis R. Izquierdo", "Acerca de",
					JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	 * Clase BotonGenerarSolucionActionListener para controlar el botón para generar la 
	 * solución del supuesto contable de la interfaz.
	 * 
	 * @author Noelia Manso García
	 */
	public class BotonGenerarSolucionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(ejecucionAlgunAsiento==false){
				JOptionPane.showMessageDialog(null, "No has introducido ningún asiento");
			}else{
				ventanaPrincipal.setVisible(false);
				initVentanaFinal();
			}
		}
	}
	
	/**
	 * Función que genera la solución del supuesto contable.
	 */
	public void generarSupuestoContable(){
		int anoFin = 0;
		//boolean hayDividendo = false;

		CuentaResultados cuentaResultados;
		Tesoreria tesoreria;
		Balance balance;
		Calculos calculos;
		
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

		activoAnoAnterior = 0;
		pnAnoAnterior = 0;
		numeroAcciones = numeroSocios;
		for(int anoActual=anoInicial; anoActual<=anoFin; anoActual++){
			//TODOS LOS AÑOS MENOS EL PRIMERO
			if (anoActual!=anoInicial){
					
				//Pago deudas hacienda
				Calendar fechaDeudasHP = Calendar.getInstance();
				fechaDeudasHP.set(anoActual,0,1);
				if(conIVA && conRetenciones){
					PagoDeudasHacienda deudasHacienda = new PagoDeudasHacienda(fechaDeudasHP, null, enunciadoConCuentas);
					AsientoPanel.añadeEnunciado(deudasHacienda.enunciados);
				}if(conIVA==false && conRetenciones){
					PagoDeudasHaciendaSinIVA deudasHaciendaSinIVA = new PagoDeudasHaciendaSinIVA(fechaDeudasHP, null, enunciadoConCuentas);
					AsientoPanel.añadeEnunciado(deudasHaciendaSinIVA.enunciados);
				}if(conIVA && conRetenciones==false ){
					PagoDeudasHaciendaSinRetenciones deudasHaciendaSinRetenciones = new PagoDeudasHaciendaSinRetenciones(fechaDeudasHP, null, enunciadoConCuentas);
					AsientoPanel.añadeEnunciado(deudasHaciendaSinRetenciones.enunciados);
				}
					 
				//Pago deudas segurada social
				if(conRetenciones){
					Calendar fechaDeudasSS = Calendar.getInstance();
					fechaDeudasSS.set(anoActual,0,1);
					PagoDeudasSS deudasSS = new PagoDeudasSS(fechaDeudasSS, null, enunciadoConCuentas);
					AsientoPanel.añadeEnunciado(deudasSS.enunciados);
				}

				//Dividendos
				if(AsientoPanel.anoDividendo.contains(anoActual)){
					if(conRetenciones){
						for (Map.Entry<Calendar, double []> entry : datosDividendos.entrySet()) {
							Calendar fechaDividendos = entry.getKey();
							double[] inputsDividendos = entry.getValue();
							if(fechaDividendos.get(Calendar.YEAR)==anoActual){
								Dividendos dividendos = new Dividendos(fechaDividendos, inputsDividendos, enunciadoConCuentas);
								AsientoPanel.añadeEnunciado(dividendos.enunciados);
							}

						}
					}else{
						for (Map.Entry<Calendar, double []> entry : datosDividendosSinRet.entrySet()) {
							Calendar fechaDividendosSinRet = entry.getKey();
							double[] inputsDividendosSinRet = entry.getValue();
							if(fechaDividendosSinRet.get(Calendar.YEAR)==anoActual){
								DividendosSinRetenciones dividendosSinR = new DividendosSinRetenciones(fechaDividendosSinRet, inputsDividendosSinRet, enunciadoConCuentas);
								AsientoPanel.añadeEnunciado(dividendosSinR.enunciados);
							}

						}
					}
				}else{
					Calendar fechaNoDividendos = Calendar.getInstance();
					fechaNoDividendos.set(anoActual,0,1);
					new NoDividendos (fechaNoDividendos, null, enunciadoConCuentas);
				}
				
			}
				
			//SIEMPRE
			//Iva
			if (conIVA){
				Calendar fechaIVA = Calendar.getInstance();
				fechaIVA.set(anoActual,11,31);
				IVA iva = new IVA (fechaIVA, null, enunciadoConCuentas);
				AsientoPanel.añadeEnunciado(iva.enunciados);
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
				
			//En este caso el número de acciones=numero de socios;
			valorContable = balance.getPN()/numeroAcciones;
				
			//Calculos
			calculos = new Calculos(anoActual);
			calculos.imprimeCalculos();
				
			activoAnoAnterior = balance.getActivo();
			pnAnoAnterior = balance.getPN();	
			
		}
		
		//IMPRIME ENUNCIADO
		generaEnunciado(AsientoPanel.listaEnunciadosOrdenados, anoFin);	
		
	}
	
	/**
	 * Función que genera el enunciado del supuesto contable.
	 * @param todosEnunciadosOrdenados Lista de los enunciados de los supuestos contables 
	 * 								   ejecutados ordenados por fecha.
	 * @param anoFin Último año al que afecta el supusto contable.
	 */
	public void generaEnunciado (ArrayList<Enunciado>todosEnunciadosOrdenados, int anoFin){
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
					 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n");
			documento.add(parrafo);
			
			documento.close();
			
		}catch(Exception e){
            System.err.println("Ocurrio un error al crear el archivo");
            System.exit(-1);
		
		}
	}
}
