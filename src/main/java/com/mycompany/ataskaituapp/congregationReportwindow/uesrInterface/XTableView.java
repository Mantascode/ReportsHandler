
package com.mycompany.ataskaituapp.congregationReportwindow.uesrInterface;


import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

class XTableView<S> extends TableView<S> {
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        Pane header = (Pane) lookup("TableHeaderRow");
        header.setMinHeight(0);
        header.setPrefHeight(0);
        header.setMaxHeight(0);
        header.setVisible(false);
    }
}