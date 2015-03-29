/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author udesc
 */
public abstract class Conexao implements Runnable{
    public static int porta = 2020;
    
    protected abstract void processaPacote();
    protected abstract void enviaPacote(float resultado);
}
