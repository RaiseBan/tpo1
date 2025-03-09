package task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование функции arcsinApprox")
class ArcsinTest {
    private static final double DELTA = 1e-2;

    @Test
    @DisplayName("Проверка arcsin(1)")
    void testArcsinOne() {
        double result = Arcsin.arcsinApprox(1, 120000);
        double expected = Math.asin(1);
        assertEquals(expected, result, DELTA, "arcsin(1) должен равняться Math.asin(1)");
    }

    @Test
    @DisplayName("Проверка arcsin(0.5)")
    void testArcsinHalf() {
        double result = Arcsin.arcsinApprox(0.5, 10);
        double expected = Math.asin(0.5);
        assertEquals(expected, result, DELTA, "arcsin(0.5) должен совпадать с Math.asin(0.5)");
    }

    @Test
    @DisplayName("Проверка arcsin(0)")
    void testArcsinZero() {
        double result = Arcsin.arcsinApprox(0, 5);
        assertEquals(0.0, result, DELTA, "arcsin(0) должен быть 0");
    }

    @Test
    @DisplayName("Проверка arcsin(-0.5)")
    void testArcsinMinusHalf() {
        double result = Arcsin.arcsinApprox(-0.5, 10);
        double expected = Math.asin(-0.5);
        assertEquals(expected, result, DELTA, "arcsin(-0.5) должен совпадать с Math.asin(-0.5)");
    }

    @Test
    @DisplayName("Проверка arcsin(-1)")
    void testArcsinMinusOne() {
        double result = Arcsin.arcsinApprox(-1, 120000);
        double expected = Math.asin(-1);
        assertEquals(expected, result, DELTA, "arcsin(-1) должен равняться Math.asin(-1)");
    }

    @Test
    @DisplayName("Проверка выброса исключения при x = 1.1")
    void testArcsinInvalidGreaterThanOne() {
        assertThrows(IllegalArgumentException.class, () -> Arcsin.arcsinApprox(1.1, 5),
                "При x > 1 должно бросаться исключение");
    }

    @Test
    @DisplayName("Проверка выброса исключения при x = -1.1")
    void testArcsinInvalidLessThanMinusOne() {
        assertThrows(IllegalArgumentException.class, () -> Arcsin.arcsinApprox(-1.1, 5),
                "При x < -1 должно бросаться исключение");
    }

    @Test
    @DisplayName("Проверка выброса исключения при terms < 1")
    void testArcsinInvalidTerms() {
        assertThrows(IllegalArgumentException.class, () -> Arcsin.arcsinApprox(0.5, 0),
                "При terms < 1 должно бросаться исключение");
    }
}
