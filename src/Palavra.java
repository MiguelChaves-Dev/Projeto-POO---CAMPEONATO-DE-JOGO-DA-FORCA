import java.time.LocalDate;
public class Palavra {
    private String palavra;
    private String dica;
    private LocalDate data;
    private Dificuldade dificuldade;

    public Palavra(String novaPalavra, String novaDica, Dificuldade novaDificuldade){
        palavra = novaPalavra;
        dica = novaDica;
        dificuldade = novaDificuldade;
    }

    //Retorna a palavra
    public String getPalavra() {
        return palavra;
    }

    //Define qual será a palavra
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    //Retorna a dica referente a palavra
    public String getDica() {
        return dica;
    }

    //Define a dica referente a palavra
    public void setDica(String dica) {
        this.dica = dica;
    }

    //Retorna a data em que a palavra foi cadastrada
    public LocalDate getData() {
        return data;
    }

    //Define a data que a palavra foi cadastrada
    public void setData(LocalDate data) {
        this.data = data;
    }

    //Retorna a dificuldade da palavra(FACIL, MEDIO, DIFICIL) <- (São opções do Enum: Dificuldade)
    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    //Define a dificuldade da palavra
    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Palavra outra = (Palavra) obj;
        if (this.palavra == null || outra.palavra == null) {
            return false;
        }
        return this.palavra.equalsIgnoreCase(outra.palavra);
    }
    public String toString(){
        return palavra + " -  Dica: " + dica;
    }
}
