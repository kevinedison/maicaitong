package com.portal.rest.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.console.Brand;
import com.portal.bean.console.Category;
import com.portal.bean.console.CategorySubType;
import com.portal.bean.request.console.BrandSaveRequest;
import com.portal.bean.request.console.BrandSearchRequest;
import com.portal.bean.request.console.BrandUpdateRequest;
import com.portal.bean.request.console.CategorySaveRequest;
import com.portal.bean.request.console.CategorySearchRequest;
import com.portal.bean.request.console.CategoryUpdateRequest;
import com.portal.bean.request.console.SubCategorySaveRequest;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.BusinessCode;
import com.portal.manager.AdminSessionManager;
import com.portal.manager.CategoryBrandManager;
import com.portal.manager.UserManager;
import com.portal.service.BrandService;
import com.portal.service.CategoryService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class BrandController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);
    
    @Autowired
    BrandService brandService;
    
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    CategoryBrandManager categoryBrandManager;
    
    @RequestMapping(value = "/console/brand/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getBrandList(HttpServletRequest request, @Valid BrandSearchRequest brandSearchRequest)
    {
        LOGGER.info("query sysbrand begin");
        ResponseData<PagingData<Brand>> responseData = new ResponseData<PagingData<Brand>>();
        
        Brand brand = brandSearchRequest.toEntity(adminSessionManager.getSessionAdminId(request));
        
        PagingData<Brand> brandData = brandService.selectByIndex(brand);
        responseData.setData(brandData);
        LOGGER.info("query sysbrand end");
        return responseData;
    }
    
    @RequestMapping(value = "/console/brand", method = RequestMethod.POST)
    @ResponseBody
    public Response addBrand(HttpServletRequest request, @Valid @RequestBody BrandSaveRequest brandSaveRequest)
    {
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        String categoryCode = brandSaveRequest.getCategoryCode();
        // 查询
        Category category = categoryService.selectByCode(categoryCode);
        if (null == category)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        Brand brand = brandSaveRequest.toEntity(admin);
        
        brandService.save(brand);
        
        // 获取brands
        List<String> categoryList = category.getBrands();
        
        if (!categoryList.contains(brand.getCode()))
        {
            categoryList.add(brand.getCode());
        }
        else
        {
            response.setCode(BusinessCode.BRAND_FAIL.getCode());
            return response;
        }
        JsonMapper mapper = new JsonMapper();
        category.setBrand(mapper.toJson(categoryList));
        categoryService.updateById(category);
        
        // 刷新品牌
        categoryBrandManager.loadBrand();
        
        // 刷新分类
        categoryBrandManager.loadCategory();
        
        return response;
    }
    
    @RequestMapping(value = "/console/brand", method = RequestMethod.PUT)
    @ResponseBody
    public Response editBrand(HttpServletRequest request, @Valid @RequestBody BrandUpdateRequest brandUpdateRequest)
    {
        LOGGER.info("edit sysbrand begin");
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Brand brand = brandUpdateRequest.toEntity(admin);
        
        brandService.updateById(brand);
        
        // 刷新品牌
        categoryBrandManager.loadBrand();
        // 刷新分类
        categoryBrandManager.loadCategory();
        
        LOGGER.info("edit sysbrand begin");
        return response;
    }
    
    @RequestMapping(value = "/console/brand", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delBrand(HttpServletRequest request, @Valid String id, @Valid String categoryCode)
    {
        LOGGER.info("delete sysbrand begin");
        Response response = new Response();
        
        // 查询分类是否
        Category category = categoryService.selectByCode(categoryCode);
        
        if (null == category)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        // 查询品牌是否存在
        Brand brand = brandService.selectById(id);
        if (null == brand)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        brandService.deleteById(brand);
        
        List<String> brandList = category.getBrands();
        brandList.remove(brand.getCode());
        
        JsonMapper mapper = new JsonMapper();
        category.setBrand(mapper.toJson(brandList));
        categoryService.updateById(category);
        
        // 刷新品牌
        categoryBrandManager.loadBrand();
        // 刷新分类
        categoryBrandManager.loadCategory();
        
        LOGGER.info("delete sys brand begin");
        return response;
    }
    
    @RequestMapping(value = "/console/category", method = RequestMethod.POST)
    @ResponseBody
    public Response addCategory(HttpServletRequest request, @Valid @RequestBody CategorySaveRequest categorySaveRequest)
    {
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Category category = categorySaveRequest.toEntity(admin);
        
        categoryService.save(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        
        return response;
    }
    
    @RequestMapping(value = "/console/category/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getCategoryList(HttpServletRequest request, @Valid CategorySearchRequest categorySearchRequest)
    {
        LOGGER.info("query sys category begin");
        ResponseData<PagingData<Category>> responseData = new ResponseData<PagingData<Category>>();
        
        Category category = categorySearchRequest.toEntity(adminSessionManager.getSessionAdminId(request));
        
        PagingData<Category> categoryData = categoryService.selectByIndex(category);
        responseData.setData(categoryData);
        LOGGER.info("query sys category end");
        return responseData;
    }
    
    @RequestMapping(value = "/console/category", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delCategory(HttpServletRequest request, @Valid String id)
    {
        LOGGER.info("delete sys category begin");
        Response response = new Response();
        
        Category category = new Category();
        category.setId(id);
        
        categoryService.deleteById(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        LOGGER.info("delete sys category begin");
        return response;
    }
    
    @RequestMapping(value = "/console/category", method = RequestMethod.PUT)
    @ResponseBody
    public Response editCategory(HttpServletRequest request,
        @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest)
    {
        LOGGER.info("edit sys category begin");
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Category category = categoryUpdateRequest.toEntity(admin);
        
        categoryService.updateById(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        LOGGER.info("edit sys category begin");
        return response;
    }
    
    @RequestMapping(value = "/console/subcategory", method = RequestMethod.POST)
    @ResponseBody
    public Response addSubCategory(HttpServletRequest request,
        @Valid @RequestBody SubCategorySaveRequest subCategorySaveRequest)
    {
        String code = subCategorySaveRequest.getCode();
        String name = subCategorySaveRequest.getName();
        LOGGER.info("add sys sub category begin,sub type code:{},sub type name:{}", code, name);
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        String categoryCode = subCategorySaveRequest.getCategoryCode();
        
        // 根据category code查询当前category
        Category category = categoryService.selectByCode(categoryCode);
        
        if (null == category)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        Map<String, String> subTypeMap = category.getSubTypes();
        
        if (!subTypeMap.containsKey(code))
        {
            subTypeMap.put(code, name);
        }
        else
        {
            response.setCode(BusinessCode.SUBTYPE_FAIL.getCode());
            return response;
        }
        
        JsonMapper mapper = new JsonMapper();
        category.setSubType(mapper.toJson(subTypeMap));
        category.setOperator(admin);
        
        categoryService.updateById(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        LOGGER.info("add sys sub category end");
        return response;
    }
    
    @RequestMapping(value = "/console/subcategory/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getSubCategoryList(HttpServletRequest request)
    {
        LOGGER.info("query sys sub category begin");
        ResponseData<PagingData<CategorySubType>> responseData = new ResponseData<PagingData<CategorySubType>>();
        
        Category category = new Category();
        category.setOffset(0);
        category.setLimit(999);
        
        PagingData<Category> categoryData = categoryService.selectByIndex(category);
        List<Category> categoryList = categoryData.getData();
        List<CategorySubType> categorySubList = new ArrayList<CategorySubType>();
        
        if (!CollectionUtils.isEmpty(categoryList))
        {
            JsonMapper mapper = new JsonMapper();
            for (Category temp : categoryList)
            {
                Map<String, String> subTypeMap = mapper.fromJson(temp.getSubType(), Map.class);
                
                if (!CollectionUtils.isEmpty(subTypeMap))
                {
                    for (Entry<String, String> entry : subTypeMap.entrySet())
                    {
                        CategorySubType subType = new CategorySubType();
                        subType.setCode(entry.getKey());
                        subType.setName(entry.getValue());
                        subType.setCategory(temp.getName());
                        subType.setCategoryId(temp.getId());
                        categorySubList.add(subType);
                    }
                }
            }
        }
        PagingData<CategorySubType> categorySubData = new PagingData<CategorySubType>();
        categorySubData.setData(categorySubList);
        categorySubData.setCount(categorySubList.size());
        responseData.setData(categorySubData);
        LOGGER.info("query sys sub category end");
        return responseData;
    }
    
    @RequestMapping(value = "/console/subcategory", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delSubCategory(HttpServletRequest request, @Valid String categoryId, @Valid String code)
    {
        LOGGER.info("delete sys sub category begin");
        Response response = new Response();
        
        Category category = categoryService.selectById(categoryId);
        
        if (null == category)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        Map<String, String> subTypeMap = category.getSubTypes();
        subTypeMap.remove(code);
        
        JsonMapper mapper = new JsonMapper();
        category.setSubType(mapper.toJson(subTypeMap));
        categoryService.updateById(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        LOGGER.info("delete sys sub category end");
        return response;
    }
    
    @RequestMapping(value = "/console/subcategory", method = RequestMethod.PUT)
    @ResponseBody
    public Response editSubCategory(HttpServletRequest request,
        @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest)
    {
        LOGGER.info("edit sys category begin");
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Category category = categoryUpdateRequest.toEntity(admin);
        
        categoryService.updateById(category);
        // 刷新分类
        categoryBrandManager.loadCategory();
        LOGGER.info("edit sys category begin");
        return response;
    }
}
