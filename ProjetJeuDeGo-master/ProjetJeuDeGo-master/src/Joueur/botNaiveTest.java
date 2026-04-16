package Joueur;

import Plateau.IPlateau;
import Plateau.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class botNaiveTest {

    private botNaive bot;
    private IPlateau board;

    @BeforeEach
    public void setUp() {
        bot = new botNaive();
        board = new Plateau(9); // Plateau de taille 9x9
    }

    @Test
    public void testGenmovePlacesPionOnEmptyBoard() {
        String couleur = "black";
        String move = bot.genmove(board, couleur);

        assertNotNull(move, "Le mouvement généré ne doit pas être null");
        assertFalse(move.isEmpty(), "Le mouvement généré ne doit pas être vide");

        int x = move.charAt(0) - 'A' + 1;
        int y = Integer.parseInt(move.substring(1));

        assertEquals(couleur, board.getPion(x, y).getCouleur(), "Le pion doit être correctement placé sur le plateau");
    }

    @Test
    public void testGenmoveHandlesFullBoard() {
        String couleur = "black";

        // Remplit le plateau
        for (int x = 1; x <= board.getSize(); x++) {
            for (int y = 1; y <= board.getSize(); y++) {
                board.setPion(x, y, "white");
            }
        }

        String move = bot.genmove(board, couleur);
        assertEquals("pass", move, "Le bot doit retourner 'pass' lorsque le plateau est plein");
    }

    @Test
    public void testGenmovePlacesPionOnPartiallyFilledBoard() {
        String couleur = "black";

        // Remplit partiellement le plateau
        board.setPion(1, 1, "white");
        board.setPion(2, 2, "white");

        String move = bot.genmove(board, couleur);

        assertNotNull(move, "Le mouvement généré ne doit pas être null");
        assertFalse(move.isEmpty(), "Le mouvement généré ne doit pas être vide");

        int x = move.charAt(0) - 'A' + 1;
        int y = Integer.parseInt(move.substring(1));

        assertEquals(couleur, board.getPion(x, y).getCouleur(), "Le pion doit être correctement placé sur une case vide");
    }
}
