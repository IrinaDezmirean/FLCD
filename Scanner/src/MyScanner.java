import FiniteAutomata.FiniteAutomata;

import java.io.*;
import java.util.*;


class Pair<K,V>
{
    K token; 
    V position;

    public Pair(K t, V p)
    {
        this.token = t;
        this.position = p;
    }

    @Override
    public String toString()
    {
        return "<" +this.token + ", " + this.position + ">";
    }
}

public class MyScanner
{
    private List<String> tokens;
    private SymbolTable<Object> stConstants;
    private SymbolTable<Object> stIdentifiers;
    private List<Pair<String, Pair<Integer,Integer>>> pif;
    private FiniteAutomata identifierFa;
    private FiniteAutomata intConstFa;
    private int index;
    private int line;


    private List<String> tokenize(String txt)
    {
        String[] tokens = txt.split("[\\s\\n]+");
        return Arrays.stream(tokens).toList();
    }

    public String readFile(String filename) throws IOException
    {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                fileContent.append(line).append("\n");
            }
        }
        return fileContent.toString();
    }


    public MyScanner() throws IOException
    {
        tokens = new ArrayList<>();
        tokens.addAll(tokenize(readFile("token.txt")));
        tokens.add(" ");
        identifierFa = new FiniteAutomata("identifierFa.txt");
        intConstFa = new FiniteAutomata("intConstFa.txt");
    }

    public boolean isSeparator(char currentCharacter)
    {
        if(Arrays.asList('(', ')', '[', ']', '{', '}', ',', ';',' ', '\n').contains(currentCharacter))
            return true;
        return false;
    }


    public boolean isInTokenList(String accumulator)
    {
        if(tokens.contains(accumulator))
            return true;
        return false;
    }

    public boolean isIdentifier(String accumulator)
    {
        if(identifierFa.sequenceIsAccepted(accumulator))
            return true;
        return false;
//        if(accumulator.matches("^[a-zA-Z_][a-zA-Z_0-9]*$"))
//            return true;
//        return false;
    }

    public void logIdentifier(String identifier)
    {
        if(stIdentifiers.getPosition(identifier) != null) // identifier is in symbol table
        {
            Pair<Integer,Integer> pos = stIdentifiers.getPosition(identifier);
            pif.add(new Pair<>(identifier, pos));
        }
        else  // add identifier in symbol table then update pif
        {
            stIdentifiers.put(identifier);
            Pair<Integer,Integer> pos = stIdentifiers.getPosition(identifier);
            pif.add(new Pair<>(identifier, pos));
        }
    }

    public boolean isIntConstant(String accumulator)
    {
        if(intConstFa.sequenceIsAccepted(accumulator))
            return true;
        return false;
    }

    public boolean isStringConst(String accumulator)
    {
        if(accumulator.matches("(['\\\"])(.*?)\\\\1"))
            return true;
        return false;
    }

    public boolean isBoolConstant(String accumulator)
    {
        if(accumulator.matches("true|false"))
            return true;
        return false;
    }

    public boolean isConstant(String accumulator)
    {
        if(accumulator.charAt(0) == '“')
            accumulator = accumulator.substring(1, accumulator.length() - 1);

        if(isIntConstant(accumulator) || isStringConst(accumulator) || isBoolConstant(accumulator))
            return true;
        return false;
//        if(accumulator.charAt(0) == '“')
//            accumulator = accumulator.substring(1, accumulator.length() - 1);
//        if(accumulator.matches("(\"(?:[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|[-+]?[0-9]+|true|false|'[^']*'|[a-zA-Z_][a-zA-Z0-9_]*)"))
//            return true;
//        return false;
    }

    public void logConstant(String constant)
    {
        if(stConstants.getPosition(constant) != null) // constant is in symbol table
        {
            Pair<Integer,Integer> pos = stConstants.getPosition(constant);
            pif.add(new Pair<>(constant, pos));
        }
        else  // add identifier in symbol table then update pif
        {
            stConstants.put(constant);
            Pair<Integer,Integer> pos = stConstants.getPosition(constant);
            pif.add(new Pair<>(constant, pos));
        }
    }

    public void detectToken(String accumulator)
    {
        if(isInTokenList(accumulator))
        {
            pif.add(new Pair(accumulator,new Pair<>(-1,-1)));
        }
        else if(isIdentifier(accumulator))
        {
            logIdentifier(accumulator);
        }
        else if(isConstant(accumulator))
        {
            logConstant(accumulator);
        }
        else
            throw new RuntimeException("Logical error on line " + line + " at token " + accumulator);
    }


    public void writeOutputToFile(String filename, List<Pair<String, Pair<Integer, Integer>>> pif) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            for (Pair<String, Pair<Integer, Integer>> p : pif)
            {
                writer.write(p.token + " " + p.position + "\n");
            }
        }
    }

    public void writePIFToFile(String filename) throws IOException
    {
        writeOutputToFile(filename, pif);
    }

    private void writeSymbolTableToFile(SymbolTable<Object> symbolTable, String filename) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            for (Object key : symbolTable)
            {
                writer.write(key.toString() + " " + symbolTable.getPosition(key) + "\n");
            }
        }
    }

    public void writeSymbolTables(String constantsFilename, String identifiersFilename) throws IOException
    {
        writeSymbolTableToFile(stConstants, constantsFilename);
        writeSymbolTableToFile(stIdentifiers, identifiersFilename);
    }

    public void scan(String filename) throws IOException
    {
        // Read the input file
        String sourceCode = readFile(filename);

        index = -1;
        line = 1;
        char currentCharacter;
        String accumulator = "";

        pif = new ArrayList<>();
        stIdentifiers = new SymbolTable<>(10);
        stConstants = new SymbolTable<>(10);

        // Start the scanning process
        while(index < sourceCode.length()-1)
        {
            index++;
            currentCharacter = sourceCode.charAt(index);

            if(isSeparator(currentCharacter))
            {
                if(!accumulator.isEmpty())
                    detectToken(accumulator);
                if(currentCharacter != ' ' && currentCharacter != '\n')
                {
                    accumulator = String.valueOf(currentCharacter);
                    detectToken(accumulator);
                    accumulator = "";
                }
                if(currentCharacter == ' ')
                    accumulator = "";
                if(currentCharacter == '\n')
                {
                    if(!accumulator.isEmpty())
                        detectToken(accumulator); // ; before new line
                    accumulator = "";
                    line++;
                }
            }
            else
            {
                accumulator = accumulator + currentCharacter;
            }

        }

        if (!accumulator.isEmpty())
        {
            detectToken(accumulator);
        }

        System.out.println("Program " + filename + " is lexically correct");

        String pifFilename = filename.substring(0, filename.length() - 4) + "FA_PIF.txt";
        String stIdentFilename = filename.substring(0, filename.length() - 4) + "FA_ST_IDENTIFIERS.txt";
        String stConstFilename = filename.substring(0, filename.length() - 4) + "FA_ST_CONSTANTS.txt";

        writePIFToFile(pifFilename);
        writeSymbolTables(stConstFilename,stIdentFilename);
    }
}
