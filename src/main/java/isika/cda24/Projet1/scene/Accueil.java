package isika.cda24.Projet1.scene;

import isika.cda24.Projet1.binaire.StagiaireBinaire;
import isika.cda24.Projet1.binaire.ArbreBinaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Accueil extends Scene {
	
/* ******************* INSTANCIATION DES BOUTONS ******************** 
 * Nous allons ici instancier tous nos boutons de la page Accueil */

	// On instancie un bouton pour accéder à la liste :
	private final Button btnAccesListe;

	// On instancie un bouton pour l'accès administrateur :
	private final Button btnAccesAdmin;
	
	// On instancie un bouton pour rechercher un stagiaire :
	private final Button btnRechercher;

	// On instancie un bouton pour ajouter un stagiaire :
	private final Button btnAjouter;
	

	// On instancie un bouton pour la déconnexion de l'accès administrateur :
	private final Button btnDeconnexion;
	private final TextField txtNom;
	private final TextField txtPrenom;
	private final TextField txtPromo;
	private final TextField txtDepartement;
	private final TextField txtAnnee;
	private final Label validationAjout;
	private final ArbreBinaire arbre;
	
	/* ******************* CONSTRUCTION DE NOTRE PAGE ******************** 
	 * Création de la scene
	 * modification et améliorations
	 * ajout du texte et des cases */

	// Constructeur de la scene
	
	public Accueil(Stage stage, Boolean administrateur) throws FileNotFoundException {
		super(new VBox(), 1000, 600);

		arbre = new ArbreBinaire();

		// On récupère la racine de la scene et on la détermine comme VBox : On déclare
		// un attribut
		
		VBox root = (VBox) this.getRoot();
		root.setStyle(("-fx-font-family: 'Arial'"));
		
		// Création de la scène avec un dégradé de couleur bleu canard vers blanc

		Stop[] stops = new Stop[] { new Stop(0, Color.rgb(51, 153, 153)), new Stop(1, Color.WHITE) };
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		root.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));
		
		// On déclare une GridPane pour y placer le formulaire :
		
		GridPane formulaire = new GridPane();
		Label labelTitre = new Label();
		validationAjout = new Label();

		if (administrateur) {
			labelTitre.setText("ACCUEIL ADMINISTRATEUR");
		} else {
			labelTitre.setText("BIENVENUE DANS L'ANNUAIRE ISIKA");
		}

		// On instancie les labels, champs de texte et boutons
		
		Label nom = new Label("Nom ");
		txtNom = new TextField();

		Label prenom = new Label("Prénom ");
		txtPrenom = new TextField();

		Label departement = new Label("Département ");
		txtDepartement = new TextField();

		// Ajout de l'année de formation
		
		Label annee = new Label("Année de formation ");
		txtAnnee = new TextField();

		Label promo = new Label("Promotion ");
		txtPromo = new TextField();
		
		// ******************* CREATION DE NOTRE FORMULAIRE ******************** //
		

		// On instancie une HBox pour y placer la GridPane 
		//et 2 HBox pour y placer les 4 boutons 
		//que l'on va instancier juste après :
		
		
		HBox hBoxFormulaire = new HBox();
		// On ajoute la hBox au GridePane
		
		hBoxFormulaire.getChildren().add(formulaire); 
		hBoxFormulaire.setAlignment(Pos.CENTER);

		HBox hBoxRechercherAjouter = new HBox();
		HBox hBoxConnexionListe = new HBox();

		hBoxRechercherAjouter.setAlignment(Pos.CENTER);
		hBoxRechercherAjouter.setPadding(new Insets(10));
		hBoxRechercherAjouter.setSpacing(300);

		hBoxConnexionListe.setAlignment(Pos.CENTER);
		hBoxConnexionListe.setPadding(new Insets(10));
		hBoxConnexionListe.setSpacing(300);

		
		//******************** IMPLEMENTATION DES BOUTONS DE LA PAGE ******************//

		// On implémente un bouton pour rechercher un stagiaire :
		
		btnRechercher = new Button("Rechercher");

		// On implémente un bouton pour ajouter un stagiaire :
		
		btnAjouter = new Button("Ajouter");

		// On implémente un bouton pour accéder à la liste :
		
		btnAccesListe = new Button("Accès liste");

		// On implémente un bouton pour l'accès administrateur :
		
		btnAccesAdmin = new Button("Connexion");

		// On implémente un bouton pour l'accès administrateur :
		
		btnDeconnexion = new Button("Déconnexion");
		
		// On implémente un bouton pour rechercher un stagiaire :
		
	
		// On relie les boutons aux HBox:
		
		hBoxRechercherAjouter.getChildren().addAll(btnRechercher, btnAjouter);
		if (administrateur) {
			hBoxConnexionListe.getChildren().addAll(btnDeconnexion, btnAccesListe);
		} else {
			hBoxConnexionListe.getChildren().addAll(btnAccesAdmin, btnAccesListe);
		}
			
		// On intègre le formulaire au GridPane:
		
		formulaire.addRow(0, nom, txtNom);
		formulaire.addRow(1, prenom, txtPrenom);
		formulaire.addRow(2, departement, txtDepartement);
		formulaire.addRow(3, promo, txtPromo);
		formulaire.addRow(4, annee, txtAnnee);
		formulaire.setHgap(25);
		formulaire.setVgap(15);

		formulaire.getColumnConstraints().add(new ColumnConstraints(200));

		// On ajoute les éléments dans la VBox (myRoot):
		
		root.getChildren().addAll(hBoxConnexionListe, labelTitre, hBoxFormulaire, hBoxRechercherAjouter, validationAjout);

		// Mise en page des éléments
		
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(60));
		root.setSpacing(30);

		// **** Implémentation des Actions **** //
		
		actionBtnAccesAdmin(stage);
		actionBtnDeconnexion(stage);
		actionBtnAccesListe(stage, administrateur);
		actionBtnAjouter(stage, administrateur);
		actionBtnRechercher(stage, administrateur);
	}

	/* ******************* IMPLEMENTATION DES METHODES ******************** 
	 * Accès administrateur, Déconnexion, accès à la liste, ajout et recherche */	
	
	
	private void actionBtnAccesAdmin(Stage stage) {
		btnAccesAdmin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AdminConnexion connexionScene = new AdminConnexion(stage);
				connexionScene.getStylesheets().add("style.css");
				stage.setScene(connexionScene);
			}
		});
	}

	private void actionBtnDeconnexion(Stage stage) {
		btnDeconnexion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Accueil accueilRetour = null;
				try {
					accueilRetour = new Accueil(stage, false);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				accueilRetour.getStylesheets().add("style.css");
				stage.setScene(accueilRetour);
			}
		});
	}
	
	
	private void actionBtnAccesListe(Stage stage, Boolean administrateur) {
		btnAccesListe.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StagiaireBinaire criteres = new StagiaireBinaire("", "", "", "", "");
				List<StagiaireBinaire> listeDeStagiaires = new ArrayList<>();
				arbre.affichageInfixeNoeud(listeDeStagiaires);
				TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stage, listeDeStagiaires,
						criteres, administrateur);
				tableStagiaireScene.getStylesheets().add("style.css");
				stage.setScene(tableStagiaireScene);
			}
		});
	}

	private void actionBtnAjouter(Stage stage, Boolean administrateur) {
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String nom = txtNom.getText();
				String prenom = txtPrenom.getText();
				String promotion = txtPromo.getText();
				String departement = txtDepartement.getText();
				String annee = txtAnnee.getText();

				System.out.println("nom : " + nom +"/ prenom : " + prenom + " / promo : " + promotion + "/année :" + annee);
				if (!nom.equals("") && !prenom.equals("") && !promotion.equals("") && !annee.equals("")) {
					System.out.println("btn methode ajout");
					StagiaireBinaire stagiaireAAjouter = new StagiaireBinaire(nom, prenom, departement, promotion,
							annee);
					arbre.ajouterStagiaire(stagiaireAAjouter);
					validationAjout.setText("Le stagiaire a bien été ajouté.");

					// Remise à zéro de champs
					txtNom.clear();
					txtPrenom.clear();
					txtDepartement.clear();
					txtPromo.clear();
					txtAnnee.clear();
					
					List<StagiaireBinaire> listeDeStagiaires = new ArrayList<>();
					arbre.affichageInfixeNoeud(listeDeStagiaires);
					TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stage, listeDeStagiaires, stagiaireAAjouter, administrateur);
					tableStagiaireScene.getStylesheets().add("style.css");
					stage.setScene(tableStagiaireScene);
					
				} else {
					System.out.println("btn ajout dnas le else");
					validationAjout.setText("Il manque des informations.");
				}
			}
		});
	}

	private void actionBtnRechercher (Stage stage, Boolean administrateur) {
        btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String promotion = txtPromo.getText();
                String departement = txtDepartement.getText();
                String annee = txtAnnee.getText();

                StagiaireBinaire criteres = new StagiaireBinaire(nom, prenom, departement, promotion, annee);
                System.out.println(criteres);
                List<StagiaireBinaire> listeTotale = new ArrayList<>();
                arbre.affichageInfixeNoeud(listeTotale);
                List<StagiaireBinaire> listStagiairesRecherches = StagiaireBinaire.rechercheAvancee(listeTotale, criteres);

                
                System.out.println("liste recherche " + listStagiairesRecherches.size());
                TableViewStagiaires tableStagiaireScene = new TableViewStagiaires(stage, listStagiairesRecherches, criteres, administrateur);
                tableStagiaireScene.getStylesheets().add("style.css");
                stage.setScene(tableStagiaireScene);
            }
        });
    }
}