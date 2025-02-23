package task1;

/**
 * Приближённое вычисление arcsin(x) через ряд Тейлора.
 */
public class Arcsin {
    public static double arcsinApprox(double x, int terms) {
        if (x < -1.0 || x > 1.0) {
            throw new IllegalArgumentException("x must be in the range [-1, 1]");
        }
        if (terms < 1) {
            throw new IllegalArgumentException("terms must be >= 1");
        }

        double result = 0.0;
        for (int k = 0; k < terms; k++) {
            double numerator = factorial(2 * k);
            double denominator = Math.pow(4, k) * Math.pow(factorial(k), 2) * (2*k + 1);
            result += (numerator / denominator) * Math.pow(x, (2*k + 1));
        }
        return result;
    }

    private static double factorial(int n) {
        if (n <= 1) return 1.0;
        return n * factorial(n - 1);
    }
}

