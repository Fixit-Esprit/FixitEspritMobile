/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

public class Tabs extends SideMenuBaseForm {

    private Form form;
    private Resources theme;

    public Tabs(Resources theme) {
        form = new Form(new BorderLayout());
        createSideMenu(theme);
        com.codename1.ui.Tabs tb = new com.codename1.ui.Tabs() {
            @Override
            protected Component createTab(String title, Image icon) {
                SpanButton custom = new SpanButton(title);
                custom.setIcon(icon);
                custom.setUIID("Container");
                custom.setTextUIID("Tab");
                custom.setIconPosition(BorderLayout.NORTH);
                custom.setIconUIID("Tab");
                return custom;
            }

            @Override
            protected void setTabSelectedIcon(Component tab, Image icon) {
                ((SpanButton) tab).setPressedIcon(icon);
            }

            @Override
            protected void selectTab(Component tab) {

            }

            @Override
            protected void bindTabActionListener(Component tab, ActionListener l) {
                ((SpanButton) tab).addActionListener(l);
            }
        };

        tb.setTabUIID(null);
        tb.addTab("Prestataires", new Prestataires(theme).getForm());
        tb.addTab("localisation", new Localisation(theme).getForm());
        tb.addTab("liste des demandes", new ClientListDemande(theme).getForm());
        tb.addTab("liste des annonces", new ClientListAnnonce(theme).getForm());

        tb.getTabsContainer().setScrollableX(true);
        tb.setSwipeActivated(false);
        form.add(BorderLayout.CENTER, tb);

        form.show();
        setupSideMenu(theme);
    }

    public Form getForm() {
        return form;
    }

    public void createSideMenu(Resources theme) {
        Image profilePic = theme.getImage("Logo.png");
        Image mask = theme.getImage("Logo.png");
        mask = mask.scaledHeight(mask.getHeight() + 260 / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("", profilePic, "");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(new ImageViewer(profilePic));
        sidemenuTop.setUIID("SidemenuTop");

        form.getToolbar().addComponentToSideMenu(sidemenuTop);

        Label title = new Label("FIXIT TUNISIE");
        title.getStyle().setFgColor(0xFFFFFF);
        Container titleComponent
                = new Container(new BorderLayout());
        titleComponent.add(BorderLayout.WEST, new ImageViewer(theme.getImage("icon.png").scaled(94, 94))).
                add(BorderLayout.CENTER,title);
        titleComponent.setUIID("BottomPaddingContainer");
        form.getToolbar().setTitleComponent(titleComponent);

        form.getToolbar().addMaterialCommandToSideMenu("  Acceuil", FontImage.MATERIAL_DASHBOARD, e -> getToolbar().closeSideMenu());
        form.getToolbar().addMaterialCommandToSideMenu("  Annocer votre besoin", FontImage.MATERIAL_ADD_ALARM, e -> showOtherForm(theme));
        form.getToolbar().addMaterialCommandToSideMenu("  Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> showOtherForm(theme));

    }

    @Override
    protected void showOtherForm(Resources res) {
        new TabsPrestataire(res).getForm();
    }
}
