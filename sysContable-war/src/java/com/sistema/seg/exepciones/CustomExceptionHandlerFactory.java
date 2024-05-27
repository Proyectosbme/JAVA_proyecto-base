/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.exepciones;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * 
 * @author BME_PERSONAL
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    /**
     * 
     */
    private ExceptionHandlerFactory parent;

    /**
     * 
     * @param parent 
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /**
     * 
     * @return 
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(parent.getExceptionHandler());
    }
}
