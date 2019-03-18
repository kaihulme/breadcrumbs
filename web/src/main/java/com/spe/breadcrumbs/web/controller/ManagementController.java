package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/management")
public class ManagementController {

    private UserDAO userDAO = new UserDbDAO();
    private ExpertDAO expertDAO = new ExpertDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

    @Value(value = "classpath:static/mapFeatures/questionIcon.png")
    private Resource questionIcon;

//    @RequestMapping(method = RequestMethod.GET)
//    public String tableContent(Model m){
//        List<Expert> experts = expertDAO.getExpertsWithQuizzes();
//        List<User> users = new ArrayList<>();
//        List<Question> questions = new ArrayList<>();
//        for(Expert e: experts){
//            for(Quiz quiz: e.getQuizzes()){
//                List<Question> q = quiz.getQuestions();
//                List<User> u = quiz.getUsers();
//                questions.addAll(q);
//                users.addAll(u);
//            }
//        }
//        m.addAttribute("users",users);
//        m.addAttribute("experts", experts);
//        m.addAttribute("questions", questions);
//        return "views/management";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String tableContent(Model m) {
        m.addAttribute("users",userDAO.getAllUsers());
        m.addAttribute("experts",expertDAO.getAllExperts());
        m.addAttribute("questions",questionDAO.getAllQuestions());
        return "views/management";
    }


    //////////////// USER UPDATE ADD ////////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/user/{id}")
    public String updateUser(@PathVariable Long id, Model m) {
        User match = userDAO.getUser(id);
        m.addAttribute("user", match);
        return "views/management_userEdit";
    }

    @PostMapping("/user/updateUser/{id}")
    public RedirectView updateUser(@ModelAttribute User user, @PathVariable Long id) {
        userDAO.update(id, user);
        return new RedirectView("http://localhost:8080/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/user")
    public String addUser(Model m) {
        m.addAttribute("user", new User());
        return "views/management_userAdd";
    }

    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute User user) {
        userDAO.addUser(user);
        return new RedirectView("http://localhost:8080/management");
    }

    @PostMapping("/user/deleteUser/{id}")
    public RedirectView deleteUser(@PathVariable Long id) {
        userDAO.deleteUser(id);
        return new RedirectView("http://localhost:8080/management");
    }

    //////////////// EXPERT UPDATE ADD /////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/expert/{id}")
    public String updateExpert(@PathVariable Long id, Model m) {
        Expert match = expertDAO.getExpert(id);
        m.addAttribute("expert", match);
        return "views/management_expertEdit";
    }

    @PostMapping("/expert/updateExpert/{id}")
    public RedirectView updateExpert(@ModelAttribute Expert expert, @PathVariable Long id) {
        expertDAO.update(id, expert);
        return new RedirectView("http://localhost:8080/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/expert")
    public String addExpert(Model m) {
        m.addAttribute("expert", new Expert());
        return "views/management_expertAdd";
    }

    @PostMapping("/addExpert")
    public RedirectView addExpert(@ModelAttribute Expert expert) {
        expertDAO.addExpert(expert);
        return new RedirectView("/management");
    }

    @PostMapping("/expert/deleteExpert/{id}")
    public RedirectView deleteExpert(@PathVariable Long id) {
        expertDAO.deleteExpert(id);
        return new RedirectView("http://localhost:8080/management");
    }

    //////////////// BREADCRUMB UPDATE ////////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/breadcrumb/{id}")
    public String updateBreadcrumb(@PathVariable Long id, Model m) {
        Question match = questionDAO.findById(id);
        m.addAttribute("question", match);
        //List<Choice> choices = questionDAO.getChoices(id);
        //m.addAttribute("choices", choices);
        return "views/management_breadcrumbEdit";
    }

    @PostMapping("/breadcrumb/updateBreadcrumb/{id}")
    public RedirectView updateBreadcrumb(@ModelAttribute Question question, @PathVariable Long id) {
        try {

            int x_coord = question.getX_coord();
            int y_coord = question.getY_coord();

            String mapName = "venueMap_q" + id.toString();
            Map map = mapDAO.getMapByName(mapName);

            Blob blob = map.getPicture();
            BufferedImage bi_map = blobToImage(blob);
            BufferedImage bi_questionIcon = ImageIO.read(questionIcon.getInputStream());

            Graphics g = bi_map.getGraphics();
            g.drawImage(bi_questionIcon, x_coord, y_coord, 50, 50, null);

            Blob newPicture = imageToBlob(bi_map);
            Map newMap = new Map(id, mapName, newPicture);

            mapDAO.updateMapByName(mapName, newMap);
            questionDAO.update(id, question);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RedirectView("http://localhost:8080/management");
    }

    /*
    *
    * Make question id compare to map name, i.e question 1 draws onto venueMap_q1
    * Ensure an empty instance of the map is always present for drawing onto, i.e venueMap_empty
    * When a new map is added delete all other maps, save as venueMap_empty and create all instances of venueMap_qN
    * 
    *
    * */

    ////////////////// MAPS //////////////////////////////

    private MapDAO mapDAO = new MapDbDAO();

    @RequestMapping(method = RequestMethod.GET, value= "/map")
    public String getMap(Model m) {
        List<Map> maps = mapDAO.getAllMaps();
        m.addAttribute("maps", maps);
        return "views/map";
    }

    @GetMapping("/map/add")
    public String addMapPage(Model m) {
        m.addAttribute("map", new Map());
        return "views/map_addMap";
    }

    public BufferedImage multipartToImage(MultipartFile file) {
        try {
            File f = new File(file.getOriginalFilename());
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(file.getBytes());
            fos.close();
            return ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Blob imageToBlob(BufferedImage in) {
        try {
            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
            // IMAGE -> BYTES
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newImage, "png", baos);
            byte[] bytes = baos.toByteArray();
            return new javax.sql.rowset.serial.SerialBlob(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage blobToImage(Blob blob) {
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            InputStream in = new ByteArrayInputStream(bytes);
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // take in file -> convert to image -> convert to blob + add name -> add new Map
    @PostMapping("/map/add")
    public RedirectView addMap(@RequestParam("f") MultipartFile f) {
        try {
            BufferedImage bi = multipartToImage(f);
            Blob picture = imageToBlob(bi);
            // CREATE AND ADD MAP
            Map map = new Map((long) 1, "venueMap", picture);
            mapDAO.addMap(map);
            return new RedirectView("http://localhost:8080/map");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("http://localhost:8080/map");
    }

}
