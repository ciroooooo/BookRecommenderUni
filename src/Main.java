//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main {
    public static void FileEsiste(File file){
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(Exception e){
    
            }
        }
    } 
      
    public static void main(String[] args) {
        File file=new File("file\\utenti.txt");
        FileEsiste(file);
        File file2 = new File("file\\Libri.txt");
        File file3=new File("file\\Librerie.txt");
        FileEsiste(file3);
        Proxy proxy = new Proxy();
        proxy.getListaNomi();
        proxy.fineComunicazione();
        ArrayList<Libro> listaLibri=new ArrayList<Libro>();
        try {
            FileInputStream fis = new FileInputStream(file2);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaLibri = (ArrayList<Libro>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LgMainFrame myFrame = new LgMainFrame(listaLibri,proxy);
        myFrame.initialize();
    }
}