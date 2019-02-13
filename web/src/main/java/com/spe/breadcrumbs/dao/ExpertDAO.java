package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.User;

import java.util.List;

public interface ExpertDAO {


    public Expert getExpert(Long id);

    List<Expert> getAllExperts();

    List<Expert> getExpertsWithQuizzes();

    boolean addExpert(Expert e);

    boolean update(Long id, Expert e);

    boolean deleteExpert(Long id);

    public boolean validate(String email,String password);

}
