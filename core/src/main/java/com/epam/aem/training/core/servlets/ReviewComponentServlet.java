package com.epam.aem.training.core.servlets;

import static com.epam.aem.training.core.bean.Constants.*;

import com.epam.aem.training.core.bean.PageDescriptor;
import com.epam.aem.training.core.models.ReviewComponentModel;
import com.epam.aem.training.core.services.ReviewComponentJCRService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "training/components/content/review-component",
        methods = HttpConstants.METHOD_GET,
        selectors = "data",
        extensions = "html")
public class ReviewComponentServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewComponentServlet.class);

    @Reference
    ReviewComponentJCRService jcrService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        Resource resource = request.getResource();
        ReviewComponentModel reviewComponentModel = resource.adaptTo(ReviewComponentModel.class);
        List<String> pathsAllPages = jcrService.getAllPathsToPages(PATH_TO_HOME_PAGE);
        int numberOfElements = reviewComponentModel.getNumberOfElements();
        try {
            List<String> pathsExistPages = getPathsToPagesInComponent(request);
            JSONObject jsonObject = new JSONObject();
            List<PageDescriptor> portionPages = getPortionPages(pathsAllPages, pathsExistPages, numberOfElements);
            jsonObject.put(PORTION, portionPages);
            jsonObject.put(REMAINDER, pathsAllPages.size() - numberOfElements);
            response.setContentType(CONTENT_TYPE);
            response.getWriter().println(jsonObject.toString());
        } catch (JSONException e) {
            LOGGER.error(MESSAGE_JSON_EXCEPTION + e.getMessage());
            throw new IOException(MESSAGE_JSON_EXCEPTION, e);
        }
    }

    private List<String> getPathsToPagesInComponent(SlingHttpServletRequest request) throws JSONException {
        List<String> pathsToExistingPages = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter(PARAMETER_PATH_EXIST_PAGES));
        for(int i = 0; i < jsonArray.length(); i++) {
            String path = jsonArray.getString(i).replace(HTML, EMPTY_STRING);
            pathsToExistingPages.add(path);
        }
        return pathsToExistingPages;
    }

    private List<PageDescriptor> getPortionPages(List<String> pathsAllPages, List<String> pathsExistPages,
                                  int numberElementPortion){
        List<String> pathsPortionPages = new ArrayList<>();
        pathsAllPages.removeAll(pathsExistPages);
        for (int i = 0; i < numberElementPortion && i < pathsAllPages.size(); i++){
            pathsPortionPages.add(pathsAllPages.get(i));
        }
        return jcrService.getPageDescriptorsForPortion(pathsPortionPages);
    }

}
