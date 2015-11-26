package pl.java.scalatech.converters;

import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.AmountFormat;
@Component
@Slf4j
public class AmountFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<AmountFormat>{

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = Sets.newHashSet();
        fieldTypes.add(Long.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(AmountFormat annotation, Class<?> fieldType) {
        log.info("++++  getPrinter(AmountFormat annotation, Class<?> fieldType)");
        return new AmountFormatter();
    }

    @Override
    public Parser<?> getParser(AmountFormat annotation, Class<?> fieldType) {
        log.info("++++  getParser(AmountFormat annotation, Class<?> fieldType)");
        return new AmountFormatter();
    }

}
