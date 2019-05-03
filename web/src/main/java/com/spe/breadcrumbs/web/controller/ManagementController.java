package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import com.spe.breadcrumbs.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin
@Controller
@RequestMapping("/management")
public class ManagementController {

    private MapDAO mapDAO = new MapDbDAO(new DBConnection());
    private UserDAO userDAO = new UserDbDAO(new DBConnection());
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());
    private HintDAO hintDAO = new HintDbDAO(new DBConnection());

    @Value(value = "classpath:static/mapFeatures/questionIcon.png")
    private Resource questionIcon;
    @Value(value = "classpath:static/mapFeatures/hintIcon.png")
    private Resource hintIcon;
    @Value(value = "classpath:static/mapFeatures/meetingIcon.png")
    private Resource meetingIcon;

    @Autowired
    private SecurityService securityService;

    private List<Meeting> getCurrentExpertsMeetings() {
        String username = securityService.findLoggedInUsername();
        Expert currentExpert = expertDAO.findByEmail(username);
        return meetingDAO.getMeetingsWithExpert(currentExpert.getId());
    }

    private List<Meeting> getMeetingsWithUserAtEnd() {

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);
        List<Meeting> expertsMeetings = meetingDAO.getUpcomingMeetingsWithExpert(expert.getId());
        List<Meeting> meetingsWithUserAtEnd = new ArrayList<>();

        int noOfQuestions = questionDAO.getAllQuestions().size();
        for (Meeting meeting : expertsMeetings) {
            List<Question> questionsAnswered = questionDAO.getQuestionsAnswered(meeting.getUser().getId());
            if (questionsAnswered.size() >= noOfQuestions - 1) meetingsWithUserAtEnd.add(meeting);
        }

        return meetingsWithUserAtEnd;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String tableContent(Model m) {

        List<User> users = userDAO.getAllUsers();
        List<Meeting> meetings = meetingDAO.getMeetings();
        List<User> usersWithoutMeetings = getUsersWithoutMeetings();

        m.addAttribute("users", users);
        m.addAttribute("meetings", meetings);
        m.addAttribute("usersWithoutMeetings", usersWithoutMeetings);
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());

        m.addAttribute("maps", mapDAO.getAllMaps());
        m.addAttribute("experts", expertDAO.getAllExperts());
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
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());
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
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());
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
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());
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
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());
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

        Question questions = questionDAO.getQuestion(id);
        m.addAttribute("question", questions);

        List<Choice> choices = questionDAO.getChoices(id);
        m.addAttribute("choices", choices);

        List<Hint> hints = questionDAO.getHints(id);
        m.addAttribute("hints", hints);

        Hint hint = new Hint();
        m.addAttribute("hint", hint);


        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());

        return "views/management_breadcrumbEdit";
    }

    @PostMapping("/breadcrumb/updateBreadcrumb/{id}")
    public RedirectView updateBreadcrumb(@ModelAttribute Question question, @PathVariable Long id, Model m) {
        questionDAO.update(id, question);
        return new RedirectView("/management/breadcrumb/" + id);
    }

    ////////////////// IMAGE HANDLING //////////////////////////////

    private BufferedImage multipartToImage(MultipartFile file) {
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

    private Blob imageToBlob(BufferedImage in) {
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

    private BufferedImage blobToImage(Blob blob) {
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

    //////////////// MAPS ////////////////////////////////

    private Blob drawMeetingImage(Meeting meeting) {
        try {

            Map emptyMap = mapDAO.getMapByName("venueMap_empty");
            Blob map = emptyMap.getPicture();

            BufferedImage bi_map = blobToImage(map);
            BufferedImage bi_meetingIcon = ImageIO.read(meetingIcon.getInputStream());

            Graphics g = bi_map.getGraphics();
            g.drawImage(bi_meetingIcon, meeting.getX_coord(), meeting.getY_coord(), 100, 100, null);

            return imageToBlob(bi_map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Blob drawQuestionImage(Question question) {
        try {

            Map emptyMap = mapDAO.getMapByName("venueMap_empty");
            Blob map = emptyMap.getPicture();

            BufferedImage bi_map = blobToImage(map);
            BufferedImage bi_questionIcon = ImageIO.read(questionIcon.getInputStream());

            Graphics g = bi_map.getGraphics();
            g.drawImage(bi_questionIcon, question.getX_coord(), question.getY_coord(), 100, 100, null);

            return imageToBlob(bi_map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Blob drawQuestionWithBothHintsImage(Question question, List<Hint> hints) {
        try {

            Map emptyMap = mapDAO.getMapByName("venueMap_empty");
            Blob map = emptyMap.getPicture();

            BufferedImage bi_map = blobToImage(map);
            BufferedImage bi_questionIcon = ImageIO.read(questionIcon.getInputStream());
            BufferedImage bi_hintIcon = ImageIO.read(hintIcon.getInputStream());

            Graphics g = bi_map.getGraphics();


            Random rand = new Random();

            int d = 400;
            int circle_x_coord = question.getX_coord() - (d/2) + rand.nextInt(d/2) - (d/4);
            int circle_y_coord = question.getY_coord() - (d/2) + rand.nextInt(d/2) - (d/4);

            Color colour = new Color(64, 128, 188, 127);
            g.setColor(colour);
            g.fillOval(circle_x_coord, circle_y_coord, d, d);

            for (Hint hint : hints) {
                g.drawImage(bi_hintIcon, hint.getX_coord(), hint.getY_coord(), 50, 50, null);
            }

            return imageToBlob(bi_map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Blob drawQuestionWithOneHintImage(Question question, Hint hint) {
        try {

            Map emptyMap = mapDAO.getMapByName("venueMap_empty");
            Blob map = emptyMap.getPicture();

            BufferedImage bi_map = blobToImage(map);
            BufferedImage bi_hintIcon = ImageIO.read(hintIcon.getInputStream());

            Graphics g = bi_map.getGraphics();

            Random rand = new Random();

            int d = 200;
            int circle_x_coord = question.getX_coord() - (d/2) + rand.nextInt(d/2) - (d/4);
            int circle_y_coord = question.getY_coord() - (d/2) + rand.nextInt(d/2) - (d/4);

            Color colour = new Color(64, 128, 188, 127);
            g.setColor(colour);
            g.fillOval(circle_x_coord, circle_y_coord, d, d);

            g.drawImage(bi_hintIcon, hint.getX_coord(), hint.getY_coord(), 50, 50, null);

            return imageToBlob(bi_map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean addQuestionMap(Question question) {

        String mapName, hintString;
        Blob mapImage;
        Map map;

        List<Hint> hints = questionDAO.getHints(question.getId());

        try {

            if (hints.size() > 1) {
                mapName = "venueMap_q" + question.getId().toString() + "_c11";
                mapImage = drawQuestionWithBothHintsImage(question, hints);
                map = new Map(question.getId(), mapName, mapImage);
                mapDAO.addMap(map);
            }

            int i = 0;
            for (Hint hint : hints) {
                if (i == 0) hintString = "_c10";
                else        hintString = "_c01";
                mapName = "venueMap_q" + question.getId().toString() + hintString;
                mapImage = drawQuestionWithOneHintImage(question, hint);
                map = new Map(question.getId(), mapName, mapImage);
                mapDAO.addMap(map);
                i++;
            }

            mapName = "venueMap_q" + question.getId().toString();
            mapImage = drawQuestionImage(question);
            map = new Map(question.getId(), mapName, mapImage);
            mapDAO.addMap(map);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean updateQuestionMap(Question question) {

        String mapName, hintString;
        Blob mapImage;
        Map map;

        List<Hint> hints = questionDAO.getHints(question.getId());

        try {

            if (hints.size() > 1) {
                mapName = "venueMap_q" + question.getId().toString() + "_c11";
                mapImage = drawQuestionWithBothHintsImage(question, hints);
                map = new Map(question.getId(), mapName, mapImage);
                mapDAO.updateMapByName(mapName, map);
            }

            int i = 0;
            for (Hint hint : hints) {
                if (i == 0) hintString = "_c10";
                else        hintString = "_c01";
                mapName = "venueMap_q" + question.getId().toString() + hintString;
                mapImage = drawQuestionWithOneHintImage(question, hint);
                map = new Map(question.getId(), mapName, mapImage);
                mapDAO.updateMapByName(mapName, map);
                i++;
            }

            mapName = "venueMap_q" + question.getId().toString();
            mapImage = drawQuestionImage(question);
            map = new Map(question.getId(), mapName, mapImage);
            mapDAO.updateMapByName(mapName, map);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // purge old maps -> add new empty map -> generate new question maps
    @PostMapping("/addMap")
    public RedirectView changeMap(@RequestParam("f") MultipartFile f) {

        try {
            mapDAO.deleteAllMaps();

            BufferedImage bi = multipartToImage(f);
            Blob picture = imageToBlob(bi);
            Map emptyMap = new Map((long)0, "venueMap_empty", picture);
            mapDAO.addMap(emptyMap);

            List<Question> questions = questionDAO.getAllQuestions();
            for (Question question:questions) {
                addQuestionMap(question);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new RedirectView("/management");
    }

    @PostMapping("/breadcrumb/updateBreadcrumbLocation/{id}")
    public RedirectView updateBreadcrumbLocation(@ModelAttribute Question question, @PathVariable Long id, Model m) {
        updateQuestionMap(question);
        questionDAO.updateLocation(id, question);
        return new RedirectView("/management/breadcrumb/" + id);
    }

    @PostMapping("//updateHintLocation/{question_id}&{hint_id}")
    public RedirectView updateHintLocation(@ModelAttribute Hint hint, @PathVariable Long question_id, @PathVariable Long hint_id, Model m) {
        hintDAO.updateHintLocation(hint, hint_id);
        Question question = questionDAO.getQuestion(question_id);
        updateQuestionMap(question);
        return new RedirectView("/management/breadcrumb/" + question_id);
    }

    @PostMapping("/addHint/{question_id}")
    public RedirectView addHint(/*@RequestParam("f") MultipartFile f,*/ @ModelAttribute Hint hint, @PathVariable Long question_id) {


        hintDAO.addHint(hint, question_id);

        Question question = questionDAO.getQuestion(question_id);
        mapDAO.deleteMapsForQuestion(question_id);
        addQuestionMap(question);

        return new RedirectView("/management/breadcrumb/"+question_id);
    }

    @PostMapping(value = "/updateHint/{question_id}&{hint_id}")
    public RedirectView updateHint(@ModelAttribute Hint hint,
                                   @PathVariable Long question_id, @PathVariable Long hint_id,
                                   @RequestParam(value="action", required=true) String action) {

        switch (action) {

            case "submit":
                hintDAO.updateHint(hint, hint_id);
                break;

            case "delete":
                hintDAO.deleteHint(hint_id);
                Question question = questionDAO.getQuestion(question_id);
                mapDAO.deleteMapsForQuestion(question_id);
                addQuestionMap(question);
                break;

        }

        return new RedirectView("/management/breadcrumb/"+question_id);
    }

    @PostMapping("/updateHintImage/{question_id}&{hint_id}&{hint_no}")
    public RedirectView changeHintImage(@RequestParam("f") MultipartFile f, @RequestParam(value="action", required=true) String action,
                                        @PathVariable Long question_id, @PathVariable Long hint_id, @PathVariable int hint_no) {

        switch (action) {

            case "submit":
                try {
                    String pictureName = "hintImage_q" + question_id + "_h" + hint_no;
                    BufferedImage bi = multipartToImage(f);
                    Blob picture = imageToBlob(bi);
                    hintDAO.updateHintImage(pictureName, picture, hint_id);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "delete":
                hintDAO.removeHintImage(hint_id);
                break;

        }

        return new RedirectView("/management/breadcrumb/"+question_id);
    }

    //////////////////////////// MEETINGS /////////////////////////////////

    private List<User> getUsersWithoutMeetings() {

        List<User> users = userDAO.getAllUsers();
        List<Meeting> meetings = meetingDAO.getMeetings();
        List<User> userWithMeetings = new ArrayList<>();

        for (User user: users) {
            for (Meeting meeting : meetings) {
                if (user.getId().equals(meeting.getUser().getId())) {
                    userWithMeetings.add(user);
                    break;
                }
            }
        }
        users.removeAll(userWithMeetings);

        return users;
    }

    @PostMapping("/addMeeting")
    public RedirectView addMeeting(@ModelAttribute Meeting meeting) {

        Expert expert = expertDAO.getExpert(meeting.getExpertId());
        User user = userDAO.getUser(meeting.getUserId());
        Time time = java.sql.Time.valueOf(meeting.getTime()+":00");

        meeting.setExpert(expert);
        meeting.setUser(user);
        meeting.setMeeting_time(time);
        //meeting.setPicture(drawMeetingImage(meeting));

        meetingDAO.createMeeting(meeting);

        return new RedirectView("/management");
    }

    @PostMapping("/meeting/deleteMeeting/{user_id}&{expert_id}")
    public RedirectView deleteUserFromEdit(@PathVariable Long user_id, @PathVariable Long expert_id) {
        meetingDAO.deleteMeeting(user_id);
        return new RedirectView("/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/meeting/{user_id}&{expert_id}")
    public String updateMeeting(@PathVariable Long user_id, @PathVariable Long expert_id, Model m) {
        List<Expert> experts = expertDAO.getAllExperts();
        List<User> users = userDAO.getAllUsers();
        Meeting meeting = meetingDAO.getMeeting(user_id);
        m.addAttribute("experts", experts);
        m.addAttribute("users", users);
        m.addAttribute("meeting", meeting);
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd());
        return "views/management_meetingEdit";
    }

    @PostMapping("/meeting/updateMeeting")
    public RedirectView updateMeeting(@ModelAttribute Meeting meeting) {
        Time time = java.sql.Time.valueOf(meeting.getTime()+":00");
        meeting.setMeeting_time(time);
        meetingDAO.updateMeeting( meeting.getUserId(), meeting);
        return new RedirectView("/management");
    }

    @PostMapping("/meeting/updateMeetingLocation/{user_id}&{expert_id}")
    public RedirectView updateMeetingLocation(@ModelAttribute Meeting meeting, @PathVariable Long user_id, @PathVariable Long expert_id) {
//        meeting.setPicture(drawMeetingImage(meeting));
        meetingDAO.updateMeetingLocation(user_id, meeting);
        return new RedirectView("/management/"+user_id+'&'+expert_id);
    }

}

