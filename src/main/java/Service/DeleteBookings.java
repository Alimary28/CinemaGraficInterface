package Service;

import Domain.Entity;
import Repository.IRepository;

import java.util.List;

public class DeleteBookings<T extends Entity> extends UndoableOperation {
    private List<T> deletedElements;

    public DeleteBookings(IRepository repository, List<T> deletedElements) {
        super(repository);
        this.deletedElements = deletedElements;
    }

    public void doUndo(){
        for (T element : deletedElements){
            repository.insert(element);
        }
    }
    public void doRedo(){
        for (T element : deletedElements){
            repository.remove(element.getId());
        }
    }
}
