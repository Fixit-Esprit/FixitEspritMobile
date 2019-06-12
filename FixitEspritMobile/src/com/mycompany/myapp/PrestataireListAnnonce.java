/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Annonce;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class PrestataireListAnnonce {

    private Form form;
    private Resources theme;
    java.util.List<Map<String, Object>> services;
    java.util.List<Map<String, Object>> allprestataire;
    Picker pickerservice;
    ArrayList<Annonce> annonces;
    InfiniteContainer ic;
    int idservice;

    public PrestataireListAnnonce(Resources theme) {
        this.theme = theme;
        form = new Form(BoxLayout.y());
        form.getToolbar().hideToolbar();

        annonces = new ArrayList<>();
        getIdServiceByIdPrestataire();

        ic = new InfiniteContainer() {
            Component[] cmps;

            @Override
            public Component[] fetchComponents(int index, int amount) {

                System.out.println(amount);
                if (index == 0) {
                    cmps = new Component[annonces.size()];
                }

                if (index + amount > annonces.size()) {
                    amount = annonces.size() - index;
                }

                if (amount <= 0) {
                    return null;
                }

                for (int i = 0; i < annonces.size(); i++) {
                    Container container = new Container(BoxLayout.y());

                    Container container2 = new Container(new BorderLayout());
                    Container container3 = new Container(new BorderLayout());
                    Container container4 = new Container(new BorderLayout());

                    Label title = new Label("Title : " + annonces.get(i).getTitle());
                    Label description = new Label("description :" + annonces.get(i).getDescription());
                    container3.add(BorderLayout.WEST, title);
                    Label nomclient = new Label("Client : " + annonces.get(i).getNomclient());
                    container.add(container3).add(description).add(nomclient);

                    Label nomprestataire = new Label("Min prix :" + String.valueOf(annonces.get(i).getMinprix()) + " DT");
                    Label date = new Label("Max prix :" + String.valueOf(annonces.get(i).getMaxprix()) + " DT");
                    container4.add(BorderLayout.WEST, nomprestataire).add(BorderLayout.EAST, date);

                    Button buttondemande = new Button("Accepte");
                    container2.add(BorderLayout.EAST, buttondemande);

                    container.add(container4).add(container2);
                    Style containerStyle = container.getAllStyles();
                    containerStyle.setMargin(10, 10, 10, 10);
                    containerStyle.setBorder(
                            RoundRectBorder.create().cornerRadius(2).
                                    strokeColor(0).
                                    strokeOpacity(100).
                                    stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.CAP_SQUARE, 1))
                    );

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

    private void getAnnoncePublier(int idservice) {
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/annonce/getAnnoncePublier/" + idservice);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        java.util.List<Map<String, Object>> allAnnonce = (java.util.List<Map<String, Object>>) res.get("root");

                        for (Map<String, Object> p : allAnnonce) {
                            System.out.println(allAnnonce);
                            Annonce annonce = new Annonce();
                            System.out.println((Double.parseDouble(p.get("id").toString())));
                            annonce.setId((int) (Double.parseDouble(p.get("id").toString())));
                            annonce.setTitle(p.get("title").toString());
                            annonce.setDescription(p.get("description").toString());
                            annonce.setMinprix((int) (Double.parseDouble(p.get("minprix").toString())));
                            annonce.setMaxprix((int) (Double.parseDouble(p.get("maxprix").toString())));
                            annonce.setNomclient(p.get("nom").toString() +" "+ p.get("prenom").toString());
                            annonces.add(annonce);
                            System.out.println(p);
                        }
                        ic.refresh();
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }

    private void getIdServiceByIdPrestataire() {
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/annonce/getIdServiceByIdPrestataire/10");
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        java.util.List<Map<String, Object>> id = (java.util.List<Map<String, Object>>) res.get("root");

                        for (Map<String, Object> list : id) {
                            System.out.println(id);
                            idservice = (int) (Double.parseDouble(list.get("id").toString()));
                            getAnnoncePublier(idservice);
                            System.out.println(list);
                        }

                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }
}
