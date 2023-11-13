public class Main
{
    public static void printFa(FiniteAutomata fa, String sequence)
    {
        System.out.println(fa);
        if(fa.isDfa())
        {
            if (fa.sequenceIsAccepted(sequence))
            {
                System.out.println(sequence + " is accepted");
            }
            else
                System.out.println(sequence + " in not accepted");
        }
        else
            System.out.println("Not DFA, can't check sequnece");

    }
    public static void main(String[] args)
    {
        FiniteAutomata dfa = new FiniteAutomata("dfa.txt");
        String sequence = "0011100101";
        printFa(dfa,sequence);

        FiniteAutomata fa = new FiniteAutomata("fa.txt");
        sequence = "12312";
        printFa(fa,sequence);

    }
}