package task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование функции arcsinApprox")
class ArcsinTest {
    private static final double DELTA = 1e-2;

    @Test
    @DisplayName("Проверка arcsin(0) с 5 членами ряда")
    void testArcsinZero() {
        double result = Arcsin.arcsinApprox(0, 5);
        assertEquals(0.0, result, DELTA, "arcsin(0) должен быть 0");
    }

    @ParameterizedTest(name = "Проверка arcsin({0}) с 10 членами ряда")
    @ValueSource(doubles = {0.5, -0.5})
    @DisplayName("Параметризованный тест для x = 0.5 и x = -0.5")
    void testArcsinParameterized(double x) {
        double result = Arcsin.arcsinApprox(x, 10);
        double expected = Math.asin(x);
        assertEquals(expected, result, DELTA, "Результат не совпадает с Math.asin(x)");
    }

    @Test
    @DisplayName("Проверка границы x = 1 (15 членов)")
    void testArcsinOne() {
        double result = Arcsin.arcsinApprox(1, 120000);
        double expected = Math.asin(1);
        assertEquals(expected, result, DELTA, "arcsin(1) должен равняться Math.asin(1)");
    }

    @Test
    @DisplayName("Проверка границы x = -1 (15 членов)")
    void testArcsinMinusOne() {
        double result = Arcsin.arcsinApprox(-1, 120000);
        double expected = Math.asin(-1);
        assertEquals(expected, result, DELTA, "arcsin(-1) должен равняться Math.asin(-1)");
    }

    @ParameterizedTest(name = "Проверка неверного x = {0}")
    @ValueSource(doubles = {1.1, -1.1})
    @DisplayName("Проверка выброса исключения при x вне диапазона [-1,1]")
    void testArcsinInvalidX(double x) {
        assertThrows(IllegalArgumentException.class, () -> Arcsin.arcsinApprox(x, 5),
                     "При x вне диапазона должно бросаться исключение");
    }

    @Test
    @DisplayName("Проверка выброса исключения при terms < 1")
    void testArcsinInvalidTerms() {
        assertThrows(IllegalArgumentException.class, () -> Arcsin.arcsinApprox(0.5, 0),
                     "При terms < 1 должно бросаться исключение");
    }
}
