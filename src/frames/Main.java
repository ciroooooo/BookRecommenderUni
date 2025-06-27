//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package frames;

public class Main {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        LgMainFrame myFrame = new LgMainFrame(proxy);
        myFrame.initialize();
    }
}