import java.awt.Color;
import java.lang.Math;
import org.apache.commons.numbers.complex.Complex;

public class Fractals {
    int w, h, maxIterations = 50, threshold = 2;
    double minRe, maxRe, minIm, maxIm;
    static Complex constant = Complex.ofCartesian(-0.4, 0.6);
    static Complex constant2 = Complex.ofCartesian(1, 0);
    static double power = 0.5;
    double initMinRe, initMaxRe, initMinIm, initMaxIm;
    static String formula = "(1,0)z^3 (-1,0)z^0";

    Fractals(int width, int height) {
        w = width;
        h = height;
        minRe = initMinRe = -3;
        maxRe = initMaxRe = 3;
        minIm = initMinIm = -2.25;
        maxIm = initMaxIm = minIm + (maxRe - minRe) * h / w;
    }

    public void resetVariables() {
        minRe = initMinRe;
        maxRe = initMaxRe;
        minIm = initMinIm;
        maxIm = initMaxIm;
    }

    private Color colorMethod(int n) {
        int gray = (int) (255 * (double) n / maxIterations);
        return new Color(gray, gray, gray);
    }

    public Color getColorNova(int i, int j) {
        Complex z = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                (maxIm - j * (maxIm - minIm) / (h - 1)));
        int n = 0;
        Polynomial poly = new Polynomial();
        poly = poly.parseFormula(formula);
        Polynomial polybar = poly.diffrentiate(1);
        while (z.abs() <= threshold && n < maxIterations) {
            z = z.subtract(poly.substitute(z).multiply(power).divide(polybar.substitute(z))).add(constant);
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorMagnet1(int i, int j) {
        Complex z, c;
        if (constant.abs() == 0) {
            z = Complex.ofCartesian(0, 0);
            c = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                    (maxIm - j * (maxIm - minIm) / (h - 1)));
        } else {
            z = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                    (maxIm - j * (maxIm - minIm) / (h - 1)));
            c = constant;
        }
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            z = ((z.pow(2).add(c.subtract(1))).divide(z.multiply(2).add(c.subtract(2)))).pow(2);
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorMagnet2(int i, int j) {
        Complex z, c;
        if (constant.abs() == 0) {
            z = Complex.ofCartesian(0, 0);
            c = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                    (maxIm - j * (maxIm - minIm) / (h - 1)));
        } else {
            z = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                    (maxIm - j * (maxIm - minIm) / (h - 1)));
            c = constant;
        }
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            z = ((z.pow(3).add((c.subtract(1)).multiply(3).multiply(z)).add((c.subtract(1)).multiply(c.subtract(2))))
                    .divide((z.pow(2)).multiply(3).add((c.subtract(2)).multiply(3).multiply(z))
                            .add((c.subtract(1)).multiply(c.subtract(2))).add(1)))
                    .pow(2);
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorMulticorn(int i, int j) {
        Complex c = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                (maxIm - j * (maxIm - minIm) / (h - 1)));
        Complex z = Complex.ofCartesian(0, 0);
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            if (power < 0) {
                z = z.conj().pow(-power).add(c);
            } else {
                z = z.pow(power).add(c);
            }
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorCollatz(int i, int j) {
        Complex z = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                (maxIm - j * (maxIm - minIm) / (h - 1)));
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            z = z.multiply(4).add(1)
                    .subtract((z.multiply(2).add(1))
                            .multiply(Complex.ofCartesian(Math.cos(z.getReal()) * Math.cosh(z.getImaginary()),
                                    -Math.sin(z.getReal()) * Math.sinh(z.getImaginary()))));
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorJulia(int i, int j) {
        Complex z = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                (maxIm - j * (maxIm - minIm) / (h - 1)));
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            z = z.pow(2).add(constant);
            n += 1;
        }
        return colorMethod(n);

    }

    public Color getColorPhoenix(int i, int j) {
        Complex z_1 = Complex.ofCartesian(0, 0);
        Complex c = constant;
        Complex p = constant2;
        Complex z = Complex.ofCartesian((maxIm - j * (maxIm - minIm) / (h - 1)),
                (minRe + i * (maxRe - minRe) / (w - 1)));
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            Complex z1 = z.pow(2).add(c).add(p.multiply(z_1));
            z_1 = z;
            z = z1;
            n += 1;
        }
        return colorMethod(n);
    }

    public Color getColorBurningShip(int i, int j) {
        Complex c = Complex.ofCartesian(minRe + i * (maxRe - minRe) / (w - 1),
                (maxIm - j * (maxIm - minIm) / (h - 1)));
        Complex z = Complex.ofCartesian(0, 0);
        int n = 0;
        while (z.abs() <= threshold && n < maxIterations) {
            Complex zn = Complex.ofCartesian(Math.abs(z.getReal()), -Math.abs(z.getImaginary()));
            z = zn.pow(2).add(c);
            n += 1;
        }
        return colorMethod(n);
    }
}
