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
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;

import com.mycompany.myapp.entity.Profile;
import com.service.ProfileService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;

/**
 *
 * @author macbook
 */
public class Signin {

    Form form;
    TextField mailTf, passTf;
    Button connectButton, inscrireButton;
    public static Profile roleProfile = new Profile();

    public Signin(Resources theme) {
        form = new Form(new FlowLayout(Component.CENTER, Component.CENTER));

        ImageViewer iv = new ImageViewer(theme.getImage("LogoMobile.png"));

        mailTf = new TextField();
        mailTf.setHint("Login");
        passTf = new TextField();
        passTf.setHint("••••••••••");

        connectButton = new Button("se connecter");

        inscrireButton = new Button("s'inscrire");
        inscrireButton.getAllStyles().setFgColor(0xFFFFFF);
        inscrireButton.getAllStyles().setBorder(Border.createEmpty());
        inscrireButton.getAllStyles().setBackgroundType(BACKGROUND_NONE);
        inscrireButton.getAllStyles().setBgTransparency(255);
        inscrireButton.getAllStyles().setBgColor(0x007bc2);

        inscrireButton.addActionListener((evt) -> {
            switcher switcher = new switcher(theme, form);
            switcher.getForm().show();
        });

        connectButton.addActionListener((evt) -> {
            ProfileService ps = new ProfileService();

            Profile p = ps.signin(mailTf.getText(), passTf.getText());
            roleProfile = p;
            if (p.getType() == 1) {

                //changer par halim
                Tabs home = new Tabs(theme);
                //home
                home.getForm().show();
            } else {
                //changer par halim
                TabsPrestataire home = new TabsPrestataire(theme);
                //home
                home.getForm().show();
            }

            System.out.println(roleProfile.getId());
        });

        Container c = new Container(BoxLayout.y());

        c.add(iv);
        c.add(mailTf);
        c.add(passTf);
        c.add(connectButton);
        c.add(inscrireButton);

        form.add(c);
    }

    public Form getForm() {
        return form;
    }

    public TextField getMailTf() {
        return mailTf;
    }

    public TextField getPassTf() {
        return passTf;
    }

    public Button getConnectButton() {
        return connectButton;
    }

}
