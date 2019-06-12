 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources; 
import com.mycompany.myapp.entity.Profile;
import com.service.ProfileService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class Validationcode {
    Form form;
    Toolbar toolbar;
    TextField codeTf;
    Resources theme;
    Button connectButton;
     public Validationcode(Resources theme){
        form = new Form(new FlowLayout(Component.CENTER,Component.CENTER));
        
       ImageViewer iv = new ImageViewer(theme.getImage("LogoMobile.png"));
        
        codeTf = new TextField();
        codeTf.setHint("code");
        
        
        connectButton = new Button("Valider");
         
   
        
        connectButton.addActionListener((evt) -> {
            ProfileService ps = new ProfileService();
            
            //Profile p = ps.validationcode(codeTf.getText());
            
            if (ps.validationcode(codeTf.getText())){
     
                 //changer par halim
                 Signin Signin = new Signin(theme);
                //home
                Signin.getForm().show();
            }  
           
        });
        
        Container c = new Container(BoxLayout.y());
        
        c.add(iv);
        c.add(codeTf);
         
        c.add(connectButton);
        
        
        form.add(c);
    }

    public Form getForm() {
        return form;
    }

    public TextField codeTf() {
        return codeTf;
    }

     
    public Button getConnectButton() {
        return connectButton;
    }
    
 
}
