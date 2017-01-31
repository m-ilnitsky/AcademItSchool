package ru.academit.ilnitsky.cash_machine_ui;

import ru.academit.ilnitsky.cash_machine_ui.presenter.Presenter;
import ru.academit.ilnitsky.moneybox.MoneyBox;
import ru.academit.ilnitsky.cash_machine_ui.common.View;
import ru.academit.ilnitsky.cash_machine_ui.gui.FrameView;

/**
 * Приложение "Банкомат" с графическим интерфейсом
 * Created by Mike on 29.01.2017.
 */
public class Application {
    public static void main(String[] args) {
        int containerSize = 15;
        int numBanknotes = 10;
        MoneyBox model = new MoneyBox(containerSize, numBanknotes);

        int[] offerForRemove = {50, 100, 200, 500, 1000, 2000, 3000, 5000};

        try (View view = new FrameView(model.getNominals(), offerForRemove)) {

            Presenter presenter = new Presenter(model, view);

            view.addViewListener(presenter);
            view.startApplication();
            System.out.println("Initial Balance: " + model.getAvailableMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
