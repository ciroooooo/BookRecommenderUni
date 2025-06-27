//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package frames;
import parametri.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.*;

public class HomeMainFrame {
    private int k;
    private int codice;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel searchPanel;
    private JPanel resultsPanel;
    private JPanel panelRisultatoNESugg;
    private JPanel panelImpostazioniProfilo;
    private JScrollPane risultatoScrollPane;
    private JButton bottoneHome;
    private JButton logout;
    private JButton bottoneLibreria;
    private JButton bottoneProfilo;
    private ArrayList<Libro> listaLibri = new ArrayList<>();
    private Utente u;
    private double mediaStile=0;
    private double mediaContenuto=0;
    private double mediaGradevolezza=0;
    private double mediaOriginalita=0;
    private double mediaEdizione=0;
    private double mediaVotoFinale=0;
    private ArrayList<Utente>alUtentiVal=new ArrayList<>();
    private ArrayList<SuggerimentoLibro>alSuggerimenti=new ArrayList<>();
    private JPanel pane=new JPanel(new GridBagLayout());

/**
 * Costruttore della classe HomeMainFrame.
 * 
 * @param codice il codice che indica il tipo di utente (0 per ospite, 1 per utente registrato)
 * @param listaLibri l'elenco dei libri da utilizzare nell'interfaccia
 * @param u l'utente corrente che ha effettuato l'accesso
 */
public HomeMainFrame(int codice, ArrayList<Libro> listaLibri, String cf) {
    this.listaLibri = listaLibri;
    this.codice = codice;
    frame = new JFrame();

    // Inizializzazione dei componenti grafici
    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    topPanel.setBackground(new Color(52, 152, 219)); // Light blue
    topPanel.setOpaque(true);

    bottoneHome = new JButton("Home");
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
        public void actionPerformed(ActionEvent e) {
            if (codice == 0) {
                JOptionPane.showMessageDialog(null, "Impossibile effettuare questa operazione come ospite");
            } else if (codice == 1) {
                Utente upass=u;
                LibrerieMainFrame myframe = new LibrerieMainFrame(listaLibri,upass);
                myframe.initialize();
                frame.dispose();
            }
        }
    });
    bottoneProfilo.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(codice==0){
                JOptionPane.showMessageDialog(null, "Impossibile effettuare questa operazione come ospite");
            }else if(codice==1){
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
    centerPanel.setBackground(Color.WHITE);

    // Titolo esplicativo sopra il box di ricerca
    JLabel titoloLabel = new JLabel("<html><div style='text-align: center;'>Inserisci un Titolo di un libro, un Autore, oppure Autore ed anno<br>e clicca il pulsante corrispondente per effettuare la ricerca</div></html>");
    titoloLabel.setFont(new Font("Arial", Font.PLAIN, 13)); // Font leggermente più piccolo
    titoloLabel.setForeground(new Color(70, 70, 70)); // Colore neutro
    titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Padding laterale e abbassamento

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
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            if (searchText.length() > 0) {
                ArrayList<Libro> risultati = cercaLibro(searchText);
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
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            if (searchText.length() > 0) {
                ArrayList<Libro> risultati = cercaLibro(new Autore(searchText));
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
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText();
            String[] parts = searchText.split(",");
            if (searchText.length() > 0) {
                if (parts.length == 2) {
                    try {
                        String autore = parts[0].trim();
                        int anno = Integer.parseInt(parts[1].trim());
                        ArrayList<Libro> risultati = cercaLibro(new Autore(autore), anno);
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
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

  
/**
 * Cerca libri nella lista per il titolo specificato, ignorando maiuscole e minuscole.
 * Restituisce un ArrayList contenente tutti i libri il cui titolo contiene la stringa specificata.
 *
 * @param titolo Il titolo da cercare nei libri.
 * @return Un ArrayList di Libri contenente i libri che corrispondono al criterio di ricerca.
 */
private ArrayList<Libro> cercaLibro(String titolo) {
    ArrayList<Libro> tmp = new ArrayList<>();
    for (Libro libro : listaLibri) {
        if (libro.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
            tmp.add(libro);
        }
    }
    return tmp;
}

    
/**
 * Cerca libri nella lista per l'autore specificato, ignorando maiuscole e minuscole.
 * Restituisce un ArrayList contenente tutti i libri scritti dall'autore specificato.
 *
 * @param autore L'Autore da utilizzare come criterio di ricerca.
 * @return Un ArrayList di Libri contenente i libri scritti dall'autore specificato.
 */
private ArrayList<Libro> cercaLibro(Autore autore) {
    ArrayList<Libro> tmp = new ArrayList<>();
    for (Libro libro : listaLibri) {
        if (libro.getAutore().toLowerCase().contains(autore.getNome().toLowerCase())) {
            tmp.add(libro);
        }
    }
    return tmp;
}

/**
 * Cerca libri nella lista in base al nome dell'autore e all'anno di pubblicazione.
 * Ignora le differenze tra maiuscole e minuscole nel nome dell'autore.
 * 
 * @param autore Nome dell'autore per la ricerca.
 * @param anno Anno di pubblicazione dei libri da cercare.
 * @return Un ArrayList di Libri contenente i libri scritti dall'autore specificato
 *         e pubblicati nell'anno indicato.
 */
private ArrayList<Libro> cercaLibro(Autore autore, int anno) {
    ArrayList<Libro> tmp = new ArrayList<>();
    for (Libro libro : listaLibri) {
        if (libro.getAutore().toLowerCase().contains(autore.getNome().toLowerCase()) && libro.getDataPubblicazione().getYear() == anno) {
            tmp.add(libro);
        }
    }
    return tmp;
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
        JPanel libroPanel = new JPanel(new BorderLayout());
        JLabel libroLabel = new JLabel(Libro.getTitolo(libro) + " " + Libro.getAutore(libro) + " " + Libro.getAnno(libro));
        libroLabel.setForeground(Color.BLUE.darker());
        libroLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        libroLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        libroPanel.add(libroLabel, BorderLayout.CENTER);
        libroPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        libroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostraDettagliLibro(libro, 1);
            }
        });

        resultsPanel.add(libroPanel);
        resultsPanel.add(Box.createVerticalStrut(5));
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

    for (Utente utente : risultato) {
        JPanel utentePanel = new JPanel(new BorderLayout());
        JLabel utenteLabel = new JLabel(utente.getUsername() + " ");
        utenteLabel.setForeground(Color.BLUE.darker());
        utenteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        utenteLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        utentePanel.add(utenteLabel, BorderLayout.CENTER);
        utentePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        utenteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostraValUtente(utente, l);
            }
        });

        panelRisultatoNESugg.add(utentePanel);
        panelRisultatoNESugg.add(Box.createVerticalStrut(5));
    }

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
    
    if (k != 0) {
        infoValutazioni = "<html><b>Media Stile:</b>" + mediaStile + "<br><b>Media Contenuto:</b>" + mediaContenuto +
                          "<br><b>Media Gradevolezza:</b>" + mediaGradevolezza;
        infoValutazioni = infoValutazioni + "<br><b>Media Originalita:</b>" + mediaOriginalita +
                          "<br><b>Media Edizione:</b>" + mediaEdizione + "<br><b>Media Voto finale:</b>" + mediaVotoFinale + "</html>";
    } else {
        infoValutazioni = "<html><b>Non ci sono valutazioni per questo libro</b></html>";
    }
    
    
    JLabel exLabel = new JLabel("");
    if (alUtentiVal.size() != 0 && n == 1) {
        exLabel = new JLabel("<html><b>Recensioni utenti:</b></html>");
    } else if (n == 1) {
        exLabel = new JLabel("<html><b>Nessun utente ha lasciato recensioni</b></html>");
    }
    
   
    GridBagConstraints c = new GridBagConstraints();
    JPanel panel = new JPanel(new GridBagLayout());
    JLabel infoLabel = new JLabel();
    infoLabel.setText(infoLibro);
    panel.setBackground(Color.decode("#f0f0f0"));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(20, 20, 20, 20);
    
   
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    panel.add(infoLabel, c);
    
    
    JLabel panelValutazioni = new JLabel(infoValutazioni);
    c.gridy = 1;
    panel.add(panelValutazioni, c);
    
    
    c.gridy = 2;
    panel.add(exLabel, c);
    
    
    if (n == 1) {
        mostraUtentiRecensioni(alUtentiVal, libro);
        c.gridy = 3;
        panel.add(panelRisultatoNESugg, c);
    }
    
    JOptionPane pane = new JOptionPane(
        panel,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        new Object[]{},
        null);
    
    JDialog dialog = pane.createDialog(frame, "Dettagli Libro");
    dialog.pack();
    dialog.setVisible(true);
}
    
