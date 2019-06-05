/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Prestataire;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static javafx.scene.input.KeyCode.T;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class Prestataires {

    private Form form;
    private Resources theme;
    java.util.List<Map<String, Object>> services;
    Picker pickerservice;

    public Prestataires(Resources theme) {
        this.theme = theme;
        form = new Form(BoxLayout.y());
        form.getToolbar().hideToolbar();
        getService();
        pickerservice = new Picker();
        pickerservice.setText("Catégorie de votre besoin");
        form.add(pickerservice);
        ArrayList<Prestataire> images = new ArrayList<>();
        images.add(new Prestataire(0, 1, "add", "add", "add", "add", "add", 1, "add", "add", "add"));
        images.add(new Prestataire(0, 1, "add", "add", "add", "add", "add", 1, "add", "add", "add"));
        InfiniteContainer ic = new InfiniteContainer() {
            Component[] cmps;

            @Override
            public Component[] fetchComponents(int index, int amount) {

                System.out.println(amount);
                if (index == 0) {
                    cmps = new Component[images.size()];
                }

                if (index + amount > images.size()) {
                    amount = images.size() - index;
                }

                if (amount <= 0) {
                    return null;
                }

                for (int i = 0; i < images.size(); i++) {
                    Container container = new Container(BoxLayout.x());
                    /**
                     * ImageViewer imageViewer = new
                     * ImageViewer(theme.getImage(images.get(i).getImage()));
                     * container.add(imageViewer);
                     */
                    Container container2 = new Container(BoxLayout.y());
                    Label nom = new Label(images.get(i).getNom() + "   " + images.get(i).getCin());
                    container2.add(nom);
                    Label total = new Label("Total rate :" + images.get(i).getEmail() + "/");
                    container2.add(total);

                    container.add(container2);
                    Button button = new Button("");
                    /*  Form2 form2 = new Form2(theme, images.get(i));
                    Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
                    FontImage icon1 = FontImage.createMaterial(FontImage.MATERIAL_ASSIGNMENT_RETURN, s);
                    FontImage icon2 = FontImage.createMaterial(FontImage.MATERIAL_ADD, s);
                    form2.getForm().getToolbar().addCommandToOverflowMenu("Back to list", icon1, (e) -> {
                        form.showBack();
                    });
                    form2.getForm().getToolbar().addCommandToOverflowMenu("Add to favourites", icon2, (e) -> {
                        Dialog.show("", "Weapon already added", "ok", "");
                    });
                    button.addActionListener((e) -> form2.getForm().show());*/
                    container.setLeadComponent(button);
                    cmps[i] = container;

                }

                return cmps;
            }
        };
        form.add(ic);
    }

    public Form getForm() {
        return form;
    }

    private void getService() {

        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/service");
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        services = (java.util.List<Map<String, Object>>) res.get("root");

                        String[] listservice = new String[services.size()];
                        int i = 0;
                        for (Map<String, Object> obj : services) {
                            listservice[i] = obj.get("description").toString();
                            i++;
                        }
                        pickerservice.setStrings(listservice);
                        pickerservice.setText("Catégorie de votre besoin");
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }

    }
}