package Calculus;

import Tracer.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleFunction {
    private final List<Variable> variables;

    public SimpleFunction(List<Variable> variables, int dummy) {
        this.variables = new ArrayList<>();
        this.variables.addAll(variables);
    }

    public SimpleFunction(List<Double> multipliers) {
        variables = generateFunction(multipliers);
    }

    private List<Variable> generateFunction(List<Double> multipliers) {
        return IntStream.range(0, multipliers.size())
                .mapToObj(constantIndex -> new Variable(multipliers.get(constantIndex), multipliers.size() - (constantIndex+1) ))
                .collect(Collectors.toList());
    }

    public double at(double t) {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);

        variables.forEach(variable -> sum.updateAndGet(v -> v + variable.calcValue(t)));
        return sum.get();
    }

    public SimpleFunction derivative() {
        return new SimpleFunction(variables.stream()
                .limit(variables.size()-1)
                .map(Variable::derivative)
                .collect(Collectors.toList()), 0);
    }

    public SimpleFunction integral() {
        return new SimpleFunction(variables.stream()
                .map(Variable::integral)
                .collect(Collectors.toList()), 0);
    }

    public SimpleFunction pow(int power) {
        return new SimpleFunction(variables.stream()
                .map(variable -> new Variable(variable.getMultiplier(), variable.getPower() * power))
                .collect(Collectors.toList()), 0);
    }
}
