package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.MapDAO;
import com.spe.breadcrumbs.dao.MapDbDAO;
import com.spe.breadcrumbs.entity.Map;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/maps")
public class MapAPI {

    private MapDAO mapDAO = new MapDbDAO(new DBConnection());

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public ResponseEntity getMap(@PathVariable Long id) {
        Map match;
        match = mapDAO.getMap(id);
        if (match != null) {
            return new ResponseEntity<>(match, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMap(@RequestBody Map m) {
        if (mapDAO.addMap(m)) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public ResponseEntity updateMap(@PathVariable Long id, @RequestBody Map m) {
        if (mapDAO.updateMap(id, m)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity deleteMap(@PathVariable Long id) {
        if (mapDAO.deleteMap(id)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
