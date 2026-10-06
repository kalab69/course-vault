module com.mycompany.course.vault.frontend {

    // ── JavaFX modules ────────────────────────────────────────────────
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.graphics;
    requires javafx.base;

    // ── Java modules ──────────────────────────────────────────────────
    requires java.net.http;
    requires java.desktop;
    requires java.logging;

    // ── Third party ───────────────────────────────────────────────────
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jdk.httpserver;

    // ── Open packages to JavaFX for FXML reflection ───────────────────
    opens com.mycompany.course.vault.frontend to javafx.fxml;
    opens frontendController to javafx.fxml;
    opens com.mycompany.model to
        javafx.fxml,
        com.fasterxml.jackson.databind;
    opens com.mycompany.service to javafx.fxml;
    opens com.mycompany.theme to javafx.fxml;

    // ── Exports ───────────────────────────────────────────────────────
    exports com.mycompany.course.vault.frontend;
}