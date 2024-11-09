package store.core.config;

import store.controller.ApplicationController;
import store.service.ApplicationService;
import store.service.DatabaseService;
import store.service.ValidationService;

public class ApplicationConfig {
    public ApplicationController appController() {
        return new ApplicationController(appService());
    }

    private ApplicationService appService() {
        return new ApplicationService(dbService(), validationService());
    }

    private DatabaseService dbService() {
        return new DatabaseService(dbInitializer());
    }

    private ValidationService validationService(){
        return new ValidationService();
    }

    private DatabaseInitializer dbInitializer() {
        return new DatabaseInitializer();
    }
}