package controleur;

import java.util.List;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modele.Equipe;
import modele.EquipeSaison;
import modele.ModeleGestionTournoi;
import vue.VueGestionTournoi;

public class ControleurGestionTournoi implements ActionListener, MouseListener {
    public enum Etat {
        ATTENTE_SELECTION, SELECTION_EN_COURS, ETAT3
    }

    private VueGestionTournoi vue;
    private ModeleGestionTournoi modele;
    private Etat etat;
    private EquipeSaison equipeSelectionne;
    private EquipeSaison equipeSelectionneTable;

    public ControleurGestionTournoi(VueGestionTournoi vue) throws Exception {
        this.vue = vue;
        this.modele = new ModeleGestionTournoi(this.vue.getTournoi());
        this.etat = Etat.ATTENTE_SELECTION;
        this.equipeSelectionne = null;
        this.equipeSelectionneTable = null;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (equipeSelectionne != null)
        if (equipeSelectionneTable != null)
        switch (this.etat) {
            case ATTENTE_SELECTION:
                if (!this.vue.getComboBoxEquipes().getSelectedItem().equals("Sélectionnez une équipe")) {
                    this.etat = Etat.SELECTION_EN_COURS;
                    equipeSelectionne = this.modele
                            .getEquipeByName(this.vue.getComboBoxEquipes().getSelectedItem().toString());
                }
                break;
            case SELECTION_EN_COURS:
                if (this.vue.getComboBoxEquipes().getSelectedItem().equals("Sélectionnez une équipe")
                        && !this.vue.isRowSelected()) {
                    this.etat = Etat.ATTENTE_SELECTION;
                } else if (this.vue.isRowSelected()
                        && this.vue.getComboBoxEquipes().getSelectedItem().equals("Sélectionnez une équipe")) {
                    this.etat = Etat.ETAT3;
                    this.vue.remplirComboBoxEquipes(getEquipes());
                    equipeSelectionneTable = this.modele
                            .getEquipeByName(this.vue.getRowSelected());
                } else if (e != null && e.getActionCommand().equals("addEquipe")) {
                    this.modele.selectEquipe(equipeSelectionne);
                    this.vue.remplirComboBoxEquipes(getEquipes());
                    this.vue.remplirListeEquipesParticipantes(getEquipesParticipantes());
                    this.etat = Etat.ATTENTE_SELECTION;

                }
                break;
            case ETAT3:
                if (!this.vue.getComboBoxEquipes().getSelectedItem().equals("Sélectionnez une équipe")) {
                    this.equipeSelectionne = this.modele
                            .getEquipeByName(this.vue.getComboBoxEquipes().getSelectedItem().toString());
                    this.etat = Etat.SELECTION_EN_COURS;
                }
                if (e != null && e.getActionCommand().equals("deleteEquipe")) {
                    int choix = JOptionPane.showConfirmDialog(null,
                            "Voulez-vous vraiment désinscrire l'équipe " + equipeSelectionneTable.getNom() + " ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {
                        this.modele.unselectEquipe(equipeSelectionneTable);
                        this.vue.remplirComboBoxEquipes(getEquipes());
                        this.vue.remplirListeEquipesParticipantes(getEquipesParticipantes());
                        this.etat = Etat.ATTENTE_SELECTION;
                    } else {
                        this.etat = Etat.ETAT3;
                    }
                }

                break;
        }

    }

    /**
     * @return les noms des équipes disponibles
     */
    public List<String> getEquipes() {
        return this.modele.getEquipesDispos();
    }

    /**
     * @return les noms des équipes sélectionnées
     */
    public List<String> getEquipesParticipantes() {
        return this.modele.getEquipesSelectionnees();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (etat) {
            case ATTENTE_SELECTION:
                this.etat = Etat.ETAT3;
                this.vue.remplirComboBoxEquipes(getEquipes());
                equipeSelectionneTable = this.modele.getEquipeByName(this.vue.getRowSelected());

                break;
            case SELECTION_EN_COURS:
                this.etat = Etat.ETAT3;
                this.vue.remplirComboBoxEquipes(getEquipes());
                equipeSelectionneTable = this.modele.getEquipeByName(this.vue.getRowSelected());
                break;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
