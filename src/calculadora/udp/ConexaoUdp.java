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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                
                if(!this.tmpMsg.requisitaOperando()){ //Enviou expressao a ser resolvida para o servidor
                    System.out.println("Conta: "+tmpMsg.n1+" "+tmpMsg.operador+" "+tmpMsg.n2);
                    float resultadoExpressao = tmpMsg.resultado();
                    Servidor.tmpExpressao = this.tmpMsg;
                    this.enviaPacote(resultadoExpressao);
                }else{ //Enviou expressao requisitando operandos temporarios do servidor
                    System.out.println("Operandos requisitados: "+Servidor.tmpExpressao.toString());
                    this.enviaPacote(Servidor.tmpExpressao);
                }
                
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
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            
            oos.writeFloat(resultado);
            byte[] data = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, pacote.getAddress(), pacote.getPort());
            this.socket.send(sendPacket);
            
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void enviaPacote(Expressao expressao){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            
            oos.writeObject(expressao);
            byte[] data = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, pacote.getAddress(), pacote.getPort());
            this.socket.send(sendPacket);
            
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
