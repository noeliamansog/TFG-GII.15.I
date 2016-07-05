/* GSC
 * GSC es una aplicación que permite la creación de supuestos contables 
 * personalizados y los resuelve de forma automática.
 * Copyright (C) 2016 Noelia Manso & Luis R. Izquierdo
 *
 * This file is part of GSC.
 *
 * GSC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSC.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.ubu.inf.tfg.ui.panelesAsientos;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.doc.Enunciado;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase DividendosPanel que implementa lo que debe aparecer en el panel para el asiento Dividendos.
 * 
 * @author Noelia Manso García
 */
public class DividendosPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField valor;
	private JTextField retencion;
	/**
	 * Enunciado del asiento contable Dividendos.
	 */
	private ArrayList<Enunciado> enunciado = new ArrayList<Enunciado>();
	
	/**
	 * Constructor de la clase DividendosPanel que genera lo que debe aparecer en el panel para 
	 * el asiento Dividendos.
	 */
	public DividendosPanel(){
		this.nombre ="Dividendos";
		
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
		this.calendario.setDate(fechaMinima.getTime());
		mainPanel.add(calendario);
		

		//CON RETENCIONES
		if(Main.conRetenciones){
			//Texto
			mainPanel.add(new JLabel(" Se decide repartir dividendos por valor del"));
		
			this.valor = new JTextField(2);
			mainPanel.add(this.valor);
		
			mainPanel.add(new JLabel("%"));
			mainPanel.add(new JLabel("del resultado del ejercicio anterior (sobre los cuales se practica una retención"));
			mainPanel.add(new JLabel("del"));
			this.retencion = new JTextField(2);
			mainPanel.add(this.retencion);
		
			mainPanel.add(new JLabel("%). El resto se lleva a Reserva Legal."));
		
		//SIN RETENCIONES	
		}else{
			//Texto
			mainPanel.add(new JLabel(" Se decide repartir dividendos por valor del"));
			
			this.valor = new JTextField(2);
			mainPanel.add(this.valor);
			
			mainPanel.add(new JLabel("%"));
			mainPanel.add(new JLabel("del resultado del ejercicio anterior."));
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
				for(int i=0; i<anoDividendo.size(); i++){
					if(fecha.get(Calendar.YEAR) == anoDividendo.get(i)){
						JOptionPane.showMessageDialog(null, "Solo se puede generar un asiento dividendo por año");
						ok=false;
					}
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
			
			if(Main.conRetenciones){
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
			}
				
			if(ok){				
				//Introduzco sólo el enunciado en el panel de la interfaz
				String enunciado1 = null;
				//Con retenciones
				if(Main.conRetenciones){
					double [] inputsDividendos = {porcentajeValorDividendos, porcentajeRetencion};
					enunciado1 = " Se decide repartir dividendos por valor del " +inputsDividendos[0]+ "% del resultado del "
						+ "ejercicio anterior (sobre los cuales se practica una retención del " +inputsDividendos[1]+ "%). El resto se lleva a Reserva Legal.\n";
					if (Main.enunciadoConCuentas){
						enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 129. Resultados del ejercicio;"
											+ "112. Reserva legal; 4751. H.P acreedor por retenciones practicadas; 12. Resultados pendientes de aplicación.\n";
					}
					Main.datosDividendos.put(fecha, inputsDividendos);
				//Sin retenciones	
				}else{
					double [] inputsDividendosSinR = {porcentajeValorDividendos};
					enunciado1 = " Se decide repartir dividendos por valor del " +inputsDividendosSinR[0]+ "% del resultado del "
								+ "ejercicio anterior \n";
					if (Main.enunciadoConCuentas){
						enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 129. Resultados del ejercicio;"
												+ "12. Resultados pendientes de aplicación.\n";
					}
					Main.datosDividendosSinRet.put(fecha, inputsDividendosSinR);
				}
				enunciado.add(new Enunciado(fecha, enunciado1));
				añadeEnunciado(enunciado);
				
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				anoDividendo.add(fecha.get(Calendar.YEAR));
				mostrarVista();
			}
		}
	}
}
