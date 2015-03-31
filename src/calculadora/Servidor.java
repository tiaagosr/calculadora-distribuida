/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author udesc
 */
public abstract class Servidor implements Runnable{
    public static Expressao tmpExpressao = null;
    
    protected abstract void iniciaSocket();
}
