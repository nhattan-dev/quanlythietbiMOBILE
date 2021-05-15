package com.example.mid_term.m_interface;

import java.util.ArrayList;

public interface CRUD<T> {
    boolean insert(T t);
    boolean delete(T t);
    boolean edit(T t);
//    T select(int chapter_id);
    ArrayList<T> selectAll();
//    boolean isExists(N n);
}
