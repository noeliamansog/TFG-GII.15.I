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
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.CompraMercaderias;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.Main;


public class CompraMercaderiasPanel extends AsientoPanel<CompraMercaderias> {

	//private static final Logger log = LoggerFactory.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField importe;
	private JTextField iva;
	private JTextField dias;
	public static CompraMercaderias compraMercaderias;
	
	
	public CompraMercaderiasPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("Compra mercaderias");	
		
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
		mainPanel.add(new JLabel(" La empresa compra mercaderías por un importe de"));
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);
		
		mainPanel.add(new JLabel("€ mas un"));
		
		this.iva = new JTextField(2);
		mainPanel.add(this.iva);
		
		mainPanel.add(new JLabel("% de IVA. Se acuerda que el pago se realice en"));
		
		this.dias = new JTextField(2);
		mainPanel.add(this.dias);
		
		mainPanel.add(new JLabel ("días. Se paga al contado."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double importeCompra = 0;
			double porcentajeIVA = 0;
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
			
			//Porcenjate IVA
			String i = iva.getText();
			if("".equals(i)){
				JOptionPane.showMessageDialog(null, "Introduce el % del IVA correctamente");
				ok = false;
			}else{
				try{
					porcentajeIVA = Double.parseDouble(i);
					if(porcentajeIVA<0 || porcentajeIVA>100){
						JOptionPane.showMessageDialog(null, "El % del IVA debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % del IVA correctamente");
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
				double [] inputsCompraMercaderias = {importeCompra, porcentajeIVA, numeroDias};
				compraMercaderias = new CompraMercaderias(fecha, inputsCompraMercaderias, Main.enunciadoConCuentas);
				añadeEnunciado(compraMercaderias.enunciados);
				mostrarVista();
			}
		}
	}
}
