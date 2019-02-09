package main.util;


public class DoublyLinkedList {

    public Node head, tail;

    public DoublyLinkedList(){
        head = null;
        tail = null;
    }

    public void remove(Node n){
        if(n.pre!=null){
            n.pre.next = n.next;
        }else{
            head = n.next;
        }

        if(n.next!=null){
            n.next.pre = n.pre;
        }else{
            tail = n.pre;
        }

    }

    public void setHead(Node n){
        n.next = head;
        n.pre = null;

        if(head!=null)
            head.pre = n;

        head = n;

        if(tail == null)
            tail = head;
    }

    public void setTail(Node n){
        n.pre = tail;
        n.next = null;

        if(tail!=null)
            tail.next = n;

        tail = n;

        if(head == null)
            head = tail;
    }
}
