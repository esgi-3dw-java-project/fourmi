package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import appli.Simulation;
import javax.swing.JToolBar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Gui {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lunch simulation
			}
		});
		btnStart.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
		btnStart.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
		btnStop.setBounds(236, 228, 89, 23);
		frame.getContentPane().add(btnStop);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(10, 29, 105, 23);
		frame.getContentPane().add(spinner);
		
		JLabel lblNumeraPasSimulation = new JLabel("Numera pas simulation");
		lblNumeraPasSimulation.setBounds(10, 10, 115, 23);
		frame.getContentPane().add(lblNumeraPasSimulation);
		
		textField = new JTextField();
		textField.setBounds(10, 171, 45, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNouritureRaporte = new JLabel("Nouriture raport\u00E9e");
		lblNouritureRaporte.setBounds(10, 151, 105, 23);
		frame.getContentPane().add(lblNouritureRaporte);
		
		JLabel lblNouritureRestante = new JLabel("Nouriture restante");
		lblNouritureRestante.setBounds(121, 151, 89, 23);
		frame.getContentPane().add(lblNouritureRestante);
		
		textField_1 = new JTextField();
		textField_1.setBounds(124, 171, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblQtePheromone = new JLabel("Qte pheromone");
		lblQtePheromone.setBounds(236, 151, 115, 23);
		frame.getContentPane().add(lblQtePheromone);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(239, 171, 86, 20);
		frame.getContentPane().add(textField_2);
		
		JLabel lblVitesseDissipation = new JLabel("Vitesse dissipation");
		lblVitesseDissipation.setBounds(135, 10, 89, 23);
		frame.getContentPane().add(lblVitesseDissipation);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(131, 29, 105, 23);
		frame.getContentPane().add(spinner_1);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
