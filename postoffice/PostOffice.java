package postoffice;

import java.util.ArrayList;
import java.util.List;

/**
 * Поштове відділення.
 *
 * Демонструє зв'язок АГРЕГАЦІЯ: PostOffice зберігає посилання на
 * об'єкти Mailbox та Postman, але НЕ створює і не володіє їхнім
 * життєвим циклом. Ці об'єкти створюються ззовні (у клієнтському коді),
 * можуть існувати самостійно ще до реєстрації у відділенні, а також
 * можуть бути передані/переприв'язані до іншого відділення.
 */
public class PostOffice {

    private String name;
    private final List<Mailbox> mailboxes = new ArrayList<>();
    private final List<Postman> postmen = new ArrayList<>();

    public PostOffice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Агрегація: скринька передається за посиланням, а не створюється тут. */
    public void registerMailbox(Mailbox mailbox) {
        mailboxes.add(mailbox);
    }

    /** Агрегація: листоноша передається за посиланням, а не створюється тут. */
    public void hirePostman(Postman postman) {
        postmen.add(postman);
    }

    public boolean unregisterMailbox(Mailbox mailbox) {
        return mailboxes.remove(mailbox);
    }

    public List<Mailbox> getMailboxes() {
        return mailboxes;
    }

    public List<Postman> getPostmen() {
        return postmen;
    }

    public void printInfo() {
        System.out.println("Поштове відділення \"" + name + "\":");
        System.out.println("  Листоноші (" + postmen.size() + "):");
        for (Postman p : postmen) {
            System.out.println("    - " + p.getName() + " (ID: " + p.getEmployeeId() + ")");
        }
        System.out.println("  Скриньки на обслуговуванні (" + mailboxes.size() + "):");
        for (Mailbox m : mailboxes) {
            System.out.println("    - " + m.getAddress() + ", власник: " + m.getOwner().getName());
        }
    }
}
