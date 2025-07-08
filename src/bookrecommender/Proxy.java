//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)
package bookrecommender;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
import parametri.*;
/**
* Classe Proxy che gestisce la comunicazione tra client e server
* Fornisce metodi per interagire con il server
* Utilizza flussi di oggetti per lo scambio di dati con il server.
*/
public class Proxy{
    private InetAddress ip;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
    * Costruttore della classe Proxy
    * Effettua la connessione al Server ed inizializza il flusso d'uscita e di entrata delle infromazioni
    */
    public Proxy(){
        try{
            this.ip = InetAddress.getByName(null);
            socket = new Socket(ip,1090);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    /**
     * Metodo per interrompere la comunicazione con il server
     */
    public void fineComunicazione(){
        try{
            out.writeObject("Fine");
            out.flush();
            socket.close();
        }catch(IOException e){}
    }
    /**
     * Metodo che chiede al server la lista degli username iscritti.
     * @return un {@code ArrayList} con gli username degli utenti iscritti,
     * oppure {@code null} in caso di errore di comunicazione.
     * 
     */
    public ArrayList<String> getListaUsernameUtente(){
        ArrayList<String> listaUsername = null;
        try {
            out.writeObject("GetListaUsernameUtente");
            out.flush();
            listaUsername = (ArrayList<String>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {}
        return listaUsername;
    }
    /**
     * Metodo che chiede al server la lista delle password degli utenti iscritti
     * @return un {@code ArrayList} con le password degli utenti iscritti,
     * oppure {@code null} in caso di errore di comunicazione.
     * 
     */
    public ArrayList<String> getListaPasswordUtente(){
        ArrayList<String> listaPassword = null;
        try {
            out.writeObject("GetListaPasswordUtente");
            out.flush();
            listaPassword = (ArrayList)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return listaPassword;
    }
    /**
     * Metodo che chiede al server il codice fiscale di una persona, dato il suo username e la sua password.
     * @param username dell'utente 
     * @param password dell'utente
     * @return codiceFiscale di un utente
     */
    public String getCF(String username,String password){
        String cf = "";
        try {
            out.writeObject("GetCF");
            out.flush();
            out.writeObject(username);
            out.flush();
            out.writeObject(password);
            out.flush();
            cf = (String)in.readObject();
        } catch (IOException | ClassNotFoundException e) {}
        return cf;
    }
    /**
     * Metodo per l'aggiunta di un libro all'interno di un database
     * @param titolo il titolo del libro
     * @param descrizione descrizione del libro
     * @param categoria categoria del libro
     * @param editore editore del libro
     * @param dataPubblicazione data della pubblicazione del libro
     * @param prezzo prezzo del libro
     * @param autore nome dell'autore del libro
     */
    public void aggiungiLibro(String titolo,String descrizione,String categoria,String editore,LocalDate dataPubblicazione,double prezzo,String autore){
        try{
            out.writeObject("AggiungiLibro");
            out.flush();
            out.writeObject(titolo);
            out.flush();
            out.writeObject(descrizione);
            out.flush();
            out.writeObject(categoria);
            out.flush();
            out.writeObject(editore);
            out.flush();
            out.writeObject(dataPubblicazione);
            out.flush();
            out.writeObject(prezzo);
            out.flush();
            out.writeObject(autore);
            out.flush();
        }catch(IOException e){}
    }
    /**
     * Metodo per l'aggiunta di un autore all'interno del database
     * @param nome nome dell'autore
     */
    public void aggiungiAutore(String nome){
        try {
            out.writeObject("AggiungiAutore");
            out.flush();
            out.writeObject(nome);
            out.flush();
        } catch (IOException e) {
        }
    }
    /**
     * Metodo per aggiungere un nuovo utente all'interno del database;
     * @param u nuovo utente che bisogna aggiungere
     * @return codice intero che rappresenta il risultato dell'operazione
     *      <ul>
     *          <li>0 - l'email è già presente all'interno del database</li>
     *          <li>1 - lo username è già presenta all'interno del database</li>
     *          <li>2 - l'aggiunta è avvenuta con successo</li>
     *          <li>4 - I campi sono vuoti</li>
     *          <li>5 - Il formato dell'email è errato</li>
     *          <li>7 - Il codice fiscale è già presente all'interno del database</li>
     *          <li>9 - problema di connessione con il server</li>
     *          <li>10 - Il codice fiscale è errato</li>
     *     </ul>
     */
    public int aggiungiUtente(Utente u){
        try {
            if(Utente.stringheVuote(u.getNome(), u.getCognome(), u.getCF(), u.getEmail(), u.getUsername(), u.getPassword())){
                return 4;
            }
            else if(Utente.controlloEmail(u.getEmail())){
                return 5;
            }
            else if(!(Utente.controlloCF(u.getCF()))){
                return 10;
            }
            out.writeObject("AggiungiUtente");
            out.flush();
            ArrayList<Utente> alUtente = (ArrayList<Utente>)in.readObject();
            if(Utente.emailEsistente(alUtente, u.getEmail())){
                out.writeObject("Fine");
                out.flush();
                return 0;
            }
            else if(Utente.usernameEsistente(alUtente, u.getUsername())){
                out.writeObject("Fine");
                out.flush();
                return 1;
            }
            else if(Utente.CodiceFiscaleEsistente(alUtente, u.getCF())){
                out.writeObject("Fine");
                out.flush();
                return 7;
            }
            out.writeObject("Inserisci");
            out.flush();
            out.writeObject(u);
            out.flush();
            return 2; 
        } catch (IOException | ClassNotFoundException e) {
            return 9;
        }
    }
     /**
     * Metodo per ricercare un libro dato il suo titolo
     * @param titolo titolo del libro da ricercare
     * @return ArrayList di libri contente i libri che corrispondono alla ricerca effettuata.
     */
    public ArrayList<Libro> ricercaPerTitolo(String titolo){
        ArrayList<Libro> alLibro = new ArrayList<>();
        try {
            out.writeObject("CercaPerTitolo");
            out.flush();
            out.writeObject(titolo);
            out.flush();
            alLibro = (ArrayList<Libro>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return alLibro;
    }
     /**
     * Metodo per la ricerca di un libro dato il nome di un autore
     * @param autore oggetto di tipo autore.
     * @return ArrayList di libri contenente i libri che corrispondono alla ricerca effettuata.
     */
    public ArrayList<Libro> ricercaPerAutore(Autore autore){
        ArrayList<Libro> alLibri = new ArrayList<>();
        try {
            out.writeObject("CercaPerAutore");
            out.flush();
            out.writeObject(autore);
            out.flush();
            alLibri = (ArrayList<Libro>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return alLibri;
        }
        return alLibri;
    }
    /**
     * Metodo per la ricerca di un libro dato l'autore e l'anno di pubblicazione
     * @param autore oggetto di tipo autore
     * @param anno  anno di pubblicazione del libro
     * @return ArrayList di libri contenente i libri che corrispondono alla ricerca effettuata. 
     */
    public ArrayList<Libro> ricercaPerAutoreEAnno(Autore autore,int anno){
        ArrayList<Libro> alLibri = new ArrayList<>();
        try {
            out.writeObject("CercaPerAutore&Anno");
            out.flush();
            out.writeObject(autore);
            out.flush();
            out.writeObject(anno);
            out.flush();
            alLibri = (ArrayList<Libro>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return alLibri;
        }
        return alLibri;
    }
     /**
     * Metodo che restituisce i nomi delle librerie di un utente
     * @param cf il codice fiscale dell'utente
     * @return ArrayList contentente i nomi delle librerie dell'utente.
     */
    public ArrayList<String> getNomiLibreriaDaUtente(String cf){
        ArrayList<String> nomiLibrerie = new ArrayList();
        try{
            out.writeObject("GetNomiLibreriaDaUtente");
            out.flush();
            out.writeObject(cf);
            out.flush();
            nomiLibrerie = (ArrayList<String>)in.readObject();
        }catch(IOException | ClassNotFoundException e){
            return nomiLibrerie;
        }
        return nomiLibrerie;
    }

     /**
     * Metodo che resituisce un oggetto di tipo utente, contenente dunque tutte le sue informazioni
     * @param cf Il codice fiscale dell'utente
     * @return Un oggetto di tipo utente.
     */

    public Utente getUtenteDaCF(String cf){
        Utente u;
        try {
            out.writeObject("GetUtenteDaCF");
            out.flush();
            out.writeObject(cf);
            out.flush();
            u =(Utente)in.readObject();
            return u;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    /**
     * Metodo per l'aggiunta di una nuova libreria all'interno del database.
     * @param libreria Oggetto di tipo Libreria.
    */
    public void aggiungiLibreria(Librerie libreria){
        try {
            out.writeObject("AggiungiLibreria");
            out.flush();
            out.writeObject(libreria);
            out.flush();
        } catch (IOException e) {
        }
    }
    /**
     * Metodo che restituisce le librerie di un utente
     * @param cf Il codice fiscale di un utente.
     * @return ArrayList contenente tutte le librerie dell'utente.
    */
    public ArrayList<Librerie> getLibrerieUtente(String cf){
        ArrayList<Librerie> librerieUtente = new ArrayList<>();
        try {
            out.writeObject("GetLibrerieDaCf");
            out.flush();
            out.writeObject(cf);
            out.flush();
            librerieUtente = (ArrayList<Librerie>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return librerieUtente;
    }
    /**
    * Metodo che resitituisce la libreria di un certo utente, dato il nome della libreria.
    * @param u Oggetto di tipo utente.
    * @param nomeLibreria Nome della libreria
    * @return Oggetto di tipo Libreria. 
    */
    public Librerie getLibreriaUtenteDaNome(Utente u,String nomeLibreria){
        Librerie libreria = null;
        try {
            out.writeObject("GetLibreriaUtenteDaNome");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(nomeLibreria);
            out.flush();
            libreria = (Librerie)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return libreria;
    }
    /**
    * Metodo utilizzato per l'aggiunta di una valutazione all'interno del DataBase.
    * @param valLibro oggetto di tipo ValutazioniLibro.
    */
    public void aggiungiValutazione(ValutazioniLibro valLibro){
        try {
            Libro l = valLibro.getLibro();
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("AggiungiValutazione");
            out.flush();
            out.writeObject(valLibro);
            out.flush();
            out.writeObject(idLibro);
            out.flush();
        } catch (IOException | ClassNotFoundException  e) {
        }
    }
    /**
    * Metodo per ottenere la valutazione di un libro fatta da un certo utente
    * @param u Oggetto di tipo utente, di cui si vuole visualizzare la Valutazione del libro
    * @param libro Oggetto di tipo libro.
    * @return ValutazioniLibro che contiene tutte le valutazioni del libro fatte dall'utente
    */
    public ValutazioniLibro getValutazioneLibro(Utente u,Libro libro){
        ValutazioniLibro valLibro = null;
        try {
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(libro);
            out.flush();
            int idLibro =(int)in.readObject();
            out.writeObject("GetValutazioneLibro");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            out.writeObject(libro);
            out.flush();
            valLibro = (ValutazioniLibro)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return valLibro;
    }
    /**
    * Metodo per sapere se un certo utente ha aggiunto una valutazione per un libro
    * @param u Oggetto di tipo utente.
    * @param l Oggetto di tipo libro.
    * @return Il metodo returna true se l'utente ha aggiunto una valutazione per il libro, false altrimenti
    */
    
    public boolean esisteValutazioneLibroUtente(Utente u,Libro l){
        boolean esiste = false;
        try {
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("EsisteValutazioneLibroUtente");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            esiste = (boolean)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return esiste;
    }
    /**
    * Metodo che controlla se un utente ha già inserito suggerimenti sul libro l.
    * @param u Oggetto di tipo utente;
    * @param l Oggetto di tipo libro;
    * @return restituisce true se l'utente ha già inserito valutazioni sul libro l, false altrimenti
    */
    public boolean getIfSuggerito(Utente u,Libro l){
        boolean b = false;
        try {
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("getIfSuggerito");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            b = (boolean)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return b;
        }
        return b;
    }
    /**
     * Metodo per l'aggiunta di un nuovo Suggerimento di un libro all'interno del database.
     * @param u Utente che inserisce il suggerimento
     * @param l Libro sorgente di cui si vuole dare dei consigli
     * @param libriList ArrayList di libri consigliati
    */
    public void aggiungiSuggerimentiLibroUtente(Utente u,Libro l,ArrayList<Libro> libriList){
        try {
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibroSorg = (int)in.readObject();
            ArrayList<Integer> alIdLibriSugg = new ArrayList<>();
            for(int i=0;i<libriList.size();i++){
                out.writeObject("GetIdDelLibro");
                out.flush();
                out.writeObject(libriList.get(i));
                out.flush();
                alIdLibriSugg.add((int)in.readObject());
            }
            out.writeObject("aggiungiSuggerimentoLibroUtente");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(idLibroSorg);
            out.flush();
            out.writeObject(alIdLibriSugg);
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
        }
    }
    /**
     * Metodo che resituisce la media delle valutazioni di un certo libro
     * @param l Libro di cui si vuole sapere la media delle valutazioni inserite.
     * @return ArrayList contenente la media di tutti i tipi di valutazioni.
    */
    public ArrayList<Double> getMediaValutazioniLibro(Libro l){
        ArrayList<Double> alMedia = null;
        try {
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("getMediaValutazioniLibro");
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            alMedia = (ArrayList<Double>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return alMedia;
    }
    /**
     * Metodo che restituisce tutti gli utente che hanno aggiunto dei suggerimenti per un certo libro
     * @param l Libro di cui si vuole sapere chi ha aggiunto un suggerimento
     * @return ArrayList contenente gli utenti che hanno aggiunto dei suggerimenti per il libro dato.
    */
    public ArrayList<Utente> getUtentiSuggeritori(Libro l){
        ArrayList<Utente> alUtenti = new ArrayList<>();
        try{
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("getUtentiSuggeritori");
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            alUtenti = (ArrayList<Utente>)in.readObject();
        }catch(IOException | ClassNotFoundException e){
        }
        return alUtenti;
    }
    /**
     * Metodo che restituisce la lista di libri che un utente ha suggerito per un certo libro
     * @param u Oggetto di tipo utente
     * @param l Oggetti di tipo libro
     * @return ArrayList contenente tutti i libri che l'utente ha consigliato per il libro dato.
    */
    public ArrayList<Libro> getLibriSuggeriti(Utente u, Libro l){
        ArrayList<Libro> alLibri = new ArrayList<>();
        try{
            out.writeObject("GetIdDelLibro");
            out.flush();
            out.writeObject(l);
            out.flush();
            int idLibro = (int)in.readObject();
            out.writeObject("getLibriSuggeriti");
            out.flush();
            out.writeObject(u);
            out.flush();
            out.writeObject(idLibro);
            out.flush();
            alLibri =(ArrayList<Libro>)in.readObject();
        }catch(IOException | ClassNotFoundException e){}
        return alLibri;
    }
    /**
     * Metodo per il cambio password di un utente
     * @param cf il codice fiscale dell'utente
     * @param passwordVecchia La password vecchia dell'utente 
     * @param nuovaPassword La password nuova dell'utente
     * @return il metodo returna true se il cambio password è andato a buon fine, false altrimenti 
    */
      public boolean cambiaPassword(String cf, String passwordVecchia, String nuovaPassword) {
        try {
            out.writeObject("controlloPassword");
            out.flush();
            out.writeObject(passwordVecchia);
            out.flush();
            out.writeObject(cf);
            out.flush();
            boolean passwordCorretta = (boolean)in.readObject();
            if(passwordCorretta){
                out.writeObject("cambiaPassword");
                out.flush();
                out.writeObject(cf);
                out.flush();
                out.writeObject(nuovaPassword);
                out.flush();
                return true;
            }else{
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
    /**
     * Metodo per il cambio username di un utente
     * @param cf Il codice fiscale dell'utente.
     * @param nuovoUsername Il nuovo username dell'utente
     * @return il metodo returna true se il cambio è avvenuto con successo, false altrimenti
    */
    public boolean cambiaUsername(String cf, String nuovoUsername) {
        try {
            out.writeObject("controlloNuovoUsername");
            out.flush();
            out.writeObject(nuovoUsername);
            out.flush();
            boolean usernameNonEsistente = (boolean)in.readObject();
            if(usernameNonEsistente){
                out.writeObject("cambiaUsername");
                out.flush();
                out.writeObject(cf);
                out.flush();
                out.writeObject(nuovoUsername);
                out.flush();
                return true;
            }
            else{
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
    /**
     * Metodo per il cambio email di un utente
     * @param cf Il codice fiscale dell'utente
     * @param nuovaEmail La nuova email dell'utente
     * @return Il metodo returna true se il cambio è andato a buon fine, false altrimenti.
    */
    public boolean cambiaEmail(String cf, String nuovaEmail)
    {
        try {
            out.writeObject("controlloEmail");
            out.flush();
            out.writeObject(nuovaEmail);
            out.flush();
            boolean emailNonEsistente = (boolean)in.readObject();
            if(emailNonEsistente)
            {
                out.writeObject("cambiaEmail");
                out.flush();
                out.writeObject(cf);
                out.flush();
                out.writeObject(nuovaEmail);
                out.flush();
                return true;
            }
            else
                return false;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
}