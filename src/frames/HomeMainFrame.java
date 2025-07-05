//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package frames;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import parametri.*;

public class HomeMainFrame {
    private boolean esisteValutazione;
    private JFrame frame;
    private final JPanel topPanel;
    private final JPanel searchPanel;
    private final JPanel resultsPanel;
    private JPanel panelRisultatoNESugg;
    private final JScrollPane risultatoScrollPane;
    private JButton bottoneHome;
    private JButton bottoneLibreria;
    private JButton bottoneProfilo;
    private Utente u;
    private double mediaStile=0;
    private double mediaContenuto=0;
    private double mediaGradevolezza=0;
    private double mediaOriginalita=0;
    private double mediaEdizione=0;
    private double mediaVotoFinale=0;
    private final Proxy proxy;

/**
 * Costruttore della classe HomeMainFrame.
 * 
 * @param proxy utilizzato per la connessione al Server
 * @param cf indica il codice fiscale della persona che ha effettuato l'accesso, altrimenti indica "Ospite" se si è entrati come ospite.
 */
    @SuppressWarnings("Convert2Lambda")
    public HomeMainFrame(Proxy proxy,String cf) {
    this.proxy = proxy;
    frame = new JFrame();

    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    topPanel.setBackground(new Color(41, 128, 185)); // Cambiato: ora uguale ai bottoni
    topPanel.setOpaque(true);

    bottoneHome = new JButton("Logout"); // Cambiato da "Home" a "Logout"
    bottoneLibreria = new JButton("Libreria");
    bottoneProfilo=new JButton("Profilo");
    customizeButton(bottoneHome);
    customizeButton(bottoneLibreria);
    customizeButton(bottoneProfilo);

    bottoneHome.setBackground(new Color(41, 128, 185)); // Darker blue
    bottoneLibreria.setBackground(new Color(41, 128, 185));
    bottoneProfilo.setBackground(new Color(41, 128, 185));
    bottoneHome.setForeground(Color.WHITE);
    bottoneLibreria.setForeground(Color.WHITE);
    bottoneProfilo.setForeground(Color.WHITE);
     // Azione per il tasto Logout
    bottoneHome.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LgMainFrame loginFrame = new LgMainFrame(proxy);
            loginFrame.initialize();
            frame.dispose();
        }
    });

    bottoneHome.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            bottoneHome.setBackground(new Color(93, 173, 226)); // Lighter blue
        }
        @Override
        public void mouseExited(MouseEvent e) {
            bottoneHome.setBackground(new Color(41, 128, 185));
        }
    });
    bottoneLibreria.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            bottoneLibreria.setBackground(new Color(93, 173, 226));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            bottoneLibreria.setBackground(new Color(41, 128, 185));
        }
    });
    bottoneProfilo.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            bottoneProfilo.setBackground(new Color(93, 173, 226));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            bottoneProfilo.setBackground(new Color(41, 128, 185));
        }
    });

    topPanel.add(bottoneHome);
    topPanel.add(bottoneLibreria);
    topPanel.add(bottoneProfilo);

    // Aggiunta dell'azione al bottone Libreria
    bottoneLibreria.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cf.equals("Ospite")) {
                JOptionPane.showMessageDialog(null, "Impossibile effettuare questa operazione come ospite");
            } else{
                LibrerieMainFrame myframe = new LibrerieMainFrame(proxy,cf);
                myframe.initialize();
                frame.dispose();
            }
        }
    });
    bottoneProfilo.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            if(cf.equals("Ospite")){
                JOptionPane.showMessageDialog(null, "Impossibile effettuare questa operazione come ospite");
            }else{
                boolean primaVolta=true;
                GridBagConstraints menuGrid=new GridBagConstraints();
                menuGrid.gridx=0;
                menuGrid.anchor=GridBagConstraints.WEST;
                menuGrid.insets=new Insets(5, 10, 5,10);
                JPanel profiloPanel=new JPanel(new GridBagLayout());
                JPanel buttonPanel = new JPanel();
               
                buttonPanel.setBackground(new Color(240, 240, 240));
                String[] menuLaterale = {"Informazioni","Cambio Password","Cambio Username","Cambio Email"};
                for(int i=0;i<menuLaterale.length;i++){
                    JButton bottoneMenuLaterale=new JButton(menuLaterale[i]);
                    bottoneMenuLaterale.setForeground(Color.WHITE);
                    bottoneMenuLaterale.setBackground(new Color(41, 128, 185)); // Darker blue
                    bottoneMenuLaterale.setBorderPainted(false);
                    bottoneMenuLaterale.setFocusPainted(false);
                    bottoneMenuLaterale.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            bottoneMenuLaterale.setBackground(new Color(93, 173, 226)); // Lighter blue
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            bottoneMenuLaterale.setBackground(new Color(41, 128, 185));
                        }
                    });
                    menuGrid.gridy=i;
                    buttonPanel.add(bottoneMenuLaterale,menuGrid);

                }
                profiloPanel.add(buttonPanel); // Aggiunge i pulsanti al pannello laterale
                if(primaVolta){
                    menuGrid.gridx=3;
                    menuGrid.gridy=0;
                    JLabel usernameLabel=new JLabel("Username:");
                    profiloPanel.add(usernameLabel,menuGrid);
                    menuGrid.gridx=4;
                    menuGrid.gridy=0;
                    JLabel usernameLabel2=new JLabel(u.getUsername());
                    profiloPanel.add(usernameLabel2,menuGrid);
                }
                JOptionPane profiloOptionPanel = new JOptionPane(
                profiloPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null);


                JDialog dialog = profiloOptionPanel.createDialog(frame,"Dettagli profilo");
                dialog.setSize(400, 400);
                dialog.getContentPane().setBackground(new Color(236, 240, 241)); // Light gray
            
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }); 
   
    
    // Pannello principale per contenere tutto
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // Pannello per il testo esplicativo e il box di ricerca
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
     centerPanel.setBackground(new Color(236, 240, 241)); // Cambiato: grigio chiaro come la parte sottostante

    // Titolo esplicativo sopra il box di ricerca
    JLabel titoloLabel = new JLabel("<html><div style='text-align: center;'><b>Inserisci un Titolo di un libro, un Autore, oppure Autore ed anno<br>e clicca il pulsante corrispondente per effettuare la ricerca</b></div></html>");
    titoloLabel.setFont(new Font("Arial", Font.PLAIN, 13)); // Font leggermente più piccolo
    titoloLabel.setForeground(new Color(70, 70, 70)); // Colore neutro
    titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Padding laterale e abbassamento
    titoloLabel.setOpaque(true);
    titoloLabel.setBackground(new Color(236, 240, 241));

    centerPanel.add(titoloLabel, BorderLayout.NORTH);

    // Pannello di ricerca
    searchPanel = new JPanel(new GridBagLayout());
    searchPanel.setBackground(new Color(236, 240, 241)); // Light gray
    GridBagConstraints gbc = new GridBagConstraints();

    JTextField searchField = new JTextField(20);
    searchField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199))); // Subtle border
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(10, 10, 10, 10);
    searchPanel.add(searchField, gbc);

    JButton searchByTitleButton = new JButton("Titolo");
    JButton searchByAuthorButton = new JButton("Autore");
    JButton searchByAuthorAndYearButton = new JButton("Autore e Anno");

    gbc.gridwidth = 1;
    gbc.gridy = 1;
    gbc.gridx = 0;
    searchPanel.add(searchByTitleButton, gbc);
    gbc.gridx = 1;
    searchPanel.add(searchByAuthorButton, gbc);
    gbc.gridx = 2;
    searchPanel.add(searchByAuthorAndYearButton, gbc);

    // Azioni per i bottoni di ricerca
    searchByTitleButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            if (searchText.length() > 0) {
                ArrayList<Libro> risultati = proxy.ricercaPerTitolo(searchText);
                if (risultati.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Non esiste nessun libro con questo titolo");
                } else {
                    mostraRisultati(risultati);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Inserire almeno un carattere");
            }
        }
    });

    searchByAuthorButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            if (searchText.length() > 0) {
                ArrayList<Libro> risultati = proxy.ricercaPerAutore(new Autore(searchText));
                if (risultati.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Non esiste nessun libro di questo autore");
                } else {
                    mostraRisultati(risultati);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Inserire almeno un carattere");
            }
        }
    });

    searchByAuthorAndYearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            String[] parts = searchText.split("z,");
            if (searchText.length() > 0) {
                if (parts.length == 2) {
                    try {
                        String autore = parts[0].trim();
                        int anno = Integer.parseInt(parts[1].trim());
                        ArrayList<Libro> risultati = proxy.ricercaPerAutoreEAnno(new Autore(autore), anno);
                        if (risultati.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Non esiste nessun libro con questo autore e anno");
                        } else {
                            mostraRisultati(risultati);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Formato anno non valido. Usare 'Autore, Anno'.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Formato input non valido. Usare 'Autore, Anno'.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Inserire almeno un carattere");
            }
        }
    });

    centerPanel.add(searchPanel, BorderLayout.CENTER);

    // Pannello dei risultati
    resultsPanel = new JPanel();
    resultsPanel.setBackground(new Color(236, 240, 241)); // Light gray
    resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
    risultatoScrollPane = new JScrollPane(resultsPanel);
    risultatoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    risultatoScrollPane.setPreferredSize(new Dimension(300, 200));
    risultatoScrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199))); // Subtle border

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(risultatoScrollPane, BorderLayout.SOUTH);

    frame.setLayout(new BorderLayout());
    frame.add(mainPanel, BorderLayout.CENTER);
}



