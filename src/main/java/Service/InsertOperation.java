package Service;

import Domain.Entity;
import Repository.IRepository;

public class InsertOperation<T extends Entity> extends UndoableOperation {

    private T newEntity;

    public InsertOperation(IRepository<T> repository, T newEntity) {
        super(repository);
        this.newEntity = newEntity;
    }
   public void doUndo(){
        repository.remove(newEntity.getId());
   }

   public void doRedo(){
        repository.insert(newEntity);
   }

}

