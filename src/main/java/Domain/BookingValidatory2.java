package Domain;

import java.util.Calendar;

public class BookingValidatory2 implements IValidator<Booking2>{

    public void validate2(Booking2 booking2){
        if(booking2.getMovieId() == null || booking2.getDate() == null){
            throw new BookingException("Movie can not exists!");
        }
        if(booking2.getDate().getYear() > Calendar.getInstance().get(Calendar.YEAR)){
            throw new BookingException("Invalid date!");
        }
    }
}
