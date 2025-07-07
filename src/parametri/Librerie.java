//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package parametri;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe Librerie per gestire le varie librerie degli utenti
 * Fornisce metodi per la gestione dei libri contenuti in essa.
*/ 
public class Librerie implements Serializable{
    private final Utente utente;
    private final ArrayList<Libro> alLibri;
    private final String nome;

/**
 * Costruisce un oggetto Librerie dato il nome della libreria, l'utente possessore della libreria e la lista dei libri
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
 * Restituisce l'utente associato alla libreria.
 * @return L'oggetto Utente associato alla libreria.
 */
    public Utente getUtente(){
        return this.utente;
    }
/**
 * Restituisce il nome della libreria.
 * @return Il nome della libreria.
 */
    public String getNome(){
        return this.nome;
    }

/**
 * Restituisce l'elenco dei libri presenti nella libreria.
 * @return Un ArrayList contenente i libri presenti nella libreria.
 */
    public ArrayList<Libro> getAlLibri(){
        return this.alLibri;
    }
}
  
