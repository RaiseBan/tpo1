package task3;

public class Message {
    private final String content;
    private final Person sender;

    public Message(Person sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Person getSender() {
        return sender;
    }
}
