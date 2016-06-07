package es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones;

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

import es.ubu.inf.tfg.asientosContables.sinRetenciones.DividendosSinRetenciones;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

public class DividendosSinRetPanel extends AsientoPanel<DividendosSinRetenciones> {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField valor;
	public static DividendosSinRetenciones dividendosSinRetenciones;
	
	public DividendosSinRetPanel(){
		this.nombre = "DividendosSinRetenciones";
		
		inicializaPanel("Dividendos");
		
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
		mainPanel.add(new JLabel(" Se decide repartir dividendos por valor del"));
		
		this.valor = new JTextField(2);
		mainPanel.add(this.valor);
		
		mainPanel.add(new JLabel("% del"));
		mainPanel.add(new JLabel("resultado del ejercicio anterior."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double porcentajeValorDividendos = 0;
			
			boolean ok = true;
			
			//Recojo fecha
			Date f = calendario.getDate();
			if (f == null){
				JOptionPane.showMessageDialog(null, "Introduce la fecha correctamente");
				ok = false;
			}
			if (ok){
				fecha.setTime(f);
				if(fecha.get(Calendar.YEAR) == Main.anoInicial){
					JOptionPane.showMessageDialog(null, "Los dividendos solo se pueden hacer a partir del segundo año: "+(Main.anoInicial+1));
					ok=false;
				}
			}
			
			//Porcentaje del valor de los dividendos
			String v = valor.getText();
			if("".equals(v)){
				JOptionPane.showMessageDialog(null, "Introduce el % del resultado del año anterior correctamente");
				ok = false;
			}else{
				try{
					porcentajeValorDividendos = Double.parseDouble(v);	
					if(porcentajeValorDividendos<0 || porcentajeValorDividendos>100){
						JOptionPane.showMessageDialog(null, "El % del valor de los dividendos debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % del resultado del año anterior correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double [] inputsDividendosSinR = {porcentajeValorDividendos};
				dividendosSinRetenciones = new DividendosSinRetenciones (fecha, inputsDividendosSinR, Main.enunciadoConCuentas);
				añadeEnunciado(dividendosSinRetenciones.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				anoDividendo.add(fecha.get(Calendar.YEAR));
				mostrarVista();
			}
		}
	}
}
