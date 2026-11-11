import javax.swing.*;
import java.awt.*;

public class LayoutJanela extends JFrame {
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
}
