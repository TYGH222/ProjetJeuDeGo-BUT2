package Appli;

import Joueur.IJouer;

public interface IAppli {
    public void boardSize(int size);
    public void partie();
    public void setPlayers(IJouer blackPlayer, IJouer whitePlayer);
}
