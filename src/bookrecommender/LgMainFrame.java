//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package bookrecommender;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Classe LgMainFrame, rappresenta il frame per poter far effettuare il login ad un certo utente.
 */
public class LgMainFrame {
    private final JButton accesso;
    JFrame frame;
    private JTextField username;
    private JPasswordField password;
    private final JLabel usernameLabel, passwordLabel;
    private JButton mostraPassword;
    private JTextField nascondiPassword;
    private Proxy proxy;

/**
 * Costruisce il frame principale di login per l'applicazione.
 * Questo frame consente agli utenti di accedere, registrarsi o accedere come ospiti.
 * @param proxy il proxy utilizzato per la comunicazione con il server.
 */
    @SuppressWarnings("Convert2Lambda")
    public LgMainFrame(Proxy proxy) {
        this.proxy = proxy;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {}
        Image sfondo = null;
        File fileSfondo = new File(".");
        String pathSfondo = fileSfondo.getAbsolutePath().substring(0,fileSfondo.getAbsolutePath().length()-1);
        pathSfondo = pathSfondo+"src\\immagini\\sfondoLG.jpg";
        System.out.println(pathSfondo);
        try {
            sfondo = ImageIO.read(new File(pathSfondo));
        } catch (IOException e) {}
        BackGroundPanel contentPanel = new BackGroundPanel(sfondo);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false); 

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        JLabel welcomeLabel = new JLabel("Login");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        frame = new JFrame("Login");

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        contentPanel.add(welcomeLabel, c);

        c.gridwidth = 1;

        username = new JTextField();
        c.gridx = 0;
        c.gridy = 1;
        contentPanel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        username.setColumns(15);
        contentPanel.add(username, c);

        JPanel passwordPanel = new JPanel(new CardLayout()); 
        password = new JPasswordField();
        password.setColumns(15);
        nascondiPassword = new JTextField();
        passwordPanel.setOpaque(false);
        nascondiPassword.setVisible(false);
        passwordPanel.add(password, "passwordField");
        passwordPanel.add(nascondiPassword, "hiddenPasswordField");

        c.gridx = 0;
        c.gridy = 2;
        contentPanel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        password.setColumns(15);
        contentPanel.add(passwordPanel, c);

        mostraPassword = new JButton("🕳️"); 
        mostraPassword.setPreferredSize(new Dimension(20, 20));
        mostraPassword.setFocusPainted(false);
        mostraPassword.setMargin(new Insets(0, 0, 0, 0));
        mostraPassword.setContentAreaFilled(false);
        mostraPassword.setBorderPainted(false);
        mostraPassword.setOpaque(false);

        c.gridx = 2;
        c.gridy = 2;
        contentPanel.add(mostraPassword, c);

        mostraPassword.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String textPassword = getPassword();
                if (password.isVisible()) {
                    mostraPassword.setText("👁️");
                    nascondiPassword.setText(textPassword);
                    nascondiPassword.setVisible(true);
                    password.setVisible(false);
                } else {
                    mostraPassword.setText("🕳️");
                    password.setText(nascondiPassword.getText());
                    nascondiPassword.setVisible(false);
                    password.setVisible(true);
                }

            }
        });

        accesso = new JButton("Login");
        accesso.setFont(new Font("Arial", Font.BOLD, 14));
        accesso.setBackground(new Color(135, 206, 250));
        accesso.setPreferredSize(new Dimension(100, 30));

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        contentPanel.add(accesso, c);
        accesso.setFocusable(false);
        frame.setContentPane(contentPanel);

        JPanel linkPanel = new JPanel(); 
        linkPanel.setLayout(new BoxLayout(linkPanel, BoxLayout.X_AXIS));
        linkPanel.setBackground(Color.white);

        JLabel registerLabel = new JLabel("Registrati"); 
        registerLabel.setForeground(Color.BLUE.darker());
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RgMainFrame myframe2 = new RgMainFrame(proxy);
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
                HomeMainFrame myframe = new HomeMainFrame(proxy, "Ospite");
                myframe.initialize();
                frame.dispose();
            }
        });

        linkPanel.add(registerLabel);
        linkPanel.add(Box.createHorizontalStrut(25));
        linkPanel.add(ospiteLabel);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        contentPanel.add(linkPanel, c);

        frame.setContentPane(contentPanel);
        accesso.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String username2 = username.getText();
                String password2;
                if (password.isVisible()) {
                    password2 = getPassword();
                } else {
                    password2 = nascondiPassword.getText();
                }
                if (autenticazione(username2, password2)) {
                    JOptionPane.showMessageDialog(null, "Login avvenuto con successo");
                    String cf = proxy.getCF(username2,password2);
                    HomeMainFrame hframe = new HomeMainFrame(proxy,cf);
                    hframe.initialize();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username o password errati");
                }
            }
        });
    }

/**
 * Inizializza il JFrame per l'applicazione.
 * Imposta il titolo, le dimensioni, l'icona e le dimensioni minime, l'operazione di chiusura predefinita
 * chiude la connessione al server in caso di chiusura del frame.
 */
    public void initialize() {
        frame.setTitle("Login");
        File fileIcona = new File(".");
        String pathIcona= fileIcona.getAbsolutePath().substring(0,fileIcona.getAbsolutePath().length()-1);
        pathIcona = pathIcona+"src\\immagini\\icona.png";
        Image icona = (new ImageIcon(pathIcona)).getImage();
        frame.setIconImage(icona);
        frame.setSize(350, 450);
        frame.setMinimumSize(new Dimension(350, 450));
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
 * Verifica le credenziali dell'utente per l'autenticazione.
 * Questo metodo legge l'elenco degli utenti da un file e verifica se
 * l'username e la password forniti corrispondono a un utente esistente.
 *
 * @param username l'username inserito dall'utente.
 * @param password la password inserita dall'utente.
 * @return true se le credenziali sono corrette false altrimenti
 *         
 */
    private boolean autenticazione(String username, String password) {
        ArrayList<String> listaUsername = proxy.getListaUsernameUtente();
        ArrayList<String> listaPassword = proxy.getListaPasswordUtente();
        for(int i=0;i<listaUsername.size();i++){
            if(listaUsername.get(i).equals(username) && listaPassword.get(i).equals(password)){
                return true;
            }
        }
        return false;
    }

/**
 * Ottiene la password inserita nel campo password.
 * Questo metodo legge i caratteri inseriti nel campo password,
 * li converte in una stringa e li restituisce.
 *
 * @return una stringa contenente la password inserita dall'utente.
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
