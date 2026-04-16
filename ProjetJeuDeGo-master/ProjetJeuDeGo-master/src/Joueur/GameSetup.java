package Joueur;

import java.util.Scanner;

public class GameSetup {

    public static PlayerConfig setupPlayers() {
        Scanner scanner = new Scanner(System.in);
        PlayerConfig config = new PlayerConfig();

        String blackInput = scanner.nextLine().trim();
        config.blackPlayer = parsePlayerInput(blackInput, "black");
        String whiteInput = scanner.nextLine().trim();
        config.whitePlayer = parsePlayerInput(whiteInput, "white");

        return config;
    }

    private static IJouer parsePlayerInput(String input, String expectedColor) {
        String[] parts = input.trim().split(" ");
        if (parts.length != 3 && parts.length != 4 && parts[0].equals("set_player")) {
            throw new IllegalArgumentException("Invalid input format. Expected \"<set_player> <color> <type>\".");
        }

        String color = parts[1].toLowerCase();
        String type = parts[2].toLowerCase();

        if (!color.equals(expectedColor)) {
            throw new IllegalArgumentException("Invalid color. Expected \"" + expectedColor + "\".");
        }

        int profRecherche = 0;//obligé de créer la variable a 0 pour éviter les erreurs
        if (type.equals("minimax"))
        {
            if (Integer.parseInt(parts[3]) > 0 && Integer.parseInt(parts[3]) <= 19 && parts.length == 4)//si la valeur est entre 0 et 19
            {
                profRecherche = Integer.parseInt(parts[3]);//profondeur de recherche de minimax
            }
            else
            {
                throw new IllegalArgumentException("Invalid research deepness try a number between 0 and 19.");
            }
        }

        return switch (type) {
            case "randombot" -> new botNaive();
            case "minimax" -> new botMinimax(profRecherche);
            case "human" -> new humain();
            default -> throw new IllegalArgumentException("Invalid type. Must be \"randomBot\",\"minimax or \"human\".");
        };
    }

    public static class PlayerConfig {
        public IJouer whitePlayer;
        public IJouer blackPlayer;
    }
}
