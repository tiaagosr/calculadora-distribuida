/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.udp;

import calculadora.Conexao;
import calculadora.Expressao;
import calculadora.Servidor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author udesc
 */
public class ConexaoUdp extends Conexao{
    private DatagramPacket pacote;
    private DatagramSocket socket;
    private byte[] dados;
    private Expressao tmpMsg;
    
     
    ConexaoUdp(DatagramSocket socket, DatagramPacket pacote, byte[] dados){
        this.pacote = pacote;
        this.dados = dados;
        this.socket = socket;
    }

    @Override
    public void run() {
        processaPacote();
    }

    protected void processaPacote() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(dados);

            ObjectInputStream ois = new ObjectInputStream(bais);
            this.tmpMsg = (Expressao) ois.readObject();
            
            if(this.tmpMsg != null){
                float resultadoExpressao = tmpMsg.resultado(Servidor.tmpNumero);
                System.out.println("Conta: "+tmpMsg.n1+" "+tmpMsg.operador+" "+tmpMsg.n2);
                Servidor.tmpNumero = resultadoExpressao;
                this.enviaPacote(resultadoExpressao);
            }else{
                System.out.println("Pacote corrompido");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void enviaPacote(float resultado){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            
            dos.writeFloat(resultado);
            byte[] data = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, pacote.getAddress(), pacote.getPort());
            this.socket.send(sendPacket);
            
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
