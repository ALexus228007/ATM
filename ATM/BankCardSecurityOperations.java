package ATM;

import java.util.ArrayList;

interface BankCardSecurityOperations extends EnteringLoginAndPIN, WorkWithFile
{
    default Object[] loginInATM() throws Exception
    {
        ArrayList<BankCard> bankCards = readBankCardsFromFile();
        String cardNumber = enterCardNumber();
        Object[] objects = new Object[2];
        objects[0] = bankCards;
        if (isCardNumberExist(bankCards, cardNumber))
        {
            BankCard bankCard = findBankCard(bankCards, cardNumber);
            if (bankCard != null && !bankCard.getIsBaned())
            {
                System.out.println("Вы ввели существующий номер карты.");
                for (int i = 0; i < 3; i++)
                {
                    if (isCardPINIsRight(bankCard, enterCardPIN()))
                    {
                        System.out.println("Вы ввели правильный пароль.");
                        objects[1] = bankCard;
                        return objects;
                    }
                    else
                    {
                        System.out.println("Введенный пароль не верен." +
                                "\nУ вас осталось " + (3 - (i + 1)) + " попыток");
                    }
                }
                System.out.println("Ваша банковская карта заблокирована. Карта автоматически разблокируется через 24 часа");
                bankCard.setBaned(true);
                objects[1] = null;
                return objects;
            }
            else
            {
                System.out.println("В доступе отказано, так как карта заблокирована.");
            }
        }
        else
        {
            System.out.println("Такой банковской карты не зарегистрировано ");
        }
        objects[1] = null;
        return objects;
    }

    private boolean isCardNumberExist(ArrayList<BankCard> bankCards, String cardNumber)
    {
        for (BankCard bankCard : bankCards)
        {
            if (bankCard.getCardNumber().equals(cardNumber))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isCardPINIsRight(BankCard bankCard, String PIN)
    {
        return bankCard.getPIN().equals(PIN);
    }

    private BankCard findBankCard(ArrayList<BankCard> bankCards, String cardNumber)
    {
        for (BankCard bankCard : bankCards)
        {
            if (bankCard.getCardNumber().equals(cardNumber))
            {
                return bankCard;
            }
        }
        return null;
    }

}
