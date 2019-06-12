/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service; 
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.mycompany.myapp.MyApplication;


import com.mycompany.myapp.entity.Profile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author macbook
 */
public class ProfileService {
    ArrayList<Profile> profiles = new ArrayList<>();
    boolean success = false;
    
    public boolean signup(Profile p){
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
      /*  String Url = "";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion*/
       
        con.setUrl(MyApplication.baseUrl + "/signup/"+p.getNom()+"/"+p.getPrenom()+"/"+p.getMail()+"/"+p.getLogin()+"/"+p.getPassword()+"/"+p.getPhone()+"/"+p.getIdpays()+"/"+p.getIdreg()+"/"+p.getIdville()+"/"+p.getType()+"/"+p.getCode());
        con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
           System.out.println("ici retour"+str);
            success = str.equals("Success");
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return success;
    }
     public boolean signupp(Profile p){
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
      /*String Url = "";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion*/
       
        con.setUrl(MyApplication.baseUrl + "/signupp/"+p.getNom()+"/"+p.getPrenom()+"/"+p.getMail()+"/"+p.getLogin()+"/"+p.getPassword()+"/"+p.getPhone()+"/"+p.getIdpays()+"/"+p.getIdreg()+"/"+p.getIdville()+"/"+p.getType()+"/"+p.getSecteur()+"/"+p.getCode());
        con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println("ici retour"+str);
            success = str.equals("Success");
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return success;
    }
    public Profile signin(String nom,String password) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        con.setUrl(MyApplication.baseUrl + "/login/"+nom+"/"+password);
        con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
          System.out.println(">>"+str);
            ProfileService ser = new ProfileService();
            profiles = ser.parseListTaskJson(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        if (profiles.get(0) == null)
                return null;
        
        return profiles.get(0);
    }
        public boolean validationcode(String code) {
             
        Profile p = new Profile();
        System.out.println(">>"+p.getId());
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        con.setUrl(MyApplication.baseUrl + "/validation/"+code);
        con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println("ici retour"+str);
            success = str.equals("Success");
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return success;
    }
    
    public ArrayList<Profile> parseListTaskJson(String json) {

        ArrayList<Profile> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Profile p = new Profile();
             //  System.out.println("email from bd "+obj.get("email").toString());
                float id = Float.parseFloat(obj.get("id").toString());

                p.setId((int) id);
                p.setNom(obj.get("nom").toString());
                p.setPrenom(obj.get("prenom").toString());
                p.setPassword(obj.get("motdepasse").toString());
                p.setMail(obj.get("email").toString());
                float type = Float.parseFloat(obj.get("type").toString());

                p.setType((int) type);
               
               
                listTasks.add(p);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listTasks);
        return listTasks;

    }

}
