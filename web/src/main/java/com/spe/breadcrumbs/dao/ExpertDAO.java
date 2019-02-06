package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Expert;

public interface ExpertDAO {
    public Expert getExpert(Long id);
    public boolean validate(String email,String password);
}
