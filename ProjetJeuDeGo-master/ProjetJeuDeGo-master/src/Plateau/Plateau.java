package Plateau;

import Plateau.pion.pion;

public class Plateau implements IPlateau {
    private pion[][] plateau;
    private final int size;

    public Plateau(int size) {
        plateau = new pion[size][size];
        this.size = size;
    }

    @Override
    public String chiffreEnChar(int i) {
        String s = String.valueOf((char) ('A' + i)); // Convertit le caractère en String
        return s;
    }

    @Override
    public pion getPion(int x, int y) {
        return plateau[y - 1][x - 1];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                plateau[i][j] = null;
            }
        }
    }

    @Override
    public boolean caseEstVide(int x, int y) {
        return getPion(x, y) == null;
    }

    @Override
    public boolean setPion(int x, int y, String couleur) {
        if (caseEstVide(x, y)) {
            plateau[y - 1][x - 1] = new pion(couleur);
            return true;
        }
        return false;
    }

    @Override
    public void removePion(int x, int y) {
        plateau[y - 1][x - 1] = null;
    }


    //vérifie pour un alignement de 5 pions consécutifs
    @Override
    public boolean checkwin(IPlateau board, String couleur) {
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                if (board.getPion(x, y) != null && board.getPion(x, y).getCouleur().equals(couleur)) {
                    for (int[] dir : directions) {
                        if (hasFiveInDirection(board, x, y, dir[0], dir[1], couleur)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasFiveInDirection(IPlateau board, int x, int y, int dx, int dy, String couleur) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            if (nx >= 1 && nx <= size && ny >= 1 && ny <= size &&
                    board.getPion(nx, ny) != null && board.getPion(nx, ny).getCouleur().equals(couleur)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i < 5; i++) {
            int nx = x - i * dx;
            int ny = y - i * dy;
            if (nx >= 1 && nx <= size && ny >= 1 && ny <= size &&
                    board.getPion(nx, ny) != null && board.getPion(nx, ny).getCouleur().equals(couleur)) {
                count++;
            } else {
                break;
            }
        }
        return count >= 5;
    }

    public String toString()
    {
        String board = "";
        for (int i = size + 1; i >= 0; --i)
        {
            if (i == 0 || i == size + 1)
            {
                board += size<10 ? "  " : "   "; //rajoute un espace si c'est un grand tableau pour etre aligné avec la grille
                for (int j = 0; j < size; ++j)
                {
                    board += chiffreEnChar(j) + " ";
                }
                board += "\n";
            }
            else
            {
                for (int j = 0; j <= size; ++j)
                {
                    if (j == 0)
                    {
                        board += size < 10 ? (i) + " " : i < 10 ? " " + (i) + " " : (i) + " "; //rajoute un espace si c'est un grand tableau pour etre aligné avec la grille
                    }
                    else
                    { // Contenu du plateau
                        if (caseEstVide(j,i))
                        {
                            board += ". ";
                        } else if (getPion(j,i).getCouleur().equals("white"))
                        {
                            board += "O ";
                        } else
                        {
                            board += "X ";
                        }
                    }
                }
                board += size < 10 ? (i) + " " : i < 10 ? " " + (i) + " " : (i) + " ";
                board += "\n";
            }
        }
        return board;
    }

    public int getTotalMoves() {
        int totalMoves = 0;
        int boardSize = getSize();

        for (int x = 1; x <= boardSize; x++) {
            for (int y = 1; y <= boardSize; y++) {
                if (!caseEstVide(x, y)) {
                    totalMoves++;
                }
            }
        }
        return totalMoves;
    }

}
