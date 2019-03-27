package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Attempt;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.User;

import java.util.List;

public interface AttemptDAO {
    boolean addAttempt(Attempt a);
    List<Choice> getAttempts(Long questionId, Long userId);
}
