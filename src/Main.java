import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Persistencia persistencia = new Persistencia();
        CentralDeInformacoes central = persistencia.recuperarCentral();
        boolean continua = false;
        while (!continua){
            System.out.print("1-Novo Jogador" +
                    "\n2-Listar todos os Jogadores" +
                    "\n3-Exibir Informações de um Jogador Específico" +
                    "\n4-Salvar Palavras a partir de um arquivo CSV"+
                    "\n5-Listar todas as Palavras salvas na Central"+
                    "\n6-Cadastrar Palavra manualmente" +
                    "\n7-Gerar relatorio em PDF" +
                    "\n8-Enviar mensagem para todos os jogadores" +
                    "\nS-Sair"+
                    "\nOpção:");
            String op = input.nextLine();
            switch (op){
                case "1":
                    System.out.print("Insira o nome do Jogador: ");
                    String nome = input.nextLine();
                    System.out.print("Insira a senha do Jogador:");
                    String senha = input.nextLine();
                    System.out.print("Insira o Email do Jogador: ");
                    String email = input.nextLine();
                    Jogador novoJogador = new Jogador(nome, email, senha, Grau.USUARIO);
                    if (central.adicionarJogador(novoJogador)){
                        persistencia.salvarCentral(central);
                        System.out.println("Jogador adicionado com sucesso!");
                    } else {
                        System.out.println("Jogador com Email repetido!");
                    }
                    break;
                case "2":
                    central = persistencia.recuperarCentral();
                    for (Jogador j : central.getTodosJogadores()){
                        System.out.println(j.toString());
                    }
                    break;
                case "3":
                    central = persistencia.recuperarCentral();
                    System.out.println("Insira o Email do Jogador que você deseja procurar: ");
                    String emailProcura =  input.nextLine();
                    boolean encontrado = false;
                    for (Jogador j : central.getTodosJogadores()){
                        if (emailProcura.equals(j.getEmail())){
                            System.out.println("Nome: " + j.getLogin());
                            System.out.println("Email: " + j.getEmail());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado){
                        System.out.println("Jogador não encontrado!");
                    }
                    break;
                case "4":
                    System.out.print("Insira o nome do arquivo CSV: ");
                    String nomeArquivo = input.nextLine();
                    ArrayList<Palavra> extraidas = ExtratorPalavrasCSV.extrairPalavras(nomeArquivo);
                    if (extraidas == null){
                        System.out.println("Arquivo inválido ou fora do formato esperado!");
                    } else {
                        int adicionadas = 0;
                        for (Palavra item : extraidas){
                            if (central.adicionarPalavra(item)){
                                adicionadas++;
                            }
                        }
                        persistencia.salvarCentral(central);
                        System.out.println(adicionadas + " palavra(s) salva(s)."
                                + (extraidas.size() - adicionadas) + " ignorada(s) por já existirem.");
                    }
                    break;
                case "5":
                    if(central.getPalavras().isEmpty()){
                        System.out.println("Nenhuma palavra salva na central.");
                    } else {
                        for (Palavra item : central.getPalavras()){
                            System.out.println(item);
                        }
                    }
                    break;
                case "6":
                    System.out.print("Insira a Palavra: ");
                    String palavra = input.nextLine();
                    System.out.print("Insira uma dica relacionada a esta palavra: ");
                    String dica = input.nextLine();
                    System.out.print("Insira a dificuldade dessa palavra " +
                            "\n0-Fácil" +
                            "\n1-Médio" +
                            "\n2-Difícil: ");
                    int dificuldade;
                    try{
                        dificuldade = Integer.valueOf(input.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Dificuldade Inválida!");
                        break;
                    }

                    Dificuldade dificuldadePalavra;
                    if (dificuldade == 0){
                        dificuldadePalavra = Dificuldade.FACIL;
                    } else if (dificuldade == 1){
                        dificuldadePalavra = Dificuldade.MEDIO;
                    } else if(dificuldade == 2) {
                        dificuldadePalavra = Dificuldade.DIFICIL;
                    } else {
                        System.out.println("Dificuldade Inválida!");
                        break;
                    }
                    Palavra p = new Palavra(palavra, dica, dificuldadePalavra);
                    if (central.adicionarPalavra(p)){
                        persistencia.salvarCentral(central);
                        System.out.println("Palavra adicionada com sucesso!");
                    } else {
                        System.out.println("Palavra repetida!");
                    }
                    break;
                case "7":
                    GeradorDeRelatorios.gerarRelatorio(central);
                    break;
                case "8":
                    System.out.print("Assunto: ");
                    String assunto = input.nextLine();
                    System.out.print("Mensagem: ");
                    String mensagem = input.nextLine();

                    for (Jogador j : central.getTodosJogadores()) {
                        Mensageiro.enviarMensagem(j.getEmail(), assunto, mensagem);
                    }
                    break;

                case "S":
                    System.out.println("Encerrando...");
                    continua = true;
            }
        }
    }
}