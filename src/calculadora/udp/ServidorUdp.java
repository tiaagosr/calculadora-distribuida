package calculadora.udp;

import calculadora.Conexao;
import calculadora.Servidor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

	
/**
 *
 * @author tiagosr
 */
public class ServidorUdp extends Servidor{
    public DatagramSocket socket;
    
    public ServidorUdp() { 
        this.iniciaSocket();
    }
    
    protected void iniciaSocket(){
        try {
            this.socket = new DatagramSocket(Conexao.porta);
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void recebePacote(){
        try {
            byte[] dados = new byte[1024];
            
            DatagramPacket pacote = new DatagramPacket(dados, dados.length);
            socket.receive(pacote);
            
            ConexaoUdp novaConexao = new ConexaoUdp(this, pacote, dados);
            System.out.printf("Novo Pacote de %s\n", pacote.getAddress().getHostAddress());
            
            Thread tmpThread = new Thread(novaConexao);   
            tmpThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        while(true){
            this.recebePacote();
        }
    }	
}