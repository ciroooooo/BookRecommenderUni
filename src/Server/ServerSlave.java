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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}