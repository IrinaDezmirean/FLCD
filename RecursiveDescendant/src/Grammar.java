import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar
{
    private List<String> nonterminals;
    private List<String> terminals;
    private Map<String,List<String>> productions;

    public Grammar(String fileName) throws GrammarException
    {
        nonterminals = new ArrayList<>();
        terminals = new ArrayList<>();
        productions = new HashMap<>();
        readFromFile(fileName);
    }

    private void readFromFile(String fileName) throws GrammarException
    {
        int lineNo = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                lineNo++;

                String[] sides = line.split( " = ");

                // split the sides of production and check the format
                if(sides.length != 2)
                {
                    throw new GrammarException("Wrong production format at line: " + lineNo);
                }
                else
                {
                    // process token by token non-terminals + terminals
                    processSide(sides[0]);
                    processSide(sides[1]);

                    // if not exception => process production
                    processProduction(sides[0],sides[1]);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error at reading file.\n"+e);
        }
    }

    private void processSide(String side) throws GrammarException
    {
        String[] tokens = side.split(" ");
        for(String token: tokens)
        {
            // non-terminal rule <>
            if(token.charAt(0) == '<' && token.charAt(token.length()-1) == '>')
            {
                // remove brackets
                String nonterminal = token.replace("<","").replace(">","");
                //add non-terminal if not yet in list
                if(!this.nonterminals.contains(nonterminal))
                    this.nonterminals.add(nonterminal);
            }
            // terminal rule no <>
            else if(token.charAt(0) != '<' && token.charAt(token.length()-1) != '>')
            {
                //add terminal if not yet in list
                if(!this.terminals.contains(token))
                    this.terminals.add(token);
            }
            else
            {
                throw new GrammarException("Wrong format for token: " + token);
            }
        }
    }

    private void processProduction(String leftHandSide, String rightHandSide)
    {
        leftHandSide = leftHandSide.replace("<","").replace(">","");
        rightHandSide = rightHandSide.replace("<","").replace(">","");
        String[] leftTokens = leftHandSide.split(" ");

        // in case we have multiple letters on right hand side
        for(String leftToken: leftTokens)
        {
            // new entry
            if(!productions.containsKey(leftToken))
            {
                productions.put(leftToken, new ArrayList<>());
                productions.get(leftToken).add(rightHandSide);
            }
            // key exists, just add value
            else if(productions.containsKey(leftToken) && !(productions.get(leftToken).contains(rightHandSide)))
            {
                productions.get(leftToken).add(rightHandSide);
            }
        }
    }

    public List<String> getNonterminals()
    {
        return nonterminals;
    }

    public List<String> getTerminals()
    {
        return terminals;
    }

    public Map<String,List<String>> getProductions()
    {
        return productions;
    }

}
