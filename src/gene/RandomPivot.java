package gene;


import java.awt.Color;

public class RandomPivot implements MutationMethod
{
    @Override
    public void mutate(Organism org1, Organism org2, Organism child)
    {
        final int splicePosition = child.rnd.nextInt(24);
        int spliceNumber = 0x1;
        
        for (int i = 0; i < splicePosition; i++)
        {
            spliceNumber = spliceNumber << 1 + 0x1;
        }
        
        child.genes = new Color(org1.genes.getRGB() & spliceNumber + org2.genes.getRGB() & (0xFFFFFF - spliceNumber));
    }

}
