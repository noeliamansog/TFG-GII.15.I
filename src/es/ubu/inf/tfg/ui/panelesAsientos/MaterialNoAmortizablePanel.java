package es.ubu.inf.tfg.ui.panelesAsientos;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.CompraMaterialNoAmortizable;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.Main;


public class MaterialNoAmortizablePanel extends AsientoPanel<CompraMaterialNoAmortizable> {

	//private static final Logger log = LoggerFactory.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JComboBox<String> desplegable;
	private JTextField importe;
	private JTextField abono;
	private JTextField meses;
	public static CompraMaterialNoAmortizable materialNoAmortizable;
	
	
	public MaterialNoAmortizablePanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("Compra material no amortizable");
		
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
		this.desplegable.setModel(new DefaultComboBoxModel<String>(new String[] {"un solar", "un local donde instalar la oficina"}));
		mainPanel.add(this.desplegable);
		
		mainPanel.add(new JLabel ("por importe de")); 
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);
		
		mainPanel.add(new JLabel("€. Este activo"));
		mainPanel.add(new JLabel("no es amortizable. Se abonan"));
		
		this.abono = new JTextField(6);
		mainPanel.add(this.abono);
		
		mainPanel.add(new JLabel("€ mediante transferencia. Se acuerda"));
		mainPanel.add(new JLabel("que la deuda se pagará en"));
        		
		this.meses = new JTextField(2);
		mainPanel.add(this.meses);
		
		mainPanel.add(new JLabel ("meses."));

	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double compra = 0;
			double importeCompra = 0;
			double importeAbonado = 0;
			double numeroMeses = 0;
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
			
			//Compra
			if (event.getSource() == desplegable){
				if (desplegable.getSelectedItem().equals("amortización")) {
					compra=0;
				}if (desplegable.getSelectedItem().equals("pago")){
					compra=1;
				}	
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
			//Importe abonado
			String ab = abono.getText();
			if("".equals(ab)){
				JOptionPane.showMessageDialog(null, "Introduce el importe del abono correctamente");
				ok = false;
			}else{
				try{
					importeAbonado = Double.parseDouble(ab);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe del abono correctamente");
					ok = false;
				}
			}
				
			//Numero de meses
			String mess = meses.getText();
			if("".equals(mess)){
				JOptionPane.showMessageDialog(null, "Introduce el número de meses correctamente");
				ok = false;
			}else{
				try{
					numeroMeses = Double.parseDouble(mess);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el número de meses correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double [] inputsMaterialNoAmortizable = {compra, importeCompra, importeAbonado, numeroMeses};
				materialNoAmortizable = new CompraMaterialNoAmortizable(fecha, inputsMaterialNoAmortizable, Main.enunciadoConCuentas);
				
				mostrarVista();
			}
		}
	}
}
