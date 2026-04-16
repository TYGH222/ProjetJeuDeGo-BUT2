package Joueur;

import Plateau.IPlateau;
import java.util.*;

public class botMinimax implements IJouer {

    private final int depth; // Profondeur par défaut donnée au bot

    // Constructeur pour initialiser la profondeur
    public botMinimax(int depth) {
        this.depth = depth;
    }

    @Override
    public String genmove(IPlateau board, String couleur) {
        List<int[]> validMoves = getValidMoves(board);

        // Calcul de la profondeur effective (minimum entre depth et taille du plateau)
        int effectiveDepth = Math.min(depth, board.getSize());

        // Priority 1: Check for a winning move
        for (int[] move : validMoves) {
            board.setPion(move[0], move[1], couleur);
            if (board.checkwin(board, couleur)) {
                board.setPion(move[0], move[1], couleur);
                return board.chiffreEnChar(move[0] - 1) + String.valueOf(move[1]);
            }
            board.removePion(move[0], move[1]);
        }

        // Priority 2: Block opponent's winning move
        String opponent = getOpponentColor(couleur);
        for (int[] move : validMoves) {
            board.setPion(move[0], move[1], opponent);
            int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
            boolean blockRequired = board.checkwin(board, opponent);

            // Check for opponent's Open Four (defensive extension)
            if (!blockRequired) {
                for (int[] dir : directions) {
                    if (evaluateDirection(board, move[0], move[1], dir[0], dir[1], opponent) >= 10000) {
                        blockRequired = true;
                        break;
                    }
                }
            }

            if (blockRequired) {
                board.removePion(move[0], move[1]);
                board.setPion(move[0], move[1], couleur);
                return board.chiffreEnChar(move[0] - 1) + String.valueOf(move[1]);
            }
            board.removePion(move[0], move[1]);
        }

        // Minimax with Alpha-Beta Pruning
        int bestScore = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();

        for (int[] move : validMoves) {
            board.setPion(move[0], move[1], couleur);
            int score = minimax(board, effectiveDepth, false, couleur, Integer.MIN_VALUE, Integer.MAX_VALUE); // Utilisation de effectiveDepth
            board.removePion(move[0], move[1]);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }

        if (!bestMoves.isEmpty()) {
            int[] bestMove = bestMoves.get(new Random().nextInt(bestMoves.size()));
            board.setPion(bestMove[0], bestMove[1], couleur);
            return board.chiffreEnChar(bestMove[0] - 1) + String.valueOf(bestMove[1]);
        }

        if (board.getTotalMoves() == 0) {
            int center = board.getSize() / 2 + 1;
            board.setPion(center, center, couleur);
            return board.chiffreEnChar(center - 1) + String.valueOf(center);
        }

        return "pass";
    }

    private int minimax(IPlateau board, int depth, boolean isMaximizing, String couleur, int alpha, int beta) {
        if (depth == 0 || board.checkwin(board, couleur)) {
            return evaluate(board, couleur);
        }

        List<int[]> validMoves = getValidMoves(board);

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : validMoves) {
                board.setPion(move[0], move[1], couleur);
                int eval = minimax(board, depth - 1, false, getOpponentColor(couleur), alpha, beta);
                board.removePion(move[0], move[1]);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : validMoves) {
                board.setPion(move[0], move[1], couleur);
                int eval = minimax(board, depth - 1, true, getOpponentColor(couleur), alpha, beta);
                board.removePion(move[0], move[1]);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    private List<int[]> getValidMoves(IPlateau board) {
        List<int[]> validMoves = new ArrayList<>();
        int boardSize = board.getSize();

        for (int x = 1; x <= boardSize; x++) {
            for (int y = 1; y <= boardSize; y++) {
                if (board.caseEstVide(x, y)) {
                    if (hasNeighbor(board, x, y)) {
                        validMoves.add(new int[]{x, y});
                    }
                }
            }
        }
        return validMoves;
    }

    protected boolean hasNeighbor(IPlateau board, int x, int y) {
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 1 && nx <= board.getSize() && ny >= 1 && ny <= board.getSize()) {
                if (!board.caseEstVide(nx, ny)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int evaluate(IPlateau board, String couleur) {
        int score = 0;
        String opponent = getOpponentColor(couleur);
        int boardSize = board.getSize();

        for (int x = 1; x <= boardSize; x++) {
            for (int y = 1; y <= boardSize; y++) {
                if (board.caseEstVide(x, y)) {
                    score += evaluateDirection(board, x, y, 1, 0, couleur);
                    score += evaluateDirection(board, x, y, 0, 1, couleur);
                    score += evaluateDirection(board, x, y, 1, 1, couleur);
                    score += evaluateDirection(board, x, y, 1, -1, couleur);
                }
            }
        }
        return score;
    }

    private int evaluateDirection(IPlateau board, int x, int y, int dx, int dy, String couleur) {
        int count = 0;
        int openEnds = 0;
        int boardSize = board.getSize();

        for (int i = 1; i < 5; i++) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            if (nx >= 1 && nx <= boardSize && ny >= 1 && ny <= boardSize) {
                if (board.getPion(nx, ny) != null && couleur.equals(board.getPion(nx, ny).getCouleur())) {
                    count++;
                } else if (board.caseEstVide(nx, ny)) {
                    openEnds++;
                    break;
                } else {
                    break;
                }
            }
        }if (count >= 5) return 100000; // Win
        if (count == 4 && openEnds >= 1) return 50000; // Open Four
        if (count == 3 && openEnds == 2) return 10000; // Double-open Three
        if (count == 3 && openEnds == 1) return 2000;  // Single-open Three
        if (count == 2 && openEnds == 2) return 500;   // Double-open Two
        if (count == 2 && openEnds == 1) return 100;   // Single-open Two

        return 0;
    }

    private String getOpponentColor(String couleur) {
        return couleur.equals("black") ? "white" : "black";
    }
}


