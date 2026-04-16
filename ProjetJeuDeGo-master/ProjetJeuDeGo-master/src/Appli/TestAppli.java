package Appli;

import Joueur.*;
import Plateau.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAppli {

    @Test
    public void testHumainPlayValidMove() {

        humain humanPlayer = new humain();

        Plateau plateau = new Plateau(7);

        String command = "play black C3";
        String result = humanPlayer.commande(command, plateau, "black");

        Assertions.assertEquals("=play", result);
        Assertions.assertEquals("black", plateau.getPion(3, 3).getCouleur());
    }

    @Test
    public void testHumainPlayInvalidMove() {
        humain humanPlayer = new humain();

        Plateau plateau = new Plateau(7);

        plateau.setPion(3, 3, "black");

        String command = "play black C3";
        String result = humanPlayer.commande(command, plateau, "black");

        Assertions.assertEquals("?play", result);
        Assertions.assertEquals("black", plateau.getPion(3,3).getCouleur());
    }

    @Test
    public void testHumainPlayWrongColor() {
        humain humanPlayer = new humain();

        Plateau plateau = new Plateau(7);

        String command = "play black C3";
        String result = humanPlayer.commande(command, plateau, "white");

        Assertions.assertEquals("?play", result);
    }

    @Test
    public void testHumainQuitCommand() {
        humain humanPlayer = new humain();

        Plateau plateau = new Plateau(7);

        String command = "resign";
        String result = humanPlayer.commande(command, plateau, "black");

        Assertions.assertEquals("RESIGN", result);
    }

    @Test
    public void testGameConfigSetup() {
        GameSetup.PlayerConfig config = new GameSetup.PlayerConfig();

        config.blackPlayer = new botNaive();
        config.whitePlayer = new humain();

        Assertions.assertNotNull(config.blackPlayer);
        Assertions.assertNotNull(config.whitePlayer);

        Assertions.assertInstanceOf(botNaive.class, config.blackPlayer);
        Assertions.assertInstanceOf(humain.class, config.whitePlayer);
    }

    @Test
    public void testAppliIntegrationWithHumain() {
        GameSetup.PlayerConfig config = new GameSetup.PlayerConfig();
        config.blackPlayer = new humain();
        config.whitePlayer = new botNaive();

        Appli appli = new Appli();
        appli.setPlayers(config.blackPlayer, config.whitePlayer);
        appli.boardSize(7);

        Plateau plateau = new Plateau(7);
        String command = "play black C3";
        String result = ((humain) config.blackPlayer).commande(command, plateau, "black");


        Assertions.assertEquals("=play", result);
        Assertions.assertEquals("black", plateau.getPion(3, 3).getCouleur());
    }

}
