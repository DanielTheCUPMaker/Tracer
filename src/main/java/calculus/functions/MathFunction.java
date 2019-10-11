package calculus.functions;

import calculus.functions.polynomialFunctions.Linear;
import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        try {
            return Arrays.asList(new Complex(newtonMethod(that, calcStep(that)), 0));
        } catch (StackOverflowError e) {
            throw new UnsolvableFunctionParametersException();
        }
    }

    public double lengthAt(double from, double to) {
        return lengthAt(from, to, calcStep(shortestLength(from, to)));
    }

    public double lengthAt(double from, double to, double step) {
        double sum = 0;

        for(double x=from; x <= to; x+=step) {
            sum += shortestLength(x, x+step);
        }
        return sum;
    }

    public double pointAtLength(double start, double length, double accuracy) {
        double sum = 0;
        double x = start;

        for(; !ExtendedMath.constrained(sum, length-accuracy, length+accuracy) && sum <length; x+=accuracy) {
            sum += shortestLength(x, x+accuracy);
        }

        return x;
    }

    private double shortestLength(double xStart, double xEnd) {
        return shortestLength(xStart, at(xStart), xEnd, at(xEnd));
    }

    private double shortestLength(double xStart, double yStart, double xEnd, double yEnd) {
        return Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));
    }

    private double calcStep(double length) {
        return 1 / (length * 1000);
    }

    private double newtonMethod(double y, double accuracy) {
        double initialGuess = new Random().nextDouble();//segments and stuff yata yata

        return nextGuess(derive(), initialGuess, y, accuracy);
    }

    private double nextGuess(MathFunction derivative, double current, double y, double accuracy) {
        double m = derivative.at(current);
        double tangentPoint = at(current);
        Linear tangent = new Linear(m, current, tangentPoint);
        double guess = tangent.realSolutionsTo(y).get(0);

        if(isCorrect(guess, y, accuracy))
            return guess;
        else
            return nextGuess(derivative, guess, y, accuracy);
    }

    private boolean isCorrect(double result, double y, double accuracy) {
        return ExtendedMath.constrained(result, y-accuracy, y+accuracy);
    }

    private List<Double> toReal(List<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
