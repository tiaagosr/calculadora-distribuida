/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import calculadora.gui.Inicial;
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
        Main.InicializaTcp();
        
        Thread tmpThread = new Thread(Main.servidor);
        tmpThread.start();

        Inicial janela = new Inicial();
        janela.setVisible(true);
    }
    
    public static void InicializaTcp(){
        Main.servidor = new ServidorTcp(Main.destino);
    }
    
    public static void InicializaUdp(){
        Main.servidor = new ServidorUdp(Main.destino);
    }
}
