package Repository;

import Domain.Entity;
import Domain.IValidator;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSonRepository<T extends Entity> implements IRepository<T>{

    private Map<String, T> schedule = new HashMap<>();
    private IValidator<T> validator;
    private String fileString;
    private Type fileType;

    public JSonRepository(IValidator<T> validator, String fileString, Type fileType){
        this.validator = validator;
        this.fileString = fileString;
        this.fileType = fileType;
    }

    /**
     * method that load content from a file
     */
    private void loadingFile() {
        schedule.clear();
        Gson gson = new Gson();
        try (FileReader in = new FileReader(fileString)) {
            try (BufferedReader bufferedReader = new BufferedReader(in)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    T entity = gson.fromJson(line, fileType);
                    schedule.put(entity.getId(), entity);
                }
            }
        } catch (Exception ex) {
            System.out.println("Load from file error: " + ex.getMessage());
        }
    }

    /**
     * method that write something into a file
     */
    private void writingFile() {
        Gson gson = new Gson();
        try (FileWriter out = new FileWriter(fileString)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {
                for (T entity : schedule.values()) {
                    bufferedWriter.write(gson.toJson(entity));
                    bufferedWriter.newLine();
                }
            }
        } catch (Exception ex) {
            System.out.println("Write to file error: " + ex.getMessage());
        }
    }

    public T findById(String id) {
        loadingFile();
        return schedule.get(id);
    }

    /**
     * Adds an entity .
     * @param entity the entity to add
     */
    public void insert(T entity){
        loadingFile();
        if(schedule.containsKey(entity.getId())){
            throw new InMemoryRepoException("This Id already exists!");
        }
        validator.validate2(entity);
        schedule.put(entity.getId(), entity);
        writingFile();
    }

    /**
     * updates an entity if it already exists
     * @param entity the entity to update
     */
    public void update(T entity){
        loadingFile();
        if(!schedule.containsKey(entity.getId())){
            throw new InMemoryRepoException("This Id does not exists!");
        }
        validator.validate2(entity);
        schedule.put(entity.getId(), entity);
        writingFile();
    }
    /**
     * Removes an entity with a given id.
     * @param id the id.
     * @throws InMemoryRepoException if there is no entity with the given id.
     */
    public void remove(String id) {
        loadingFile();
        if (!schedule.containsKey(id)) {

            throw new InMemoryRepoException("There is no entity with the given id to remove.");
        }
        schedule.remove(id);
        writingFile();
    }

    public List<T> getAll() {
        loadingFile();
        return new ArrayList<>(schedule.values());
    }

}
