/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.udp;

import calculadora.Conexao;
import java.io.ByteArrayInputStream;
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
    private int tipoMensagem;
     
    ConexaoUdp(ServidorUdp servidor, DatagramPacket pacote, byte[] dados, int tipoMensagem){
        this.pacote = pacote;
        this.dados = dados;
        this.socket = servidor.socket;
        this.tipoMensagem = tipoMensagem;
    }

    @Override
    public void run() {
        
    }

    @Override
    protected void recebePacote() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(dados);
            ObjectInputStream ois = new ObjectInputStream(bais);
            try {
                switch(tipoMensagem){
                case ServidorUdp.MENSAGEM_NUMERO:
                    this.tmpMsg = (tipoMensagem) ois.readObject();
                break;
                }
                if(this.tmpMsg != null){
                    this.server.gui.insertLogTable(this.request.getAddress().getHostName(), tmpMsg.getRequestId());
                    this.sendResponse(tmpMsg);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
