package pthink.mailservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "test")
@Slf4j
public class TestController {
    @GetMapping(value = "/")
    public ModelAndView account(ModelAndView mav) {

        mav.setViewName("test/index");
        return mav;
    }
}
