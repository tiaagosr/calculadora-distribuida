package calculadora;

public class Calculadora {
    
    private String dado ;

    public Calculadora(byte [] dado) {
        this.dado = new String(dado);
    }
    
     public Calculadora(String dado) {
        this.dado = dado;
    }
    
    public Calculadora(String operacao, String valor1, String valor2) {
       this.dado = "0 "+operacao+" "+valor1+" "+valor2 ;
    }

    public String getDado() {
        return dado;
    }  
    
    public char getOperacao() {
        String[] split;
        split = dado.split(" ");
        char[] operacao = split[1].toCharArray();
        return operacao[0];
    }

    public int getvalor1() {
        String[] split;
        split = dado.split(" ");
        int operador = Integer.valueOf(split[2]);
        return operador;
    }

    public int getvalor2() {
        String[] split;
        split = dado.split(" ");
        int operador = Integer.valueOf(split[3]);
        return operador;
    }
    
}
