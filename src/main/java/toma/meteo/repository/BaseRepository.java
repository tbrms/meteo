package toma.meteo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface BaseRepository<T> extends CrudRepository<T, Long> {

    //long customMethod();
}
