
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
public class VentanaMain implements Runnable {
 
   @Override
   public void run() {
     // FRAME
     JFrame ventana = new JFrame("Ventana principal de Probando Swing");
     ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
     FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 25, 25);
     ventana.setLayout(layout);
 
     // PANEL
     JPanel panel = new JPanel(layout);
     panel.setSize(new Dimension(700, 350));
     panel.setBackground(Color.ORANGE);
     panel.setPreferredSize(new Dimension(700, 350));
     ventana.add(panel);
 
     // DEFINIMOS ESCUCHADOR DE EVENTOS PARA CUANDO HAGAMOS CLICK SOBRE LOS BOTONES
     ActionListener escuchador = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
             JButton boton = (JButton) ae.getSource();
             String nombreBoton = boton.getText();
 
             switch (nombreBoton) {
                 case "Bot�n 1":
                    panel.setBackground(Color.YELLOW);
                    break;
                 case "Bot�n 2":
                    panel.setBackground(Color.BLUE);
                    break;
                 case "Bot�n 3":
                    panel.setBackground(Color.BLACK);
                    break;
                 case "Bot�n 4":
                    panel.setBackground(Color.CYAN);
                    break;
                 case "Bot�n 5":
                    panel.setBackground(Color.GREEN);
                    break;
                 case "Bot�n 6":
                    panel.setBackground(Color.MAGENTA);
                    break;
                 case "Bot�n 7":
                    panel.setBackground(Color.PINK);
                    break;
             }
         }
     };
 
     // BOTONES
     String titulosBotones[] = {"Bot�n 1", "Bot�n 2", "Bot�n 3", "Bot�n 4", "Bot�n 5", "Bot�n 6", "Bot�n 7"};
 
     for (String tituloBoton : titulosBotones) {
         JButton boton = new JButton(tituloBoton);
         boton.setSize(new Dimension(150, 80));
         ventana.add(boton);
         boton.addActionListener(escuchador);
     }
 
     // PANEL --> Area de Texto
     String textoCualquiera = "Joachim L�w celebr� jugar la final del Mundial de Brasil frente a Argentina, "
     + "un duelo que calific� de \"magn�fica constelaci�n\". \"Europa contra Sudam�rica. Una magn�fica "
     + "constelaci�n\", dijo el seleccionador alem�n tras la victoria de Argentina ante Holanda en la "
     + "segunda semifinal.\n"
     + "\"Argentina es fuerte defensivamente, compacta, bien organizada\", coment� L�w. \"En ataque "
     + "tienen jugadores extraordinarios como Messi e Higua�n. Nos vamos a preparar bien y nos "
     + "alegramos del partido en R�o\", prosigui� el preparador alem�n.\n"
     + "Argentina y Alemania se enfrentar�n el domingo en Maracan� por tercera vez en una final de un "
     + "Mundial. Argentina, liderarda por Diego Armando Maradona, se impuso por 3-2 en la final de "
     + "M�xico'86, mientras que Alemania se tom� revancha en Italia'90 venciendo por 1-0.\n"
     + "\n"
     + "\n"
     + "Leer mￜs: Alemania-Argentina: L�w ve la final frente a Argentina como una \"magn�fica "
     + "constelaci�n\" - MARCA.com";
     JTextArea texto = new JTextArea("", 15, 40);
     texto.setText(textoCualquiera);
     texto.setFont(new Font("Monospace", Font.PLAIN, 14));
     texto.setLineWrap(true);
     texto.setWrapStyleWord(true);
     texto.setForeground(Color.WHITE);
     texto.setBackground(Color.BLACK);
 
     // SCROLL
     JScrollPane scroll = new JScrollPane(texto);
     panel.add(scroll);
 
     // ETIQUETA
     JLabel etiqueta = new JLabel("En esta etiqueta comento que la ventana tiene " + titulosBotones.length + " botones.");
     etiqueta.setForeground(Color.RED);
     etiqueta.setFont(new Font("Serif", Font.BOLD, 20));
     ventana.add(etiqueta);
 
     // END
     ventana.setSize(800, 600);
     ventana.setVisible(true);
  }
 
}