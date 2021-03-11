package ru.sfedu.photosearch.xmlTables;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.photosearch.newModels.Event;
import ru.sfedu.photosearch.newModels.Feedback;
import ru.sfedu.photosearch.newModels.Offer;

import java.util.List;


@Root (name="offers_table")
public class XML_OffersTable {
    @ElementList
    private List<Offer> offers;

    public List<Offer> getxmlOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        for (Offer offer:offers){
            offer.setId(offer.getId().toString());
        }
        this.offers = offers;
    }
}
