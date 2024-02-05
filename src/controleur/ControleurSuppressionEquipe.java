package controleur;

import java.awt.event.ActionEvent;
import vue.PopupSuppressionEquipe;
import vue.VueAccueil;
import vue.VueDetailEquipe;
import vue.VueSuppressionEquipe;
import modele.ModeleSuppressionEquipe;
import ressources.Pages;
import ui.ButtonTableActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;

import modele.EquipeSaison;

public class ControleurSuppressionEquipe implements ButtonTableActionListener {

    private ModeleSuppressionEquipe modele;
    private VueSuppressionEquipe vue;

    private JTable table;
    private int row;

    public ControleurSuppressionEquipe(VueSuppressionEquipe vue) {
        this.vue = vue;
        this.modele = new ModeleSuppressionEquipe();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        switch (b.getActionCommand()) {
            case "delete":
                try {
                    PopupSuppressionEquipe popup = new PopupSuppressionEquipe(
                            this.table.getValueAt(this.row, 0).toString(), vue);
                    popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                    popup.setVisible(true);

                } catch (Exception exept) {
                    exept.printStackTrace();
                }
                break;
            case "detail":
                this.vue.changePage(new VueDetailEquipe(
                        this.modele.getEquipeByName(this.table.getValueAt(this.row, 0).toString()), vue));
                break;
            case "Retour":
                this.vue.changePage(Pages.ACCUEIL);
                break;
            case "ajout":
            	this.vue.changePage(Pages.CREATION_EQUIPE);
                break;
        }

    }

    /**
     * setTable et setRow permettent d'initialiser l'objet Table
     */
    @Override
    public void setTable(JTable table) {
        this.table = table;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return toutes les équipes de la saison en cours
     */
    public List<EquipeSaison> getAll() {
        return this.modele.getAll();
    }

}
