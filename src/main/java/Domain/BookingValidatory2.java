package Domain;

import java.util.Calendar;

public class BookingValidatory2 implements IValidator<Booking2>{

    public void validate2(Booking2 booking2){

        if(booking2.getDate().getYear() > Calendar.getInstance().get(Calendar.YEAR)){
            throw new BookingException("Invalid date!");
        }
        String reverse = "";
        int length = booking2.getId().length();

        for(int i = length-1; i >= 0; i--){
            reverse = reverse + booking2.getId().charAt(i);
        }
        if(!booking2.getId().equals(reverse)){
            throw new BookingException("Not palindrome!");
        }
    }
}
