package pl.java.scalatech.converters;

import static java.lang.Long.parseLong;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class AmountFormatter implements Formatter<BigDecimal>{

    @Override
    public String print(BigDecimal object, Locale locale) {
        String ret = object.toString() + " USD";
        if(locale.getLanguage().equals(new Locale("pl").getLanguage())) {
        ret = object.toString() + " PLN";
        }
        log.info("+++ object: {} , locale {} : ret {} ",object, locale,ret);
        return ret;
    }

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        String str[] = text.split(" ");
        BigDecimal ret = BigDecimal.valueOf(parseLong(str[0]));
        log.info("parse : {}",ret);
        return ret;
    }

}
