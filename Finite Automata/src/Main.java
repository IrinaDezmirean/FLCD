import java.util.Scanner;
public class Main
{

    public static void menu()
    {
        System.out.println("1. Show set of states");
        System.out.println("2. Show alphabet");
        System.out.println("3. Show transitions");
        System.out.println("4. Show initial state");
        System.out.println("5. Show set of final states");
        System.out.println("6. Check sequence");
        System.out.println("7. Exit");
    }

    //dfa.txt 0011100101
    //fa.txt 12312
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input finite automata file name: ");
        String filename = scanner.nextLine();
        FiniteAutomata fa = new FiniteAutomata(filename);

        System.out.println("Input sequence: ");
        String sequence = scanner.nextLine();

        menu();

        boolean running = true;

        while(running)
        {
            System.out.println("Choose option: ");
            int option = scanner.nextInt();

            if(option == 1)
            {
                System.out.println("THE SET OF STATES IS: ");
                System.out.println(fa.getStates());
            }
            else if(option == 2)
            {
                System.out.println("THE ALPHABET IS: ");
                System.out.println(fa.getAlphabet());
            }
            else if(option == 3)
            {
                System.out.println("THE SET OF TRANSITIONS IS: ");
                System.out.println(fa.getTransitions());
            }
            else if(option == 4)
            {
                System.out.println("THE INITIAL STATE IS: ");
                System.out.println(fa.getInitialState());
            }
            else if(option == 5)
            {
                System.out.println("THE SET OF FINAL STATES IS: ");
                System.out.println(fa.getFinalStates());
            }
            else if(option == 6)
            {
                if(fa.isDfa())
                {
                    if(fa.sequenceIsAccepted(sequence))
                        System.out.println(sequence + " is accepted");
                    else
                        System.out.println(sequence + " is not accepted");
                }
                else
                    System.out.println("Not DFA, can't check sequence");
            }
            else if(option == 7)
                running = false;
            else
                System.out.println("Invalid option");
        }
    }
}