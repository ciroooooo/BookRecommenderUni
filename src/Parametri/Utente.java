//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package Parametri;


import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.sql.*;
public class Utente implements Serializable {

    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private int codice;
    private String cf;
    ArrayList<Utente> alUtente = new ArrayList<Utente>();

/**
 * Costruttore per la classe Utente che inizializza un nuovo utente con nome, cognome, email, username e password.
 * Controlla la validità dei parametri e gestisce il codice di stato dell'utente.
 *
 * @param nome Il nome dell'utente.
 * @param cognome Il cognome dell'utente.
 * @param email L'email dell'utente.
 * @param username Lo username dell'utente.
 * @param password La password dell'utente.
 *
 * @throws IllegalArgumentException Se uno dei parametri nome, cognome, email, username o password è una stringa vuota.
 * @throws EmailNonValidaException Se l'email non è nel formato corretto.
 */
    public Utente(String nome, String cognome,String codiceFiscale, String email, String username, String password) {
        if(stringheVuote(nome, cognome,codiceFiscale, email, username, password)){
            this.codice=4;
            return;
        }
        if(controlloEmail(email)){
            this.codice=5;
            return;
        }
        if(!(controlloCF(codiceFiscale))){
            this.codice=10;
            return;
        }
        this.cf=codiceFiscale;
        
        alUtente.clear();
        alUtente=leggiFile();

        this.nome = nome;
        this.cognome = cognome;
        if(emailEsistente(alUtente,email)){
            this.codice=0;
            return;
        }
        this.email = email;
        if(usernameEsistente(alUtente,username)){
            this.codice=1;
            return;
        }
        this.username = username;
        this.password = password;
        
        this.codice = 2;
    }
/**
 * Restituisce il nome dell'utente.
 *
 * @return Il nome dell'utente.
 */
    public String getNome() {
        return this.nome;
    }
/**
 * Restituisce il cognome dell'utente.
 *
 * @return Il cognome dell'utente.
 */
    public String getCognome() {
        return this.cognome;
    }
/**
 * Restituisce l'indirizzo email dell'utente.
 *
 * @return L'indirizzo email dell'utente.
 */
    public String getEmail() {
        return this.email;
    }
/**
 * Restituisce il nome utente dell'utente.
 *
 * @return Il nome utente dell'utente.
 */
    public String getUsername() {
        return this.username;
    }
/**
 * Restituisce la password dell'utente.
 *
 * @return La password dell'utente.
 */
    public String getPassword() {
        return this.password;
    }
/**
 * Restituisce il codice di stato legato alla creazione dell'utente.
 *
 * @return Il codice di stato: 0 se l'email è già esistente, 1 se il username è già esistente,
 * 2 se l'utente è stato creato con successo, 4 se una delle stringhe passate come parametro è vuota,
 * 5 se l'email passata come parametro non è valida.
 */
    public int getCode() {
        return this.codice;
    }
/**
 * Imposta il nome dell'utente.
 *
 * @param nome Il nuovo nome da assegnare all'utente.
 */
    public void setNome(String Nome) {
        this.nome = Nome;
    }
/**
 * Imposta il cognome dell'utente.
 *
 * @param cognome Il nuovo cognome da assegnare all'utente.
 */
    public void setCognome(String Cognome) {
        this.cognome = Cognome;
    }
/**
 * Imposta l'indirizzo email dell'utente.
 *
 * @param email Il nuovo indirizzo email da assegnare all'utente.
 */
    public void setEmail(String Email) {
        this.email = Email;
    }
/**
 * Imposta lo username dell'utente.
 *
 * @param username Il nuovo username da assegnare all'utente.
 */
    public void setUsername(String Username) {
        this.username = Username;
    }
/**
 * Imposta la password dell'utente.
 *
 * @param password La nuova password da assegnare all'utente.
 */
    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getCF(){
        return cf;
    }
    public void setCF(String cf){
        this.cf=cf;
    }




/**
 * Restituisce la dimensione dell'ArrayList di utenti letta da un file.
 *
 * @return La dimensione dell'ArrayList di utenti.
 * @throws IOException            Se si verifica un errore di input/output durante la lettura del file.
 * @throws ClassNotFoundException Se la classe degli oggetti letti dal file non viene trovata.
 */
    public int getAlSize(){
        alUtente.clear();
        File file=new File("file\\utenti.txt");
        try{
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            alUtente=(ArrayList<Utente>)ois.readObject();
            ois.close();
            fis.close();
        }catch(EOFException e){
        }catch(IOException | ClassNotFoundException e){
        }
        return alUtente.size();
    }
/**
/**
 * Verifica se una delle stringhe passate è vuota.
 *
 * @param nome     Il nome da controllare.
 * @param cognome  Il cognome da controllare.
 * @param email    L'email da controllare.
 * @param username Lo username da controllare.
 * @param password La password da controllare.
 * @return true se una delle stringhe è vuota, false altrimenti.
 */
    private boolean stringheVuote(String nome, String cognome,String codiceFiscale, String email,String username, String password)
    {   
        if(nome.length()==0 || cognome.length()==0 || codiceFiscale.length()==0 || email.length()==0 || username.length()==0 || password.length()==0){
            return true;
        }else
        {
        return false;
        }
    }
/**
 * Verifica la validità di un indirizzo email utilizzando un'espressione regolare.
 *
 * @param email L'indirizzo email da controllare.
 * @return true se l'indirizzo email è valido, false altrimenti.
 */
    private boolean controlloEmail(String email){
        boolean b=true;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)==64){
                b=false;
            }
        }
        return b;
    }
/**
 * Legge un file di utenti serializzato e restituisce un ArrayList di Utente.
 *
 * @return ArrayList di Utente letto dal file.
 * @throws IOException            Se si verifica un errore di I/O durante la lettura del file.
 * @throws ClassNotFoundException Se la classe dell'oggetto letto non è trovata.
 */
    public ArrayList<Utente> leggiFile(){
        ArrayList<Utente> tmp=new ArrayList<>();
        File file=new File("file\\utenti.txt");
        if(file.length()!=0){
            try{
                FileInputStream fis=new FileInputStream(file);
                ObjectInputStream ois=new ObjectInputStream(fis);
                tmp=(ArrayList<Utente>)ois.readObject();
                fis.close();
                ois.close();
                return tmp;
            }catch(EOFException e){
            }catch(IOException | ClassNotFoundException e){

            }
        }
    return tmp;
    }
/**
 * Verifica se un'email è già presente nella lista degli utenti.
 *
 * @param alUtente ArrayList di Utente in cui cercare l'email.
 * @param email    Email da cercare.
 * @return true se l'email esiste nella lista degli utenti, false altrimenti.
 */
    private boolean emailEsistente(ArrayList<Utente> alUtente, String email){
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
 *
 * @param alUtente ArrayList di Utente in cui cercare lo username.
 * @param username Username da cercare.
 * @return true se lo username esiste nella lista degli utenti, false altrimenti.
 */
    private boolean usernameEsistente(ArrayList<Utente> alUtente, String username)
    {
        boolean b=false;
        for (int i = 0; i < alUtente.size() && !b; i++) {
            if (alUtente.get(i).getUsername().equals(username)) {
                b=true;
            }
        }
        return b;
    }
    private boolean controlloCF(String cf){
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
     
}
