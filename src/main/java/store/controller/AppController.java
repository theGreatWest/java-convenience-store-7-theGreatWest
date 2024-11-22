package store.controller;

import store.core.constant.ExceptionMessage;
import store.model.Receipt;
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
        while(true){
            List<UserRequest> userRequests = promptInputProducts();
            if(userRequests!=null){
                List<Receipt> receipts = promptPayment(userRequests);
                String receipt = appService.createFinalReceipt(receipts, promptMembership());
                outputView.printReceipt(receipt);
            }else promptMembership();
            if(!promptAskContinue()) break;
        }
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

    public List<Receipt> promptPayment(List<UserRequest> userRequests){
        for(UserRequest userRequest : userRequests){
            if(appService.checkApplyPromotion(userRequest)) {
                int get = appService.checkLessRequest(userRequest);
                if(get!=0) promptAskAdditionFreeProduct(userRequest, get);

                int regularQuantity = appService.checkLackStock(userRequest);
                if(regularQuantity!=0) promptAskRegularPayment(userRequest, regularQuantity);
            }
        }
        return appService.payment(userRequests);
    }

    public void promptAskAdditionFreeProduct(UserRequest userRequest, int get){
        outputView.printAskAdditionFreeProduct(userRequest.getName(), get);
        try{
            if(processYesNo(inputView.readYesNo())){
                userRequest.updateQuantity(true, get);
            }
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMsg(e.getMessage());
        }
    }

    public void promptAskRegularPayment(UserRequest userRequest, int regularQuantity){
        outputView.printAskRegularPayment(userRequest.getName(), regularQuantity);
        try{
            if(!processYesNo(inputView.readYesNo())){
                userRequest.updateQuantity(false, regularQuantity);
            }
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMsg(e.getMessage());
        }
    }

    public boolean promptMembership(){
        outputView.printAskMembership();
        try{
            return processYesNo(inputView.readYesNo());
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMsg(e.getMessage());
            return false;
        }
    }

    public boolean promptAskContinue(){
        outputView.printAskContinue();
        try{
            return processYesNo(inputView.readYesNo());
        }catch (IllegalArgumentException e){
            outputView.printExceptionMsg(e.getMessage());
            return false;
        }
    }

    private boolean processYesNo(String answer){
        if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) return true;
        if(answer.equalsIgnoreCase("N")||answer.equalsIgnoreCase("NO")) return false;

        throw new IllegalArgumentException(ExceptionMessage.INVALID_ETC.fullErrorMessage());
    }

}
