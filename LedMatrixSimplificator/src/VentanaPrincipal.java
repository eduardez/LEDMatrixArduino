import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.Checkbox;
import java.awt.Button;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class VentanaPrincipal extends JFrame {

	public JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
					FuncionesPrograma.main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setTitle("Generador de codigos de matrices LED Arduino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		boolean[][] matrizActivos = generarMatriz();
		
		
		JTextPane panelTexto = new JTextPane();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelTexto, -270, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelTexto, -10, SpringLayout.EAST, contentPane);
		panelTexto.setEditable(false);
		contentPane.add(panelTexto);

		TextArea areaDebug = new TextArea();
		sl_contentPane.putConstraint(SpringLayout.WEST, areaDebug, 0, SpringLayout.WEST, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.EAST, areaDebug, -12, SpringLayout.EAST, panelTexto);
		areaDebug.setText("---- Debugger  ----");
		areaDebug.setEditable(false);
		contentPane.add(areaDebug);

		JButton botonGenerar = new JButton("Generar Codigo");
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelTexto, 6, SpringLayout.SOUTH, botonGenerar);
		sl_contentPane.putConstraint(SpringLayout.NORTH, botonGenerar, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, botonGenerar, -312, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, botonGenerar, -427, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, botonGenerar, -83, SpringLayout.EAST, contentPane);
		botonGenerar.setBackground(Color.LIGHT_GRAY);
		contentPane.add(botonGenerar);
		
		
		JButton botonCopy = new JButton("Copiar");
		sl_contentPane.putConstraint(SpringLayout.NORTH, areaDebug, 23, SpringLayout.SOUTH, botonCopy);
		sl_contentPane.putConstraint(SpringLayout.NORTH, botonCopy, 6, SpringLayout.SOUTH, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.WEST, botonCopy, 0, SpringLayout.WEST, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.EAST, botonCopy, -296, SpringLayout.EAST, contentPane);
		botonCopy.setBackground(Color.LIGHT_GRAY);
		contentPane.add(botonCopy);

	

		JButton botonLlenar = new JButton("Llenar Matriz Botones");
		sl_contentPane.putConstraint(SpringLayout.NORTH, botonLlenar, 6, SpringLayout.SOUTH, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, botonLlenar, 43, SpringLayout.SOUTH, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.WEST, botonLlenar, 564, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, botonLlenar, 0, SpringLayout.EAST, panelTexto);
		botonLlenar.setBackground(Color.LIGHT_GRAY);
		contentPane.add(botonLlenar);

		
		
		// ---------- Seccion Cajas ---------------------------------

		JComboBox cajaLetras = new JComboBox();
		sl_contentPane.putConstraint(SpringLayout.WEST, cajaLetras, 10, SpringLayout.WEST, contentPane);
		cajaLetras.setModel(new DefaultComboBoxModel(new String[] { "Letras Mays.", "A", "B", "C", "D", "E", "F", "G" }));
		cajaLetras.setBackground(Color.LIGHT_GRAY);
		contentPane.add(cajaLetras);

		JComboBox cajaFormas = new JComboBox();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, cajaFormas, 0, SpringLayout.SOUTH, cajaLetras);
		cajaFormas.setModel(new DefaultComboBoxModel(new String[] { "Formas", "<3", ".|.", ": )" }));
		cajaFormas.setBackground(Color.LIGHT_GRAY);
		contentPane.add(cajaFormas);

		
		

		JToggleButton[][] matrizBotones = generarBotones(sl_contentPane, cajaLetras, cajaFormas, panelTexto, botonCopy);
		for (int i = 0; i < matrizBotones.length; i++) {
			for (int j = 0; j < matrizBotones.length; j++) {
				int n=i; int m=j;
				matrizBotones[i][j].addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						boolean onoff = matrizBotones[n][m].isSelected();
						if (onoff == true)
							 onoff = false;
							else
							 onoff = true;
						matrizActivos[n][m] = onoff;
						actualizarDebugger("Casilla",n,m , areaDebug, matrizActivos[n][m]);	
					}
				});
			}
		}
		
		JButton botonLimpiar = new JButton("Limpiar matrizBotones");
		sl_contentPane.putConstraint(SpringLayout.NORTH, botonLimpiar, 6, SpringLayout.SOUTH, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.WEST, botonLimpiar, 6, SpringLayout.EAST, botonCopy);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, botonLimpiar, 43, SpringLayout.SOUTH, panelTexto);
		sl_contentPane.putConstraint(SpringLayout.EAST, botonLimpiar, -6, SpringLayout.WEST, botonLlenar);
		
		botonLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaDebug.append("\nMatriz Botones puesta a cero");
 
				for (int i = 0; i < matrizBotones.length; i++) {
					for (int j = 0; j < matrizBotones.length; j++) {
						matrizActivos[i][j] = false;
						matrizBotones[i][j].setSelected(false);
					}
				} 
			}
		});
		botonLimpiar.setBackground(Color.LIGHT_GRAY);
		contentPane.add(botonLimpiar);
		
		botonLlenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				matrizBotones[1][1].setEnabled(true);
				
				areaDebug.append("\nMatriz Botones puesta a uno");
				for (int i = 0; i < matrizBotones.length; i++) {
					for (int j = 0; j < matrizBotones.length; j++) {
						matrizActivos[i][j] = true;
						matrizBotones[i][j].setSelected(true);
					}
				}
			}
		});

		botonGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaDebug.append("\nCódigo generado");
				generarCodigo(matrizBotones,  panelTexto);
			}
		});
		
		
		botonCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				areaDebug.append("\nCódigo Copiado");
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(generarCodigo(matrizBotones,  panelTexto));
				clipboard.setContents(data, data);
			}
		});
	}
	
	
	public JToggleButton[][] generarBotones(SpringLayout sl_contentPane, JComboBox cajaLetras, JComboBox cajaFormas, JTextPane panelTexto, JButton botonCopy){
		// ----------------- Seccion Switches ------------------------

				JToggleButton toggle01 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle01, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle01, -418, SpringLayout.SOUTH, contentPane);
				toggle01.setBackground(Color.DARK_GRAY);
				toggle01.setForeground(Color.RED);
				contentPane.add(toggle01);

				JToggleButton toggle00 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle00, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle00, 42, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle00, 0, SpringLayout.WEST, toggle01);
				toggle00.setBackground(Color.DARK_GRAY);
				toggle00.setForeground(Color.RED);
				contentPane.add(toggle00);

				JToggleButton toggle02 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.EAST, cajaLetras, 46, SpringLayout.WEST, toggle02);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle02, -418, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle01, 0, SpringLayout.WEST, toggle02);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle02, 5, SpringLayout.NORTH, contentPane);
				toggle02.setBackground(Color.DARK_GRAY);
				toggle02.setForeground(Color.RED);
				contentPane.add(toggle02); 
				
				JToggleButton toggle03 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle03, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle03, 109, SpringLayout.WEST, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle02, 0, SpringLayout.WEST, toggle03);
				toggle03.setBackground(Color.DARK_GRAY);
				toggle03.setForeground(Color.RED);
				contentPane.add(toggle03);

				JToggleButton toggle04 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle04, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle04, -531, SpringLayout.EAST, contentPane);
				toggle04.setForeground(Color.RED);
				toggle04.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle04);

				JToggleButton toggle05 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.WEST, cajaFormas, 0, SpringLayout.WEST, toggle05);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle05, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle05, 177, SpringLayout.WEST, contentPane);
				toggle05.setBackground(Color.DARK_GRAY);
				toggle05.setForeground(Color.RED);
				contentPane.add(toggle05);

				JToggleButton toggle06 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle06, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle06, 210, SpringLayout.WEST, contentPane);
				toggle06.setBackground(Color.DARK_GRAY);
				toggle06.setForeground(Color.RED);
				contentPane.add(toggle06);

				JToggleButton toggle07 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.WEST, panelTexto, 30, SpringLayout.EAST, toggle07);
				sl_contentPane.putConstraint(SpringLayout.EAST, cajaFormas, 0, SpringLayout.EAST, toggle07);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle07, 5, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle07, 0, SpringLayout.EAST, toggle06);
				toggle07.setForeground(Color.RED);
				toggle07.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle07);

				JToggleButton toggle10 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle10, -380, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle10, 0, SpringLayout.EAST, toggle00);
				toggle10.setForeground(Color.RED);
				toggle10.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle10);

				JToggleButton toggle11 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle10, 0, SpringLayout.NORTH, toggle11);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle11, 0, SpringLayout.WEST, toggle01);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle11, -380, SpringLayout.SOUTH, contentPane);
				toggle11.setForeground(Color.RED);
				toggle11.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle11);

				JToggleButton toggle12 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle12, 1, SpringLayout.SOUTH, toggle02);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle11, 0, SpringLayout.NORTH, toggle12);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle12, 0, SpringLayout.EAST, toggle02);
				toggle12.setForeground(Color.RED);
				toggle12.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle12);

				JToggleButton toggle13 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle13, 43, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle03, -1, SpringLayout.NORTH, toggle13);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle13, 0, SpringLayout.EAST, toggle03);
				toggle13.setForeground(Color.RED);
				toggle13.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle13);

				JToggleButton toggle14 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle14, 43, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle04, -1, SpringLayout.NORTH, toggle14);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle14, 0, SpringLayout.WEST, toggle04);
				toggle14.setForeground(Color.RED);
				toggle14.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle14);

				JToggleButton toggle15 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle15, 43, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle05, -1, SpringLayout.NORTH, toggle15);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle15, 0, SpringLayout.EAST, toggle05);
				toggle15.setForeground(Color.RED);
				toggle15.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle15);

				JToggleButton toggle16 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle16, 43, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle06, -1, SpringLayout.NORTH, toggle16);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle16, 0, SpringLayout.EAST, toggle06);
				toggle16.setForeground(Color.RED);
				toggle16.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle16);

				JToggleButton toggle17 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle17, 43, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle07, -1, SpringLayout.NORTH, toggle17);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle17, 0, SpringLayout.EAST, toggle07);
				toggle17.setForeground(Color.RED);
				toggle17.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle17);

				JToggleButton toggle20 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle20, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle20, -342, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle20, 0, SpringLayout.EAST, toggle00);
				toggle20.setForeground(Color.RED);
				toggle20.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle20);

				JToggleButton toggle21 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle21, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle21, -342, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle21, 0, SpringLayout.EAST, toggle01);
				toggle21.setForeground(Color.RED);
				toggle21.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle21);

				JToggleButton toggle22 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle22, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle22, -342, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle12, -1, SpringLayout.NORTH, toggle22);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle22, 0, SpringLayout.EAST, toggle02);
				toggle22.setForeground(Color.RED);
				toggle22.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle22);

				JToggleButton toggle23 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle23, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle13, -1, SpringLayout.NORTH, toggle23);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle23, 0, SpringLayout.EAST, toggle03);
				toggle23.setForeground(Color.RED);
				toggle23.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle23);

				JToggleButton toggle24 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle24, 1, SpringLayout.SOUTH, toggle14);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle24, 0, SpringLayout.EAST, toggle04);
				toggle24.setForeground(Color.RED);
				toggle24.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle24);

				JToggleButton toggle25 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle25, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle25, 0, SpringLayout.EAST, toggle05);
				toggle25.setForeground(Color.RED);
				toggle25.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle25);

				JToggleButton toggle26 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle26, 1, SpringLayout.SOUTH, toggle16);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle26, 0, SpringLayout.EAST, toggle06);
				toggle26.setForeground(Color.RED);
				toggle26.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle26);

				JToggleButton toggle27 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle27, 81, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle27, 0, SpringLayout.EAST, toggle07);
				toggle27.setForeground(Color.RED);
				toggle27.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle27);

				JToggleButton toggle30 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle30, 120, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle30, 0, SpringLayout.WEST, toggle00);
				toggle30.setForeground(Color.RED);
				toggle30.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle30);

				JToggleButton toggle31 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle31, 2, SpringLayout.SOUTH, toggle21);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle31, 0, SpringLayout.WEST, toggle01);
				toggle31.setForeground(Color.RED);
				toggle31.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle31);

				JToggleButton toggle32 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle32, 0, SpringLayout.NORTH, toggle30);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle32, 0, SpringLayout.WEST, toggle02);
				toggle32.setForeground(Color.RED);
				toggle32.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle32);

				JToggleButton toggle33 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle23, -2, SpringLayout.NORTH, toggle33);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle33, 0, SpringLayout.NORTH, toggle30);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle33, 0, SpringLayout.WEST, toggle03);
				toggle33.setForeground(Color.RED);
				toggle33.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle33);

				JToggleButton toggle34 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle24, -2, SpringLayout.NORTH, toggle34);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle34, 120, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle34, -303, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle14, -40, SpringLayout.NORTH, toggle34);
				toggle34.setForeground(Color.RED);
				toggle34.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle34);

				JToggleButton toggle35 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle35, 120, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle25, -2, SpringLayout.NORTH, toggle35);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle15, -40, SpringLayout.NORTH, toggle35);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle34, 0, SpringLayout.WEST, toggle35);
				toggle35.setForeground(Color.RED);
				toggle35.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle35);

				JToggleButton toggle36 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle26, -2, SpringLayout.NORTH, toggle36);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle36, 120, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle36, -303, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle16, -40, SpringLayout.NORTH, toggle36);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle36, 210, SpringLayout.WEST, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle35, 0, SpringLayout.WEST, toggle36);
				toggle36.setForeground(Color.RED);
				toggle36.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle36);

				JToggleButton toggle37 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle37, 120, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle27, -2, SpringLayout.NORTH, toggle37);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle37, -303, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle17, -40, SpringLayout.NORTH, toggle37);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle37, 0, SpringLayout.EAST, toggle36);
				toggle37.setForeground(Color.RED);
				toggle37.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle37);

				JToggleButton toggle40 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle40, 157, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle30, 0, SpringLayout.NORTH, toggle40);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle40, 0, SpringLayout.EAST, toggle00);
				toggle40.setForeground(Color.RED);
				toggle40.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle40);

				JToggleButton toggle41 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle41, 0, SpringLayout.SOUTH, toggle31);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle41, 0, SpringLayout.WEST, toggle01);
				toggle41.setForeground(Color.RED);
				toggle41.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle41);

				JToggleButton toggle42 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle42, 157, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle32, 0, SpringLayout.NORTH, toggle42);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle42, 0, SpringLayout.EAST, toggle02);
				toggle42.setForeground(Color.RED);
				toggle42.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle42);

				JToggleButton toggle43 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle33, 0, SpringLayout.NORTH, toggle43);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle43, 0, SpringLayout.WEST, toggle03);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle43, 0, SpringLayout.SOUTH, toggle40);
				toggle43.setForeground(Color.RED);
				toggle43.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle43);

				JToggleButton toggle44 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle44, 0, SpringLayout.SOUTH, toggle34);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle44, 0, SpringLayout.WEST, toggle04);
				toggle44.setForeground(Color.RED);
				toggle44.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle44);

				JToggleButton toggle45 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle45, 157, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle35, 0, SpringLayout.NORTH, toggle45);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle45, 0, SpringLayout.SOUTH, toggle40);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle45, 0, SpringLayout.EAST, toggle05);
				toggle45.setForeground(Color.RED);
				toggle45.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle45);

				JToggleButton toggle46 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle46, -303, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle46, 0, SpringLayout.WEST, toggle06);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle46, 0, SpringLayout.SOUTH, toggle40);
				toggle46.setForeground(Color.RED);
				toggle46.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle46);

				JToggleButton toggle47 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle47, 0, SpringLayout.NORTH, toggle40);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle47, 36, SpringLayout.NORTH, toggle40);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle47, 0, SpringLayout.EAST, toggle07);
				toggle47.setForeground(Color.RED);
				toggle47.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle47);

				JToggleButton toggle50 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle40, 0, SpringLayout.NORTH, toggle50);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle50, -229, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle50, 0, SpringLayout.EAST, toggle00);
				toggle50.setForeground(Color.RED);
				toggle50.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle50);

				JToggleButton toggle51 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle41, 0, SpringLayout.NORTH, toggle51);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle31, -36, SpringLayout.NORTH, toggle51);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle51, 193, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle51, 0, SpringLayout.WEST, toggle01);
				toggle51.setForeground(Color.RED);
				toggle51.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle51);

				JToggleButton toggle52 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle42, 0, SpringLayout.NORTH, toggle52);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle52, 193, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle52, -229, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle43, -74, SpringLayout.SOUTH, toggle52);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle52, 0, SpringLayout.EAST, toggle02);
				toggle52.setForeground(Color.RED);
				toggle52.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle52);

				JToggleButton toggle53 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle53, 0, SpringLayout.SOUTH, toggle43);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle53, 0, SpringLayout.EAST, toggle03);
				toggle53.setForeground(Color.RED);
				toggle53.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle53);

				JToggleButton toggle54 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle44, 0, SpringLayout.NORTH, toggle54);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle54, 193, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle54, 0, SpringLayout.WEST, toggle04);
				toggle54.setForeground(Color.RED);
				toggle54.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle54);

				JToggleButton toggle55 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle55, 0, SpringLayout.SOUTH, toggle45);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle55, 0, SpringLayout.WEST, toggle05);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle55, -229, SpringLayout.SOUTH, contentPane);
				toggle55.setForeground(Color.RED);
				toggle55.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle55);

				JToggleButton toggle56 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle56, 0, SpringLayout.SOUTH, toggle46);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle56, 0, SpringLayout.EAST, toggle06);
				toggle56.setForeground(Color.RED);
				toggle56.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle56);

				JToggleButton toggle57 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle57, 0, SpringLayout.SOUTH, toggle47);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle57, 0, SpringLayout.WEST, toggle07);
				toggle57.setForeground(Color.RED);
				toggle57.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle57);

				JToggleButton toggle60 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle60, 2, SpringLayout.SOUTH, toggle50);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle60, 0, SpringLayout.EAST, toggle00);
				toggle60.setForeground(Color.RED);
				toggle60.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle60);

				JToggleButton toggle61 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle61, 233, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle51, -2, SpringLayout.NORTH, toggle61);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle61, 0, SpringLayout.WEST, toggle01);
				toggle61.setForeground(Color.RED);
				toggle61.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle61);

				JToggleButton toggle62 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle62, 233, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle62, 0, SpringLayout.SOUTH, toggle60);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle62, 0, SpringLayout.EAST, toggle02);
				toggle62.setForeground(Color.RED);
				toggle62.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle62);

				JToggleButton toggle63 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle63, -191, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle50, -76, SpringLayout.SOUTH, toggle63);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle63, 233, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle53, -2, SpringLayout.NORTH, toggle63);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle63, 0, SpringLayout.WEST, toggle03);
				toggle63.setForeground(Color.RED);
				toggle63.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle63);

				JToggleButton toggle64 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle64, -191, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle54, -2, SpringLayout.NORTH, toggle64);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle64, 0, SpringLayout.NORTH, toggle60);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle64, 0, SpringLayout.EAST, toggle04);
				toggle64.setForeground(Color.RED);
				toggle64.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle64);

				JToggleButton toggle65 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle65, -191, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle65, 0, SpringLayout.NORTH, toggle60);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle65, 0, SpringLayout.EAST, toggle05);
				toggle65.setForeground(Color.RED);
				toggle65.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle65);

				JToggleButton toggle66 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle56, -2, SpringLayout.NORTH, toggle66);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle66, -227, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle66, 0, SpringLayout.SOUTH, toggle60);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle66, 0, SpringLayout.EAST, toggle06);
				toggle66.setForeground(Color.RED);
				toggle66.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle66);

				JToggleButton toggle67 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle57, -2, SpringLayout.NORTH, toggle67);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle67, 0, SpringLayout.NORTH, toggle60);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle67, 0, SpringLayout.WEST, toggle07);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle67, 0, SpringLayout.SOUTH, toggle60);
				toggle67.setForeground(Color.RED);
				toggle67.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle67);

				JToggleButton toggle70 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.SOUTH, botonCopy, -74, SpringLayout.SOUTH, toggle70);
				sl_contentPane.putConstraint(SpringLayout.NORTH, cajaLetras, 35, SpringLayout.SOUTH, toggle70);
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle70, 270, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle70, -153, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle60, 0, SpringLayout.NORTH, toggle70);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle70, 0, SpringLayout.EAST, toggle00);
				toggle70.setForeground(Color.RED);
				toggle70.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle70);

				JToggleButton toggle71 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle71, 270, SpringLayout.NORTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle61, -1, SpringLayout.NORTH, toggle71);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle71, 0, SpringLayout.WEST, toggle01);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle71, 0, SpringLayout.SOUTH, toggle70);
				toggle71.setForeground(Color.RED);
				toggle71.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle71);

				JToggleButton toggle72 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle72, 0, SpringLayout.SOUTH, toggle62);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle72, 0, SpringLayout.WEST, toggle02);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle72, -153, SpringLayout.SOUTH, contentPane);
				toggle72.setForeground(Color.RED);
				toggle72.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle72);

				JToggleButton toggle73 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle73, 1, SpringLayout.SOUTH, toggle63);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle73, -153, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle73, 0, SpringLayout.EAST, toggle03);
				toggle73.setForeground(Color.RED);
				toggle73.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle73);

				JToggleButton toggle74 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle74, 0, SpringLayout.NORTH, toggle70);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle74, 0, SpringLayout.SOUTH, toggle72);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle74, 0, SpringLayout.EAST, toggle04);
				toggle74.setForeground(Color.RED);
				toggle74.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle74);

				JToggleButton toggle75 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle75, 1, SpringLayout.SOUTH, toggle65);
				sl_contentPane.putConstraint(SpringLayout.WEST, toggle75, 0, SpringLayout.WEST, toggle05);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle75, 0, SpringLayout.SOUTH, toggle70);
				toggle75.setForeground(Color.RED);
				toggle75.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle75);

				JToggleButton toggle76 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle76, 0, SpringLayout.SOUTH, toggle66);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle76, -153, SpringLayout.SOUTH, contentPane);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle76, 0, SpringLayout.EAST, toggle06);
				toggle76.setForeground(Color.RED);
				toggle76.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle76);

				JToggleButton toggle77 = new JToggleButton("");
				sl_contentPane.putConstraint(SpringLayout.NORTH, toggle77, 0, SpringLayout.NORTH, toggle70);
				sl_contentPane.putConstraint(SpringLayout.SOUTH, toggle77, 0, SpringLayout.SOUTH, toggle73);
				sl_contentPane.putConstraint(SpringLayout.EAST, toggle77, 0, SpringLayout.EAST, toggle07);
				toggle77.setForeground(Color.RED);
				toggle77.setBackground(Color.DARK_GRAY);
				contentPane.add(toggle77);

				JToggleButton[][] matrizBotones = new JToggleButton[8][8];
				matrizBotones[0][0] = toggle00;
				matrizBotones[0][1] = toggle01;
				matrizBotones[0][2] = toggle02;
				matrizBotones[0][3] = toggle03;
				matrizBotones[0][4] = toggle04;
				matrizBotones[0][5] = toggle05;
				matrizBotones[0][6] = toggle06;
				matrizBotones[0][7] = toggle07;
				matrizBotones[1][0] = toggle10;
				matrizBotones[1][1] = toggle11;
				matrizBotones[1][2] = toggle12;
				matrizBotones[1][3] = toggle13;
				matrizBotones[1][4] = toggle14;
				matrizBotones[1][5] = toggle15;
				matrizBotones[1][6] = toggle16;
				matrizBotones[1][7] = toggle17;
				matrizBotones[2][0] = toggle20;
				matrizBotones[2][1] = toggle21;
				matrizBotones[2][2] = toggle22;
				matrizBotones[2][3] = toggle23;
				matrizBotones[2][4] = toggle24;
				matrizBotones[2][5] = toggle25;
				matrizBotones[2][6] = toggle26;
				matrizBotones[2][7] = toggle27;
				matrizBotones[3][0] = toggle30;
				matrizBotones[3][1] = toggle31;
				matrizBotones[3][2] = toggle32;
				matrizBotones[3][3] = toggle33;
				matrizBotones[3][4] = toggle34;
				matrizBotones[3][5] = toggle35;
				matrizBotones[3][6] = toggle36;
				matrizBotones[3][7] = toggle37;
				matrizBotones[4][0] = toggle40;
				matrizBotones[4][1] = toggle41;
				matrizBotones[4][2] = toggle42;
				matrizBotones[4][3] = toggle43;
				matrizBotones[4][4] = toggle44;
				matrizBotones[4][5] = toggle45;
				matrizBotones[4][6] = toggle46;
				matrizBotones[4][7] = toggle47;
				matrizBotones[5][0] = toggle50;
				matrizBotones[5][1] = toggle51;
				matrizBotones[5][2] = toggle52;
				matrizBotones[5][3] = toggle53;
				matrizBotones[5][4] = toggle54;
				matrizBotones[5][5] = toggle55;
				matrizBotones[5][6] = toggle56;
				matrizBotones[5][7] = toggle57;
				matrizBotones[6][0] = toggle60;
				matrizBotones[6][1] = toggle61;
				matrizBotones[6][2] = toggle62;
				matrizBotones[6][3] = toggle63;
				matrizBotones[6][4] = toggle64;
				matrizBotones[6][5] = toggle65;
				matrizBotones[6][6] = toggle66;
				matrizBotones[6][7] = toggle67;
				matrizBotones[7][0] = toggle70;
				matrizBotones[7][1] = toggle71;
				matrizBotones[7][2] = toggle72;
				matrizBotones[7][3] = toggle73;
				matrizBotones[7][4] = toggle74;
				matrizBotones[7][5] = toggle75;
				matrizBotones[7][6] = toggle76;
				matrizBotones[7][7] = toggle77;
				return matrizBotones;
	}
	
	
	
	
	public void actualizarDebugger(String string, int n, int m, TextArea areaDebug, boolean onoff) {
		if (onoff) {
			areaDebug.append("\n" + string + n+ m +" off");
		} else {
			areaDebug.append("\n" + string + n+ m +" on");
		}

	}
	
	public String generarCodigo(JToggleButton[][] matrizBotones, JTextPane panelTexto){
		String linea="B";
		
		for (int i = 0; i < matrizBotones.length; i++) {
			for (int j = 0; j < matrizBotones.length; j++) {				
				if (matrizBotones[i][j].isSelected()){
					linea=linea+"1";
				}else
					linea=linea+"0";
				
			}if(i<matrizBotones.length-1)linea=linea+", B";
		}
		panelTexto.setText(linea);
		return linea;
	}
	
	public boolean[][] generarMatriz(){
		boolean[][] matrizActivos = new boolean[8][8];
		for (int i = 0; i < matrizActivos.length; i++) {
			for (int j = 0; j < matrizActivos.length; j++) {
				matrizActivos[i][j] = false;
			}
		}
		return matrizActivos;
	}
}
