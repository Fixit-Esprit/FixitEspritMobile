/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.CN;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Offre;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class DetailOffre {
    Form form;
    Toolbar toolbar;
    Offre offre;
    Resources theme;
    
    public DetailOffre(Offre offre,Resources theme,Form parentForm){
        this.theme = theme;
        this.offre = offre;
        
        form = new Form(new FlowLayout(CN.CENTER, CN.CENTER));
        form.setTitle("Details offre");
        toolbar = form.getToolbar();
        
        toolbar.setBackCommand("back", (evt) -> {
            parentForm.showBack();
        });
        
        ImageViewer imageViewer = new ImageViewer(theme.getImage("project.png"));
        Label titre = new Label(offre.getTitre());
        Label description = new Label(offre.getDescription());
        
        form.add(imageViewer);
        form.add(titre);
        form.add(description);
    }
    
    public Form getForm(){
        return form;
    }
}
