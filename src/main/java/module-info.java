module J4.sae_labyrinthe {
    requires javafx.controls;
    requires javafx.fxml;


    opens J4.sae_labyrinthe to javafx.fxml;
    exports J4.sae_labyrinthe;
    exports J4.sae_labyrinthe.controleur;
    opens J4.sae_labyrinthe.controleur to javafx.fxml;
}