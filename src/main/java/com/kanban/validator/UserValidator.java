package com.kanban.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.kanban.pojo.User;

@Component
public class UserValidator {

   public boolean validate(User obj) {

	  int error = 0;		
	  Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE); 
	  if(!(pattern.matcher(obj.getEmailId()).matches())) {
		  error++;
	  }
	  if(obj.getUserName().isEmpty() || obj.getUserName() == null) {
		  error++;
	  }
	  if(obj.getPassword().isEmpty() || obj.getPassword() == null) {
		  error++;
	  }
	  if(obj.getRole().isEmpty() || obj.getRole() == null) {
		  error++;
	  }
      if(error > 0) {
    	  return false;
      }
      
      return true;
   }
}
