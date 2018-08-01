package tv.huan.master.common.annotation.impl;

import tv.huan.master.common.annotation.FixLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/28
 * Time: 15:01
 * To change this template use File | Settings | File Templates
 */
public class FixLengthImpl implements ConstraintValidator<FixLength, String> {
    private int length;
    @Override
    public boolean isValid(String validStr,
                           ConstraintValidatorContext constraintContext) {
        return validStr != null && validStr.length() == length;
    }

    @Override
    public void initialize(FixLength fixLen) {
        this.length = fixLen.length();
    }
}