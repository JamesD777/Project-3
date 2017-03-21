package LinkedList;

/**
 *
 * @author jcd4912
 */
public class LinkedList {
    private DoubleLinkNode head;
    private DoubleLinkNode tail;
    
    public LinkedList(){head = null;tail = null;}
    public LinkedList(DoubleLinkNode n){head = n;tail = n;}
    
    public DoubleLinkNode getHead(){return head;}
    public DoubleLinkNode getTail(){return tail;}
    public void setHead(DoubleLinkNode head){this.head = head;}
    public void setTail(DoubleLinkNode tail){this.tail = tail;}
    
    public void addNode(int r, int s){
        if(head == null){
            head = new DoubleLinkNode(r,s,null,null);
            tail = head;
        }
        else{
            tail.setNext(new DoubleLinkNode(r,s,null,tail));
            setTail(tail.getNext());
        }
    }
}
