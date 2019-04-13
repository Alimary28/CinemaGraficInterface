package Domain;

import java.util.Calendar;

public class ClientCardValidatory2 implements IValidator<ClientCard2>{

    public void validate2(ClientCard2 clientCard2) {

        if(clientCard2.getBirthDate().getYear() < 1900 || clientCard2.getBirthDate().getYear() > Calendar.getInstance().get(Calendar.YEAR)){
            throw new ClientCardException("Invalid date of birth!");
        }
        if(clientCard2.getRegistrationDate().getYear() < 1900 || clientCard2.getRegistrationDate().getYear() > Calendar.getInstance().get(Calendar.YEAR)){
            throw new ClientCardException("Invalid date of registration!");
        }

        if((clientCard2.getCnp()).length() < 9 || (clientCard2.getCnp()).length() >9){
            throw new ClientCardException("Invalid CNP!");
        }
        if(clientCard2.getPoints() < 0){
            throw new ClientCardException("The value of points must be greater than 0!");
        }
        String reverse = "";
        int length = clientCard2.getId().length();

        for(int i = length-1; i >= 0; i--){
            reverse = reverse + clientCard2.getId().charAt(i);
        }
        if(!clientCard2.getId().equals(reverse)){
            throw new ClientCardException("Not palindrome!");
        }
    }

}
