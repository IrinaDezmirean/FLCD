public class Main
{
    public static void main(String[] args)
    {
        SymbolTable<Integer,Object> stConstants = new SymbolTable<>(2);
        SymbolTable<Integer,Object> stIdentifiers = new SymbolTable<>(2);

        // a = 1;
        // b = "ana are mere";
        // c = "23"
        // d = 104

        stConstants.put(0,1);
        stConstants.put(1,"ana are mere");
        stConstants.put(2, "23");
        stConstants.put(3,104);

        stIdentifiers.put(0,"a");
        stIdentifiers.put(1,"b");
        stIdentifiers.put(2, "c");
        stIdentifiers.put(3,"d");

        System.out.println("Constants symbol table iterator:");
        for(Object val: stConstants)
            System.out.println(val);


        System.out.println("Identifiers symbol table iterator:");
        for(Object val: stIdentifiers)
            System.out.println(val);


        System.out.println("Search for key 2 in constants symbol table:");
        System.out.println("RESULT: " + stConstants.get(2));

        System.out.println("Search for key 2 in identifiers symbol table:");
        System.out .println("RESULT: " + stIdentifiers.get(2));

        System.out.println("Removing element with key 1 from constants symbol table.");
        stConstants.remove(1);

        System.out.println("Reiterate through constants symbol table:");
        for(Object val: stConstants)
            System.out.println(val);

        System.out.println("Removing element with key 1 from identifiers symbol table.");
        stIdentifiers.remove(1);


        System.out.println("Reiterate through identifiers symbol table:");
        for(Object val: stIdentifiers)
            System.out.println(val);


        System.out.println("Search for key 1 in constants symbol table (previously removed):");
        System.out.println("RESULT: " + stConstants.get(1));

        System.out.println("Search for key 1 in identifiers symbol table (previously removed):");
        System.out.println("RESULT: " + stIdentifiers.get(1));
    }
}