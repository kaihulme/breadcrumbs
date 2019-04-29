package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Hint;

import java.sql.Blob;

public interface HintDAO {
    Hint getHintByName(String name);

    Hint getHintByCode(String code);

    boolean addHint(Hint h, Long question_id);

    boolean deleteHint(Long hint_id);

    boolean updateHint(Hint h, Long id);

    boolean updateHintLocation(Hint h, Long id);

    boolean updateHintImage(String pictureName, Blob picture, Long id);

    boolean removeHintImage(Long hint_id);
}
