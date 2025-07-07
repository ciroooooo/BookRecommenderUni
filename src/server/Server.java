package server;
import java.io.*;
import java.net.*;
/**
 * Classe Server per la comunicazione con i vari client
 * Il server accetta varie connessioni e per ognuna di essa fa partire un thread che gestirà in modo autonomo le varie richieste.
 */
public class Server{
    private ServerSocket serverSocket;
    public final static int PORT = 1090;
    /**
     * Costruttore della classe Server.
     */
    public Server(){
        try{
            serverSocket = new ServerSocket(PORT);
        }catch(IOException e){}
    }
    /**
     * Metodo per accettare le varie richieste di connessione al server, ad ogni connessione accettata, fa partire un thread che si occuperà della connessione.
     */
    public void exec(){
        while(true){
            try {
                System.out.println("In attesa di una nuova connessione");
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata");
                new ServerSlave(socket);
            } catch (IOException e) {}
        }
    }
    /** 
     * Metodo main per l'avvio del programma. 
     */ 
    public static void main(String[] args) {
        Server server = new Server();
        server.exec();
    }
}