/**
 * Personalizza l'aspetto di un JButton specificato.
 * Imposta il font, il colore di sfondo, il colore del testo, le dimensioni preferite,
 * rimuove il bordo visibile, il focus e l'area di contenuto riempita, e rende il bottone opaco.
 * Aggiunge un listener per gestire il cambio di colore quando il mouse preme e rilascia il bottone.
 *
 * @param bottone Il JButton da personalizzare.
 */
private void customizeButton(JButton bottone) {
    bottone.setFont(new Font("Arial", Font.BOLD, 14));
    bottone.setBackground(new Color(135, 206, 250));
    bottone.setForeground(Color.BLACK);
    bottone.setPreferredSize(new Dimension(100, 50));
    bottone.setBorderPainted(false);
    bottone.setFocusPainted(false);
    bottone.setContentAreaFilled(false);
    bottone.setOpaque(true);
    bottone.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            bottone.setBackground(new Color(135, 255, 255));
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            bottone.setBackground(new Color(135, 206, 250));
        }
    });
}

    
/**
 * Inizializza il JFrame per l'applicazione.
 * Imposta il titolo, le dimensioni, le dimensioni minime, l'operazione di chiusura predefinita,
 * e centra il frame nello schermo.
 */
public void initialize() {
    frame.setTitle("Home");
    frame.setSize(350, 500);
    frame.setMinimumSize(new Dimension(300, 400)); 
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e){
            proxy.fineComunicazione();
            frame.dispose();
            
        }
    });
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

