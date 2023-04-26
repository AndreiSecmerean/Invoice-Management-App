package ro.itschool.InvoiceManagementApp.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoDigitsValidator implements ConstraintValidator<NoDigits, String>{

    @Override
    public void initialize(NoDigits constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        for(char c:value.toCharArray()){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
