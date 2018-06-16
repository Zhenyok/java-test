package com.itnetsoft.controllers;

import com.itnetsoft.dto.SettingsDTO;
import com.itnetsoft.exceptions.SettingsServiceException;
import com.itnetsoft.services.ISettingsService;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/")
public class FrontEndController {

    @Inject
    private ISettingsService settingsService;

    @RequestMapping(value = "/login")
    public String login(Model model) {
        setRecapthcha(model);
        return "login";
    }


    private void setRecapthcha(Model model) {
        Properties property = new Properties();
        property.setProperty("theme","clean");
        ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha("6Ld_ev8SAAAAAPPEO07_zYW-r-AYTTmk9QndxK7-",
                "6Ld_ev8SAAAAANenvLM22Cx5PNTC_8lW6VjHXesW", false);
        String cHtml = c.createRecaptchaHtml(null, property);
        model.addAttribute("recaptcha", cHtml);
    }

    @RequestMapping(value = "/contacts",method = RequestMethod.GET)
    public String getContactsIndex(Model model) {
        model.addAttribute("settings", getSettingsList());
        return "page-contacts";
    }

    private List<SettingsDTO> getSettingsList() {
        List<SettingsDTO> settings = null;
        try {
            settings = settingsService.getSettingsList().getData();
            return settings;
        } catch(SettingsServiceException e) {
            e.printStackTrace();
        }
        return null;
    }


}
