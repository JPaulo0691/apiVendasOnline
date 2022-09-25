package com.github.myproject.vendas.customValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.myproject.vendas.customAnotations.NotEmptyList;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		
		return list != null && !list.isEmpty();
	}

	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
		constraintAnnotation.message();
	}
	
	
}
