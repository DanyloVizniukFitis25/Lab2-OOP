package postoffice;

import java.util.Arrays;

/**
 * Демонстрація роботи моделі поштової скриньки (лаба 1)
 * + демонстрація зв'язків композиції та агрегації (лаба 2).
 */
public class Main {

    public static void main(String[] args) {
        // --- Створення незалежних об'єктів (передумова для агрегації) ---
        MailboxOwner owner = new MailboxOwner("Іван Петренко", "+380501112233");
        Postman postman = new Postman("Олег Коваль", "PM-042");

        // Один і той самий власник (owner) агрегується двома різними скриньками -
        // це показує, що MailboxOwner існує незалежно від конкретної Mailbox.
        Mailbox mailboxHome = new Mailbox("вул. Хрещатик, 1, кв. 5", owner);
        Mailbox mailboxOffice = new Mailbox("вул. Шевченка, 10, офіс 3", owner);

        System.out.println("=== Композиція: Mailbox --*-- MailboxKey ===");
        System.out.println(mailboxHome.getAddress() + " -> " + mailboxHome.getKey());
        System.out.println(mailboxOffice.getAddress() + " -> " + mailboxOffice.getKey());
        System.out.println("(кожен ключ створено разом зі своєю скринькою і належить лише їй)");

        System.out.println();
        System.out.println("=== Агрегація: Mailbox --o-- MailboxOwner ===");
        System.out.println("Обидві скриньки належать одному й тому ж власнику: " + owner.getName());

        // --- Поштове відділення агрегує скриньки та листонош ---
        PostOffice postOffice = new PostOffice("Відділення №42");
        postOffice.registerMailbox(mailboxHome);
        postOffice.registerMailbox(mailboxOffice);
        postOffice.hirePostman(postman);

        System.out.println();
        System.out.println("=== Агрегація: PostOffice --o-- Mailbox, PostOffice --o-- Postman ===");
        postOffice.printInfo();

        // --- Функціонал з лаби 1: доставка, статистика, сортування ---
        Letter simpleLetter = new Letter("Ольга Іванова", owner.getName(), 0.05, 2);
        RegisteredLetter registeredLetter = new RegisteredLetter(
                "Податкова служба", owner.getName(), 0.08, 3, "UA123456789");
        Parcel parcel = new Parcel(
                "Інтернет-магазин \"Розетка\"", owner.getName(), 1.75,
                "NP987654321", "Навушники");

        System.out.println();
        System.out.println("=== Доставка відправлень ===");
        postman.deliver(mailboxHome, simpleLetter);
        postman.deliver(mailboxHome, registeredLetter);
        postman.deliver(mailboxHome, parcel);

        System.out.println();
        mailboxHome.printContents();

        System.out.println();
        System.out.println("=== Статистика скриньки ===");
        System.out.printf("Загальна вага відправлень: %.2f кг%n",
                MailStatistics.totalWeight(mailboxHome));

        MailItem[] itemsArray = mailboxHome.getItems().toArray(new MailItem[0]);
        int swaps = MailStatistics.sortByWeight(itemsArray);
        System.out.println("Відправлення, відсортовані за вагою (обмінів: " + swaps + "):");
        for (MailItem item : itemsArray) {
            System.out.println("  - " + item);
        }

        System.out.println();
        System.out.println("=== Демонстрація математичної задачі окремо (масив чисел) ===");
        double[] weights = {1.75, 0.05, 0.08, 3.20, 0.15};
        System.out.println("До сортування: " + Arrays.toString(weights));
        int numSwaps = MailStatistics.bubbleSort(weights);
        System.out.println("Після сортування: " + Arrays.toString(weights));
        System.out.println("Кількість обмінів: " + numSwaps);

        // --- Демонстрація незалежності частин при агрегації ---
        System.out.println();
        System.out.println("=== Скринька видаляється з відділення, але сама і далі існує ===");
        postOffice.unregisterMailbox(mailboxOffice);
        postOffice.printInfo();
        System.out.println("Скринька " + mailboxOffice.getAddress() +
                " все ще існує і має власника: " + mailboxOffice.getOwner().getName());
    }
}
