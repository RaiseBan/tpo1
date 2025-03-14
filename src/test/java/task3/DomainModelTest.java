package task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование доменной модели (7 сущностей)")
public class DomainModelTest {

    @Nested
    @DisplayName("Тесты для Person")
    class PersonTests {
        private Person person;

        @BeforeEach
        void init() {
            person = new Person("Arthur Dent");
        }

        @Test
        @DisplayName("Проверка имени и метода speak")
        void testPersonSpeakAndName() {
            assertAll(
                    () -> assertEquals("Arthur Dent", person.getName(), "Имя должно соответствовать переданному значению"),
                    () -> {
                        person.speak("Don't Panic");
                        assertEquals("Don't Panic", person.getSpokenPhrase(), "Фраза должна сохраняться корректно");
                    }
            );
        }

        @Test
        @DisplayName("Проверка на null в методе speak")
        void testPersonSpeakNull() {
            person.speak(null);
            assertNull(person.getSpokenPhrase(), "Фраза должна быть null, если передано null");
        }
    }

    @Nested
    @DisplayName("Тесты для Message")
    class MessageTests {
        private Person sender;

        @BeforeEach
        void init() {
            sender = new Person("Ford Prefect");
        }

        @Test
        @DisplayName("Проверка содержания сообщения и отправителя")
        void testMessageContentAndSender() {
            Message msg = new Message(sender, "Hello, Universe!");
            assertAll(
                    () -> assertEquals("Hello, Universe!", msg.getContent(), "Контент сообщения неверен"),
                    () -> assertEquals(sender, msg.getSender(), "Отправитель должен совпадать")
            );
        }

        @Test
        @DisplayName("Проверка на null в конструкторе Message")
        void testMessageNullContent() {
            assertThrows(IllegalArgumentException.class, () -> new Message(sender, null),
                    "Сообщение с null-контентом должно выбрасывать исключение");
        }
    }

    @Nested
    @DisplayName("Тесты для SpaceTimeHole")
    class SpaceTimeHoleTests {
        private SpaceTimeHole hole;
        private Galaxy galaxy;
        private WarlikeCreature creature;

        @BeforeEach
        void init() {
            galaxy = new Galaxy("Magrathea");
            creature = new WarlikeCreature("Vogon", WarStatus.PEACE, CreatureType.SCOUT);
            galaxy.addInhabitant(creature);
            hole = new SpaceTimeHole("Earth", galaxy);
        }

        @Test
        @DisplayName("Перенос сообщения в галактику")
        void testTransportMessage() {
            Person sender = new Person("Arthur");
            Message msg = new Message(sender, "I need a towel");

            hole.transportMessage(msg);

            // Проверяем, что сообщение получено галактикой
            assertNotNull(galaxy.getLastReceivedMessage(), "Галактика должна получить сообщение");
            assertEquals(msg.getContent(), galaxy.getLastReceivedMessage().getContent(),
                    "Галактика должна получить корректный текст сообщения");
            assertEquals(msg.getSender(), galaxy.getLastReceivedMessage().getSender(),
                    "Отправитель сообщения должен совпадать");
        }



        @Test
        @DisplayName("Проверка origin и destination")
        void testOriginDestination() {
            assertAll(
                    () -> assertEquals("Earth", hole.getOrigin(), "Origin должен быть 'Earth'"),
                    () -> assertEquals("Magrathea", hole.getDestination(), "Destination должен быть 'Magrathea'")
            );
        }

        @Test
        @DisplayName("Проверка на null в методе transportMessage")
        void testTransportMessageNull() {
            assertThrows(IllegalArgumentException.class, () -> hole.transportMessage(null),
                    "Передача null-сообщения должна выбрасывать исключение");
        }
    }

    @Nested
    @DisplayName("Тесты для Galaxy")
    class GalaxyTests {
        private Galaxy galaxy;
        private WarlikeCreature creature1;
        private WarlikeCreature creature2;

        @BeforeEach
        void init() {
            galaxy = new Galaxy("Milky Way");
            creature1 = new WarlikeCreature("Zorg", WarStatus.PEACE, CreatureType.WARRIOR);
            creature2 = new WarlikeCreature("Vogon", WarStatus.PEACE, CreatureType.SCOUT);
            galaxy.addInhabitant(creature1);
            galaxy.addInhabitant(creature2);
        }

