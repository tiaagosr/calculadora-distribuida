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
    public static float tmpNumero = 0F;
    
    protected abstract void iniciaSocket();
}
