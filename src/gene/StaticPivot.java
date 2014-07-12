package gene;

import java.awt.Color;

public class StaticPivot implements MutationMethod
{
    @Override
    public void mutate(Organism org1, Organism org2, Organism child)
    {
        int r = org1.genes.getRed();
        int g = org1.genes.getGreen() & 0xF0 + org2.genes.getGreen() & 0x0F;
        int b = org2.genes.getBlue();
        child.genes = new Color(r, g, b);
    }
}
