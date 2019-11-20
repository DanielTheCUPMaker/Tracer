package calculus.variables;

public class Variable {
    private final double modifier;
    private final int power;

    public Variable(double modifier, int power) {
        this.modifier = modifier;
        this.power = power;
    }

    public static Variable modifier(double modifier) {
        return new Variable(modifier, 0);
    }

    public static Variable zero() {
        return new Variable(0,0);
    }

    public double modifier() {
        return modifier;
    }

    public int power() {
        return power;
    }

    public Variable derive() {
        return power == 0 ? numberDerivative() : normalDerivative();
    }

    public Variable integrate() {
        return new Variable(integralMultiplier(), power+1);
    }

    public double at(double value) {
        return modifier * Math.pow(value, power);
    }

    public Variable add(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier + variable.modifier(), power);
    }

    public Variable subtract(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier - variable.modifier(), power);
    }

    public Variable mul(Variable variable) {
        return new Variable(modifier * variable.modifier(), power + variable.power());
    }

    public Variable mul(double val) {
        return mul(new Variable(val, 0));
    }

    public Variable square() {
        return mul(this);
    }

    @Override
    public String toString() {
        return String.format("%fX^%d", modifier, power);
    }

    public boolean equals(Variable other) {
        return modifier == other.modifier() && power == other.power();
    }

    private Variable numberDerivative() {
        return new Variable(0,0);
    }

    private Variable normalDerivative() {
        return new Variable(derivativeMultiplier(), power-1);
    }

    private double derivativeMultiplier() {
        return modifier * power;
    }

    private double integralMultiplier() {
        return modifier /(power+1);
    }

    private void checkIfMatching(Variable variable) {
        if(variable.power() > power) {
            throw new BiggerPowerException();
        }

        else if(variable.power() < power) {
            throw new IllegalArgumentException("Given variable's power is smaller then the initial");
        }
    }
}
