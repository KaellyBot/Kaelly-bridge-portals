package com.github.kaellybot.portals.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MultilingualValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Multilingual {
    String message() default "A label is mandatory for these languages: FR, EN, ES";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
