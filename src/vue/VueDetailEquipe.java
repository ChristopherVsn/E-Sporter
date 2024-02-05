package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.header.User;
import composant.table.GoldAndWhiteTable;
import controleur.ControleurDetailEquipe;
import modele.Equipe;
import modele.Joueur;
import modele.Tournoi;
import ressources.CharteGraphique;
import ressources.Images;

public class VueDetailEquipe extends VueMain {

    /**
     * Create the panel.
     */
    private ControleurDetailEquipe controleur;
    private Equipe equipe;
    private DefaultTableModel modeleTable;
    private DefaultTableModel modeleTableJoueurs;
    private GoldAndWhiteTable tblHistorique;
    private GoldAndWhiteTable tblJoueurs;
    private GoldUnderlinedButton btnRetour = new GoldUnderlinedButton("Retour");
    private JScrollPane scrollPaneHistorique;
    private JPanel panelBody;
    private static ImageIcon imgTournoi = new ImageIcon(VueAccueil.class.getResource("/pictures/img/tournoi.png"));

    public VueDetailEquipe(Equipe equipe, VueMain vuePrecedente) {
        // double responsiveSizeWidth = VueDetailEquipe.this.getWidth() * 0.1;
        // double responsiveSizeHeight = VueDetailEquipe.this.getHeight() * 0.05;
        this.equipe = equipe;
        this.controleur = new ControleurDetailEquipe(this, vuePrecedente);

        this.modeleTable = new DefaultTableModel(new String[] { "Saison", "Tournoi", "Ligue", "Rank" }, 0);
        this.modeleTableJoueurs = new DefaultTableModel(new String[] { "Joueurs" }, 0);

        panelBody = new JPanel();
        panelBody.setBorder(new EmptyBorder(50, 100, 50, 100));
        add(panelBody, BorderLayout.CENTER);
        panelBody.setLayout(new BorderLayout(0, 0));

        scrollPaneHistorique = new JScrollPane();
        scrollPaneHistorique.setOpaque(false);
        scrollPaneHistorique.setBackground(new Color(9, 0, 43));
        scrollPaneHistorique.setPreferredSize(new Dimension(500, 10));
        panelBody.add(scrollPaneHistorique, BorderLayout.EAST);

        tblHistorique = new GoldAndWhiteTable();
        scrollPaneHistorique.setViewportView(tblHistorique);
        scrollPaneHistorique.getViewport().setOpaque(false);
        scrollPaneHistorique.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        JPanel panelPlaceIcon = new JPanel();
        panelPlaceIcon.setOpaque(false);
        panelPlaceIcon.setBorder(new EmptyBorder(0, 50, 0, 50));
        panelPlaceIcon.setLayout(new BorderLayout());
        panelBody.add(panelPlaceIcon, BorderLayout.CENTER);

        JPanel panelIcon = new JPanel();
        panelPlaceIcon.add(panelIcon, BorderLayout.NORTH);
        panelIcon.setLayout(new BorderLayout(0, 0));

        JPanel panelButtons = new JPanel();
        panelBody.add(panelButtons, BorderLayout.SOUTH);
        panelButtons.setLayout(new BorderLayout(0, 0));

        btnRetour = new GoldUnderlinedButton("Retour");
        btnRetour.addActionListener(controleur);
        panelButtons.add(btnRetour, BorderLayout.WEST);
        btnRetour.setActionCommand("retour");
        panelHeader.setOpaque(false);
        panelBody.setOpaque(false);
        panelButtons.setOpaque(false);
        panelIcon.setOpaque(false);
        panelButtons.setOpaque(false);

        JLabel lblSaisonEnCours = new JLabel("Saison " + Calendar.getInstance().get(Calendar.YEAR));
        lblSaisonEnCours.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaisonEnCours.setForeground(CharteGraphique.WHITE);
        lblSaisonEnCours.setFont(CharteGraphique.ITALIC_TITLE);
        panelIcon.add(lblSaisonEnCours, BorderLayout.NORTH);


        JPanel panelImage = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2D;
				g2D = (Graphics2D) grphcs;
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				Image cup;
				String wr = VueDetailEquipe.this.controleur.getWr(equipe);
				switch(wr) {
				case "-1":
					cup = Images.CUP_UNDEFINED.getImage();
					break;
				case "1":
					cup = Images.CUP_GOLD.getImage();
					break;
				case "2":
					cup = Images.CUP_SILVER.getImage();
					break;
				case "3":
					cup = Images.CUP_COPPER.getImage();
					break;
				default:
					cup = Images.CUP_WOOD.getImage();
					break;
				}
				AffineTransform aTran = new AffineTransform();
				float facteurDeReduction = 0.2F;
				int start = (this.getWidth()/2) - 118;
				aTran.translate(start, 0);
				aTran.scale(facteurDeReduction, facteurDeReduction);
				g2D.drawImage(cup, aTran , null);
			}
		};
		
		JPanel panelMarginImage = new JPanel();
		panelMarginImage.setOpaque(false);
		panelMarginImage.setBorder(new EmptyBorder(0,0,100,0));
		
		
        panelImage.setPreferredSize(new Dimension(10, 250));
        panelImage.setOpaque(false);
        panelIcon.add(panelImage, BorderLayout.CENTER);
        panelImage.setLayout(new BorderLayout(0, 0));
        JLabel test = new JLabel(this.controleur.getWr(equipe));
        JLabel lblScore = new JLabel("Score " + this.controleur.getScore(equipe));
        if(this.controleur.getWr(equipe).equals("-1")) {
        	test.setText("?");
        	test.setForeground(CharteGraphique.BLACK);
        	lblScore.setText("L'équipe ne participe pas à la saison en cours");
        } else {
            test.setForeground(CharteGraphique.WHITE);
        }
        test.setHorizontalAlignment(SwingConstants.CENTER);

        test.setFont(new Font("Georgia", Font.BOLD, 40));
        panelImage.add(panelMarginImage);
        panelMarginImage.setLayout(new BorderLayout(0, 0));
        panelMarginImage.add(test);


        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        panelIcon.add(lblScore, BorderLayout.SOUTH);
        lblScore.setForeground(CharteGraphique.WHITE);
        lblScore.setFont(CharteGraphique.ITALIC_TITLE);

        JScrollPane scrollPaneJoueurs = new JScrollPane();
        scrollPaneJoueurs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneJoueurs.setOpaque(false);
        panelBody.add(scrollPaneJoueurs, BorderLayout.WEST);
        tblJoueurs = new GoldAndWhiteTable();
        tblHistorique.setEnabled(false);
        tblJoueurs.setEnabled(false);
        scrollPaneJoueurs.setViewportView(tblJoueurs);
        scrollPaneJoueurs.getViewport().setOpaque(false);
        tblJoueurs.setOpaque(false);
        tblJoueurs.setPreferredScrollableViewportSize(new Dimension(100, 200));
        
        
        
        scrollPaneJoueurs.setBorder(new EmptyBorder(0, 0, 0, 0));

        this.remplirTable(this.controleur.getTournois());
        this.remplirTableJoueurs(this.controleur.getJoueurs());
        this.setHeader();
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    private void setHeader() {
        this.setTitle("Equipe - " + this.equipe.getNom());
        this.setUser(User.ADMIN);
    }

    public void remplirTable(List<Tournoi> tournois) {

        for (Tournoi tournoi : tournois) {
            if (this.controleur.tournoiFini(tournoi)) {
                this.modeleTable.addRow(
                        new Object[] { tournoi.getAnnee(), tournoi.getNom(), tournoi.getLigue().getNom(),
                                this.controleur.getRank(tournoi) });
            }
        }
        if (this.modeleTable.getRowCount() == 0) {
            JLabel txt = new JLabel("Aucun tournoi joué pour cette équipe");
            JPanel panelSansTournoi = new JPanel();
            txt.setForeground(CharteGraphique.WHITE);
            txt.setFont(CharteGraphique.TEXT);
            panelSansTournoi.setOpaque(false);
            panelBody.remove(scrollPaneHistorique);
            panelBody.add(panelSansTournoi, BorderLayout.EAST);
            panelSansTournoi.add(txt);

        }
        this.tblHistorique.setModel(this.modeleTable);
        tblHistorique.getColumn("Ligue").setPreferredWidth(200);
        tblHistorique.getColumn("Tournoi").setPreferredWidth(100);
        
    }

    public void remplirTableJoueurs(List<Joueur> joueurs) {
        for (Joueur joueur : joueurs) {
            modeleTableJoueurs.addRow(new Object[] { joueur.getPseudo() });
        }
        this.tblJoueurs.setModel(this.modeleTableJoueurs);
    }

}
