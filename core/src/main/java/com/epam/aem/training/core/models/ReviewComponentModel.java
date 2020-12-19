package com.epam.aem.training.core.models;

import com.epam.aem.training.core.bean.Constants;
import com.epam.aem.training.core.bean.PageDescriptor;
import com.epam.aem.training.core.services.ReviewComponentJCRService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ReviewComponentModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewComponentModel.class);

    @OSGiService
    private ReviewComponentJCRService jcrService;

    @Inject
    private String behavior;

    @Inject
    private String headline;

    @Inject
    private String numberOfElements;

    @Inject
    private List<String> paths = new ArrayList<>();

    @Inject
    @Default(values = Constants.PATH_TO_HOME_PAGE)
    private String path;

    public String getBehavior() {
        return behavior.toUpperCase();
    }

    public String getHeadline() {
        return headline.toUpperCase();
    }

    public int getNumberOfElements() {
        return Integer.parseInt(numberOfElements);
    }

    public List<String> getPaths() {
        return paths;
    }

    public String getPath() {
        return path;
    }

    public List<PageDescriptor> getFirstPortionPages() {
        return jcrService.getPageDescriptorsForPortion(paths);
    }

}
