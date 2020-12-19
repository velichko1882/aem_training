package com.epam.aem.training.core.services.impl;

import static com.epam.aem.training.core.bean.Constants.*;

import com.epam.aem.training.core.bean.PageDescriptor;
import com.epam.aem.training.core.services.ReviewComponentJCRService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = ReviewComponentJCRService.class)
public class ReviewComponentJCRServiceImpl  implements ReviewComponentJCRService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewComponentJCRServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<PageDescriptor> getPageDescriptorsForPortion(List<String> pathsToPages) {
        List<PageDescriptor> pageDescriptors = new ArrayList<>();
        ResourceResolver resourceResolver = null;
        Session session = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
            session = resourceResolver.adaptTo(Session.class);
            for (String path : pathsToPages) {
                Node currentNode = session.getNode(path);
                String title = currentNode.getProperty(PROPERTY_TITLE).getString();
                String text = currentNode.getProperty(PROPERTY_TEXT).getString();
                String pathToImage = currentNode.getNode(PROPERTY_IMAGE).getPath();
                pageDescriptors.add(new PageDescriptor(title, text, pathToImage, path + HTML));
            }
        } catch (LoginException e) {
            LOGGER.error(MESSAGE_LOGIN_EXCEPTION + e.getMessage());
        } catch (PathNotFoundException e) {
            LOGGER.error(MESSAGE_PATH_NOT_FOUND_EXCEPTION + e.getMessage());
        } catch (RepositoryException e) {
            LOGGER.error(MESSAGE_REPOSITORY_EXCEPTION + e.getMessage());
        } finally {
            if (resourceResolver != null) {
                resourceResolver.close();
            }
            if (session != null) {
                session.logout();
            }
        }
        return pageDescriptors;
    }

    @Override
    public List<String> getAllPathsToPages(String homePath) {
        List<String> allPagePaths = new ArrayList<>();
        ResourceResolver resourceResolver = null;
        Session session = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
            session = resourceResolver.adaptTo(Session.class);
            Node homeNode = session.getNode(homePath);
            allPagePaths.addAll(getAllChildPagesPathsRecursively(homeNode));
        } catch (LoginException e) {
            LOGGER.error(MESSAGE_LOGIN_EXCEPTION + e.getMessage());
        } catch (PathNotFoundException e) {
            LOGGER.error(MESSAGE_PATH_NOT_FOUND_EXCEPTION + e.getMessage());
        } catch (RepositoryException e) {
            LOGGER.error(MESSAGE_REPOSITORY_EXCEPTION + e.getMessage());
        } finally {
            if (resourceResolver != null) {
                resourceResolver.close();
            }
            if (session != null) {
                session.logout();
            }
        }
        return allPagePaths;
    }

    private List<String> getAllChildPagesPathsRecursively(Node currentNode) throws RepositoryException {
        List<String> paths = new ArrayList<>();
        NodeIterator childNodes = currentNode.getNodes();
        while(childNodes.hasNext()) {
            Node node = childNodes.nextNode();
            if(node.getProperty(PROPERTY_PRIMARY_TYPE).getString().equals(VALUE_CQ_PAGE)){
                paths.add(node.getPath());
                paths.addAll(getAllChildPagesPathsRecursively(node));
            }
        }
        return paths;
    }


}
