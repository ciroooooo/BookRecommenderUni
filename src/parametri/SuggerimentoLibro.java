//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package parametri;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe SuggerimentoLibro per la gestione dei vari suggerimenti di un determinato libro
 * Presenta metodi per l'aggiunta dei suggerimenti e metodi per conoscere il contenuto della libreria.
 */
public class SuggerimentoLibro implements Serializable{
    private ArrayList<Libro>suggerimenti;
    private final  Libro l;
    private final Utente u;
/**
 * Costruisce un nuovo oggetto SuggerimentoLibro.
 * @param l Il libro associato al suggerimento.
 * @param u L'utente che effettua il suggerimento
 */
    public SuggerimentoLibro(Libro l,Utente u){
        this.l=l;
        this.u=u;
        suggerimenti=new ArrayList<>();
    }
/**
 * Inserisce una lista di suggerimenti per il libri.
 * @param suggerimenti Una ArrayList, contenente i libri da suggerire per il libro dato.
 */
    public void inserisciSuggerimento(ArrayList<Libro> suggerimenti){
        this.suggerimenti=suggerimenti;
    }

/**
 * Restituisce l'utente associato al suggerimento.
 * @return L'oggetto Utente associato al suggerimento del libro.
 */
    public Utente getUtente(){
        return this.u;
    }
/**
 * Restituisce il libro sorgente, su cui si applicano i vari libri suggeriti
 * @return L'oggetto Libro associato a questo suggerimento di libro.
 */
    public Libro getLibro(){
        return this.l;
    }
/**
 * Restituisce l'elenco di libri suggeriti associati a SuggerimentoLibro.
 * @return Un ArrayList contenente l'elenco di libri suggeriti.
 */
    public ArrayList<Libro>getALLibri(){
        return this.suggerimenti;
    }
}