package com.stockholdergame.server.model;

import java.io.Serializable;

/**
 * @author Alexander Savin
 */
public interface Identifiable<I> extends Serializable {

    I getId();
}
