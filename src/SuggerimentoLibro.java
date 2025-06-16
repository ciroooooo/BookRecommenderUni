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

public class SuggerimentoLibro implements Serializable{
    private ArrayList<Libro>suggerimenti;
    private Libro l;
    private Utente u;
/**
 * Costruisce un nuovo oggetto SuggerimentoLibro con due parametri uno Libro e l'altro Utente.
 *
 * @param l Il libro associato al suggerimento.
 * @param u L'utente a cui è destinato il suggerimento.
 */
    public SuggerimentoLibro(Libro l,Utente u){
        this.l=l;
        this.u=u;
        suggerimenti=new ArrayList<>();
    }
/**
 * Inserisce una lista di suggerimenti per i libri.
 *
 * @param suggerimenti Una ArrayList contenente i suggerimenti da inserire.
 */
    public void inserisciSuggerimento(ArrayList<Libro> suggerimenti){
        this.suggerimenti=suggerimenti;
    }
/**
 * Scrive un ArrayList di SuggerimentoLibro su un file chiamato "Suggerimenti.txt".
 *
 * @param alSugg L'ArrayList di SuggerimentoLibro da scrivere sul file.
 * @throws Exception Se si verificano errori durante la scrittura del file.
 */
    public static void scriviFileSugg(ArrayList<SuggerimentoLibro> alSugg){
        File file=new File("file\\Suggerimenti.txt");
        LibrerieMainFrame.FileEsiste(file);
        try {
            FileOutputStream fos=new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(alSugg);
            fos.close();
            oos.close();
        } catch (Exception e) {
        }
    }
/**
 * Legge un ArrayList di SuggerimentoLibro dal file "Suggerimenti.txt".
 *
 * @return Un ArrayList di SuggerimentoLibro letto dal file.
 * @throws Exception Se si verificano errori durante la lettura del file.
 */
    public static ArrayList<SuggerimentoLibro> leggiFileSugg(){
        File file=new File("file\\Suggerimenti.txt");
        LibrerieMainFrame.FileEsiste(file);
        ArrayList<SuggerimentoLibro> tmp=new ArrayList<>();
        if(file.length()!=0){
            try {
                FileInputStream fis=new FileInputStream(file);
                ObjectInputStream ois=new ObjectInputStream(fis);
                tmp=(ArrayList<SuggerimentoLibro>)ois.readObject();
            } catch (Exception e) {
            }
        }
        return tmp;
    }
/**
 * Restituisce l'utente associato al suggerimento.
 *
 * @return L'oggetto Utente associato a questo suggerimento di libro.
 */
    public Utente getUtente(){
        return this.u;
    }
/**
 * Restituisce il libro associato al suggerito.
 *
 * @return L'oggetto Libro associato a questo suggerimento di libro.
 */
    public Libro getLibro(){
        return this.l;
    }
/**
 * Restituisce l'elenco di libri suggeriti associati a SuggerimentoLibro.
 *
 * @return Un ArrayList contenente l'elenco di libri suggeriti.
 */
    public ArrayList<Libro>getALLibri(){
        return this.suggerimenti;
    }

}