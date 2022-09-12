
package com.mycompany.ataskaituapp.dataWindow.domain;

import java.util.ArrayList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

//Kol kas dar sio kodo nenaudoju, bet gal praves ateityje

public class ListViewSettings {
    
    private ListView listView;
    private ArrayList<Publisher> list;
    
    public ListViewSettings(ListView listView, ArrayList<Publisher> list) {
        
        this.listView = listView;
        this.list = list;
        
    }
    
    public void addList() {
        
        this.listView.getItems().addAll(this.list);
        
        this.listView.setCellFactory(lv -> new ListCell<Publisher>() {
            @Override
            public void updateItem(Publisher pub, boolean empty) {
                super.updateItem(pub, empty);
                setText(empty ? null : pub.getKey());
            }
        });
    }
    
    public void updateList(ArrayList<Publisher> newList) {
        
        this.listView.getItems().clear();
        
        this.listView.getItems().addAll(newList);
        
        this.listView.setCellFactory(lv -> new ListCell<Publisher>() {
            @Override
            public void updateItem(Publisher pub, boolean empty) {
                super.updateItem(pub, empty);
                setText(empty ? null : pub.getKey());
            }
        });
    } 
    
}
