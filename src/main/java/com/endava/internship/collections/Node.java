package com.endava.internship.collections;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {
    private final Student key;

    private Integer value;

    private Node left;

    private Node right;

    public Node(Student key, Integer value) {
        this.key = key;
        this.value = value;
    }

}
