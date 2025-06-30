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

public class LibrerieMainFrame {
    private Libro libroCorrente;
    private JFrame frame;
    private JPanel topPanel;
    private String nomeLib;
    private JPanel resultsPanel;
    private JScrollPane risultatoScrollPane;
    private JScrollPane risultatoScrollPaneLib;
    private JButton bottoneHome;
    private JButton bottoneCLibrerie;
    private ArrayList<Libro> listaLibri = new ArrayList<>();
    private ArrayList<Boolean> libriVisibili = new ArrayList<>();
    private ArrayList<Libro> libriDaAggiungere = new ArrayList<>();
    private Utente u;
    private String cf;
    private ArrayList<Libro> alSuggerimenti = new ArrayList<>();
    private int k=0;
    private JScrollPane risultatoScrollPaneSugg;
    private ArrayList<SuggerimentoLibro> alSugg=new ArrayList<>();
    private JButton searchByTitleButton,searchByAuthorButton,searchByAuthorAndYearButton;
    private JPanel OptionPanel1 = new JPanel(new GridBagLayout());
    private JTextField searchField;
    private GridBagConstraints c=new GridBagConstraints();
    private JLabel labelRicerca;
    private boolean firstTime=true;
    private Proxy proxy;
    private ArrayList<Librerie> listaLibrerieUtente;
/**
 * Costruttore per la classe LibrerieMainFrame.
 * Inizializza il frame principale per la gestione delle librerie di libri.
 * Configura il layout, i pannelli e i bottoni necessari per l'interfaccia utente.
 *
 * @param listaLibri una lista di oggetti {@code Libro} da visualizzare e gestire.
 * 
 * @throws IOException se si verifica un errore durante la lettura dei file di librerie o suggerimenti.
 * @throws ClassNotFoundException se la classe degli oggetti letti dai file non viene trovata.
 */
    public LibrerieMainFrame(Proxy proxy, String cf) {
        this.proxy = proxy;
        u = proxy.getUtenteDaCF(cf);
        this.cf = cf;
        listaLibrerieUtente = proxy.getLibrerieUtente(cf);
        frame = new JFrame();
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setBackground(new Color(41, 128, 185)); // Stesso colore di HomeMainFrame
        topPanel.setOpaque(true);

        bottoneHome = new JButton("Home");
        bottoneCLibrerie = new JButton("Crea Librerie");
        customizeButton(bottoneHome);
        customizeButton(bottoneCLibrerie);

        // Imposta colore dei bottoni come HomeMainFrame
        bottoneHome.setBackground(new Color(41, 128, 185));
        bottoneCLibrerie.setBackground(new Color(41, 128, 185));
        bottoneHome.setForeground(Color.WHITE);
        bottoneCLibrerie.setForeground(Color.WHITE);

        // Effetto hover come HomeMainFrame
        bottoneHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bottoneHome.setBackground(new Color(93, 173, 226));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                bottoneHome.setBackground(new Color(41, 128, 185));
            }
        });
        bottoneCLibrerie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bottoneCLibrerie.setBackground(new Color(93, 173, 226));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                bottoneCLibrerie.setBackground(new Color(41, 128, 185));
            }
        });

        topPanel.add(bottoneHome);
        topPanel.add(bottoneCLibrerie);

        bottoneHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                HomeMainFrame myFrame=new HomeMainFrame(proxy,cf);
                frame.dispose();
                myFrame.initialize();
            }
        });
        
        bottoneCLibrerie.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                JPanel OptionPanelCLib = new JPanel(new GridBagLayout());
                libriDaAggiungere.clear();
                GridBagConstraints c=new GridBagConstraints();
                c.insets = new Insets(10, 10, 10, 10);
                
                JLabel nomeLabel = new JLabel("Nome Libreria:");

                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                OptionPanelCLib.add(nomeLabel, c);

                JTextField nomeLibField= new JTextField(20);
                nomeLib = nomeLibField.getText();

                c.gridx = 2;
                c.gridwidth = 2;
                OptionPanelCLib.add(nomeLibField, c);

                AggiungiBottoniECampoRicerca(OptionPanelCLib);
                
                JPanel risultatoRicercaLibrerie=new JPanel();
                risultatoRicercaLibrerie.setBackground(Color.white);
                risultatoRicercaLibrerie.setLayout(new BoxLayout(risultatoRicercaLibrerie, BoxLayout.Y_AXIS));
                risultatoScrollPaneLib = new JScrollPane(risultatoRicercaLibrerie); 
                risultatoScrollPaneLib.setPreferredSize(new Dimension(300, 200));
                c.gridx = 0;
                c.gridy = 6;
                c.gridwidth = 6;
                c.fill = GridBagConstraints.BOTH;
                OptionPanelCLib.add(risultatoScrollPaneLib, c);

                JButton ApplicaButton=new JButton("Applica");
                c.gridx=0;
                c.gridy=8;
                c.gridwidth=1;
                OptionPanelCLib.add(ApplicaButton,c);
                
                JButton cancelButton=new JButton("Annulla");
                c.gridx=3;
                c.gridy=8;
                OptionPanelCLib.add(cancelButton,c);

                JOptionPane pane = new JOptionPane(
                OptionPanelCLib,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null);
                
                searchByTitleButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        risultatoRicercaLibrerie.removeAll();
                        String testo=searchField.getText();
                        ArrayList<Libro>risultati=proxy.ricercaPerTitolo(testo);
                        mostraRisultati(risultati,0,risultatoRicercaLibrerie);
                    }
                });

                searchByAuthorButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        risultatoRicercaLibrerie.removeAll();
                        String testo=searchField.getText();
                        ArrayList<Libro>risultati=proxy.ricercaPerAutore(new Autore(testo));
                        mostraRisultati(risultati,0,risultatoRicercaLibrerie);
                    }
                });
                
                searchByAuthorAndYearButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        risultatoRicercaLibrerie.removeAll();
                       String testo=searchField.getText();
                       String[] testoSplit = testo.split(",");
                       String nomeAutore = testoSplit[0].trim();
                       int anno = Integer.parseInt(testoSplit[1].trim());
                       ArrayList<Libro>risultati=proxy.ricercaPerAutoreEAnno(new Autore(nomeAutore), anno);
                       mostraRisultati(risultati, 0,risultatoRicercaLibrerie);
                    }
                });
                JDialog dialog=pane.createDialog(frame,"Creazione Libreria");
                ApplicaButton.addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e){
                        String nomeLibreria = nomeLibField.getText();
                        if(nomeLibreria.length()==0){
                            JOptionPane.showMessageDialog(frame,"Inserire il nome della libreria");
                            return;
                        }                     
                        ArrayList<String> nomiLibrerieUtente = proxy.getNomiLibreriaDaUtente(cf);
                        for(int i = 0;i<nomiLibrerieUtente.size();i++){
                            if(nomiLibrerieUtente.get(i).equals(nomeLibreria)){
                                JOptionPane.showMessageDialog(frame,"Esiste gia' una libreria con questo nome");
                                return;
                            }
                        }
                        if(libriDaAggiungere.isEmpty()){
                            JOptionPane.showMessageDialog(frame,"Inserire almeno un libro");
                            return;
                        }
                        Librerie libreria = new Librerie(nomeLibreria, u, libriDaAggiungere);
                        proxy.aggiungiLibreria(libreria);
                        JOptionPane.showMessageDialog(frame, "Libreria Creata con successo");
                        searchField.setText("");
                        nomeLibField.setText("");
                        risultatoRicercaLibrerie.removeAll();
                        risultatoRicercaLibrerie.revalidate();
                        risultatoRicercaLibrerie.repaint();
                        listaLibrerieUtente = proxy.getLibrerieUtente(cf);
                        listaLibrerie(listaLibrerieUtente);
                        dialog.dispose();
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        dialog.dispose();
                        risultatoRicercaLibrerie.removeAll();
                    }
                });
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        resultsPanel = new JPanel(new GridBagLayout());
        resultsPanel.setBackground(Color.white);
        resultsPanel.repaint();
        resultsPanel.validate();
        risultatoScrollPane = new JScrollPane(resultsPanel);
        risultatoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        risultatoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        risultatoScrollPane.setPreferredSize(new Dimension(300, 200)); // Allineato a HomeMainFrame
        listaLibrerie(listaLibrerieUtente);
        frame.setLayout(new BorderLayout());
        frame.add(risultatoScrollPane, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);
    }
