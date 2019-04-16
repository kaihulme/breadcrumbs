package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.ExpertDAO;
import com.spe.breadcrumbs.dao.ExpertDbDAO;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/experts")

public class ExpertAPI {

    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());

//    @RequestMapping(method = RequestMethod.GET,value = "{id}")
//    public ResponseEntity getExpert(@PathVariable Long id){
//        Expert e = expertDAO.getExpert(id);
//        if(e != null){
//            return new ResponseEntity<>(e, HttpStatus.OK);
//        }
//        return new ResponseEntity(null,HttpStatus.NOT_FOUND);
//    }
}
