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

import es.ubu.inf.tfg.asientosContables.Dividendos;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.Main;


public class DividendosPanel extends AsientoPanel<Dividendos> {

	//private static final Logger log = LoggerFactory.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField valor;
	private JTextField retencion;
	public static Dividendos dividendos;
	
	public DividendosPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

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
		mainPanel.add(new JLabel("resultado del ejercicio anterior (sobre los cuales se practica una retención del"));
		
		this.retencion = new JTextField(2);
		mainPanel.add(this.retencion);
		
		mainPanel.add(new JLabel("%). El resto se lleva a Reserva Legal."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double porcentajeValorDividendos = 0;
			double porcentajeRetencion = 0;
			
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
			
			//Porcentaje retencion
			String r = retencion.getText();
			if("".equals(r)){
				JOptionPane.showMessageDialog(null, "Introduce el % de la retención correctamente");
				ok = false;
			}else{
				try{
					porcentajeRetencion = Double.parseDouble(r);
					if(porcentajeRetencion<0 || porcentajeRetencion>100){
						JOptionPane.showMessageDialog(null, "El % de la retención debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % de la retención correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double [] inputsDividendos = {porcentajeValorDividendos, porcentajeRetencion};
				dividendos = new Dividendos (fecha, inputsDividendos, Main.enunciadoConCuentas);
				
				mostrarVista();
			}
		}
	}
}
