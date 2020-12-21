package com.epam.aem.training.core.services;

import com.epam.aem.training.core.bean.PageDescriptor;

import java.util.List;

public interface ReviewComponentSlingService {

    PageDescriptor getPageDescriptor(String path);

    List<String> getChildPagesPaths(String rootPath);
}
