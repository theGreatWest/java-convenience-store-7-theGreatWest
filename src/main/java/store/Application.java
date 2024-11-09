package store;

import store.controller.ApplicationController;
import store.core.config.ApplicationConfig;

public class Application {
    public static void main(String[] args) {
        ApplicationConfig appConfig = new ApplicationConfig();
        ApplicationController appController = appConfig.appController();

        appController.run();
    }
}
