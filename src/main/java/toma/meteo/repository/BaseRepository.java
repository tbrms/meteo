package toma.meteo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
interface BaseRepository<T> extends PagingAndSortingRepository<T, Long> {

    //long customMethod();
}
