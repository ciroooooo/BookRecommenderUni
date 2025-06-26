//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.swing.*;

public class RgMainFrame {
    private JTextField nome;
    private JTextField cognome;
    private JButton bottoneRG;
    JFrame frame;
    private JTextField email;
    private JTextField username;
    private JPasswordField password;
    private JTextField cf;
    private JButton bottoneMostraPassword;
    private JLabel nomeLabel, cognomeLabel, cfLabel, emailLabel, usernameLabel, passwordLabel;
    private JLabel erroreLabel;
    private JTextField nascondiPasswordField;

/**
 * Costruttore per la finestra di registrazione utente.
 * 
 * @param listaLibri La lista dei libri disponibili nel sistema.
 */
    public RgMainFrame(Proxy proxy) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.white);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        JLabel benvenutoLabel = new JLabel("Benvenuti");
        benvenutoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        benvenutoLabel.setHorizontalAlignment(JLabel.CENTER); 

        nomeLabel = new JLabel("Nome:"); 
        cognomeLabel = new JLabel("Cognome:");
        cfLabel=new JLabel("Codice Fiscale");
        emailLabel = new JLabel("Email:");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        frame = new JFrame("CARLO");

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        contentPanel.add(benvenutoLabel, c);
        
        c.gridwidth = 1;

        nome = new JTextField();   //Inserisce i TextField per poter inserire i vari campi, 
        c.gridx = 0;
        c.gridy = 1;
        contentPanel.add(nomeLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        nome.setColumns(15);
        contentPanel.add(nome, c);

        cognome = new JTextField();
        c.gridx = 0;
        c.gridy = 2;
        contentPanel.add(cognomeLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        cognome.setColumns(15);
        contentPanel.add(cognome, c);

        cf=new JTextField();
        c.gridx=0;
        c.gridy=3;
        contentPanel.add(cfLabel,c);

        c.gridx=1;
        c.gridy=3;
        contentPanel.add(cf,c);


        email = new JTextField();
        c.gridx = 0;
        c.gridy = 4;
        contentPanel.add(emailLabel, c);

        c.gridx = 1;
        c.gridy = 4;
        email.setColumns(15);
        contentPanel.add(email, c);

        username = new JTextField();
        c.gridx = 0;
        c.gridy = 5;
        contentPanel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 5;
        username.setColumns(15);
        contentPanel.add(username, c);

        JPanel passwordPanel = new JPanel(new CardLayout());
        password = new JPasswordField();
        password.setColumns(15);
        nascondiPasswordField = new JTextField();
        nascondiPasswordField.setColumns(15);
        nascondiPasswordField.setVisible(false);
        passwordPanel.add(password, "passwordField");
        passwordPanel.add(nascondiPasswordField, "hiddenPasswordField");

        c.gridx = 0;
        c.gridy = 6;
        contentPanel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 6;
        contentPanel.add(passwordPanel, c);

        bottoneMostraPassword = new JButton("🕳️"); //occhio password 
        bottoneMostraPassword.setPreferredSize(new Dimension(20, 20));
        bottoneMostraPassword.setFocusPainted(false);
        bottoneMostraPassword.setMargin(new Insets(0, 0, 0, 0));
        bottoneMostraPassword.setContentAreaFilled(false);
        bottoneMostraPassword.setBorderPainted(false);
        bottoneMostraPassword.setOpaque(false);

        c.gridx = 2;
        c.gridy = 6;
        contentPanel.add(bottoneMostraPassword, c);

        bottoneMostraPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String textPassword=getPassword();
                if(password.isVisible()){
                    bottoneMostraPassword.setText("👁️");
                    nascondiPasswordField.setText(textPassword);
                    nascondiPasswordField.setVisible(true);
                    password.setVisible(false);
                }else{
                    bottoneMostraPassword.setText("🕳️");
                    password.setText(nascondiPasswordField.getText());
                    nascondiPasswordField.setVisible(false);
                    password.setVisible(true);
                }
                
            }
        });

        bottoneRG = new JButton("registrami");
        bottoneRG.setFont(new Font("Arial", Font.BOLD, 14)); 
        bottoneRG.setBackground(new Color(135, 206, 250)); 
        bottoneRG.setForeground(Color.BLACK);
        bottoneRG.setPreferredSize(new Dimension(100, 30));

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2; 
        contentPanel.add(bottoneRG, c);

        erroreLabel = new JLabel(" ");
        erroreLabel.setForeground(Color.RED);
        erroreLabel.setPreferredSize(new Dimension(1,20)); 
        erroreLabel.setVisible(true); 

        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        contentPanel.add(erroreLabel, c);

        bottoneRG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String textPassword;
                File file = new File("file\\utenti.txt");
                if(password.isVisible()){
                    textPassword=getPassword();
                }else{
                    textPassword=nascondiPasswordField.getText();
                }
                Utente u = new Utente(nome.getText(), cognome.getText(),cf.getText(), email.getText(), username.getText(),textPassword);
                if (u.getCode() == 2) {
                    proxy.aggiungiUtente(u);
                    JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo");
                    LgMainFrame myframe2 = new LgMainFrame(listaLibri,new Proxy());
                    myframe2.initialize();
                    frame.dispose();
                } else if (u.getCode() == 0) {
                    erroreLabel.setText("Email già esistente.");
                } else if (u.getCode() == 1) {
                    erroreLabel.setText("Username già in uso.");
                } else if (u.getCode() == 4) {
                    erroreLabel.setText("Attenzione! compilare tutti i campi");
                } else if (u.getCode() == 5) {
                    erroreLabel.setText("formato Email non valido.");
                } else if(u.getCode()==10){
                    erroreLabel.setText("CodiceFiscale errato");
                }
            }
        });
        bottoneRG.setFocusable(false);
        JPanel linkPanel = new JPanel();
        linkPanel.setLayout(new BoxLayout(linkPanel, BoxLayout.X_AXIS));
        linkPanel.setBackground(Color.white);
        JLabel accessoLabel = new JLabel("Accedi");
        accessoLabel.setForeground(Color.BLUE.darker());
        accessoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        accessoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LgMainFrame myframe2 = new LgMainFrame(listaLibri,new Proxy());
                myframe2.initialize();
                frame.dispose();
            }
        });

        JLabel ospiteLabel = new JLabel("Accedi come ospite");
        ospiteLabel.setForeground(Color.BLUE.darker());
        ospiteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ospiteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HomeMainFrame myframe = new HomeMainFrame(0, listaLibri ,"ospite");
                myframe.initialize();
                frame.dispose();
            }
        });
        linkPanel.add(accessoLabel);
        linkPanel.add(Box.createHorizontalStrut(25));
        linkPanel.add(ospiteLabel);
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        contentPanel.add(linkPanel, c);
        frame.setContentPane(contentPanel);
    }
