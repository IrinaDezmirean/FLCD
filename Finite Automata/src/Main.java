public class Main
{
    public static void main(String[] args)
    {
        FiniteAutomata dfa = new FiniteAutomata("dfa.txt");
        System.out.println(dfa);
        String sequence = "0011100101";
        if(dfa.sequenceIsAccepted(sequence))
        {
            System.out.println(sequence+" is accepted");
        }
        else
            System.out.println(sequence + " in not accepted");
        FiniteAutomata fa = new FiniteAutomata("fa.txt");
        System.out.println(fa);
    }
}