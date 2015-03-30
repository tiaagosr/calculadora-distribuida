/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.udp;

import calculadora.Cliente;
import calculadora.Conexao;
import calculadora.Expressao;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiagosr
 */
public class ClienteUdp extends Cliente{
    public DatagramSocket socket;
    InetAddress address;
    
    public ClienteUdp(String endereco) {
        try {
            address = InetAddress.getByName(endereco);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.iniciaSocket();
    }
    
    protected void iniciaSocket(){
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(ClienteUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public float novaConta(Float n1, int operacao, Float n2){
        Expressao expressao = new Expressao(n1, operacao, n2);
        
        this.enviaPacote(expressao);
        return this.recebeResultado();
        //return 0F;
    }
    
    protected void enviaPacote(Expressao conta){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            
            oos.writeObject(conta);
            byte[] data = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, this.address, Conexao.porta);
            this.socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected float recebeResultado() {
        float resultado = 0;
        byte[] dados = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(dados, dados.length);
        try {
            socket.receive(pacote);
            ByteArrayInputStream bais = new ByteArrayInputStream(dados);
            DataInputStream dis = new DataInputStream(bais);
            
            resultado = dis.readFloat();
        } catch (IOException ex) {
            Logger.getLogger(ConexaoUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
}
