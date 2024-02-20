package com.launchcode.violetSwap.models;

import org.springframework.stereotype.Component;
        import org.springframework.validation.Errors;
        import org.springframework.validation.Validator;
        import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> assignclass) {
        return MultipartFile.class.isAssignableFrom(assignclass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile file = (MultipartFile) target;

        // Check if the file is not empty and contains data (optional field)
        if (file != null && file.isEmpty()) {
            // The file is not mandatory, so no error is added for an empty file
        }
    }
}

