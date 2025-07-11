//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package bookrecommender;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import parametri.*;
/**
 * Classe HomeMainFrame, rappresenta la home del progetto, nella quale un utente può effettuare varie operazioni.
 */
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
 * @param proxy utilizzato per la connessione al Server
 * @param cf indica il codice fiscale della persona che ha effettuato l'accesso, altrimenti indica "Ospite" se si è entrati come ospite.
 */
    @SuppressWarnings("Convert2Lambda")
    public HomeMainFrame(Proxy proxy,String cf) {
        this.proxy = proxy;
        frame = new JFrame();

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setBackground(new Color(41, 128, 185)); 
        topPanel.setOpaque(true);

        bottoneHome = new JButton("Logout"); 
        bottoneLibreria = new JButton("Libreria");
        bottoneProfilo=new JButton("Profilo");
        customizeButton(bottoneHome);
        customizeButton(bottoneLibreria);
        customizeButton(bottoneProfilo);

        bottoneHome.setBackground(new Color(41, 128, 185)); 
        bottoneLibreria.setBackground(new Color(41, 128, 185));
        bottoneProfilo.setBackground(new Color(41, 128, 185));
        bottoneHome.setForeground(Color.WHITE);
        bottoneLibreria.setForeground(Color.WHITE);
        bottoneProfilo.setForeground(Color.WHITE);

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
                bottoneHome.setBackground(new Color(93, 173, 226)); 
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

        bottoneLibreria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cf.equals("Ospite")) {
                    JOptionPane.showMessageDialog(null, "Impossibile effettuare questa operazione come ospite");
                }else{
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
                    Color bgMain = new Color(245, 250, 255);
                    Color bgPanel = new Color(237, 245, 252);
                    Color blue = new Color(41, 128, 185);
                    Color blueLabel = new Color(33, 97, 140);
                    Color borderBtn = new Color(120, 160, 200); 
                    Font titleFont = new Font("Arial", Font.BOLD, 20);
                    Font labelFont = new Font("Arial", Font.BOLD, 14);
                    Font valueFont = new Font("Arial", Font.PLAIN, 14);

                    JPanel profiloPanel = new JPanel(new BorderLayout());
                    profiloPanel.setBackground(bgMain);
                    profiloPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

                    JPanel panelSinistra = new JPanel();
                    panelSinistra.setLayout(new BoxLayout(panelSinistra, BoxLayout.Y_AXIS));
                    panelSinistra.setBackground(bgPanel);
                    panelSinistra.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220,230,240), 1, true),
                        BorderFactory.createEmptyBorder(20, 10, 20, 10)
                    ));

                    Dimension buttonSize = new Dimension(160, 36);

                    JButton bottoneInformazioni = new JButton("Informazioni");
                    JButton bottoneCambioUsername = new JButton("Cambia Username");
                    JButton bottoneCambioPassword = new JButton("Cambia Password");
                    JButton bottoneCambioEmail = new JButton("Cambia Email");

                    JButton[] bottoni = {
                        bottoneInformazioni,
                        bottoneCambioUsername,
                        bottoneCambioPassword,
                        bottoneCambioEmail
                    };
                    for (JButton b : bottoni) {
                        b.setFont(labelFont);
                        b.setBackground(Color.WHITE);
                        b.setForeground(blue);
                        b.setFocusPainted(false);
                        b.setBorder(BorderFactory.createLineBorder(borderBtn, 2, true)); // bordo diverso
                        b.setMaximumSize(buttonSize);
                        b.setPreferredSize(buttonSize);
                        b.setMinimumSize(buttonSize);
                        b.setAlignmentX(0.5f);
                        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        b.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                b.setBackground(new Color(220, 240, 255));
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                b.setBackground(Color.WHITE);
                            }
                        });
                        panelSinistra.add(b);
                        panelSinistra.add(Box.createVerticalStrut(14));
                    }

                    JPanel panelDestra = new JPanel();
                    panelDestra.setLayout(new BoxLayout(panelDestra, BoxLayout.Y_AXIS));
                    panelDestra.setBackground(bgPanel);
                    panelDestra.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220,230,240), 1, true),
                        BorderFactory.createEmptyBorder(24, 32, 24, 32)
                    ));

                    Utente u = proxy.getUtenteDaCF(cf);

                    JLabel titolo = new JLabel("Profilo utente");
                    titolo.setFont(titleFont);
                    titolo.setForeground(blue);
                    titolo.setAlignmentX(0.0f);
                    panelDestra.add(titolo);
                    panelDestra.add(Box.createVerticalStrut(16));
                    JSeparator sep = new JSeparator();
                    sep.setForeground(new Color(200, 210, 220));
                    sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                    panelDestra.add(sep);
                    panelDestra.add(Box.createVerticalStrut(16));

                    String[][] dati = {
                        {"Nome:", u.getNome()},
                        {"Cognome:", u.getCognome()},
                        {"Codice Fiscale:", u.getCF()},
                        {"Username:", u.getUsername()},
                        {"Email:", u.getEmail()}
                    };
                    for (String[] r : dati) {
                        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                        row.setOpaque(false);
                        JLabel l1 = new JLabel(r[0]);
                        l1.setFont(labelFont);
                        l1.setForeground(blueLabel);
                        JLabel l2 = new JLabel(" " + r[1]);
                        l2.setFont(valueFont);
                        l2.setForeground(Color.DARK_GRAY);
                        row.add(l1); row.add(l2);
                        panelDestra.add(row);
                        panelDestra.add(Box.createVerticalStrut(8));
                    }

                    profiloPanel.add(panelSinistra, BorderLayout.WEST);
                    profiloPanel.add(panelDestra, BorderLayout.CENTER);

                    JDialog dialog = new JDialog(frame, "Profilo", false);
                    dialog.setSize(620, 340);
                    dialog.getContentPane().setBackground(bgMain);
                    dialog.setLayout(new BorderLayout());
                    dialog.setLocationRelativeTo(null);
                    dialog.add(profiloPanel, BorderLayout.CENTER);
                    dialog.setResizable(false);
                    dialog.setVisible(true);

                    // --- STILE PER I PANNELLI SECONDARI ---
                    bottoneInformazioni.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JPanel informazioniPanel = new JPanel(new BorderLayout());
                            informazioniPanel.setBackground(bgMain);
                            informazioniPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

                            JPanel panelSinistra2 = new JPanel();
                            panelSinistra2.setLayout(new BoxLayout(panelSinistra2, BoxLayout.Y_AXIS));
                            panelSinistra2.setBackground(bgPanel);
                            panelSinistra2.setBorder(panelSinistra.getBorder());
                            for (JButton b : bottoni) {
                                b.setFont(new Font("Arial", Font.BOLD, 13));
                                b.setBackground(Color.WHITE);
                                b.setForeground(blue);
                                b.setFocusPainted(false);
                                b.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                                b.setMaximumSize(buttonSize);
                                b.setPreferredSize(buttonSize);
                                b.setMinimumSize(buttonSize);
                                b.setAlignmentX(0.5f);
                                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                b.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                        b.setBackground(new Color(220, 240, 255));
                                    }
                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                        b.setBackground(Color.WHITE);
                                    }
                                });
                                panelSinistra2.add(b);
                                panelSinistra2.add(Box.createVerticalStrut(14));
                            }

                            JPanel panelDestra2 = new JPanel();
                            panelDestra2.setLayout(new BoxLayout(panelDestra2, BoxLayout.Y_AXIS));
                            panelDestra2.setBackground(bgPanel);
                            panelDestra2.setBorder(panelDestra.getBorder());

                            JLabel titolo = new JLabel("Profilo utente");
                            titolo.setFont(new Font("Arial", Font.BOLD, 20));
                            titolo.setForeground(blue);
                            titolo.setAlignmentX(0.0f);
                            panelDestra2.add(titolo);
                            panelDestra2.add(Box.createVerticalStrut(16));
                            JSeparator sep = new JSeparator();
                            sep.setForeground(new Color(200, 210, 220));
                            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                            panelDestra2.add(sep);
                            panelDestra2.add(Box.createVerticalStrut(16));

                            Utente u = proxy.getUtenteDaCF(cf);
                            String[][] dati = {
                                {"Nome:", u.getNome()},
                                {"Cognome:", u.getCognome()},
                                {"Codice Fiscale:", u.getCF()},
                                {"Username:", u.getUsername()},
                                {"Email:", u.getEmail()}
                            };
                            for (String[] r : dati) {
                                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                                row.setOpaque(false);
                                JLabel l1 = new JLabel(r[0]);
                                l1.setFont(new Font("Arial", Font.BOLD, 15));
                                l1.setForeground(blueLabel);
                                JLabel l2 = new JLabel(" " + r[1]);
                                l2.setFont(new Font("Arial", Font.PLAIN, 15));
                                l2.setForeground(Color.DARK_GRAY);
                                row.add(l1); row.add(l2);
                                panelDestra2.add(row);
                                panelDestra2.add(Box.createVerticalStrut(10));
                            }

                            informazioniPanel.add(panelSinistra2, BorderLayout.WEST);
                            informazioniPanel.add(panelDestra2, BorderLayout.CENTER);

                            dialog.setContentPane(informazioniPanel);
                            dialog.getContentPane().revalidate();
                            dialog.getContentPane().repaint();
                        }
                    });
                    bottoneCambioPassword.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.getContentPane().removeAll();

                            JPanel cambioPasswordPanel = new JPanel(new BorderLayout());
                            cambioPasswordPanel.setBackground(bgMain);
                            cambioPasswordPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

                            JPanel panelSinistra2 = new JPanel();
                            panelSinistra2.setLayout(new BoxLayout(panelSinistra2, BoxLayout.Y_AXIS));
                            panelSinistra2.setBackground(bgPanel);
                            panelSinistra2.setBorder(panelSinistra.getBorder());
                            for (JButton b : bottoni) {
                                b.setFont(new Font("Arial", Font.BOLD, 13));
                                b.setBackground(Color.WHITE);
                                b.setForeground(blue);
                                b.setFocusPainted(false);
                                b.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                                b.setMaximumSize(buttonSize);
                                b.setPreferredSize(buttonSize);
                                b.setMinimumSize(buttonSize);
                                b.setAlignmentX(0.5f);
                                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                b.addMouseListener(new java.awt.event.MouseAdapter() {
                                    @Override
                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                        b.setBackground(new Color(220, 240, 255));
                                    }
                                    @Override
                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                        b.setBackground(Color.WHITE);
                                    }
                                });
                                panelSinistra2.add(b);
                                panelSinistra2.add(Box.createVerticalStrut(14));
                            }

                            JPanel panelDestra2 = new JPanel();
                            panelDestra2.setLayout(new BoxLayout(panelDestra2, BoxLayout.Y_AXIS));
                            panelDestra2.setBackground(bgPanel);
                            panelDestra2.setBorder(panelDestra.getBorder());

                            JLabel titolo = new JLabel("Cambia Password");
                            titolo.setFont(new Font("Arial", Font.BOLD, 18));
                            titolo.setForeground(blue);
                            titolo.setAlignmentX(0.0f);
                            panelDestra2.add(titolo);
                            panelDestra2.add(Box.createVerticalStrut(16));
                            JSeparator sep = new JSeparator();
                            sep.setForeground(new Color(200, 210, 220));
                            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                            panelDestra2.add(sep);
                            panelDestra2.add(Box.createVerticalStrut(16));

                            JLabel vecchiaPasswordLabel = new JLabel("Vecchia password:");
                            vecchiaPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
                            vecchiaPasswordLabel.setForeground(blueLabel);
                            vecchiaPasswordLabel.setAlignmentX(0.0f);
                            panelDestra2.add(vecchiaPasswordLabel);
                            panelDestra2.add(Box.createVerticalStrut(5));
                            JPasswordField vecchiaPasswordField = new JPasswordField(20);
                            vecchiaPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
                            vecchiaPasswordField.setMaximumSize(new Dimension(250, 28));
                            vecchiaPasswordField.setAlignmentX(0.0f);
                            panelDestra2.add(vecchiaPasswordField);
                            panelDestra2.add(Box.createVerticalStrut(10));

                            JLabel nuovaPasswordLabel = new JLabel("Nuova password:");
                            nuovaPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
                            nuovaPasswordLabel.setForeground(blueLabel);
                            nuovaPasswordLabel.setAlignmentX(0.0f);
                            panelDestra2.add(nuovaPasswordLabel);
                            panelDestra2.add(Box.createVerticalStrut(5));
                            JPasswordField nuovaPasswordField = new JPasswordField(20);
                            nuovaPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
                            nuovaPasswordField.setMaximumSize(new Dimension(250, 28));
                            nuovaPasswordField.setAlignmentX(0.0f);
                            panelDestra2.add(nuovaPasswordField);
                            panelDestra2.add(Box.createVerticalStrut(18));

                            JButton applicaButton = new JButton("Applica");
                            applicaButton.setFont(new Font("Arial", Font.BOLD, 13));
                            applicaButton.setBackground(blue);
                            applicaButton.setForeground(blueLabel); // colore testo come "Informazioni"
                            applicaButton.setFocusPainted(false);
                            applicaButton.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                            applicaButton.setMaximumSize(new Dimension(120, 32));
                            applicaButton.setAlignmentX(0.0f);
                            applicaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            applicaButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    applicaButton.setBackground(new Color(93, 173, 226));
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    applicaButton.setBackground(blue);
                                }
                            });
                            panelDestra2.add(applicaButton);

                            applicaButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String passwordVecchia = new String(vecchiaPasswordField.getPassword());
                                    String passwordNuova = new String(nuovaPasswordField.getPassword());
                                    boolean risultato = proxy.cambiaPassword(cf, passwordVecchia, passwordNuova);
                                    if (risultato) {
                                        JOptionPane.showMessageDialog(dialog, "Aggiornamento password avvenuto con successo");
                                    } else {
                                        JOptionPane.showMessageDialog(dialog, "Password vecchia errata, Riprovare");
                                    }
                                }
                            });
                            cambioPasswordPanel.add(panelSinistra2, BorderLayout.WEST);
                            cambioPasswordPanel.add(panelDestra2, BorderLayout.CENTER);

                            dialog.setContentPane(cambioPasswordPanel);
                            dialog.revalidate();
                            dialog.repaint();
                        }
                    });

                    bottoneCambioUsername.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.getContentPane().removeAll();

                            JPanel cambioUsernamePanel = new JPanel(new BorderLayout());
                            cambioUsernamePanel.setBackground(bgMain);
                            cambioUsernamePanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

                            JPanel panelSinistra2 = new JPanel();
                            panelSinistra2.setLayout(new BoxLayout(panelSinistra2, BoxLayout.Y_AXIS));
                            panelSinistra2.setBackground(bgPanel);
                            panelSinistra2.setBorder(panelSinistra.getBorder());
                            for (JButton b : bottoni) {
                                b.setFont(new Font("Arial", Font.BOLD, 13));
                                b.setBackground(Color.WHITE);
                                b.setForeground(blue);
                                b.setFocusPainted(false);
                                b.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                                b.setMaximumSize(buttonSize);
                                b.setPreferredSize(buttonSize);
                                b.setMinimumSize(buttonSize);
                                b.setAlignmentX(0.5f);
                                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                b.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                        b.setBackground(new Color(220, 240, 255));
                                    }
                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                        b.setBackground(Color.WHITE);
                                    }
                                });
                                panelSinistra2.add(b);
                                panelSinistra2.add(Box.createVerticalStrut(14));
                            }

                            JPanel panelDestra2 = new JPanel();
                            panelDestra2.setLayout(new BoxLayout(panelDestra2, BoxLayout.Y_AXIS));
                            panelDestra2.setBackground(bgPanel);
                            panelDestra2.setBorder(panelDestra.getBorder());

                            JLabel titolo = new JLabel("Cambia Username");
                            titolo.setFont(new Font("Arial", Font.BOLD, 18));
                            titolo.setForeground(blue);
                            titolo.setAlignmentX(0.0f);
                            panelDestra2.add(titolo);
                            panelDestra2.add(Box.createVerticalStrut(16));
                            JSeparator sep = new JSeparator();
                            sep.setForeground(new Color(200, 210, 220));
                            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                            panelDestra2.add(sep);
                            panelDestra2.add(Box.createVerticalStrut(16));

                            JLabel nuovoUsernameLabel = new JLabel("Nuovo username:");
                            nuovoUsernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                            nuovoUsernameLabel.setForeground(blueLabel);
                            nuovoUsernameLabel.setAlignmentX(0.0f);
                            panelDestra2.add(nuovoUsernameLabel);
                            panelDestra2.add(Box.createVerticalStrut(5));
                            JTextField nuovoUsernameField = new JTextField(20);
                            nuovoUsernameField.setFont(new Font("Arial", Font.PLAIN, 14));
                            nuovoUsernameField.setMaximumSize(new Dimension(250, 28));
                            nuovoUsernameField.setAlignmentX(0.0f);
                            panelDestra2.add(nuovoUsernameField);
                            panelDestra2.add(Box.createVerticalStrut(18));

                            JButton applicaButton = new JButton("Applica");
                            applicaButton.setFont(new Font("Arial", Font.BOLD, 13));
                            applicaButton.setBackground(blue);
                            applicaButton.setForeground(blueLabel); // colore testo come "Informazioni"
                            applicaButton.setFocusPainted(false);
                            applicaButton.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                            applicaButton.setMaximumSize(new Dimension(120, 32));
                            applicaButton.setAlignmentX(0.0f);
                            applicaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            applicaButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    applicaButton.setBackground(new Color(93, 173, 226));
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    applicaButton.setBackground(blue);
                                }
                            });
                            panelDestra2.add(applicaButton);

                            applicaButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String usernameNuovo = nuovoUsernameField.getText();
                                    boolean risultato = proxy.cambiaUsername(cf, usernameNuovo);
                                    if (risultato) {
                                        JOptionPane.showMessageDialog(dialog, "Aggiornamento username avvenuto con successo");
                                    } else {
                                        JOptionPane.showMessageDialog(dialog, "Username già in uso, Riprovare");
                                    }
                                }
                            });

                            cambioUsernamePanel.add(panelSinistra2, BorderLayout.WEST);
                            cambioUsernamePanel.add(panelDestra2, BorderLayout.CENTER);

                            dialog.setContentPane(cambioUsernamePanel);
                            dialog.revalidate();
                            dialog.repaint();
                        }
                    });
                        


                    bottoneCambioEmail.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.getContentPane().removeAll();

                            JPanel cambioEmailPanel = new JPanel(new BorderLayout());
                            cambioEmailPanel.setBackground(bgMain);
                            cambioEmailPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

                            JPanel panelSinistra2 = new JPanel();
                            panelSinistra2.setLayout(new BoxLayout(panelSinistra2, BoxLayout.Y_AXIS));
                            panelSinistra2.setBackground(bgPanel);
                            panelSinistra2.setBorder(panelSinistra.getBorder());
                            for (JButton b : bottoni) {
                                b.setFont(new Font("Arial", Font.BOLD, 13));
                                b.setBackground(Color.WHITE);
                                b.setForeground(blue);
                                b.setFocusPainted(false);
                                b.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                                b.setMaximumSize(buttonSize);
                                b.setPreferredSize(buttonSize);
                                b.setMinimumSize(buttonSize);
                                b.setAlignmentX(0.5f);
                                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                b.addMouseListener(new java.awt.event.MouseAdapter() {
                                    @Override
                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                        b.setBackground(new Color(220, 240, 255));
                                    }
                                    @Override
                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                        b.setBackground(Color.WHITE);
                                    }
                                });
                                panelSinistra2.add(b);
                                panelSinistra2.add(Box.createVerticalStrut(14));
                            }

                            JPanel panelDestra2 = new JPanel();
                            panelDestra2.setLayout(new BoxLayout(panelDestra2, BoxLayout.Y_AXIS));
                            panelDestra2.setBackground(bgPanel);
                            panelDestra2.setBorder(panelDestra.getBorder());

                            JLabel titolo = new JLabel("Cambia Email");
                            titolo.setFont(new Font("Arial", Font.BOLD, 18));
                            titolo.setForeground(blue);
                            titolo.setAlignmentX(0.0f);
                            panelDestra2.add(titolo);
                            panelDestra2.add(Box.createVerticalStrut(16));
                            JSeparator sep = new JSeparator();
                            sep.setForeground(new Color(200, 210, 220));
                            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                            panelDestra2.add(sep);
                            panelDestra2.add(Box.createVerticalStrut(16));

                            JLabel nuovaEmailLabel = new JLabel("Nuova email:");
                            nuovaEmailLabel.setFont(new Font("Arial", Font.BOLD, 14));
                            nuovaEmailLabel.setForeground(blueLabel);
                            nuovaEmailLabel.setAlignmentX(0.0f);
                            panelDestra2.add(nuovaEmailLabel);
                            panelDestra2.add(Box.createVerticalStrut(5));
                            JTextField nuovaEmailField = new JTextField(20);
                            nuovaEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
                            nuovaEmailField.setMaximumSize(new Dimension(250, 28));
                            nuovaEmailField.setAlignmentX(0.0f);
                            panelDestra2.add(nuovaEmailField);
                            panelDestra2.add(Box.createVerticalStrut(18));

                            JButton applicaButton = new JButton("Applica");
                            applicaButton.setFont(new Font("Arial", Font.BOLD, 13));
                            applicaButton.setBackground(blue);
                            applicaButton.setForeground(blueLabel); // colore testo come "Informazioni"
                            applicaButton.setFocusPainted(false);
                            applicaButton.setBorder(BorderFactory.createLineBorder(blue, 1, true));
                            applicaButton.setMaximumSize(new Dimension(120, 32));
                            applicaButton.setAlignmentX(0.0f);
                            applicaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            applicaButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    applicaButton.setBackground(new Color(93, 173, 226));
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    applicaButton.setBackground(blue);
                                }
                            });
                            panelDestra2.add(applicaButton);

                            applicaButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String emailNuova = nuovaEmailField.getText();
                                    boolean risultato = proxy.cambiaEmail(cf, emailNuova);
                                    if(emailNuova.contains("@")){
                                        if (risultato) {
                                            JOptionPane.showMessageDialog(dialog, "Aggiornamento email avvenuto con successo");
                                        } else {
                                            JOptionPane.showMessageDialog(dialog, "Email già in uso, Riprovare");
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(dialog, "Formato email errato");
                                    }
                                }
                            });

                            cambioEmailPanel.add(panelSinistra2, BorderLayout.WEST);
                            cambioEmailPanel.add(panelDestra2, BorderLayout.CENTER);

                            dialog.setContentPane(cambioEmailPanel);
                            dialog.revalidate();
                            dialog.repaint();
                        }
                    });
                }
            }
        });
    
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(236, 240, 241));

        JLabel titoloLabel = new JLabel("<html><div style='text-align: center;'><b>Inserisci un Titolo di un libro, un Autore, oppure Autore ed anno<br>e clicca il pulsante corrispondente per effettuare la ricerca</b></div></html>");
        titoloLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titoloLabel.setForeground(new Color(70, 70, 70));
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        titoloLabel.setOpaque(true);
        titoloLabel.setBackground(new Color(236, 240, 241));

        centerPanel.add(titoloLabel, BorderLayout.NORTH);

        searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
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

        resultsPanel = new JPanel();
        resultsPanel.setBackground(new Color(236, 240, 241));
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        risultatoScrollPane = new JScrollPane(resultsPanel);
        risultatoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        risultatoScrollPane.setPreferredSize(new Dimension(300, 200));
        risultatoScrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

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
 * Imposta il titolo, le dimensioni, l'icona e le dimensioni minime, l'operazione di chiusura predefinita
 * chiude la connessione al server in caso di chiusura del frame.
 */
    public void initialize() {
        frame.setTitle("Home");
        File fileIcona = new File(".");
        String pathIcona= fileIcona.getAbsolutePath().substring(0,fileIcona.getAbsolutePath().length()-1);
        pathIcona = pathIcona+"src\\immagini\\icona.png";
        Image icona = (new ImageIcon(pathIcona)).getImage();
        frame.setIconImage(icona);
        frame.setSize(350, 500);
        frame.setMinimumSize(new Dimension(300, 400)); 
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

        JLabel titoloLabel = new JLabel("<html><span style='font-size:13px'><b>Dettagli libro</b></span></html>");
        titoloLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titoloLabel.setForeground(new Color(41, 128, 185));
        c.gridx = 0;
        c.gridy = row++;
        c.gridwidth = 3;
        contentPanel.add(titoloLabel, c);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(189, 195, 199));
        c.gridy = row++;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(sep, c);

        JLabel infoLabel = new JLabel(infoLibro);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(33, 97, 140));
        c.gridy = row++;
        c.gridx = 0;
        c.gridwidth = 3;
        contentPanel.add(infoLabel, c);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(189, 195, 199));
        c.gridy = row++;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(sep2, c);

        JLabel panelValutazioni = new JLabel(infoValutazioni);
        panelValutazioni.setFont(new Font("Arial", Font.PLAIN, 12));
        panelValutazioni.setForeground(new Color(21, 67, 96));
        c.gridy = row++;
        c.gridx = 0;
        c.gridwidth = 3;
        contentPanel.add(panelValutazioni, c);

        JSeparator sep3 = new JSeparator();
        sep3.setForeground(new Color(189, 195, 199));
        c.gridy = row++;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(sep3, c);

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
