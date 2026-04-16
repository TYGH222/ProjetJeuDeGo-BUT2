package Plateau;

import Plateau.pion.pion;

public interface IPlateau
{
    public pion getPion(int x, int y);
    public void clear();
    public boolean setPion(int x, int y, String couleur);
    public String toString();
    public int getSize();
    public String chiffreEnChar(int i);
    public boolean caseEstVide(int x, int y);
    public void removePion(int x, int y);
    public boolean checkwin(IPlateau board, String couleur);
    public int getTotalMoves();
}
