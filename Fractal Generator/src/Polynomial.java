import java.util.ArrayList;
import org.apache.commons.numbers.complex.Complex;

public class Polynomial {
    private ArrayList<PolynomialTerm> polynomial = new ArrayList<PolynomialTerm>();

    private class PolynomialTerm {
        Complex coefficent;
        int power;

        PolynomialTerm(Complex coefficent, int power) {
            this.coefficent = coefficent;
            this.power = power;
        }

        PolynomialTerm diffrentiate() {
            Complex newCoeff = this.coefficent.multiply(Complex.ofCartesian(this.power, 0));
            return new PolynomialTerm(newCoeff, power - 1);
        }

        Complex substitute(Complex z) {
            return z.pow(power).multiply(coefficent);
        }

        public String toString() {
            return "(" + this.coefficent + ")" + "*z" + "^" + this.power;
        }
    }

    void addTerm(Complex coeff, int power) {
        polynomial.add(new PolynomialTerm(coeff, power));
    }

    Polynomial diffrentiate(int derivative) {
        Polynomial n = new Polynomial();
        for (PolynomialTerm p : polynomial) {
            for (int i = 0; i < derivative; i++)
                p = p.diffrentiate();
            if (p.power < 0)
                continue;
            n.addTerm(p.coefficent, p.power);
        }
        return n;
    }

    Polynomial parseFormula(String formula) {
        String[] arr1 = formula.split(" ");
        Polynomial poly = new Polynomial();
        for (String str : arr1) {
            String[] arr2 = str.split("z");
            poly.addTerm(Complex.parse(arr2[0].strip()), Integer.parseInt(arr2[1].substring(1)));
        }
        return poly;
    }

    Complex substitute(Complex z) {
        Complex sum = Complex.ofCartesian(0, 0);
        for (PolynomialTerm p : polynomial) {
            sum = sum.add(p.substitute(z));
        }
        return sum;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < polynomial.size(); i++) {
            if (polynomial.get(i).power == 0)
                s += "(" + polynomial.get(i).coefficent + ")" + " + ";
            else
                s += polynomial.get(i) + " + ";
        }
        return s.substring(0, s.length() - 3);
    }
}