package com.epam.aem.training.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ReviewComponentModel {

    @Inject
    private String behavior;

    @Inject
    private String headline;

    @Inject
    private String numberOfElements;

    @Inject
    private List<String> paths;

    @Inject
    private String path;

    public String getBehavior() {
        return behavior;
    }

    public String getHeadline() {
        return headline;
    }

    public String getNumberOfElements() {
        return numberOfElements;
    }

    public List<String> getPaths() {
        return paths;
    }

    public String getPath() {
        return path;
    }
}
