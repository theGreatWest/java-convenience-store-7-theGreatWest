package store;

import store.controller.AppController;
import store.core.config.AppConfig;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        AppController appController = appConfig.appController();

        appController.run();
    }
}
