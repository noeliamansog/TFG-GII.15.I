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

import es.ubu.inf.tfg.asientosContables.CompraIntangibleNoAmortizable;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;


public class IntangibleNoAmortizablePanel extends AsientoPanel<CompraIntangibleNoAmortizable> {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField valor;
	public static CompraIntangibleNoAmortizable intangibleNoAmortizable;
	
	
	public IntangibleNoAmortizablePanel(){
		this.nombre = "CompraIntangibleNoAmortizable";
		
		inicializaPanel("Compra intangible no amortizable");
		
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
		mainPanel.add(new JLabel(" La empresa compra a una Administración Pública "));
		mainPanel.add(new JLabel("el derecho de explotación de un terreno por valor de"));
		
		this.valor = new JTextField(6);
		mainPanel.add(this.valor);
		
		mainPanel.add(new JLabel("€. "));
		mainPanel.add(new JLabel("Se paga al contado."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double valorCompra = 0;
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
			
			//Valor de la compra
			String v = valor.getText();
			if("".equals(v)){
				JOptionPane.showMessageDialog(null, "Introduce el valor de la compra correctamente");
				ok = false;
			}else{
				try{
					valorCompra = Double.parseDouble(v);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el valor de la compra correctamente");
					ok = false;
				}
			}
			if(ok){
				double [] inputsIntangibleNoAmortizable = {valorCompra};
				intangibleNoAmortizable = new CompraIntangibleNoAmortizable(fecha, inputsIntangibleNoAmortizable, Main.enunciadoConCuentas);
				añadeEnunciado(intangibleNoAmortizable.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
