package ATM;

import java.util.InputMismatchException;
import java.util.Scanner;

interface EnteringLoginAndPIN
{
    //Интерфейс помощник BankCardSecurityOperations
    default String enterCardNumber()
    {
        String cardNumber;
        Scanner in = new Scanner(System.in);
        while (true)
        {
            try
            {
                System.out.print("Введите номер карты (16 цифр) без пробелов и пропусков: ");
                cardNumber = in.next();
                if (cardNumber.length() != 16)
                {
                    throw new IllegalArgumentException("Строка должна быть 16 символов длиной.");
                }
                for (char c : cardNumber.toCharArray())
                {
                    if (!Character.isDigit(c))
                    {
                        throw new IllegalArgumentException("Строка должна содержать только цифры.");
                    }
                }
                System.out.println("Введенный номер карты: " + cardNumber);
                break;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Ошибка. Длина введенного номера не равна 16.");
            } catch (InputMismatchException e)
            {
                System.out.println("Ошибка. Введенный номер карты содержит неверные символы.");
            }
        }
        return cardNumber;
    }

    default String enterCardPIN()
    {
        String PIN;
        Scanner in = new Scanner(System.in);
        while (true)
        {
            try
            {
                System.out.print("Введите пароль карты (4 цифры) без пробелов и пропусков: ");
                PIN = in.next();
                if (PIN.length() != 4)
                {
                    throw new IllegalArgumentException();
                }
                for (char c : PIN.toCharArray())
                {
                    if (!Character.isDigit(c))
                    {
                        throw new InputMismatchException();
                    }
                }
                System.out.println("Введенный пароль карты: " + PIN);
                break;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Ошибка. Длина введенного пароля не равна 4.");
            } catch (InputMismatchException e)
            {
                System.out.println("Ошибка. Введенный пароль содержит неверные символы.");
            }
        }
        return PIN;
    }
}