/**
 * Visualizza l'elenco delle librerie dell'utente all'interno del pannello delle librerie.
 * Questo metodo rimuove tutti i componenti esistenti dal pannello dei risultati
 * e aggiunge un nuovo pannello per ciascuna libreria nella lista filtrata.
 * Ogni pannello della libreria contiene un'etichetta con il nome della libreria e,
 * quando viene cliccata, mostra o nasconde i libri appartenenti alla libreria.
 *
 * @param listaLibrerieUtente la lista delle librerie filtrate da visualizzare.
 * 
 * @throws NullPointerException se {@code listaLibrerieUtente} è null.
 */
private void listaLibrerie(ArrayList<Librerie> listaLibrerieUtente) {
    resultsPanel.removeAll();
    // Aggiungi il titolo sopra l'elenco delle librerie
    JLabel titoloLabel = new JLabel("Elenco Librerie");
    titoloLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titoloLabel.setForeground(new Color(70, 130, 180)); // Azzurro
    titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titoloLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    resultsPanel.add(titoloLabel);

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 10, 10);

    for (int i = 0; i < listaLibrerieUtente.size(); i++) {
        String nomeLibreria = listaLibrerieUtente.get(i).getNome();
        JPanel libreriaPanel = new JPanel();
        libreriaPanel.setLayout(new BoxLayout(libreriaPanel, BoxLayout.Y_AXIS));
        libreriaPanel.setBackground(Color.WHITE);
        libreriaPanel.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 2, true));
        libreriaPanel.setPreferredSize(new Dimension(260, 80));
        libreriaPanel.setMaximumSize(new Dimension(260, 80));

        JLabel libreriaLabel = new JLabel(nomeLibreria);
        libreriaLabel.setForeground(new Color(41, 128, 185)); // Stesso colore del top panel
        libreriaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        libreriaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        libreriaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        libreriaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        libreriaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nomeCurrentLibreria = nomeLibreria;
                Librerie libreria = null;
                libreria = proxy.getLibreriaUtenteDaNome(u,nomeLibreria);
                apriTabLibreria(libreria);
            }
        });

        libreriaPanel.add(libreriaLabel);
        c.gridx = 0;
        c.gridy = i + 1;
        resultsPanel.add(libreriaPanel, c);
    }

    resultsPanel.revalidate();
    resultsPanel.repaint();
}

