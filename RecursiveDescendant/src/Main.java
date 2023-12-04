import java.util.Scanner;

public class Main
{
    private static void menu()
    {
        System.out.println("1. Print set of non-terminals ");
        System.out.println("2. Print set of terminals ");
        System.out.println("3. Print set of productions ");
        System.out.println("4. Print set of productions for non-terminal ");
        System.out.println("5. CFG check ");
        System.out.println("6. Exit ");

    }


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Read from file: ");
        String fileName = scanner.nextLine();

        try
        {
            Grammar grammar = new Grammar(fileName);

            menu();

            while(true)
            {

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