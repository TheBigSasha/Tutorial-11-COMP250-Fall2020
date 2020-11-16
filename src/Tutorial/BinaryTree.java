package Tutorial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * This data structures adds stuff to it in a way that keeps it in order,
 * or rather easy to get into order quickly. Good for homies with chaotic
 * energy who may want to spontaneously add and remove items from their
 * dataset but still need to be able to quickly get their s*** together
 * in case someone wants things in order.
 */
public class BinaryTree<E extends Comparable<E>> implements Iterable<E> {
    /**
     * The top of this binary tree
     */
    private TreeNode<E> root;
    /**
     * The size of this tree
     */
    private int size = 0;

    /**
     * Instantiates an empty binary tree
     */
    public BinaryTree(){

    }

    public E largest(){
        if(root == null) return null;
        TreeNode<E> temp = root;
        while(temp.getLeft() != null){
            temp = temp.getLeft();
        }
        return temp.getData();
    }

    public E smallest(){
        if(root == null) return null;
        TreeNode<E> temp = root;
        while(temp.getRight() != null){
            temp = temp.getRight();
        }
        return temp.getData();
    }

    /**
     * Adds an item to the tree, it puts it in its place right away
     * @param data what is being added
     */
    public void add(E data){
        if(root == null){
            root = new TreeNode<>(data);
            size++;
        }else{
            add(data, root);
        }


    }

    private void add(E data, TreeNode<E> node){
        if(data.compareTo(node.getData()) > 0){
            if(node.getLeft() == null){
                node.setLeft(new TreeNode<>(data));
                size++;
            }else{
                add(data, node.getLeft());
            }
        }else{
            if(node.getRight() == null){
                node.setRight(new TreeNode<>(data));
                size++;
            }else{
                add(data, node.getRight());
            }
        }
    }

    /**
     * We return the contents of the tree in sort order!
     * @return a list of the items in the tree in order
     */
    public Stack<E> inOrder(){
        Stack<E> workingStack = new Stack<>();
        return inOrder(root, workingStack);
    }

    /**
     * We return the contents of the tree in reverse sort order!
     * @return a list of the items in the tree in reverse order
     */
    public Stack<E> reverseOrder(){
        Stack<E> workingStack = new Stack<>();
        return reverseOrder(root, workingStack);
    }


    private Stack<E> inOrder(TreeNode<E> temp, Stack<E> list){
        if(temp.getRight() != null){
            inOrder(temp.getRight(),list);
        }
        list.push(temp.data);
        if(temp.getLeft() != null){
            inOrder(temp.getLeft(),list);
        }
        return list;
    }

    /**
     * Shh! We are being sneaky with our recursion and hiding it.
     * This is helping us with reverseOrder()
     * @return a list of the items in the tree in reverse order
     */
    private Stack<E> reverseOrder(TreeNode<E> temp, Stack<E> list){
        if(temp.getLeft() != null){
            reverseOrder(temp.getLeft(),list);
        }
        list.push(temp.data);
        if(temp.getRight() != null){
            reverseOrder(temp.getRight(),list);
        }
        return list;
    }

    /**
     * An iterator which traverses through our tree
     *
     * @return an Iterator which traverses our btree in order
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Stack<E> inOrder = reverseOrder();
            @Override
            public boolean hasNext() {

                return !inOrder.isEmpty();
            }

            @Override
            public E next() {
                return inOrder.pop();
            }
        };
    }


    /**
     * The node used for the binary tree
     * @param <E>
     */
    class TreeNode<E>{
        private TreeNode<E> left;
        private TreeNode<E> right;
        private E data;

        public TreeNode(E data) {
            this.data = data;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }
    }


    private static void testBinaryTree(){
        BinaryTree<PocketMonster> tree = new BinaryTree<>();
        ArrayList<PocketMonster> list = new ArrayList<>();
        for(int i = 0; i < 150; i++){
            PocketMonster pm = PocketMonster.createPocketMonster();
            tree.add(pm);
            list.add(pm);
        }
        list.sort(PocketMonster::compareTo);
        List<PocketMonster> treeTraversed = tree.inOrder();
        List<PocketMonster> treeReversed = tree.reverseOrder();
        if(treeTraversed.get(0) != treeReversed.get(treeReversed.size() - 1)) System.out.println("Reversed is not reversed!");
        if(treeTraversed.size() != list.size()) System.out.println("FAILED! Sizes do not match!");
        for(int i = 0; i < list.size(); i++){
           if(treeTraversed.get(i).compareTo(list.get(i)) != 0) {
               System.out.println("FAILED! Not in sort order at index " + i);
           }
            System.out.println(treeTraversed.get(i) + "        " + list.get(i));
        }

        for(PocketMonster pm : tree){
            System.out.println(pm);
        }
    }

    public static void main(String args[]){
        testBinaryTree();
    }
}
