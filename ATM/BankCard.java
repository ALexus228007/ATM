package ATM;

public class BankCard
{
    private String cardNumber;
    private String PIN;
    private double balance;

    private boolean isBaned = false;

    public BankCard(String cardNumber, String PIN, double balance)
    {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.PIN = PIN;
    }

    public boolean getIsBaned()
    {
        return isBaned;
    }

    public void setBaned(boolean baned)
    {
        isBaned = baned;
    }

    public BankCard()
    {
        this("", "", 0);
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getPIN()
    {
        return PIN;
    }

    public void setPIN(String PIN)
    {
        this.PIN = PIN;
    }
}

