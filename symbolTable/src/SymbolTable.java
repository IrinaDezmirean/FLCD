import java.util.Iterator;

class Node<K,V>
{
    K key;
    V value;
    Node<K,V> nextNode;


    public Node (K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}


public class SymbolTable<K,V> implements Iterable<V>
{

    private int tableSize;
    private Node<K,V>[] table;

    public SymbolTable(int size)
    {
        tableSize = size;
        table = new Node[size];
    }

    private int hashFunction(K key)
    {
        return (int)key % tableSize;
    }

    public void put(K key, V value)
    {
        int hash = hashFunction(key);
        Node<K,V> newNode;
        if(value instanceof String)
        {
            V newValue = (V) ("\"" + value + "\"");
            newNode = new Node<>(key, newValue);
        }
        else
        {
            newNode = new Node<>(key, value);
        }

        if (table[hash] == null)
        {
            table[hash] = newNode;
        }
        else
        {
            // Handle collision by chaining
            Node<K,V> currentNode = table[hash];
            while (currentNode.nextNode != null)
            {
                if (currentNode.key == key)
                {
                    // Update the value if the key already exists
                    currentNode.value = value;
                    return;
                }
                currentNode = currentNode.nextNode;
            }
            if (currentNode.key == key)
            {
                // Update the value if the key already exists
                currentNode.value = value;
            }
            else
            {
                currentNode.nextNode = newNode;
            }
        }
    }

    public V get(K key)
    {
        int hash = hashFunction(key);
        Node<K,V> currentNode = table[hash];

        while (currentNode != null)
        {
            if (currentNode.key == key) {
                return currentNode.value;
            }
            currentNode = currentNode.nextNode;
        }

        return null; // Key not found
    }

    public void remove(K key)
    {
        int hash = hashFunction(key);
        Node<K,V> currentNode = table[hash];
        Node<K,V> previousNode = null;

        while (currentNode != null)
        {
            if (currentNode.key == key)
            {
                if (previousNode == null)
                {
                    table[hash] = currentNode.nextNode;
                }
                else
                {
                    previousNode.nextNode = currentNode.nextNode;
                }
                return;
            }
            previousNode = currentNode;
            currentNode = currentNode.nextNode;
        }
    }

    @Override
    public Iterator<V> iterator()
    {
        return new SymbolTableIterator();
    }

    private class SymbolTableIterator implements Iterator<V>
    {
        private int currentIndex = 0;
        private Node<K,V> currentNode = null;

        @Override
        public boolean hasNext()
        {
            if (currentNode != null && currentNode.nextNode != null)
            {
                return true;
            }
            for (int i = currentIndex; i < tableSize; i++)
            {
                if (table[i] != null)
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public V next()
        {
            if (!hasNext())
            {
                throw new RuntimeException();
            }
            if (currentNode == null || currentNode.nextNode == null)
            {
                while (table[currentIndex] == null)
                {
                    currentIndex++;
                }
                currentNode = table[currentIndex];
                currentIndex++;
            }
            else
            {
                currentNode = currentNode.nextNode;
            }
            return currentNode.value;
        }
    }
}
