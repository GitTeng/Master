/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package tv.huan.master.common.bind.method;

import org.apache.commons.lang3.StringUtils;
import tv.huan.master.common.bind.annotation.SearchableDefaults;
import tv.huan.master.common.model.Searchable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @since 3.1
 */
public class SearchableMethodArgumentResolver extends BaseMethodArgumentResolver {

    private static final String DEFAULT_SEARCH_PREFIX = "search";
    private String prefix = DEFAULT_SEARCH_PREFIX;

    public boolean supportsParameter(MethodParameter parameter) {
        return Searchable.class.isAssignableFrom(parameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String searchPrefix = getSearchPrefix(parameter);

        Map<String, String> searcheableMap = getPrefixParameterMap(searchPrefix, webRequest, true);

        boolean hasCustomSearchFilter = searcheableMap.size() > 0;

        SearchableDefaults searchDefaults = getSearchableDefaults(parameter);

        boolean needMergeDefault = searchDefaults != null && searchDefaults.merge();

        Searchable searchable = null;
        //自定义覆盖默认
        if (needMergeDefault || !hasCustomSearchFilter) {
            searchable = getDefaultFromAnnotation(searchDefaults);
        }
        if (hasCustomSearchFilter) {
            if (searchable == null) {
                searchable = Searchable.newSearchable();
            }
            for (String name : searcheableMap.keySet()) {
                String mapValues = searcheableMap.get(name);
                if (!StringUtils.isBlank(mapValues)) {
                    searchable.addSearchParam(name, mapValues);
                }
            }
        }

        return searchable;
    }

    private SearchableDefaults getSearchableDefaults(MethodParameter parameter) {
        //首先从参数上找
        SearchableDefaults searchDefaults = parameter.getParameterAnnotation(SearchableDefaults.class);
        //找不到从方法上找
        if (searchDefaults == null) {
            searchDefaults = parameter.getMethodAnnotation(SearchableDefaults.class);
        }
        return searchDefaults;
    }


    private Searchable getDefaultFromAnnotation(SearchableDefaults searchableDefaults) {

        Searchable searchable = defaultSearchable(searchableDefaults);
        if (searchable != null) {
            return searchable;
        }

        return Searchable.newSearchable();
    }

    private Searchable defaultSearchable(SearchableDefaults searchableDefaults) {

        if (searchableDefaults == null) {
            return null;
        }

        Searchable searchable = Searchable.newSearchable();
        for (String searchParam : searchableDefaults.value()) {
            String[] searchPair = searchParam.split("=");
            String paramName = searchPair[0];
            String paramValue = searchPair[1];
            searchable.addSearchParam(paramName, paramValue);
        }

        return searchable;
    }

    private String getSearchPrefix(MethodParameter parameter) {
        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);
        if (qualifier != null) {
            return (qualifier).value() + "_" + prefix;
        }
        return prefix;
    }
}
