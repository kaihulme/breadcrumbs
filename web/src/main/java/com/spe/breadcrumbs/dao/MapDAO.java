package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Map;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;

import java.util.List;

public interface MapDAO {

    Map getMap(Long id);

    boolean addMap(Map m);

    boolean updateMap(Long id, Map m);

    boolean deleteMap(Long id);

}
