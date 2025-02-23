package task3;

public class Person {
    private final String name;
    private String spokenPhrase;

    public Person(String name) {
        this.name = name;
    }

    public void speak(String phrase) {
        this.spokenPhrase = phrase;
    }

    public String getSpokenPhrase() {
        return spokenPhrase;
    }

    public String getName() {
        return name;
    }
}
