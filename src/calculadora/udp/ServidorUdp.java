package calculadora.udp;

import calculadora.Servidor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

	
/**
 *
 * @author tiagosr
 */
public class ServidorUdp extends Servidor{
    public DatagramSocket socket;
    
    public ServidorUdp() { 
        conexoes = new ArrayList<>();
        this.connect();
    }
    
    protected void connect(){
        try {
            this.socket = new DatagramSocket(this.porta);
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void getNextConnection(){
        try {
            byte[] incomingData = new byte[1024];
            
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            
            ConexaoUdp novaConexao = new ConexaoUdp(this, incomingPacket, incomingData);
            this.conexoes.add(novaConexao);
            
            Thread tmpThread = new Thread(novaConexao);   
            tmpThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        while(true){
            this.getNextConnection();
        }
    }	
}