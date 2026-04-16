package Plateau;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPlateau {

    @Test
    public void testCreationTabVide() {
        Plateau p1 = new Plateau(2);
        Plateau p2 = new Plateau(3);

        Assertions.assertEquals(2, p1.getSize());
        Assertions.assertEquals(3, p2.getSize());

        for (int i = 1; i <= p1.getSize(); i++) {
            for (int j = 1; j <= p1.getSize(); j++) {
                Assertions.assertTrue(p1.caseEstVide(i,j));
            }
        }

        for (int i = 1; i <= p2.getSize(); i++) {
            for (int j = 1; j <= p2.getSize(); j++) {
                Assertions.assertNull(p2.getPion(i, j));
            }
        }
    }

    @Test
    public void testPlayClear() {
        Plateau p1 = new Plateau(4);

        Assertions.assertTrue(p1.setPion(1, 1, "black"));
        Assertions.assertTrue(p1.setPion(2, 2, "white"));

        Assertions.assertEquals("black", p1.getPion(1, 1).getCouleur());
        Assertions.assertEquals("white", p1.getPion(2, 2).getCouleur());

        Assertions.assertFalse(p1.setPion(1, 1, "white"));

        Assertions.assertEquals( p1.getTotalMoves(), 2);

        p1.removePion(1,1);
        Assertions.assertNull(p1.getPion(1,1));

        p1.clear();
        for (int i = 1; i <= p1.getSize(); i++) {
            for (int j = 1; j <= p1.getSize(); j++) {
                Assertions.assertTrue(p1.caseEstVide(i,j));
            }
        }
    }

    @Test
    public void testIntVersChar() {
        Plateau p1 = new Plateau(3);


        Assertions.assertEquals("A", p1.chiffreEnChar(0));
        Assertions.assertEquals("B", p1.chiffreEnChar(1));
        Assertions.assertEquals("C", p1.chiffreEnChar(2));

    }

    @Test
    public void testAffichage() {
        Plateau p1 = new Plateau(7);
        System.out.println(p1.toString());


        p1.setPion(1, 1, "black");
        p1.setPion(1, 2, "white");
        p1.setPion(7, 7, "black");

        System.out.println(p1.toString());
    }

    @Test
    public void testVictoire() {
        Plateau p1 = new Plateau(5);

        // horizontale
        p1.setPion(1, 1, "black");
        p1.setPion(2, 1, "black");
        p1.setPion(3, 1, "black");
        p1.setPion(4, 1, "black");
        p1.setPion(5, 1, "black");

        Assertions.assertTrue(p1.checkwin(p1,"black"));

        //verticale
        p1.clear();
        p1.setPion(1, 1, "white");
        p1.setPion(1, 2, "white");
        p1.setPion(1, 3, "white");
        p1.setPion(1, 4, "white");
        p1.setPion(1, 5, "white");

        Assertions.assertTrue(p1.checkwin(p1,"white"));

        // diagonale
        p1.clear();
        p1.setPion(1, 1, "black");
        p1.setPion(2, 2, "black");
        p1.setPion(3, 3, "black");
        p1.setPion(4, 4, "black");
        p1.setPion(5, 5, "black");

        Assertions.assertTrue(p1.checkwin(p1,"black"));


        p1.clear();
        p1.setPion(1, 1, "black");
        p1.setPion(2, 2, "white");
        Assertions.assertFalse(p1.checkwin(p1,"black"));
        Assertions.assertFalse(p1.checkwin(p1,"white"));
    }


}
