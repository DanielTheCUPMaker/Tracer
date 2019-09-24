package calculus.functions;

import calculus.Variable;

import java.util.Arrays;
import java.util.List;

public class Constant extends PolynomialFunction {
    public static Constant fromConstants(double a) {
        return new Constant(generateFunction(Arrays.asList(a)));
    }

    protected Constant(List<Variable> variables) {
        super(variables, Zero::new, Linear::new);
    }

    @Override
    public List<Double> solve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
