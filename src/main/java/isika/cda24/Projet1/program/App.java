package isika.cda24.Projet1.program;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import isika.cda24.Projet1.binaire.StagiaireBinaire;
import isika.cda24.Projet1.binaire.ArbreBinaire;
import isika.cda24.Projet1.binaire.NoeudBinaire;
import isika.cda24.Projet1.scene.Accueil;

public class App extends Application {
	public static void main(String[] args) throws IOException {

		ArbreBinaire arbre = new ArbreBinaire();
		arbre.getRaf();// .verificationImportFichierDon(arbre);
		if (arbre.getRaf().length() == 0) {
			List<StagiaireBinaire> stagiaires = StagiaireBinaire.readStagiairesFromResource("STAGIAIRES.DON");
			for (StagiaireBinaire stag : stagiaires) {
				arbre.ajouterStagiaire(stag);
			}
		}
		launch(args);
//		 arbre.getRaf();//.fermetureAccessFile();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Accueil accueilScene = new Accueil(primaryStage, false);
		accueilScene.getStylesheets().add("style.css");

		primaryStage.setResizable(false);
		primaryStage.setTitle("ISIKONNECT");
		primaryStage.setScene(accueilScene);
		primaryStage.show();
	}
}
