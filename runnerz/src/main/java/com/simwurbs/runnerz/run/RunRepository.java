
package com.simwurbs.runnerz.run;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;


public interface RunRepository extends ListCrudRepository<Run,Integer> {

    @Query
    List<Run> findAllByLocation(String Location);
}
