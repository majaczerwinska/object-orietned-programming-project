package server.database;

import commons.Palette;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class PaletteRepositoryTest implements PaletteRepository{
    Set<Palette> palettes = new HashSet<>();

    @Override
    public List<Palette> findAll() {
        return null;
    }

    @Override
    public List<Palette> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Palette> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Palette> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Palette entity) {
        palettes.remove(entity);
    }

//    @Override
//    public void delete(PaletteRepository entity) {
//        palettes.remove(entity);
//    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Palette> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Palette> S save(S entity) {
        palettes.add(entity);
        return entity;
    }

    @Override
    public <S extends Palette> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Palette> findById(Integer integer) {
        if(!existsById(integer)) return Optional.empty();
        for(Palette palette : palettes){
            if(palette.getId()==integer) return Optional.of(palette);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        for(Palette palette : palettes){
            if(palette.getId()==integer) return true;
        }
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Palette> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Palette> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Palette> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Palette getOne(Integer integer) {
        return null;
    }

    @Override
    public Palette getById(Integer integer) {
        if(!existsById(integer)) return null;
        for(Palette palette : palettes){
            if(palette.getId()==integer) return palette;
        }
        return null;
    }

    @Override
    public <S extends Palette> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Palette> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Palette> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Palette> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Palette> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Palette> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Palette, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
