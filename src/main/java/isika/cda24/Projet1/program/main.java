package isika.cda24.Projet1.program;

import java.io.EOFException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import isika.cda24.Projet1.binaire.ArbreBinaire;
import isika.cda24.Projet1.binaire.NoeudBinaire;
import isika.cda24.Projet1.binaire.StagiaireBinaire;

public class main {

	public static void main(String[] args) {

		// Méthode pour SUPPRIMER le fichier - permet d'effacer toute liste qui pourrait
				// préexister sur le fichier .bin
				try {
					StagiaireBinaire.effacerFichier("Stagiaires.bin");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		// Création du fichier .bin		
		String filename = "Stagiaires.bin";


		// Lecture et écriture du fichier Stagiaires.bin ("rw")
				try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {

					// Création d'un arbre binaire nommé "arbre"
					ArbreBinaire arbre = new ArbreBinaire();

					// Création d'une liste de stagiaires de la classe StagiaireBinaire issue de la
					// liste du document STAGIAIRES.DON
					List<StagiaireBinaire> stagiaires = StagiaireBinaire.readStagiairesFromResource("STAGIAIRES.DON");

					// Ajout de tous les noms de la liste dans l'arbre binaire via la méthode
					// ajouterStagiaire
					for (StagiaireBinaire stagiaire : stagiaires) {
						arbre.ajouterStagiaire(stagiaire);
					}
			List<StagiaireBinaire> newcleList = arbre.affichageInfixeNoeud(stagiaires);
			for (StagiaireBinaire cle : newcleList) {
				System.out.println(cle);
			}
			//to delete
			//arbre.chercherNoeudAsupprimer("CHAVENEAU", raf);
			//--------------------------------------------------
		//	StagiaireBinaire nouveauStagiaire = new StagiaireBinaire(newNom, newPrenom, newDepartement, newPromo, newAnnee);
			StagiaireBinaire nouvStagiaire = new StagiaireBinaire("ZZZ", "David", "24", "CDA 24", "2023");


			arbre.modifierStagiaire("CHAVENEAU",nouvStagiaire,raf);



// to refresh the list
			 newcleList = arbre.affichageInfixeNoeud(stagiaires);
			 for (StagiaireBinaire cle : newcleList) {
			     System.out.println(cle);
			 }
		} catch (EOFException e) {
			// handle the EOFException
			System.err.println("Unexpected end of file encountered: " + e.getMessage());
			e.printStackTrace();
			//raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}}


		
//
//		
		
//
////			// Affichage infixe de l'arbre (permet un affichage des stagiaires par ordre
////			// alphabétique)
////			List<StagiaireBinaire> cleList = arbre.affichageInfixeNoeud(stagiaires);
////			for (StagiaireBinaire cle : cleList) {
////				System.out.println(cle); // On imprime les infos de chaque stagiaire
////			}
////			// FIN DE L'AFFICHAGE INFIXE
//////
////			// METHODE DE RECHERCHE D'UN STAGIAIRE PAR SON NOM
////			// Affiche également les doublons
////			arbre.rechercheStagiaire("NGUYEN");
////			
////			
////			// Si le nom n'est pas présent dans la liste :
////			if (cleList.isEmpty()) {
////				System.out.println("Le nom n'existe pas.");
////
////				// Si le nom est présent dans la liste :
////			} else {
////				for (StagiaireBinaire s : cleList) {
////					// On supprime les " " (chaque variable ayant une taille maximum unique définie
////					// en bites.)
////					// pour ne garder que les charactères du Nom du stagiaire
////					if (s.getNom().replace(" ", "").equals("NGUYEN")) {
////						if (s.getNom().replace(" ", "").equals("NGUYEN")) {
////
////							// On affiche les informations du stagiaire (ou des stagiaires s'il y a des
////							// doublons)
////							// On supprime à nouveau les " ", le cas échéant
////							System.out.println("Stagiaire trouvé: ");
////							System.out.println("Nom: " + s.getNom().replace(" ", ""));
////							System.out.println("Prénom: " + s.getPrenom().replace(" ", ""));
////							System.out.println("Département: " + s.getDepartement().replace(" ", ""));
////							System.out.println("Promo : " + s.getPromo().replace(" ", ""));
////							System.out.println("Annee : " + s.getAnnee().replace(" ", ""));
////						}
////
////					}
////
////				}
////			}
//			// FIN DE LA METHODE DE RECHERCHE PAR NOM.
//
////			// Méthode Recherche Avancée - cherche et affiche un stagiaire quand on appelle
////			// un seul paramètre du StagiaireBinaire
////			StagiaireBinaire recherche = new StagiaireBinaire("", "", "", "", "2020");
////			// Call the "rechercheAvancee" method and pass in the List and search criteria
////			List<StagiaireBinaire> resultList = StagiaireBinaire.rechercheAvancee(stagiaires, recherche);
////			// Loop through the resultList and print out the matching objects
////			for (StagiaireBinaire s : resultList) {
////				System.out.println(s.getNom() + " " + s.getPrenom() + " " + s.getDepartement() + " " + s.getPromo()
////						+ " " + s.getAnnee());
////			}
//			// FIN DE LA METHODE DE RECHERCHE AVANCEE.
//
////			//DEBUT METHODE AJOUTER STAGIAIRE.
////			StagiaireBinaire david = new StagiaireBinaire("ESA", "David", "21", "CDA24", "2023"); // création du stagiaire à ajouter
////			arbre.ajouterStagiaire(david); // ajout du stagiaire
////			// FIN METHODE AJOUT STAGIAIRE 
////			
//
//			// to delete
//			arbre.chercherNoeudAsupprimer("CHAVENEAU", raf);
////			// DEBUT METHODE MODIFIER STAGIAIRE
////			StagiaireBinaire nouvStagiaire = new StagiaireBinaire("ZZZ", "David", "24", "CDA 24", "2023");
////			arbre.modifierStagiaire("CHAVENEAU", nouvStagiaire, raf);
//
//			// to refresh the list
//			List<StagiaireBinaire> newcleList = new ArrayList<>();
//			arbre.affichageInfixeNoeud(newcleList);
//			for (StagiaireBinaire cle : newcleList) {
//				System.out.println(cle);
//			}
//		} catch (EOFException e) {
//			// handle the EOFException
//			System.err.println("Unexpected end of file encountered: " + e.getMessage());
//			e.printStackTrace();
//			// raf.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}