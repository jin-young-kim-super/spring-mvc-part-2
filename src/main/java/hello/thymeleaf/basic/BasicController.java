package hello.thymeleaf.basic;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data","Hello Spring");
        return "/basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data","Hello <b>Spring</b>");
        return "/basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        ArrayList<Object> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String,User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "/basic/variable";
    }

    /**
     * Spring boot 3.0 이상부터는 더이상 ${#request} , ${#response} , ${#session} , ${#servletContext} 를 타임리프에서 바로 쓸 수 있게 자동 지원하지 않는다.
     * 그래서 model.addAttribute를 통해 일일이 넣어 줘야 한다.
     * 아래 버전은 Spring boot 3.0 이상 버전 코드이다.(그 아래에는 Spring boot 3.0 미만 버전의 코드도 있으니 참고, 강의자료에 자세한 설명이 있음)
     */
    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    /**
     * Spring boot 3.0 이상부터는 더이상 ${#request} , ${#response} , ${#session} , ${#servletContext} 를 자동 지원한다
     * 고로, 위 코드처럼 일일이 model.addAttribute에 넣어 주지 않아도 된다.
     */
//    @GetMapping("/basic-objects")
//    public String basicObjects(HttpSession session) {
//        session.setAttribute("sessionData", "Hello Session");
//        return "basic/basic-objects";
//    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "/basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data","Spring!");
        return "/basic/literal";
    }

    @GetMapping("/attribute")
    public String attribute() {
        return "/basic/attribute";
    }

    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData",null);
        model.addAttribute("data","Spring!");
        return "/basic/operation";
    }

    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "/basic/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "/basic/condition";
    }

    private void addUsers(Model model) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new User("UserA",10));
        list.add(new User("UserB",20));
        list.add(new User("UserB",30));
        model.addAttribute("users",list);
    }

    @Data
    public static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello" + data;
        }
    }

}