/**
 * Mostra i risultati della ricerca dei libri in un pannello.
 * Rimuove tutti i componenti dal pannello dei risultati e aggiunge un pannello separato per ogni libro trovato.
 * Ogni pannello libro contiene il titolo, l'autore e l'anno del libro come bottone cliccabile.
 * Quando viene cliccato il bottone, viene visualizzato il dettaglio del libro corrispondente.
 *
 * @param risultati ArrayList di Libro contenente i libri da mostrare nei risultati della ricerca.
 */
private void mostraRisultati(ArrayList<Libro> risultati) {
    resultsPanel.removeAll();
    for (Libro libro : risultati) {
        JPanel libroPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Bordo arrotondato
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 250, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        libroPanel.setOpaque(false);
        libroPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel libroLabel = new JLabel(
            "<html><b>" + Libro.getTitolo(libro) + "</b><br>"
            + "<span style='color:#555;'>" + Libro.getAutore(libro) + " &middot; " + Libro.getAnno(libro) + "</span></html>"
        );
        libroLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        libroLabel.setForeground(new Color(33, 97, 140));
        libroLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        libroLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        libroLabel.addMouseListener(new MouseAdapter() {
            Color orig = libroLabel.getForeground();
            @Override
            public void mouseEntered(MouseEvent e) {
                libroLabel.setForeground(new Color(21, 67, 96));
                libroPanel.setBorder(BorderFactory.createLineBorder(new Color(93, 173, 226), 2, true));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                libroLabel.setForeground(orig);
                libroPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                mostraDettagliLibro(libro, 1);
            }
        });

        libroPanel.add(libroLabel, BorderLayout.CENTER);
        resultsPanel.add(libroPanel);
        resultsPanel.add(Box.createVerticalStrut(8));
    }

    resultsPanel.revalidate();
    resultsPanel.repaint();
}
/**
 * Mostra l'elenco degli utenti che hanno recensito il libro specificato in un pannello.
 * Rimuove tutti i componenti dal pannello dei risultati e aggiunge un pannello separato per ogni utente trovato.
 * Ogni pannello utente contiene il nome utente come bottone cliccabile.
 * Quando viene cliccato un bottone utente, vengono mostrati i dettagli della valutazione data dall'utente al libro.
 *
 * @param risultato ArrayList di Utente contenente gli utenti che hanno recensito il libro.
 * @param l Il Libro per il quale si vogliono mostrare le recensioni degli utenti.
 */