/**
 * Apre un nuovo tab con l'elenco dei libri contenuti nella libreria selezionata.
 * 
 * @param libreria La libreria selezionata.
 */
private void apriTabLibreria(Librerie libreria) {
    // Colori coerenti con il top panel
    Color topPanelColor = new Color(41, 128, 185);
    Color hoverColor = new Color(93, 173, 226);
    Color libroBg = new Color(240, 248, 255);

    JFrame tabFrame = new JFrame(libreria.getNome()); // Titolo della finestra con il nome della libreria
    tabFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    tabFrame.setSize(350, 500); // stessa size di LibrerieMainFrame
    tabFrame.setMinimumSize(new Dimension(300, 400));
    tabFrame.setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);

    // Lista libri
    JPanel listaLibriPanel = new JPanel();
    listaLibriPanel.setLayout(new BoxLayout(listaLibriPanel, BoxLayout.Y_AXIS));
    listaLibriPanel.setBackground(Color.WHITE);

    for (Libro libro : libreria.getAlLibri()) {
        JPanel libroPanel = new JPanel(new BorderLayout());
        libroPanel.setBackground(libroBg);
        libroPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 10, 0, 10),
            BorderFactory.createLineBorder(topPanelColor, 1, true)
        ));
        libroPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        libroPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel libroLabel = new JLabel(
            "<html><b>" + libro.getTitolo() + "</b> - " + libro.getAutore() + " (" + libro.getDataPubblicazione().getYear() + ")</html>"
        );
        libroLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        libroLabel.setForeground(Color.DARK_GRAY);
        libroLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        libroPanel.add(libroLabel, BorderLayout.CENTER);

        // Hover effect
        libroPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                libroPanel.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                libroPanel.setBackground(libroBg);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                Opzioni(libro);
            }
        });

        listaLibriPanel.add(libroPanel);
        listaLibriPanel.add(Box.createVerticalStrut(8));
    }

    JScrollPane scrollPane = new JScrollPane(listaLibriPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.setBackground(Color.WHITE);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Bottone indietro in basso a sinistra
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottomPanel.setBackground(Color.WHITE);
    JButton indietroButton = new JButton("Indietro");
    indietroButton.setFont(new Font("Arial", Font.BOLD, 14));
    indietroButton.setBackground(topPanelColor);
    indietroButton.setForeground(Color.WHITE);
    indietroButton.setPreferredSize(new Dimension(110, 40));
    indietroButton.setBorderPainted(false);
    indietroButton.setFocusPainted(false);
    indietroButton.setContentAreaFilled(false);
    indietroButton.setOpaque(true);
    indietroButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            indietroButton.setBackground(hoverColor);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            indietroButton.setBackground(topPanelColor);
        }
    });
    indietroButton.addActionListener(e -> tabFrame.dispose());
    bottomPanel.add(indietroButton);

    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    tabFrame.setContentPane(mainPanel);
    tabFrame.setVisible(true);
}

