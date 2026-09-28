package postoffice;

import java.util.ArrayList;
import java.util.List;

/**
 * Поштова скринька. Зберігає список відправлень та сповіщає власника
 * при отриманні нового відправлення (реалізує Notifiable).
 *
 * Демонструє два типи зв'язків між класами:
 *  - КОМПОЗИЦІЯ з {@link MailboxKey}: ключ створюється тут, у конструкторі,
 *    не приймається ззовні і знищується разом зі скринькою -- частина
 *    не має самостійного існування без цілого;
 *  - АГРЕГАЦІЯ з {@link MailboxOwner}: власник створюється поза межами
 *    Mailbox і передається за посиланням -- він існує незалежно
 *    (може існувати і без цієї скриньки, і навіть володіти кількома
 *    скриньками одночасно).
 */
public class Mailbox implements Notifiable {

    private String address;
    private MailboxOwner owner;                 // агрегація (owner створюється зовні)
    private final MailboxKey key;                // композиція (key створюється всередині)
    private final List<MailItem> items = new ArrayList<>();

    public Mailbox(String address, MailboxOwner owner) {
        this.address = address;
        this.owner = owner;
        this.key = new MailboxKey(address); // ключ народжується разом зі скринькою
    }

    public String getAddress() {
        return address;
    }

    public MailboxOwner getOwner() {
        return owner;
    }

    public MailboxKey getKey() {
        return key;
    }

    public List<MailItem> getItems() {
        return items;
    }

    public void addItem(MailItem item) {
        items.add(item);
        notifyOwner("Нове відправлення: " + item.getType() + " від " + item.getSender());
    }

    public boolean removeItem(MailItem item) {
        return items.remove(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public void notifyOwner(String message) {
        System.out.println("[Сповіщення для " + owner.getName() + "] " + message);
    }

    public void printContents() {
        System.out.println("Вміст скриньки за адресою " + address + ":");
        if (items.isEmpty()) {
            System.out.println("  (порожньо)");
            return;
        }
        for (MailItem item : items) {
            System.out.println("  - " + item);
        }
    }
}
