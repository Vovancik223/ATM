public class Card {
    private String cardNumber;
    private String pin;
    private int balance;
    private boolean isBlocked;
    private long blockTime;

    public Card(String cardNumber, String pin, int balance, boolean isBlocked, long blockTime) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.isBlocked = isBlocked;
        this.blockTime = blockTime;
    }

    public String getPin(){
        return pin;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public boolean authenticate(String pin) {
        if (isBlocked && System.currentTimeMillis() - blockTime < 86400000) {
            return false;
        } else {
            isBlocked = false;
        }
        return this.pin.equals(pin);
    }
    public void withdraw(int amountToWithdraw){
        balance -= amountToWithdraw;
        return;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void blockCard() {
        isBlocked = true;
        blockTime = System.currentTimeMillis();
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public long getBlockTime() {
        return blockTime;
    }
}