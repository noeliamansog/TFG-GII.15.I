package es.ubu.inf.tfg.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static boolean conIVA = true;
	public static boolean conRetenciones = true;
	public static boolean enunciadoConCuentas = true;
	public static int anoInicial;
	public static double numeroSocios;
	public static boolean aportacionEjecutada = false;
	public static Calendar fechaAportacion;
	

	public JFrame ventana;
	public JLabel textoVentana;
	public JLabel ivaText;
	public JComboBox<String> desplegable1;
	public JLabel retencionText;
	public JComboBox<String> desplegable2;
	public JLabel enunciadoCuentasText;
	public JComboBox<String> desplegable3;
	public JTextField introduceAno;
	public JButton botonVentana;
	
	public JFrame frmSupuesto;
	public JPanel controlPanel;
	public JPanel panelBox;
	public JButton añadirButton;
	public JComboBox<String> añadirBox;
	public JPanel contenedorPanel;
	public Component añadirDerechoStrut;
	public Component añadirIzquierdoStrut;
	public JMenuBar menuBar;
	public JMenuItem menuNuevo;
	public JMenu menuArchivo;
	public JMenu menuAyuda;
	public JMenuItem menuAcercaDe;
	public JMenuItem menuWeb;

	public JFileChooser fileChooser;
	public JScrollPane contenedorScroll;
	public JPanel panelAsientos;
	public AportacionPanel panelAportacion = null;
	
	
	public boolean scrollContenedor = true;
	
	public List<AsientoPanel<?>> panelesAsiento = new ArrayList<>();

	public static void main(String[] args) {
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
		
		initVentana();
		
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		initPanel();
		initMenuBar();
		initControlPanel();

	}

	public void initVentana(){
		this.ventana = new JFrame();   
        
        this.ventana.setTitle("Configuaración antes de empezar");
        this.ventana.setSize(400, 400);
        this.ventana.setLocationRelativeTo(null);
        ventana.setLayout(null); 
    
        this.textoVentana = new JLabel();
        this.textoVentana.setText("<html><body><u> ¿COMO DESEA REALIZAR EL SUPUESTO CONTABLE?</u></body></html>");
        this.textoVentana.setBounds(10, 7, 400, 50);
        this.ventana.add(this.textoVentana);		
        
        //IVA
        this.ivaText = new JLabel();
        this.ivaText.setText("¿Con IVA?");
        this.ivaText.setBounds(10, 60, 70, 50);
        this.ventana.add(this.ivaText);
        
        this.desplegable1 = new JComboBox<>();
		this.desplegable1.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable1.setBounds(100, 60, 50, 40);
        this.ventana.add(this.desplegable1);
        this.desplegable1.addItemListener(new DesplegableItemListener()); 
       
        //Retenciones
        this.retencionText = new JLabel();
        this.retencionText.setText("¿Con Retenciones?");
        this.retencionText.setBounds(10, 110, 150, 50);
        this.ventana.add(this.retencionText);
        
        this.desplegable2 = new JComboBox<>();
		this.desplegable2.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable2.setBounds(160, 110, 50, 40);
        this.ventana.add(this.desplegable2);
        this.desplegable2.addItemListener(new DesplegableItemListener()); 
        
        //Enunciado con cuentas
        this.enunciadoCuentasText = new JLabel();
        this.enunciadoCuentasText.setText("<html><body> ¿Deseas que en el enunciado del supuesto aparezcan las cuentas del PGC? </body></html>");
        this.enunciadoCuentasText.setBounds(10, 160, 390, 50);
        this.ventana.add(this.enunciadoCuentasText);
        
        this.desplegable3 = new JComboBox<>();
		this.desplegable3.setModel(new DefaultComboBoxModel<String>(new String[] {"Si", "No"}));
		this.desplegable3.setBounds(20, 210, 50, 40);
        this.ventana.add(this.desplegable3);
        this.desplegable3.addItemListener(new DesplegableItemListener()); 
        
        //Año inicio
        this.enunciadoCuentasText = new JLabel();
        this.enunciadoCuentasText.setText("<html><body> Introduce el año inicial: </body></html>");
        this.enunciadoCuentasText.setBounds(10, 260, 390, 50);
        this.ventana.add(this.enunciadoCuentasText);
        
        this.introduceAno =  new JTextField(4);
        this.introduceAno.setBounds(170, 270, 40, 25);
        this.ventana.add(this.introduceAno);
        
        //Boton aceptar
        this.botonVentana = new JButton();
        this.botonVentana.setText("Aceptar"); 
        this.botonVentana.setBounds(195, 335, 150, 30);
        this.ventana.add(this.botonVentana);
        this.botonVentana.addActionListener (new BotonAceptarActionListener()); 
        
        this.ventana.setVisible(true);		
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

	public void initControlPanel() { 
		//Panel ¿?
		this.controlPanel = new JPanel();
		this.controlPanel.setBorder(null);
		this.frmSupuesto.getContentPane().add(this.controlPanel, BorderLayout.WEST);
		this.controlPanel.setLayout(new BorderLayout(0, 0));

		this.contenedorScroll = new JScrollPane();
		this.contenedorScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.controlPanel.add(this.contenedorScroll, BorderLayout.CENTER);

		//Panel de la izquierda para los asientos
		this.panelAsientos = new JPanel();
		this.panelAsientos.setBorder(null);
		this.contenedorScroll.add(this.panelAsientos);
		this.contenedorScroll.setViewportView(this.panelAsientos);

		//Panel de la izquierda
		this.contenedorPanel = new JPanel();
		this.contenedorPanel.setBorder(null);
		this.contenedorPanel.setLayout(new BoxLayout(this.contenedorPanel, BoxLayout.Y_AXIS));
		this.panelAsientos.add(this.contenedorPanel);
		
		//Panel para el despliegue
		this.panelBox = new JPanel();
		this.controlPanel.add(this.panelBox, BorderLayout.SOUTH);

		this.añadirButton = new JButton("+");
		this.añadirButton.addActionListener(new AddButtonActionListener());

		this.añadirIzquierdoStrut = Box.createHorizontalStrut(110);
		this.panelBox.add(this.añadirIzquierdoStrut);
		this.panelBox.add(this.añadirButton);

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
	
	public void añadeAsiento(String asiento){
		switch (asiento){
			
			case "AportaciónInicial": 	panelAportacion = new AportacionPanel(this, contenedorPanel, 1);
										scrollContenedor = false;
										contenedorPanel.add(panelAportacion);
										panelesAsiento.add(panelAportacion);
										contenedorPanel.revalidate();
										break;
			case "Prestamo":  	PrestamoPanel panelPrestamo = new PrestamoPanel(this, contenedorPanel, 1);
								scrollContenedor = false;
								contenedorPanel.add(panelPrestamo);
								panelesAsiento.add(panelPrestamo);
								contenedorPanel.revalidate();
								break;
			case "CompraMaterialNoAmortizable":  	MaterialNoAmortizablePanel panelMatNoAmort = new MaterialNoAmortizablePanel(this, contenedorPanel, 1);
													scrollContenedor = false;
													contenedorPanel.add(panelMatNoAmort);
													panelesAsiento.add(panelMatNoAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraMaterialAmortizable":   	MaterialAmortizablePanel panelMatAmort = new MaterialAmortizablePanel(this, contenedorPanel, 1);
													scrollContenedor = false;										
													contenedorPanel.add(panelMatAmort);
													panelesAsiento.add(panelMatAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraIntangibleNoAmortizable": 	IntangibleNoAmortizablePanel panelIntNoAmort = new IntangibleNoAmortizablePanel(this, contenedorPanel, 1);
													scrollContenedor = false;
													contenedorPanel.add(panelIntNoAmort);
													panelesAsiento.add(panelIntNoAmort);
													contenedorPanel.revalidate();
													break;
			case "CompraSWAmortizable": CompraSWPanel panelCompraSW = new CompraSWPanel(this, contenedorPanel, 1);
										scrollContenedor = false;
										contenedorPanel.add(panelCompraSW);
										panelesAsiento.add(panelCompraSW);
										contenedorPanel.revalidate();
										break;
			case "CompraPIAmortizable": CompraPIPanel panelCompraPI = new CompraPIPanel(this, contenedorPanel, 1);
										scrollContenedor = false;
										contenedorPanel.add(panelCompraPI);
										panelesAsiento.add(panelCompraPI);
										contenedorPanel.revalidate();
										break;
			case "CompraMercaderias": 	
								if(conIVA){
									CompraMercaderiasPanel panelCompraMerc = new CompraMercaderiasPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelCompraMerc);
									panelesAsiento.add(panelCompraMerc);
									contenedorPanel.revalidate();
								}else{
									CompraMercaderiasSinIVAPanel panelCompraMercSinIVA = new CompraMercaderiasSinIVAPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelCompraMercSinIVA);
									panelesAsiento.add(panelCompraMercSinIVA);
									contenedorPanel.revalidate();			
								}
								break;
			case "VentaMercaderias":  	
								if(conIVA){
									VentaMercaderiasPanel panelVentaMerc = new VentaMercaderiasPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelVentaMerc);
									panelesAsiento.add(panelVentaMerc);
									contenedorPanel.revalidate();
								}else{
									VentaMercaderiasSinIVAPanel panelVentaMercSinIVA = new VentaMercaderiasSinIVAPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelVentaMercSinIVA);
									panelesAsiento.add(panelVentaMercSinIVA);
									contenedorPanel.revalidate();
								}
								break;
			case "VentaProyecto":  	
								if(conIVA){
									VentaProyectoPanel panelVentaProy = new VentaProyectoPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelVentaProy);
									panelesAsiento.add(panelVentaProy);
									contenedorPanel.revalidate();
								}else{
									VentaProyectoSinIVAPanel panelVentaProySinIVA = new VentaProyectoSinIVAPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
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
									SueldoEmpleadosPanel panelSueldoEmpl = new SueldoEmpleadosPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelSueldoEmpl);
									panelesAsiento.add(panelSueldoEmpl);
									contenedorPanel.revalidate();
								}else{
									SueldoEmpleadosSinRetPanel panelSueldoEmplSinRet = new SueldoEmpleadosSinRetPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelSueldoEmplSinRet);
									panelesAsiento.add(panelSueldoEmplSinRet);
									contenedorPanel.revalidate();
								}
							}
							break;
			case "SueldoIgeniero": 	SueldoIngenieroPanel panelSueldoIng = new SueldoIngenieroPanel(this, contenedorPanel, 1);
									scrollContenedor = false;
									contenedorPanel.add(panelSueldoIng);
									panelesAsiento.add(panelSueldoIng);
									contenedorPanel.revalidate();
									break;
			case "Interes": 
						if (conRetenciones){
							InteresPanel panelInteres = new InteresPanel(this, contenedorPanel, 1);
							scrollContenedor = false;
							contenedorPanel.add(panelInteres);
							panelesAsiento.add(panelInteres);
							contenedorPanel.revalidate();
						}else{
							InteresSinRetPanel panelInteresSinRet = new InteresSinRetPanel(this, contenedorPanel, 1);
							scrollContenedor = false;
							contenedorPanel.add(panelInteresSinRet);
							panelesAsiento.add(panelInteresSinRet);
							contenedorPanel.revalidate();
						}
						break;
			case "NuevoSocio": 	
							if(panelAportacion==null || aportacionEjecutada==false){
								JOptionPane.showMessageDialog(null, "Para añadir un nuevo socio antes tiene que haber una aportació inicial ejecutada");
							}else{
								NuevoSocioPanel panelNuevoSoc = new NuevoSocioPanel(this, contenedorPanel, 1);
								scrollContenedor = false;
								contenedorPanel.add(panelNuevoSoc);
								panelesAsiento.add(panelNuevoSoc);
								contenedorPanel.revalidate();
							}
								break;
			case "Inventario": 	InventarioPanel panelInventario = new InventarioPanel(this, contenedorPanel, 1);
								scrollContenedor = false;
								contenedorPanel.add(panelInventario);
								panelesAsiento.add(panelInventario);
								contenedorPanel.revalidate();
								break;
			case "Dividendos": 
							if (conRetenciones){
								DividendosPanel panelDividendos = new DividendosPanel(this, contenedorPanel, 1);
								scrollContenedor = false;
								contenedorPanel.add(panelDividendos);
								panelesAsiento.add(panelDividendos);
								contenedorPanel.revalidate();
							}else{
								DividendosSinRetPanel panelDividendosSinRet = new DividendosSinRetPanel(this, contenedorPanel, 1);
								scrollContenedor = false;
								contenedorPanel.add(panelDividendosSinRet);
								panelesAsiento.add(panelDividendosSinRet);
								contenedorPanel.revalidate();	
							}
							break;
		}

	}
/*
	void eliminaMarca() {
		for (AsientoPanel<?> panel : this.panelesAsiento)
			panel.eliminarVista();
	}*/
	
	
	private class DesplegableItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == desplegable1){
				if (desplegable1.getSelectedItem().equals("Si")) {
					log.info("Configurando asiento contable con IVA");
					conIVA=true;
				}if (desplegable1.getSelectedItem().equals("No")){
					log.info("Configurando asiento contable sin IVA");
					conIVA=false;
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
		}
	}
	
	public class BotonAceptarActionListener implements ActionListener {
		public void actionPerformed (ActionEvent event){
			if (event.getSource()==botonVentana){	
				String input = introduceAno.getText();
				if("".equals(input) || input.length()!=4 ){
					JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
				}else{
					try{
						anoInicial = Integer.parseInt(input);
						initialize();
						ventana.dispose();	
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, "Introduce el año correctamente");
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
}
