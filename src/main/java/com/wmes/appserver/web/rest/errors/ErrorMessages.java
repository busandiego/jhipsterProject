package com.wmes.appserver.web.rest.errors;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

public class ErrorMessages {


    // 이렇게 하면 설정되나?
    public static ErrorKey ERROR_KEY;

    public static final String MESSAGES_INVALID_LOGIN_ID = "아이디가 올바르지 않습니다.";

    public static final String MESSAGE_INVALID_PASSWORD = "유효하지않은 비밀번호 입니다.";

    public static BadRequestAlertException invalidLoginId() {
        return new BadRequestAlertException(MESSAGES_INVALID_LOGIN_ID, ENTITY_NAME, ERROR_KEY.INVALID_LOGIN_ID);
    }
}
