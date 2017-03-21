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
        if(head == null){
            head = new DoubleLinkNode(r,s,null,null);
            tail = head;
        }
        else if(tail.getRow() == r && tail.getSeat() == s){
            tail.setNext(new DoubleLinkNode(r,s,null,tail));
            setTail(tail.getNext());
        }
        else{
            DoubleLinkNode cur = head;
            while(cur.getNext() != null){
                if(cur.getRow() >= r){
                    if(cur.getSeat() > s){
                        DoubleLinkNode N = new DoubleLinkNode(r,s,cur,cur.getPrev());
                        cur.getPrev().setNext(N);
                        cur.setPrev(N);
                        return;
                    }
                }
                cur = cur.getNext();
            }
        }
    }
    public void deleteNode(int r, int s){
        DoubleLinkNode c = head;
        while(c.getNext() != null){
            if(c.getRow() == r){
                if(c.getSeat() == s){
                    c.getPrev().setNext(c.getNext());
                    c.getNext().setPrev(c.getPrev());
                    break;
                }
            }
            c = c.getNext();
        }
    }
    void print(PrintWriter out, DoubleLinkNode n)
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
                    print(out, n.getNext());
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
}
