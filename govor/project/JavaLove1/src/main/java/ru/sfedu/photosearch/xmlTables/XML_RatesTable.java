package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.Models.Photo;
import ru.sfedu.photosearch.Models.Rate;

import java.util.List;

/**
 * таблица для конвертации оценок из XML
 * @see Rate
 */
@Root (name="rates_table")
public class XML_RatesTable {
    @ElementList
    private List<Rate> rates;

    public List<Rate> getxmlRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        for (Rate rate: rates){
            rate.setId(rate.getId().toString());
        }
        this.rates = rates;
    }
}
