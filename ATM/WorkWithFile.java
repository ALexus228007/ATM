package ATM;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public interface WorkWithFile
{
    String fileName = "information.txt";
    String folderPath = "src/ATM"; // Replace with your actual folder path
    File file = new File(folderPath, fileName);

    private String prepareToWritingToFile(BankCard bankCard)
    {
        String cardNumber = bankCard.getCardNumber();
        return cardNumber.substring(0, 4) + "-" + cardNumber.substring(4, 8) + "-" +
                cardNumber.substring(8, 12) + "-" + cardNumber.substring(12, 16);
    }

    private ArrayList<BankCard> prepareAReadBanksCard(String str)
    {
        String[] data = str.split("\n");
        ArrayList<BankCard> bankCards = new ArrayList<>();
        BankCard bankCard;
        String[] information;
        Instant timeInFile = Instant.parse(data[0]);

        for (int i = 1; i < data.length; i++)
        {
            information = data[i].split(" ");
            bankCard = new BankCard(information[0].replaceAll("-", ""), information[1], Double.parseDouble(information[2]));
            if (information[3].equals("1"))
            {
                if (isTheRequiredTimePassSinceBan(timeInFile))
                {
                    bankCard.setBaned(false);
                }
                else
                {
                    bankCard.setBaned(true);
                }
            }
            else
            {
                bankCard.setBaned(false);
            }
            bankCards.add(bankCard);
        }
        return bankCards;
    }

    private boolean isTheRequiredTimePassSinceBan(Instant oldTime)
    {
        Instant newTime = Instant.now();
        Duration passedTime = Duration.between(oldTime, newTime);
        return passedTime.toHours() >= 24;
    }

    default void writeBankCardsToFile(ArrayList<BankCard> bankCards) throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
        {
            Instant time = Instant.now();
            bw.write(time + "\n");
            for (BankCard bankCard : bankCards)
            {
                bankCard.setCardNumber(prepareToWritingToFile(bankCard));
                bw.write(bankCard.getCardNumber() + " ");
                bw.write(bankCard.getPIN() + " ");
                bw.write(bankCard.getBalance() + " ");
                bw.write((bankCard.getIsBaned() ? "1" : "0") + "\n");
            }
        }
    }

    default ArrayList<BankCard> readBankCardsFromFile() throws IOException
    {
        String str = "";
        try (BufferedReader bf = new BufferedReader(new FileReader(file)))
        {
            int c;
            while ((c = bf.read()) != -1)
            {
                str += (char) c;
            }
        }
        return prepareAReadBanksCard(str);
    }
}
