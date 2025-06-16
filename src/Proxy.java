import java.io.*;
import java.net.*;
public class Proxy{
    private InetAddress ip;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Proxy(){
        try{
            this.ip = InetAddress.getByName(null);
            socket = new Socket(ip,1090);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    public void fineComunicazione(){
        try{
            out.writeObject("Fine");
            out.flush();
            socket.close();
        }catch(IOException e){}
    }
    public void getListaNomi(){
        try {
            out.writeObject("GetListaNomi");
            out.flush();
            
        } catch (IOException e) {
        }

    }
}