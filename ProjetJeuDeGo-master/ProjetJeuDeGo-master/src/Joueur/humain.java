package Joueur;

import Plateau.IPlateau;

public class humain extends botNaive implements Ihumain{
    final String  commadeValide = "=";
    final String commandeInvalide = "?";
    public String genmove(IPlateau board, String couleur) {
        return super.genmove(board, couleur);
    }

    public String commande(String ligneCommande, IPlateau board, String couleur) {
        String prefix = "";
        String fonction;
        String arguments;

        try {
            int indexSpace = ligneCommande.indexOf(" ");
            if (indexSpace != -1) {
                String firstPart = ligneCommande.substring(0, indexSpace);
                if (firstPart.matches("\\d+")) {
                    prefix = firstPart;
                    ligneCommande = ligneCommande.substring(indexSpace + 1).trim();
                }
            }

            int indexEspace = ligneCommande.indexOf(" ");
            if (indexEspace != -1) {
                fonction = ligneCommande.substring(0, indexEspace);
                arguments = ligneCommande.substring(indexEspace + 1);
            } else {
                fonction = ligneCommande;
                arguments = "";
            }

            boolean valider = true;

            switch (fonction) {
                case "play" -> {
                    if (!play(arguments, couleur, board)) {
                        valider = false;
                    }
                }
                case "clear_board" -> {
                    clearBoard(board);
                }
                case "genmove" -> {
                    String generatedMove = genmove(board, couleur); // Par exemple "C2"
                    return (prefix.isEmpty() ? commadeValide : commadeValide + prefix) + " genmove " + generatedMove;
                }

                case "showboard" -> {
                    System.out.print("=" + prefix +"\n"+ board);
                }
                case "resign" -> {
                    return (prefix.isEmpty() ? commadeValide : commadeValide + prefix) + "RESIGN";
                }
                case "quit" ->{
                    return (prefix.isEmpty() ? commadeValide : commadeValide + prefix) + "quit";
                }
                default -> {
                    return (prefix.isEmpty() ? "?" : "?" + prefix) + "Invalid command: " + fonction;
                }
            }

            if (valider) {
                return (prefix.isEmpty() ? commadeValide + fonction  : commadeValide + prefix);
            }
            else
            {
                return (prefix.isEmpty() ? commandeInvalide + fonction  : commandeInvalide + prefix);
            }

        } catch (Exception e) {
            return "?Error processing command: " + ligneCommande;
        }
    }


    public int lettreEnInt(String lettre) {return lettre.charAt(0) - 'A' + 1;}

    public boolean play(String commande, String currentColor,IPlateau board)
    {
        if(commande.length() != 8 || !commande.contains(" "))
        {
            return false;
        }
        //traitement de la commande
        String[] separateur = commande.split(" ");
        String couleur = separateur[0];
        String[] coordonees = separateur[1].split("");
        int CX = lettreEnInt(coordonees[0]);
        int CY = Integer.parseInt(coordonees[1]);
        //vérification des données
        if(!couleur.equals(currentColor) || CX > board.getSize() || CX < 1 || CY > board.getSize() || CY < 1)//si c'est pas la bonne commande donc ni black et white et si c'est out of bounds
        {
            return false;
        }
        else
        {
            return board.setPion(CX,CY,couleur);
        }
    }

    public void clearBoard(IPlateau board)
    {
        board.clear();
    }
}
