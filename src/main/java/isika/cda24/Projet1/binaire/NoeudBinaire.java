package isika.cda24.Projet1.binaire;

/* ******************* IMPORTS ********************/
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;


/* ******************* CLASSE PUBLIQUE ARBRE BINAIRE ********************/

public class NoeudBinaire {
   public final static int TAILLE_MAX_NOEUD_OCTET = StagiaireBinaire.TAILLE_STAGIAIRE_OCTET + 3 * 4;
    private StagiaireBinaire cle;
    private int filsGauche;
    private int filsDroit;
    private int filsDoublon;
    private int index;
    private NoeudBinaire parent;

    
    
    /* ******************* PREMIER CONSTRUCTEUR DE L'ARBRE *******************
	 * Prend un stagiaire en paramètre et inititalise la cle du noeud
     * @param cle
     */
    public NoeudBinaire(StagiaireBinaire cle) {
        this.filsDoublon = -1;
        this.cle = cle;
        this.filsGauche = -1;
        this.filsDroit = -1;
        this.parent = null;
        this.index= index;
    }
    
    
    
    /* ******************* SECOND CONSTRUCTEUR DE L'ARBRE *******************
     * Permet de créer un objet noeud
    /**
     * @param cle
     * @param filsGauche
     * @param filsDroit
     * @param filsDoublon
     */
    public NoeudBinaire(StagiaireBinaire cle, int filsGauche, int filsDroit, int filsDoublon) {
        this.cle = cle;
        this.filsDroit = filsDroit;
        this.filsGauche = filsGauche;
        this.filsDoublon = filsDoublon;
    }

    
    
