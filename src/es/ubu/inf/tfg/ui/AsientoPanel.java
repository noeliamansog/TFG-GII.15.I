package es.ubu.inf.tfg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public class BotonBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			contenedorPanel.remove(actualPanel);
			contenedorPanel.revalidate();
		}
	}
	
}