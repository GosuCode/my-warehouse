package com.alember.my_warehouse.validator;

import com.alember.my_warehouse.exception.CategoryException;
import com.alember.my_warehouse.model.CategoryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
public class InputRequestValidatorTest {




    @Test
    public void validateCategoryRequestMethodTest() throws Exception{


        CategoryModel categoryModel = new CategoryModel();
        String exMsg = "";
        try {
            InputRequestValidator.validateCategoryRequest(categoryModel);
        }catch (CategoryException catEx){
            exMsg = catEx.getMessage();
        }
        Assertions.assertEquals("Name is Empty or Null", exMsg);
    }


    @Test
    public void validateCategoryRequestMethodTest02() throws Exception{


        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("Test Name");
        String exMsg = "";
        try {
            InputRequestValidator.validateCategoryRequest(categoryModel);
        }catch (CategoryException catEx){
            exMsg = catEx.getMessage();
        }

        Assertions.assertEquals("Description is Empty or Null", exMsg);

    }


    @Test
    public void validateCategoryRequestMethodTest03() throws Exception{


        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("Test Name");
        categoryModel.setDescription("Test Description");
        String exMsg = "";
        boolean exceptionOccur = false;
        try {

            InputRequestValidator.validateCategoryRequest(categoryModel);

        }catch (CategoryException catEx){
            exMsg = catEx.getMessage();
            exceptionOccur = true;
        }

        Assertions.assertEquals(false, exceptionOccur);

    }
}
