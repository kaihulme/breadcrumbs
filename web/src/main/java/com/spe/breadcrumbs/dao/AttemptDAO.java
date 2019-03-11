package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.User;

import java.util.List;

public interface AttemptDAO {
    boolean addAttempt(User u, Choice c);
    List<Choice> getAttempts(Long questionId, Long userId);
}