private void mostraUtentiRecensioni(ArrayList<Utente> risultato, Libro l) {
    panelRisultatoNESugg.removeAll();

    JPanel utentiGridPanel = new JPanel(new GridBagLayout());
    utentiGridPanel.setBackground(new Color(245, 250, 255));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    int col = 0, row = 0;
    for (Utente utente : risultato) {
        JPanel utentePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 250, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            }
        };
        utentePanel.setOpaque(false);
        utentePanel.setBackground(new Color(245, 250, 255));
        utentePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        utentePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        utentePanel.setPreferredSize(new Dimension(180, 38));
        utentePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel utenteLabel = new JLabel("<html><b>" + utente.getUsername() + "</b></html>");
        utenteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        utenteLabel.setForeground(new Color(41, 128, 185));
        utenteLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        utenteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        utentePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                utentePanel.setBackground(new Color(220, 240, 255));
                utenteLabel.setForeground(new Color(21, 67, 96));
                utentePanel.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                utentePanel.setBackground(new Color(245, 250, 255));
                utenteLabel.setForeground(new Color(41, 128, 185));
                utentePanel.repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                mostraValUtente(utente, l);
            }
        });

        utentePanel.add(utenteLabel, BorderLayout.CENTER);

        gbc.gridx = col;
        gbc.gridy = row;
        utentiGridPanel.add(utentePanel, gbc);

        col++;
        if (col > 1) {
            col = 0;
            row++;
        }
    }

    JScrollPane utentiScrollPane = new JScrollPane(utentiGridPanel);
    utentiScrollPane.setBorder(null);
    utentiScrollPane.getViewport().setBackground(new Color(245, 250, 255));
    utentiScrollPane.setBackground(new Color(245, 250, 255));
    utentiScrollPane.setPreferredSize(new Dimension(400, 90));
    utentiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    panelRisultatoNESugg.setLayout(new BorderLayout());
    panelRisultatoNESugg.setBackground(new Color(245, 250, 255));
    panelRisultatoNESugg.add(utentiScrollPane, BorderLayout.CENTER);

    panelRisultatoNESugg.revalidate();
    panelRisultatoNESugg.repaint();
}
   
/**
 * Mostra i dettagli del libro specificato in una finestra di dialogo.
 * Se specificato, mostra anche le valutazioni medie e le recensioni degli utenti.
 *
 * @param libro Il libro di cui mostrare i dettagli.
 * @param n Un intero che determina se mostrare le recensioni degli utenti (1 per mostrare, 0 per non mostrare).
 */
