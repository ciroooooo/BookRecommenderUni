import java.io.*;
import java.net.*;
public class Server{
    private ServerSocket serverSocket;
    public final static int PORT = 1090;
    public Server(){
        try{
            serverSocket = new ServerSocket(PORT);
        }catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
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
    public static void main(String[] args) {
        Server server = new Server();
        server.exec();
    }
}