package isika.cda24.Projet1.scene;

public class Admins {

	    // Attributs de classe. private
	    public static final String IDENTIFIANT = "Vincent";
	    public static final String MOTDEPASSE = "CDA24";

	    // Constructeur
	    public Admins() {
	        super();
	    }

	    // Getters & setters
	    public String getIdentifiant() {
	        return IDENTIFIANT;
	    }

	    public String getMotDePasse() {
	        return MOTDEPASSE;
	    }

	    // methodes spécifiques
	    @Override
	    public String toString() {
	        return "Administrateur\nIdentifiant : " + IDENTIFIANT + "\n Mot de Passe : " + MOTDEPASSE + ".\n";
	    }

	}