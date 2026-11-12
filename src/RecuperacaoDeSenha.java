import Exceptions.CamposVaziosException;

import java.security.SecureRandom;
import java.util.Scanner;

public class RecuperacaoDeSenha {
    private String codigo;

    public String getCodigo(){
        return codigo;
    }

    public void gerarCodigo(){
        SecureRandom random = new SecureRandom();
        String codigoGerado = "";
        for (int i = 0; i < 5; i++){
            int num = random.nextInt(10);
            codigoGerado += (""+num);
        }
        codigo = codigoGerado;
    }
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
