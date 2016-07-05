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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.CompraSWAmortizable;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase CompraSWPanel que implementa lo que debe aparecer en el panel para el asiento CompraSWAmortizable.
 * 
 * @author Noelia Manso García
 */
public class CompraSWPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField valor;
	private JTextField dias;
	private JTextField anos;
	/**
	 * Asiento contable CompraSWAmortizable.
	 */
	public static CompraSWAmortizable softwareAmortizable;
	
	/**
	 * Constructor de la clase CompraSWPanel que genera lo que debe aparecer en el panel para 
	 * el asiento CompraSWAmortizable
	 */
	public CompraSWPanel(){
		this.nombre = "CompraSWAmortizable";
		
		inicializaPanel("Compra software amortizable");	
		
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
		

		//Texto
		mainPanel.add(new JLabel(" La empresa compra una aplicación informática por"));
		mainPanel.add(new JLabel("valor de"));
		
		this.valor = new JTextField(6);
		mainPanel.add(this.valor);
		
		mainPanel.add(new JLabel("€. El importe de la compra se abonará a los"));
		
		this.dias = new JTextField(2);
		mainPanel.add(this.dias);
		
		mainPanel.add(new JLabel ("días. ")); 
		mainPanel.add(new JLabel ("El software se amortiza linealmente en"));
		
		this.anos = new JTextField(2);
		mainPanel.add(this.anos);
		
		mainPanel.add(new JLabel ("años."));
	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double valorCompra = 0;
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
				double [] inputsSoftwareAmortizable = {valorCompra, numeroDias, numeroAnos};
				softwareAmortizable = new CompraSWAmortizable(fecha, inputsSoftwareAmortizable, Main.enunciadoConCuentas);
				añadeEnunciado(softwareAmortizable.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
