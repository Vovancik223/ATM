import java.util.Scanner;

public class ATM {
    private static final int MAX_RETRY = 3; // Количество попыток ввода PIN
    private static final int REPLENISHMENT_LIMIT  = 1000000; //Лимит единоразового пополнения счёта
    private  static final int ATM_LIMIT = 100000; //Лимит выдачи средств у банкомата
    private static final String CARD_FILE = "cards.txt"; //Файл, где хранится информация о картах
    private static final String CARD_PATTERN = "\\d{4}-\\d{4}-\\d{4}-\\d{4}"; //Регулярное выражение для проверки на правильность введеного номера карты

    private CardRepository cardRepository;

    private Card card;
    private Scanner scanner;

    public ATM() {
        cardRepository = new CardRepository(CARD_FILE);
        scanner = new Scanner(System.in);
    }

    public boolean authenticationCard(String cardNumber){
        if (!cardNumber.matches(CARD_PATTERN)) {
            System.out.println("Неверно введен номер карты.");
            return false;
        }
        card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Карта с таким номером не найдена.");
            return false;
        }
        if (card.isBlocked() && System.currentTimeMillis() - card.getBlockTime() < 86400000) {
            System.out.println("Карта заблокирована.");
            long hh= (int) (System.currentTimeMillis()-card.getBlockTime())/3600000;
            System.out.println("Карта будет разблокирована через "+(24-hh)+"ч.");
            return false;
        }
        int attempts = 0;
        boolean isAuthenticated = false;
        while (attempts < MAX_RETRY && !isAuthenticated) {
            System.out.println("Введите PIN:");
            String pin = scanner.nextLine();
            if (card.authenticate(pin)) {
                isAuthenticated = true;
            } else {
                attempts++;
                System.out.println("Неверный PIN. Осталось попыток: " + (MAX_RETRY - attempts));
            }
        }
        if (!isAuthenticated) {
            System.out.println("Карта была заблокирована на 24 часа из-за множественный неверных попыток ввода PIN.");
            card.blockCard();
            cardRepository.updateCard(card);
            return false;
        }
        return true;
    }

    public int getBalance(){
        return card.getBalance();
    }
    public void withdraw(int amountToWithdraw){
        if(amountToWithdraw>ATM_LIMIT){
            System.out.println("Сумма для снятия превышает лимит выдачи у банкомата.");
            return;
        }
        else if(amountToWithdraw>card.getBalance()){
            System.out.println("Недостаточно средств на карте.");
            return;
        }
        else {
            card.withdraw(amountToWithdraw);
            System.out.println("Снятие со счёта прошло успешно.");
            System.out.println("Новый баланс: "+card.getBalance());
        }
        return;
    }
    public void deposit(int amountToDeposit){
        if(amountToDeposit > REPLENISHMENT_LIMIT){
            System.out.println("Сумма пополнения превышает допустимую.");
            return;
        }
        card.deposit(amountToDeposit);
        System.out.println("Счёт пополнен.");
        System.out.println("Новый баланс: "+card.getBalance());
        return;
    }
    public void ExitATM(){
        cardRepository.updateCard(card);
    }
}
