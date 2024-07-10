//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Librerie implements Serializable{
    private Utente utente;
    private ArrayList<Libro> alLibri;
    private String nome;
    /**
 * Costruisce un oggetto Librerie con il nome, l'utente e la lista di libri fornita.
 *
 * @param nome Il nome della libreria.
 * @param utente L'utente associato alla libreria.
 * @param alLibri Un ArrayList contenente i libri della libreria.
 */
    public Librerie(String nome,Utente utente,ArrayList<Libro>alLibri){
        this.utente=utente;
        this.alLibri=alLibri;
        this.nome=nome;    
    }
/**
 * Aggiunge un libro alla lista dei libri della libreria.
 *
 * @param l Il libro da aggiungere alla libreria.
 */
    public void aggiungiLibro(Libro l){
        this.alLibri.add(l);
    }
/**
 * Restituisce l'utente associato alla libreria.
 *
 * @return L'oggetto Utente associato alla libreria.
 */
    public Utente getUtente(){
        return this.utente;
    }
/**
 * Restituisce il nome della libreria.
 *
 * @return Il nome della libreria.
 */
    public String getNome(){
        return this.nome;
    }
/**
 * Restituisce l'elenco dei libri presenti nella libreria.
 *
 * @return Un ArrayList contenente i libri presenti nella libreria.
 */
    public ArrayList<Libro> getAlLibri(){
        return this.alLibri;
    }
/**
 * Scrive l'elenco delle librerie su un file.
 *
 * @param lib L'ArrayList contenente le librerie da scrivere su file.
 * @throws FileNotFoundException Se il file "Librerie.txt" non può essere creato.
 * @throws IOException Se si verifica un errore di I/O durante la scrittura del file.
 */
    public static void scriviAlLibrerieFile(ArrayList<Librerie> lib){ 
        File file=new File("file\\Librerie.txt");
        try {
            FileOutputStream fos=new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(lib);
            fos.close();
            oos.close();
        } catch (Exception e) {
        }
    }
    /**
 * Legge l'elenco delle librerie da un file utilizzando la deserializzazione.
 *
 * @return Un ArrayList contenente le librerie lette dal file, o un ArrayList vuoto se il file non contiene dati.
 * @throws FileNotFoundException Se il file "Librerie.txt" non esiste.
 * @throws IOException Se si verifica un errore di I/O durante la lettura del file.
 * @throws ClassNotFoundException Se la classe delle librerie non viene trovata durante la deserializzazione.
 */
    public static ArrayList<Librerie> leggiFileLibrerie(){
        ArrayList<Librerie>alLibtemp=new ArrayList<>();
        File file = new File("file\\Librerie.txt");
        if (file.length() != 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                alLibtemp = (ArrayList<Librerie>) ois.readObject();
                fis.close();
                ois.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return alLibtemp;
    }
/**
 * Filtra le librerie in base all'username dell'utente.
 *
 * @param u L'oggetto Utente tramite cui filtrare le librerie.
 * @return Un ArrayList contenente le librerie dell'utente specificato.
 */
    public static ArrayList<Librerie> filtraLibrerie(Utente u) {
    ArrayList<Librerie> alLibrerie = leggiFileLibrerie();
        ArrayList<Librerie> alLibreries = new ArrayList<>();
        for (int i = 0; i < alLibrerie.size(); i++) {
            if (alLibrerie.get(i).getUtente().getUsername().equals(u.getUsername())) {
                alLibreries.add(alLibrerie.get(i));
            }
        }
        return alLibreries;
    }
}