public class Complex {
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
      this.real = real;
        this.imaginary = imaginary;
     }

    public double getReal(){
      return real;
	}

    public double getImaginary() {
         return imaginary;
  }

    public Complex add(Complex other) {
        return new Complex(real + other.real,imaginary + other.imaginary);
    }

     public Complex subtract(Complex other) {
	    return new Complex(real - other.real, imaginary - other.imaginary);
    }

  public Complex multiply(Complex other) {
        double deystvChast = real * other.real - imaginary * other.imaginary;
        double mnimChast = real * other.imaginary + imaginary * other.real;
         return new Complex(deystvChast, mnimChast);
    }

    public Complex divide(Complex other) {
        if(other.isZero()) {
	        throw new ArithmeticException("Деление на нулевое комплексное число.");
        }
      double znamenatel = other.real * other.real + other.imaginary * other.imaginary;
        double deystvChast  =  (real * other.real + imaginary * other.imaginary) / znamenatel;
        double mnimChast = (imaginary * other.real - real * other.imaginary) / znamenatel;
        return new Complex(deystvChast, mnimChast);
    }

    public double abs() {
         return Math.sqrt(real * real + imaginary * imaginary);
    }

  public boolean isZero(){
        return real == 0 && imaginary == 0;
    }

     public String toString() {
      if (imaginary < 0) {
            return real + " - " + (-imaginary) + "i";
        }
        return real + " + " + imaginary + "i";
    }
}
