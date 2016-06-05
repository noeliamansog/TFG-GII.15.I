package es.ubu.inf.tfg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import es.ubu.inf.tfg.doc.Enunciado;


@SuppressWarnings("serial")
public class AsientoPanel<Asiento> extends JPanel {

	private Border seleccionado;
	private Border noSeleccionado;
	protected int numero;
	
	protected JSlider simbolosSlider;
	protected JSlider estadosSlider;
	protected JLabel estadosEstadoLabel;
	protected JLabel simbolosEstadoLabel;
	protected Main main;
	protected JPanel contenedorPanel;
	protected JPanel actualPanel = this;
	protected Asiento asientoActual = null;
	protected JRadioButton modoA;
	protected JRadioButton modoB;
	protected JRadioButton modoC;
	protected JPanel mainPanel;
	protected JPanel ordenPanel;
	protected JPanel mostrarPanel;
	protected JButton arribaButton;
	protected JButton abajoButton;
	//protected JButton mostrarButton;
	
	public static ArrayList<ArrayList<Enunciado>> listaEnunciados = new ArrayList<ArrayList<Enunciado>>();
	public static int anoFin=0;
	

	public AsientoPanel() {
		super();
	}
	
	public AsientoPanel(LayoutManager layout) {
		super(layout);
	}

	public AsientoPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public AsientoPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public void inicializaPanel(String titulo) {
		this.noSeleccionado = new CompoundBorder(
				new EmptyBorder(5, 2, 15, 2), new TitledBorder(new LineBorder(
						new Color(0, 0, 0), 1, true), titulo, TitledBorder.LEADING, TitledBorder.TOP, null,
						new Color(51, 51, 51)));
		this.seleccionado = new CompoundBorder(new EmptyBorder(5, 2, 15, 2), new TitledBorder(
				new LineBorder(new Color(0, 255, 0), 4, true), titulo, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51)));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		
		
		this.ordenPanel = new JPanel();
		this.ordenPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		this.ordenPanel.setLayout(new BoxLayout(this.ordenPanel, BoxLayout.Y_AXIS));
		add(this.ordenPanel);

		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(this.noSeleccionado);
		//this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		//DIMENSIOOONESSSS
		Dimension dim = new Dimension(525,200) ;
		this.mainPanel.setPreferredSize(dim);
		
	
		add(this.mainPanel);

		
		this.mostrarPanel = new JPanel();
		this.mostrarPanel.setBorder(new EmptyBorder(new Insets(13, 0, 16, 0)));
		this.mostrarPanel.setLayout(new BorderLayout());
		add(this.mostrarPanel);
		
		/*
		this.mostrarButton = new JButton(">>");
		this.mostrarButton.setMargin(new Insets(0, 1, 0, 1));
		this.mostrarButton.addActionListener(new BotonMostrarActionListener());
		this.mostrarPanel.add(this.mostrarButton, BorderLayout.CENTER);
		*/

	}

	public void mostrarVista() {
		//main.eliminaMarca();
		mainPanel.setBorder(seleccionado);
	}
	
	void eliminarVista() {
		this.mainPanel.setBorder(this.noSeleccionado);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	/*
	private class BotonMostrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			mostrarVista();
		}
	}*/
	
	
	public void añadeEnunciado (ArrayList<Enunciado> enunciado){		
		ArrayList<Enunciado> listaEnunciadosOrdenados = new ArrayList<Enunciado>();
		listaEnunciados.add(enunciado);
		
		Calendar fech;
		String enun;
		
		//Calendar fechaLimite = Calendar.getInstance();
		//fechaLimite.set(anoFin,11,31);
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
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
		
			
		int anoTemp1 = Main.anoInicial;
		int anoTemp2 = 0;
		Calendar fechaCierre = Calendar.getInstance();
			 
		for(int i=0; i<listaEnunciadosOrdenados.size(); i++){
			//Nuevo año
			if(listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==anoTemp1 && anoTemp1<=anoFin){
				Main.textoEnunciado = Main.textoEnunciado +"<b>Se tiene la siguiente información vinculada con la empresa correspondiente al año "+anoTemp1+":</b><br><br>";
				anoTemp1++;
			}
				
			//Imprime asientos
			fech= listaEnunciadosOrdenados.get(i).getFecha();
			enun = listaEnunciadosOrdenados.get(i).getEnunciado();
			Main.textoEnunciado = Main.textoEnunciado + formateador.format(fech.getTime())+ " " + enun + "<br><br>";
			
			//Cierre
			if(listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)!=anoTemp2 && anoTemp2<=anoFin){
				fechaCierre.set(anoTemp2,11,31);
				if(Main.enunciadoConCuentas){
					if(Main.conRetenciones){
						Main.textoEnunciado = Main.textoEnunciado + formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades (+impuestoSociedad+% del beneficio).\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.<br><br>";
					}else{
						Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.<br><br>";
					}
				}else{
					if(Main.conRetenciones){
						Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades (+impuestoSociedad+% del beneficio).<br><br>";	
					}else{
						Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.<br><br>";
					}
				}
				anoTemp2=listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR);
				
			}
			
			
		}
		//Imprime enunciado
		Main.textoEnunciado = Main.textoEnunciado +"<br><br>Se pide: <br>"
				 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
				 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
				 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. ";
		if(Main.conRetenciones && Main.conIVA){
			Main.textoEnunciado = Main.textoEnunciado +"El impuesto de sociedades es el +impuestoSociedad+ % del beneficio y el IVA es +IVA+ %.";
		}if(Main.conRetenciones==false && Main.conIVA){
			Main.textoEnunciado = Main.textoEnunciado + "El IVA es  +Main.IVA+ %.\n"; 
		}
		if(Main.conRetenciones && Main.conIVA==false){
			Main.textoEnunciado  = Main.textoEnunciado +"El impuesto de sociedades es el +impuestoSociedad+ % del beneficio.";				 
		}
		Main.textoEnunciado = Main.textoEnunciado + "<br>  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
				 + "que se cumple la ecuación que las liga. <br>"
				 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.<br>"
				 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.<br>"
				 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.<br>"
				 + "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
				 + "acción emitida el +formateador.format(fechaLimite.getTime())"
				 + ". ¿Cuál sería el valor de mercado de las acciones? <br>";
		
		Main.panelEnunciado.setText(Main.textoEnunciado);
		Main.textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciados de los asientos contable<br><br></b></p>";
		

	}	
	
	
	public class BotonBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			contenedorPanel.remove(actualPanel);
			contenedorPanel.revalidate();
			//Main.panelEnunciado.eliminarEnunciado;	
		}
	}
	
}