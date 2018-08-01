package tv.huan.master.common.annotation;

import tv.huan.master.common.annotation.impl.FixLengthImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/28
 * Time: 15:01
 * To change this template use File | Settings | File Templates
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FixLengthImpl.class)
public @interface FixLength {

    int length();
    String message() default "{fixLength.not.valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}