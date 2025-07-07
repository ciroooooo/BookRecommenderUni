//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package frames;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import parametri.*;

public class RgMainFrame {
    private JTextField nome;
    private JTextField cognome;
    private final JButton bottoneRG;
    private JFrame frame;
    private JTextField email;
    private JTextField username;
    private JPasswordField password;
    private JTextField cf;
    private JButton bottoneMostraPassword;
    private final JLabel nomeLabel, cognomeLabel, cfLabel, emailLabel, usernameLabel, passwordLabel;
    private JLabel erroreLabel;
    private JTextField nascondiPasswordField;
    private Proxy proxy;
/**
 * Costruttore per la finestra di registrazione utente.
 * 
 * @param proxy Per la comunicazione con il server.
 */
    @SuppressWarnings("Convert2Lambda")
    public RgMainFrame(Proxy proxy) {
        this.proxy = proxy;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {}
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
            @Override
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
            @Override
            public void actionPerformed(ActionEvent e) {
                String textPassword;
                if(password.isVisible()){
                    textPassword=getPassword();
                }else{
                    textPassword=nascondiPasswordField.getText();
                }
                Utente u = new Utente(nome.getText(), cognome.getText(),cf.getText(), email.getText(), username.getText(),textPassword);
                int codice = proxy.aggiungiUtente(u);
                if (codice == 2) {
                    JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo");
                    LgMainFrame myframe2 = new LgMainFrame(proxy);
                    myframe2.initialize();
                    frame.dispose();
                } else if (codice == 0) {
                    erroreLabel.setText("Email già esistente.");
                } else if (codice == 1) {
                    erroreLabel.setText("Username già in uso.");
                } else if (codice == 4) {
                    erroreLabel.setText("Attenzione! compilare tutti i campi");
                } else if (codice == 5) {
                    erroreLabel.setText("formato Email non valido.");
                } else if(codice == 10){
                    erroreLabel.setText("CodiceFiscale errato");
                } else if(codice == 9){
                    erroreLabel.setText("Errore di Connessione");
                } else if(codice == 7){
                    erroreLabel.setText("Codice Fiscale esistente");
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
                LgMainFrame myframe2 = new LgMainFrame(proxy);
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
                HomeMainFrame myframe = new HomeMainFrame(proxy,"Ospite");
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
        File fileIcona = new File(".");
        String pathIcona= fileIcona.getAbsolutePath().substring(0,fileIcona.getAbsolutePath().length()-1);
        pathIcona = pathIcona+"src\\immagini\\icona.png";
        Image icona = (new ImageIcon(pathIcona)).getImage();
        frame.setIconImage(icona);
        frame.setSize(350, 450);
        frame.setMinimumSize(new Dimension(1, 1)); 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                proxy.fineComunicazione();
                frame.dispose();
                
            }
        });
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true); 
}

/**
 * 
 * Restituisce la password del TextField password nel caso in cui sia invisibile
 * @return La password inserita all'interno del TextField
 */
    private String getPassword() {
        String testoPassword = "";
        char[] charPass = password.getPassword();
        for (int i = 0; i < password.getPassword().length; i++) {
            testoPassword = testoPassword + charPass[i];
        }
        return testoPassword;
    }
}
