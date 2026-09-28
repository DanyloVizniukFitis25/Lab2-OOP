package postoffice;

/**
 * Ключ від поштової скриньки.
 *
 * Демонструє зв'язок КОМПОЗИЦІЯ (варіант 1 завдання): об'єкт MailboxKey
 * не має сенсу і не може існувати поза межами конкретної Mailbox --
 * він створюється всередині конструктора Mailbox і знищується разом з нею.
 * Конструктор пакетного рівня доступу (без модифікатора) не дозволяє
 * створити ключ ззовні пакета -- створити MailboxKey може лише сам клас Mailbox.
 */
public class MailboxKey {

    private final String keyCode;

    /**
     * Пакетний конструктор: викликається виключно з Mailbox,
     * що й реалізує композицію "ціле створює та володіє частиною".
     */
    MailboxKey(String mailboxAddress) {
        this.keyCode = "KEY-" + Integer.toHexString(mailboxAddress.hashCode()).toUpperCase();
    }

    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        return "Ключ " + keyCode;
    }
}
