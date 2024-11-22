package store.core.config;

import store.controller.AppController;
import store.service.AppService;
import store.service.DatabaseService;
import store.service.ValidationService;

public class AppConfig {
    public AppController appController(){
        return new AppController(appService());
    }

    private AppService appService(){
        return new AppService(databaseService(), validationService());
    }

    private DatabaseService databaseService(){
        return new DatabaseService();
    }

    private ValidationService validationService(){
        return new ValidationService();
    }
}
