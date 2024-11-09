package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.core.constant.ExceptionMessage;

public class InputView {
    public String readProducts() {
        String requestProductQuantity;
        try {
            requestProductQuantity = Console.readLine().strip();

            return requestProductQuantity;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.OTHER_INPUT_EXCEPTION.errorNotification());
        }
    }

    public String readYesNo() {
        try {
            String answer = Console.readLine().strip();

            return answer;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.OTHER_INPUT_EXCEPTION.errorNotification());
        }
    }
}
