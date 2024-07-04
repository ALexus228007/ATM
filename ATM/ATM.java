package ATM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM implements BankCardSecurityOperations, BankCardTransaction
{
    private BankCard bankCard;
    private double moneyInATM;
    private ArrayList<BankCard> bankCards;

    public ATM(double moneyInATM) throws Exception
    {
        Object[] objects = loginInATM();
        this.bankCards=(ArrayList<BankCard>) objects[0];
        this.bankCard = (BankCard) objects[1];
        this.moneyInATM = moneyInATM;
        if (bankCard == null)
        {
            System.out.println("В доступе отказано");
            writeBankCardsToFile(bankCards);
        }
        else
        {
            menu();
        }
    }

    private void menu() throws IOException
    {
        int answer;
        Scanner in = new Scanner(System.in);
        while (true)
        {
            while (true)
            {
                try
                {
                    System.out.println("""
                            Выберите и введите номер желаемой операции
                            1. Проверить баланс карты
                            2. Снятие наличных
                            3. Пополнение баланса
                            4. Закончить работу с банкоматом
                            Ответ:\s""");
                    answer = in.nextInt();
                    if (answer < 1 || answer > 4)
                    {
                        throw new IllegalArgumentException();
                    }
                    break;
                } catch (IllegalArgumentException e)
                {
                    System.out.println("Ошибка. Введено неверное значение. Введите значение от 1 до 4");
                } catch (InputMismatchException e)
                {
                    System.out.println("Ошибка. Введено неверное значение. Ответ может состоять только из цифр 1,2,3.");
                    in.next();
                }
            }
            if (answer == 1)
            {
                checkCardBalance(bankCard);
            }
            else if (answer == 2)
            {
                withdrawMoney(bankCard, moneyInATM);
            }
            else if (answer == 3)
            {
                toUpBankCardBalance(bankCard, moneyInATM);
            }
            else
            {
                writeBankCardsToFile(bankCards);
                break;
            }
        }
    }
}