private void mostraDettagliLibro(Libro libro, int n) {
    panelRisultatoNESugg = new JPanel();
    panelRisultatoNESugg.removeAll();

    String infoLibro = Libro.getInfoBase(libro);

    String infoValutazioni;
    MediaValutazioneLibri(libro);

    if (esisteValutazione) {
        infoValutazioni = "<html><b>Media Stile:</b> " + mediaStile + "<br><b>Media Contenuto:</b> " + mediaContenuto +
                          "<br><b>Media Gradevolezza:</b> " + mediaGradevolezza +
                          "<br><b>Media Originalita:</b> " + mediaOriginalita +
                          "<br><b>Media Edizione:</b> " + mediaEdizione +
                          "<br><b>Media Voto finale:</b> " + mediaVotoFinale + "</html>";
    } else {
        infoValutazioni = "<html><b>Non ci sono valutazioni per questo libro</b></html>";
    }

    JLabel exLabel = new JLabel("");
    ArrayList<Utente> alUtentiSuggerimenti = proxy.getUtentiSuggeritori(libro);
    if (!(alUtentiSuggerimenti.isEmpty()) && n == 1) {
        exLabel = new JLabel("<html><b>Suggerimenti degli utenti:</b></html>");
    } else if (alUtentiSuggerimenti.isEmpty() && n == 1) {
        exLabel = new JLabel("<html><b>Nessun utente ha lasciato recensioni</b></html>");
    }

    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(new Color(245, 250, 255));
    contentPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
        BorderFactory.createEmptyBorder(12, 16, 12, 16)
    ));
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(6, 6, 6, 6);

    int row = 0;

    // Titolo libro
    JLabel titoloLabel = new JLabel("<html><span style='font-size:13px'><b>Dettagli libro</b></span></html>");
    titoloLabel.setFont(new Font("Arial", Font.BOLD, 13));
    titoloLabel.setForeground(new Color(41, 128, 185));
    c.gridx = 0;
    c.gridy = row++;
    c.gridwidth = 3;
    contentPanel.add(titoloLabel, c);

    // Divider
    JSeparator sep = new JSeparator();
    sep.setForeground(new Color(189, 195, 199));
    c.gridy = row++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    contentPanel.add(sep, c);

    // Info libro
    JLabel infoLabel = new JLabel(infoLibro);
    infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    infoLabel.setForeground(new Color(33, 97, 140));
    c.gridy = row++;
    c.gridx = 0;
    c.gridwidth = 3;
    contentPanel.add(infoLabel, c);

    // Divider
    JSeparator sep2 = new JSeparator();
    sep2.setForeground(new Color(189, 195, 199));
    c.gridy = row++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    contentPanel.add(sep2, c);

    // Valutazioni
    JLabel panelValutazioni = new JLabel(infoValutazioni);
    panelValutazioni.setFont(new Font("Arial", Font.PLAIN, 12));
    panelValutazioni.setForeground(new Color(21, 67, 96));
    c.gridy = row++;
    c.gridx = 0;
    c.gridwidth = 3;
    contentPanel.add(panelValutazioni, c);

    // Divider
    JSeparator sep3 = new JSeparator();
    sep3.setForeground(new Color(189, 195, 199));
    c.gridy = row++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    contentPanel.add(sep3, c);

    // Suggerimenti utenti
    c.gridy = row++;
    c.gridx = 0;
    c.gridwidth = 3;
    exLabel.setFont(new Font("Arial", Font.BOLD, 12));
    exLabel.setForeground(new Color(41, 128, 185));
    contentPanel.add(exLabel, c);

    if (!(alUtentiSuggerimenti.isEmpty()) && n == 1) {
        mostraUtentiRecensioni(alUtentiSuggerimenti, libro);
        c.gridy = row++;
        c.gridx = 0;
        c.gridwidth = 3;
        contentPanel.add(panelRisultatoNESugg, c);
    }

    JScrollPane scrollPane = new JScrollPane(contentPanel);
    scrollPane.setBorder(null);
    scrollPane.setPreferredSize(new Dimension(420, 300));
    scrollPane.setMinimumSize(new Dimension(300, 200));

    JOptionPane pane = new JOptionPane(
        scrollPane,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        new Object[]{},
        null);

    JDialog dialog = pane.createDialog(frame, "Dettagli Libro");
    dialog.setSize(600, 500);
    dialog.setMinimumSize(new Dimension(350, 320));
    dialog.setResizable(true);
    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
}

/** 
 * Calcola le medie delle valutazioni degli utenti per un determinato libro e 
 * aggiorna le variabili di media, le liste di utenti e suggerimenti associati.
 *
 * @param libro Il libro per cui calcolare le medie delle valutazioni.
 */
private void MediaValutazioneLibri(Libro libro){
    ArrayList<Double> alMediaVal = proxy.getMediaValutazioniLibro(libro);
    if(alMediaVal!=null){
        mediaStile = alMediaVal.get(0);
        mediaContenuto = alMediaVal.get(1);
        mediaGradevolezza = alMediaVal.get(2);
        mediaOriginalita = alMediaVal.get(3);
        mediaEdizione = alMediaVal.get(4);
        mediaVotoFinale = alMediaVal.get(5);
        esisteValutazione = true;

    }else{
        esisteValutazione = false;
    }
    
    
}

/**
 * Mostra i dettagli delle valutazioni e dei suggerimenti di un libro dato da un specifico utente 
 *
 * @param u L'utente di cui mostrare le valutazioni e i suggerimenti.
 * @param l Il libro di cui mostrare le valutazioni e i suggerimenti.
 */
