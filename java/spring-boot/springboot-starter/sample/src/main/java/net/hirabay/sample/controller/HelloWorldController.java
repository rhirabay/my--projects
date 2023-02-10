package net.hirabay.sample.controller;

import lombok.RequiredArgsConstructor;
import net.hirabay.sample.service.HelloWorldService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controllerアノテーションを付与する
@Controller
// finalがついたフィールドを引数にとるコンストラクタを自動で生成してくれる
@RequiredArgsConstructor
public class HelloWorldController {
    // 利用したいクラスをfinal修飾子つきで定義
    private final HelloWorldService helloWorldService;

    // アクセスされるパスを指定
    @GetMapping("/hello")
    public String hello() {
        helloWorldService.hello();

        // テンプレート名を返却
        // templates配下の「xxxx.html」と対応させる
        return "hello";
    }
}
