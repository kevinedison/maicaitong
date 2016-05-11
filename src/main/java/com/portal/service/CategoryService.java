package com.portal.service;

import com.portal.bean.console.Category;

public interface CategoryService extends CrudService<Category>
{
    Category selectByCode(String code);
}