/**
 * Calcola le medie delle valutazioni degli utenti per un determinato libro e 
 * aggiorna le variabili di media, le liste di utenti e suggerimenti associati.
 *
 * @param libro Il libro per cui calcolare le medie delle valutazioni.
 */
private void MediaValutazioneLibri(Libro libro){
    alUtentiVal.clear();
    alSuggerimenti.clear();
    mediaStile = 0;
    mediaContenuto = 0;
    mediaGradevolezza = 0;
    mediaOriginalita = 0;
    mediaEdizione = 0;
    mediaVotoFinale = 0;
    k = 0;

    ArrayList<ValutazioniLibro> alVl;
    alVl = leggiFile(); 

    ArrayList<SuggerimentoLibro> tmpSugg = SuggerimentoLibro.leggiFileSugg(); 

    for (int i = 0; i < alVl.size(); i++) {
        if (alVl.get(i).getLibro().getTitolo().equals(libro.getTitolo())) {
            alUtentiVal.add(alVl.get(i).getUtente());
            mediaStile += alVl.get(i).getStile();
            mediaContenuto += alVl.get(i).getContenuto();
            mediaGradevolezza += alVl.get(i).getGradevolezza();
            mediaOriginalita += alVl.get(i).getOriginalita();
            mediaEdizione += alVl.get(i).getEdizione();
            mediaVotoFinale += alVl.get(i).getVotoFinale();
            k++;
        }
    }

    for (int i = 0; i < tmpSugg.size(); i++) {
        for (int j = 0; j < alUtentiVal.size(); j++) {
            if (alUtentiVal.get(j).getUsername().equals(tmpSugg.get(i).getUtente().getUsername()) && 
                tmpSugg.get(i).getLibro().getTitolo().equals(libro.getTitolo())) {
                alSuggerimenti.add(tmpSugg.get(i));
            }
        }
    }

    if (k != 0) {
        mediaStile /= k;
        mediaContenuto /= k;
        mediaGradevolezza /= k;
        mediaOriginalita /= k;
        mediaEdizione /= k;
        mediaVotoFinale /= k;
    }
}

