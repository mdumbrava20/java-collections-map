package com.endava.internship.collections;

import java.util.*;

public class StudentMap implements Map<Student, Integer> {
    private int size;

    private Node head;

    @Override
    public Integer put(Student student, Integer integer) {
        Integer result = null;
        if (isKeyPresent(head, student)) {
            result = getValueByKey(head, student);
        }
        Node newNode = new Node(student, integer);
        head = insert(head, newNode);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        return isKeyPresent(head, o);
    }

    @Override
    public boolean containsValue(Object o) {
        Boolean isValuePresent = false;
        return isValuePresent(head, o, isValuePresent);
    }

    @Override
    public Integer get(Object o) {
        if (!containsKey(o)) return null;
        return getValueByKey(head, o);
    }

    @Override
    public Integer remove(Object o) {
        if (!isKeyPresent(head, o)) return null;
        return removeByKey(o);
    }

    @Override
    public void putAll(Map<? extends Student, ? extends Integer> map) {
        for (Entry<? extends Student, ? extends Integer> node : map.entrySet()) {
            Node newNode = new Node(node.getKey(), node.getValue());
            head = insert(head, newNode);
        }
    }

    @Override
    public void clear() {
        head = clearTree(head);
        size = 0;
    }

    @Override
    public Set<Student> keySet() {
        print(head);
        Set<Student> studentSet = new HashSet<>();
        getKeySet(head, studentSet);
        return studentSet;
    }

    @Override
    public Collection<Integer> values() {
        Collection<Integer> valueCollection = new ArrayList<>();
        getAllValues(head, valueCollection);
        return valueCollection;
    }

    @Override
    public Set<Entry<Student, Integer>> entrySet() {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    private void print(Node current) {
        if (current != null) {
            print(current.getLeft());
            System.out.println(current.getKey());
            print(current.getRight());
        }
    }

    private Node insert(Node root, Node newNode) {
        if (root == null) {
            root = new Node(newNode.getKey(), newNode.getValue());
            size++;
            return root;
        }

        if (root.getKey().compareTo(newNode.getKey()) < 0) {
            root.setLeft(insert(root.getLeft(), newNode));
        } else if (root.getKey().compareTo(newNode.getKey()) > 0) {
            root.setRight(insert(root.getRight(), newNode));
        } else if (root.getKey().equals(newNode.getKey())) {
            root.setValue(newNode.getValue());
        }
        return root;
    }

    private boolean isKeyPresent(Node current, Object key) {
        if (current == null) {
            return false;
        }
        if (current.getKey().equals(key)) {
            return true;
        }
        if (current.getKey().compareTo((Student) key) > 0) {
            return isKeyPresent(current.getRight(), key);
        } else {
            return isKeyPresent(current.getLeft(), key);
        }
    }

    private boolean isValuePresent(Node current, Object value, Boolean isPresent) {
        if (current != null) {
            if (current.getValue().equals(value)) {
                return true;
            } else {
                isPresent = isValuePresent(current.getLeft(), value, isPresent);
                isPresent = isValuePresent(current.getRight(), value, isPresent);
                return isPresent;
            }
        } else {
            return isPresent;
        }
    }

    private Integer removeByKey(Object o) {
        Integer value;
        Node parentNode = getNodeReference(head, head, o);
        if (head.getKey().equals(o)) {
            value = head.getValue();
            Node newHead = head.getLeft();
            Node rightNode = newHead.getRight();
            newHead.setRight(head.getRight());
            head = newHead;
            head = insert(head, rightNode);
            size -= 2;
            return value;
        }

        Node nodeToDelete;
        if (parentNode.getLeft() == null) {
            nodeToDelete = parentNode.getRight();
            parentNode.setRight(null);
        } else if (parentNode.getRight() == null) {
            nodeToDelete = parentNode.getLeft();
            parentNode.setLeft(null);
        } else {
            if (parentNode.getRight().getKey().equals(o)) {
                nodeToDelete = parentNode.getRight();
                parentNode.setRight(null);
            } else {
                nodeToDelete = parentNode.getLeft();
                parentNode.setLeft(null);
            }
        }

        Node leftRemain = nodeToDelete.getLeft();
        Node rightRemain = nodeToDelete.getRight();
        value = nodeToDelete.getValue();

        if (leftRemain == null && rightRemain == null) {
            size--;
            return value;
        }

        if (leftRemain == null) {
            head = insert(head, rightRemain);
            size -= 2;
        } else if (rightRemain == null) {
            head = insert(head, leftRemain);
            size -= 2;
        } else {
            head = insert(head, leftRemain);
            head = insert(head, rightRemain);
            size -= 3;
        }
        return value;
    }

    private Node getNodeReference(Node current, Node previous, Object key) {
        if (current != null) {
            if (current.getKey().equals(key)) return previous;
            else if (current.getKey().compareTo((Student) key) > 0) {
                return getNodeReference(current.getRight(), current, key);
            } else {
                return getNodeReference(current.getLeft(), current, key);
            }
        } else {
            return null;
        }
    }

    private Node clearTree(Node current) {
        if (current != null) {
            current.setLeft(clearTree(current.getLeft()));
            current.setRight(clearTree(current.getRight()));
        }
        return current;
    }

    private void getAllValues(Node current, Collection<Integer> values) {
        if (current != null) {
            getAllValues(current.getLeft(), values);
            values.add(current.getValue());
            getAllValues(current.getRight(), values);
        }
    }

    private void getKeySet(Node current, Set<Student> set) {
        if (current != null) {
            getKeySet(current.getLeft(), set);
            set.add(current.getKey());
            getKeySet(current.getRight(), set);
        }
    }

    private Integer getValueByKey(Node current, Object key) {
        if (current.getKey().equals(key)) {
            return current.getValue();
        }
        if (current.getKey().compareTo((Student) key) > 0) {
            return getValueByKey(current.getRight(), key);
        } else {
            return getValueByKey(current.getLeft(), key);
        }
    }
}

