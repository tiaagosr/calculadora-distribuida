/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author tiagosr
 */
public class Expressao {
    public int operador = 0;
    public Float n1 = null, n2 = null;
    public static final int SOMA = 0, SUBTRACAO = 1, MULTIPLICACAO = 2, DIVISAO = 3; //Variáveis estáticas definindo os operadores
    
    Expressao(Float n2, int operador){
        defineExpressao(null, operador, n2);
    }
    
    Expressao(Float n1, int operador, Float n2){
        defineExpressao(n1, operador, n2);
    }
    
    public void defineExpressao(Float n1, int operador, Float n2){
       this.n1 = n1;
       this.operador = operador;
       this.n2 = n2;
    }
    /**
     *
     * @param tmp float - Número a ser usado caso o objeto não possua n1 definido
     * @return float - Resultado da operação
     */
    public float resultado(float tmp){
        float resultado;
        
        if(n1 != null){
            tmp = n1;
        }
        
        switch(operador){
            case SOMA:
                resultado = tmp + n2;
            break;
            case SUBTRACAO:
                resultado = tmp - n2;
            break;
            case MULTIPLICACAO:
                resultado = tmp * n2;
            break;
            case DIVISAO:
                resultado = tmp / n2;
            break;
            default:
                resultado = 0; //Operador não definido
            break;
        }
        
        return resultado;
    }
}