	/* ******************* DEBUT DE LA METHODE POUR ECRIRE UN NOEUD ********************/
    /**
     * @param cleAjout
     * @param raf
     * @throws IOException
     */
    public void ecrireNoeud(NoeudBinaire cleAjout, RandomAccessFile raf) throws IOException {

        try {
        	/*
        	 * On écrit chacun des paramètres du noeud cleAjout
        	 * en faisant appel à chaque longueur des variables 
        	 * les variables "xxxLong" fixent la taille maximale, unique pour chaque variable
        	 * on initialise le noeud comme une feuille (enfants à -1)
        	 */
            raf.writeChars(cleAjout.getNomLong());
            raf.writeChars(cleAjout.getPrenomLong());
            raf.writeChars(cleAjout.getDepartementLong());
            raf.writeChars(cleAjout.getPromoLong());
            raf.writeChars(cleAjout.getAnneeLong());
            raf.writeInt(-1);
            raf.writeInt(-1);
            raf.writeInt(-1);

        } catch (IOException e) {
            System.err.println("Erreur en ecrivant: " + e.getMessage());
            e.printStackTrace();
        }
    }
	/* ******************* FIN DE LA METHODE POUR ECRIRE UN NOEUD ********************/

    
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
        StagiaireBinaire stagiaire = null;
        /*on initialise l'index temporaire du noeud à sa position 
         * afin de pouvoir l'utiliser pour la méthode de suppression d'un noeud*/
        int indexTemp = (int) (raf.getFilePointer() / NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
        
        try {
        	/**
			 * Lire chaque information du noeud Pour chaque boucle, on va jusqu'à la taille
			 * maximale de la variable
			 */
            if (raf.getFilePointer() < raf.length()) {
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
               stagiaire = new StagiaireBinaire(nom.trim(), prenom.trim(), departement.trim(), promo.trim(), annee.trim());
            } else {
                System.out.println("End of file reached.");
            }
        } catch (EOFException e) {
            // handle the EOFException
            System.err.println("Unexpected end of file encountered: " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("Erreur en lisant: " + e.getMessage());
            e.printStackTrace();
        }
		
        /* On crée un nouveau noeud contenant les informations qu'on vient de lire
         * La valeur de l'index du noeud "res" est donnée à "indexTemp" (l'index temporaire)
         * On retourne le nouveau noeud "res"
         */
        
        NoeudBinaire res = new NoeudBinaire(new StagiaireBinaire(nom, prenom, departement, promo, annee), filsGauche, filsDroit,
                filsDoublon);
        res.index = indexTemp;
        return res;
    }
	/******************** FIN DE LA METHODE POUR LIRE UN NOEUD ********************/

    

	/******************** DEBUT DE LA METHODE POUR AJOUTER UN NOEUD ********************/
    /**
     * @param noeudStagiaire
     * @param raf
     */
    public void ajouterNoeud(NoeudBinaire noeudStagiaire, RandomAccessFile raf) {
      
        try {
            //On compare le nom du noeud acteul avec celui du noeudStagiaire
        	int comparaison = this.cle.getNom().compareTo(noeudStagiaire.cle.getNom());

        	/*
        	 * Si la comparaison est >0
        	 * et si le noeud n'a pas de fils gauche,
        	 * on remonte d'un fils gauche (-12 dans le fichier binaire)
        	 * on inscrit la valeur dans le fils gauche du noeud actuel
        	 * on va jusqu'à  la fin de la branch
        	 * on écrit le nouveau noeud (noeudStagiaire)
        	 */
            if (comparaison > 0) {
                if (this.filsGauche == -1) { // cas de terminaison
                    this.filsGauche = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
                    raf.seek(raf.getFilePointer() - 12);
                    raf.writeInt(this.filsGauche);
                    raf.seek(raf.length());
                    noeudStagiaire.parent = this;// Set parent of new node
                    ecrireNoeud(noeudStagiaire, raf);

                } else {
                	/*s'il y a un fils gauche
                	 * on se positionne au fils gauche du noeud actuel
                	 * on lit le noeud
                	 * on fait appel à la méthode récursive ajouterNoeud sur le noeudStagiaire
                	 */
                    raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                    NoeudBinaire filsGauche = lireNoeud(raf);
                    filsGauche.ajouterNoeud(noeudStagiaire, raf);

                }
            } else if (comparaison < 0) {
            	/*
            	 * Si la comparaison est < 0
            	 * et si le noeud n'a pas de fils droit,
            	 * on remonte d'un fils droit (-8 dans le fichier binaire)
            	 * on inscrit la valeur dans le fils droit du noeud actuel
            	 * on va jusqu'à  la fin de la branche
            	 * on écrit le nouveau noeud (noeudStagiaire)
            	 */
                if (this.filsDroit == -1) {
                    this.filsDroit = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
                    raf.seek(raf.getFilePointer() - 8); 
                    raf.writeInt(this.filsDroit);
                    raf.seek(raf.length());
                    noeudStagiaire.parent = this; // Set parent of new node
                    ecrireNoeud(noeudStagiaire, raf);
                } else {
                	/*s'il y a un fils droit
                	 * on se positionne au fils droit du noeud actuel
                	 * on lit le noeud
                	 * on fait appel à la méthode récursive ajouterNoeud sur le noeudStagiaire
                	 */
                    raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
                    NoeudBinaire filsDroit = lireNoeud(raf);
                    filsDroit.ajouterNoeud(noeudStagiaire, raf);//Pass parent node
                }
            } else {
            	/*
            	 * Si la comparaison est = 0 on est dans une situation de DOUBLON
            	 * et si le noeud n'a pas de filsDoublon,
            	 * le doublon prend la valeur de la longueur de la branche de l'arbre
            	 * on remonte de -4 octets
            	 * on inscrit la valeur dans le fils doublon du noeud actuel
            	 * on va jusqu'à  la fin de la branche
            	 * on écrit le nouveau noeud (noeudStagiaire)
            	 */
                if (this.filsDoublon == -1) {
                    this.filsDoublon = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
                    raf.seek(raf.getFilePointer() - 4);
                    raf.writeInt(this.filsDoublon); 
                    raf.seek(raf.length());
                    noeudStagiaire.parent = this; // Set parent of new node
                    ecrireNoeud(noeudStagiaire, raf);
                } else {
                	/*
                	 * Si le doublon a un fils,
                	 * on se place au doublon,
                	 * on lit l'information du noeud où l'on se trouve et on l'applique à un nouveau noeud ("doublon")
                	 * appel récursif de la méthode ajouterNoeud au doublon
                	 */
                    raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
                    NoeudBinaire doublon = lireNoeud(raf);
                    doublon.ajouterNoeud(noeudStagiaire, raf);//pass parent mode
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/******************** FIN DE LA METHODE POUR AJOUTER UN NOEUD ********************/

    

    
    
	/* ******************* DEBUT DE LA METHODE AFFICHAGE INFIXE ********************/
    /**
     * @param raf
     * @param cleList
     */
    public void affichageInfixeNoeudB(RandomAccessFile raf, List<StagiaireBinaire> cleList) {
        try {
            if (this.filsGauche != -1) {
            	/*
				 * si le noeud n'a pas de fils gauche,
				 * on se positionne sur le fils gauche (cadré par la taille max du noeud en octet)
				 * méthode affichageInfixeNoeud sur le fils gauche
				 */
                raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire filsGauche = lireNoeud(raf);
                filsGauche.affichageInfixeNoeudB(raf, cleList);
            }

            /*
			 * Idem si le fils doublon n'existe pas
			 */
            if (this.filsDoublon != -1) {
                raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire filsDoublon = lireNoeud(raf);
                filsDoublon.affichageInfixeNoeudB(raf, cleList);
            }
            /*
			 * Idem si le fils droit n'existe pas
			 */
            if (this.filsDroit != -1) {
                raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire filsDroit = lireNoeud(raf);
                filsDroit.affichageInfixeNoeudB(raf, cleList);
               
            }
            //On ajoute le stagiaire "cle" à la liste des stagiaires cleList 
            cleList.add(this.cle);
            
        } catch (IOException e) {
            System.err.println("Erreur en lisant: " + e.getMessage());
            e.printStackTrace();
        }
    }
	/* ******************* FIN DE LA METHODE AFFICHAGE INFIXE ********************/

    
    
    
	/* ******************* DEBUT DE LA METHODE RECHERCHER NOEUD PAR LE NOM ********************/
    /**
     * @param listeResultats
     * @param stagiaireRecherche
     * @param raf
     * @throws IOException
     */
    public void rechercheNoeud(List<StagiaireBinaire> listeResultats, StagiaireBinaire stagiaireRecherche,
                               RandomAccessFile raf) throws IOException {
        // Chercher si la clef du noeud courant est la même que la clef du stagiaire recherché
        if (stagiaireRecherche.getNom().compareToIgnoreCase(this.cle.getNom()) == 0) {
            // Ajouter la valeur du noeud courant à la liste de résultats
            if (this.filsDoublon != -1) {
                /*
                 * S'il n'y a pas de doublon, continuer à chercher sur cette branche
                 */
                raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudSuivant = lireNoeud(raf);
                noeudSuivant.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
            }
           
        } else if (stagiaireRecherche.getNom().compareToIgnoreCase(this.cle.getNom()) < 0) {
        	 /*
             * si la clef du stagiaire recherché est supérieure à la clef du noeud courant
             * on continue à chercher dans le sous-arbre gauche
             */
            if (this.filsGauche == -1) {
            } else {
            	 /*
                 * S'il n'y a pas de fils gauche, continuer à chercher sur cette branche
                 */
                raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudFilsGauche = lireNoeud(raf);
                noeudFilsGauche.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
            }
            /*
             * si la clef du stagiaire recherché est inférieure à la clef du noeud courant
             * on continue à chercher dans le sous-arbre droit
             */
        } else {
        	
        	if (this.filsDroit == -1) {
        		
            } else {
            	 /*
                 * S'il n'y a pas de fils droit, continuer à chercher dans le sous-arbre droit
                 */
                raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudFilsDroit = lireNoeud(raf);
                noeudFilsDroit.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
            }
        }
    }
	/* ******************* FIN DE LA METHODE RECHERCHER NOEUD PAR LE NOM********************/

    
    
	/* ******************* DEBUT DE LA METHODE SUPPRIMER NOEUD ********************/
    /**
     * @param noeudASupprimer
     * @param indexDuParent
     * @param raf
     * @throws IOException
     */

    public void supprimerNoeud(NoeudBinaire noeudASupprimer, int indexDuParent, RandomAccessFile raf)
            throws IOException {
        int index = (int) ((raf.getFilePointer() - TAILLE_MAX_NOEUD_OCTET) / TAILLE_MAX_NOEUD_OCTET);
        System.out.println("Method supprimerNoed : index ->" + index + "index du parent ->" + indexDuParent);
//--------------------------------------------if it's a leaf node ------------------------------------------------------
        try {
            if (noeudASupprimer.getFilsGauche() == -1 && noeudASupprimer.getFilsDroit() == -1) {
                raf.seek(indexDuParent * NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);// put the pointer to the pos of parent of noeudASupprimer
                NoeudBinaire parent = lireNoeud(raf);

                if (parent.getCle().getNom().trim().compareTo(noeudASupprimer.getCle().getNom().trim()) > 0) {
                    //if parent > note to delete, go left, -12 =to move the pointer to FG
                    raf.seek(raf.getFilePointer() - 12);
                    // by writing -1 , FG doesn't exist anymore
                    raf.writeInt(-1);

                } else if (parent.getCle().getNom().trim().compareTo(noeudASupprimer.getCle().getNom().trim()) < 0) {
                    //if parent < note to delete, go right, -8  =to move the pointer to FD
                    raf.seek(raf.getFilePointer() - 8);
                    // by writing -1 , FDr doesn't exist anymore
                    raf.writeInt(-1);
                } else {
                    raf.seek(raf.getFilePointer() - 4); //put the pointer to doublon, -4
                    // by writing -1 , FDo doesn't exist anymore
                    raf.writeInt(-1);
                }
                //----------------- if the node to delete has only one child (left or right)-------------
            } else if (noeudASupprimer.getFilsGauche() == -1 || noeudASupprimer.getFilsDroit() == -1) {
                // Determine which child node exists
                int indexFilsGauche = noeudASupprimer.getFilsGauche();
                int indexFilsDroit = noeudASupprimer.getFilsDroit();
                System.out.println("FilsGauche: " + indexFilsGauche);
                System.out.println("FilsDroit: " + indexFilsDroit);
                // raf.seek(indexDuParent*NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);

                // if there is a FG
                if (noeudASupprimer.filsGauche != -1) {
                    raf.seek(indexDuParent * NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
                    parent = lireNoeud(raf);
                    System.out.println("Parent : " + getParent().getCle().getNom().trim());
                    int comparaison = (parent.getCle().getNom().trim())
                            .compareTo(noeudASupprimer.getCle().getNom().trim());
                    if (comparaison > 0) {
                        raf.seek(raf.getFilePointer() - 12);
                        raf.writeInt(noeudASupprimer.getFilsGauche());
                    } else if (comparaison < 0) {
                        raf.seek(raf.getFilePointer() - 8);// pointeur->position fils Droit
                        raf.writeInt(noeudASupprimer.getFilsGauche());
                    } else {
                        raf.seek(raf.getFilePointer() - 4);// pointeur->position doublon
                        raf.writeInt(-1);// modifié doublon en -1 = doublon n'existe plus }

                    }
                } else {
                    raf.seek(indexDuParent * NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
                    parent = lireNoeud(raf);
                    System.out.println("Parent : " + getParent().getCle().getNom().trim());
                }
            }
///----------------------------------if the node has 2 children-------------------------------------------
            // SET THE CONDITION
            System.out.println("deletion with 2 children");
            if (noeudASupprimer.getFilsGauche() != -1 && noeudASupprimer.getFilsDroit() != -1) {
                int indexFilsGauche = noeudASupprimer.getFilsGauche();
                int indexFilsDroit = noeudASupprimer.getFilsDroit();
               //raf.seek(indexFilsGauche * TAILLE_MAX_NOEUD_OCTET);
                raf.seek(indexFilsDroit * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudDroit = lireNoeud(raf);
                // Find the parent of the successor node
               // int positionNoeudSuppr = (int) raf.getFilePointer();
//                System.out.println("the postion of stagiaire:"+ index+ getParent().getCle().getNom().trim());
                // Find the successor node (i.e., the node with the next highest value)
                NoeudBinaire successor = getSuccesseur(noeudDroit, raf);
                // replace the note to deleate with the successor
//                System.out.println("the postion of" +successor+ index+ getParent().getCle().getNom().trim());
                raf.seek(index * NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
                raf.writeChars(successor.getNomLong());
                raf.writeChars(successor.getPrenomLong());
                raf.writeChars(successor.getDepartementLong());
                raf.writeChars(successor.getPromoLong());
                raf.writeChars(successor.getAnneeLong());
            // Update the position of the node to be deleted to be the position of the successor node

                raf.seek((int)raf.getFilePointer()+8);
                raf.writeInt(successor.filsDoublon);

                //Delete the successor node from its current position
                raf.seek(index*TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire parent = lireNoeud(raf);
                raf.seek(indexFilsDroit * TAILLE_MAX_NOEUD_OCTET + TAILLE_MAX_NOEUD_OCTET);
                chercheretsupprimer(noeudDroit, successor, parent, raf  );
            }
        } catch (IOException e) {
            System.err.println("An IO exception in delete method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //if x has no FD,successor = parent of the x‘s youngest ancestor,that is a left child
    // 2. set current to x's parent,While current is not null and x is its FD,set x to current,current to current's parent, return current
    //In-order Successor: if x has FD,Successor=min of x's right ,i.e, left most leaf of right child of x
    //1. set current to FD, while current has FG,set current to current's FG, return current
    public NoeudBinaire getSuccesseur(NoeudBinaire current, RandomAccessFile raf) throws IOException {
        try {
            if (current.filsGauche != -1) {
                raf.seek(current.getFilsGauche() * NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudGauche = lireNoeud(raf);
               return getSuccesseur(noeudGauche, raf);
            } else {
                return current;
            }
        } catch (IOException e) {
            System.err.println("An IO exception occurred: " + e.getMessage());
            // rethrow the exception as a runtime exception to signal that an unexpected error occurred
            throw new RuntimeException("Failed to get successor node", e);
        } catch (RuntimeException e) {
            // handle the runtime exception (log the error or show an error message to the user)
            System.err.println("A runtime exception occurred: " + e.getMessage());
            throw e; // rethrow the exception
        }

    }

    //  ----------------------------fin getSuccesseur------------------------------------------------

    
	/* ******************* FIN DE LA METHODE CHERCHER ET SUPPRIMER NOEUD ********************/
    /**
     * @param currentNode
     * @param nodeToDelete
     * @param parent
     * @param raf
     * @return
     * @throws IOException
     */
    public NoeudBinaire chercheretsupprimer(NoeudBinaire currentNode, NoeudBinaire nodeToDelete, NoeudBinaire parent, RandomAccessFile raf) throws IOException {
        try {
            if (currentNode == null) {
                return null; // node not found
            }
            System.out.println("Visiting node: " + currentNode.cle.getNom());
            //--------------------currentNode is not the node to delete ------------------------
            // If the nodeToDelete < currentNode, search in the left subtree
            if (currentNode.cle.getNom().trim().compareToIgnoreCase(nodeToDelete.cle.getNom().trim()) > 0) {
                System.out.println("Going left comparing " + currentNode.cle.getNom() + " " + nodeToDelete.cle.getNom() + ":" + currentNode.cle.getNom().trim().compareToIgnoreCase(nodeToDelete.cle.getNom()));
                long offset = (long) currentNode.filsGauche * TAILLE_MAX_NOEUD_OCTET;
                if (offset < 0) {
                    // handle the error
                    System.err.println("Invalid seek offset: " + offset);
                    System.err.println("Error: The file position is negative, cannot seek to the left subtree node.");
                    return null;
                }
                raf.seek(offset);
                // read the left child from the file
                NoeudBinaire leftChild = lireNoeud(raf);
                System.out.println("LeftChild index value :" + leftChild.index + "---------------------------------");
                // recursively call chercheNoeudSup on the left child
                return chercheretsupprimer(leftChild, nodeToDelete, currentNode, raf);
                // If the nodeToDelete > currentNode, search in the right
            } else if (currentNode.cle.getNom().trim().compareToIgnoreCase(nodeToDelete.cle.getNom().trim()) < 0) {
                // seek to the right child's position
                System.out.println("Going right " + currentNode.cle.getNom() + " " + nodeToDelete.cle.getNom() + ":" + currentNode.cle.getNom().trim().compareToIgnoreCase(nodeToDelete.cle.getNom()));
                long offset = (long) currentNode.filsDroit * TAILLE_MAX_NOEUD_OCTET;
                if (offset < 0) {
                    // handle the error
                    System.err.println("Invalid seek offset: " + offset);
                    System.err.println("Error: The file position is negative, cannot seek to the right subtree node.");
                    return null;
                }
                raf.seek(offset);
                // read the right child
                NoeudBinaire rightChild = lireNoeud(raf);
                // recursively call chercheNoeudSup on the right child
                return chercheretsupprimer(rightChild, nodeToDelete, currentNode, raf);

            } else {
                //---------------- currentNode is the node to delete----------------------------
                // if (currentNode.filsGauche == -1 && currentNode.filsDroit == -1) {
                // currentNode is a leaf node,delete
                System.out.println("Node Found" + currentNode.cle.getNom() + currentNode.index + " " + nodeToDelete.cle.getNom() + currentNode.index + ":" + currentNode.cle.getNom().trim().compareToIgnoreCase(nodeToDelete.cle.getNom()));
                //if (parent != null) {
                System.out.println("Calling suppimerNoeud with " + currentNode.getCle() + " parent " + parent.getCle() + " " + parent.index);
                supprimerNoeud(currentNode, parent.index, raf);
                //}

                return currentNode;
            }
        } catch (IOException e) {
            System.err.println("An IO exception occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
// parent parameter is not used directly in the chercheNoeudSup method.
// However, the method getSuccesseur that is called inside chercheNoeudSup takes a RandomAccessFile parameter and
// returns the successor of a given node along with its parent node.
// Therefore, parent is indirectly tracked through the use of the getSuccesseur method.

// parent parameter is not used directly in the chercheNoeudSup method.
// However, the method getSuccesseur that is called inside chercheNoeudSup takes a RandomAccessFile parameter and
// returns the successor of a given node along with its parent node.
// Therefore, parent is indirectly tracked through the use of the getSuccesseur method.
    
	/* ******************* FIN DE LA METHODE CHERCHER ET SUPPRIMER NOEUD ********************/

    
    

    
	/* ******************* GETTERS ET SETTERS ********************/

    private int comparerNom(StagiaireBinaire s1, StagiaireBinaire s2) {
        return s1.getNom().compareToIgnoreCase(s2.getNom());
    }

    public String getNomLong() {
        String nomLong = this.cle.getNom();
        for (int i = this.cle.getNom().length(); i < StagiaireBinaire.TAILLE_NOM; i++) {
            nomLong += " ";
        }
        return nomLong;
    }

    public String getPrenomLong() {
        String prenomLong = this.cle.getPrenom();
        for (int i = this.cle.getPrenom().length(); i < StagiaireBinaire.TAILLE_PRENOM; i++) {
            prenomLong += " ";
        }
        return prenomLong;
    }

    public String getDepartementLong() {
        String departementLong = this.cle.getDepartement();
        for (int i = this.cle.getDepartement().length(); i < StagiaireBinaire.TAILLE_DEPARTEMENT; i++) {
            departementLong += " ";
        }
        return departementLong;
    }

    public String getPromoLong() {
        String promoLong = this.cle.getPromo();
        for (int i = this.cle.getPromo().length(); i < StagiaireBinaire.TAILLE_PROMO; i++) {
            promoLong += " ";
        }
        return promoLong;
    }

    public String getAnneeLong() {
        String anneeLong = this.cle.getAnnee();
        for (int i = this.cle.getAnnee().length(); i < StagiaireBinaire.TAILLE_ANNEE; i++) {
            anneeLong += " ";
        }
        return anneeLong;
    }

    public StagiaireBinaire getCle() {
        return cle;
    }

    public void setCle(StagiaireBinaire cle) {
        this.cle = cle;
    }

    public int getFilsGauche() {
        return filsGauche;
    }

    public void setFilsGauche(int filsGauche) {
        this.filsGauche = filsGauche;
    }

    public int getFilsDroit() {
        return filsDroit;
    }

    public void setFilsDroit(int filsDroit) {
        this.filsDroit = filsDroit;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public NoeudBinaire getParent() {
        return parent;
    }

    public void setParent(NoeudBinaire parent) {
        this.parent = parent;
    }

}
