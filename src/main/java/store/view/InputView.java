package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.core.constant.ExceptionMessage;

public class InputView {
    public String readProducts(){
        try{
            String userProducts = Console.readLine().strip();

            return userProducts;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ETC.fullErrorMessage());
        }
    }

    public String readYesNo() {
        try {
            String answer = Console.readLine().strip();

            return answer;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ETC.fullErrorMessage());
        }
    }
}
