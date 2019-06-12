package com.mycompany.myapp.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author macbook
 */
public class Profile {

    private int id, type,idpays,idreg,idville,secteur,code;
    private String nom, prenom, password, mail,login,phone;

    public Profile() {
    }

    public Profile(String nom, String prenom, String mail, String login, String password, String phone, int idpays, int idreg, int idville, int type,int code) {

        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.idpays = idpays;
        this.idreg = idreg;
        this.idville = idville;
 this.type = type;
        this.code = code;
    }

    public Profile(String nom, String prenom, String mail, String login, String password, String phone, int idpays, int idreg, int idville, int type,int secteur,int code) {

        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.idpays = idpays;
        this.idreg = idreg;
        this.idville = idville;
        this.type = secteur;
        this.type = type;
        this.code = code;
    }
    public Profile(int id, String nom, String prenom, String password, String mail, int type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mail = mail;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

 
    public int getIdpays() {
        return idpays;
    }

    public int getIdreg() {
        return idreg;
    }

    public int getIdville() {
        return idville;
    }

    public String getLogin() {
        return login;
    }

    public String getPhone() {
        return phone;
    }

    public void setIdpays(int idpays) {
        this.idpays = idpays;
    }

    public void setIdreg(int idreg) {
        this.idreg = idreg;
    }

    public int getSecteur() {
        return secteur;
    }

    public void setIdville(int idville) {
        this.idville = idville;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCode() {
        return code;
    }

    public void setSecteur(int secteur) {
        this.secteur = secteur;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
   @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profile other = (Profile) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile{" + "id=" + id + ", type=" + type + ", idpays=" + idpays + ", idreg=" + idreg + ", idville=" + idville + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", mail=" + mail + ", login=" + login + ", phone=" + phone + '}';
    }
 

}
