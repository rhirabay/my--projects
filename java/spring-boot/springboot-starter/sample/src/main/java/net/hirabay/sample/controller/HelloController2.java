package net.hirabay.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController2 {
    // GETメソッドでパス「/hello」のリクエストを受け付ける設定
    @GetMapping("/hello2")
    public String hello(Model model) {
        // テンプレートで使う変数を設定
        model.addAttribute("key", "value");

        // テンプレート名を返す
        return "hello";
    }

    @GetMapping("/hello3")
    public ModelAndView hello(ModelAndView mav) {
        // テンプレートで使う変数を設定
        mav.addObject("key", "value");

        // テンプレート名を設定
        mav.setViewName("hello");

        // 引数で受け取ったものをそのまま返却
        return mav;
    }
}
