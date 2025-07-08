//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package bookrecommender;
/**
 * Classe Main per lo start del programma.
 * Qua avviene l'inizializzazione della classe proxy per la comunicazione con il database.
 */
public class Main {
    /** 
     * Metodo main per l'inizio del programma; visualizza il frame di login.
    */ 
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        LgMainFrame myFrame = new LgMainFrame(proxy);
        myFrame.initialize();
    }
}