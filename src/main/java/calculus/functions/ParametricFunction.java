package calculus.functions;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;

public class ParametricFunction extends MathFunction {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;

    public ParametricFunction(PolynomialFunction yFunction, PolynomialFunction xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
    }
    
    @Override
    public double applyAsDouble(double x) {
        return yFunction.applyAsDouble(x);
    }

    @Override
    public MathFunction derive() {
        return new RationalFunction(yFunction, xFunction);
    }

    @Override
    public String toString() {
        return String.format("y= %s\nx= %s", yFunction.toString().replace('X', 't'), xFunction.toString().replace('X', 't'));
    }

    @Override
    public Linear linearOn(MathFunction derivative, double x) {
        return super.linearOn(derivative, xFunction.applyAsDouble(x));
    }
}
