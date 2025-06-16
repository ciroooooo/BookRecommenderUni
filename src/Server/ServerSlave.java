import java.io.*;
import java.net.*;
import java.sql.*;
import org.postgresql.*;
import java.util.*;

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
            }
        } catch (Exception e) {
        }
    }

}