/**
 * Personalizza l'aspetto di un pulsante.
 * <p>
 * Questo metodo imposta la dimensione, il font, i colori di sfondo e testo,
 * e gestisce gli eventi del mouse per un pulsante specificato.
 * </p>
 *
 * @param bottone il pulsante da personalizzare.
 */
    private void customizeButton(JButton bottone) { 
        bottone.setFont(new Font("Arial", Font.BOLD, 14));
        bottone.setBackground(new Color(135, 206, 250));
        bottone.setForeground(Color.BLACK);
        bottone.setPreferredSize(new Dimension(150, 50));
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
 * Inizializza e visualizza il frame principale dell'applicazione Librerie.
 * <p>
 * Questo metodo imposta il titolo, le dimensioni, il comportamento di chiusura,
 * e la posizione del frame principale. Infine, rende visibile il frame.
 * </p>
 */
    public void initialize() {
        frame.setTitle("Librerie");
        frame.setSize(350, 500); // Stesse dimensioni di HomeMainFrame
        frame.setMinimumSize(new Dimension(300, 400)); // Stesse dimensioni minime di HomeMainFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
  /**
 * Mostra un dialogo per inserire o modificare le valutazioni di un libro da parte di un utente.
 * 
 * @param libro Il libro per il quale inserire o modificare le valutazioni.
 */  
    private void mostraInserimentoValutazioni(Libro libro) { 
        String infoLibro=Libro.getInfoBase(libro);
        JLabel infoLabel = new JLabel(infoLibro);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#f0f0f0"));
        GridBagConstraints c=new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(infoLabel);

        
        Boolean esisteValutazione = proxy.esisteValutazioneLibroUtente(u, libro);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        String[] arrayPunteggio={"1","2","3","4","5"};
        JComboBox<String> boxStile = new JComboBox<>(arrayPunteggio);

        JComboBox<String> boxContenuto = new JComboBox<>(arrayPunteggio);

        JComboBox<String> boxGradevolezza = new JComboBox<>(arrayPunteggio);

        JComboBox<String> boxOriginalita = new JComboBox<>(arrayPunteggio);

        JComboBox<String> boxEdizione = new JComboBox<>(arrayPunteggio);

        JComboBox<String> boxVotoFinale = new JComboBox<>(arrayPunteggio);

        JButton applicaButton=new JButton("Applica");
        JButton cancelButton=new JButton("Annulla");

        
        
        String info="Stile:";
        JTextField aggiunteStile = new JTextField(20);
        
        c.gridwidth=1;
        JLabel stileLabel=new JLabel(info);
        c.gridx=0;
        c.gridy=4;
        panel.add(stileLabel,c);

        c.gridx=1;
        c.gridy=4;
        panel.add(boxStile,c);

        c.gridx=2;
        panel.add(aggiunteStile,c);
        
        info="Contenuto:";
        JLabel contenutoLabel=new JLabel(info);

        c.gridx=0;
        c.gridy=5;
        panel.add(contenutoLabel,c);

        c.gridx=1;
        c.gridy=5;
        panel.add(boxContenuto,c);

        JTextField aggiunteContenuto=new JTextField(20);
        c.gridx=2;
        panel.add(aggiunteContenuto,c);

        JLabel gradevolezzaLabel=new JLabel("gradevolezza");

        c.gridx=0;
        c.gridy=6;
        panel.add(gradevolezzaLabel,c);

        c.gridx=1;
        c.gridy=6;
        panel.add(boxGradevolezza,c);

        JTextField aggiunteGradevolezza=new JTextField(20);
        c.gridx=2;
        panel.add(aggiunteGradevolezza,c);
        JLabel originalitaLabel=new JLabel("Originalita:");

        c.gridx=0;
        c.gridy=7;
        panel.add(originalitaLabel,c);

        c.gridx=1;
        c.gridy=7;
        panel.add(boxOriginalita,c);
        JTextField aggiunteOriginalita=new JTextField(20);
        c.gridx=2;
        panel.add(aggiunteOriginalita,c);
        
        JLabel edizioneLabel=new JLabel("Edizione:");

        c.gridx=0;
        c.gridy=8;
        panel.add(edizioneLabel,c);

        c.gridx=1;
        c.gridy=8;
        panel.add(boxEdizione,c);
        JTextField aggiunteEdizione=new JTextField(20);
        c.gridx=2;
        
        panel.add(aggiunteEdizione,c);

        JLabel votoFinaleLabel=new JLabel("Voto finale:");

        c.gridx=0;
        c.gridy=9;
        panel.add(votoFinaleLabel,c);

        c.gridx=1;
        c.gridy=9;
        panel.add(boxVotoFinale,c);
        JTextField aggiunteVotoFinale=new JTextField(20);
        c.gridx=2;
        
        panel.add(aggiunteVotoFinale,c);

        c.gridx=0;
        c.gridy=10;
        panel.add(cancelButton,c);

        c.gridx=1;
        c.gridy=10;
        panel.add(applicaButton,c);

        if(esisteValutazione){ 
            ValutazioniLibro vl = proxy.getValutazioneLibro(u,libro);
            boxStile.setSelectedIndex(vl.getStile()-1);
            boxContenuto.setSelectedIndex(vl.getContenuto()-1);
            boxGradevolezza.setSelectedIndex(vl.getGradevolezza()-1);
            boxOriginalita.setSelectedIndex(vl.getOriginalita()-1);
            boxEdizione.setSelectedIndex(vl.getEdizione()-1);
            boxVotoFinale.setSelectedIndex(vl.getVotoFinale()-1);
            aggiunteStile.setText(vl.getNoteStile());
            aggiunteContenuto.setText(vl.getNoteContenuto());
            aggiunteGradevolezza.setText(vl.getNoteGradevolezza());
            aggiunteOriginalita.setText(vl.getNoteOriginalita());
            aggiunteEdizione.setText(vl.getNoteEdizione());
            aggiunteVotoFinale.setText(vl.getNoteVotoFinale());
        }
        frame.add(panel);
        JOptionPane provaPane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null);
        JDialog provaDialog=provaPane.createDialog(frame,"Valutazioni");
        applicaButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                int votoStile=Integer.parseInt(boxStile.getSelectedItem().toString());
                int votoContenuto=Integer.parseInt(boxContenuto.getSelectedItem().toString());
                int votoGradevolezza=Integer.parseInt(boxGradevolezza.getSelectedItem().toString());
                int votoOriginalita=Integer.parseInt(boxOriginalita.getSelectedItem().toString());
                int votoEdizione=Integer.parseInt(boxEdizione.getSelectedItem().toString());
                int votoFinale=Integer.parseInt(boxVotoFinale.getSelectedItem().toString());
                String noteStile=aggiunteStile.getText();
                String noteContenuto=aggiunteContenuto.getText();
                String noteGradevolezza=aggiunteGradevolezza.getText();
                String noteOriginalita=aggiunteOriginalita.getText();
                String noteEdizione=aggiunteEdizione.getText();
                String noteVotoFinale=aggiunteVotoFinale.getText();
                ValutazioniLibro valLibro=new ValutazioniLibro(u, libro);
                if(noteStile.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note stile non può essere più lunga di 255 caratteri");
                }else if(noteContenuto.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note contenuto non può essere più lunga di 255 caratteri");
                }else if(noteGradevolezza.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note gradevolezza non può essere più lunga di 255 caratteri");
                }else if(noteOriginalita.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note originalità non può essere più lunga di 255 caratteri");
                }else if(noteEdizione.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note edizione non può essere più lunga di 255 caratteri");
                }else if(noteVotoFinale.length()>255){
                    JOptionPane.showMessageDialog(frame,"La lunghezza delle note sul voto finale non può essere più lunga di 255 caratteri");
                }else{
                    valLibro.inserisciValutazioneLibro(votoStile, noteStile, votoContenuto, noteContenuto, votoGradevolezza, noteGradevolezza, votoOriginalita, noteOriginalita, votoEdizione, noteEdizione, votoFinale, noteVotoFinale);
                    proxy.aggiungiValutazione(valLibro);
                    provaDialog.dispose();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                provaDialog.dispose();
            }
        });
        provaDialog.pack();
        provaDialog.setVisible(true);
    }
  /**
 * Mostra i risultati della ricerca dei libri all'interno di un pannello.
 * 
 * @param risultati ArrayList dei libri da mostrare come risultato della ricerca.
 * @param n Indica il contesto in cui vengono mostrati i risultati:
 *          - 0 per la visualizzazione nella lista delle librerie.
 *          - 1 per la visualizzazione in un altro contesto.
 * @param panel Il pannello su cui aggiungere i risultati della ricerca.
 */  
    private void mostraRisultati(ArrayList<Libro> risultati,int n,JPanel panel) {
        panel.removeAll();
        for (Libro libro : risultati) {
            JPanel libroPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Bordo arrotondato come HomeMainFrame
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
                    if(n==0){
                        mostraInfoInLibrerie(libro,0);
                    }else if(n==1){
                        mostraInfoInLibrerie(libro,1);
                    }
                }
            });

            libroPanel.add(libroLabel, BorderLayout.CENTER);
            panel.add(libroPanel);
            panel.add(Box.createVerticalStrut(8));
        }
        panel.revalidate();
        panel.repaint();
    }
