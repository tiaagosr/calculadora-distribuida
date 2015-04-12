/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import calculadora.tcp.ClienteTcp;
import calculadora.tcp.ServidorTcp;
import calculadora.udp.ClienteUdp;
import calculadora.udp.ServidorUdp;
/**
 *
 * @author udesc
 */
public class Main {
    public static Servidor servidor;
    public static Cliente cliente;
    public static String destino = "localhost";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main.InicializaUdp();
        
        Thread tmpThread = new Thread(Main.servidor);
        tmpThread.start();
        float resultado;
        
        resultado = cliente.novaConta(5.5F, Expressao.SOMA, 5.5F);
        System.out.println("Resultado:"+resultado);
        
        Expressao tmp = cliente.requisitaExpressao();
        System.out.println("Expressao:"+tmp.toString());
    }
    
    public static void InicializaTcp(){
        Main.servidor = new ServidorTcp(Main.destino);
        Main.cliente = new ClienteTcp(Main.destino);
    }
    
    public static void InicializaUdp(){
        Main.servidor = new ServidorUdp(Main.destino);
        Main.cliente = new ClienteUdp(Main.destino);
    }
}
