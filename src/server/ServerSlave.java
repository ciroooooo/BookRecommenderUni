package server;

import parametri.Utente;
import parametri.Libro;
import parametri.Autore;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;

import org.postgresql.*;
import java.util.*;
import java.time.LocalDate;
import parametri.Librerie;
import parametri.ValutazioniLibro;

public class ServerSlave extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Connection con;
    private Statement stmt;
    public ServerSlave(Socket socket){
        this.socket = socket;
        try{
            con = DriverManager.getConnection(
            "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:5432/postgres?user=postgres.qgavtjdnntjotuevqsds&password=7apU3CLd6rDrXCHM");
            this.stmt = con.createStatement();
            System.out.println("Connessione ad database avvenuta con successo!");
        }catch(SQLException sq){
            sq.printStackTrace();
        }
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
            this.start();
        } catch (IOException e) {
        }
    }
    public void run(){
        try {
            while(!socket.isClosed()){
                String operazione = (String)in.readObject();
                System.out.println("Operazione: "+operazione);
                if(operazione.equals("Fine")){
                    System.out.println("Chiusura connessione in corso...");
                    socket.close();
                    con.close();
                }
                else if(operazione.equals("GetListaUsernameUtente")){
                    ResultSet risultatoQuery = stmt.executeQuery("SELECT username FROM utente");
                    ArrayList<String> listaUsername = new ArrayList<>();
                    while(risultatoQuery.next()){
                        listaUsername.add(risultatoQuery.getString("username"));
                    }
                    out.writeObject(listaUsername);
                    out.flush();
                }
                else if(operazione.equals("GetListaPasswordUtente")){
                    ResultSet risultatoQuery = stmt.executeQuery("SELECT password FROM utente");
                    ArrayList<String> listaPassword = new ArrayList<>();
                    while(risultatoQuery.next()){
                        listaPassword.add(risultatoQuery.getString("password"));
                    }   
                    out.writeObject(listaPassword);
                    out.flush();
                }
                else if(operazione.equals("GetCF")){
                    String username = (String)in.readObject();
                    String password = (String)in.readObject();
                    String sqlCommand = "SELECT cf FROM utente WHERE username=? and password=?";
                    PreparedStatement ps = con.prepareStatement(sqlCommand);
                    ps.setString(1,username);
                    ps.setString(2,password);
                    ResultSet risultatoQuery = ps.executeQuery();
                    risultatoQuery.next();
                    String cf = risultatoQuery.getString(1);
                    out.writeObject(cf);
                }
                else if(operazione.equals("AggiungiAutore")){
                    String nomeAutore = (String)in.readObject();
                    if(nomeAutore.length()>99){
                        nomeAutore = nomeAutore.substring(0,98);
                    }
                    String sqlCommand = "SELECT nome FROM autore where nome=?";
                    PreparedStatement ps = con.prepareStatement(sqlCommand);
                    ps.setString(1,nomeAutore);
                    ResultSet risultatoQuery = ps.executeQuery();
                    if(!(risultatoQuery.next())){
                        String comandoInsert = "INSERT INTO autore(nome) VALUES(?)";
                        PreparedStatement psI = con.prepareStatement(comandoInsert);
                        psI.setString(1,nomeAutore);
                        psI.executeUpdate();
                    }
                }
                else if(operazione.equals("AggiungiLibro")){
                    String titolo = (String)in.readObject();
                    String descrizione = (String)in.readObject();
                    String categoria = (String)in.readObject();
                    String editore = (String)in.readObject();
                    LocalDate data= (LocalDate)in.readObject();
                    Double prezzo = (Double)in.readObject();
                    String autore = (String)in.readObject();
                    Date dataPub = Date.valueOf(data);
                    if(autore.length()>99){
                        autore = autore.substring(0,98);
                    }
                    String comandoQuery = "SELECT idautore FROM autore WHERE nome=?";
                    PreparedStatement psQ = con.prepareStatement(comandoQuery);
                    psQ.setString(1,autore);
                    ResultSet risultatoQuery = psQ.executeQuery();
                    risultatoQuery.next();
                    int fk = risultatoQuery.getInt("idautore");
                    String comandoInsert = "INSERT INTO libro(titolo,descrizione,categoria,editore,datapubblicazione,prezzo,idautore) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement ps = con.prepareStatement(comandoInsert);
                    ps.setString(1,titolo);
                    ps.setString(2,descrizione);
                    ps.setString(3,categoria);
                    ps.setString(4,editore);
                    ps.setDate(5,dataPub);
                    ps.setDouble(6,prezzo);
                    ps.setInt(7,fk);
                    ps.executeUpdate();
                }
                else if(operazione.equals("AggiungiUtente")){
                    ResultSet risultatoQuery = stmt.executeQuery("SELECT * FROM Utente");
                    ArrayList<Utente> alUtente = new ArrayList<>();
                    while(risultatoQuery.next()){
                        alUtente.add(new Utente(risultatoQuery.getString("nome"), risultatoQuery.getString("cognome"),risultatoQuery.getString("cf"), risultatoQuery.getString("email"), risultatoQuery.getString("username"), risultatoQuery.getString("password")));
                    }
                    out.writeObject(alUtente);
                    out.flush();
                    String aggiungo = (String)in.readObject();
                    if(aggiungo.equals("Inserisci")){
                        Utente u = (Utente)in.readObject();
                        String query = "INSERT INTO utente(cf,nome,cognome,email,username,password) VALUES(?,?,?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1,u.getCF());
                        ps.setString(2,u.getNome());
                        ps.setString(3,u.getCognome());
                        ps.setString(4,u.getEmail());
                        ps.setString(5,u.getUsername());
                        ps.setString(6,u.getPassword());
                        ps.executeUpdate();
                    }else if(aggiungo.equals("Fine")){
                        System.out.println("Impossibile inserire l'utente");
                    }
                }
                else if(operazione.equals("CercaPerTitolo")){
                    String titolo= ((String)in.readObject()).toLowerCase();
                    ArrayList<Libro> listaLibri = new ArrayList<>();
                    String comandoQuery = "SELECT titolo,autore.nome, descrizione, categoria, editore, datapubblicazione, prezzo FROM libro JOIN autore on(Libro.idautore = Autore.idautore) WHERE LOWER(titolo) LIKE ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1, "%"+titolo+"%");
                    ResultSet risultatoQuery = ps.executeQuery();
                    while(risultatoQuery.next()){
                        listaLibri.add(new Libro(risultatoQuery.getString(1),new Autore(risultatoQuery.getString(2)),risultatoQuery.getString(3),risultatoQuery.getString(4),risultatoQuery.getString(5),risultatoQuery.getDate(6).toLocalDate(),risultatoQuery.getDouble(7)));
                    }
                    out.writeObject(listaLibri);
                    out.flush();
                }
                else if(operazione.equals("CercaPerAutore")){
                    ArrayList<Libro> alLibro = new ArrayList();
                    Autore autore = (Autore)in.readObject();
                    String nomeAutore = autore.getNome().toLowerCase();
                    String comandoQuery = "SELECT titolo,autore.nome, descrizione, categoria, editore, datapubblicazione, prezzo FROM libro JOIN autore on(Libro.idautore = Autore.idautore) WHERE LOWER(autore.nome) LIKE ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,"%"+nomeAutore+"%");
                    ResultSet risultatoQuery = ps.executeQuery();
                    while(risultatoQuery.next()){
                        alLibro.add(new Libro(risultatoQuery.getString(1),new Autore(risultatoQuery.getString(2)),risultatoQuery.getString(3),risultatoQuery.getString(4),risultatoQuery.getString(5),risultatoQuery.getDate(6).toLocalDate(),risultatoQuery.getDouble(7)));
                    }
                    out.writeObject(alLibro);
                    out.flush();    
                }
                else if(operazione.equals("CercaPerAutore&Anno")){
                    ArrayList<Libro> alLibro = new ArrayList();
                    Autore autore = (Autore)in.readObject();
                    int anno = (int)in.readObject();
                    String nomeAutore = autore.getNome().toLowerCase();
                    String comandoQuery = "SELECT titolo,autore.nome, descrizione, categoria, editore, datapubblicazione, prezzo FROM libro JOIN autore on(Libro.idautore = Autore.idautore) WHERE LOWER(autore.nome) LIKE ? and EXTRACT(YEAR FROM datapubblicazione) = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,"%"+nomeAutore+"%");
                    ps.setInt(2,anno);
                    ResultSet risultatoQuery = ps.executeQuery();
                    while(risultatoQuery.next()){
                        alLibro.add(new Libro(risultatoQuery.getString(1),new Autore(risultatoQuery.getString(2)),risultatoQuery.getString(3),risultatoQuery.getString(4),risultatoQuery.getString(5),risultatoQuery.getDate(6).toLocalDate(),risultatoQuery.getDouble(7)));
                    }
                    out.writeObject(alLibro);
                    out.flush();
                }
                else if(operazione.equals("GetNomiLibreriaDaUtente")){
                    String codiceFiscale = (String)in.readObject();
                    String comandoQuery = "SELECT nome FROM libreria WHERE cf = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,codiceFiscale);
                    ResultSet risultatoQuery = ps.executeQuery();
                    ArrayList<String> alNomi = new ArrayList<>();
                    while(risultatoQuery.next()){
                        alNomi.add(risultatoQuery.getString(1));
                    }
                    out.writeObject(alNomi);
                    out.flush();
                }
                else if(operazione.equals("GetUtenteDaCF")){
                    String codiceFiscale = (String)in.readObject();
                    String comandoQuery = "SELECT nome, cognome, cf, email, username, password FROM utente WHERE cf = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,codiceFiscale);
                    ResultSet risultatoQuery = ps.executeQuery();
                    Utente utente = null;
                    while(risultatoQuery.next()){
                        utente= new Utente(risultatoQuery.getString(1), risultatoQuery.getString(2), risultatoQuery.getString(3), risultatoQuery.getString(4), risultatoQuery.getString(5), risultatoQuery.getString(6));
                    }
                    out.writeObject(utente);
                    out.flush();
                }
                else if(operazione.equals("AggiungiLibreria")){
                    Librerie libreria = (Librerie)in.readObject();
                    String nomeLibreria = libreria.getNome();
                    String cf = libreria.getUtente().getCF();
                    String comandoQuery = "INSERT INTO Libreria(nome,cf) VALUES (?,?)";
                    PreparedStatement ps = con.prepareStatement(comandoQuery,Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, nomeLibreria);
                    ps.setString(2,cf);
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    int chiaveLibreria = rs.getInt(1);
                    ArrayList<Libro> alLibro = libreria.getAlLibri();
                    comandoQuery = "INSERT INTO libroinlibreria(idlibreria,idlibro) VALUES (?,?)";
                    ps = con.prepareStatement(comandoQuery);
                    String queryGetIdLibro = "SELECT idlibro FROM libro JOIN autore on(libro.idautore = autore.idautore) WHERE titolo = ? AND descrizione = ? and categoria = ? and editore = ? and datapubblicazione = ? and prezzo = ? and autore.nome = ?";
                    PreparedStatement psQueryLibro = con.prepareStatement(queryGetIdLibro);
                    for(int i=0;i<alLibro.size();i++){
                        ps.setInt(1,chiaveLibreria);
                        psQueryLibro.setString(1,alLibro.get(i).getTitolo());
                        psQueryLibro.setString(2,alLibro.get(i).getDescrizione());
                        psQueryLibro.setString(3,alLibro.get(i).getCategoria());
                        psQueryLibro.setString(4,alLibro.get(i).getEditore());
                        psQueryLibro.setDate(5,Date.valueOf(alLibro.get(i).getDataPubblicazione()));
                        psQueryLibro.setDouble(6,alLibro.get(i).getPrezzo());
                        psQueryLibro.setString(7,alLibro.get(i).getAutore());
                        ResultSet risultato = psQueryLibro.executeQuery();
                        risultato.next();
                        int chiaveLibro = risultato.getInt(1);
                        ps.setInt(2,chiaveLibro);
                        ps.executeUpdate();
                    }
                }
                else if(operazione.equals("GetLibrerieDaCf")){
                    try {
                        ArrayList<Librerie> librerieUtente = new ArrayList<>();
                        String cf = (String)in.readObject();
                        String comandoQuery = "Select l.nome as nome_libreria, l.idlibreria as idlib, u.*, li.*,a.nome as nome_autore FROM libreria l JOIN utente u on(l.cf = u.cf) JOIN libroinlibreria on(l.idlibreria = libroinlibreria.idlibreria) JOIN libro li on(libroinlibreria.idlibro = li.idlibro) JOIN autore a on(a.idautore = li.idautore) WHERE l.cf = ? ORDER BY idlib asc";
                        PreparedStatement ps = con.prepareStatement(comandoQuery);
                        ps.setString(1, cf);
                        ResultSet risultatoQuery = ps.executeQuery();
                        if(risultatoQuery.next()){
                            String nomeUtente = risultatoQuery.getString("nome");
                            String cognome = risultatoQuery.getString("cognome");
                            String email = risultatoQuery.getString("email");
                            String username = risultatoQuery.getString("username");
                            String password = risultatoQuery.getString("password");
                            Utente u = new Utente(nomeUtente,cognome,cf,email,username,password);
                            String nomeLibreria = risultatoQuery.getString("nome_libreria");
                            int idLibreria = risultatoQuery.getInt("idlib");
                            ArrayList<Libro> libri = new ArrayList();
                            do { 
                                String titolo = risultatoQuery.getString("titolo");
                                String descrizione = risultatoQuery.getString("descrizione");
                                String categoria = risultatoQuery.getString("categoria");
                                String editore = risultatoQuery.getString("editore");
                                LocalDate dataPub = risultatoQuery.getDate("datapubblicazione").toLocalDate();
                                double prezzo = risultatoQuery.getDouble("prezzo");
                                Autore autore = new Autore(risultatoQuery.getString("nome_autore"));
                                Libro l = new Libro(titolo,autore,descrizione,categoria,editore,dataPub,prezzo);
                                if(idLibreria==risultatoQuery.getInt("idlib")){
                                    libri.add(l);
                                }else{
                                    Librerie lib = new Librerie(nomeLibreria,u,libri);
                                    librerieUtente.add(lib);
                                    libri.clear();
                                    idLibreria = risultatoQuery.getInt("idlib");
                                    nomeLibreria = risultatoQuery.getString("nome_libreria");
                                }
                            } while (risultatoQuery.next());     
                            Librerie ultimaLib = new Librerie(nomeLibreria,u,libri);
                            librerieUtente.add(ultimaLib);
                        }
                        out.writeObject(librerieUtente);
                        out.flush();
                    } catch (IOException | ClassNotFoundException e) {
                    }
                }
                else if(operazione.equals("GetLibreriaUtenteDaNome")){
                    Utente u = (Utente)in.readObject();
                    String nomeLibreria = (String)in.readObject();
                    String cf = u.getCF();
                    String comandoQuery = "Select li.*,a.nome as nome_autore FROM libreria l JOIN libroinlibreria ll on (l.idlibreria = ll.idlibreria) JOIN libro li ON (li.idlibro = ll.idlibro) JOIN autore a ON (a.idautore = li.idautore) WHERE l.cf = ? AND l.nome = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,cf);
                    ps.setString(2,nomeLibreria);
                    ResultSet risultatoQuery = ps.executeQuery();
                    ArrayList<Libro> listaLibri = new ArrayList();
                    while(risultatoQuery.next()){
                        String titolo = risultatoQuery.getString("titolo");
                        String descrizione = risultatoQuery.getString("descrizione");
                        String categoria = risultatoQuery.getString("categoria");
                        String editore = risultatoQuery.getString("editore");
                        LocalDate dataPub = risultatoQuery.getDate("datapubblicazione").toLocalDate();
                        double prezzo = risultatoQuery.getDouble("prezzo");
                        Autore autore = new Autore(risultatoQuery.getString("nome_autore")); 
                        Libro l = new Libro(titolo,autore,descrizione,categoria,editore,dataPub,prezzo);
                        listaLibri.add(l);
                    }
                    Librerie libreria = new Librerie(nomeLibreria,u,listaLibri);
                    out.writeObject(libreria);
                    out.flush();
                }   
                else if(operazione.equals("GetIdDelLibro")){
                    Libro l = (Libro)in.readObject();
                    String operazioneQuery = "SELECT idlibro FROM libro JOIN autore on(libro.idautore = autore.idautore) WHERE titolo = ? and descrizione = ? and categoria = ? and editore = ? and datapubblicazione = ? and prezzo = ? and nome = ?";
                    String titolo = l.getTitolo();
                    String descrizione = l.getDescrizione();
                    String categoria = l.getCategoria();
                    String editore = l.getEditore();
                    Date dataPubb = Date.valueOf(l.getDataPubblicazione());
                    double prezzo = l.getPrezzo();
                    String nomeAutore = l.getAutore();
                    PreparedStatement ps = con.prepareStatement(operazioneQuery);
                    ps.setString(1,titolo);
                    ps.setString(2,descrizione);
                    ps.setString(3,categoria);
                    ps.setString(4,editore);
                    ps.setDate(5,dataPubb);
                    ps.setDouble(6, prezzo);
                    ps.setString(7,nomeAutore);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    int idLibro = rs.getInt(1);
                    out.writeObject(idLibro);
                    out.flush();
                }
                else if(operazione.equals("AggiungiValutazione")){
                    ValutazioniLibro valLibro = (ValutazioniLibro)in.readObject();
                    //Controlla se esiste già una valutazione;
                    Utente u = valLibro.getUtente();
                    String cf = u.getCF();
                    int idLibro = (int)in.readObject();
                    String operazioneQuery = "SELECT idvalutazione FROM valutazione v JOIN libro l on(v.idlibro = l.idlibro) JOIN utente u on(u.cf = v.cf) WHERE v.cf = ? and v.idlibro = ?";
                    PreparedStatement ps = con.prepareStatement(operazioneQuery);
                    ps.setString(1,cf);
                    ps.setInt(2,idLibro);
                    ResultSet risultatoQuery = ps.executeQuery();
                    int stile = valLibro.getStile();
                    String noteStile = valLibro.getNoteStile();
                    int contenuto = valLibro.getContenuto();
                    String noteContenuto = valLibro.getNoteContenuto();
                    int gradevolezza = valLibro.getGradevolezza();
                    String noteGradevolezza = valLibro.getNoteGradevolezza();
                    int originalita = valLibro.getOriginalita();
                    String noteOriginalita = valLibro.getNoteOriginalita();
                    int edizione = valLibro.getEdizione();
                    String noteEdizione = valLibro.getNoteEdizione();
                    int votoFinale = valLibro.getVotoFinale();
                    String noteVotoFinale = valLibro.getNoteVotoFinale();
                    if(risultatoQuery.next()){ //c'è gia una valutazione
                        operazioneQuery = "UPDATE valutazione SET stile = ?,notestile = ?,contenuto = ?,notecontenuto = ?,gradevolezza = ?,notegradevolezza = ?,originalita = ?,noteoriginalita = ?,edizione = ?,noteedizione = ?,votofinale = ?,notevotofinale = ? WHERE cf = ? AND idlibro = ?";
                        ps = con.prepareStatement(operazioneQuery);
                        ps.setInt(1,stile);
                        ps.setString(2,noteStile);
                        ps.setInt(3,contenuto);
                        ps.setString(4,noteContenuto);
                        ps.setInt(5,gradevolezza);
                        ps.setString(6,noteGradevolezza);
                        ps.setInt(7,originalita);
                        ps.setString(8,noteOriginalita);
                        ps.setInt(9,edizione);
                        ps.setString(10,noteEdizione);
                        ps.setInt(11,votoFinale);
                        ps.setString(12,noteVotoFinale);
                        ps.setString(13,cf);
                        ps.setInt(14,idLibro);
                        ps.executeUpdate();
                    }else{
                        operazioneQuery = "INSERT INTO valutazione(idlibro,stile,notestile,contenuto,notecontenuto,gradevolezza,notegradevolezza,originalita,noteoriginalita,edizione,noteedizione,votofinale,notevotofinale,cf) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = con.prepareStatement(operazioneQuery);
                        ps.setInt(1,idLibro);
                        ps.setInt(2,stile);
                        ps.setString(3,noteStile);
                        ps.setInt(4,contenuto);
                        ps.setString(5,noteContenuto);
                        ps.setInt(6,gradevolezza);
                        ps.setString(7,noteGradevolezza);
                        ps.setInt(8,originalita);
                        ps.setString(9,noteOriginalita);
                        ps.setInt(10,edizione);
                        ps.setString(11,noteEdizione);
                        ps.setInt(12,votoFinale);
                        ps.setString(13,noteVotoFinale);
                        ps.setString(14,cf);
                        ps.executeUpdate();
                    }
                }
                else if(operazione.equals("GetValutazioneLibro")){
                    Utente u = (Utente)in.readObject();
                    String cf = u.getCF();
                    int idLibro = (int)in.readObject();
                    Libro libro = (Libro)in.readObject();
                    String comandoQuery = "SELECT v.* FROM valutazione v JOIN utente u on(v.cf = u.cf) JOIN libro l on(l.idlibro = v.idlibro) where v.cf = ? and v.idlibro = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,cf);
                    ps.setInt(2,idLibro);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    int stile = rs.getInt("stile");
                    String noteStile = rs.getString("notestile");
                    int contenuto = rs.getInt("contenuto");
                    String noteContenuto = rs.getString("notecontenuto");
                    int gradevolezza = rs.getInt("gradevolezza");
                    String noteGradevolezza = rs.getString("notegradevolezza");
                    int originalita = rs.getInt("originalita");
                    String noteOriginalita = rs.getString("noteoriginalita");
                    int edizione = rs.getInt("edizione");
                    String noteEdizione = rs.getString("noteedizione");
                    int votoFinale = rs.getInt("votofinale");
                    String noteVotoFinale = rs.getString("notevotofinale");
                    ValutazioniLibro valutazioneLibro = new ValutazioniLibro(u,libro);
                    valutazioneLibro.inserisciValutazioneLibro(stile, noteStile, contenuto, noteContenuto, gradevolezza, noteGradevolezza, originalita, noteOriginalita, edizione, noteEdizione, votoFinale, noteVotoFinale);  
                    out.writeObject(valutazioneLibro);
                    out.flush(); 
                }
                else if(operazione.equals("EsisteValutazioneLibroUtente")){
                    Utente u = (Utente)in.readObject();
                    int idLibro = (int)in.readObject();
                    String cf = u.getCF();
                    String comandoQuery = "SELECT idvalutazione FROM valutazione WHERE cf = ? and idlibro = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,cf);
                    ps.setInt(2,idLibro);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        out.writeObject(true);
                        out.flush();
                    }else{
                        out.writeObject(false);
                        out.flush();
                    }
                }
                else if(operazione.equals("getIfSuggerito")){
                    Utente u = (Utente)in.readObject();
                    int idLibro = (int)in.readObject();
                    String cf = u.getCF();
                    String comandoQuery = "SELECT cf FROM suggerimento WHERE cf = ? and idlibrosorgente = ?";
                    PreparedStatement ps = con.prepareStatement(comandoQuery);
                    ps.setString(1,cf);
                    ps.setInt(2,idLibro);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        out.writeObject(true);
                        out.flush();
                    }else{
                        out.writeObject(false);
                        out.flush();
                    }
                    
                }else if(operazione.equals("aggiungiSuggerimentoLibroUtente")){
                    Utente u = (Utente)in.readObject();
                    int idLibroSorg = (int)in.readObject();
                    String cf = u.getCF();
                    ArrayList<Integer> alIdLibriSugg = (ArrayList<Integer>)in.readObject();
                    String operazioneQuery = "INSERT INTO suggerimento(idlibrosorgente,idlibrosuggerito,cf) VALUES(?,?,?)";
                    for(int i=0;i<alIdLibriSugg.size();i++){
                        PreparedStatement ps = con.prepareStatement(operazioneQuery);
                        ps.setInt(1,idLibroSorg);
                        ps.setString(3,cf);
                        ps.setInt(2,alIdLibriSugg.get(i));
                        ps.executeUpdate();
                    }
                }
                else if(operazione.equals("getMediaValutazioniLibro")){
                    int id = (int)in.readObject();
                    String operazioneQuery = "SELECT AVG(stile), AVG(contenuto), AVG(gradevolezza), AVG(originalita), AVG(edizione), AVG(votofinale) FROM valutazione WHERE idLibro = ?";
                    ArrayList<Double> alMedia = null;
                    PreparedStatement ps = con.prepareStatement(operazioneQuery);
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        boolean tuttiNull = true;
                        for(int i=1;i<=6;i++){
                            if(rs.getObject(i)!=null){
                                tuttiNull = false;
                                break;
                            }
                        }
                        if(tuttiNull){
                            out.writeObject(null);
                            out.flush();
                        }else{
                            alMedia = new ArrayList<>();
                            for(int i=1;i<=6;i++){
                                alMedia.add(rs.getDouble(i));
                            }
                            out.writeObject(alMedia);
                            out.flush();
                        }
                    }
                    
                }else if(operazione.equals("getUtentiSuggeritori")){
                    int idLibro = (int)in.readObject();
                    ArrayList<Utente> alUtenti = new ArrayList<Utente>();
                    String operazioneQuery= "SELECT DISTINCT u.* FROM SUGGERIMENTO s JOIN utente u ON (s.cf = u.cf) WHERE s.idlibrosorgente = ?";
                    PreparedStatement ps = con.prepareStatement(operazioneQuery);
                    ps.setInt(1, idLibro);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        String nome = rs.getString(1);
                        String cognome =  rs.getString(2);
                        String cf = rs.getString(3);
                        String email = rs.getString(4);
                        String username = rs.getString(5);
                        String password = rs.getString(6);
                        alUtenti.add(new Utente(nome, cognome, cf, email, username, password));
                    }
                    out.writeObject(alUtenti);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}