/**
 * Mostra le informazioni dettagliate di un libro in un pannello.
 * Consente all'utente di aggiungere il libro alla propria collezione o ai suggerimenti.
 * 
 * @param libro Il libro di cui mostrare le informazioni dettagliate.
 * @param n Indica il contesto in cui viene mostrata l'informazione:
 *          - 0 per la visualizzazione nelle librerie dell'utente.
 *          - 1 per la visualizzazione nei suggerimenti.
 */
    private void mostraInfoInLibrerie(Libro libro,int n) {
        String infoLibro=Libro.getInfoBase(libro);
        GridBagConstraints c=new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel infoLabel=new JLabel(infoLibro);
        panel.setBackground(Color.decode("#f0f0f0"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(infoLabel);
        JButton AggiungiLibro = new JButton("✔️");
        JLabel pulsanteLabel=new JLabel("<html><b>Aggiungi:<b><html>");
        c.gridx=0;
        c.gridy=3;
        panel.add(pulsanteLabel,c);

        c.gridx=1;
        c.gridy=3;

        panel.add(AggiungiLibro,c);
        frame.add(panel);
        
        JOptionPane pane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null);
        JDialog dialog=pane.createDialog(frame,"");
        if(n==0){
            AggiungiLibro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    boolean libroAggiunto=true;
                    firstTime = true;
                    if(libriDaAggiungere.isEmpty()){
                        libriDaAggiungere.add(libro);
                        dialog.dispose();
                        libroAggiunto=false;
                        firstTime = false;
                    }else{
                        for(int i=0;i<libriDaAggiungere.size() && libroAggiunto;i++){
                            if(libriDaAggiungere.get(i).getTitolo().equals(libro.getTitolo())){
                                libroAggiunto=false;
                            }
                        }
                    }
                    if(libroAggiunto){
                        libriDaAggiungere.add(libro);
                        dialog.dispose();
                    }else if(!libroAggiunto && firstTime){
                        JOptionPane.showMessageDialog(frame,"Libro gia inserito");
                    }
                }  
            });
        }else if(n==1){
            AggiungiLibro.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e){
                    boolean aggiunto=true;
                    firstTime=true;
                    boolean controlloLibrerie=true;
                    if(alSuggerimenti.size()==0){
                        alSuggerimenti.add(libro);
                        aggiunto=false;
                        firstTime=false;
                        dialog.dispose();
                    }else if(aggiunto){
                        if(alSuggerimenti.size()<3){
                            for(int i=0;i<alSuggerimenti.size();i++){
                                if(alSuggerimenti.get(i).getTitolo().equals(libro.getTitolo())){
                                    aggiunto=false;
                                }
                            }
                        }else{
                            aggiunto=false;
                            JOptionPane.showMessageDialog(frame,"Hai inserito il numero massimo di libri");
                            controlloLibrerie=false;
                        }
                    }
                        boolean sameLib=true;
                        if(libro.getTitolo().equals(libroCorrente.getTitolo())){
                            sameLib=false;
                        }
                        if(aggiunto && sameLib){
                            alSuggerimenti.add(libro);
                            aggiunto=false;
                            dialog.dispose();
                        }
                        else if(!sameLib){
                            JOptionPane.showMessageDialog(frame,"non puoi suggerire lo stesso libro di cui stai inserendo suggerimenti");
                        }else if(!aggiunto && firstTime){
                            if(alSuggerimenti.size()<=3){
                                if(controlloLibrerie){
                                    JOptionPane.showMessageDialog(frame,"Libro gia inserito");
                                }
                            }
                        }
                    
                }
            });
        }
        dialog.pack();
        dialog.setVisible(true);
    }
    
