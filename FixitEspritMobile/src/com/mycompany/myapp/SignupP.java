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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entity.Profile;
import com.service.ProfileService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author macbook
 */
public class SignupP {
    Form form;
    TextField mailTf,passTf,nomTf,prenomTf,loginTf,phoneTf;
    Button connectButton;
    Toolbar toolbar;
    java.util.List<Map<String, Object>> services;   
    java.util.List<Map<String, Object>> pays;
    java.util.List<Map<String, Object>> region;
    java.util.List<Map<String, Object>> ville;
    Picker pickerpays;
    Picker pickerregion;
    Picker pickerville;
    Picker pickerservice;
    int idpays,idreg,idville,idservice;
    public SignupP(Resources theme,Form parentForm){
        form = new Form(new FlowLayout(Component.CENTER,Component.CENTER));
        toolbar = form.getToolbar();
        toolbar.setBackCommand("back", (evt) -> {
            parentForm.showBack();
        });
        
        ImageViewer iv = new ImageViewer(theme.getImage("LogoMobile.png"));
        
        loginTf = new TextField();
        loginTf.setHint("Login");
        passTf = new TextField();
        passTf.setHint("••••••••••");
        nomTf = new TextField();
        nomTf.setHint("Nom");
        prenomTf = new TextField();
        prenomTf.setHint("Prenom");
        
        mailTf = new TextField();
        mailTf.setHint("exp@exemple.com");
        
        phoneTf = new TextField();
        phoneTf.setHint("Téléphone");
        
     
        
        connectButton = new Button("S'inscrire");
        
        connectButton.addActionListener((evt) -> {
            System.out.println("service"+idservice);
                  Random r = new Random();
            //int verifCode = r.nextInt(9999);
            int verifCode = r.nextInt((9999 - 1000) + 1) + 1000;
            String ACCOUNT_SID = "AC53695a0810423ac99cc123e2d09a000d";
            String AUTH_TOKEN = "e82e3599383dd1019008b436610c8a9e";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String verifMessage = "Votre code de vérification est " + verifCode;
            Message message = Message.creator(new PhoneNumber("+216"+phoneTf.getText() ),
                    new PhoneNumber("+12568134657"),
                    verifMessage).create();
            ProfileService service = new ProfileService();
            Profile p = new Profile(nomTf.getText(), prenomTf.getText(),  mailTf.getText(),loginTf.getText(),passTf.getText(),phoneTf.getText(),idpays,idreg,idville,2,idservice,verifCode);
            
            if (service.signupp(p)){
                Validationcode validationcode = new Validationcode(theme);
                //home
                validationcode.getForm().show();
            }else{
                System.out.println("Utilisateur non ajouté");
            }
        });
        getService();
        pickerservice = new Picker();    
        pickerservice.addActionListener((a) -> {
            if (pickerpays.getValue() != null) {
                System.out.print(pickerpays.getValue().toString());
                for (Map<String, Object> services : services) {
                    if (services.get("description").toString().equals(pickerservice.getValue().toString())) {
                        getRegion((int) (Double.parseDouble(services.get("id").toString())));
                        idservice=(int) (Double.parseDouble(services.get("id").toString()));
                        System.out.println("service"+idservice);
                    }
                }
            }  
        });
        getPays();
        getRegion(205) ;
        getVille(2398202);
        pickerpays = new Picker();
        pickerpays.addActionListener((a) -> {
            if (pickerpays.getValue() != null) {
                System.out.print(pickerpays.getValue().toString());
                for (Map<String, Object> pays : pays) {
                    if (pays.get("nom").toString().equals(pickerpays.getValue().toString())) {
                        getRegion((int) (Double.parseDouble(pays.get("id").toString())));
                        idpays=(int) (Double.parseDouble(pays.get("id").toString()));
                    }
                }
            }  
        });
        pickerregion = new Picker();
       
        pickerregion.addActionListener((a) -> {
            if (pickerregion.getValue() != null) {
                System.out.print(pickerregion.getValue().toString());
                for (Map<String, Object> region : region) {
                    if (region.get("nom").toString().equals(pickerregion.getValue().toString())) {
                        getVille((int) (Double.parseDouble(region.get("id").toString())));
                        idreg=(int) (Double.parseDouble(region.get("id").toString()));
                    }
                }
            }  
        });
        pickerville = new Picker();
        pickerville.addActionListener((a) -> {
            if (pickerville.getValue() != null) {
                System.out.print(pickerregion.getValue().toString());
                for (Map<String, Object> ville : ville) {
                    if (ville.get("nom").toString().equals(pickerregion.getValue().toString())) {
                          idville=(int) (Double.parseDouble(ville.get("id").toString()));
                    }
                }
            }  
        });
      
        Container container = new Container(BoxLayout.y());
        container.add(iv);
        container.add(loginTf);
        container.add(passTf);
        container.add(nomTf);
        container.add(prenomTf);
        container.add(mailTf);
        container.add(phoneTf);        
        container.add(pickerservice);
        container.add(pickerpays);
        container.add(pickerregion);
        container.add(pickerville);
        container.add(connectButton);
        
        form.add(container);
    }
    
    public Form getForm(){
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
                        pickerservice.setText("Secteur");
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }
      private void getPays() {

        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/pays");
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        pays = (java.util.List<Map<String, Object>>) res.get("root");

                        String[] listpays = new String[pays.size()];
                        int i = 0;
                        for (Map<String, Object> obj : pays) {
                            listpays[i] = obj.get("nom").toString();
                            i++;
                        }
                        pickerpays.setStrings(listpays);
                        pickerpays.setText("Pays");
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }

    private void getRegion(int payid) {
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/region/" + payid);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        region = (java.util.List<Map<String, Object>>) res.get("root");

                        String[] listpays = new String[region.size()];
                        int i = 0;
                        for (Map<String, Object> obj : region) {
                            listpays[i] = obj.get("nom").toString();
                            i++;
                        }
                        pickerregion.setStrings(listpays);
                        pickerregion.setText("Region");
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }
    private void getVille(int regid) {
        try {
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl(MyApplication.baseUrl + "/ville/" + regid);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent r) {
                    try {
                        Map<String, Object> res = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getConnectionRequest().getResponseData()), "UTF-8"));
                        ville = (java.util.List<Map<String, Object>>) res.get("root");

                        String[] listpays = new String[ville.size()];
                        int i = 0;
                        for (Map<String, Object> obj : ville) {
                            listpays[i] = obj.get("nom").toString();
                            i++;
                        }
                        pickerville.setStrings(listpays);
                        pickerville.setText("Ville");
                    } catch (Exception ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueue(req);
        } catch (Exception ex) {
        }
    }
}
