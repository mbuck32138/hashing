package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Matt Buck
 */
public class BSTMap<K extends Comparable<K>, V>
        implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }

    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Iterator iter = iterator();
        Set<K> allKeys = new HashSet<>();
        while (iter.hasNext()) {
            K key = (K) iter.next();
            allKeys.add(key);
        }
        return allKeys;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator<>(root);
    }


    public void displayBSTMap() {
        displayHelper(root, 0);
    }


    //Prints the BST as subtrees.
    private void displayHelper(Node tree, int level) {
        if (tree == null) {
            return;
        }

        System.out.println("level:   " + level + "      " + tree);
        System.out.println("               /      \\");
        System.out.println("         " + tree.left + "        " + tree.right);
        System.out.println();
        level += 1;
        displayHelper(tree.left, level);
        displayHelper(tree.right, level);
    }
    /*************************************************************************
    * Helper class
    *************************************************************************/

    //Iterates based on position in the BST.
    //No comparisons are performed.
    public class KeyIterator<K> implements Iterator<K> {
        Stack<Node> stack = new Stack<>();

        //Stack up all of the far left nodes.
        public KeyIterator(Node root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node topNode = stack.pop();
            K result = (K) topNode.key;
            if (topNode.right != null) {
                topNode = topNode.right;
                while (topNode != null) {
                    stack.push(topNode);
                    topNode = topNode.left;
                }
            }
            return result;
        }
    }


}