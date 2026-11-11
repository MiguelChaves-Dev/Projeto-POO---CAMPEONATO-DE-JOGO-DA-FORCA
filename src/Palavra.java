import java.time.LocalDate;
public class Palavra {
    private String palavra;
    private String dica;
    private LocalDate data;
    private Dificuldade dificuldade;

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Palavra(String novaPalavra, String novaDica, Dificuldade novaDificuldade){
        palavra = novaPalavra;
        dica = novaDica;
        dificuldade = novaDificuldade;
    }

    public boolean equals(Palavra p) {
        if (this.getPalavra().equals(p.getPalavra())){
            return true;
        }
        return false;
    }
    public String toString(){
        return palavra + " -  Dica: " + dica;
    }
}
