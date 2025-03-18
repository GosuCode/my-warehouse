package com.alember.my_warehouse.validator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cache.CacheException;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;

import ch.qos.logback.core.util.StringUtil;

public final class InputRequestValidator {
	
	
	public static void validateCategoryRequest(CategoryModel category) throws CategoryException {
		
		if(category.getName() == null || category.getName() == "") {
			throw new CategoryException("Name is Empty or Null");
		}
		
		if(category.getDescription() == null || category.getDescription() == "") {
			throw new CategoryException("Description is Empty or Null");
		}
		
		
	}
	
	
	
	

}
