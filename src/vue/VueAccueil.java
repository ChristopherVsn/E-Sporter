package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import composant.buttons.GoldUnderlinedButton;
import composant.buttons.PicturedButton;
import composant.header.User;
import controleur.ControleurAccueil;
import ressources.CharteGraphique;
import ressources.Images;

public class VueAccueil extends VueMain {
	
	private JPanel panelEquipes;
	private JPanel panelArbitres;
	private JPanel panelPalmares;
	private JPanel panelHistorique;
	private JPanel panelTurnament;
	
	private PicturedButton btnImageEquipe;
	private PicturedButton btnImageArbitres;
	private PicturedButton btnImagePalmares;
	private PicturedButton btnImageHistorique;
	private PicturedButton btnImgTournois;

	private ControleurAccueil controleur;


	public VueAccueil() {
		this.controleur = new ControleurAccueil(this);
		this.setMinimumSize(CharteGraphique.MIN_SIZE);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = (int)panelEquipes.getWidth();
				int height = (int)(width * (11.0/19.0));
				btnImageEquipe.resizeIcon(width, height);
				btnImageArbitres.resizeIcon(width, height);
				btnImagePalmares.resizeIcon(width, height);
				btnImageHistorique.resizeIcon(width, height);
				
				int borders = (int)(panelEquipes.getHeight() * 0.2);
			}
		});

		this.setUser(User.ADMIN);
		this.setTitle("Accueil");
		
		JPanel panelBody = new JPanel();
		panelBody.setOpaque(false);
		panelBody.setBackground(new Color(0, 0, 128));
		panelBody.setBorder(new EmptyBorder(50, 200, 50, 200));
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelInscriptions = new JPanel();
		panelInscriptions.setOpaque(false);
		panelInscriptions.setBackground(new Color(255, 0, 0));
		panelInscriptions.setBorder(new EmptyBorder(0, 0, 0, 50));
		panelBody.add(panelInscriptions);
		panelInscriptions.setLayout(new GridLayout(2, 0, 0, 0));
		
		
		panelEquipes = new JPanel();
		panelEquipes.setOpaque(false);
		panelInscriptions.add(panelEquipes);
		panelEquipes.setLayout(new BorderLayout(0, 0));
		
		panelArbitres = new JPanel();
		panelArbitres.setOpaque(false);
		panelInscriptions.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));

		


		JPanel panelConsultations = new JPanel();
		panelConsultations.setOpaque(false);
		panelConsultations.setBackground(new Color(0, 0, 0));
		panelBody.add(panelConsultations);
		panelConsultations.setLayout(new GridLayout(2, 0, 0, 0));
		panelConsultations.setBorder(new EmptyBorder(0, 50, 0, 0));
		
		panelPalmares = new JPanel();
		panelPalmares.setOpaque(false);
		panelConsultations.add(panelPalmares);
		panelPalmares.setLayout(new BorderLayout(0, 0));
		
		panelHistorique = new JPanel();
		panelHistorique.setOpaque(false);
		panelConsultations.add(panelHistorique);
		panelHistorique.setLayout(new BorderLayout(0, 0));
		
		btnImageEquipe = new PicturedButton(Images.EQUIPES);
		btnImageEquipe.setBorder(null);
		btnImageEquipe.addActionListener(controleur);
		btnImageEquipe.setActionCommand("equipe");
		
		btnImageArbitres = new PicturedButton(Images.ARBITRES);
		btnImageArbitres.setBorder(null);
		btnImageArbitres.addActionListener(controleur);
		btnImageArbitres.setActionCommand("arbitres");
		
		btnImagePalmares = new PicturedButton(Images.PALMARES);
		btnImagePalmares.setBorder(null);
		btnImagePalmares.addActionListener(controleur);
		btnImagePalmares.setActionCommand("palmares");
		
		btnImageHistorique = new PicturedButton(Images.TOURNOI);
		btnImageHistorique.setBorder(null);
		btnImageHistorique.addActionListener(controleur);
		btnImageHistorique.setActionCommand("tournois");
		
		JPanel imgEquipe = new JPanel();
		imgEquipe.setLayout(new BorderLayout());
		imgEquipe.setOpaque(false);
		imgEquipe.add(btnImageEquipe, BorderLayout.NORTH);
		panelEquipes.add(imgEquipe, BorderLayout.CENTER);
		
		JPanel imgArbitres = new JPanel();
		imgArbitres.setLayout(new BorderLayout());
		imgArbitres.setOpaque(false);
		imgArbitres.add(btnImageArbitres, BorderLayout.NORTH);
		panelArbitres.add(imgArbitres, BorderLayout.CENTER);
		
		JPanel imgPalmares = new JPanel();
		imgPalmares.setLayout(new BorderLayout());
		imgPalmares.setOpaque(false);
		imgPalmares.add(btnImagePalmares, BorderLayout.NORTH);
		panelPalmares.add(imgPalmares, BorderLayout.CENTER);
		
		JPanel imgHistorique = new JPanel();
		imgHistorique.setLayout(new BorderLayout());
		imgHistorique.setOpaque(false);
		imgHistorique.add(btnImageHistorique, BorderLayout.NORTH);
		panelHistorique.add(imgHistorique, BorderLayout.CENTER);
		
		GoldUnderlinedButton btnEquipe = new GoldUnderlinedButton("Equipes");
		btnEquipe.addActionListener(controleur);
		btnEquipe.setActionCommand("equipe");
		GoldUnderlinedButton btnArbitres = new GoldUnderlinedButton("Arbitres");
		btnArbitres.addActionListener(controleur);
		btnArbitres.setActionCommand("arbitres");
		GoldUnderlinedButton btnPalmares = new GoldUnderlinedButton("Palmares");
		btnPalmares.addActionListener(controleur);
		btnPalmares.setActionCommand("palmares");
		GoldUnderlinedButton btnHistorique = new GoldUnderlinedButton("Gestion de Tournois");
		btnHistorique.addActionListener(controleur);
		btnHistorique.setActionCommand("tournois");
		
		panelEquipes.setBorder(new EmptyBorder(0,0,30,0));
		panelPalmares.setBorder(new EmptyBorder(0,0,30,0));

		panelEquipes.add(btnEquipe, BorderLayout.NORTH);
		panelArbitres.add(btnArbitres, BorderLayout.NORTH);
		panelPalmares.add(btnPalmares, BorderLayout.NORTH);
		panelHistorique.add(btnHistorique, BorderLayout.NORTH);
		
	}
}