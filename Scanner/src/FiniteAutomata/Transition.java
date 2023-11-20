package FiniteAutomata;

public class Transition
{
    private final String fromState;
    private final Character label;
    private final String toState;

    public Transition(String from, Character label, String to)
    {
        this.fromState = from;
        this.label = label;
        this.toState = to;
    }

    public String getFromState()
    {
        return fromState;
    }

    public String getToState()
    {
        return toState;
    }

    public Character getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return fromState + " --" + label + "--> " + toState + "\n";
    }
}