/**
 * Inizializza la finestra di registrazione.
 * Imposta il titolo della finestra, le dimensioni minime, l'operazione di chiusura predefinita,
 * la posizione relativa al centro dello schermo e rende la finestra visibile.
 */
public void initialize() {
    frame.setTitle("Registrazione");
    frame.setSize(350, 450);
    frame.setMinimumSize(new Dimension(1, 1)); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true); 
}

/**
 * Legge gli utenti da un file e restituisce un'ArrayList di Utente.
 * Se il file non contiene dati o non esiste, restituisce un ArrayList vuoto.
 * 
 * @return ArrayList di Utente letto dal file.
 */
public ArrayList<Utente> leggiFile() {
    ArrayList<Utente> alU = new ArrayList<>();
    File file = new File("file\\utenti.txt");

    // Verifica se il file ha dati da leggere
    if (file.length() != 0) {
        try {
            // Apre lo stream di input per leggere il file
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Legge l'oggetto ArrayList di Utente dal file
            alU = (ArrayList<Utente>) ois.readObject();

            // Chiude gli stream di input
            ois.close();
            fis.close();
        } catch (Exception e) {
            // Gestisce le eccezioni ma non fa nulla con l'eccezione stessa
        }
    }
    return alU;
}

/**
 * Ottiene la password inserita nel campo di testo protetto e la restituisce come una stringa.
 * 
 * @return La password inserita nel campo di testo protetto come una stringa.
 */
public String getPassword() {
    String testoPassword = "";
    char[] charPass = password.getPassword();
    // Concatena i caratteri della password in una stringa
    for (int i = 0; i < password.getPassword().length; i++) {
        testoPassword = testoPassword + charPass[i];
    }
    return testoPassword;
}

}
