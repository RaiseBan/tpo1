package task1;

public class Arcsin {
    public static double arcsinApprox(double x, int terms) {
        if (x < -1.0 || x > 1.0) {
            throw new IllegalArgumentException("x must be in the range [-1, 1]");
        }
        if (terms < 1) {
            throw new IllegalArgumentException("terms must be >= 1");
        }

        double result = 0.0;
        double term = x;
        double logFact2k = 0.0;
        double logFactK = 0.0;

        for (int k = 0; k < terms; k++) {
            if (k > 0) {
                logFact2k += Math.log(2 * k) + Math.log(2 * k - 1);
                logFactK += Math.log(k);
                term *= x * x;
            }

            double logDenominator = logFactK * 2 + k * Math.log(4) + Math.log(2 * k + 1);
            double coefficient = Math.exp(logFact2k - logDenominator);

            double delta = coefficient * term;
            if (Double.isNaN(delta) || Double.isInfinite(delta)) break;

            result += delta;
        }
        return result;
    }
}
