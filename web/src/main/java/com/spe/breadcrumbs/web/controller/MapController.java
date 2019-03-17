//package com.spe.breadcrumbs.web.controller;
//
//import com.spe.breadcrumbs.dao.MapDAO;
//import com.spe.breadcrumbs.dao.MapDbDAO;
//import com.spe.breadcrumbs.entity.Map;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.view.RedirectView;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.sql.Blob;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@CrossOrigin
//@Controller
//@RequestMapping("/map")
//public class MapController {
//
//    private MapDAO mapDAO = new MapDbDAO();
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String getMap(Model m) {
//        List<Map> maps = mapDAO.getAllMaps();
//        m.addAttribute("maps", maps);
//        return "views/map";
//    }
//
//    @GetMapping("/add")
//    public String addMapPage(Model m) {
//        m.addAttribute("map", new Map());
//        return "views/map_addMap";
//    }
//
//    public BufferedImage multipartToImage(MultipartFile file) {
//        try {
//            File f = new File(file.getOriginalFilename());
//            f.createNewFile();
//            FileOutputStream fos = new FileOutputStream(f);
//            fos.write(file.getBytes());
//            fos.close();
//            return ImageIO.read(f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Blob imageToBlob(BufferedImage in) {
//        try {
//            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = newImage.createGraphics();
//            g.drawImage(in, 0, 0, null);
//            g.dispose();
//            // IMAGE -> BYTES
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(newImage, "png", baos);
//            byte[] bytes = baos.toByteArray();
//            return new javax.sql.rowset.serial.SerialBlob(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // take in file -> convert to image -> convert to blob + add name -> add new Map
//    @PostMapping("/add")
//    public RedirectView addMap(@RequestParam("f") MultipartFile f) {
//        try {
//            BufferedImage bi = multipartToImage(f);
//            Blob picture = imageToBlob(bi);
//            // CREATE AND ADD MAP
//            Map map = new Map((long) 1, "venueMap", picture);
//            mapDAO.addMap(map);
//            return new RedirectView("http://localhost:8080/map");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new RedirectView("http://localhost:8080/map");
//    }
//
//}
