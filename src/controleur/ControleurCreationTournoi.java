package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import modele.ModeleCreationTournoi;
import modele.ModeleListeTournois;
import modele.Tournoi;
import ressources.Pages;
import vue.VueCreationTournoi;
import vue.VueListeTournois;

public class ControleurCreationTournoi implements ActionListener, KeyListener, CaretListener {
    private enum Etat {
        ATTENTE_INFORMATIONS, INFORMATIONS_SAISIES
    }

    private VueCreationTournoi vue;
    private Etat etat;
    private ModeleCreationTournoi modele;
    private ModeleListeTournois modeleTransitif;

    /**
     * @param v VueCreationTournoi
     */
    public ControleurCreationTournoi(VueCreationTournoi v) {
        this.vue = v;
        this.etat = Etat.ATTENTE_INFORMATIONS;
        this.modele = new ModeleCreationTournoi();
        this.modeleTransitif = new ModeleListeTournois();
    }

    // S'occupe de la saisie au clavier
    private void updateState(KeyEvent e) {
        switch (this.etat) {
            // En fonction de si tous les champs sont saisis ou non on grise le bouton de
            // validation
            case ATTENTE_INFORMATIONS:
                if (this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(true);
                    this.etat = Etat.INFORMATIONS_SAISIES;
                }
                break;
            case INFORMATIONS_SAISIES:
                if (!this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(false);
                    this.etat = Etat.ATTENTE_INFORMATIONS;
                }
        }
    }

    // S'occupe des dates
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b.getActionCommand().equals("Retour")) {
        	Pages.LISTE_TOURNOIS.remplirTable(this.modeleTransitif.getAll());
            this.vue.changePage(Pages.LISTE_TOURNOIS);
        }
        switch (this.etat) {
            case ATTENTE_INFORMATIONS:
                if (this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(true);
                    this.etat = Etat.INFORMATIONS_SAISIES;
                }
                break;
            case INFORMATIONS_SAISIES:
                if (!this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(false);
                    this.etat = Etat.ATTENTE_INFORMATIONS;
                }
                if (b.getActionCommand().equals("CreationTournoi")) {
                    // On crée l'objet tournoi qui servira à ajouter le tournoi à la BD après
                    Tournoi tournoiCree = new Tournoi(this.vue.getNomTournoi(), this.vue.getLigue(),
                            this.vue.getDateDebut(),
                            this.vue.getDateFin(), ModeleCreationTournoi.generateLogin(),
                            ModeleCreationTournoi.generatePassword());
                    // Si le tournoi n'est pas valide, un message sera affiché par la méthode et
                    // false sera renvoyé
                    String msgTournoiValide = this.modele.verificationTournoiValide(tournoiCree);
                    if (msgTournoiValide.isEmpty()) {
                        this.modele.addTournoi(tournoiCree);
                        Pages.LISTE_TOURNOIS.remplirTable(this.modeleTransitif.getAll());
                        this.vue.changePage(Pages.LISTE_TOURNOIS);
                    } else {
                        this.vue.afficherMessageErreur(msgTournoiValide);
                    }
                }
        }
    }

    //
    /**
     * Listener de la saisie au clavier pour le nom du tournoi
     */
    @Override
    public void keyPressed(KeyEvent arg0) {
        updateState(arg0);
    }

    /**
     *
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
        updateState(arg0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        updateState(arg0);
    }

    // Déteste les saisies dans les DatePickers
    @Override
    public void caretUpdate(CaretEvent e) {
        switch (this.etat) {
            case ATTENTE_INFORMATIONS:
                if (this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(true);
                    this.etat = Etat.INFORMATIONS_SAISIES;
                }
                break;
            case INFORMATIONS_SAISIES:
                if (!this.vue.informationsSaisies()) {
                    this.vue.setBoutonValidateVisible(false);
                    this.etat = Etat.ATTENTE_INFORMATIONS;
                }
        }
    }

}
