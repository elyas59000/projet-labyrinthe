module J4.sae_labyrinthe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    opens J4.sae_labyrinthe.modele to javafx.base;
    //requires org.junit.jupiter.api;


    opens J4.sae_labyrinthe to javafx.fxml;
    exports J4.sae_labyrinthe;
    exports J4.sae_labyrinthe.controleur;
    opens J4.sae_labyrinthe.controleur to javafx.fxml;
    opens J4.sae_labyrinthe.utils to javafx.base;
}