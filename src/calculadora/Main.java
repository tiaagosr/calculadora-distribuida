/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import calculadora.udp.ClienteUdp;
import calculadora.udp.ServidorUdp;
/**
 *
 * @author udesc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Servidor servidor = new ServidorUdp();
        Thread tmpThread = new Thread(servidor);
        
        Cliente cliente = new ClienteUdp("localhost");
        tmpThread.start();
        float resultado;
        
        resultado = cliente.novaConta(5.5F, Expressao.SOMA, 5.5F);
        System.out.println("Resultado:"+resultado);
        
        resultado = cliente.novaConta(null, Expressao.MULTIPLICACAO, 2F);
        System.out.println("Resultado:"+resultado);
        
        resultado = cliente.novaConta(null, Expressao.SUBTRACAO, 3F);
        System.out.println("Resultado:"+resultado);
    }
}
