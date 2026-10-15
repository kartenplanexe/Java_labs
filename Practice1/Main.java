import java.util.Scanner;
import java.util.NoSuchElementException;

public class Main {
    private static Scanner vvod = new Scanner(System.in);

    public static void main(String[] args) {
        ComplexMatrix matrica = null;
      System.out.println("Результат операции становится текущей матрицей.");

        while (true) {
            System.out.println("1. Создать новую матрицу");
             System.out.println("2. Показать текущую матрицу");
          System.out.println("3. Сложить с другой матрицей");
            System.out.println("4. Умножить на другую матрицу");
            System.out.println("5. Разделить на другую матрицу (A * B^(-1))");
            System.out.println("6. Транспонировать");
            System.out.println("7. Вычислить определитель");
          System.out.println("0. Выход");
             System.out.print("Выберите действие: ");

            try {
                int punkt  =  Integer.parseInt(vvod.next());
              if (punkt == 0) {
                    return;
                }
                 if (punkt < 1 || punkt > 7) {
                    System.out.println("Нет такого действия.");
              } else if (punkt == 1) {
                    matrica = readMatrix();
                    System.out.println("Текущая матрица:");
	                System.out.println(matrica);
                } else if (matrica == null) {
                  System.out.println("Сначала создайте матрицу.");
                } else {
                    ComplexMatrix vtoraya;
                    switch (punkt) {
                        case 2:
                          System.out.println(matrica);
                            break;
                         case 3:
                            System.out.println("Введите вторую матрицу:");
	                        vtoraya  =  readMatrix();
                          matrica = matrica.add(vtoraya);
                            System.out.println("Результат:");
                            System.out.println(matrica);
                            break;
                         case 4:
                          System.out.println("Введите вторую матрицу:");
                            vtoraya = readMatrix();
                            matrica  =  matrica.multiply(vtoraya);
                            System.out.println("Результат:");
                            System.out.println(matrica);
	                        break;
                         case 5:
                            System.out.println("Введите вторую матрицу:");
                            vtoraya = readMatrix();
                            matrica = matrica.divide(vtoraya);
                          System.out.println("Результат:");
                            System.out.println(matrica);
                            break;
                         case 6:
                            matrica  =  matrica.transpose();
                          System.out.println("Результат:");
	                        System.out.println(matrica);
                            break;
                        case 7:
                            System.out.println("Определитель: " + matrica.determinant());
                          break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести число. Для дробной части используйте точку.");
          } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
	        } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
              return;
            }
        }
    }

  private static ComplexMatrix readMatrix() {
        System.out.print("Количество строк: ");
        int stroki = Integer.parseInt(vvod.next());
	    System.out.print("Количество столбцов: ");
        int stolbcy = Integer.parseInt(vvod.next());
      ComplexMatrix matrica = new ComplexMatrix(stroki,stolbcy);
         System.out.println("Введите действительную и мнимую части каждого числа через пробел.");
        System.out.println("Например, 2 -3 означает 2 - 3i. Дробные числа вводите через точку.");
        for (int i = 0; i < stroki; i++) {
            for (int j = 0; j < stolbcy; j++) {
              System.out.print("Элемент [" + (i + 1) + "][" + (j + 1) + "]: ");
                double real  =  Double.parseDouble(vvod.next());
                double imaginary = Double.parseDouble(vvod.next());
                 Complex chislo = new Complex(real, imaginary);
	            matrica.set(i, j, chislo);
          }
        }
        return matrica;
    }
}
