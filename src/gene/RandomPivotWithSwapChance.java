package gene;

import java.awt.Color;

public class RandomPivotWithSwapChance extends RandomPivot
{
    @Override
    public void mutate(Organism org1, Organism org2, Organism child)
    {
        super.mutate(org1, org2, child);

        if (child.rnd.nextBoolean())
        {
            int r = child.genes.getRed();
            int g = child.genes.getGreen();
            int b = child.genes.getBlue();
            int t;

            switch (child.rnd.nextInt(3))
            {
            case 0:
                t = r;
                r = g;
                g = t;
                break;
            case 1:
                t = r;
                r = b;
                b = t;
                break;
            case 2:
                t = g;
                g = b;
                b = t;
                break;
            }
            
            child.genes = new Color(r, g, b);
        }
    }
}
