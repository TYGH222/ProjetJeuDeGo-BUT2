package Joueur;

import Plateau.IPlateau;
import Plateau.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class botMinimaxTest {

    private botMinimax bot;
    private IPlateau board;

    @BeforeEach
    public void setUp() {
        bot = new botMinimax(5); // Profondeur de 5 pour les tests
        board = new Plateau(5); // Plateau de taille 5x5
    }

    @Test
    public void testGenmoveFindsWinningMove() {
        String couleur = "black";
        // Prépare une situation où le bot peut gagner immédiatement
        board.setPion(1, 1, "black");
        board.setPion(1, 2, "black");
        board.setPion(1, 3, "black");
        board.setPion(1, 4, "black");

        String move = bot.genmove(board, couleur);

        assertEquals("A5", move, "Le bot doit détecter et jouer le coup gagnant");
        assertEquals("black", board.getPion(1, 5).getCouleur(), "Le pion doit être placé correctement pour gagner");
    }

    @Test
    public void testGenmoveBlocksOpponentWinningMove() {
        String couleur = "black";
        String opponent = "white";

        // Prépare une situation où l'adversaire peut gagner au prochain tour
        board.setPion(1, 1, "white");
        board.setPion(1, 2, "white");
        board.setPion(1, 3, "white");
        board.setPion(1, 4, "white");

        String move = bot.genmove(board, couleur);

        assertEquals("A5", move, "Le bot doit bloquer le coup gagnant de l'adversaire");
        assertEquals("black", board.getPion(1, 5).getCouleur(), "Le bot doit placer un pion pour bloquer l'adversaire");
    }

    @Test
    public void testGenmovePlaysOptimalMove() {
        String couleur = "black";

        // Simule un plateau partiellement rempli
        board.setPion(3, 3, "black");
        board.setPion(2, 2, "white");
        board.setPion(4, 4, "white");

        String move = bot.genmove(board, couleur);

        assertNotNull(move, "Le mouvement généré ne doit pas être null");
        assertFalse(move.isEmpty(), "Le mouvement généré ne doit pas être vide");
    }

    @Test
    public void testHasNeighbor() {
        // Vérifie qu'une case a un voisin
        board.setPion(3, 3, "black");
        assertTrue(bot.hasNeighbor(board, 3, 2), "La case (3,2) doit avoir un voisin");
        assertFalse(bot.hasNeighbor(board, 1, 1), "La case (1,1) ne doit pas avoir de voisin");
    }
}