/**
 * Mostra l'interfaccia utente per inserire suggerimenti per un libro specifico.
 * 
 * @param libro Il libro per il quale si vogliono inserire i suggerimenti.
 */    
    private void Suggerimenti(Libro libro){
        libroCorrente=libro;
        alSuggerimenti.clear();
        AggiungiBottoniECampoRicerca(OptionPanel1);
       
       
        JPanel risultatoRicercaSuggerimenti=new JPanel();
        risultatoRicercaSuggerimenti.setBackground(Color.white);
        risultatoRicercaSuggerimenti.setLayout(new BoxLayout(risultatoRicercaSuggerimenti, BoxLayout.Y_AXIS));
        risultatoScrollPaneSugg=new JScrollPane(risultatoRicercaSuggerimenti);
        risultatoScrollPaneSugg = new JScrollPane(risultatoRicercaSuggerimenti);
        risultatoScrollPaneSugg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        risultatoScrollPaneSugg.setPreferredSize(new Dimension(300, 200));
        
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 6;
        c.fill = GridBagConstraints.BOTH;
        OptionPanel1.add(risultatoScrollPaneSugg,c);

        JButton ApplicaButton=new JButton("Applica");
        c.gridx=0;
        c.gridy=8;
        c.gridwidth=1;
        OptionPanel1.add(ApplicaButton,c);
        
        JButton cancelButton=new JButton("Annulla");
        c.gridx=3;
        c.gridy=8;
        OptionPanel1.add(cancelButton,c);

        searchByTitleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                risultatoRicercaSuggerimenti.removeAll();
                String testo=searchField.getText();
                ArrayList<Libro>risultati= proxy.ricercaPerTitolo(testo);
                mostraRisultati(risultati,1,risultatoRicercaSuggerimenti);
            }
        });

        searchByAuthorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                risultatoRicercaSuggerimenti.removeAll();
                String testo=searchField.getText();
                ArrayList<Libro>risultati=proxy.ricercaPerTitolo(testo);
                mostraRisultati(risultati,1,risultatoRicercaSuggerimenti);
            }
        });
        
        searchByAuthorAndYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                risultatoRicercaSuggerimenti.removeAll();
                String testo=searchField.getText();
                String[] testoSplit = testo.split(",");
                String nomeAutore = testoSplit[0].trim();
                int anno = Integer.parseInt(testoSplit[1].trim());
                ArrayList<Libro>risultati= proxy.ricercaPerAutoreEAnno(new Autore(nomeAutore), anno);
                mostraRisultati(risultati,1,risultatoRicercaSuggerimenti);
            }
        }); 

        JOptionPane pane = new JOptionPane(
        OptionPanel1,
        JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION,
        null,
        new Object[]{},
        null);
        JDialog dialog=pane.createDialog(frame,"Suggerimenti Libri");
        ApplicaButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){  
                if(alSuggerimenti.size()>0){
                    SuggerimentoLibro sl = new SuggerimentoLibro(libro,u); 
                    sl.inserisciSuggerimento(alSuggerimenti);
                    boolean controlloLibrerie=true;
                    for(int i=0;i<alSugg.size();i++){
                        if(alSugg.get(i).getLibro().getTitolo().equals(libro.getTitolo()) && u.getUsername().equals(alSugg.get(i).getUtente().getUsername())){
                            controlloLibrerie=false;
                        }
                    }
                    if(controlloLibrerie){
                        alSugg.add(sl);
                        //qui si scriveva sul file tutto l'arrayList
                        risultatoRicercaSuggerimenti.removeAll();
                        dialog.dispose();
                    }else{
                        JOptionPane.showMessageDialog(frame,"Esiste gia un suggerimento per questo libro");
                    }
                }else{
                    JOptionPane.showMessageDialog(frame,"Inserire almeno un libro suggerito");
                }              
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dialog.dispose();
                risultatoRicercaSuggerimenti.removeAll();
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }
/**
 * Mostra una serie di opzioni per un libro, tra cui valutazioni e suggerimenti.
 * 
 * @param libro Il libro per il quale si vogliono visualizzare le opzioni.
 */
