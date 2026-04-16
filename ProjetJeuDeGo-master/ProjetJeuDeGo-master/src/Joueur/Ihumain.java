package Joueur;

import Plateau.IPlateau;

public interface Ihumain {
    public String commande(String ligneCommande, IPlateau board, String couleur);
}