private void mostraValUtente(Utente utente, Libro l) {
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(new Color(245, 250, 255));
    contentPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
        BorderFactory.createEmptyBorder(12, 16, 12, 16)
    ));
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(6, 6, 6, 6);

    ValutazioniLibro valLibro = proxy.getValutazioneLibro(utente, l);
    ArrayList<Libro> libriSuggeriti = proxy.getLibriSuggeriti(utente, l);

    int row = 0;

    JLabel utenteTitle = new JLabel("<html><span style='font-size:13px'><b>Recensione di " + utente.getUsername() + "</b></span></html>");
    utenteTitle.setFont(new Font("Arial", Font.BOLD, 13));
    utenteTitle.setForeground(new Color(41, 128, 185));
    c.gridx = 0;
    c.gridy = row++;
    c.gridwidth = 3;
    contentPanel.add(utenteTitle, c);

    JSeparator sep = new JSeparator();
    sep.setForeground(new Color(189, 195, 199));
    c.gridy = row++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    contentPanel.add(sep, c);

    if (valLibro != null) {
        String[] labels = {
            "Stile", "Contenuto", "Gradevolezza", "Originalità", "Edizione", "Voto finale"
        };
        int[] voti = {
            valLibro.getStile(), valLibro.getContenuto(), valLibro.getGradevolezza(),
            valLibro.getOriginalita(), valLibro.getEdizione(), valLibro.getVotoFinale()
        };
        String[] note = {
            valLibro.getNoteStile(), valLibro.getNoteContenuto(), valLibro.getNoteGradevolezza(),
            valLibro.getNoteOriginalita(), valLibro.getNoteEdizione(), valLibro.getNoteVotoFinale()
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel("<html><b>" + labels[i] + ":</b></html>");
            label.setFont(new Font("Arial", Font.BOLD, 11));
            label.setForeground(new Color(33, 97, 140));
            c.gridx = 0;
            c.gridy = row;
            c.gridwidth = 1;
            contentPanel.add(label, c);

            JLabel voto = new JLabel(String.valueOf(voti[i]));
            voto.setFont(new Font("Arial", Font.BOLD, 11));
            voto.setForeground(new Color(21, 67, 96));
            c.gridx = 1;
            contentPanel.add(voto, c);

            JTextArea noteArea = new JTextArea(note[i]);
            noteArea.setFont(new Font("Arial", Font.PLAIN, 10));
            noteArea.setLineWrap(true);
            noteArea.setWrapStyleWord(true);
            noteArea.setEditable(false);
            noteArea.setBackground(new Color(245, 250, 255));
            noteArea.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            noteArea.setRows(1);
            c.gridx = 2;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            contentPanel.add(noteArea, c);

            row++;
        }
    } else {
        JLabel labelVotiNF = new JLabel("L'utente non ha inserito voti per questo libro");
        labelVotiNF.setFont(new Font("Arial", Font.ITALIC, 11));
        labelVotiNF.setForeground(new Color(120, 120, 120));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = row++;
        contentPanel.add(labelVotiNF, c);
    }

    JSeparator sep2 = new JSeparator();
    sep2.setForeground(new Color(189, 195, 199));
    c.gridy = row++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    contentPanel.add(sep2, c);

    c.gridx = 0;
    c.gridy = row++;
    c.gridwidth = 3;
    JLabel labelSuggF = new JLabel("<html><b>Libri Suggeriti:</b></html>");
    labelSuggF.setFont(new Font("Arial", Font.BOLD, 12));
    labelSuggF.setForeground(new Color(41, 128, 185));
    contentPanel.add(labelSuggF, c);

    for (Libro libro : libriSuggeriti) {
        JLabel libroLabel = new JLabel(
            "<html><b>" + Libro.getTitolo(libro) + "</b> <span style='color:#888;'>" +
            Libro.getAutore(libro) + " &middot; " + Libro.getAnno(libro) + "</span></html>"
        );
        libroLabel.setFont(new Font("Arial", Font.BOLD, 11));
        libroLabel.setForeground(new Color(33, 97, 140));
        libroLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        libroLabel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        c.gridy = row++;
        c.gridx = 0;
        c.gridwidth = 3;
        contentPanel.add(libroLabel, c);

        libroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostraDettagliLibro(libro, 0);
            }
        });
    }

    JScrollPane scrollPane = new JScrollPane(contentPanel);
    scrollPane.setBorder(null);
    scrollPane.setPreferredSize(new Dimension(420, 300));
    scrollPane.setMinimumSize(new Dimension(300, 200));

    JOptionPane pane = new JOptionPane(
            scrollPane,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[] {},
            null);
    JDialog dialog = pane.createDialog(frame, "Dettagli Utente");
    dialog.setSize(600, 500);
    dialog.setMinimumSize(new Dimension(350, 320));
    dialog.setResizable(true);
    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
}


}
