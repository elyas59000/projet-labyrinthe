module J4.sae_labyrinthe {
    requires javafx.controls;
    requires javafx.fxml;


    opens J4.sae_labyrinthe to javafx.fxml;
    exports J4.sae_labyrinthe;
}