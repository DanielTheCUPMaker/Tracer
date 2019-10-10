package calculus.functions;

import calculus.functions.polynomialFunctions.Linear;
import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();

    public MathFunction integrate() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public double difference(double from, double to) {
        return at(to) - at(from);
    }

    public List<Double> realSolutionsTo(double that) throws UnsupportedOperationException {
        return toReal(solutionsTo(that));
    }

    public List<Complex> solutionsTo(double that) throws UnsupportedOperationException, UnsolvableFunctionParametersException {
        throw new UnsupportedOperationException();
    }

    public double lengthAt(double from, double to) {
        return lengthAt(from, to, calcStep(shortestLength(from, to)));
    }

    public double lengthAt(double from, double to, double step) {
        return IntStream.range(0, (int) (to/step) + 3)
                .mapToDouble(i -> at(i * step + from + step) - at(i * step + from))
                .sum();
    }

    public double pointAtLength(double start, double length, double accuracy) {
        return newtonMethod(start, length, accuracy);
    }

    private double shortestLength(double start, double end) {
        double m = derive().at(start);

        return (end - start) * m;
    }

    private double calcStep(double length) {
        return 1 / (length * 1000);
    }

    private double newtonMethod(double start, double length, double accuracy) {
        double initialGuess = pointAtShortestLength(start, length);

        return nextGuess(derive(), start, initialGuess, length, accuracy);
    }

    private double pointAtShortestLength(double start, double length) {
        double m = derive().at(start);

        return length/m + start;
    }

    private double nextGuess(MathFunction derivative, double start, double current, double target, double accuracy) {
        double m = derivative.at(current);
        double y = at(current);
        Linear tangent = new Linear(m, current, y);
        double guess = tangent.realSolutionsTo(target).get(0);

        if(isCorrect(start, guess, target, accuracy))
            return guess;
        else
            return nextGuess(derivative, start, guess, target, accuracy);
    }

    private boolean isCorrect(double start, double result, double target, double accuracy) {
        return ExtendedMath.constrained(lengthAt(start, result, accuracy), target-accuracy, target+accuracy);
    }

    private List<Double> toReal(List<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
