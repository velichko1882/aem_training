package com.epam.aem.training.core.services.impl;

import static com.epam.aem.training.core.bean.Constants.*;

import com.epam.aem.training.core.bean.PageDescriptor;
import com.epam.aem.training.core.services.ReviewComponentSlingService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = ReviewComponentSlingService.class)
public class ReviewComponentSlingServiceImpl implements ReviewComponentSlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewComponentSlingServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public PageDescriptor getPageDescriptor(String path) {
        String title;
        String text;
        String pathToImage;
        String referenceToPage;
        PageDescriptor pageDescriptor = new PageDescriptor();
        ResourceResolver resourceResolver = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
            Resource resource = resourceResolver.resolve(path);
            title = resource.getChild(JCR_CONTENT).getValueMap().get(JCR_TITLE, String.class);
            if (path.equals(PATH_TO_HOME_PAGE)){
                text = resource.getChild(JCR_CONTENT).getValueMap().get(JCR_DESCRIPTION, String.class);
            } else {
                text = resource.getChild(RESOURCE_TEXT).getValueMap().get(TEXT, String.class);
            }
            pathToImage = resource.getChild(PROPERTY_IMAGE).getPath();
            referenceToPage = path + HTML;
            pageDescriptor = new PageDescriptor(title, text, pathToImage, referenceToPage);
        } catch (LoginException e) {
            LOGGER.error(MESSAGE_LOGIN_EXCEPTION, e.getMessage());
        }
        return pageDescriptor;
    }

    @Override
    public List<String> getChildPagesPaths(String rootPath) {
        ResourceResolver resourceResolver = null;
        List<String> childPagesPaths = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
            Resource resource = resourceResolver.resolve(rootPath);
            Iterable<Resource> resourceChildren = resource.getChildren();
            for (Resource child : resourceChildren) {
                if (child.isResourceType(VALUE_CQ_PAGE)){
                    childPagesPaths.add(child.getPath());
                }
            }
        } catch (LoginException e) {
            LOGGER.error(MESSAGE_LOGIN_EXCEPTION, e.getMessage());
        }
        return childPagesPaths;
    }
}
