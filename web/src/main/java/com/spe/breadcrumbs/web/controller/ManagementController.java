package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.web.DBConnection;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/management")
public class ManagementController {

    private MapDAO mapDAO = new MapDbDAO(new DBConnection());
    private UserDAO userDAO = new UserDbDAO(new DBConnection());
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());

    @Value(value = "classpath:static/mapFeatures/questionIcon.png")
    private Resource questionIcon;

    @RequestMapping(method = RequestMethod.GET)
    public String tableContent(Model m) {
        m.addAttribute("maps", mapDAO.getAllMaps());
        m.addAttribute("users", userDAO.getAllUsers());
        m.addAttribute("experts", expertDAO.getAllExperts());
        m.addAttribute("meetings", meetingDAO.getMeetings());
        m.addAttribute("questions", questionDAO.getAllQuestions());

        m.addAttribute("user", new User());
        m.addAttribute("expert", new Expert());
        m.addAttribute("meeting", new Meeting());

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
        return new RedirectView("/management");
    }

    @PostMapping("/user/deleteUser/{id}")
    public RedirectView deleteUserFromEdit(@PathVariable Long id) {
        userDAO.deleteUser(id);
        return new RedirectView("/management");
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
        return new RedirectView("/management");
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
        return new RedirectView("/management");
    }

    //////////////// BREADCRUMB UPDATE ////////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/breadcrumb/{id}")
    public String updateBreadcrumb(@PathVariable Long id, Model m) {

        Question questions = questionDAO.findById(id);
        m.addAttribute("question", questions);

        List<Choice> choices = questionDAO.getChoices(id);
        m.addAttribute("choices", choices);

        List<Hint> hints = questionDAO.getHints(id);
        m.addAttribute("hints", hints);

        Hint hint = new Hint();
        m.addAttribute("hint", hint);

//        int x_max = 2001;
//        int y_max = 2002;
//        int[] max = new int[]{ x_max, y_max };
//        m.addAttribute("x_max", x_max);
//        m.addAttribute("y_max", y_max);

        return "views/management_breadcrumbEdit";
    }

    public Blob drawQuestionImage(Long qNo, int x_coord, int y_coord) {
        try {
            Map emptyMap = mapDAO.getMapByName("venueMap_empty");
            Blob blob = emptyMap.getPicture();
            BufferedImage bi_emptyMap = blobToImage(blob);
            BufferedImage bi_questionIcon = ImageIO.read(questionIcon.getInputStream());
            Graphics g = bi_emptyMap.getGraphics();
            g.drawImage(bi_questionIcon, x_coord, y_coord, 50, 50, null);
            return imageToBlob(bi_emptyMap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/breadcrumb/updateBreadcrumb/{id}")
    public RedirectView updateBreadcrumb(@ModelAttribute Question question, @PathVariable Long id, Model m) {
        questionDAO.update(id, question);
        return new RedirectView("/management/breadcrumb/" + id);
    }

    @PostMapping("/breadcrumb/updateBreadcrumbLocation/{id}")
    public RedirectView updateBreadcrumbLocation(@ModelAttribute Question question, @PathVariable Long id, Model m) {
        int x_coord = question.getX_coord();
        int y_coord = question.getY_coord();
        String mapName = "venueMap_q" + id.toString();
        Blob newPicture = drawQuestionImage(id, x_coord, y_coord);
        Map newMap = new Map(id, mapName, newPicture);
        mapDAO.updateMapByName(mapName, newMap);
        questionDAO.updateLocation(id, question);
        return new RedirectView("/management/breadcrumb/" + id);
    }

    ////////////////// HINTS //////////////////////////////

    @PostMapping("/addHint/{question_id}")
    public RedirectView addHint(@ModelAttribute Hint hint, @PathVariable Long question_id) {
        questionDAO.addHint(hint, question_id);
        return new RedirectView("/management/breadcrumb/"+question_id);
    }

    ////////////////// MAPS //////////////////////////////

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

    // purge old maps -> add new empty map -> generate new question maps
    @PostMapping("/addMap")
    public RedirectView addMap(@RequestParam("f") MultipartFile f) {
        try {
            mapDAO.deleteAllMaps();
            BufferedImage bi = multipartToImage(f);
            Blob picture = imageToBlob(bi);
            Map emptyMap = new Map((long) 1, "venueMap_empty", picture);
            mapDAO.addMap(emptyMap);
            List<Question> questions = questionDAO.getAllQuestions();
            for (Question question:questions) {
                String mapName = "venueMap_q" + question.getId().toString();
                Blob newPicture = drawQuestionImage(question.getId(), question.getX_coord(), question.getY_coord());
                Map newMap = new Map(question.getId(), mapName, newPicture);
                mapDAO.addMap(newMap);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new RedirectView("/management");
    }

    //////////////////////////// MEETINGS /////////////////////////////////

    @PostMapping("/addMeeting")
    public RedirectView addMeeting(@ModelAttribute Meeting meeting) {

        Expert expert = expertDAO.getExpert(meeting.getExpertId());
        User user = userDAO.getUser(meeting.getUserId());
        Time time = java.sql.Time.valueOf(meeting.getTime()+":00");

        meeting.setExpert(expert);
        meeting.setUser(user);
        meeting.setMeeting_time(time);

        meetingDAO.createMeeting(meeting);
        return new RedirectView("/management");
    }

    @PostMapping("/meeting/deleteMeeting/{user_id}&{expert_id}")
    public RedirectView deleteUserFromEdit(@PathVariable Long user_id, @PathVariable Long expert_id) {
        meetingDAO.deleteMeeting(user_id, expert_id);
        return new RedirectView("/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/meeting/{user_id}&{expert_id}")
    public String updateMeeting(@PathVariable Long user_id, @PathVariable Long expert_id, Model m) {
        List<Expert> experts = expertDAO.getAllExperts();
        List<User> users = userDAO.getAllUsers();
        Meeting meeting = meetingDAO.getMeeting(user_id, expert_id);
        m.addAttribute("experts", experts);
        m.addAttribute("users", users);
        m.addAttribute("meeting", meeting);
        return "views/management_meetingEdit";
    }

    @PostMapping("/meeting/updateMeeting")
    public RedirectView updateMeeting(@ModelAttribute Meeting meeting) {

        Expert expert = expertDAO.getExpert(meeting.getExpertId());
        User user = userDAO.getUser(meeting.getUserId());
        Time time = java.sql.Time.valueOf(meeting.getTime()+":00");

        meeting.setExpert(expert);
        meeting.setUser(user);
        meeting.setMeeting_time(time);

        meetingDAO.updateMeeting( meeting.getUserId(),meeting.getExpertId(), meeting);
        return new RedirectView("/management");
    }

}

