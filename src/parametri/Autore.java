//Simone Cirillo 758727 (VA)
//Simone Diano Domenico 757775 (VA)
//Jacopo Loni 758223 (VA)
//Matteo Corda 757928 (VA)
//Gabriele Schioppa 756634 (VA)

package parametri;

import java.io.Serializable;

//*Classe autore utilizzata per gestire gli autori dei vari libri */
public class Autore implements Serializable{
    private final String nome;
/**
* Costruisce un oggetto Autore con il nome.
* @param nome Il nome dell'autore.
*/
    public Autore(String nome){
        this.nome=nome;
    }
/**
* Restituisce il nome dell'autore.
* @return Il nome dell'autore.
*/
    public String getNome()
    {
        return this.nome;
    }
}