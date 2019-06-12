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
import com.mycompany.myapp.entity.Offre;
import com.mycompany.myapp.entity.Profile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author macbook
 */
public class OffreService {
    ArrayList<Offre> offres = new ArrayList<>();
    
    public List<Offre> getOffres() {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/smartstart/offre.php";// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);
              OffreService ser = new OffreService();
              offres = ser.parseListTaskJson(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        
        
        return offres;
    }
    
    public ArrayList<Offre> parseListTaskJson(String json) {

        ArrayList<Offre> listOffre = new ArrayList<>();

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
                Offre o = new Offre();
                
                
                float id = Float.parseFloat(obj.get("id_offre").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                float idCat = Float.parseFloat(obj.get("id_categorie").toString());
                
                o.setId((int) id);
                o.setPrix((int)prix);
                o.setIdCategorie((int)idCat);
                o.setTitre(obj.get("titre").toString());
                o.setDescription(obj.get("description").toString());
                
                listOffre.add(o);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listOffre);
        return listOffre;

    }
}
