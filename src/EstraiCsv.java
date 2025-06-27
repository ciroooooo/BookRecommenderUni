//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class EstraiCsv {
    /**
     * Verifica se un file esiste; se non esiste, tenta di crearlo.
     *
     * @param file Il file da verificare o creare.
     * @throws IOException Se si verifica un errore durante la creazione del file.
     */
    public static void FileEsiste(File file){
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }  

    /**
     * Converte il nome di un mese in un numero intero corrispondente.
     *
     * @param s Il nome completo del mese in lingua inglese.
     * @return Il numero del mese (da 1 a 12).
     * @throws NumberFormatException Se il nome del mese non è valido.
     */
    public static int getNumDiMese(String s){
        switch(s){
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: throw new NumberFormatException("questo non è un mese");
        }
    }

    /**
     * Legge una riga da un file CSV e restituisce un ArrayList formato da informazioni separate.
     *
     * @param riga La riga di testo da analizzare.
     * @return Un ArrayList contenente i campi della riga separati.
     */
    public static ArrayList<String> leggiRiga(String riga){
        ArrayList<String> listaRiga = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean continua = false;
        String linea = riga;
        for(int i=0;i<linea.length();i++){
            char c=linea.charAt(i);
            if(c<32 || c>126){
                c=' ';  
            }
            if(c=='\"'){
                continua=!continua;
            }else if(c==',' && !continua){
                listaRiga.add(sb.toString().trim());
                sb.setLength(0);
            }else{
                sb.append(c);
            }
        }
        listaRiga.add(sb.toString().trim());
        sb.setLength(0);
        for(int i=0;i<listaRiga.size();i++){
            String campo=listaRiga.get(i).replaceFirst("By", "")
            .replace("Monday", "")
            .replace("Tuesday", "")
            .replace("Wednesday", "")
            .replace("Thursday", "")
            .replace("Friday", "")
            .replace("Saturday", "")
            .replace("Sunday", "");
            campo = campo.replaceFirst("^,\\s*", "");
            listaRiga.set(i,campo);
        }
        return listaRiga;
    }

    /**
     * Converte una stringa in un valore numerico (double), rappresentando un prezzo.
     *
     * @param s La stringa da cui estrarre il prezzo.
     * @return Il valore numerico rappresentato dalla stringa.
     * @throws NumberFormatException Se la stringa non contiene un valore numerico valido.
     */
    public static double prezzoDaString(String s){
        boolean continua = true;
        String numero = "";
        double n = 0;
        for (int i = 0; i < s.length() && continua; i++) {
            if (Character.isDigit(s.charAt(i))) {
                for (int k = i; k < s.length(); k++) { 
                    char c=s.charAt(k);
                    if(c==','){
                        continue;
                    }
                    numero = numero + c;
                }
                continua = false;
            }
        }
        if (s.length() > 0) {
            n = Double.parseDouble(numero);
        }
        return n;
    }

    /**
     * Estrae una lista di elementi da una parte specifica di listaRiga, rappresentando una data.
     *
     * @param listaRiga L'ArrayList di stringhe contenente i dati da analizzare.
     * @return Un ArrayList di stringhe rappresentanti i componenti della data, normalizzati.
     */
    public static ArrayList<String> getALDate(ArrayList<String> listaRiga){
        String[] arrayData = listaRiga.get(5).split(" ");
        ArrayList<String> alk = new ArrayList<>();
        for (int i = 0; i < arrayData.length; i++) {
            alk.add(arrayData[i].replace(",", ""));
        }
        return alk;
    }

    /**
     * Metodo principale per l'estrazione dei dati da un file CSV e la loro serializzazione in un file.
     *
     * @param args Gli argomenti della riga di comando.
     */
    public static void main(String[] args){
        Proxy proxy = new Proxy();
        ArrayList<Libro> alLibri = new ArrayList<>();
        ArrayList<String> listaRiga;
        File file = new File("file\\Libri.txt");
        FileEsiste(file);
        File fileCsv = new File("BooksDataset.csv");
        FileEsiste(fileCsv);
        try {
            FileReader fr = new FileReader(fileCsv);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String riga;
            while ((riga=br.readLine())!=null) {
                listaRiga = leggiRiga(riga);
                if(listaRiga.isEmpty() || listaRiga.size()<7 || listaRiga.size()>7){
                    continue;
                }
                String prezzoStringa = listaRiga.get(6);
                double n = prezzoDaString(prezzoStringa);
                ArrayList<String> dataPub = getALDate(listaRiga);
                int numeroMese = getNumDiMese(dataPub.get(0));
                LocalDate data;
                if(dataPub.size()<3){
                    data = LocalDate.of(Integer.parseInt(dataPub.get(1)), numeroMese,1);
                }else{
                    data = LocalDate.of(Integer.parseInt(dataPub.get(2)), numeroMese, Integer.parseInt(dataPub.get(1)));
                }
                proxy.aggiungiAutore(listaRiga.get(1));
                proxy.aggiungiLibro(listaRiga.get(0), listaRiga.get(2), listaRiga.get(3), listaRiga.get(4), data, n, listaRiga.get(1));
                listaRiga.clear();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(alLibri);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
