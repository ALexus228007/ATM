package ATM;

import java.util.InputMismatchException;
import java.util.Scanner;

interface BankCardTransaction extends WorkWithFile
{
    default void checkCardBalance(BankCard bankCard)
    {
        System.out.println("На вашем счету: " + bankCard.getBalance());
        checkPrinting();
    }

    default void withdrawMoney(BankCard bankCard, Double moneyInATM)
    {
        double desiredAmountOfMoney = askingUser(false);
        if (moneyInATM < desiredAmountOfMoney)
        {
            System.out.println("В банкомате недостаточно средств.");
        }
        else if (bankCard.getBalance() < desiredAmountOfMoney)
        {
            System.out.println("На вашем счету недостаточно денег для снятия желаемой суммы. " +
                    "Пожалуйста проверьте баланс.");
        }
        else
        {
            bankCard.setBalance(bankCard.getBalance() - desiredAmountOfMoney);
            moneyInATM -= desiredAmountOfMoney;
            checkPrinting();
        }
    }

    default void toUpBankCardBalance(BankCard bankCard, Double moneyInATM)
    {
        double replenishmentAmount = askingUser(true);
        if (replenishmentAmount <= 0)
        {
            System.out.println("Введенная сумма меньше либо равна нулю. Операция выполнена не будет.");
        }
        else if (replenishmentAmount > 1_000_000)
        {
            System.out.println("Такую сумму нельзя пополнить в банкомате. " +
                    "Пожалуйста обратитесь к сотрудникам банка.");
        }
        else
        {
            bankCard.setBalance(bankCard.getBalance() + replenishmentAmount);
            moneyInATM += replenishmentAmount;
            checkPrinting();
        }
    }

    private double askingUser(boolean value)//если true значит пополнение баланса, если false значит снятие наличных
    {
        double amount = 0;
        Scanner in = new Scanner(System.in);
        while (true)
        {
            try
            {
                System.out.println("Введите желаемую сумму: ");
                amount = in.nextDouble();
                if (value)
                {
                    if (amount <= 0 || amount > 1_000_000)
                    {
                        throw new IllegalArgumentException();
                    }
                }
                else
                {
                    if (amount <= 0)
                    {
                        throw new NumberFormatException();
                    }
                }
                break;

            } catch (NumberFormatException e)
            {
                System.out.println("Ошибка. Введенное значение меньше 0.");
            } catch (IllegalArgumentException e)
            {
                System.out.println("Ошибка. Введенное значение должно быть больше 0, но меньше либо равно 1000000.");
            } catch (InputMismatchException e)
            {
                System.out.println("Ошибка. Введено не число.");
                in.next();
            }
        }
        return amount;
    }

    default void checkPrinting()
    {
        Scanner in = new Scanner(System.in);
        int answer;
        System.out.println("Операция проведена успешно." +
                "\nНужен ли вам чек?");
        while (true)
        {
            try
            {
                System.out.print("""
                        Введите 1 если вам нужен чек
                        Введите 0 если чек вам не нужен
                        Ответ:\s""");
                answer = in.nextInt();
                if (answer != 0 && answer != 1)
                {
                    throw new IllegalArgumentException();
                }
                if (answer == 1)
                {
                    System.out.println("Пожалуйста заберите свой чек.");
                }
                break;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Ошибка. Введите либо 1 либо 0");
            } catch (InputMismatchException e)
            {
                System.out.println("Ошибка. Введено неверное значение.");
                in.next();
            }
        }
    }
}
