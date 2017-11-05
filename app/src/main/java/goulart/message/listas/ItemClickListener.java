package goulart.message.listas;
/**
 * Criado por Igor em 13/10/2017.
 */

public interface ItemClickListener {

    /**
     * O método onClick é o método que deverá ser implementado
     *
     * Este método recebe apenas uma posição
     *
     * A posição que ele recebe é a posição do item clicado na lista
     *
     * Esta posição será útil para obter os dados do elemento clicado
     */
    void onClick(int posicao);

}
