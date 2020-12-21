package com.epam.aem.training.core.bean;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;

public class Constants {

    public static final String PATH_TO_HOME_PAGE = "/content/training-home";
    public static final String SUBSERVICE_NAME = "review-component-service";

    public static final String PROPERTY_TITLE = "jcr:content/jcr:title";
    public static final String PROPERTY_TEXT = "jcr:content/text/text";
    public static final String RESOURCE_TEXT = "jcr:content/text";
    public static final String PROPERTY_IMAGE = "jcr:content/image/file";
    public static final String PROPERTY_PRIMARY_TYPE = JcrConstants.JCR_PRIMARYTYPE;
    public static final String VALUE_CQ_PAGE = NameConstants.NT_PAGE;
    public static final String JCR_CONTENT = JcrConstants.JCR_CONTENT;
    public static final String JCR_TITLE = JcrConstants.JCR_TITLE;
    public static final String JCR_DESCRIPTION = JcrConstants.JCR_DESCRIPTION;
    public static final String HTML = ".html";
    public static final String EMPTY_STRING = "";

    public static final String MESSAGE_LOGIN_EXCEPTION = "Login Exception! ";
    public static final String MESSAGE_PATH_NOT_FOUND_EXCEPTION = "PathNotFoundException: ";
    public static final String MESSAGE_REPOSITORY_EXCEPTION = "RepositoryException: ";
    public static final String MESSAGE_JSON_EXCEPTION = "JSONException: ";

    public static final String PORTION = "portion";
    public static final String REMAINDER = "remainder";
    public static final String CONTENT_TYPE = "application/json";
    public static final String PARAMETER_PATH_EXIST_PAGES = "pathsToExistingPages";
    public static final String PARAMETER_PATH = "path";
    public static final String TEXT = "text";


}
