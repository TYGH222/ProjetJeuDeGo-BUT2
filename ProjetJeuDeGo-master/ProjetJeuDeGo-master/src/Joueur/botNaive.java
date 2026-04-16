package Joueur;

import Plateau.IPlateau;
import java.util.*;

public class botNaive implements IJouer {

    @Override
    public String genmove(IPlateau board, String couleur) {
        int boardSize = board.getSize();
        List<int[]> positions = new ArrayList<>();

        for (int x = 1; x <= boardSize; x++) {
            for (int y = 1; y <= boardSize; y++) {
                positions.add(new int[]{x, y});
            }
        }

        Collections.shuffle(positions);

        for (int i = 0; i < positions.size(); i++) {
            int[] chosenPos = positions.get(i);
            int X = chosenPos[0];
            int Y = chosenPos[1];

            if (board.caseEstVide(X, Y)) {
                board.setPion(X, Y, couleur);
                return board.chiffreEnChar(X - 1) + String.valueOf(Y);
            }
        }
        System.out.println("Unexpected error: could not place pion.");
        return "pass";
    }
}
