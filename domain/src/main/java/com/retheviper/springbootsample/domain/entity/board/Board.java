package com.retheviper.springbootsample.domain.entity.board;

import com.retheviper.springbootsample.domain.entity.common.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Board entity class.
 *
 * @author retheviper
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Board extends Auditable<String> {

    /**
     * Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Board's name
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Board's description
     */
    private String description;
}
