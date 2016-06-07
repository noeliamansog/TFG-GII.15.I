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

import es.ubu.inf.tfg.asientosContables.CompraPIAmortizable;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;


public class CompraPIPanel extends AsientoPanel<CompraPIAmortizable> {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField importe;
	private JTextField dias;
	private JTextField anos;
	public static CompraPIAmortizable propiedadIndustrialAmortizable;
	
	
	public CompraPIPanel(){
		this.nombre = "CompraPIAmortizable";
		
		inicializaPanel("Compra propiedad industrial amortizable");	
		
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
		mainPanel.add(new JLabel(" La empresa adquiere el derecho a usar un logotipo,"));
		mainPanel.add(new JLabel("por lo cual paga"));
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);
		
		mainPanel.add(new JLabel("€. El importe de la compra se abonará a los"));
		
		this.dias = new JTextField(2);
		mainPanel.add(this.dias);
		
		mainPanel.add(new JLabel ("días.  La propiedad industrial se amortiza linealmente en"));
		
		this.anos = new JTextField(2);
		mainPanel.add(this.anos);
		
		mainPanel.add(new JLabel ("años."));

	}
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
				double [] inputsPropiedadIndustrialAmortizable = {importeCompra, numeroDias, numeroAnos};
				propiedadIndustrialAmortizable = new CompraPIAmortizable(fecha, inputsPropiedadIndustrialAmortizable, Main.enunciadoConCuentas);
				añadeEnunciado(propiedadIndustrialAmortizable.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
