import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiniteAutomata
{
    private String initialState;
    private List<String> finalStates;
    private List<String> states;
    private List<Character> alphabet;
    private List<Transition> transitions;
    private boolean isDfa = true;


    public FiniteAutomata(String filename)
    {
        this.initialState = null;
        this.finalStates = new ArrayList<>();
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitions = new ArrayList<>();
        readFiniteAutomataFormFile(filename);
    }

    public void readFiniteAutomataFormFile(String filename)
    {
        int lineNo = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                lineNo++;
                if(lineNo == 0)
                {
                    this.initialState = line;
                }
                else if(lineNo == 1)
                {
                    this.finalStates = Arrays.stream(line.split(",")).toList();
                }
                else
                {
                    String[] transitionElems = line.split(",");
                    this.transitions.add(new Transition(transitionElems[0],transitionElems[1].charAt(0),transitionElems[2]));
                    if(!this.states.contains(transitionElems[0]))
                    {
                        this.states.add(transitionElems[0]);
                    }
                    if(!this.states.contains(transitionElems[2]))
                    {
                        this.states.add(transitionElems[2]);
                    }
                    if(!this.alphabet.contains(transitionElems[1].charAt(0)))
                    {
                        this.alphabet.add(transitionElems[1].charAt(0));
                    }

                    // check if is dfa
                    if(this.states.contains(transitionElems[0]) && this.states.contains(transitionElems[1].charAt(0)))
                        isDfa = false;
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getNextStateFromCurrentStateWithLabel(String fromState, Character label)
    {
        for(Transition tran: transitions)
        {
            if(tran.getFromState().equals(fromState) && tran.getLabel() == label)
            {
                return tran.getToState();
            }
        }
        return null;
    }


    public boolean sequenceIsAccepted(String sequence) // ONLY FOR DFA
    {
        if(isDfa)
        {
            String currentState = initialState;
            for (int i = 0; i < sequence.length(); i++)
            {
                String nextState = getNextStateFromCurrentStateWithLabel(currentState, sequence.charAt(i));
                if (nextState == null)
                    return false;
                else
                {
                    currentState = nextState;
                }
            }
            if (finalStates.contains(currentState))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "INITIAL STATE: " + this.initialState + "\n" +
                "FINAL STATES: " + this.finalStates + "\n" +
                "ALL STATES: " + this.states + "\n" +
                "ALPHABET: " + this.alphabet + "\n" +
                "TRANSITIONS: " + this.transitions.toString();
    }


}
