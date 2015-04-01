/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora.tcp;

import calculadora.Cliente;
import calculadora.Conexao;
import calculadora.Expressao;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiagosr
 */
public class ClienteTcp extends Cliente{
    public Socket socket;
    private InetAddress destino;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public ClienteTcp(String endereco) {
        try {
            destino = InetAddress.getByName(endereco);
            this.iniciaSocket();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteTcp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteTcp.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    protected void iniciaSocket() throws IOException{
            this.socket = new Socket(destino, Conexao.porta);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
    }
    
    protected void initInputStream() throws IOException{
        if(this.ois == null){
            this.ois = new ObjectInputStream(this.socket.getInputStream());
        }
    }
    
    @Override
    public float novaConta(Float n1, int operacao, Float n2){
        float resultado = 0;
        Expressao expressao = new Expressao(n1, operacao, n2);
        
        try {
            this.enviaPacote(expressao);
            resultado = this.recebeResultado();
        } catch (IOException ex) {
            Logger.getLogger(ClienteTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
    
    @Override
    public Expressao requisitaExpressao(){
        Expressao expressao = new Expressao(0F, Expressao.OPERANDOS, 0F);
        
        try {    
            this.enviaPacote(expressao);
            expressao = this.recebeExpressao();
        } catch (IOException ex) {
            Logger.getLogger(ClienteTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return expressao;
    }
    
    protected void enviaPacote(Expressao conta) throws IOException{
        oos.writeObject(conta);
        this.oos.flush();
    }
    
    protected float recebeResultado() throws IOException {
        float resultado = 0;
        
        this.initInputStream();
        resultado = this.ois.readFloat();
        
        return resultado;
    }
    
    protected Expressao recebeExpressao() throws IOException {
        Expressao expressao = null;
        
        try {
            this.initInputStream();
            expressao = (Expressao) this.ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return expressao;
    }
}
