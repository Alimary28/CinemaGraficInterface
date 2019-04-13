package Service;

import Domain.ClientCard2;
import Domain.Movie2;
import Repository.IRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class ClientCardService2 {

    private IRepository<ClientCard2> cardRepo;

    private Stack<UndoableOperation<Movie2>> undoableOperations = new Stack<>();
    private Stack<UndoableOperation<Movie2>> redoableOperations = new Stack<>();

    public ClientCardService2(IRepository<ClientCard2> cardRepo){
        this.cardRepo = cardRepo;
    }

    /**
     *
     * @param id add a client card with the specified id
     * @param name add a client card with the specified name
     * @param firstName add a client card with the specified firstname
     * @param cnp add a client card with the specified cnp
     * @param birthDate add a client card with the specified date of birth
     * @param registrationDate add a client card with the specified date of registration
     * @param points add a client card with the specified points
     */
    public void insert(String id, String name, String firstName, String cnp, LocalDate birthDate, LocalDate registrationDate, int points) {

        ClientCard2 card2 = new ClientCard2(id, name, firstName, cnp, birthDate, registrationDate, points);
        List<ClientCard2> clients = new ArrayList<>(cardRepo.getAll());

        for (ClientCard2 cnpTest : clients) {
            if(cnp.equals(cnpTest.getCnp())){
                throw new ClientServiceException("This CNP already exists!");
            }
        }

        cardRepo.insert(card2);
        undoableOperations.add(new InsertOperation<>(cardRepo, card2));
        redoableOperations.clear();
    }

    /**
     *
     * @param id update a client card with the specified id
     * @param name update a client card with the specified name
     * @param firstName update a client card with the specified firstname
     * @param cnp update a client card with the specified cnp
     * @param birthDate update a client card with the specified date of birth
     * @param registrationDate update a client card with the specified date of registration
     * @param points update a client card with the specified points
     */
    public void update(String id, String name, String firstName, String cnp, LocalDate birthDate, LocalDate registrationDate, int points){
        ClientCard2 clientCard1 = cardRepo.findById(id);
        ClientCard2 clientCard2 = new ClientCard2(id, name, firstName, cnp, birthDate, registrationDate, points);
        List<ClientCard2> clients = new ArrayList<>(cardRepo.getAll());
        for(ClientCard2 cnpTest : clients){
            if(cnp.equals(cnpTest.getCnp()) && !cnp.equals(clientCard2.getCnp())){
                throw new ClientServiceException("Cnp already exists!");
            }
        }
        cardRepo.update(clientCard2);
        undoableOperations.add(new UpdateOperation(cardRepo, clientCard2, clientCard1));
        redoableOperations.clear();
    }


    /**
     * Searches clients whose fields contain a given text.
     * @param text the text searched for
     * @return A list of clients whose fields contain text.
     */
    public List<ClientCard2> fullTextSearch(String text) {
        List<ClientCard2> found = new ArrayList<>();
        for (ClientCard2 client : cardRepo.getAll()) {
            // Might return false positives
            if (client.getId().contains(text) || client.getName().contains(text) || client.getFirstName().contains(text) ||
                client.getCnp().contains(text) || client.getBirthDate().toString().contains(text) ||
                client.getRegistrationDate().toString().contains(text) || Integer.toString(client.getPoints()).contains(text) && !found.contains(client)) {

                found.add(client);
            }
        }

        return found;
    }

    /**
     *
     * @param id remove a client card with the specified id
     */
    public void remove(String id) {
        ClientCard2 clientCard = cardRepo.findById(id);
        cardRepo.remove(id);
        undoableOperations.add(new DeleteOperation(cardRepo, clientCard));
        redoableOperations.clear();
    }

    /**
     *
     * @return a list with all client cards
     */
    public List<ClientCard2> getAll() {
        return cardRepo.getAll();
    }

    public IRepository<ClientCard2> getCardRepo() {
        return cardRepo;
    }

    /**
     *
     * @param start is the start birthDate
     * @param end is the end birthDate
     * @param points are the incremented points by birthday date
     */
    public void incrementPoints(LocalDate start, LocalDate end, int points){
        List<ClientCard2> newClientCard = new ArrayList<>();
        List<ClientCard2> clientCard = new ArrayList<>();

        for (ClientCard2 clients : cardRepo.getAll()){
            if(clients.getBirthDate().getDayOfYear() > start.getDayOfYear() && clients.getBirthDate().getDayOfYear() < end.getDayOfYear()){

                ClientCard2 updateCard = new ClientCard2(clients.getId(), clients.getName(), clients.getFirstName(), clients.getCnp(), clients.getBirthDate(),
                        clients.getRegistrationDate(), clients.getPoints());
                clientCard.add(updateCard);
                clients.setPoints(clients.getPoints() + points);
                cardRepo.update(clients);
            }
        }
        newClientCard.addAll(cardRepo.getAll());
        undoableOperations.add(new UpdateCards<ClientCard2>(cardRepo, newClientCard, clientCard));
        redoableOperations.clear();
    }

    public List<ClientCard2> decreasingOrderedCards(){
        List<ClientCard2> orderedCard = cardRepo.getAll();
        orderedCard.sort(Comparator.comparing(ClientCard2::getPoints).reversed());

        return orderedCard;
    }

    public void undoAble(){
        if(!undoableOperations.empty()) {
            UndoableOperation<Movie2> endOperation = undoableOperations.pop();
            endOperation.doUndo();
            redoableOperations.add(endOperation);
        }
    }
    public void redoAble(){
        if(!redoableOperations.empty()){
            UndoableOperation<Movie2> endOperation = redoableOperations.pop();
            endOperation.doRedo();
            undoableOperations.add(endOperation);
        }
    }
}
