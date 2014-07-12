package gene;

import java.awt.Color;
import java.util.Random;

public class Organism
{
    public final Random rnd = new Random();
    
    public static MutationMethod mutationMethod = null;
    public static Organism perfectOrganism = null;
    
    public Color genes;
    
    public Organism(Color gene)
    {
        this.genes = gene;
    }
    
    public Organism(int r, int g, int b)
    {
        this(new Color(r, g, b));
    }
    
    public Organism(Organism parent1, Organism parent2)
    {
        this(new Color((parent1.genes.getRGB() + parent2.genes.getRGB()) / 2));
        
        if (rnd.nextDouble() < GenePoolSimulator.MUTATION_PERCENTAGE)
        {
            mutationMethod.mutate(parent1, parent2, this);
        }
    }
    
    public Color getGene()
    {
        return this.genes;
    }
    
    public int rollWeightedDie()
    {
        return ((rnd.nextInt() + rnd.nextInt()) / 2) - getRelativeFitness(perfectOrganism) * 300;
    }
    
    private String paddedBinaryNumber(int num)
    {
        String s = "00000000" + Integer.toBinaryString(num);
        return s.substring(s.length() - 8);
    }
    
    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        output.append('[');
        output.append(paddedBinaryNumber(this.genes.getRed()) + ", ");
        output.append(paddedBinaryNumber(this.genes.getGreen()) + ", ");
        output.append(paddedBinaryNumber(this.genes.getBlue()));
        output.append(']');
        
        if (perfectOrganism != null)
        {
            output.append('\t');
            output.append("Relative Fitness: ");
            output.append(this.getRelativeFitness(perfectOrganism));
        }
        
        return output.toString();
    }
    
    public int getRelativeFitness(Organism org)
    {
        int r = this.genes.getRed() + this.genes.getGreen() + this.genes.getBlue();
        r -= org.genes.getRed() + org.genes.getGreen() + org.genes.getBlue();
        return Math.abs(r);
    }
}
