/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.tcp;

import calculadora.Conexao;
import calculadora.Expressao;
import calculadora.Servidor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author udesc
 */
public class ConexaoTcp extends Conexao{
    private Socket socket;
    private Expressao tmpMsg;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private InetAddress peer;
     
    ConexaoTcp(Socket socket, InetAddress peer){
        this.socket = socket;
        this.peer = peer;
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                recebePacote();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    protected void recebePacote() {
        try {
            this.tmpMsg = (Expressao) ois.readObject();
            
            if(this.tmpMsg != null){
                switch(this.tmpMsg.operador){
                    case Expressao.OPERANDOS: 
                        System.out.println("Operandos requisitados: "+Servidor.tmpExpressao.toString());
                        this.enviaPacote(Servidor.tmpExpressao);
                    break;
                        
                    case Expressao.SINCRONIZACAO:
                        Servidor.tmpExpressao = this.tmpMsg;
                    break;
                        
                    default: //Enviou expressao a ser resolvida para o servidor
                        System.out.println("Conta: "+tmpMsg.n1+" "+tmpMsg.operador+" "+tmpMsg.n2);
                        float resultadoExpressao = tmpMsg.resultado();
                        Servidor.tmpExpressao = this.tmpMsg;
                        this.sincroniza();
                        this.enviaPacote(resultadoExpressao);
                    break;
                }
                
            }else{
                System.out.println("Pacote corrompido");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void sincroniza(){
        try {
            DatagramSocket socket = new DatagramSocket();
            int operacaoTmp = this.tmpMsg.operador;
            
            this.tmpMsg.operador = Expressao.SINCRONIZACAO;
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            
            oos.writeObject(this.tmpMsg);
            byte[] data = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, this.peer, Conexao.porta);
            socket.send(sendPacket);
            
            this.tmpMsg.operador = operacaoTmp;
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void enviaPacote(float resultado){
        try {
            this.oos.writeFloat(resultado);
            this.oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void enviaPacote(Expressao expressao){
        try {
            this.oos.writeObject(expressao);
            this.oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void initInputStream() throws IOException{
        if(this.ois == null){
            this.ois = new ObjectInputStream(this.socket.getInputStream());
        }
    }
   
}
