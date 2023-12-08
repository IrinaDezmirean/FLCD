import java.util.Scanner;

public class Main
{
    private static void menu()
    {
        System.out.println();
        System.out.println("1. Print set of non-terminals ");
        System.out.println("2. Print set of terminals ");
        System.out.println("3. Print set of productions ");
        System.out.println("4. Print set of productions for non-terminal ");
        System.out.println("5. CFG check ");
        System.out.println("6. Exit ");
        System.out.println();
    }


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Read from file: ");
        //String fileName = scanner.nextLine();
//        String fileName = "simpleGrammar.txt";
//        String fileName = "simpleGrammarNotCfg.txt";
        String fileName = "syntaxRules_Irina.txt";

        try
        {
            Grammar grammar = new Grammar(fileName);

            while(true)
            {
                menu();
                System.out.println("Choose option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                if(option == 1)
                {
                    System.out.println(grammar.getNonterminals());
                }
                else if(option == 2)
                {
                    System.out.println(grammar.getTerminals());
                }
                else if(option == 3)
                {
                    System.out.println(grammar.getProductions());
                }
                else if(option == 4){
                    System.out.println("Choose nonterminal: ");
                    String nonTerminal = scanner.nextLine();
                    grammar.printProductionsForNonterminal(nonTerminal);
                }
                else if (option == 5)
                {
                    if(grammar.getIsCFG()){
                        System.out.println("Grammar is CFG");
                    }
                    else System.out.println("Grammar is not CFG");
                }
                else if(option == 6)
                {
                    break;
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}