private void Opzioni(Libro libro){  
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.decode("#f0f0f0"));
    GridBagConstraints c=new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 10, 10);
    JButton valutazioni=new JButton("<html><b>Valutazioni<b></html>");
    JButton consigli=new JButton("<html><b>Consigli<b></html>");
    JButton cancel=new JButton("<html><b>Annulla<b></html>");
    c.gridx=0;
    c.gridy=0;
    c.gridwidth=1;
    panel.add(valutazioni,c);
    c.gridy=2;
    panel.add(consigli,c);
    c.gridy=4;
    panel.add(cancel,c);

    JOptionPane provaPane = new JOptionPane(
            panel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{},
            null);
    JDialog dialog=provaPane.createDialog(frame,"Opzioni");
    valutazioni.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            mostraInserimentoValutazioni(libro);
            dialog.dispose();
        }
    });
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            dialog.dispose();
        }
    });
    
    consigli.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            Suggerimenti(libro);
            dialog.dispose();
        }
    });
    dialog.pack();
    dialog.setVisible(true);
}      
/**
 * Aggiunge i componenti grafici per la ricerca (bottone Titolo, Autore, Autore e Anno e campo di testo) al pannello specificato.
 * I componenti sono aggiunti solo alla prima chiamata.
 * 
 * @param panel Il pannello a cui aggiungere i componenti grafici.
 */
    private void AggiungiBottoniECampoRicerca(JPanel panel){
        if(k==0){
            searchByTitleButton = new JButton("Titolo");
            searchByAuthorButton = new JButton("Autore");
            searchByAuthorAndYearButton = new JButton("Autore e Anno");
            searchField = new JTextField(20);
            labelRicerca=new JLabel("Ricerca:");
            k++;
        }
        c.insets=new Insets(20, 20,20,20);
        searchField.setText("");
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=1;
        panel.add(labelRicerca,c);
        
        c.gridx = 2;
        panel.add(searchField, c);
        
        c.gridwidth = 1;
        c.gridy = 4;
        c.gridx = 0;        
        panel.add(searchByTitleButton, c);

        c.gridx = 2;
        panel.add(searchByAuthorButton, c);
        c.gridx = 3;
        panel.add(searchByAuthorAndYearButton, c);
        
    }
    
}