import Appli.Appli;
import Appli.IAppli;
import Joueur.GameSetup;

public class Main {
    public static void main(String[] args) {
        IAppli appli = new Appli();

        GameSetup.PlayerConfig config = GameSetup.setupPlayers();

        appli.setPlayers(config.blackPlayer, config.whitePlayer);
        appli.partie();

    }
}
