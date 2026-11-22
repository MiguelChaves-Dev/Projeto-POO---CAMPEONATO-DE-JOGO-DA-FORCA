import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    // Lê o CSV, aplica cada linha na central e devolve o relatório final
    public static String importar(String arquivo, CentralDeInformacoes central) throws IOException {
        Dificuldade[] niveis = Dificuldade.values();
        int aceitas = 0;
        StringBuilder erros = new StringBuilder();
        int rejeitadas = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String motivo = processarLinha(linha, central, niveis);
                if (motivo == null) {
                    aceitas++;
                } else {
                    rejeitadas++;
                    erros.append("Linha ").append(numeroLinha).append(": ")
                            .append(motivo).append("  -> ").append(linha).append("\n");
                }
            }
        }

        return "Linhas aceitas: " + aceitas + "\n"
                + "Linhas rejeitadas: " + rejeitadas + "\n\n" + erros;
    }

    // Devolve null se a linha foi aceita, ou o motivo da rejeição
    private static String processarLinha(String linha, CentralDeInformacoes central, Dificuldade[] niveis) {
        String[] partes = linha.split(",", 4);
        if (partes.length != 4) {
            return "formato inválido (esperado: operação,palavra,dificuldade,dica)";
        }

        int operacao;
        try {
            operacao = Integer.parseInt(partes[0].trim());
        } catch (NumberFormatException e) {
            return "a operação não é um número";
        }

        int numeroDificuldade;
        try {
            numeroDificuldade = Integer.parseInt(partes[2].trim());
        } catch (NumberFormatException e) {
            return "a dificuldade não é um número";
        }

        String texto = partes[1].trim();
        String dica = partes[3].trim();

        if (operacao < 0 || operacao > 2) {
            return "operação deve ser 0, 1 ou 2";
        }
        if (texto.isEmpty()) {
            return "palavra vazia";
        }
        if (numeroDificuldade < 0 || numeroDificuldade >= niveis.length) {
            return "dificuldade deve ser 0, 1 ou 2";
        }
        if (operacao != 2 && dica.isEmpty()) {
            return "dica vazia";
        }

        Palavra existente = central.recuperarPalavra(texto);

        if (operacao == 0) {                       // cadastrar
            if (existente != null) {
                return "palavra já cadastrada";
            }
            central.adicionarPalavra(new Palavra(texto, dica, niveis[numeroDificuldade]));

        } else if (operacao == 1) {                // editar
            if (existente == null) {
                return "palavra não cadastrada, não pode ser editada";
            }
            if (existente.getDificuldade() != niveis[numeroDificuldade]) {
                return "a dificuldade não pode ser editada";
            }
            existente.setDica(dica);

        } else {                                   // excluir
            if (existente == null) {
                return "palavra não cadastrada, não pode ser excluída";
            }
            central.getPalavras().remove(existente);
        }
        return null;
    }
}