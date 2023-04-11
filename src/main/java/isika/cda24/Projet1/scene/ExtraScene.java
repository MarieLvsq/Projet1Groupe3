package isika.cda24.Projet1.scene;

import isika.cda24.Projet1.binaire.*;
import isika.cda24.Projet1.binaire.ArbreBinaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtraScene extends Scene {

	/*
	 * ******************* INSTANCIATION DES BOUTONS ******************** 
	 * Nous allons ici instancier nos boutons de l'extra scene
	 */

	private final Button boutonValider;
	private final Button boutonAnnuler;
	private final ArbreBinaire arbre;

	/*
	 * ******************* CONSTRUCTION DE NOTRE PAGE ******************** 
	 * Création de la scene modification et améliorations ajout du texte et des cases
	 */

	// Constructeur de la scene

	public ExtraScene(Stage stageExtra, Stage stageTableau, StagiaireBinaire stagiaireASupprimer,
			StagiaireBinaire criteres) throws FileNotFoundException {

		super(new BorderPane(), 500, 230);
		arbre = new ArbreBinaire();

		// Attributs de la page
		
		BorderPane root = (BorderPane) this.getRoot();
		root.setPadding(new Insets(10, 10, 10, 10));
		
		// Création de la scène avec un dégradé de couleur bleu canard vers blanc

		Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));


		// ---------------COMMENTAIRES--------------------- 
		
		
		HBox hb = new HBox();
		boutonValider = new Button("Valider");
		boutonAnnuler = new Button("Annuler");
		Label texte = new Label("Vous allez supprimer le stagiaire : \n\n" + stagiaireASupprimer.getNom() + "\n"
				+ stagiaireASupprimer.getPrenom() + "\n" + stagiaireASupprimer.getDepartement() + "\n"
				+ stagiaireASupprimer.getPromo() + "\n" + stagiaireASupprimer.getAnnee()
				+ "                 " + "\n \n Voulez-vous continuer ?");
		texte.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(100);

		// Affichage centré du texte sur plusieurs lignes
		
		texte.setWrapText(true);
		texte.setTextAlignment(TextAlignment.CENTER);

		// POLICE MAC OS
		root.setStyle("-fx-font-family: 'Arial'");

		// Ajout des boutons à la HBox
		
		hb.getChildren().addAll(boutonValider, boutonAnnuler);
		hb.setSpacing(50);

		// Ajout du label et la HBox à la racine
		
		root.setTop(texte);
		root.setBottom(hb);
		texte.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setMargin(texte, (new Insets(0, 0, 0, 0)));

		// Instanciations des actions "Valider" et "Annuler" 
		
		//actionBtnValider(stagiaireASupprimer, criteres, stageTableau, stageExtra);
		actionBtnAnnuler(criteres, stageTableau, stageExtra);
	}

	/* ******************* IMPLEMENTATION DES METHODES ******************** 
	 * Valider, Annuler et Construction du tableau */
	
//	private void actionBtnValider(StagiaireBinaire stagiaireASupprimer, StagiaireBinaire criteres, Stage stageTableau,
//			Stage stageExtra) {
//		boutonValider.setOnAction(actionEvent -> {
//
//			try {
//				arbre.supprimerUnStagiaire(stagiaireASupprimer);
//				constructionTableau(criteres, stageTableau, stageExtra);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//	}

	private void actionBtnAnnuler(StagiaireBinaire criteres, Stage stageTableau, Stage stageExtra) {
		boutonAnnuler.setOnAction(actionEvent -> {
			try {
				constructionTableau(criteres, stageTableau, stageExtra);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void constructionTableau(StagiaireBinaire criteres, Stage stageTableau, Stage stagePopup)
			throws IOException {
		List<StagiaireBinaire> listeDeResultat = new ArrayList<>();
		StagiaireBinaire.rechercheAvancee(listeDeResultat, criteres);

		TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stageTableau, listeDeResultat, criteres,
				true);
		tableStagiaireScene.getStylesheets().add("style.css");
		stageTableau.setScene(tableStagiaireScene);
		stagePopup.close();
	}
}
