import Exceptions.CamposVaziosException;

import java.security.SecureRandom;
import java.util.Scanner;

public class RecuperacaoDeSenha {
    private String codigo;

    public String getCodigo(){
        return codigo;
    }

    //Gera um codigo aleatório de 5 digitos
    public void gerarCodigo(){
        SecureRandom random = new SecureRandom();
        String codigoGerado = "";
        for (int i = 0; i < 5; i++){
            int num = random.nextInt(10);
            codigoGerado += (""+num);
        }
        codigo = codigoGerado;
    }

    //Verifica se o código inserido pelo jogador é o mesmo que foi gerado, se sim retorna true, se não retorna false
    public boolean testarCodigo(String codJogador) throws CamposVaziosException {
        if (codJogador.isBlank()) {
            throw new CamposVaziosException();
        }
        if (codigo.equals(codJogador)){
            return true;
        }
        return false;
    }
}
