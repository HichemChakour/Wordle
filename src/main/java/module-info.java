module wordle {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.jsoup;

    opens wordle to javafx.fxml;
    exports wordle;
}