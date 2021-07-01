package com.groupdocs.ui.total;

import com.groupdocs.ui.config.GlobalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class TotalController {

    private static final Logger logger = LoggerFactory.getLogger(TotalController.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @RequestMapping("/")
    public String total(Map<String, Object> model) {
        logger.debug("global config: {}", globalConfiguration);
        model.put("globalConfiguration", globalConfiguration);
        return "total";
    }
}
