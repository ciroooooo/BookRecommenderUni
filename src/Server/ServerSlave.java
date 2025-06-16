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
                }
                if(operazione.equals("GetListaNomi")){
                    ResultSet risultatoQuery = stmt.executeQuery("SELECT nome FROM utente");
                    ArrayList<String> listaNomi = new ArrayList<>();
                    while(risultatoQuery.next()){
                        listaNomi.add(risultatoQuery.getString("nome"));
                    }
                    System.out.println(listaNomi.size());
                }
                //Lista operazioni.
            }
        } catch (Exception e) {
        }
    }

}