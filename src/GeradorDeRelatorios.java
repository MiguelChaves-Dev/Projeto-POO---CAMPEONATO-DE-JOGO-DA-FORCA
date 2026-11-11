import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorDeRelatorios {
	public static void gerarRelatorio(CentralDeInformacoes central) {
	        Document documento = new Document();
	
	        try {
	            PdfWriter.getInstance(documento, new FileOutputStream("relatorio.pdf"));
	            documento.open();
	
	            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	            Font fonteSecao = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	
	            Paragraph titulo = new Paragraph("Relatório da Central de Informações", fonteTitulo);
	            titulo.setAlignment(Element.ALIGN_CENTER);
	            titulo.setSpacingAfter(20);
	            documento.add(titulo);
	
	            documento.add(new Paragraph("Jogadores cadastrados", fonteSecao));
	            documento.add(new Paragraph(" "));
	
	            if (central.getTodosJogadores().isEmpty()) {
	                documento.add(new Paragraph("Nenhum jogador cadastrado."));
	            } else {
	                for (Jogador j : central.getTodosJogadores()) {
	                    documento.add(new Paragraph(
	                        "Login: " + j.getLogin()
	                        + " | E-mail: " + j.getEmail()
							+ " | Nível Admistrativo: " + j.getGrau()));
	                 }
	            }
	
	            documento.add(new Paragraph(" "));
	            documento.add(new Paragraph("Palavras cadastradas", fonteSecao));
	            documento.add(new Paragraph(" "));
	
	            if (central.getPalavras().isEmpty()) {
	                documento.add(new Paragraph("Nenhuma palavra cadastrada."));
	            } else {
	                for (Palavra p : central.getPalavras()) {
	                    documento.add(new Paragraph(
	                        "Palavra: " + p.getPalavra()
	                        + " | Dica: " + p.getDica()
	                        + " | Nível: " + p.getDificuldade()
	                        + " | Cadastro: " + p.getData()));
	                }
	            }
	
	            System.out.println("Relatório gerado com sucesso: relatorio.pdf");
	
	        } catch (DocumentException | java.io.IOException e) {
	            System.out.println("Erro ao gerar o relatório: " + e.getMessage());
	        } finally {
	            if (documento.isOpen()) {
	                documento.close();
	            }
	        }
	}

}