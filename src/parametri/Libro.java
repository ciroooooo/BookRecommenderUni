//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package parametri;
import java.io.Serializable;
import java.time.LocalDate;
/**
 * Classe Libro per la gestione dei vari libri 
 * Permette di creare dei nuovi libri e di ottenere le varie informazioni associate ad esso
 */
public class Libro implements Serializable{
    private final  String titolo;
    private final Autore autore;
    private final String descrizione;
    private final String categoria;
    private final String editore;
    private final LocalDate dataPubblicazione;
    private final double prezzo;

    /**
     * Crea una nuova istanza della classe Libro.
     *
     * @param titolo Il titolo del libro.
     * @param autore Il nome dell'autore del libro.
     * @param descrizione Una breve descrizione del libro.
     * @param categoria La categoria del libro (ad esempio, "Romanzo", "Saggio").
     * @param editore Il nome dell'editore del libro.
     * @param dataPubblicazione La data di pubblicazione del libro.
     * @param prezzo Il prezzo del libro.
     */
    public Libro(String titolo,Autore autore,String descrizione,String categoria,String editore,LocalDate dataPubblicazione,double prezzo){
        this.titolo=titolo;
        this.autore= autore;
        this.descrizione=descrizione;
        this.categoria=categoria;
        this.editore=editore;
        this.dataPubblicazione=dataPubblicazione;
        this.prezzo=prezzo;
    }
    
 /**
 * Restituisce una rappresentazione dell'oggetto Libro.
 *
 * @return Una stringa che rappresenta il libro, includendo titolo, autore, descrizione,
 * categoria, editore, data di pubblicazione e prezzo.
 */
    @Override
    public String toString(){
        return this.titolo+" "+this.autore.getNome()+" "+this.descrizione+" "+this.categoria+" "+this.editore+" "+this.dataPubblicazione+" "+this.prezzo;
    }
    /**
 * Restituisce il nome dell'autore del libro.
 * @return Una stringa contenente il nome dell'autore del libro.
 */
    public String getAutore(){
        return this.autore.getNome();
    }
    /**
 * Restituisce il titolo del libro.
 * @return Una stringa contenente il titolo del libro.
 */
    public String getTitolo(){
        return this.titolo;
    }
    /**
 * Restituisce la descrizione del libro.
 * @return Una stringa contenente la descrizione del libro.
 */
    public String getDescrizione(){
        return this.descrizione;
    }
    /**
 * Restituisce la categoria del libro.
 *
 * @return Una stringa contenente la categoria del libro.
 */
    public String getCategoria(){
        return this.categoria;
    }
    /**
 * Restituisce il nome dell'editore del libro.
 *
 * @return Una stringa contenente l'editore del libro.
 */
    public String getEditore(){
        return this.editore;
    }
    /**
 * Restituisce la data di pubblicazione del libro.
 *
 * @return La data di pubblicazione del libro come oggetto LocalDate.
 */
    public LocalDate getDataPubblicazione(){
        return this.dataPubblicazione;
    }
    /**
 * Restituisce il prezzo del libro.
 *
 * @return Il prezzo del libro come valore double.
 */
    public double getPrezzo(){
        return this.prezzo;
    }
    /**
 * Restituisce una rappresentazione del libro fornito.
 *
 * @param l Il libro da visualizzare.
 * @return Una stringa che rappresenta il libro, ottenuta invocando il metodo toString() del libro.
 */
    public static String visualizzaLibro(Libro l){
        return l.toString();
    }
    /**
 * Restituisce il nome dell'autore del libro fornito.
 *
 * @param l Il libro di cui si vuole ottenere il nome dell'autore.
 * @return Una stringa contenente il nome dell'autore del libro.
 */
    public static String getAutore(Libro l){
        return l.getAutore();
    }
    /**
 * Restituisce l'anno di pubblicazione del libro fornito.
 *
 * @param l Il libro di cui si vuole ottenere l'anno di pubblicazione.
 * @return Una stringa che rappresenta l'anno di pubblicazione del libro nel formato "AAAA".
 */
    public static String getAnno(Libro l){
        return l.getDataPubblicazione().toString();
    }
    /**
 * Restituisce il titolo del libro fornito.
 *
 * @param l Il libro di cui si vuole ottenere il titolo.
 * @return Una stringa contenente il titolo del libro.
 */
    public static String getTitolo(Libro l){
        return l.getTitolo();
    }
    /**
 * Restituisce le informazioni di base del libro, ovvero il titolo, l'autore, l'anno di pubblicazione e se c'è, anche la descrizione
 * @param libro Il libro di cui si vogliono ottenere le informazioni di base.
 * @return Una stringa HTML che rappresenta le informazioni di base del libro, inclusi titolo, autore,
 *         anno di pubblicazione e, se disponibile, la descrizione del libro con un layout specifico.
 */
    public static String getInfoBase(Libro libro){
        String infoLibro = "<html>";
        infoLibro += "<b>Titolo:</b> " + libro.getTitolo() + "<br>";
        infoLibro += "<b>Autore:</b> " + libro.getAutore() + "<br>";
        infoLibro += "<b>Anno Pubblicazione:</b> " + libro.getDataPubblicazione().getYear() + "<br>";
        
        if (!libro.getDescrizione().isEmpty()) {
            infoLibro += "<b>Descrizione:</b>";
            infoLibro += "<div style='width: 300px; max-height: 300px; overflow-y: auto;'>";
            infoLibro += libro.getDescrizione();
            infoLibro += "</div>";
        }
        infoLibro += "</html>";
        return infoLibro;
    }
    
}
