package com.retheviper.springbootsample.api.v1.form.board;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentForm implements Serializable {

    /**
     * Article's content
     */
    @NotEmpty
    private String content;
}
