package algolabor1;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.*;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author anuta
 */
public class UserInterface {
    private static String com="";//Zeigt, welche Datenstruktur benutzt werden muss
    public static JTextField hashwert = new JTextField("Hashwert bitte einführen");
    
    public static void hauptFenster(){
        JFrame fenster =new JFrame();      
        //Creating radio buttons 
        JRadioButton mapWahl = new JRadioButton("JavaStruktur");
        mapWahl.setActionCommand("JavaStruktur");
        mapWahl.setSelected(true);
        mapWahl.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {
                com="Java";
            }
        });
        JRadioButton sqlWahl = new JRadioButton("SQL Tabelle");
        sqlWahl.setActionCommand("SQL Tabelle");
        sqlWahl.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {
                com="SQL";
            }
        });
        //Group and Panel the radio buttons.
        ButtonGroup tableWahlGroup = new ButtonGroup();
        tableWahlGroup.add(mapWahl);
        tableWahlGroup.add(sqlWahl);
        JPanel tableWahlPanel = new JPanel(new MigLayout());
        tableWahlPanel.add(mapWahl, "wrap");
        tableWahlPanel.add(sqlWahl);
        //Panel the radio buttons with text
        JLabel label1= new JLabel("Ich will ");
        JLabel label2= new JLabel(" benutzen");
        JPanel tableWahlPanel2 = new JPanel(new MigLayout());
        tableWahlPanel2.add(label1);
        tableWahlPanel2.add(tableWahlPanel);
        tableWahlPanel2.add(label2);
        
        //Tabelle einfüllen
        JLabel label3= new JLabel("Die Tabelle wird mit ");
        JLabel label4= new JLabel(" Elementen  ");
        final JComboBox zahlFuellen = new JComboBox();
        zahlFuellen.setModel(new javax.swing.DefaultComboBoxModel(new String[] 
                                                { "<html>2<sup>8</sup></html>",
                                                  "<html>2<sup>10</sup></html>",
                                                  "<html>2<sup>12</sup></html>",
                                                  "<html>2<sup>14</sup></html>",
                                                  "<html>2<sup>16</sup></html>",
                                                  "<html>2<sup>18</sup></html>" }));
        JButton fuellen =new JButton("füllen");
        fuellen.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    String zahl=(String)zahlFuellen.getSelectedItem();
               }
        });
        //Tabelle zeigen
        JButton zeigen =new JButton("zeigen");
        zeigen.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    liste();
               }
        });
              
                //JTextField hashwert = new JTextField("Hashwert bitte einführen");
        hashwert.setPreferredSize(new Dimension(295, 15));
        //dieser Knopf einführt randomen Hashwert in JTextField prob
        JButton randomButton =new JButton("Random Haschwert");
        randomButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    Random rand=new Random();
                    String s="";
                    for (int i=0;i<32;i++)  {
                        if (rand.nextBoolean()) s=s+"0";
                        else s=s+"1";
                        if (((i % 4)==3)&&(i!=31)) s=s+" ";
                    }
                    hashwert.setText(s);
               }
        });
        //Hashwert in der Tabelle suchen
        JButton probButton =new JButton("Probieren");
        probButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    Random rand=new Random();
                    if (rand.nextBoolean())
                        pechFenster();
                    else
                        glueckFenster();
               }
        });
        JButton opferButton =new JButton("Neue Versuch der Opfer");
        opferButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    opferFenster();
               }
        });
        //Fenster einfüllen
        fenster.setSize(530, 310);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLayout(new MigLayout("","[][][][]21[]","[]30[]15[]15[]"));
        fenster.add(tableWahlPanel2, "span 5, wrap");
        fenster.add(label3,"cell 0 1");
        fenster.add(zahlFuellen, "cell 1 1");
        fenster.add(label4, "cell 2 1");
        fenster.add(fuellen, "cell 3 1");
        fenster.add(zeigen, "cell 4 1");
        fenster.add(hashwert, "cell 0 2 3 1");
        fenster.add(probButton, "cell 3 2 2 1");
        fenster.add(randomButton, "cell 0 3 2 1");
        fenster.add(opferButton, "cell 0 4 4 1");
        fenster.setVisible(true);    
    }
    
    private static void pechFenster(){
        final JFrame fenster =new JFrame();      
        JLabel label1= new JLabel("<html><p align=\"center\"><font size=\"+2\">Pech!</font>"
                     + "</p>Es gibt leider nicht diesen<br>Hashwert in der Tabelle</html>");
        JButton exitButton =new JButton("neue Versuch");
        exitButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    fenster.setVisible(false);
               }
        });
        fenster.setSize(250, 180);
        fenster.setResizable(false);
        fenster.setLayout(new MigLayout("", "[center]","20[center]"));
        fenster.add(label1, "wrap");
        fenster.add(exitButton);
        fenster.setVisible(true);      
        
    }
    
    private static void glueckFenster(){
        final JFrame fenster =new JFrame();      
        JLabel label1= new JLabel("<html><p align=\"center\"><font size=\"+2\">Glück!"
                        + "</font></p><br>Es gibt diesen Hashwert<br>in der Tabelle</html>");
        JButton exitButton =new JButton("neue Versuch");
        exitButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    fenster.setVisible(false);
               }
        });
        JButton probButton =new JButton("Probieren das zu schicken");
        probButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    fenster.setVisible(false);
               }
        });
        fenster.setSize(250, 200);
        fenster.setResizable(false);
        fenster.setLayout(new MigLayout("", "[center]","[center]"));
        fenster.add(label1, "wrap");
        fenster.add(probButton, "wrap");
        fenster.add(exitButton); 
        fenster.setVisible(true);      
        
    }
    
    //Tabelle zeigen
    private static void liste(){
        final JFrame fenster =new JFrame("Die Tabelle"); 
        
        Object[] headers = { "Passwort", "Hashwert" };
        Object[][] data = {{ "Tom", "11011111110111111101111111011111" },
        { "Wendy","11011111110111111101111111011111" },
        { "Steve","11011111110111111101111111011111"},
        { "Adam", "11011111110111111101111111011111" },
        { "Larry", "11011111110111111101111111011111" },
        { "Mark", "11011111110111111101111111011111" },
        { "Terry", "11011111110111111101111111011111" }};
        
        
        //JTable table = new JTable(data, headers);
        JTable table = new JTable(new javax.swing.table.DefaultTableModel(data.length,2)); 
        for (int i=0; i<data.length;i++)
            for (int j=0; j<data[i].length;j++)
                table.setValueAt(data[i][j], i,j);
        table.getColumnModel().getColumn(0).setHeaderValue("Passwort");
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setHeaderValue("Hashwert");
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        
        JScrollPane scrTable = new JScrollPane(table);
        fenster.setSize(scrTable.getPreferredSize());
        fenster.setLayout(new MigLayout("wrap 1"));
        fenster.add(scrTable);
        fenster.setVisible(true);      
        
    }
    
    public static void opferFenster(){
        final JFrame fenster =new JFrame();      
        JLabel label1= new JLabel("Benutzername:");
        final JTextField login = new JTextField("");
        login.setPreferredSize(new Dimension(295, 15));        
        JLabel label2= new JLabel("Passwort:");
        final JPasswordField passwort = new JPasswordField("");
        passwort.setPreferredSize(new Dimension(295, 15));
        JButton sendButton =new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    char[] ch= passwort.getPassword();;
                    String pass="";
                    //String hash=String.valueOf(passwort.getPassword().hashCode());
                    for (int i=0;i<ch.length;i++)
                       pass=pass+ch[i];
                    hashwert.setText(pass);
                    fenster.setVisible(false);
               }
        });
        fenster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fenster.setSize(220, 130);
        fenster.setResizable(false);
        fenster.setLayout(new MigLayout());
        fenster.add(label1);
        fenster.add(login, "wrap");
        fenster.add(label2);
        fenster.add(passwort, "wrap");
        fenster.add(sendButton);
        fenster.setVisible(true); 
    }
    
    public static void main(String[] args) {
        hauptFenster();
        opferFenster();
        
    }
}
