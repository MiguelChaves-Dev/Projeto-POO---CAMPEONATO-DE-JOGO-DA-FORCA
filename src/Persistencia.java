import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;

public class Persistencia {
    private XStream xstream;
    private File arquivo = new File("central.xml");

    public Persistencia(){
        xstream = new XStream(new DomDriver());
        xstream.allowTypes (new Class[] {
                CentralDeInformacoes.class,
                Jogador.class,
                Palavra.class,
                Grau.class,
                Dificuldade.class
        });
    }
    public void salvarCentral(CentralDeInformacoes central){
        String xml = xstream.toXML(central);
        try {
            if(!arquivo.exists()) {
                arquivo.createNewFile();
            }
            PrintWriter gravar = new PrintWriter(arquivo);
            gravar.print(xml);
            gravar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CentralDeInformacoes recuperarCentral(){
        if (arquivo.exists()){
            return (CentralDeInformacoes) xstream.fromXML(arquivo);
        }
        return new CentralDeInformacoes();
    }
}
