package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.BrowserType;

/**
 * @ClassName: BrowseRecordAnnotation
 * @Description: 浏览记录
 * @author: hmr
 * @date: 2020/4/26 15:19
 */
public @interface BrowseRecordAnnotation {

    String value();

    BrowserType browserType();

}
