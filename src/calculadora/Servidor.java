/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.ArrayList;

/**
 *
 * @author udesc
 */
public abstract class Servidor implements Runnable{
    public int porta = 2020;
    protected ArrayList<Conexao> conexoes;
    
    public static final int MENSAGEM_NUMERO = 0;
    public static final int MENSAGEM_OPERADOR = 1;
}
