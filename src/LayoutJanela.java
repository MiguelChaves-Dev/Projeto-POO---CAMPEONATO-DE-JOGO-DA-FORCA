import javax.swing.*;
import java.awt.*;

public class LayoutJanela extends JFrame {
    //Layout de Janelas Padrão
    public LayoutJanela(String titulo){
        configurar(titulo);
    }
    public void configurar(String titulo){
        setTitle(titulo);
        ImageIcon icone = new ImageIcon("Logo 64.png");
        setIconImage(icone.getImage());
        getContentPane().setPreferredSize(new Dimension(1280,720));
        pack();
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Layout de Janelas estilo pop-up
    public LayoutJanela(String titulo, int width, int height){
        configurar(titulo, width, height);
    }
    public void configurar(String titulo, int width, int height){
        setTitle(titulo);
        ImageIcon icone = new ImageIcon("Logo 64.png");
        setIconImage(icone.getImage());
        getContentPane().setPreferredSize(new Dimension(width,height));
        pack();
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
