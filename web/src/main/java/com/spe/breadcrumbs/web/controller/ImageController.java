package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.MapDAO;
import com.spe.breadcrumbs.dao.MapDbDAO;
import com.spe.breadcrumbs.entity.Map;
import com.spe.breadcrumbs.web.DBConnection;
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

    private MapDAO mapDAO = new MapDbDAO(new DBConnection());

    @RequestMapping(value = "/{image}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> postImage(@PathVariable("image") String image) throws SQLException {

        Map map = mapDAO.getMapByName(image);
        if (map != null) {
            Blob blob = map.getPicture();
            byte[] picture = blob.getBytes(1, (int)blob.length());
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(picture, headers, HttpStatus.OK);
        }

        try {
            Long id = (long) 0;
            id = id.parseLong(image);
            map = mapDAO.getMap(id);
            if (map != null) {
                Blob blob = map.getPicture();
                byte[] picture = blob.getBytes(1, (int) blob.length());
                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity<byte[]>(picture, headers, HttpStatus.OK);
            }
        } finally {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

    }

}
