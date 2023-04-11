package isika.cda24.Projet1.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import isika.cda24.Projet1.binaire.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Print extends Application {
    public static final String DESTINATION = "src/main/resources/impressionRecherche.pdf";

    // Création d'un PDF, ouvre l'écriture sur le PDF et écris la liste des stagiaires
    
    public void createPDF(List<StagiaireBinaire> listeRecherche) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(DESTINATION);
        PdfWriter.getInstance(document, fos);

        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font fontCell = new Font(Font.FontFamily.HELVETICA, 10);
        BaseColor blue = new BaseColor(247, 157, 79);
        BaseColor grisClair = new BaseColor(229, 229, 229);

        PdfPTable tableauStagaires = new PdfPTable(new float[] { 3, 3, 2, 2, 2 });

        //Mise en page  du tableau
        tableauStagaires.setWidthPercentage(100f);
        tableauStagaires.getDefaultCell().setPadding(5);
        tableauStagaires.setHeaderRows(1);

        //Remplissage du Header
        tableauStagaires.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableauStagaires.getDefaultCell().setBackgroundColor(blue);

        tableauStagaires.addCell(new Phrase("Nom", fontHeader));
        tableauStagaires.addCell(new Phrase("Prénom", fontHeader));
        tableauStagaires.addCell(new Phrase("Département", fontHeader));
        tableauStagaires.addCell(new Phrase("Promotion", fontHeader));
        tableauStagaires.addCell(new Phrase("Année de formation", fontHeader));

        //Remplissage du contenu
        tableauStagaires.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableauStagaires.getDefaultCell().setBackgroundColor(null);


        int i = 0;
        for(StagiaireBinaire stagiaire : listeRecherche){
            if (i%2 != 0) {
                tableauStagaires.getDefaultCell().setBackgroundColor(blue);
            } else {
                tableauStagaires.getDefaultCell().setBackgroundColor(null);
            }
            tableauStagaires.addCell(new Phrase(stagiaire.getNom(), fontCell));
            tableauStagaires.addCell(new Phrase(stagiaire.getPrenom(), fontCell));
            tableauStagaires.addCell(new Phrase(stagiaire.getDepartement(), fontCell));
            tableauStagaires.addCell(new Phrase(stagiaire.getPromo(), fontCell));
            tableauStagaires.addCell(new Phrase(String.valueOf(stagiaire.getAnnee()), fontCell));

            i++;
        }

        document.open();

        //écriture du document
        document.add(new Paragraph("Liste des stagaires recherchés", fontTitle));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(tableauStagaires);

        document.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File pdfFile = new File(DESTINATION);
        getHostServices().showDocument(pdfFile.toURI().toString());
    }
}
