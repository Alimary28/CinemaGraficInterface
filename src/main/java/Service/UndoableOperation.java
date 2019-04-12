package Service;

import Domain.Entity;
import Repository.IRepository;

public abstract class UndoableOperation<T extends Entity> {

    protected IRepository<T> repository;

    public UndoableOperation(IRepository<T> repository) {
        this.repository = repository;
    }

    public abstract void doUndo();
    public abstract void doRedo();
}
