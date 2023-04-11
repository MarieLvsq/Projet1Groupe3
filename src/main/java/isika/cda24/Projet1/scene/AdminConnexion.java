package isika.cda24.Projet1.scene;

	import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
	import javafx.scene.control.PasswordField;
	import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

	public class AdminConnexion  extends Scene {
		
/* ******************* INSTANCIATION DES BOUTONS ******************** 
* Nous allons ici instancier tous nos boutons de la page Administrateur */

	    private final TextField txtIdentifiant;
	    private final PasswordField txtMotDePasse;
	    
/* ******************* CONSTRUCTION DE NOTRE PAGE ******************** 
* Création de la scene
* modification et améliorations
* ajout du texte et des cases */

		// Constructeur de la scene
	    
	    public AdminConnexion(Stage stage) {
	        super(new VBox(), 500, 400);
	        Label identifiant = new Label("Identifiant");
	        Label motDePasse = new Label("Mot de passe");
	        txtIdentifiant = new TextField();
	        txtMotDePasse = new PasswordField();
	        Button btnValider = new Button("Valider");
	        Button btnAnnuler = new Button("Annuler");
	        HBox hbBoutons = new HBox();

	        //On fixe la taille des champs de texte
	        
	        txtIdentifiant.setPrefWidth(100);
	        txtMotDePasse.setPrefWidth(100);

	        //on met les boutons dans la HBox
	        
	        hbBoutons.getChildren().addAll(btnValider, btnAnnuler);

	        VBox root = ((VBox) this.getRoot());
	        root.setPadding(new Insets(10));
	        root.setSpacing(25);
	        root.setAlignment(Pos.CENTER);

	        GridPane formulaireId = new GridPane();
	        formulaireId.setVgap(20);
	        formulaireId.setHgap(20);
	        formulaireId.setAlignment(Pos.CENTER);

	        //On instancie des objets à afficher sur le panneau
	        //on les stylise et on les positionne:
	        
	        Label labelTitre= new Label("Accès Administrateur");
	        Label erreurIdentification = new Label( "");
	        erreurIdentification.setFont(Font.font("Arial", FontWeight.BOLD, 24));

	        root.getChildren().add(labelTitre);
	        root.getChildren().add(formulaireId);
	        root.getChildren().add(erreurIdentification);
	        root.getChildren().add(hbBoutons);

	        // COULEURS ET GRAPHISMES
	    	// Création de la scène avec un dégradé de couleur bleu canard vers blanc

			Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
			LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
			root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
	        root.setStyle("-fx-font-family: 'Arial'");

	        hbBoutons.setAlignment(Pos.CENTER);
	        hbBoutons.setSpacing(50);

	        formulaireId.addRow(0, identifiant, txtIdentifiant);
	        formulaireId.addRow(1, motDePasse, txtMotDePasse);

	     // **** Implémentation des Actions **** //
	        
	        btnValider.setOnAction(eventAction -> {
	            if (Admins.IDENTIFIANT.equals(txtIdentifiant.getText()) && Admins.MOTDEPASSE.equals(txtMotDePasse.getText())) {
	                Accueil accueilScene = null;
	                try {
	                    accueilScene = new Accueil(stage,true);
	                } catch (FileNotFoundException e) {
	                    throw new RuntimeException(e);
	                }
	                accueilScene.getStylesheets().add("style.css");
	                stage.setScene(accueilScene);

	            } else {
	                erreurIdentification.setText("Il y a une erreur d'identification");
	            }
	        });

	        btnAnnuler.setOnAction(eventAction -> {
	            Accueil accueilScene = null;
	            try {
	                accueilScene = new Accueil(stage,false);
	            } catch (FileNotFoundException e) {
	                throw new RuntimeException(e);
	            }
	            accueilScene.getStylesheets().add("style.css");
	            stage.setScene(accueilScene);
	        });
	    }
	
	}