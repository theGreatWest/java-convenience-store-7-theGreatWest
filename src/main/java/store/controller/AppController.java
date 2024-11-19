package store.controller;

import store.model.UserRequest;
import store.service.AppService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class AppController {
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    public void run(){
        List<UserRequest> userRequests = promptInputProducts();
    }

    public List<UserRequest> promptInputProducts(){
        outputView.printWelcomeStockStatus(appService.stockStatus());
        try {
            return appService.createUserRequestObj(inputView.readProducts());
        }catch (IllegalArgumentException e){
            outputView.printExceptionMsg(e.getMessage());
            return null;
        }
    }

}
