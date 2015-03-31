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
public abstract class Cliente {
    Expressao tmpExpressao;
    
    public abstract float novaConta(Float n1, int operacao, Float n2);
    public abstract Expressao requisitaExpressao();
}
