package es.ubu.inf.tfg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AportacionPanel extends JPanel{

	private static final Logger log = LoggerFactory
			.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -8899275410326830826L;

	private boolean generando = false;
	
	private JPanel expresionPanel;
	private Main main;
	private JPanel contenedorPanel;
	private int numero;
	private JPanel botonesPanel;
	private JButton generarButton;
	private JButton resolverButton;
	private JButton borrarButton;
	private JPanel opcionesPanel;
	private JPanel vacioPanel;
	private JLabel texto1; 
	private JLabel texto2;
	private JLabel texto3;
	private JLabel texto4;
	private JTextField aportacionText1;
	private JTextField aportacionText2;
	private JTextField aportacionText3;
	private JCheckBox vacioCheck;
	private JPanel simbolosPanel;
	private JLabel simbolosLabel;
	private JPanel estadosPanel;
	private JLabel estadosLabel;
	private JPanel progresoPanel;
	private JProgressBar progresoBar;
	private JPanel modoPanelA;
	private final ButtonGroup modoGroup = new ButtonGroup();
	private JPanel modoPanelB;
	private JPanel mainPanel;
	private JPanel ordenPanel;
	private CompoundBorder noSeleccionado;
	private CompoundBorder seleccionado;
	private JButton abajoButton;
	private JPanel mostrarPanel;
	private JButton mostrarButton;

	
	public AportacionPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("Aportacion Inicial");


		this.expresionPanel = new JPanel();
		this.mainPanel.add(this.expresionPanel);
		this.expresionPanel.setBounds(0, 0, 3, 3);
		//this.expresionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.expresionPanel.setLayout(new BoxLayout(this.expresionPanel,BoxLayout.Y_AXIS));

		this.borrarButton = new JButton("-");
		//this.borrarButton.addActionListener(new BotonBorrarActionListener());
		this.expresionPanel.add(this.borrarButton);

		
		this.texto1 = new JLabel();
		texto1.setText("Cada uno de los ");
		this.expresionPanel.add(this.texto1);
		
		
		this.aportacionText1 = new JTextField(); 
		this.expresionPanel.add(this.aportacionText1);
		//this.expresionText.addActionListener(new BotonResolverActionListener());
		this.aportacionText1.setColumns(2);
		
		this.texto2 = new JLabel();
		texto2.setText("socios fundadores realiza una aportación inicial en efectivo a favor de la empresa por importe de ");
		this.expresionPanel.add(this.texto2);
		
		this.aportacionText2 = new JTextField(); 
		this.expresionPanel.add(this.aportacionText2);
		//this.expresionText.addActionListener(new BotonResolverActionListener());
		this.aportacionText2.setColumns(10);
		
		this.texto3 = new JLabel();
		texto3.setText("€ ( ");
		this.expresionPanel.add(this.texto3);
		
		this.aportacionText3 = new JTextField(); 
		this.expresionPanel.add(this.aportacionText3);
		//this.expresionText.addActionListener(new BotonResolverActionListener());
		this.aportacionText3.setColumns(10);
		
		this.texto4 = new JLabel();
		texto4.setText("€ en total).Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente.");
		this.expresionPanel.add(this.texto4);
		

	
	}

	/*void problema(Problema<AhoSethiUllman> problema) {
		if (problemaActual != null) {
			if (!problema.getProblema().equals(problemaActual))
				main.eliminaImagen(problemaActual.getProblema().arbolVacio());
		}

		switch (problema.getTipo()) {
		case "AhoSethiUllmanConstruccion":
			for (BufferedImage imagen : problema.getProblema().alternativas())
				main.añadeImagen(imagen);
			modoA.setSelected(true);
			break;
		case "AhoSethiUllmanTablas":
			modoB.setSelected(true);
			break;
		case "AhoSethiUllmanEtiquetado":
			main.añadeImagen(problema.getProblema().arbolVacio());
			modoC.setSelected(true);
			break;
		default:
			log.error(
					"Error identificando tipo de problema Aho-Sethi-Ullman, definido como {}",
					problema.getTipo());
		}

		problemaActual = problema;
		expresionText.setText(problema.getProblema().problema());
	}

	private class BotonGenerarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (!generando) {
				worker = new Worker();
				worker.execute();
			} else {
				((Worker) worker).cancel();
			}
		}
	}

	private class BotonResolverActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String expresion = expresionText.getText();

			try {
				if (expresion.length() > 0) {
					if (problemaActual != null) {
						if (!expresion.equals(problemaActual.getProblema()
								.problema())) {
							AhoSethiUllman problema = new AhoSethiUllman(
									expresion);
							Problema<AhoSethiUllman> asuProblema;

							if (modoB.isSelected())
								asuProblema = Problema.asuTablas(problema, numero);
							else if (modoC.isSelected())
								asuProblema = Problema.asuEtiquetado(problema, numero);
							else
								asuProblema = Problema
										.asuConstruccion(problema, numero);

							main.eliminaImagen(problemaActual.getProblema()
									.arbolVacio());
							for (BufferedImage imagen : problemaActual
									.getProblema().alternativas())
								main.eliminaImagen(imagen);
							main.añadeImagen(problema.arbolVacio());
							for (BufferedImage imagen : problema.alternativas())
								main.añadeImagen(imagen);
							problemaActual = asuProblema;
						}
					} else {
						AhoSethiUllman problema = new AhoSethiUllman(expresion);
						Problema<AhoSethiUllman> asuProblema;

						if (modoB.isSelected())
							asuProblema = Problema.asuTablas(problema, numero);
						else if (modoC.isSelected())
							asuProblema = Problema.asuEtiquetado(problema, numero);
						else
							asuProblema = Problema.asuConstruccion(problema, numero);

						main.añadeImagen(problema.arbolVacio());
						for (BufferedImage imagen : problema.alternativas())
							main.añadeImagen(imagen);
						problemaActual = asuProblema;
					}
					mostrarVista();
				}
			} catch (UnsupportedOperationException e) {
				JOptionPane
						.showMessageDialog(
								actualPanel,
								"Expresi�n regular no valida.",
								"Error", JOptionPane.ERROR_MESSAGE);
//				expresionText.setText("");
			}
		}
	}

	/**
	 * Implementa un SwingWorker cancelable encargado de generar problemas de
	 * tipo AhoSethiUllman de manera concurrente, y de actualizar la interfaz en
	 * consecuencia.
	 * 
	 * @author Roberto Izquierdo Amo.
	 *
	 
	private class Worker extends SwingWorker<AhoSethiUllman, Void> {

		private AhoSethiUllmanGenerador generador;

		@Override
		protected AhoSethiUllman doInBackground() throws Exception {
			generando = true;
			generarButton.setText("Cancelar");
			generador = new AhoSethiUllmanGenerador();
			int nSimbolos = simbolosSlider.getValue();
			int nEstados = estadosSlider.getValue();
			boolean usaVacio = vacioCheck.isSelected();
			progresoBar.setVisible(true);

			AhoSethiUllman problema = generador.nuevo(nSimbolos, nEstados,
					usaVacio);
			return problema;
		}

		@Override
		public void done() {
			AhoSethiUllman problema = null;
			Problema<AhoSethiUllman> asuProblema = null;
			try {
				problema = get();
				if (modoB.isSelected())
					asuProblema = Problema.asuTablas(problema, numero);
				else if (modoC.isSelected())
					asuProblema = Problema.asuEtiquetado(problema, numero);
				else
					asuProblema = Problema.asuConstruccion(problema, numero);

				if (problemaActual != null) {
					main.eliminaImagen(problemaActual.getProblema()
							.arbolVacio());
					for (BufferedImage imagen : problemaActual.getProblema()
							.alternativas())
						main.eliminaImagen(imagen);
				}
				main.añadeImagen(asuProblema.getProblema().arbolVacio());
				for (BufferedImage imagen : asuProblema.getProblema()
						.alternativas())
					main.añadeImagen(imagen);

				problemaActual = asuProblema;
				expresionText.setText(problema.problema());
				mostrarVista();
			} catch (InterruptedException | ExecutionException
					| CancellationException e) {
				log.error("Error generando problema de tipo AhoSethiUllman", e);
			} finally {
				generando = false;
				generarButton.setText("Generar");
				progresoBar.setVisible(false);
			}
		}

		public void cancel() {
			log.info("Cancelando generaci�n de problema AhoSethiUllman.");
			generador.cancelar();
		}
	}

	private class ModoButtonChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton modoButton = (JRadioButton) e.getSource();
			Problema<AhoSethiUllman> asuProblema = null;
			if(problemaActual != null) {
				AhoSethiUllman problema = problemaActual.getProblema();
	
				if (modoC == modoButton) {
					log.info("Seleccionado modo etiquetado en problema de Aho-Sethi-Ullman numero {}.", numero);
					if (problemaActual != null) {
						asuProblema = Problema.asuEtiquetado(problema, numero);
					}
				} else if (modoB == modoButton) {
					log.info("Seleccionado modo tablas en problema de Aho-Sethi-Ullman numero {}.", numero);
					if (problemaActual != null) {
						asuProblema = Problema.asuTablas(problema, numero);
					}
				} else {
					log.info("Seleccionado modo construcción en problema de Aho-Sethi-Ullman numero {}.", numero);
					if (problemaActual != null) {
						asuProblema = Problema.asuConstruccion(problema, numero);
					}
				}
				problemaActual = asuProblema;
			}
			mostrarVista();
		}
	}*/
	void inicializaPanel(String titulo) {
		this.noSeleccionado = new CompoundBorder(
				new EmptyBorder(5, 2, 15, 2), new TitledBorder(new LineBorder(
						new Color(0, 0, 0), 1, true), titulo,
						TitledBorder.LEADING, TitledBorder.TOP, null,
						new Color(51, 51, 51)));
		this.seleccionado = new CompoundBorder(
				new EmptyBorder(5, 2, 15, 2), new TitledBorder(new LineBorder(
						new Color(255, 0, 0), 4, true), titulo,
						TitledBorder.LEADING, TitledBorder.TOP, null,
						new Color(51, 51, 51)));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

		this.ordenPanel = new JPanel();
		this.ordenPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		this.ordenPanel.setLayout(new BoxLayout(this.ordenPanel,
				BoxLayout.Y_AXIS));
		add(this.ordenPanel);

		JButton arribaButton = new JButton("<<");
		arribaButton.setMargin(new Insets(0, 1, 0, 1));
		//arribaButton.addActionListener(new BotonArribaActionListener());
		this.ordenPanel.add(arribaButton);

		this.abajoButton = new JButton(">>");
		this.abajoButton.setMargin(new Insets(0, 1, 0, 1));
		//this.abajoButton.addActionListener(new BotonAbajoActionListener());
		this.ordenPanel.add(this.abajoButton);

		this.mainPanel = new JPanel();
		((JComponent) this.mainPanel).setBorder(this.noSeleccionado);
		this.mainPanel
				.setLayout(new BoxLayout((Container) this.mainPanel, BoxLayout.Y_AXIS));
		add((Component) this.mainPanel);

		this.mostrarPanel = new JPanel();
		this.mostrarPanel.setBorder(new EmptyBorder(new Insets(13, 0, 16, 0)));
		this.mostrarPanel.setLayout(new BorderLayout());
		add(this.mostrarPanel);

		this.mostrarButton = new JButton(">>");
		this.mostrarButton.setMargin(new Insets(0, 1, 0, 1));
		//this.mostrarButton.addActionListener(new BotonMostrarActionListener());
		this.mostrarPanel.add(this.mostrarButton, BorderLayout.CENTER);
	}
}
