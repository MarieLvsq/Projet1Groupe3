package isika.cda24.Projet1.scene;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import isika.cda24.Projet1.binaire.StagiaireBinaire;
import isika.cda24.Projet1.binaire.ArbreBinaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class SceneModif extends Scene {

	/* ******************* INSTANCIATION DES BOUTONS ******************** 
	 * Nous allons ici instancier tous nos boutons de la scène modifié */

	// On instancie un bouton pour valider :
    private final Button btnValider;
    
    // On instancie un bouton pour annuler :
    private final Button btnAnnuler;
    
    // On instancie les TextFields
    private final TextField txtNom;
    private final TextField txtPrenom;
    private final TextField txtPromo;
    private final TextField txtDepartement;
    private final TextField txtAnnee;
    
    // On instancie un arbre :
    private final ArbreBinaire arbre;

    /* ******************* CONSTRUCTION DE NOTRE PAGE ******************** 
	 * Création de la scene
	 * modification et améliorations
	 * ajout du texte et des cases */

	// Constructeur de la scene
    public SceneModif(Stage stage, StagiaireBinaire stagiaireAModifier, StagiaireBinaire criteres) throws FileNotFoundException {
        super(new VBox(), 1000, 600);
        arbre = new ArbreBinaire();

        // On récupère la racine de la scene et on la détermine comme BorderPane:
        // On déclare un attribut
        VBox root = (VBox) this.getRoot();
        
		// Création de la scène avec un dégradé de couleur bleu canard vers blanc

		Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
		
        // On déclare une GridPane pour y placer le formulaire:
        GridPane formulaire = new GridPane();

        Label labelTitre = new Label("Modification du stagiaire");

        // On instancie les labels, champs de texte et boutons
        Label nom = new Label("Nom ");
        txtNom = new TextField();
        txtNom.setText(stagiaireAModifier.getNom().trim());
        txtNom.setPrefWidth(100);

        Label prenom = new Label("Prénom ");
        txtPrenom = new TextField();
        txtPrenom.setText(stagiaireAModifier.getPrenom().trim());
        txtPrenom.setPrefWidth(100);

        Label promo = new Label("Promotion ");
        txtPromo = new TextField();
        txtPromo.setText(stagiaireAModifier.getPromo().trim());
        txtPromo.setPrefWidth(100);

        Label departement = new Label("Lieu d'habitation ");
        txtDepartement = new TextField();
        txtDepartement.setText(stagiaireAModifier.getDepartement().trim());
        txtDepartement.setPrefWidth(100);

        Label annee = new Label("Année de formation ");
        txtAnnee = new TextField();
        txtAnnee.setText("" + stagiaireAModifier.getAnnee());
        txtAnnee.setPrefWidth(100);

        // On instancie une HBox pour y placer la GridPane et 2 HBox pour y placer 
        // les 4 boutons que l'on va instancier juste après :

        // ***************************** INSTANCIATION DU FORMULAIRE ************************
        
        // Création de la Hbox formulaire
        
        HBox hBoxFormulaire = new HBox();
        hBoxFormulaire.getChildren().add(formulaire); // on ajoute la hBox au GridePane
        hBoxFormulaire.setAlignment(Pos.CENTER);

        // Création de la Hbox qui contient les deux boutons
        HBox hBoxValiderAnnuler = new HBox();

        hBoxValiderAnnuler.setAlignment(Pos.CENTER);
        hBoxValiderAnnuler.setPadding(new Insets(10));
        hBoxValiderAnnuler.setSpacing(150);

        // On instancie un bouton pour rechercher un stagiaire:
        btnValider = new Button("Valider");// lance méthode modifier
        //actionBtnValider(stagiaireAModifier,criteres, stage);

        // On instancie un bouton pour ajouter un stagiaire:
        btnAnnuler = new Button("Annuler");
        actionBtnAnnuler(stage, criteres);

        // On relie les boutons aux HBox:
        hBoxValiderAnnuler.getChildren().addAll(btnValider, btnAnnuler);

        // On intègre le formulaire au GridPane:
        formulaire.addRow(0, nom, txtNom);
        formulaire.addRow(1, prenom, txtPrenom);
        formulaire.addRow(2, promo, txtPromo);
        formulaire.addRow(3, departement, txtDepartement);
        formulaire.addRow(4, annee, txtAnnee);
        formulaire.setHgap(25);
        formulaire.setVgap(15);

        formulaire.getColumnConstraints().add(new ColumnConstraints(300)); // column 0 is 100 wide

        // On ajoute les éléments dans la VBox :
        root.getChildren().addAll(labelTitre, hBoxFormulaire, hBoxValiderAnnuler);

        // Mise en page des éléments
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setSpacing(30);
        root.setStyle(("-fx-font-family: 'Arial'"));
        
        actionBtnValider(stagiaireAModifier, criteres, stage);
        actionBtnAnnuler(stage, criteres);

    }
 
    /* ******************* IMPLEMENTATION DES METHODES ******************** 
	 * Valider et Annuler */
    
    
    private void actionBtnValider(StagiaireBinaire stagiaireAModifier, StagiaireBinaire criteres, Stage stage) {
        btnValider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int anneeInt = 0;

                    if(!txtAnnee.getText().trim().equals("")) {
                        try {
                            anneeInt = Integer.parseInt(txtAnnee.getText().trim());
                        } catch (NumberFormatException e) {
                            txtAnnee.clear();
                        }
                    }

                    StagiaireBinaire stagiaireAJour = new StagiaireBinaire(txtNom.getText(), txtPrenom.getText(),
                            txtDepartement.getText(), txtPromo.getText(), txtAnnee.getText());
                  
                    RandomAccessFile raf = new RandomAccessFile("STAGIAIRES.DON", "rw");                    
                    arbre.modifierStagiaire(stagiaireAModifier.getNom(), stagiaireAJour, raf);

                    List<StagiaireBinaire> listeDeStagiaire = new ArrayList<>();
                    
                    TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stage, listeDeStagiaire, criteres, true);
                    tableStagiaireScene.getStylesheets().add("style.css");
                    stage.setScene(tableStagiaireScene);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void actionBtnAnnuler(Stage stage, StagiaireBinaire criteres) {
        btnAnnuler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<StagiaireBinaire> listeDeStagiaire = new ArrayList<>();
				arbre.affichageInfixeNoeud(listeDeStagiaire);

				TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stage, listeDeStagiaire, criteres, true);
				tableStagiaireScene.getStylesheets().add("style.css");
				stage.setScene(tableStagiaireScene);

            }
        });
    }

}
