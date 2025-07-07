//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package parametri;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Classe Utente per la gestione dei vari utenti iscritti alla piattaforma
 * Presenta metodi per il cambio dei dati e per l'ottenimento dei dati di un certo utente
 */

public class Utente implements Serializable {
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private String cf;
    private int codice;

/**
 * Costruttore per la classe Utente che inizializza un nuovo utente.
 *
 * @param nome Il nome dell'utente.
 * @param cognome Il cognome dell'utente.
 * @param codiceFiscale il codiceFiscale dell'utente
 * @param email L'email dell'utente.
 * @param username Lo username dell'utente.
 * @param password La password dell'utente.
 */
    public Utente(String nome, String cognome,String codiceFiscale, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = codiceFiscale;
        this.email = email;
        this.username = username;
        this.password = password;
    }
/**
 * Restituisce il nome dell'utente.
 * @return Il nome dell'utente.
 */
    public String getNome() {
        return this.nome;
    }
/**
 * Restituisce il cognome dell'utente.
 * @return Il cognome dell'utente.
 */
    public String getCognome() {
        return this.cognome;
    }
/**
 * Restituisce l'indirizzo email dell'utente.
 * @return L'indirizzo email dell'utente.
 */
    public String getEmail() {
        return this.email;
    }
/**
 * Restituisce il nome utente dell'utente.
 * @return Il nome utente dell'utente.
 */
    public String getUsername() {
        return this.username;
    }
/**
 * Restituisce la password dell'utente.
 * @return La password dell'utente.
 */
    public String getPassword() {
        return this.password;
    }
/**
 * Imposta l'indirizzo email dell'utente.
 * @param email Il nuovo indirizzo email da assegnare all'utente.
 */
    public void setEmail(String email) {
        this.email = email;
    }
/**
 * Imposta lo username dell'utente.
 * @param username Il nuovo username da assegnare all'utente.
 */
    public void setUsername(String username) {
        this.username = username;
    }
/**
 * Imposta la password dell'utente.
 * @param password La nuova password da assegnare all'utente.
 */
    public void setPassword(String password) {
        this.password = password;
    }
/**
 * Restituisce il codice fiscale dell'utente
 * @return il codice fiscale dell'utente
 */
    public String getCF(){
        return cf;
    }

/**
/**
 * Verifica se una delle stringhe passate è vuota.
 *
 * @param nome     Il nome da controllare.
 * @param cognome  Il cognome da controllare.
 * @param codiceFiscale Il codice fiscale dell'utente
 * @param email    L'email da controllare.
 * @param username Lo username da controllare.
 * @param password La password da controllare.
 * @return true se una delle stringhe è vuota, false altrimenti.
 */
    public static boolean stringheVuote(String nome, String cognome,String codiceFiscale, String email,String username, String password)
    {   
        return nome.length()==0 || cognome.length()==0 || codiceFiscale.length()==0 || email.length()==0 || username.length()==0 || password.length()==0;
    }

/**
 * Verifica la validità di un indirizzo email
 * @param email L'indirizzo email da controllare.
 * @return true se l'indirizzo email è valido, false altrimenti.
 */
    public static boolean controlloEmail(String email){
        boolean b=true;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)==64){
                b=false;
            }
        }
        return b;
    }
/**
 * Verifica se un'email è già presente nella lista degli utenti.
 * @param alUtente ArrayList di tutti gli utenti iscritti
 * @param email    Email da cercare.
 * @return true se l'email esiste nella lista degli utenti, false altrimenti.
 */
    public static boolean emailEsistente(ArrayList<Utente> alUtente, String email){
        boolean b=false;
        for (int i = 0; i < alUtente.size() && !b; i++) {
            if (alUtente.get(i).getEmail().equals(email)) {
                b=true;
            }
        }
        return b;
    }
/**
 * Verifica se uno username è già presente nella lista degli utenti.
 * @param alUtente ArrayList di tutti gli utenti iscritti
 * @param username Username da cercare.
 * @return true se lo username esiste nella lista degli utenti, false altrimenti.
 */
    public static boolean usernameEsistente(ArrayList<Utente> alUtente, String username)
    {
        boolean b=false;
        for (int i = 0; i < alUtente.size() && !b; i++) {
            if (alUtente.get(i).getUsername().equals(username)) {
                b=true;
            }
        }
        return b;
    }
    /**
     * Verifica della validità del codice fiscale di un utente.
     * @param cf il codice fiscale da controllare
     * @return il metodo restituisce true se il codice fiscale è corretto, false altrimenti
     */
    public static boolean controlloCF(String cf){
        if(cf.length()==16){
            String stringaTagliata=cf.substring(0,6);
            boolean corretto=true;
            for(int i=0;i<stringaTagliata.length() && corretto;i++){
                if(!(Character.isLetter(stringaTagliata.charAt(i)))){
                    corretto=false;
                }
            }
            stringaTagliata=cf.substring(6,8);
            for(int i=0;i<stringaTagliata.length() && corretto;i++){
                if(!(Character.isDigit(stringaTagliata.charAt(i)))){
                    corretto=false;
                }
            }
            if(corretto && (!(Character.isLetter(cf.charAt(8))))){
                corretto=false;
            }
            stringaTagliata=cf.substring(9, 11);
            for(int i=0;i<stringaTagliata.length() && corretto;i++){
                if(!(Character.isDigit(stringaTagliata.charAt(i)))){
                    corretto=false;
                }
            }
            if(corretto && (!(Character.isLetter(cf.charAt(11))))){
                corretto=false;
            }
            stringaTagliata=cf.substring(12,15);
            for(int i=0;i<stringaTagliata.length() && corretto;i++){
                if(!(Character.isDigit(stringaTagliata.charAt(i)))){
                    corretto=false;
                }
            }
            if(corretto && (!(Character.isLetter(cf.charAt(15))))){
                corretto=false;
            }
            return corretto;
        }else{
            return false;
        }
    }
    /**
     * Verifica se il codice fiscale è già stato utilizzato da altri utenti
     * @param alUtente ArrayList di utenti registrati.
     * @param codiceFiscale il codice fiscale da controllare
     * @return il metodo restituisce true se il codice fiscale è già stato utilizzato da qualcuno, false altrimenti.
     */
    public static boolean CodiceFiscaleEsistente(ArrayList<Utente> alUtente, String codiceFiscale){
        boolean esiste = false;
        for(int i=0;i<alUtente.size() && !esiste;i++){
            if(alUtente.get(i).getCF().equals(codiceFiscale)){
                esiste = true;
            }
        }
        return esiste;
    }
     
}
