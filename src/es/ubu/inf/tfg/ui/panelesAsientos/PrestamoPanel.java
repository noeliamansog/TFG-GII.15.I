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

import es.ubu.inf.tfg.asientosContables.Prestamo;
import es.ubu.inf.tfg.main.Main;
import es.ubu.inf.tfg.ui.AsientoPanel;



public class PrestamoPanel extends AsientoPanel<Prestamo> {

	private static final long serialVersionUID = -1805230103073818602L;

	
	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField importe;
	private JComboBox<String> desplegable1;
	private JComboBox<String> desplegable2;
	private double tipoPrestamo = 0;
	private double mensualAnual = 0;
	private JTextField anos;
	private JTextField interes;
	public static Prestamo prestamo;
	
	
	public PrestamoPanel(){
		this.nombre ="Prestamo";
		
		inicializaPanel("Préstamo");

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
		mainPanel.add(new JLabel(" La empresa obtiene un préstamo por importe de"));
		
		importe = new JTextField(6);
		mainPanel.add(importe);
		
		mainPanel.add(new JLabel ("€, que se ingresa en la cuenta corriente de la que es titular la "));
		mainPanel.add(new JLabel ("empresa. El préstamo se devolverá en cuotas de")); 
			
		this.desplegable1 = new JComboBox<>();
		this.desplegable1.setModel(new DefaultComboBoxModel<String>(new String[] {"amortización", "pago"}));
		mainPanel.add(this.desplegable1);
		this.desplegable1.addItemListener(new DesplegablesItemListener()); 
		
        
		this.desplegable2 = new JComboBox<>();
		this.desplegable2.setModel(new DefaultComboBoxModel<String>(new String[] {"mensual", "anual"}));
		mainPanel.add(this.desplegable2);
		this.desplegable2.addItemListener(new DesplegablesItemListener()); 
		

		mainPanel.add(new JLabel("constantes, en"));
		
		anos = new JTextField(2);
		mainPanel.add(anos);
		
		mainPanel.add(new JLabel ("años, a un tipo de interés fijo del"));
		
		interes = new JTextField(2);
		mainPanel.add(interes);
		
		
		mainPanel.add(new JLabel("% nominal."));
		mainPanel.add(new JLabel("El primer pago se realizará al cabo de un mes o año desde la concesión del"));
		mainPanel.add(new JLabel("préstamo."));
	}
	
	private class DesplegablesItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			//Tipo de prestamo
			if (e.getSource() == desplegable1){
				if (desplegable1.getSelectedItem().equals("amortización")) {
					tipoPrestamo = 0;
				}if(desplegable1.getSelectedItem().equals("pago")){
					tipoPrestamo = 1;
				}
			}
			//Mensual o anual
			if (e.getSource() == desplegable2){
				if (desplegable2.getSelectedItem().equals("mensual")) {
					mensualAnual = 0;
				}if(desplegable2.getSelectedItem().equals("anual")){
					mensualAnual = 1;
				}
			}
		}
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double importePrestamo = 0;
			double numeroAnos = 0;
			double interesFijo = 0;
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
			
			//Recojo el importe
			String imp = importe.getText();
			if("".equals(imp)){
				JOptionPane.showMessageDialog(null, "Introduce el importe del préstamo correctamente");
				ok = false;
			}else{
				try{
					importePrestamo = Double.parseDouble(imp);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe del préstamo correctamente");
					ok = false;
				}
			}
			
			//Numero de años
			String numAnos = anos.getText();
			if("".equals(numAnos)){
				JOptionPane.showMessageDialog(null, "Introduce el número de años correctamente");
				ok = false;
			}else{
				try{
					numeroAnos = Double.parseDouble(numAnos);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el número de años correctamente");
					ok = false;
				}
			}
			
			//Interés Fijo
			String ints = interes.getText();
			if("".equals(ints)){
				JOptionPane.showMessageDialog(null, "Introduce el interés fijo correctamente");
				ok = false;
			}else{
				try{
					interesFijo = Double.parseDouble(ints);
					if(interesFijo<0 || interesFijo>100){
						JOptionPane.showMessageDialog(null, "El % del interes fijo debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el interés fijo correctamente");
					ok = false;
				}
			}
			
			if(ok){
				double [] inputsPrestamo = {importePrestamo, tipoPrestamo, mensualAnual, numeroAnos, interesFijo};
				prestamo = new Prestamo(fecha, inputsPrestamo, Main.enunciadoConCuentas);
				añadeEnunciado(prestamo.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
			
		}
	}
}
