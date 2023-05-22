package com.example.demo.controllers;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsService;
import com.example.demo.security.TokenAuthentication;
import com.example.demo.users.Roles;
import com.example.demo.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DatabaseController{

    @ResponseBody
    @GetMapping(value ="/get_authenticated_username", produces="application/json")
    public String load_user_info()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null
                    && authentication.isAuthenticated())
                return ((TokenAuthentication)authentication).getClaims().get("name").asString();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

    @ResponseBody
    @GetMapping(value ="/get_roles", produces="application/json")
    public List<GrantedAuthority> get_roles()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null)
                return new ArrayList<>(authentication.getAuthorities());

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    @ResponseBody
    @GetMapping(value ="/on_main_page_loaded_script", produces="application/json")
    public String on_main_page_loaded_script()
    {
        JSONObject jsonObject = new JSONObject();
        String jsonField = "scriptName";
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication == null)
                jsonObject.put(jsonField, "main_page_init.js");
            else if(authentication instanceof TokenAuthentication)
            {
                var roles = AuthenticationUtils.get_roles_of_user();

                if(roles.contains(Roles.ADMIN.name()))
                    jsonObject.put(jsonField, "on_admin_logged_in.js");
                else
                    jsonObject.put(jsonField, "on_user_logged_in.js");
            }
            else
                jsonObject.put(jsonField, "main_page_init.js");
        } catch (Exception ex)
        {
            jsonObject.put(jsonField, "main_page_init.js");
            ex.printStackTrace();
        }

        return jsonObject.toString();
    }

    @GetMapping("/get_card_holder")
    public String get_card_holder() throws IOException {

        Resource resource = resourceLoader.getResource(
                "classpath:templates/card_holder.html");
        File file = resource.getFile();

        return new String(Files.readAllBytes(file.toPath()));
    }

    @GetMapping("/get_card_template")
    public String get_card_template() throws IOException{

        Resource resource = resourceLoader.getResource(
                "classpath:templates/card_template.html");
        File file = resource.getFile();

        return new String(Files.readAllBytes(file.toPath()));
    }


    @GetMapping("/get_user_cards")
    public List<Card> get_user_cards()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null
                    && authentication instanceof TokenAuthentication
                    && authentication.isAuthenticated())
            {
                TokenAuthentication tokenAuthentication =
                        (TokenAuthentication) authentication;

                var claims = tokenAuthentication.getClaims();

                return cardsService.get_users_cards(claims.get("name").asString());
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    private final ResourceLoader resourceLoader;
    private final CardsService cardsService;
}