        @Test
        @DisplayName("Добавление обитателя")
        void testAddInhabitant() {
            assertAll(
                    () -> assertEquals(2, galaxy.getInhabitants().size(), "В галактике должно быть 2 обитателя"),
                    () -> assertEquals("Zorg", galaxy.getInhabitants().get(0).getSpecies(), "Название вида не совпадает")
            );
        }

        @Test
        @DisplayName("Получение сообщения и реакция существа")
        void testReceiveMessage() {
            Message msg = new Message(new Person("Arthur"), "Problems are escalating");
            galaxy.receiveMessage(msg);

            assertAll(
                    () -> assertEquals(WarStatus.ON_EDGE, creature1.getWarStatus(), "Состояние существа 1 должно измениться на ON_EDGE"),
                    () -> assertEquals(WarStatus.ON_EDGE, creature2.getWarStatus(), "Состояние существа 2 должно измениться на ON_EDGE")
            );
        }

        @Test
        @DisplayName("Проверка коллективного состояния галактики")
        void testCollectiveWarStatus() {
            Message msg = new Message(new Person("Arthur"), "Problems everywhere");
            galaxy.receiveMessage(msg);
            assertEquals(WarStatus.ON_EDGE, galaxy.getCollectiveWarStatus(), "Галактика должна быть в состоянии ON_EDGE");

            creature1.setWarStatus(WarStatus.AT_WAR);
            assertEquals(WarStatus.AT_WAR, galaxy.getCollectiveWarStatus(), "Галактика должна быть в состоянии AT_WAR");
        }

        @Test
        @DisplayName("Проверка на null в методе receiveMessage")
        void testReceiveMessageNull() {
            assertThrows(IllegalArgumentException.class, () -> galaxy.receiveMessage(null),
                    "Передача null-сообщения должна выбрасывать исключение");
        }
    }

    @Nested
    @DisplayName("Тесты для WarlikeCreature")
    class WarlikeCreatureTests {
        private WarlikeCreature creature;

        @BeforeEach
        void init() {
            creature = new WarlikeCreature("Klingon", WarStatus.PEACE, CreatureType.SCOUT);
        }

        @Test
        @DisplayName("Начальные значения и реакция на сообщение")
        void testInitialValuesAndReaction() {
            assertAll(
                    () -> assertEquals("Klingon", creature.getSpecies(), "Вид существа неверный"),
                    () -> assertEquals(WarStatus.PEACE, creature.getWarStatus(), "Начальный статус должен быть PEACE"),
                    () -> assertEquals(CreatureType.SCOUT, creature.getCreatureType(), "Тип существа должен быть SCOUT")
            );

            creature.reactToMessage("Problems are escalating");
            assertEquals(WarStatus.ON_EDGE, creature.getWarStatus(), "При наличии слова 'Problems' статус должен стать ON_EDGE");
        }

        @Test
        @DisplayName("Проверка установки WarStatus через setWarStatus")
        void testSetWarStatus() {
            creature.setWarStatus(WarStatus.AT_WAR);
            assertEquals(WarStatus.AT_WAR, creature.getWarStatus(), "WarStatus должен устанавливаться через setWarStatus");
        }

        @Test
        @DisplayName("Проверка на null в методе reactToMessage")
        void testReactToMessageNull() {
            creature.reactToMessage(null);
            assertEquals(WarStatus.PEACE, creature.getWarStatus(), "Статус не должен измениться при null-сообщении");
        }
    }

    @Nested
    @DisplayName("Тесты для enum'ов WarStatus и CreatureType")
    class EnumTests {

        @Test
        @DisplayName("Проверка значений WarStatus")
        void testWarStatusEnum() {
            assertAll(
                    () -> assertEquals(WarStatus.PEACE, WarStatus.valueOf("PEACE")),
                    () -> assertEquals(WarStatus.ON_EDGE, WarStatus.valueOf("ON_EDGE")),
                    () -> assertEquals(WarStatus.AT_WAR, WarStatus.valueOf("AT_WAR"))
            );
        }

        @Test
        @DisplayName("Проверка значений CreatureType")
        void testCreatureTypeEnum() {
            assertAll(
                    () -> assertEquals(CreatureType.SCOUT, CreatureType.valueOf("SCOUT")),
                    () -> assertEquals(CreatureType.WARRIOR, CreatureType.valueOf("WARRIOR"))
            );
        }
    }



}