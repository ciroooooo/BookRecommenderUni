package Frames;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
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



}