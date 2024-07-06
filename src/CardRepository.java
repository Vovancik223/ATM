import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CardRepository {
    private Map<String, Card> cards;
    private String fileName;

    public CardRepository(String fileName) {
        this.fileName = fileName;
        this.cards = new HashMap<>();
        loadCards();
    }

    public Card getCardByNumber(String cardNumber) {
        return cards.get(cardNumber);
    }

    public void updateCard(Card card) {
        cards.put(card.getCardNumber(), card);
        saveCards();
    }

    private void loadCards() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    String cardNumber = parts[0];
                    String pin = parts[1];
                    int balance = Integer.parseInt(parts[2]);
                    boolean isBlocked = Boolean.parseBoolean(parts[3]);
                    long blockTime = Long.parseLong(parts[4]);
                    cards.put(cardNumber, new Card(cardNumber, pin, balance, isBlocked, blockTime));
                }
            }
            catch (NumberFormatException e){
                System.out.println("Неверный формат данных в файле.");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Файл с данными не найден.");
        }
    }

    private void saveCards() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Card card : cards.values()) {
                writer.write(card.getCardNumber() + " " +card.getPin() +" "+ card.getBalance() + " " + card.isBlocked() + " " + card.getBlockTime());
                writer.newLine();
            }
            System.out.println("Файл сохранен.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Неудалось сохранить файл.");
        }
    }
}