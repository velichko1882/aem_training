package com.epam.aem.training.core.services;

import com.epam.aem.training.core.bean.PageDescriptor;

import java.util.List;

public interface ReviewComponentJCRService {

    List<PageDescriptor> getPageDescriptorsForPortion(List<String> pathsToPages);

    List<String> getAllPathsToPages(String homePath);

}
