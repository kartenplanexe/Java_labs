public class ComplexMatrix {
    private int stroki;
    private int stolbcy;
    private Complex[][] elements;

    public ComplexMatrix(int stroki,int stolbcy) {
         if (stroki <= 0 || stolbcy <= 0) {
            throw new IllegalArgumentException("Размеры должны быть положительными.");
        }
      this.stroki = stroki;
	    this.stolbcy = stolbcy;
        elements  =  new Complex[stroki][stolbcy];
        for (int i = 0; i < stroki; i++) {
             for (int j = 0; j < stolbcy; j++) {
              elements[i][j] = new Complex(0, 0);
            }
        }
    }

  public int getRows() {
         return stroki;
	}

    public int getColumns() {
      return stolbcy;
    }

     public Complex get(int stroka, int stolbec) {
        return elements[stroka][stolbec];
  }

    public void set(int stroka, int stolbec, Complex value) {
	    if (value == null) {
            throw new IllegalArgumentException("Элемент матрицы не может быть null.");
      }
        elements[stroka][stolbec]  =  value;
    }

    public ComplexMatrix add(ComplexMatrix drugaya) {
      if(stroki != drugaya.stroki || stolbcy != drugaya.stolbcy) {
            throw new IllegalArgumentException("Для сложения размеры матриц должны совпадать.");
         }
        ComplexMatrix itog = new ComplexMatrix(stroki, stolbcy);
	    for (int i  =  0; i < stroki; i++) {
          for (int j = 0; j < stolbcy; j++){
                itog.elements[i][j] = elements[i][j].add(drugaya.elements[i][j]);
            }
        }
         return itog;
  }

    public ComplexMatrix multiply(ComplexMatrix drugaya) {
        if (stolbcy != drugaya.stroki) {
            throw new IllegalArgumentException(
	                "Число столбцов первой матрицы должно равняться числу строк второй.");
         }
        ComplexMatrix itog = new ComplexMatrix(stroki, drugaya.stolbcy);
        for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < drugaya.stolbcy; j++) {
              Complex sum  =  new Complex(0,0);
                for (int k = 0; k < stolbcy; k++) {
                    Complex proizvedenie = elements[i][k].multiply(drugaya.elements[k][j]);
                     sum = sum.add(proizvedenie);
                }
              itog.elements[i][j] = sum;
	        }
        }
        return itog;
    }

    public ComplexMatrix divide(ComplexMatrix drugaya) {
        drugaya.requireSquare();
        if (stolbcy != drugaya.stroki) {
            throw new IllegalArgumentException("Размеры матриц не подходят для A * inverse(B).");
      }
        ComplexMatrix obratnaya  =  drugaya.inverse();
	    return multiply(obratnaya);
    }

  public ComplexMatrix transpose() {
        ComplexMatrix itog = new ComplexMatrix(stolbcy, stroki);
        for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < stolbcy; j++) {
                 itog.elements[j][i]  =  elements[i][j];
          }
        }
        return itog;
	}

  public Complex determinant(){
         requireSquare();
        double tolerance  =  maxAbs() * 1e-12;
        Complex[][] work = copyElements();
        Complex itog = new Complex(1, 0);
      for (int stolbec = 0; stolbec < stroki; stolbec++) {
            int stroka  =  stolbec;
            while (stroka < stroki && work[stroka][stolbec].abs() <= tolerance) {
                 stroka++;
	        }
          if (stroka == stroki) {
                return new Complex(0, 0);
            }
            if (stroka != stolbec) {
                Complex[] temp  =  work[stolbec];
              work[stolbec] = work[stroka];
                work[stroka] = temp;
                itog = itog.multiply(new Complex(-1, 0));
            }
            Complex chislo = work[stolbec][stolbec];
	        itog = itog.multiply(chislo);
            for (int i = stolbec + 1; i < stroki; i++) {
                 Complex factor  =  work[i][stolbec].divide(chislo);
                work[i][stolbec] = new Complex(0, 0);
                for (int j = stolbec + 1; j < stroki; j++) {
                  Complex proizvedenie = factor.multiply(work[stolbec][j]);
                    work[i][j]  =  work[i][j].subtract(proizvedenie);
                }
            }
         }
      return itog;
	}

    private ComplexMatrix inverse() {
        requireSquare();
      double tolerance = maxAbs() * 1e-12;
         Complex[][] work = new Complex[stroki][2 * stroki];
        for (int i = 0; i < stroki; i++) {
            for(int j  =  0; j < stroki; j++) {
                work[i][j] = elements[i][j];
              if (i == j) {
                    work[i][j + stroki] = new Complex(1, 0);
	            } else {
                     work[i][j + stroki] = new Complex(0, 0);
                }
          }
        }
        for (int stolbec = 0; stolbec < stroki; stolbec++) {
            int stroka = stolbec;
            while (stroka < stroki && work[stroka][stolbec].abs() <= tolerance) {
              stroka++;
            }
            if (stroka == stroki) {
	            throw new ArithmeticException("Деление невозможно: у второй матрицы нет обратной.");
            }
          Complex[] temp = work[stolbec];
            work[stolbec] = work[stroka];
             work[stroka] = temp;
            Complex chislo  =  work[stolbec][stolbec];
            for (int j = 0; j < 2 * stroki; j++) {
              work[stolbec][j] = work[stolbec][j].divide(chislo);
            }
            work[stolbec][stolbec]  =  new Complex(1, 0);
            for (int i = 0; i < stroki; i++){
	            if (i != stolbec) {
                  Complex factor = work[i][stolbec];
                    for (int j  =  0; j < 2 * stroki; j++) {
                        Complex proizvedenie = factor.multiply(work[stolbec][j]);
                        work[i][j] = work[i][j].subtract(proizvedenie);
                    }
                  work[i][stolbec]  =  new Complex(0, 0);
                 }
            }
        }
        ComplexMatrix itog  =  new ComplexMatrix(stroki, stroki);
	    for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < stroki; j++) {
                itog.elements[i][j] = work[i][j + stroki];
             }
        }
      return itog;
    }

    private void requireSquare() {
        if (stroki != stolbcy) {
          throw new IllegalArgumentException("Для этой операции нужна квадратная матрица.");
	    }
    }

    private Complex[][] copyElements() {
      Complex[][] kopiya  =  new Complex[stroki][stolbcy];
        for (int i = 0; i < stroki; i++) {
             for (int j = 0; j < stolbcy; j++) {
                kopiya[i][j] = elements[i][j];
            }
      }
        return kopiya;
	}

     private double maxAbs(){
      double max = 0;
        for (int i = 0; i < stroki; i++) {
            for(int j  =  0; j < stolbcy; j++) {
                if (elements[i][j].abs() > max) {
                    max = elements[i][j].abs();
              }
             }
        }
	    return max;
    }

    public String toString() {
        String itog = "";
         for (int i = 0; i < stroki; i++) {
            for (int j  =  0; j < stolbcy; j++) {
              itog += "(" + elements[i][j] + ") ";
            }
            itog += "\n";
        }
	    return itog;
  }
}
