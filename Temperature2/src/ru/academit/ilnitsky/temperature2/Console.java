package ru.academit.ilnitsky.temperature2;

import ru.academit.ilnitsky.temperature2.common.UnitConverter;
import ru.academit.ilnitsky.temperature2.common.View;
import ru.academit.ilnitsky.temperature2.console.ConsoleView;
import ru.academit.ilnitsky.temperature2.controller.Controller;
import ru.academit.ilnitsky.temperature2.model.TEWConverter;

/**
 * Класс консольное приложение.
 * Создаёт модель, представление и контроллер, и запускае представление view.
 * Created by UserLabView on 02.03.17.
 */
public class Console {
    public static void main(String[] args) {
        UnitConverter model = new TEWConverter();

        try (View view = new ConsoleView(model)) {
            Controller controller = new Controller(model, view);
            view.addViewListener(controller);
            view.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
