public class Main {
    public static void main(String[] args) {
        LinkedListDummyHead<Integer> linkedListDummyHead = new LinkedListDummyHead<>();
        for (int i = 0; i < 5; i++) {
            linkedListDummyHead.addFirst(i);
            System.out.println(linkedListDummyHead);
        }

        linkedListDummyHead.add(3, 1000);
        System.out.println(linkedListDummyHead);

        linkedListDummyHead.remove(3);
        System.out.println(linkedListDummyHead);
        linkedListDummyHead.removeFirst();
        System.out.println(linkedListDummyHead);
        linkedListDummyHead.removeLast();
        System.out.println(linkedListDummyHead);
    }
}
