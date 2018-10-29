package com.zcpure.foreign.trade.user.utils.page;

import com.github.pagehelper.Page;
import com.zcpure.foreign.trade.utils.page.PageBean;

public class PageBeanAssembler {
    
    public <T> PageBean<T> toBean(Page<T> page) {
        return new PageBean<T>(
                        page.getTotal(), 
                        page.getResult(), 
                        page.getPageNum(), 
                        page.getPageSize(), 
                        page.getPages());
    }
    
}
