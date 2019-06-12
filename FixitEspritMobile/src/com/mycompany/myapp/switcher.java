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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Profile;
import com.service.ProfileService;
/**
 *
 * @author EXTHONE-marwa
 */
public class switcher {
    Form form;
    Button prestButton,clientButton;
    Toolbar toolbar;
    public switcher(Resources theme,Form parentForm){
        form = new Form(new FlowLayout(Component.CENTER,Component.CENTER));
        toolbar = form.getToolbar();
        toolbar.setBackCommand("back", (evt) -> {
            parentForm.showBack();
        });
        
       ImageViewer iv = new ImageViewer(theme.getImage("LogoMobile.png"));
     
        
        prestButton = new Button("Inscription en tant que prestataire");
       
        
        clientButton = new Button("Inscription en tant qu'utilisateur");
        clientButton.getAllStyles().setFgColor(0xFFFFFF);
        clientButton.getAllStyles().setBorder(Border.createEmpty());
        clientButton.getAllStyles().setBackgroundType(BACKGROUND_NONE);
        clientButton.getAllStyles().setBgTransparency(255);
        clientButton.getAllStyles().setBgColor(0x007bc2); 
        
        clientButton.addActionListener((evt) -> {
            Signup signup = new Signup(theme,form);
            signup.getForm().show();
        });
        
        prestButton.addActionListener((evt) -> {
         SignupP signupP = new SignupP(theme,form);
         signupP.getForm().show();
        });
        
        Container c = new Container(BoxLayout.y());
        
        c.add(iv);        
        c.add(clientButton);
        c.add(prestButton);
        
        form.add(c);
    }

 

    public Form getForm() {
        return form;
    }
}
