package server.database;

import commons.CardList;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class TaskListRepositoryTest implements TaskListRepository {

    Set<CardList> tasks = new HashSet<>();

    /**
     * @return
     */
    @Override
    public List<CardList> findAll() {
        return null;
    }

    /**
     * @param sort
     * @return
     */
    @Override
    public List<CardList> findAll(Sort sort) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Override
    public Page<CardList> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param integers must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public List<CardList> findAllById(Iterable<Integer> integers) {
        return null;
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param integer must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Override
    public void deleteById(Integer integer) {

    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(CardList entity) {
        tasks.remove(entity);
    }

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     *
     * @param integers must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    @Override
    public void deleteAll(Iterable<? extends CardList> entities) {

    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {

    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public <S extends CardList> S save(S entity) {
        tasks.add(entity);
        return entity;
    }

    /**
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends CardList> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param integer must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<CardList> findById(Integer integer) {
        return Optional.empty();
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param integer must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public boolean existsById(Integer integer) {
        for(CardList task : tasks){
            if(task.getId()==integer) return true;
        }
        return false;
    }

    /**
     * Flushes all pending changes to the database.
     */
    @Override
    public void flush() {

    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity entity to be saved. Must not be {@literal null}.
     * @return the saved entity
     */
    @Override
    public <S extends CardList> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * Saves all entities and flushes changes instantly.
     *
     * @param entities entities to be saved. Must not be {@literal null}.
     * @return the saved entities
     * @since 2.5
     */
    @Override
    public <S extends CardList> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * Deletes the given entities in a batch which means it will create a single query. This kind of operation leaves JPAs
     * first level cache and the database out of sync. Consider flushing the {@link EntityManager} before calling this
     * method.
     *
     * @param entities entities to be deleted. Must not be {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllInBatch(Iterable<CardList> entities) {

    }

    /**
     * Deletes the entities identified by the given ids using a single query. This kind of operation leaves JPAs first
     * level cache and the database out of sync. Consider flushing the {@link EntityManager} before calling this method.
     *
     * @param integers the ids of the entities to be deleted. Must not be {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    /**
     * Deletes all entities in a batch call.
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param integer must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
     * @deprecated use {@link JpaRepository#getById(ID)} instead.
     */
    @Override
    public CardList getOne(Integer integer) {
        return null;
    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param integer must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
     * @since 2.5
     */
    @Override
    public CardList getById(Integer integer) {
        if(!existsById(integer)) return null;
        for(CardList task : tasks){
            if(task.getId()==integer) return task;
        }
        return null;
    }

    /**
     * Returns a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
     *
     * @param example must not be {@literal null}.
     * @return a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
     * @throws IncorrectResultSizeDataAccessException if the Example yields more than one result.
     */
    @Override
    public <S extends CardList> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends CardList> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends CardList> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities matching the given {@link Example}. In case no match could be found, an empty
     * {@link Page} is returned.
     *
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return a {@link Page} of entities matching the given {@link Example}.
     */
    @Override
    public <S extends CardList> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * Returns the number of instances matching the given {@link Example}.
     *
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @return the number of instances matching the {@link Example}.
     */
    @Override
    public <S extends CardList> long count(Example<S> example) {
        return 0;
    }

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     */
    @Override
    public <S extends CardList> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * Returns entities matching the given {@link Example} applying the {@link Function queryFunction} that defines the
     * query and its result type.
     *
     * @param example       must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return all entities matching the given {@link Example}.
     * @since 2.6
     */
    @Override
    public <S extends CardList, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
