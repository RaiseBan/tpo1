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
    }

    @Nested
    @DisplayName("Тесты для SpaceTimeHole")
    class SpaceTimeHoleTests {
        private SpaceTimeHole hole;

        @BeforeEach
        void init() {
            hole = new SpaceTimeHole("Earth", "Magrathea");
        }

        @Test
        @DisplayName("Перенос сообщения без изменений")
        void testTransportMessage() {
            Person sender = new Person("Arthur");
            Message msg = new Message(sender, "I need a towel");
            String transported = hole.transportMessage(msg);
            assertEquals("I need a towel", transported, "Сообщение должно переноситься без изменений");
        }

        @Test
        @DisplayName("Проверка origin и destination")
        void testOriginDestination() {
            assertAll(
                    () -> assertEquals("Earth", hole.getOrigin(), "Origin должен быть 'Earth'"),
                    () -> assertEquals("Magrathea", hole.getDestination(), "Destination должен быть 'Magrathea'")
            );
        }
    }

    @Nested
    @DisplayName("Тесты для Galaxy")
    class GalaxyTests {
        private Galaxy galaxy;
        private WarlikeCreature creature;

        @BeforeEach
        void init() {
            galaxy = new Galaxy("Milky Way");
            creature = new WarlikeCreature("Zorg", WarStatus.PEACE, CreatureType.WARRIOR);
            galaxy.addInhabitant(creature);
        }

        @Test
        @DisplayName("Добавление обитателя")
        void testAddInhabitant() {
            assertAll(
                    () -> assertEquals(1, galaxy.getInhabitants().size(), "В галактике должен быть 1 обитатель"),
                    () -> assertEquals("Zorg", galaxy.getInhabitants().get(0).getSpecies(), "Название вида не совпадает")
            );
        }

        @Test
        @DisplayName("Рассылка сообщения и реакция существа")
        void testBroadcastMessage() {
            galaxy.broadcastMessage("There are problems in the sector");
            assertEquals(WarStatus.ON_EDGE, creature.getWarStatus(), "Состояние существа должно измениться на ON_EDGE");
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
