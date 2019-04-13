package Tests;

import Domain.ClientCard2;
import Domain.ClientCardValidatory2;
import Domain.IValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientCardValidatory2Test {

    IValidator<ClientCard2> cardValidator = new ClientCardValidatory2();

    @Test
    void validate2() {

        ClientCard2 card = new ClientCard2("1","ioana","ratiu","987456321", LocalDate.of(2312,11,12),
                LocalDate.of(2312,11,12),-4);


        try {
            cardValidator.validate2(card);
        } catch (RuntimeException rex) {
            assertTrue(true);
        }
    }
}