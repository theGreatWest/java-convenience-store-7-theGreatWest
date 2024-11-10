package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.config.DatabaseInitializer;
import store.core.constant.Constants;
import store.core.constant.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationServiceTest {
    private ApplicationService appService = new ApplicationService(new DatabaseService(new DatabaseInitializer()), new ValidationService());




}
