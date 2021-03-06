package ru.academit.ilnitsky.temperature2;

import ru.academit.ilnitsky.temperature2.common.UnitConverter;
import ru.academit.ilnitsky.temperature2.common.View;
import ru.academit.ilnitsky.temperature2.controller.Controller;
import ru.academit.ilnitsky.temperature2.gui.FrameView;
import ru.academit.ilnitsky.temperature2.model.TEW2Converter;

/**
 * Класс приложение с интерфейсом Swing.
 * Создаёт модель, представление и контроллер, и запускае представление view.
 * Created by Mike on 28.01.2017.
 */
public class Application {
    public static void main(String[] args) {
        UnitConverter model = new TEW2Converter();

        try (View view = new FrameView(model)) {
            Controller controller = new Controller(model, view);
            view.addViewListener(controller);
            view.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
