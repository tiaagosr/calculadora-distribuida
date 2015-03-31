/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.io.Serializable;

/**
 *
 * @author tiagosr
 */
public class Expressao implements Serializable{
    public int operador = 0;
    public float n1 = 0, n2 = 0;
    public static final int SOMA = 0, SUBTRACAO = 1, MULTIPLICACAO = 2, DIVISAO = 3, OPERANDOS = 4; //Variáveis estáticas definindo os operadores
    
    public Expressao(Float n1, int operador, Float n2){
        defineExpressao(n1, operador, n2);
    }
    
    private void defineExpressao(Float n1, int operador, Float n2){
       this.n1 = n1;
       this.operador = operador;
       this.n2 = n2;
    }
    
    public boolean requisitaOperando(){
        return (this.operador == Expressao.OPERANDOS);
    }

    public float resultado(){
        float resultado;
        
        switch(operador){
            case SOMA:
                resultado = n1 + n2;
            break;
            case SUBTRACAO:
                resultado = n1 - n2;
            break;
            case MULTIPLICACAO:
                resultado = n1 * n2;
            break;
            case DIVISAO:
                resultado = n1 / n2;
            break;
            default:
                resultado = 0; //Operador não definido
            break;
        }
        
        return resultado;
    }
    
    @Override
    public String toString(){
        return n1+" "+operador+" "+n2;
    }
}
