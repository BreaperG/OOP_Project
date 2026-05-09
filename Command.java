package ComandLine;

/**
 * Функционален интерфейс за имплементация на шаблона Command (Команда).
 * Позволява уеднаквено изпълнение на всички команди от потребителя.
 */
@FunctionalInterface
public interface Command {
    /**
     * Изпълнява дадената команда.
     *
     * @param args Масив от аргументи, подадени от конзолата.
     */
    void execute(String[] args);
}