package es.ubu.inf.tfg.ui.panelesAsientos;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.CompraMaterialAmortizable;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase CompraMaterialAmortizablePanel que implementa lo que debe aparecer en el panel para 
 * el asiento CompraMaterialAmortizable.
 * 
 * @author Noelia Manso García
 */
public class CompraMaterialAmortizablePanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JComboBox<String> desplegable;
	private double compra = 0;
	private JTextField importe;
	private JTextField dias;
	private JTextField anos;
	/**
	 * Supuesto contable CompraMaterialAmortizable.
	 */
	public static CompraMaterialAmortizable materialAmortizable;
	
	/**
	 * Constructor de la clase CompraMaterialAmortizablePanel que genera lo que debe aparecer en el panel para 
	 * el asiento CompraMaterialAmortizable.
	 */
	public CompraMaterialAmortizablePanel(){
		this.nombre = "CompraMaterialAmortizable";
		
		inicializaPanel("Compra material amortizable");
		
		// Botón -
		this.borrarButton = new JButton("-");
		this.borrarButton.addActionListener(new BotonBorrarActionListener());
		mainPanel.add(this.borrarButton);
				
			
		// Botón >>
		this.mostrarButton = new JButton(">>");
		this.mostrarButton.setMargin(new Insets(0, 1, 0, 1));
		this.mostrarButton.addActionListener(new BotonMostrarActionListener());
		this.mostrarPanel.add(this.mostrarButton, BorderLayout.CENTER);
					
		// Fecha
		this.calendario = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		Calendar fechaMinima = Calendar.getInstance();
		fechaMinima.set(Main.anoInicial, 0, 1);
		this.calendario.setMinSelectableDate(fechaMinima.getTime());
		mainPanel.add(calendario);
				

		//Texto
		mainPanel.add(new JLabel(" La empresa compra"));
		
		
		this.desplegable = new JComboBox<>();
		this.desplegable.setModel(new DefaultComboBoxModel<String>(new String[] {"maquinaria", "mobiliario de oficina",
				"equipos para procesos de información", "un elemento de transporte" }));
		mainPanel.add(this.desplegable);
		this.desplegable.addItemListener(new DesplegablesItemListener()); 
		
		
		mainPanel.add(new JLabel ("por valor de")); 
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);
		
		mainPanel.add(new JLabel("€. El ")); 
		mainPanel.add(new JLabel("importe de la compra se abonará a los"));
			
		this.dias = new JTextField(2);
		mainPanel.add(this.dias);
		
		mainPanel.add(new JLabel ("días. La compra se amortiza"));
		mainPanel.add(new JLabel ("linealmente en"));
		
		this.anos = new JTextField(2);
		mainPanel.add(this.anos);
		
		mainPanel.add(new JLabel ("años."));
			
	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar el desplegable de la interfaz.
	 * 
	 * @author Noelia Manso García.
	 */
	private class DesplegablesItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent event) {
			//Compra
			if (event.getSource() == desplegable){
				if (desplegable.getSelectedItem().equals("maquinaria")) {
					compra=0;
				}if (desplegable.getSelectedItem().equals("mobiliario de oficina")){
					compra=1;
				}if (desplegable.getSelectedItem().equals("equipos para procesos de información")){
					compra=2;
				}if (desplegable.getSelectedItem().equals("un elemento de transporte")){
					compra=3;
				}	
			}
		}
	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double importeCompra = 0;
			double numeroDias = 0;
			double numeroAnos = 0;
			boolean ok = true;
			
			//Recojo fecha
			Date f = calendario.getDate();
			if (f == null){
				JOptionPane.showMessageDialog(null, "Introduce la fecha correctamente");
				ok = false;
			}
			if (ok){
				fecha.setTime(f);
			}
			
			//Importe de la compra
			String imp = importe.getText();
			if("".equals(imp)){
				JOptionPane.showMessageDialog(null, "Introduce el importe de la compra correctamente");
				ok = false;
			}else{
				try{
					importeCompra = Double.parseDouble(imp);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe de la compra correctamente");
					ok = false;
				}
			}
			//Numero de días
			String d = dias.getText();
			if("".equals(d)){
				JOptionPane.showMessageDialog(null, "Introduce el número de días correctamente");
				ok = false;
			}else{
				try{
					numeroDias = Double.parseDouble(d);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el número de días correctamente");
					ok = false;
				}
			}
				
			//Numero de años
			String a = anos.getText();
			if("".equals(a)){
				JOptionPane.showMessageDialog(null, "Introduce el número de años correctamente");
				ok = false;
			}else{
				try{
					numeroAnos = Double.parseDouble(a);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el número de años correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double [] inputsMaterialAmortizable = {compra, importeCompra, numeroDias, numeroAnos};
				materialAmortizable = new CompraMaterialAmortizable(fecha, inputsMaterialAmortizable, Main.enunciadoConCuentas);
				añadeEnunciado(materialAmortizable.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
