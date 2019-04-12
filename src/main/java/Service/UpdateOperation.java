package Service;

import Domain.Entity;
import Repository.IRepository;

public class UpdateOperation<T extends Entity> extends UndoableOperation {

    private T entity;
    private T updateEntity;

    public UpdateOperation(IRepository repository, T entity, T updateEntity) {
        super(repository);
        this.entity = entity;
        this.updateEntity = updateEntity;
    }
    public void doUndo(){
        repository.update(entity);
    }
    public void doRedo(){
        repository.update(updateEntity);
    }
}
