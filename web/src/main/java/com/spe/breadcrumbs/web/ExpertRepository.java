package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.entity.Expert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

//@org.springframework.stereotype.Repository
public interface ExpertRepository extends Repository<Expert,Long> {

    //@Override
    //long count();
    Expert findByEmailAndPassword(String email, String password);


}
