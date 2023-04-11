package isika.cda24.Projet1.binaire;

/* ******************* IMPORTS ********************/
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/* ******************* CLASSE PUBLIQUE ARBRE BINAIRE ********************/

public class ArbreBinaire {
	private NoeudBinaire racine;
	private RandomAccessFile raf;

	/* ******************* CONSTRUCTEUR DE L'ARBRE ********************/

	/**
	 * @param racine
	 * @param raf
	 */
	public ArbreBinaire(NoeudBinaire racine, RandomAccessFile raf) {
		super();
		this.racine = new NoeudBinaire(null);
		try {
			this.raf = new RandomAccessFile("STAGIAIRES.DON", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* ******************* CONSTRUCTEUR VIDE DE L'ARBRE ********************/

	public ArbreBinaire() {
		this.racine = new NoeudBinaire(null);
		try {
			raf = new RandomAccessFile("Stagiaires.bin", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* ******************* DEBUT DE LA METHODE POUR LIRE UN NOEUD ********************/
	/**
	 * @param raf
	 * @return
	 * @throws IOException
	 */
	public NoeudBinaire lireNoeud(RandomAccessFile raf) throws IOException {
		// initialiser les informations du noeud (infos du stagiaire + enfants du noeud)
		String nom = "";
		String prenom = "";
		String departement = "";
		String promo = "";
		String annee = "";
		int filsGauche = -1;
		int filsDroit = -1;
		int filsDoublon = -1;

		try {
			/**
			 * Lire chaque information du noeud Pour chaque boucle, on va jusqu'à la taille
			 * maximale de la variable
			 */
			for (int j = 0; j < StagiaireBinaire.TAILLE_NOM; j++) {
				nom += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_PRENOM; j++) {
				prenom += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_DEPARTEMENT; j++) {
				departement += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_PROMO; j++) {
				promo += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_ANNEE; j++) {
				annee += raf.readChar();
			}

			// On lit également les valeurs des enfants du noeud
			filsGauche = raf.readInt();
			filsDroit = raf.readInt();
			filsDoublon = raf.readInt();

			// On crée un nouveau stagiaire
			/**
			 * La fonction ".trim" permet de ne garder que les charactères et d'effacer
			 * l'espace restant éventuel entre la fin du nom/prenom/département/promo/année
			 * et la taille maximale de la variable
			 */
			StagiaireBinaire stagiaire = new StagiaireBinaire(nom.trim(), prenom.trim(), departement.trim(),
					promo.trim(), annee.trim());
		} catch (IOException e) {
			// Cas d'exception / d'erreur
			System.err.println("Erreur en lisant: " + e.getMessage());
			e.printStackTrace();
		}
		// On retourne un nouveau noeud contenant les informations qu'on vient de lire
		return new NoeudBinaire(new StagiaireBinaire(nom, prenom, departement, promo, annee), filsGauche, filsDroit,
				filsDoublon);
	}
	/******************** FIN DE LA METHODE POUR LIRE UN NOEUD ********************/

	
	
	
	/******************** DEBUT DE LA METHODE POUR AJOUTER UN STAGIAIRE ********************/

	/**
	 * @param noeudStagiaire
	 */
	public void ajouterStagiaire(StagiaireBinaire noeudStagiaire) {
		try {
			/*
			 * cas de base : s'il n'y a pas de racine on crée un nouveau noeud qui devient
			 * alors la racine on se positionne à zéro (localisation de la racine) on écrit
			 * la racine à cet emplacement
			 */

			if (raf.length() == 0) {
				racine = new NoeudBinaire(noeudStagiaire);
				raf.seek(0);
				racine.ecrireNoeud(racine, raf);
			} else {
				/*
				 * Si l'arbre n'est pas vide, On lit le premier noeud du fichier binaire On le
				 * stocke dans la racine on ajoute le noeudStagiaire comme nouveau NoeudBinaire
				 */
				raf.seek(0);
				racine = racine.lireNoeud(raf);
				racine.ajouterNoeud(new NoeudBinaire(noeudStagiaire), raf);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/******************** FIN DE LA METHODE POUR LIRE UN NOEUD ********************/

	
	
	
	/* ******************* DEBUT DE LA METHODE AFFICHAGE INFIXE ********************/
	/**
	 * @param cleList
	 * @return
	 */
	public List<StagiaireBinaire> affichageInfixeNoeud(List<StagiaireBinaire> cleList) {
		try {
			/*
			 * Si l'arbre est vide On affiche "l'arbre est vide"
			 */
			if (raf.length() == 0) {
				System.out.println("l'arbre est vide");
			} else {
				/*
				 * Sinon, on se positionne à la racine On lit le noeud racine On appelle la
				 * méthode affichageInfixeNoeud
				 */
				raf.seek(0);
				racine = racine.lireNoeud(raf);
				racine.affichageInfixeNoeudB(raf, cleList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cleList; // on retourne la liste de stagiaires cleList
	}
	/* ******************* FIN DE LA METHODE AFFICHAGE INFIXE ********************/

	
	
	
	/* ******************* DEBUT DE LA METHODE RECHERCHER STAGIAIRE PAR LE NOM********************/
	/**
	 * @param nom
	 * @return
	 */
	public List<StagiaireBinaire> rechercheStagiaire(String nom) {
		/*
		 * On crée une nouvelle liste de stagiaires et un nouveau stagiaireBinaire avec
		 * une variable "nom" (le nom qui sera recherché) + les 4 derniers paramètres
		 * nuls
		 */
		List<StagiaireBinaire> listeResultats = new ArrayList<>();
		StagiaireBinaire stagiaireRecherche = new StagiaireBinaire(nom, "", "", "", "");

		try {
			/*
			 * Si l'arbre est vide On affiche "l'arbre est vide"
			 */
			if (raf.length() == 0) {
				System.out.println("l'arbre est vide");
			} else {
				/*
				 * Si l'arbre n'est pas vide, on se positionne à la racine on lit le noeud
				 * racine on appelle la méthode rechercherNoeud qui passe en paramètre la liste
				 * de stagiaires listeResultats, le stagiaire stagiaireRecherche et le
				 * RandomAccessFile
				 */
				raf.seek(0);
				racine = racine.lireNoeud(raf);
				racine.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * on retourne les stagiaires de la listeResultats
		 */
		return listeResultats;
	}
	/******************** FIN DE LA METHODE RECHERCHER STAGIAIRE PAR LE NOM ********************/

	
	
	/******************** DEBUT DE LA METHODE RECHERCHER STAGIAIRE A SUPPRIMER ********************/
	/**
	 * @param nom
	 * @param raf
	 * @return
	 * @throws IOException
	 */
	public StagiaireBinaire chercherNoeudAsupprimer(String nom, RandomAccessFile raf) throws IOException {
		/*
		 * On crée un stagiaire à supprimer (cleToDelete) avec la variable nom, le reste
		 * initialisé à nul ("")
		 */
		StagiaireBinaire cleToDelete = new StagiaireBinaire(nom, "", "", "", "");
		/*
		 * On cherche le noeud à supprimer avec la méthode chercheretsupprimer de la classe NoeudBinaire
		 */
		NoeudBinaire nodeToDelete = racine.chercheretsupprimer(racine, new NoeudBinaire(cleToDelete), null, raf);

		if (nodeToDelete == null) {
			/*Si le noeud à supprimer n'existe pas
			 * on affiche "Stagiaire non trouvé"
			 * on retourne null
			 */
			System.out.println("Stagiaire non trouvé");
			return null;
		} else {
			System.out.println("Stagiaire trouvé et supprimé : " + nodeToDelete.getCle().getNom());
			return nodeToDelete.getCle();
		}
	}
	/******************** FIN DE LA METHODE RECHERCHER STAGIAIRE A SUPPRIMER ********************/

	
	
	
	/******************** DEBUT DE LA METHODE MODIFIER STAGIAIRE ********************/

	public StagiaireBinaire modifierStagiaire(String nom, StagiaireBinaire nouvStagiaire, RandomAccessFile raf)
			throws IOException {
		/*
		 * On cherche le noeud à supprimer avec la méthode chercheretsupprimer de la
		 * classe NoeudBinaire
		 */
		NoeudBinaire nodeToDelete = racine.chercheretsupprimer(racine,
				new NoeudBinaire(new StagiaireBinaire(nom, "", "", "", "")), null, raf);

		/*
		 * si le noeud à supprimer n'existe pas, on affiche "Stagiaire non trouvé" et on
		 * retourne la valeur null
		 */
		if (nodeToDelete == null) {
			// node not found, return "Stagiaire non trouvé"
			System.out.println("Stagiaire non trouvé");
			return null;
		} else {
			/*
			 * Si le noeud existe dans l'arbre, on affiche les informations du noeud à
			 * supprimer on ajoute le nouveau stagiaire (nouvStagiaire)dans l'arbre puis on
			 * affiche les informations du stagiaire on retourne le nouveau stagiaire
			 * (nouvStagiaire)
			 */
			System.out.println("Stagiaire trouvé et supprimé : " + nodeToDelete.getCle().getNom());
			ajouterStagiaire(nouvStagiaire);
			System.out.print("Stagiaire modifié : ");
			System.out.print("Nom : " + nouvStagiaire.getNom() + ", ");
			System.out.print("Prénom : " + nouvStagiaire.getPrenom() + ", ");
			System.out.print("Département : " + nouvStagiaire.getDepartement() + ", ");
			System.out.print("Promo : " + nouvStagiaire.getPromo() + ", ");
			System.out.println("Année : " + nouvStagiaire.getAnnee());
			return nouvStagiaire;
		}
	}

	
	
	
	/* ******************* GETTERS ET SETTERS ********************/

	public NoeudBinaire getRacine() {
		return racine;
	}

	public void setRacine(NoeudBinaire racine) {
		this.racine = racine;
	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}
}