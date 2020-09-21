package com.github.kaellybot.portals.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.github.kaellybot.portals.controller.PortalConstants.LABEL_NOT_FOUND_MESSAGE;

@Documented
@Constraint(validatedBy = MultilingualValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Multilingual {
    String message() default LABEL_NOT_FOUND_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