/**
 * Mostra i dettagli delle valutazioni e dei suggerimenti di un libro dato da un specifico utente 
 *
 * @param u L'utente di cui mostrare le valutazioni e i suggerimenti.
 * @param l Il libro di cui mostrare le valutazioni e i suggerimenti.
 */
private void mostraValUtente(Utente u, Libro l) {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.decode("#f0f0f0"));
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 10, 10);

    ArrayList<ValutazioniLibro> tmp = ValutazioniLibro.leggiFileVL();
    ValutazioniLibro valLibro = new ValutazioniLibro(u, l);
    boolean valTrovata = false;

    for (int i = 0; i < tmp.size(); i++) {
        if (tmp.get(i).getUtente().getUsername().equals(u.getUsername()) && tmp.get(i).getLibro().getTitolo().equals(l.getTitolo())) {
            valTrovata = true;
            valLibro = tmp.get(i);
            break;
        }
    }

    ArrayList<SuggerimentoLibro> tmpS = SuggerimentoLibro.leggiFileSugg();
    SuggerimentoLibro sl = new SuggerimentoLibro(l, u);
    boolean suggTrovato = false;

    for (int i = 0; i < tmpS.size(); i++) {
        if (tmpS.get(i).getUtente().getUsername().equals(u.getUsername()) && tmpS.get(i).getLibro().getTitolo().equals(l.getTitolo())) {
            suggTrovato = true;
            sl = tmpS.get(i);
            break;
        }
    }

    if (valTrovata) {
        JLabel labelStile = new JLabel("<html><b>Voto Stile:</b>" + valLibro.getStile() + "<br>Note Stile:" + valLibro.getNoteStile());
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(labelStile, c);

        JLabel labelContenuto = new JLabel("<html><b>Voto Contenuto:</b>" + valLibro.getContenuto() + "<br>Note Contenuto:" + valLibro.getNoteContenuto());
        c.gridy = 1;
        panel.add(labelContenuto, c);

        JLabel labelGradevolezza = new JLabel("<html><b>Voto Gradevolezza:</b>" + valLibro.getGradevolezza() + "<br>Note Gradevolezza:" + valLibro.getNoteGradevolezza());
        c.gridy = 2;
        panel.add(labelGradevolezza, c);

        JLabel labelOriginalita = new JLabel("<html><b>Voto originalita:</b>" + valLibro.getOriginalita() + "<br>Note Originalita:" + valLibro.getNoteOriginalita());
        c.gridy = 3;
        panel.add(labelOriginalita, c);

        JLabel labelEdizione = new JLabel("<html><b>Voto Edizione:</b>" + valLibro.getEdizione() + "<br>Note Edizione:" + valLibro.getNoteEdizione());
        c.gridy = 4;
        panel.add(labelEdizione, c);

        JLabel labelVotoFinale = new JLabel("<html><b>Voto finale:</b>" + valLibro.getVotoFinale() + "<br>Note voto finale:" + valLibro.getNoteVotoFinale());
        c.gridy = 5;
        panel.add(labelVotoFinale, c);
    } else {
        JLabel labelVotiNF = new JLabel("L'utente non ha inserito voti per questo libro");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(labelVotiNF, c);
    }

    if (suggTrovato) {
        c.gridx = 0;
        int y = c.gridy + 1;
        c.gridy = y;
        JLabel labelSuggF = new JLabel("<html><b>Libri Suggeriti:</b><br></html>");
        panel.add(labelSuggF, c);

        y++;
        ArrayList<Libro> libriSuggeriti = sl.getALLibri();
        for (Libro libro : libriSuggeriti) {
            JLabel libroLabel = new JLabel(Libro.getTitolo(libro) + " " + Libro.getAutore(libro) + " " + Libro.getAnno(libro));
            libroLabel.setForeground(Color.BLUE.darker());
            libroLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            libroLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            c.gridy = y;
            y++;
            panel.add(libroLabel, c);
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            libroLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    mostraDettagliLibro(libro, 0);
                }
            });
        }
    } else {
        c.gridx = 0;
        c.gridy = c.gridy + 1;
        JLabel labelSuggNF = new JLabel("L'utente non ha inserito suggerimenti per questo libro");
        panel.add(labelSuggNF, c);
    }

    JOptionPane pane = new JOptionPane(
            panel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{},
            null);
    JDialog dialog = pane.createDialog(frame, "Dettagli Utente");
    dialog.pack();
    dialog.setVisible(true);
}

/**
 * Legge le valutazioni dei libri da un file e restituisce un ArrayList di ValutazioniLibro.
 *
 * @return Un ArrayList di ValutazioniLibro contenente le valutazioni lette dal file.
 */
public ArrayList<ValutazioniLibro> leggiFile() {
    ArrayList<ValutazioniLibro> al = new ArrayList<>();
    File file = new File("file\\Valutazioni.txt");
    if (file.length() != 0) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            al = (ArrayList<ValutazioniLibro>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return al;
}

}
