package za.co.dladle.thirdparty.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Created by prady on 10/2/2017.
 */
@Service
public class MailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    String build(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateName, context);
    }
}
