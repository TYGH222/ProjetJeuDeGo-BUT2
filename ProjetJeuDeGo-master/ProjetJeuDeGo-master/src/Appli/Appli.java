package Appli;

import Joueur.IJouer;
import Joueur.Ihumain;
import Plateau.IPlateau;
import Plateau.Plateau;

import java.util.HashSet;
import java.util.Scanner;

public class Appli implements IAppli{
    private static final int MIN_BOARD_SIZE = 5;
    private static final int MAX_BOARD_SIZE = 19;
    private IPlateau plateau;
    private IJouer blackPlayer;
    private IJouer whitePlayer;
    private String currentColor;

    private Ihumain humain;

    public Appli() {
        this.plateau = null;
        currentColor = "black";
    }

    public void setPlayers(IJouer blackPlayer, IJouer whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;

        if (whitePlayer instanceof Ihumain) {
            this.humain = (Ihumain) whitePlayer;
        }
    }


    public void boardSize(int size) {
        plateau = new Plateau(size);
    }
    private boolean checkTaille(int size){
        //si la taille est superieur a la taille minimum et inferieur a la taille maximum return true
        return size >= MIN_BOARD_SIZE && size <= MAX_BOARD_SIZE;
    }

    public static void storeCommand(HashSet<String> commandIds, String id) throws IllegalArgumentException {
        if (commandIds.contains(id)) {
            throw new IllegalArgumentException("ID already exists: " + id);
        }
        commandIds.add(id);
    }

    public void partie() {
        Scanner sc = new Scanner(System.in);
        HashSet<String> commandIds = new HashSet<>();

        int size = handleInitialBoardSize(sc, commandIds);

        boolean jeuEnCours = true;
        int quitcpt = 0; // Compteur de quit

        while (jeuEnCours) {
            IJouer currentPlayer = currentColor.equals("black") ? blackPlayer : whitePlayer;

            if (currentPlayer instanceof Joueur.humain) {
                jeuEnCours = handleHumanPlayerTurn(sc, (Ihumain) currentPlayer, commandIds, quitcpt);
            } else {
                handleAIPlayerTurn(currentPlayer);
            }

            if (plateau.checkwin(plateau, currentColor)) {
                System.out.println(currentColor + " wins!");
                jeuEnCours = false;
            } else {
                switchCurrentColor();
            }
        }
        sc.close();
    }

    private int handleInitialBoardSize(Scanner sc, HashSet<String> commandIds) {
        int size = 0;
        String prefixe = "";

        String ligneCommande = sc.nextLine().trim();
        String[] parts = ligneCommande.split(" ");

        if (parts.length == 3 && parts[1].equals("boardsize")) {
            size = Integer.parseInt(parts[2]);
            prefixe = parts[0];
            storeCommand(commandIds, prefixe);
        } else if (parts.length == 2 && parts[0].equals("boardsize")) {
            size = Integer.parseInt(parts[1]);
        }

        if (checkTaille(size)) {
            boardSize(size);
            if (!prefixe.equals("")) {
                System.out.println("=" + prefixe);
            } else {
                System.out.println("=" + "boardsize");
            }
        } else {
            throw new IllegalArgumentException("board size outside engine's limits");
        }

        System.out.print("\n");
        return size;
    }

    private boolean handleHumanPlayerTurn(Scanner sc, Ihumain humainPlayer, HashSet<String> commandIds, int quitcpt) {
        boolean jeuEnCours = true;
        boolean validMove = false;

        while (!validMove) {
            String command = sc.nextLine().trim();
            String result = humainPlayer.commande(command, plateau, currentColor);

            if (result.contains("RESIGN")) {
                System.out.println(result);
                jeuEnCours = false;
                validMove =true;
            } else if (result.contains("quit")) {
                System.out.println(result);
                quitcpt++;
                if (quitcpt == 2) {
                    jeuEnCours = false;
                }
                validMove = true;
            } else if (result.contains("genmove")) {
                handleGenMoveResult(result, commandIds);
            } else if (command.contains("showboard") || command.contains("clear_board")) {
                System.out.println(result);
                quitcpt = 0;
            } else if (result.startsWith("=") || result.contains("genmove")) {
                System.out.println(result);
                validMove = true;
                quitcpt = 0;
            } else {
                System.out.println(result + "illegal move");
                quitcpt = 0;
            }
            System.out.print("\n");
        }
        return jeuEnCours;
    }

    private void handleAIPlayerTurn(IJouer currentPlayer) {
        System.out.println("genmove " + currentColor);
        String move = currentPlayer.genmove(plateau, currentColor);
        System.out.println("= " + move + "\n");
    }

    private void handleGenMoveResult(String result, HashSet<String> commandIds) {
        String[] cmdparts = result.trim().split(" ");
        if (cmdparts.length >= 3) {
            if (cmdparts[0].length() != 1) {
                storeCommand(commandIds, cmdparts[0].substring(1));
                System.out.println(cmdparts[0] + " " + cmdparts[2]);
            } else {
                System.out.println(cmdparts[0] + " " + cmdparts[1] + " " + cmdparts[2]);
            }
        }
    }

    private void switchCurrentColor() {
        currentColor = currentColor.equals("black") ? "white" : "black";
    }
}