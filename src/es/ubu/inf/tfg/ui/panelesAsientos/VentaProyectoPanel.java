package es.ubu.inf.tfg.ui.panelesAsientos;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.VentaProyecto;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase VentaProyectoPanel que implementa lo que debe aparecer en el panel para el asiento VentaProyecto.
 * 
 * @author Noelia Manso García
 */
public class VentaProyectoPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField importe;
	private JTextField dias;
	/**
	 * Supuesto contable VentaProyecto
	 */
	public static VentaProyecto ventaProyecto;
	
	/**
	 * Constructor de la clase VentaProyectoPanel que genera lo que debe aparecer en el panel para 
	 * el asiento VentaProyecto.
	 */
	public VentaProyectoPanel(){
		this.nombre = "VentaProyecto";
		
		inicializaPanel("Venta proyecto");	
		
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
		mainPanel.add(new JLabel(" La empresa entrega un proyecto a un cliente, por el"));
		mainPanel.add(new JLabel("cual factura"));
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);
		
		mainPanel.add(new JLabel("€ más un "+Main.IVA+"% de IVA. Se acuerda que el cliente"));
		mainPanel.add(new JLabel ("pague en"));
		
		this.dias = new JTextField(2);
		mainPanel.add(this.dias);
		
		mainPanel.add(new JLabel ("días."));
	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
	private class BotonMostrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double importeVenta = 0;
			double numeroDias = 0;
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
				JOptionPane.showMessageDialog(null, "Introduce el importe de la venta correctamente");
				ok = false;
			}else{
				try{
					importeVenta = Double.parseDouble(imp);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe de la venta correctamente");
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
		
				
			if(ok){
				double [] inputsVentaProyecto = {importeVenta, numeroDias};
				ventaProyecto = new VentaProyecto(fecha, inputsVentaProyecto, Main.enunciadoConCuentas);
				añadeEnunciado(ventaProyecto.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
