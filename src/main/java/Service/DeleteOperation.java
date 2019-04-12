package Service;

import Domain.Entity;
import Repository.IRepository;

public class DeleteOperation<T extends Entity> extends UndoableOperation {
    private T entity;

    public DeleteOperation(IRepository repository, T entity) {
        super(repository);
        this.entity = entity;
    }

    public void doUndo(){
        repository.insert(entity);
    }
    public void doRedo(){
        repository.remove(entity.getId());
    }
}
