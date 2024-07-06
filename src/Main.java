import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты (формата XXXX-XXXX-XXXX-XXXX):");
        String cardNumber = scanner.nextLine();
        if(atm.authenticationCard(cardNumber)){
            while (true) {
                System.out.println("1. Проверить баланс");
                System.out.println("2. Снять средства");
                System.out.println("3. Положить средства");
                System.out.println("4. Выход");
                System.out.print("Выберите операцию: ");
                int numberOperation = Integer.parseInt(scanner.nextLine());

                switch (numberOperation) {
                    case 1:
                        System.out.println("Ваш баланс: " + atm.getBalance());
                        break;
                    case 2:
                        try {
                            System.out.println("Сумма для снятия: ");
                            int amountToWithdraw = Integer.parseInt(scanner.nextLine());
                            atm.withdraw(amountToWithdraw);
                        }
                        catch (NumberFormatException e){
                            System.out.println("Введена неправильная сумма");
                        }
                        break;
                    case 3:
                        try {
                            System.out.println("Сумма для пополнения: ");
                            int amountToDeposit = Integer.parseInt(scanner.nextLine());
                            atm.deposit(amountToDeposit);
                        }
                        catch (NumberFormatException e){
                            System.out.println("Введена неправильная сумма");
                        }
                        break;
                    case 4:
                        atm.ExitATM();
                        System.out.println("Не забудьте забрать карту!");
                        return;
                    default:
                        System.out.println("Такой операции не существует. Попробуйте еще раз.");
                }
            }
        }
    }
}