package com.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.entity.Comment;

public interface CommentService extends CrudService<Comment>
{
    @Transactional
    Long saveComment(Comment comment, String saleUserId);
}
