package com.retheviper.springbootsample.api.v1.viewmodel.board;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Article comment view model class.
 *
 * @author retheviper
 */
@Data
public class CommentViewModel implements Serializable {

    /**
     * Comment ID
     */
    private long id;

    /**
     * Comment's content
     */
    private String content;

    /**
     * Created by
     */
    private String createdBy;

    /**
     * Last modified date
     */
    private LocalDateTime lastModifiedDate;
}
