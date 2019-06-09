/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Demande;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author abdelhalim.benjmila
 */
public class ClientListDemande {

    private Form form;
    private Resources theme;
    java.util.List<Map<String, Object>> services;
    java.util.List<Map<String, Object>> allprestataire;
    Picker pickerservice;
    ArrayList<Demande> demandes;
    InfiniteContainer ic;

    public ClientListDemande(Resources theme) {
        this.theme = theme;
        form = new Form(BoxLayout.y());
        form.getToolbar().hideToolbar();

        demandes = new ArrayList<>();
        getAllDemandeAccepter();
        ic = new InfiniteContainer() {
            Component[] cmps;

            @Override
            public Component[] fetchComponents(int index, int amount) {

                System.out.println(amount);
                if (index == 0) {
                    cmps = new Component[demandes.size()];
                }

                if (index + amount > demandes.size()) {
                    amount = demandes.size() - index;
                }

                if (amount <= 0) {
                    return null;
                }

                for (int i = 0; i < demandes.size(); i++) {
                    Container container = new Container(BoxLayout.x());
                    Image image = theme.getImage("avatar.png").scaled(172, 172);
                    ImageViewer imageViewer = new ImageViewer(image);
                    container.add(imageViewer);

                    Container container2 = new Container(BoxLayout.y());
                    Container container3 = new Container();
                    Container container4 = new Container();

                    Label nom = new Label(demandes.get(i).getNomclient());
                    Button buttondetail = new Button("détail");
                    container4.add(nom).add(buttondetail);
                    Label service = new Label(demandes.get(i).getTitle());
                    Button buttondemande = new Button("demande");

                    container3.add(service).add(buttondemande);

                    container2.add(container4).add(container3);
                    container.add(container2);
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

    private void getAllDemandeAccepter() {
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/demande/getAllDemandeAccepter/7");
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        java.util.List<Map<String, Object>> alldemande = (java.util.List<Map<String, Object>>) res.get("root");

                        for (Map<String, Object> p : alldemande) {
                            System.out.println(alldemande);
                            Demande demande = new Demande();
                            System.out.println((Double.parseDouble(p.get("id").toString())));
                            demande.setId((int) (Double.parseDouble(p.get("id").toString())));
                            demande.setNomclient(p.get("nom").toString()+ " "+ p.get("prenom").toString());
                            demande.setTitle(p.get("title").toString());
                            demande.setDescription(p.get("description").toString());
                            demande.setDateFunction(p.get("dateFunction").toString());
                            demande.setPrix((int) (Double.parseDouble(p.get("prix").toString())));
                            demandes.add(demande);
                            System.out.println(p);
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