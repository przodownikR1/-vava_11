package pl.java.scalatech.web;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public final class GlobalControllerAdvice {

    

    @ModelAttribute("localTime")
    public LocalDateTime setUpServerTime() {
        return LocalDateTime.now();
    }

    @ModelAttribute("serverLocale")
    public Locale setUpLocale() {
        return Locale.getDefault();
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}