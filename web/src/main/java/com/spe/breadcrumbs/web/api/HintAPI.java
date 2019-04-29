package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.HintDAO;
import com.spe.breadcrumbs.dao.HintDbDAO;
import com.spe.breadcrumbs.entity.Hint;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/hints")
public class HintAPI {
    private DBConnection dbConnection = new DBConnection();
    private HintDAO hintDAO = new HintDbDAO(dbConnection);
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getHint(@RequestParam(value = "code") String code){
        Hint h = hintDAO.getHintByCode(code);
        if( h != null){
            return new ResponseEntity<>(h, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public ResponseEntity getHintById(@PathVariable Long id){
        Hint h = hintDAO.getHintById(id);
        if( h != null){
            return new ResponseEntity<>(h, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }    }
}
