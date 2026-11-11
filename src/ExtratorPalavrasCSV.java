import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ExtratorPalavrasCSV {
    public static ArrayList<Palavra> extrairPalavras(String arquivo){
        ArrayList<Palavra> palavras = new ArrayList<Palavra>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))){
            String linha;
            while ((linha = leitor.readLine()) != null){
                if (linha.trim().isEmpty()){
                    continue;
                }
                String[] partes = linha.split(",",3);
                if(partes.length != 3){
                    return null;
                }

                String texto = partes[0].trim();
                String dica = partes[2].trim();
                if(texto.isEmpty() || dica.isEmpty()){
                    return null;
                }
                int numeroDificuldade;
                try {
                    numeroDificuldade = Integer.valueOf(partes[1].trim());
                } catch (NumberFormatException e){
                    return null;
                }

                Dificuldade[] niveis = Dificuldade.values();
                if(numeroDificuldade<0 || numeroDificuldade>= niveis.length){
                    return null;
                }
                palavras.add(new Palavra(texto, dica, niveis[numeroDificuldade]));
            }
        } catch (IOException e) {
            return null;
        }
        return palavras;
    }
}
