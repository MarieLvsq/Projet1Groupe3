module isika.cda24.Projet1 {
	requires javafx.controls;
	 requires javafx.base;
	 requires itextpdf;
	 requires java.desktop;
	    
	    opens isika.cda24.Projet1.binaire to javafx.fxml, javafx.base, javafx.controls; 
	    exports isika.cda24.Projet1.binaire;
	    
	    opens isika.cda24.Projet1.program to javafx.fxml, javafx.base, javafx.controls; 
	    exports isika.cda24.Projet1.program;
	    
	    opens isika.cda24.Projet1.scene to javafx.fxml, javafx.base, javafx.controls, itextpdf; 
	    exports isika.cda24.Projet1.scene;
	}