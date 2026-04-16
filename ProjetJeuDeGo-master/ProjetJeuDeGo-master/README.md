# Projet du jeu de Go de Théo ROBERT Gregoire KUHN Zhongqi SU

## NOTE 14/20

## SYNTHESE DE LA CORRECTION
Pas mal de travail dans ce projet mais un manque de rigueur. La saisie est très fragile et plante dans plusieurs cas. Ce qui est demandé est imparfaitement respecté (format du diagramme d'architecture, taille minimale, commandes aux noms absurdes, methodes beaucoup trop longues)
aucun choix n'est expliqué au moins en une ligne sur le choix des interfaces par exemples
Les tests de minimax sont extrèmement incomplets
l'algo ne semble pas convenir à votre niveau et contient des commentaires en anglais !
Je n'ai jamais demandé alpha beta, pourquoi l'avoir choisi, il n'est pas testé, rien ne montre que c'est votre propre travail
## DETAILS SUR LA CORRECTION
 * J'ai demandé un diagramme d'architecture, pas un diagramme de classes.
 * Il aurait été utile de dire un mot sur les choix de l'architecture : pourquoi les interfaces IAppli et IHumain (alors qu'il y a déjà IJouer)
 * La taille minimum est 5 ; j'avais demandé 3
 * Si on set 2 joueurs à black le programme plante au lieu de signaler simplement une erreur, de même avec d'autres valeurs par exemple boardsize 8, alors que c'est la taille de l'exemple du README ! Pourquoi ? parce que vous semblez obliger à mettre un id pour la commande ! S'il n'y en a pas la taille n'est pas prise correctement en compte.
 * play black C3 donne une erreur alors que C3 est valide: ?playillegal move, la saisie des coups est trop fragile
 * quit n'arrete pas la partie : vous avez modifié une commande de GTP, pourquoi ?
 * resume veut dire reprendre, pour ce nom absurde pour arrêter alors qu'il y a quit ?
 * Une partie entre 2 bots ne marche pas : Unexpected error: could not place pion.
 * j'ai pu tout de meme faire un test avec 2 joueurs humain en saisissant l'id de commande à chaque coup
 * j'ai pu aussi tester une partie avec randonBot qui joue les blancs
 * TestAppli échoue, les autres tests passent
 ![image](https://github.com/user-attachments/assets/f93b304d-19f7-41c4-94ec-8a68356cd6c1)


> **Statut :** Projet fini ou dans sa phase finale


---

## Table des Matières
- [À propos](#à-propos)
- [Language](#Language)
- [Avancements](#Dernière_avancée)
- [Fonctionnement](#Fonctionnement)
---

## À propos

Ce projet vise à créer le jeu du Gomoku en JAVA. Toutes les fonctionnalitées demandées correspondent au protocole GTP et sont testées.

## Language

Le projet est exclusivement en java.

## Dernière_avancée

Toutes les commandes demandées sont fonctionnelles au charactère près. Les tests sont fonctionnels et complets.

---

## Fonctionnement

Le premier joueur est toujours le joueur noir. Un joueur est soit humain, soit un ordinateur.  
Un ordinateur peut être intelligent ou non, il en existe deux types le "randomBot" et le "minimax". Le randomBot place ces pions au hazard et est donc le mode facile du jeu. Tandis que minimax est un bot intelligent qui calculera son meilleur coup en fonction de la profondeur qui lui sera associée.  
La sélection des joueurs se fait comme ceci :  
  
* Pour un humain :            set_player black human  
* Pour un robot naif :        set_player black randomBot  
* pour un robot intelligent : set_player black minimax 5  
  
Le chiffre à la droite de minimax est la profondeur de calcul que le robot aura, c'est-à-dire combien de coups d'avance, il calculera à chaque itération. Il choisira le coup qu'il lui parait le plus fort en conséquence.  
Pour gagner l'un des joueurs devra aligner 5 pions de sa couleur qu'elle que soit la taille du plateau, c'elle-ci est sélectionnée par la commande "boardsize" et doit être comprise entre 5 et 19.  
Le jeu dispose d'un système de gestion d'ID des coups, c'est une charactéristique de GTP, chaque coup a son propre ID donc si une ID est répétée une exception est levée, mais la sélection d'ID n'es pas obligatoire ainsi un coup peut être joué sans.  

* Un début de partie entre deux joueurs ressemble à ceci :  

set_player black human  
set_player white minimax 2  
156 boardsize 8  
=156  
  
### Les fonctions disponibles pour les humains sont les suivantes :
* clear_board :  
cette fonction supprime tous les pions du plateau.  
  

* showboard :  
cette fonction affiche le plateau avec des lettres en abscisse et des chiffres en ordonnée.  


* resume :  
cette fonction met fin au jeu. (laissez votre adversaire gagner quand même)
  
  
* quit :  
sert à passer son tour, si deux joueurs font cette action de suite le jeu s'arrête.  

  
* play :  
cette fonction sert à placer un pion, vous devez préciser VOTRE couleur dans le coup par exemple pour le noir : "play black C2" le système de coordonnées suis celui de showboard.
  
  
* genmove :  
cette fonction génère un coup aléatoire à votre place, cette exécution est comparable à c'elle d'un randomBot.


#### La gestion des erreurs :
Quand un coup n'es pas valide notament lors de l'usage de la commande play le symbole "?" est utilisé sinon un "=" confirme que la commande a été exécutée avec succès.

---
#### note:

Les commits ne sont pas représentatifs du travail fourni par chaque membre du groupe, lesdit commits ont étés réalisés ensemble.
