package Joueur;

import Plateau.IPlateau;
import Plateau.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class humainTest {

    private humain humainPlayer;
    private IPlateau board;

    @BeforeEach
    public void setUp() {
        humainPlayer = new humain();
        board = new Plateau(9); // Plateau de taille 9x9
    }

    @Test
    public void testGenmove() {
        String couleur = "black";
        String move = humainPlayer.genmove(board, couleur);
        assertNotNull(move, "Le mouvement généré ne doit pas être null");
        assertFalse(move.isEmpty(), "Le mouvement généré ne doit pas être vide");
    }

    @Test
    public void testCommandePlayValid() {
        String ligneCommande = "play black A1";
        String result = humainPlayer.commande(ligneCommande, board, "black");
        assertEquals("=play", result, "La commande play valide doit retourner '=play'");
        assertTrue(board.getPion(1, 1).getCouleur().equals("black"), "Le pion doit être placé sur le plateau");
    }

    @Test
    public void testCommandePlayInvalid() {
        String ligneCommande = "play black Z1";
        String result = humainPlayer.commande(ligneCommande, board, "black");
        assertTrue(result.startsWith("?"), "La commande play invalide doit retourner une erreur");
    }

    @Test
    public void testClearBoard() {
        board.setPion(1, 1, "black");
        assertEquals("black", board.getPion(1, 1).getCouleur(), "Le plateau doit contenir un pion noir");

        humainPlayer.clearBoard(board);
        assertNull(board.getPion(1, 1), "Le plateau doit être vidé après clearBoard");
    }

    @Test
    public void testCommandeClearBoard() {
        board.setPion(1, 1, "black");
        String result = humainPlayer.commande("clear_board", board, "black");
        assertEquals("=clear_board", result, "La commande clear_board doit retourner '=clear_board'");
        assertNull(board.getPion(1, 1), "Le plateau doit être vidé après la commande clear_board");
    }

    @Test
    public void testCommandeQuit() {
        String result = humainPlayer.commande("quit", board, "black");
        assertEquals("=quit", result, "La commande quit doit retourner '=quit'");
    }

    @Test
    public void testCommandeResign() {
        String result = humainPlayer.commande("resign", board, "black");
        assertEquals("=RESIGN", result, "La commande resign doit retourner '=RESIGN'");
    }

    @Test
    public void testInvalidCommande() {
        String result = humainPlayer.commande("invalid_command", board, "black");
        assertTrue(result.startsWith("?"), "Une commande invalide doit retourner une erreur avec '?' au début");
    }
}
