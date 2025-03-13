package task3;

public class Message {
    private final String content;
    private final Person sender;

    public Message(Person sender, String content) {
        if (content == null) {
            throw new IllegalArgumentException("Контент сообщения не может быть null");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Отправитель не может быть null");
        }
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