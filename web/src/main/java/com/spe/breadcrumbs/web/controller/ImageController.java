package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.MapDAO;
import com.spe.breadcrumbs.dao.MapDbDAO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Blob;
import java.sql.SQLException;

@CrossOrigin
@Controller
@RequestMapping("/image")
public class ImageController {

    private MapDAO mapDAO = new MapDbDAO();

    @RequestMapping(value = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> postImage(@PathVariable("id") Long id) throws SQLException {
        Blob blob = mapDAO.getMap(id).getPicture();
        byte[] picture = blob.getBytes(1, (int)blob.length());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(picture, headers, HttpStatus.OK);
    }

}
