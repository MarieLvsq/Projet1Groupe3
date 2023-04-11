package isika.cda24.Projet1.binaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class StagiaireBinaire {

	public final static int TAILLE_NOM = 21;
	public final static int TAILLE_PRENOM = 20;
	public final static int TAILLE_DEPARTEMENT = 2;
	public final static int TAILLE_PROMO = 11;
	public final static int TAILLE_ANNEE = 4;
	public final static int TAILLE_STAGIAIRE_OCTET = (TAILLE_NOM + TAILLE_PRENOM + TAILLE_DEPARTEMENT + TAILLE_PROMO
			+ TAILLE_ANNEE) * 2;
	public static final int TAILLE_MAX_STRING = 21;
	public static final int TAILLE_STAGIAIRE_OCTETS = TAILLE_MAX_STRING * 2 * 4 + 4;

	private String nom;
	private String prenom;
	private String departement;
	private String promo;
	private String annee;
	private StagiaireBinaire stagiaire;

	public StagiaireBinaire(String nom, String prenom, String departement, String promo, String annee) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.promo = promo;
		this.annee = annee;

	}

	public static List<StagiaireBinaire> readStagiairesFromResource(String resourceName) {

//public  List<NoeudBinaire> readNoeudsFromResource(String resourceName) {

		List<NoeudBinaire> noeuds = new ArrayList<>();
		List<StagiaireBinaire> stagiaires = new ArrayList<>();
		try (InputStream inputStream = StagiaireBinaire.class.getClassLoader().getResourceAsStream(resourceName)) {

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = reader.readLine();
			int lineNumber = 0;

			String nom = "";
			String prenom = "";
			String departement = "";
			String promo = "";
			String annee = "";

			while (line != null) {
				if (line.equals("*")) {
					line = reader.readLine();
					continue;
				}
				lineNumber++;
				switch (lineNumber) {
				case 1:
					nom = line;
					break;
				case 2:
					prenom = line;
					break;
				case 3:
					departement = line;
					break;
				case 4:
					promo = line;
					break;
				case 5:
					annee = line;
					stagiaires.add(new StagiaireBinaire(nom, prenom, departement, promo, annee));
					lineNumber = 0; // reset the line number for the next set of values
					break;
				default:
					// handle invalid input
					break;
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			// handle the exception
		}

		for (StagiaireBinaire stagiaire : stagiaires) {
			NoeudBinaire noeud = new NoeudBinaire(stagiaire);
			noeuds.add(noeud);
		}

		// System.out.println(stagiaires);
////	 pour afficher la liste
		// return noeuds;
		return stagiaires;
	}

//	}

	public StagiaireBinaire getStagiaire() {
		return this.stagiaire;
	}

// ajouterStagiaire method
	public static void ajouterStagiaire(List<NoeudBinaire> noeuds, StagiaireBinaire stagiaire) {
		NoeudBinaire nouveauNoeud = new NoeudBinaire(stagiaire);
		noeuds.add(nouveauNoeud);
	}

	public static void effacerFichier(String nomFichier) throws IOException {
		// Clear the contents of the binary file
		RandomAccessFile clearFile = new RandomAccessFile(nomFichier, "rw");
		clearFile.setLength(0);
		clearFile.close();
	}

	// Méthodes Spécifiques
	private static String mettreStringAttributALaTaille(String attribut) {
		if (attribut == null) {
			attribut = "";
		}
		String attributLong = attribut;
		if (attribut.length() > TAILLE_MAX_STRING) {
			attributLong = attribut.substring(0, TAILLE_MAX_STRING - 1); // TAILLE_MAX_STRING car c'est l'indice.
		} else {
			for (int i = attribut.length(); i < TAILLE_MAX_STRING; i++) {
				attributLong += " ";
			}
		}
		return attributLong;
	}

	@Override
	public String toString() {
		return this.nom + this.prenom + this.departement + this.promo + this.annee;
	}

	public int compareTo(StagiaireBinaire monStagiaire) {
		if (monStagiaire.nom.compareToIgnoreCase(this.nom) == 0) {
			if (monStagiaire.prenom.compareToIgnoreCase(this.prenom) == 0) {
				if (monStagiaire.departement.compareToIgnoreCase(this.departement) == 0) {
					if (monStagiaire.promo.compareToIgnoreCase(this.promo) == 0) {
						return monStagiaire.annee.compareToIgnoreCase(this.annee);
					} else {
						return monStagiaire.promo.compareToIgnoreCase(this.promo);
					}
				} else {
					return monStagiaire.departement.compareToIgnoreCase(this.departement);
				}
			} else {
				return monStagiaire.prenom.compareToIgnoreCase(this.prenom);
			}
		} else {
			return monStagiaire.nom.compareToIgnoreCase(this.nom);
		}
	}

	public static List<StagiaireBinaire> rechercheAvancee(List<StagiaireBinaire> stagiaires, StagiaireBinaire recherche) {
	    List<StagiaireBinaire> result = new ArrayList<>();

	    for (StagiaireBinaire s : stagiaires) {
	        boolean nomIdentique = false;
	        boolean prenomIdentique = false;
	        boolean departementIdentique = false;
	        boolean promoIdentique = false;
	        boolean anneeIdentique = false;

	        if (!recherche.getNom().isEmpty()) {
	            if (recherche.getNom().equalsIgnoreCase(s.getNom().trim())) {
	                nomIdentique = true;
	            }
	        } else {
	            nomIdentique = true;
	        }

	        if (!recherche.getPrenom().isEmpty()) {
	            if (recherche.getPrenom().equalsIgnoreCase(s.getPrenom().trim())) {
	                prenomIdentique = true;
	            }
	        } else {
	            prenomIdentique = true;
	        }

	        if (!recherche.getDepartement().isEmpty()) {
	            if (recherche.getDepartement().equalsIgnoreCase(s.getDepartement())) {
	                departementIdentique = true;
	            }
	        } else {
	            departementIdentique = true;
	        }

	        if (!recherche.getPromo().isEmpty()) {
	            if (s.getPromo().toLowerCase().contains(recherche.getPromo().toLowerCase().trim())) {
	                promoIdentique = true;
	            }
	        } else {
	            promoIdentique = true;
	        }

	        if (!recherche.getAnnee().isEmpty()) {
	            if (recherche.getAnnee().equals(s.getAnnee())) {
	                anneeIdentique = true;
	            }
	        } else {
	            anneeIdentique = true;
	        }

	        if (nomIdentique && prenomIdentique && departementIdentique && promoIdentique && anneeIdentique) {
	            result.add(s);
	        }
	    }

	    return result;
	}
	
	
	
	
	//getters and setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

}
