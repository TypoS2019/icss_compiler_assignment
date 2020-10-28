package nl.han.ica.icss.parser;

public class HANListNode<T> {
    T element;
    HANListNode<T> next;

    public HANListNode(T value) {
        this.element = value;
    }

    public HANListNode() {

    }

    @Override
    public String toString() {
        return "{" +
                "element=" + element +
                ", next=\n" + next +
                '}';
    }
}