/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.tcp;

import calculadora.Conexao;
import calculadora.Expressao;
import calculadora.Servidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
     
    ConexaoTcp(Socket socket){
        this.socket = socket;
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
            Logger.getLogger(ConexaoTcp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
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
