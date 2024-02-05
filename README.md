# E-Sporter Tournament Manager

Bienvenue dans le projet E-Sporter Tournament Manager ! Cette application Java autonome a été conçue dans le cadre de projet universitaire pour mon BUT Informatique. Le but est d'organiser des tournois e-sports de League of Legends à l'échelle mondiale.

## Fonctionnalités

### 1. Gestion des équipes

- **Saisie, Modification, Suppression :** Permet de gérer la composition des équipes participant à un tournoi.

### 2. Gestion des tournois

- **Création et Gestion :** Permet à l'administrateur de créer et de gérer des tournois avec une plage de 4 à 8 équipes.
- **Saisie des Résultats :** L'arbitre peut saisir les résultats des matchs en temps réel.
- **Rapport de Classement :** Consultation d'un rapport de classement pendant le déroulement du tournoi.
- **Calcul des Points :** Automatique et basé sur les résultats des équipes.

### 3. Historique et Palmarès

- **Sauvegarde des Points :** Enregistrement des points gagnés par chaque équipe au cours de la saison.
- **Affichage d'État :** Consultation de l'historique des points et du palmarès des équipes.

## Contraintes Techniques ###(Demander dans le cadre du projet universitaire)

- **Standalone :** Fonctionne localement sans connexion réseau.
- **Interface Conviviale :** Utilisation de JavaDB/Derby pour stocker toutes les données.
- **Ergonomie Gaming :** Interface conviviale adaptée au contexte "gaming".

## Profils Utilisateurs

1. **Arbitre :** Saisie et correction des résultats des matchs, fermeture d'un tournoi.
2. **Administrateur E-sporter :** Administration complète de l'application (à l'exception de la saisie/modification des scores et la clôture d'un tournoi).

## Déroulement d'un Tournoi

1. **Ouverture du Tournoi :** Saisie des équipes, création du tournoi par l'administrateur, désignation des arbitres.
2. **Matches de Poule :** Saisie des résultats par l'arbitre au fur et à mesure des matches.
3. **Clôture du Tournoi :** Désignation des deux meilleures équipes pour la finale, calcul des points et mise à jour de l'historique.

## Calcul des Points

- **Multiplicateur selon la Notoriété :** En fonction du niveau du tournoi.
- **Points par Match (Poule) :** Victoire +25 pts, Défaite +15 pts.
- **Points par Classement :** 1er (victoire Finale) 200 pts, 2ème (défaite Finale) 100 pts, 3ème 50 pts, 4ème 15 pts.


## Lancement
Pour lancer le projet E-Sporter, suivez ces étapes :

1. Extraire le zip E-Sporter dans un dossier.
2. Ouvrir Eclipse.
3. Aller dans `File > Import > General > Existing Projects into Workspace`.
4. Dans 'Select root directory', spécifier le chemin d'accès au dossier qui contient l'archive extraite.
5. Une fois le projet importé, dans le package 'lancement', sélectionnez `DataBase.java` et effectuez 'Run as 1 Java application'.
6. Après l'apparition du message "Base de Données initialisée" dans la console, sélectionnez le fichier `main.java` du package 'lancement' et exécutez-le en tant qu'une 'Java application'.

Vous serez alors sur la page d'identification.

### Identifiants admin
- Login : `admin`
- Mot de passe : `admin`

Vous pourrez naviguer dans la partie administrateur de l'application.

**Note :** Au premier lancement, un tournoi est en cours. Ses identifiants sont :
- Login : `tournoiA`
- Mot de passe : `admin`


---

Fait par une équipe de 4 en 120h en suivant les méthodes agiles (scrum)
Christopher VOISIN
