import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class SymbolTable<K> implements Iterable<K>
{
    private int tableSize;
    private ArrayList<ArrayList<K>> table;

    public SymbolTable(int size)
    {
        tableSize = size;
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
        {
            table.add(new ArrayList<>());
        }
    }

    private int hashFunction(K key)
    {
        int hash = -1;
        if (key instanceof Integer)
            hash = (int) key % tableSize;
        if (key instanceof String)
        {
            int asciiSum = 0;
            for (int i = 0; i < ((String) key).length(); i++)
                asciiSum = asciiSum + ((String) key).charAt(i);
            hash = asciiSum % tableSize;
        }
        return hash;
    }

    public void put(K key)
    {
        int hash = hashFunction(key);
        ArrayList<K> bucket = table.get(hash);

        if (!contains(key))
        {
            bucket.add(key);
        }
    }

    public boolean contains(K key)
    {
        int hash = hashFunction(key);
        ArrayList<K> bucket = table.get(hash);
        return bucket.contains(key);
    }

    public Pair<Integer,Integer> getPosition(K key)
    {
        if (this.contains(key))
        {
            int hashValue = hashFunction(key);
            int index = table.get(hashValue).indexOf(key);
            if (index >= 0)
            {
                return new Pair<>(hashValue,index);
            }
        }
        return null; // Key not found or not present in the bucket
    }

    @Override
    public Iterator<K> iterator()
    {
        return new SymbolTableIterator();
    }

    private class SymbolTableIterator implements Iterator<K>
    {
        private int currentIndex = 0;
        private int currentListIndex = 0;

        @Override
        public boolean hasNext()
        {
            while (currentIndex < tableSize)
            {
                if (currentListIndex < table.get(currentIndex).size())
                {
                    return true;
                }
                currentIndex++;
                currentListIndex = 0;
            }
            return false;
        }

        @Override
        public K next()
        {
            if (!hasNext())
            {
                throw new RuntimeException("No more elements");
            }

            K key = table.get(currentIndex).get(currentListIndex);
            currentListIndex++;

            return key;
        }
    }
}
