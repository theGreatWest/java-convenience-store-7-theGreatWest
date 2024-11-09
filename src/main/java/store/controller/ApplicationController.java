package store.controller;

import store.model.domain.UserRequests;
import store.service.ApplicationService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class ApplicationController {
    private final ApplicationService appService;
    private final InputView inputView;
    private final OutputView outputView;

    public ApplicationController(ApplicationService appService) {
        this.appService = appService;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        while (true) {
            outputView.printProducts(appService.getProductsInventory());
            try {
//                [ 변수 정리 ]
//                List<String[]> userRequest = promptProductsNameQuantity();
//                boolean userMembership =  promptMembership();
//                boolean goStop = promptInquireAnotherProducts();

                UserRequests userRequest = appService.createUserRequestObject(promptProductsNameQuantity(), promptMembership());



                if(!promptInquireAnotherProducts()){
                    break;
                }
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public List<String[]> promptProductsNameQuantity() {
        while (true) {
            outputView.productNameQuantityPrompt();
            try {
                String userRequestProductsQuantity = appService.checkEmptyRequest(inputView.readProducts());

                return appService.parseUserRequestProductsNameQuantity(userRequestProductsQuantity);
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public boolean promptMembership() {
        while (true) {
            outputView.membershipPrompt();
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public boolean promptInquireAnotherProducts(){
        while (true) {
            outputView.inquireAnotherProductsPrompt();
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }
}
