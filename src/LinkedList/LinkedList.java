package LinkedList;

import java.io.PrintWriter;

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
        //edge case for head
        if(head == null){
            head = new DoubleLinkNode(r,s,tail,null);
            tail = head;
            return;
        }
        //edge case for tail
        if(tail.getRow() < r || (tail.getRow() == r && tail.getSeat() < s)){
            tail.setNext(new DoubleLinkNode(r,s,null,tail));
            tail = tail.getNext();
            return;
        }
        DoubleLinkNode c = head;
        //looks for node that is after new node
        while(c.getNext() != null){
            if(c.getRow() >= r){
                if(c.getSeat() > s || c.getRow() > r){
                    //adds new node right before node that is bigger than it
                    DoubleLinkNode newN = new DoubleLinkNode(r,s,c,c.getPrev());
                    c.getPrev().setNext(newN);
                    c.setPrev(newN);
                    return;
                }
            }
            c = c.getNext();
        }
        //used when first making list from file
        tail.setNext(new DoubleLinkNode(r,s,null,tail));
        tail = tail.getNext();
    }
    
    public void deleteNode(int r, int s){
        DoubleLinkNode c = head;
        //Loops though list util it finds a node with matching row and seat
        while(c != null){
            if(c.getRow() == r){
                if(c.getSeat() == s){
                    //edge case for deleting head
                    if(c == head){
                        head = head.getNext();
                        return;
                    }
                    //edge case for deleteing tail
                    if(c == tail){
                        tail = tail.getPrev();
                        tail.setNext(null);
                        return;
                    }
                    //manges pointers to "go around" deleted node and thus delete it
                    c.getPrev().setNext(c.getNext());
                    c.getNext().setPrev(c.getPrev());
                    return;
                }
            }
            c = c.getNext();
        }
    }
    public void printR(PrintWriter out, DoubleLinkNode n)
    {
        if(n == null)
            return;
        else{
            out.print('.');
            if(n.getNext().getRow() > n.getRow())
                out.println();
            for(int i = n.getRow(); i < n.getNext().getRow()+1; i++){
                for(int j = n.getSeat(); j < n.getNext().getSeat(); j++)
                        out.print('#');
                out.println();
            }
            if(n.getNext().getNext() != null)
                printR(out, n.getNext());
        }
        out.print('.');
        if(n.getNext().getRow() > n.getRow())
            out.println();
        for(int i = n.getRow(); i < n.getNext().getRow()+1; i++){
                for(int j = n.getSeat(); j < n.getNext().getSeat(); j++)
                        out.print('#');
                out.println();
        }
    }
    public void printO(PrintWriter out, DoubleLinkNode n)
    {
        if(n == null)
            return;
        else{
            out.print('#');
            if(n.getNext().getRow() > n.getRow())
                out.println();
            for(int i = n.getRow(); i < n.getNext().getRow()+1; i++){
                for(int j = n.getSeat(); j < n.getNext().getSeat(); j++)
                        out.print('.');
                out.println();
            }
            if(n.getNext().getNext() != null)
                printO(out, n.getNext());
        }
        out.print('#');
        if(n.getNext().getRow() > n.getRow())
            out.println();
        for(int i = n.getRow(); i < n.getNext().getRow()+1; i++){
                for(int j = n.getSeat(); j < n.getNext().getSeat(); j++)
                        out.print('.');
                out.println();
        }
    }
}
