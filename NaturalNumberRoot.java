import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program implements a root static method for NaturalNumber using the
 * interval halving root algorithm.
 *
 * @author Michael Silverii
 */
public final class NaturalNumberRoot {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private NaturalNumberRoot() {
        // no code needed here
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {

        // NN constants of 1 and 2 established
        NaturalNumber one = new NaturalNumber1L(1);
        NaturalNumber two = new NaturalNumber1L(2);
        /*
         * With interval halving a lowEnough and tooHigh value are the bounds or
         * range of possible values that the guess could occupy. A starting low
         * value of 0 is established, and a starting high value of n + 1 is
         * established.
         *
         */
        NaturalNumber lowEnough = new NaturalNumber1L(0);
        NaturalNumber tooHigh = new NaturalNumber1L();
        tooHigh.copyFrom(n);
        tooHigh.add(one);
        // A NN placeholder for the guess is established
        NaturalNumber guess = new NaturalNumber1L();
        /*
         * NN half is established as the midpoint between the upper and lower
         * bounds
         */
        NaturalNumber half = new NaturalNumber1L();
        half.add(lowEnough);
        half.add(tooHigh);
        half.divide(two);
        guess.copyFrom(half);
        NaturalNumber g = new NaturalNumber1L();
        /*
         * The NN g is a copy of guess, and is used in situations where the
         * guess value is needed, but aliasing is not intended
         */
        g.copyFrom(guess);
        /*
         * With interval halving, the guess must be raised to the r power so
         * that it can be compared to n.
         *
         */
        guess.power(r);
        /*
         * Range is assigned to be the difference between the high and low
         * bounds.
         */
        NaturalNumber range = new NaturalNumber1L();
        range.copyFrom(tooHigh);
        range.subtract(lowEnough);
        /*
         * This loop conducts the interval halving algorithm. The midpoint
         * between the upper and lower bounds is raised to the r power and then
         * compared to the n value. If it is lower than n, that guess value is
         * considered to be lowEnough, thus the range changes and the new
         * midpoint is considered the new guess, and the loop runs again. This
         * occurs until the difference between the lower and upper bounds is 1.
         * Once this happens, the lowEnough value is considered to be the
         * correct, rounded down estimate.
         */
        while (range.compareTo(one) != 0) {
            if (guess.compareTo(n) > 0) {
                tooHigh.copyFrom(g);
                range.copyFrom(tooHigh);
                range.subtract(lowEnough);
                half.clear();
                half.add(lowEnough);
                half.add(tooHigh);
                half.divide(two);
                guess.copyFrom(half);
                g.copyFrom(half);
                guess.power(r);
            } else {
                lowEnough.copyFrom(g);
                range.copyFrom(tooHigh);
                range.subtract(lowEnough);
                half.clear();
                half.add(lowEnough);
                half.add(tooHigh);
                half.divide(two);
                guess.copyFrom(half);
                g.copyFrom(half);
                guess.power(r);

            }

        }
        n.copyFrom(g);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
