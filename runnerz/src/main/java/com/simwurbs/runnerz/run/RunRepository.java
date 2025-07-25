/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.simwurbs.runnerz.run;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

/**
 *
 * @author sumse
 */
public interface RunRepository extends ListCrudRepository<Run,Integer> {

    @Query
    List<Run> findAllByLocation(String Location);
}
