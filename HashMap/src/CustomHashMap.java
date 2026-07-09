import lombok.*;
@Getter
@Setter
class Node<K,V>{
    K key;
    V val;// jsut bu comment it becone cpode of hash set
    Node next;
    Node prev;
    public Node(K key, V val){
        this.key = key;
        this.val = val;
    }
}
public class CustomHashMap<K,V>{
    private final int INITIAL_SIZE=4;
    private final int MAX_CAPACITY=1<<30;
    private final float LOAD_FACTOR=0.75f;
    private int countOfNodes=0;

    private Node[] map;
    CustomHashMap(){
        map=new Node[INITIAL_SIZE];// 4
        // in each bucket we are creating head and tail
        for(int i=0;i<INITIAL_SIZE;i++){
            map[i]=new Node<>(null,null);
            map[i].next=new Node<>(null,null);
            map[i].next.prev=map[i];
        }
    }
    public void put(K key,V value){
        Node node=findNode(key);
        if(node!=null){
            node.val=value;
            return;
        }
        // if key is note present find the bucket
        // we always inset new node in head of doubly linked list

        int bucket=Math.abs(key.hashCode())%map.length;
        Node head=map[bucket];
        Node newNode=new Node(key,value);
        Node old_first=head.next;
        head.next=newNode;
        newNode.prev=head;
        newNode.next=old_first;
        old_first.prev=newNode;
        countOfNodes++;
        if(countOfNodes>LOAD_FACTOR* map.length){
            rehash(map.length*2);
        }
    }
    private void rehash(int newSize){
        if(newSize>MAX_CAPACITY){
            System.out.println("Out of Memory");
            return;
        }
        Node[] newMap=new Node[newSize];
        for(int i=0;i<newSize;i++){
            newMap[i]=new Node<>(null,null);
            newMap[i].next=new Node<>(null,null);
            newMap[i].next.prev=newMap[i];// agin point to each other
        }
        // itrate through old map
        for(Node<K,V> curr : map){

            while(curr!=null){
                //  we ignore head and tail part
                if(curr.key==null){
                    curr=curr.next;
                    continue;
                }
                // we will go to each bucket in each bucket we will go for each node
                int bucket=Math.abs(curr.key.hashCode())%newSize;// curr will be in old map
                Node newHead=newMap[bucket];
                Node oldFirst=newHead.next; //new map ka pahal aelement
                Node nextNode=curr.next;
                newHead.next=curr;
                curr.prev=newHead;
                curr.next=oldFirst;
                oldFirst.prev=curr;
                curr=nextNode;
            }
        }
        map=newMap;
    }
    public int getSize(){
        return countOfNodes;
    }
    public void remove(K key){
        Node nodeToRemove=findNode(key);
        if(nodeToRemove==null){
            return;
        }
        Node prevNode=nodeToRemove.prev;
        Node nextNode=nodeToRemove.next;
        prevNode.next=nextNode;
        nextNode.prev=prevNode;
        countOfNodes--;
    }
    public V get(K key){
        Node node=findNode(key);
        return node==null ?null : (V)node.val;
    }

    public Node findNode(K key){
        int bucket=Math.abs(key.hashCode())%map.length;
        Node head=map[bucket];
        while(head!=null){
            if(head.key!=null && head.key.equals(key)){
                return head;
            }
            head=head.next;
        }
        return null;
    }

}
