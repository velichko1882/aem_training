package com.epam.aem.training.core.servlets;

import static com.epam.aem.training.core.bean.Constants.*;

import com.epam.aem.training.core.bean.Constants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "training/components/content/review-component",
        methods = HttpConstants.METHOD_GET,
        selectors = "path",
        extensions = "html")
public class ChangePathServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePathServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        Resource resource = request.getResource();
        String path = request.getParameter(PARAMETER_PATH);
        path = path.replace("http://localhost:4502", EMPTY_STRING).replace(HTML, EMPTY_STRING);
        ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
        modifiableValueMap.put(PARAMETER_PATH, path);
        resource.getResourceResolver().commit();
        LOGGER.info("new path = " + resource.getValueMap().get("path", String.class));
        response.sendRedirect(PATH_TO_HOME_PAGE + HTML);
    }
}
