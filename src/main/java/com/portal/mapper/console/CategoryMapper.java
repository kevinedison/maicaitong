package com.portal.mapper.console;

import com.portal.bean.console.Category;
import com.portal.mapper.CrudMapper;

public interface CategoryMapper extends CrudMapper<Category>
{
    Category selectByCode(String code);
}
