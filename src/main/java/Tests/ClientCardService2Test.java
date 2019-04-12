package Tests;

import Domain.ClientCard2;
import Domain.ClientCardValidatory2;
import Domain.IValidator;
import Repository.IRepository;
import Repository.JSonRepository;
import Service.ClientCardService2;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardService2Test {

    private IValidator<ClientCard2> cardValidator = new ClientCardValidatory2();
    private IRepository<ClientCard2> cardRepo = new JSonRepository<>(cardValidator);
    private ClientCardService2 cardService = new ClientCardService2(cardRepo);

    @Test
    void insert() {

        cardService.insert("1", "ana", "bumb", "123456789", LocalDate.of(1992,10,22),
                LocalDate.of(2010,12,13), 15);
        assertEquals(1,cardService.getAll().size());
        try {
            cardService.insert("1", "ana", "bumb", "123456789", LocalDate.of(1992,10,22),
                    LocalDate.of(2010,12,13), 15);
            fail("Exception not thrown for duplicate card id!");
        } catch(RuntimeException rex) {
            assertTrue(true);
        }
    }

    @Test
    void update() {

        cardService.insert("1", "ana", "vlaicu", "123456789", LocalDate.of(1992,10,22),
                LocalDate.of(2010,12,13), 15);
        cardService.update("1", "ana", "bumb", "123456789", LocalDate.of(1992,10,22),
                LocalDate.of(2010,12,13), 15);
        assertEquals("vlaicu",cardService.getAll().get(0).getFirstname());
    }

    @Test
    void fullTextSearch() {

        cardService.insert("1", "ana", "bumb", "123456789", LocalDate.of(1992,10,22),
                LocalDate.of(2010,12,13), 15);
        assertEquals(1, cardService.fullTextSearch("bu").size());
        assertEquals(1, cardService.fullTextSearch("an").size());
    }

    @Test
    void remove() {

        cardService.insert("1", "ana", "bumb", "123456789", LocalDate.of(1992,10,22),
                LocalDate.of(2010,12,13), 15);
        cardService.remove("1");
        assertEquals(0,cardService.getAll().size());
    }
}