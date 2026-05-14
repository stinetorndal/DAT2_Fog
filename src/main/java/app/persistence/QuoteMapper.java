package app.persistence;
import app.entities.Quote;

public class QuoteMapper {

    public void saveQuote(Quote quote){
        //Skal bruge SQL statements her for at gemme i DB

        System.out.println("Quote saved:");
        System.out.println("price: " + quote.getPrice());


    }

    public Quote findQuoteById(int quoteId){

        //her er bare lidt dummy data til test
    return new Quote(quoteId, 1,1,45000,app.enums.QuoteStatus.PENDING,1,null);

    }
}
