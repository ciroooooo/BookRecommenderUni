package frames;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
import parametri.*;

public class Proxy{
    private InetAddress ip;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Proxy(){
        try{
            this.ip = InetAddress.getByName(null);
            socket = new Socket(ip,1090);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    public void fineComunicazione(){
        try{
            out.writeObject("Fine");
            out.flush();
            socket.close();
        }catch(IOException e){}
    }
    public ArrayList<String> getListaUsernameUtente(){
        ArrayList<String> listaUsername = null;
        try {
            out.writeObject("GetListaUsernameUtente");
            out.flush();
            listaUsername = (ArrayList<String>)in.readObject();
        } catch (Exception e) {
        }
        return listaUsername;
    }
    public ArrayList<String> getListaPasswordUtente(){
        ArrayList<String> listaPassword = null;
        try {
            out.writeObject("GetListaPasswordUtente");
            out.flush();
            listaPassword = (ArrayList)in.readObject();
        } catch (Exception e) {
        }
        return listaPassword;
    }
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
        } catch (Exception e) {
        }
        return cf;
    }
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void aggiungiAutore(String nome){
        try {
            out.writeObject("AggiungiAutore");
            out.flush();
            out.writeObject(nome);
            out.flush();
        } catch (IOException e) {
        }
    }
    public void cercaPerTitolo(String titolo){
        try{
            out.writeObject("");
            out.flush();

        } catch(IOException e){
        }
    }

    public void getUsernameDaCF(String cf){
        try {
            out.writeObject("GetUsernameFromCF");
            out.flush();
            out.writeObject(cf);
            out.flush();
        } catch (Exception e) {
        }
    }
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
        } catch (Exception e) {
            e.printStackTrace();
            return 9;
        }
    }
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
    public void aggiungiLibreria(Librerie libreria){
        try {
            out.writeObject("AggiungiLibreria");
            out.flush();
            out.writeObject(libreria);
            out.flush();
        } catch (IOException e) {
        }
    }
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
}