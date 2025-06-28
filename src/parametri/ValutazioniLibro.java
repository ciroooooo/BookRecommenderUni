//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)


package parametri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



public class ValutazioniLibro implements Serializable{
    private int stile;
    private Utente u;
    private String noteStile;
    private int contenuto;
    private String noteContenuto;
    private int gradevolezza;
    private String noteGradevolezza;
    private int originalita;
    private String noteOriginalita;
    private int edizione;
    private String noteEdizione;
    private int votoFinale;
    private String noteVotoFinale;
    private Libro l;
    
    /**
 * Costruisce un nuovo oggetto ValutazioniLibro associato a un utente e a un libro specificati.
 *
 * @param u L'utente associato alla valutazione del libro.
 * @param l Il libro associato alla valutazione.
 */
    public ValutazioniLibro(Utente u,Libro l)
    {
        this.l=l;
        this.u=u;
    }
/**
 * Inserisce le valutazioni di vari aspetti di un libro e le relative note.
 * Verifica anche la lunghezza delle note per evitare superamenti dei limiti consentiti.
 *
 * @param stile Valutazione dello stile del libro.
 * @param noteStile Note sulla valutazione dello stile.
 * @param contenuto Valutazione del contenuto del libro.
 * @param noteContenuto Note sulla valutazione del contenuto.
 * @param gradevolezza Valutazione della gradevolezza del libro.
 * @param noteGradevolezza Note sulla valutazione della gradevolezza.
 * @param originalita Valutazione dell'originalità del libro.
 * @param noteOriginalita Note sulla valutazione dell'originalità.
 * @param edizione Valutazione dell'edizione del libro.
 * @param noteEdizione Note sulla valutazione dell'edizione.
 * @param votoFinale Voto finale complessivo del libro.
 * @param noteVotoFinale Note sul voto finale.
 */
    public void inserisciValutazioneLibro(int stile,String noteStile,int contenuto,String noteContenuto,int gradevolezza,String noteGradevolezza,int originalita,String noteOriginalita,int edizione,String noteEdizione,int votoFinale,String noteVotoFinale){
        this.stile=stile;
        this.contenuto=contenuto;
        this.gradevolezza=gradevolezza;
        this.originalita=originalita;
        this.edizione=edizione;
        this.votoFinale=votoFinale;
        this.noteStile=noteStile;
        this.noteContenuto=noteContenuto;
        this.noteGradevolezza=noteGradevolezza;
        this.noteOriginalita=noteOriginalita;
        this.noteEdizione=noteEdizione;
        this.noteVotoFinale=noteVotoFinale;
    }
    /**
 * Restituisce la valutazione dello stile del libro.
 *
 * @return La valutazione dello stile del libro.
 */
    public int getStile(){
        return this.stile;
    }
/**
 * Restituisce la valutazione del contenuto del libro.
 *
 * @return La valutazione del contenuto del libro.
 */
    public int getContenuto(){
        return this.contenuto;
    }
/**
 * Restituisce la valutazione della gradevolezza del libro.
 *
 * @return La valutazione della gradevolezza del libro.
 */
    public int getGradevolezza(){
        return this.gradevolezza;
    }
/**
 * Restituisce la valutazione dell'originalità del libro.
 *
 * @return La valutazione dell'originalità del libro.
 */
    public int getOriginalita(){
        return this.originalita;
    }
/**
 * Restituisce il numero di edizione del libro.
 *
 * @return Il numero di edizione del libro.
 */
    public int getEdizione(){
        return this.edizione;
    }
/**
 * Restituisce il voto finale assegnato al libro.
 *
 * @return Il voto finale assegnato al libro.
 */
    public int getVotoFinale(){
        return this.votoFinale;
    }
/**
 * Restituisce le note sulla valutazione dello stile del libro.
 *
 * @return Le note sulla valutazione dello stile del libro.
 */
    public String getNoteStile(){
        return this.noteStile;
    }
/**
 * Restituisce le note sulla valutazione del contenuto del libro.
 *
 * @return Le note sulla valutazione del contenuto del libro.
 */
    public String getNoteContenuto(){
        return this.noteContenuto;
    }
/**
 * Restituisce le note sulla valutazione della gradevolezza del libro.
 *
 * @return Le note sulla valutazione della gradevolezza del libro.
 */
    public String getNoteGradevolezza(){
        return this.noteGradevolezza;
    }
/**
 * Restituisce le note sulla valutazione dell'originalità del libro.
 *
 * @return Le note sulla valutazione dell'originalità del libro.
 */
    public String getNoteOriginalita(){
        return this.noteOriginalita;
    }
/**
 * Restituisce le note sulla valutazione dell'edizione del libro.
 *
 * @return Le note sulla valutazione dell'edizione del libro.
 */
    public String getNoteEdizione(){
        return this.noteEdizione;
    }
/**
 * Restituisce le note sulla valutazione finale del libro.
 *
 * @return Le note sulla valutazione finale del libro.
 */
    public String getNoteVotoFinale(){
        return this.noteVotoFinale;
    }
/**
 * Restituisce il libro associato a questa valutazione.
 *
 * @return Il libro associato a questa valutazione.
 */
    public Libro getLibro(){
        return this.l;
    }
/**
 * Restituisce l'utente associato a questa valutazione.
 *
 * @return L'utente associato a questa valutazione.
 */
    public Utente getUtente(){
        return this.u;
    }
/**
 * Scrive un elenco di valutazioni di libri su un file.
 *
 * @param alVL L'ArrayList di ValutazioniLibro da scrivere su file.
 * @throws IOException Se si verifica un errore di input/output durante la scrittura su file.
 */
    public static void scriviFileVL(ArrayList<ValutazioniLibro> alVL){
        File file=new File("file\\Valutazioni.txt");
        try {
            FileOutputStream fos=new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(alVL);
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
    }
/**
 * Legge un elenco di valutazioni di libri da un file.
 *
 * @return Un ArrayList di ValutazioniLibro lette dal file.
 * @throws IOException            Se si verifica un errore di input/output durante la lettura dal file.
 * @throws ClassNotFoundException Se la classe delle valutazioni di libri non viene trovata durante la deserializzazione.
 */
}