package Service;

import Domain.Entity;
import Repository.IRepository;

import java.util.List;

public class UpdateCards<T extends Entity> extends UndoableOperation {

    private List<T> firstList;
    private List<T> secondList;

    public UpdateCards(IRepository repository, List<T> firstList, List<T> secondList) {
        super(repository);
        this.firstList = firstList;
        this.secondList = secondList;
    }

    public void doUndo(){
        for (T newEntity : firstList){
            repository.update(newEntity);
        }
    }
    public void doRedo(){
        for (T newEntity : secondList){
            repository.update(newEntity);
        }
    }
}
